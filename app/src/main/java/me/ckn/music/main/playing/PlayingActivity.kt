package me.ckn.music.main.playing

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioManager

import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.blurry.Blurry
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import me.wcy.lrcview.LrcView
import me.ckn.music.R
import me.ckn.music.common.BaseMusicActivity
import me.ckn.music.consts.RoutePath
import me.ckn.music.databinding.ActivityPlayingBinding
import me.ckn.music.discover.DiscoverApi

import me.ckn.music.ext.addClickScaleAnimation
import me.ckn.music.ext.registerReceiverCompat
import me.ckn.music.main.playlist.CurrentPlaylistFragment
import me.ckn.music.service.PlayMode
import me.ckn.music.service.PlayState
import me.ckn.music.service.PlayerController
import me.ckn.music.service.likesong.LikeSongProcessor
import me.ckn.music.storage.LrcCache
import me.ckn.music.storage.preference.ConfigPreferences
import me.ckn.music.utils.ImageUtils.transAlpha
import me.ckn.music.utils.TimeUtils
import me.ckn.music.utils.VipUtils
import me.ckn.music.utils.getDuration
import me.ckn.music.utils.getLargeCover
import me.ckn.music.utils.getSongId
import me.ckn.music.utils.isLocal
import me.ckn.music.widget.VipTrialDialog
import me.wcy.router.annotation.Route
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.ext.viewBindings
import top.wangchenyan.common.net.apiCall
import top.wangchenyan.common.utils.LaunchUtils
import top.wangchenyan.common.utils.image.ImageUtils
import java.io.File
import javax.inject.Inject
import kotlin.math.abs

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/4
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：播放页面
 * File Description: Playing page
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@Route(RoutePath.PLAYING)
@AndroidEntryPoint
class PlayingActivity : BaseMusicActivity() {
    private val viewBinding by viewBindings<ActivityPlayingBinding>()

    @Inject
    lateinit var playerController: PlayerController

    @Inject
    lateinit var likeSongProcessor: LikeSongProcessor

    private val audioManager by lazy {
        getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }



    private val defaultCoverBitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.bg_playing_default_cover)
    }
    private val defaultBgBitmap by lazy {
        BitmapFactory.decodeResource(
            resources,
            R.drawable.bg_playing_default,
            BitmapFactory.Options().apply {
                inPreferredConfig = Bitmap.Config.RGB_565
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    outConfig = Bitmap.Config.RGB_565
                }
            }
        )
    }

    private var loadLrcJob: Job? = null

    private var lastProgress = 0
    private var isDraggingProgress = false

    // 优化的颜色缓存，避免重复计算 - 增加缓存大小提升命中率
    private val colorCache = mutableMapOf<String, Int>()
    private val maxCacheSize = 50 // 增加缓存大小到50，提升命中率
    
    // 防止重复更新UI的标志
    private var lastUpdateSongId: Long = -1
    private var isUpdatingUI = false
    
    // 歌词时间更新节流控制 - 性能优化
    private var lastLrcUpdateTime = 0L
    private val lrcUpdateInterval = 100L // 歌词更新最小间隔100ms
    private var lastLrcProgress = -1L // 上次更新的播放进度

    // 封面URL保存 - 用于歌词颜色时序修复（只保存URL，不保存bitmap避免内存问题）
    private var currentCoverUrl: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        initWindowInsets()
        initTitle()
        initVolume()
        initCover()
        initLrc()
        initActions()
        initPlayControl()
        initData()
        switchCoverLrc(true)
    }

    private fun initWindowInsets() {
        configWindowInsets {
            fillNavBar = false
            fillDisplayCutout = false
            statusBarTextDarkStyle = false
            navBarButtonDarkStyle = false
        }

        val updateInsets = { insets: WindowInsetsCompat ->
            val result = insets.getInsets(
                WindowInsetsCompat.Type.statusBars()
                        or WindowInsetsCompat.Type.navigationBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            viewBinding.llContent.updatePadding(
                left = result.left,
                top = result.top,
                right = result.right,
                bottom = result.bottom,
            )
        }
        val insets = ViewCompat.getRootWindowInsets(viewBinding.llContent)
        if (insets != null) {
            updateInsets(insets)
        }
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.llContent) { _, insets ->
            updateInsets(insets)
            insets
        }
    }

    private fun initTitle() {
        // 设置固定的关闭按钮 - 只有点击这个按钮才能返回
        viewBinding.ivCloseButton.setOnClickListener {
            finishWithAnimation()
        }

        // 移除背景点击返回功能，确保只有关闭按钮能返回
        viewBinding.flBackground.setOnClickListener(null)

        // 如果是竖屏模式，也移除标题栏的关闭按钮功能
        if (resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            viewBinding.titleLayout?.ivClose?.setOnClickListener(null)
        }
    }

    private fun finishWithAnimation() {
        // 直接关闭Activity
        finish()
    }



    private fun initVolume() {
        // 区分Android Automotive和普通Android的音量控制
        if (isAndroidAutomotive()) {
            // Android Automotive：使用车载系统音量控制API
            initAutomotiveVolume()
        } else {
            // 普通Android：使用标准媒体音量控制
            initStandardVolume()
        }
    }

    /**
     * 检查是否为Android Automotive平台
     */
    private fun isAndroidAutomotive(): Boolean {
        return packageManager.hasSystemFeature("android.hardware.type.automotive")
    }

    /**
     * 初始化Android Automotive音量控制
     *
     * 根据Android官方文档和CSDN技术博客分析：
     * 1. Android Automotive OS使用固定音量(Fixed Volume)架构
     * 2. 音量通过硬件放大器控制，而非软件混音器
     * 3. AudioManager的STREAM_MUSIC会自动映射到AAOS的音量组和音区
     * 4. CarAudioService负责将音频路由到AUDIO_DEVICE_OUT_BUS设备
     *
     * 参考文档：
     * - https://source.android.com/docs/automotive/audio/archive
     * - https://blog.csdn.net/LJX646566715/article/details/127611501
     * - https://blog.csdn.net/tq08g2z/article/details/129329989
     * - https://developer.android.com/training/cars/apps
     */
    private fun initAutomotiveVolume() {
        try {
            // Android Automotive OS音量控制原理：
            // 1. 应用使用标准AudioManager.STREAM_MUSIC
            // 2. CarAudioService将其映射到相应的音量组(VolumeGroup)
            // 3. 音量组控制硬件放大器，实现真正的音量调节
            // 4. 支持多音区(Multi-Zone)和音频上下文(AudioContext)管理

            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

            viewBinding.volumeLayout.sbVolume.max = maxVolume
            viewBinding.volumeLayout.sbVolume.progress = currentVolume

            // 注册音量变化监听
            val filter = IntentFilter(VOLUME_CHANGED_ACTION)
            registerReceiverCompat(volumeReceiver, filter)

            Log.d(TAG, "Android Automotive volume control initialized successfully")
            Log.d(TAG, "Platform: Android Automotive OS (AAOS)")
            Log.d(TAG, "Volume control method: AudioManager.STREAM_MUSIC -> CarAudioService -> VolumeGroup -> Hardware Amplifier")
            Log.d(TAG, "Max volume: $maxVolume, Current volume: $currentVolume")
            Log.d(TAG, "Audio routing: App -> AudioFlinger -> CarAudioService -> AUDIO_DEVICE_OUT_BUS -> Car Amplifier")

        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize Automotive volume control", e)
            // 降级到标准音量控制
            initStandardVolume()
        }
    }

    /**
     * 初始化标准Android音量控制
     */
    private fun initStandardVolume() {
        try {
            // 标准媒体音量控制
            viewBinding.volumeLayout.sbVolume.max =
                audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            viewBinding.volumeLayout.sbVolume.progress =
                audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

            // 注册音量变化监听
            val filter = IntentFilter(VOLUME_CHANGED_ACTION)
            registerReceiverCompat(volumeReceiver, filter)

            Log.d(TAG, "Standard Android volume control initialized")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize standard volume control", e)
        }
    }

    private fun initCover() {
        val playState = playerController.playState.value
        viewBinding.albumCoverView.initNeedle(playState.isPlaying)
        viewBinding.albumCoverView.setOnClickListener {
            switchCoverLrc(false)
        }
        setDefaultCover()
    }

    private fun initLrc() {
        // 彻底覆盖LrcView的默认颜色设置，强制使用白色
        viewBinding.lrcView.setCurrentColor(android.graphics.Color.WHITE)
        viewBinding.lrcView.setNormalColor(android.graphics.Color.WHITE)
        viewBinding.lrcView.setTimelineTextColor(ContextCompat.getColor(this, R.color.lrc_timeline_highlight_color))
        viewBinding.lrcView.setTimeTextColor(android.graphics.Color.WHITE)

        // 强制刷新视图，确保颜色设置立即生效
        viewBinding.lrcView.invalidate()

        // 延迟再次设置，确保完全覆盖库的默认设置
        viewBinding.lrcView.post {
            viewBinding.lrcView.setCurrentColor(android.graphics.Color.WHITE)
            viewBinding.lrcView.setNormalColor(android.graphics.Color.WHITE)
            viewBinding.lrcView.setTimelineTextColor(ContextCompat.getColor(this, R.color.lrc_timeline_highlight_color))
            viewBinding.lrcView.setTimeTextColor(android.graphics.Color.WHITE)
            Log.d(TAG, "🎨 强制设置LrcView初始颜色为白色")
        }

        viewBinding.lrcView.setDraggable(true) { _, time ->
            val playState = playerController.playState.value
            if (playState.isPlaying || playState.isPausing) {
                playerController.seekTo(time.toInt())
                if (playState.isPausing) {
                    playerController.playPause()
                }
                return@setDraggable true
            }
            return@setDraggable false
        }
        viewBinding.lrcView.setOnTapListener { _: LrcView?, _: Float, _: Float ->
            switchCoverLrc(true)
        }
    }

    private fun initActions() {
        // 喜欢按钮 - 只添加缩放动画效果，不要波纹效果
        viewBinding.controlLayout.ivLike.addClickScaleAnimation(
            scaleDown = 0.9f,
            duration = 200L
        )
        viewBinding.controlLayout.ivLike.setOnClickListener {
            lifecycleScope.launch {
                val song = playerController.currentSong.value ?: return@launch
                val res = likeSongProcessor.like(this@PlayingActivity, song.getSongId())
                if (res.isSuccess()) {
                    updateOnlineActionsState(song)
                } else {
                    toast(res.msg)
                }
            }
        }

        // 下载按钮 - 只添加缩放动画效果，不要波纹效果
        viewBinding.controlLayout.ivDownload.addClickScaleAnimation(
            scaleDown = 0.9f,
            duration = 200L
        )
        viewBinding.controlLayout.ivDownload.setOnClickListener {
            lifecycleScope.launch {
                val song = playerController.currentSong.value ?: return@launch
                val res = apiCall {
                    DiscoverApi.get()
                        .getSongUrl(song.getSongId(), ConfigPreferences.downloadSoundQuality)
                }
                if (res.isSuccessWithData() && res.getDataOrThrow().isNotEmpty()) {
                    val url = res.getDataOrThrow().first().url
                    LaunchUtils.launchBrowser(this@PlayingActivity, url)
                } else {
                    toast(res.msg)
                }
            }
        }
    }

    private fun initPlayControl() {
        lifecycleScope.launch {
            playerController.playMode.collectLatest { playMode ->
                viewBinding.controlLayout.ivMode.setImageLevel(playMode.value)
            }
        }

        // 播放模式按钮 - 只添加缩放动画效果，不要波纹效果
        viewBinding.controlLayout.ivMode.addClickScaleAnimation(
            scaleDown = 0.9f,
            duration = 200L
        )
        viewBinding.controlLayout.ivMode.setOnClickListener {
            switchPlayMode()
        }

        // 播放按钮 - 只添加缩放动画效果，不要波纹效果
        viewBinding.controlLayout.flPlay.addClickScaleAnimation(
            scaleDown = 0.9f,
            duration = 200L
        )
        viewBinding.controlLayout.flPlay.setOnClickListener {
            playerController.playPause()
        }

        // 上一首按钮 - 只添加缩放动画效果，不要波纹效果
        viewBinding.controlLayout.ivPrev.addClickScaleAnimation(
            scaleDown = 0.9f,
            duration = 200L
        )
        viewBinding.controlLayout.ivPrev.setOnClickListener {
            playerController.prev()
        }

        // 下一首按钮 - 只添加缩放动画效果，不要波纹效果
        viewBinding.controlLayout.ivNext.addClickScaleAnimation(
            scaleDown = 0.9f,
            duration = 200L
        )
        viewBinding.controlLayout.ivNext.setOnClickListener {
            playerController.next()
        }

        // 播放列表按钮 - 移除波纹效果，只保留点击事件
        viewBinding.controlLayout.ivPlaylist.setOnClickListener {
            CurrentPlaylistFragment.newInstance()
                .show(supportFragmentManager, CurrentPlaylistFragment.TAG)
        }
        viewBinding.controlLayout.sbProgress.setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (abs(progress - lastProgress) >= DateUtils.SECOND_IN_MILLIS) {
                    viewBinding.controlLayout.tvCurrentTime.text =
                        TimeUtils.formatMs(progress.toLong())
                    lastProgress = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isDraggingProgress = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar ?: return
                isDraggingProgress = false
                val playState = playerController.playState.value
                if (playState.isPlaying || playState.isPausing) {
                    val progress = seekBar.progress
                    val currentSong = playerController.currentSong.value

                    // 检查VIP试听限制，确保在试听范围内的拖拽不会重新加载
                    if (currentSong != null && VipUtils.needTrialLimit(currentSong)) {
                        val trialEndTime = VipUtils.getTrialEndTime(currentSong)
                        if (progress <= trialEndTime) {
                            // 在试听范围内，正常seek
                            playerController.seekTo(progress)
                            if (viewBinding.lrcView.hasLrc()) {
                                viewBinding.lrcView.updateTime(progress.toLong())
                            }
                        }
                        // 超出试听范围的情况已经在VipTrialSeekBar中处理了
                    } else {
                        // 非VIP歌曲，正常处理
                        playerController.seekTo(progress)
                        if (viewBinding.lrcView.hasLrc()) {
                            viewBinding.lrcView.updateTime(progress.toLong())
                        }
                    }
                } else {
                    seekBar.progress = 0
                }
            }
        })
        viewBinding.volumeLayout.sbVolume.setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 实时显示音量变化（可选）
                if (fromUser) {
                    Log.d(TAG, "Volume changed to: $progress")
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.d(TAG, "Volume adjustment started")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar ?: return
                setSystemVolume(seekBar.progress)
            }
        })
    }

    private fun initData() {
        playerController.currentSong.observe(this) { song ->
            if (song != null) {
                // 避免重复更新相同的歌曲
                val songId = song.getSongId()
                if (songId == lastUpdateSongId || isUpdatingUI) {
                    return@observe
                }
                
                lastUpdateSongId = songId
                isUpdatingUI = true
                
                // 立即重置歌词颜色为白色，防止显示默认红色
                viewBinding.lrcView.setCurrentColor(android.graphics.Color.WHITE)
                viewBinding.lrcView.setNormalColor(android.graphics.Color.WHITE)
                viewBinding.lrcView.invalidate()
                // 更新歌曲信息布局（在黑胶封面下方）- 横屏和竖屏模式都存在
                viewBinding.tvSongTitle?.text = song.mediaMetadata.title
                viewBinding.tvSongArtist?.text = song.mediaMetadata.artist

                // 更新VIP标签显示
                updateVipLabel(song)

                // 启动跑马灯效果
                startMarqueeEffect()

                // 为歌曲信息添加渐变文字效果
                setupGradientText()

                // 标题栏的歌曲信息更新（仅在竖屏模式下存在）
                if (resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
                    viewBinding.titleLayout?.tvTitle?.text = song.mediaMetadata.title
                    viewBinding.titleLayout?.tvArtist?.text = song.mediaMetadata.artist
                }

                // 优化进度条初始化 - 确保数据有效性
                // 优先从 MediaMetadata 的 durationMs 获取时长（与PlayBar保持一致）
                val initialDuration = song.mediaMetadata.durationMs ?: song.mediaMetadata.getDuration()
                Log.d(TAG, "初始duration检查: durationMs=${song.mediaMetadata.durationMs}, getDuration()=${song.mediaMetadata.getDuration()}, 使用值=$initialDuration")
                
                // 如果duration无效，等待MediaItem增强完成后再次检查
                if (initialDuration <= 0) {
                    Log.w(TAG, "歌曲 duration 无效，等待数据增强...")
                    // 延迟一小段时间等待validateAndEnhanceMediaItem完成
                    lifecycleScope.launch {
                        delay(200) // 增加到200ms，给更多时间加载
                        val updatedSong = playerController.currentSong.value
                        if (updatedSong != null && updatedSong.mediaId == song.mediaId) {
                            val updatedDuration = updatedSong.mediaMetadata.durationMs ?: updatedSong.mediaMetadata.getDuration()
                            if (updatedDuration > 0) {
                                Log.d(TAG, "检测到duration更新: $initialDuration -> $updatedDuration，重新初始化进度条")
                                updateProgressBar(updatedSong, updatedDuration)
                            }
                        }
                    }
                    
                    // 暂时设置一个默认值，避免进度条完全不可用
                    updateProgressBar(song, 0)
                } else {
                    updateProgressBar(song, initialDuration)
                }
                updateCover(song)
                updateLrc(song)
                viewBinding.albumCoverView.reset()
                updatePlayState(playerController.playState.value)
                updateOnlineActionsState(song)
                
                // 更新完成，重置标志
                isUpdatingUI = false
            } else {
                // 当前歌曲为null时，不要立即关闭播放页面
                // 可能是临时状态，等待一段时间再决定是否关闭
                Log.w(TAG, "Current song is null, waiting for recovery...")
                lifecycleScope.launch {
                    delay(1000) // 等待1秒
                    // 再次检查，如果仍然为null且播放列表为空，则关闭
                    if (playerController.currentSong.value == null &&
                        playerController.playlist.value.isNullOrEmpty()) {
                        Log.w(TAG, "No songs available, closing playing activity")
                        finish()
                    }
                }
            }
        }

        lifecycleScope.launch {
            playerController.playState.collectLatest { playState ->
                updatePlayState(playState)
            }
        }

        lifecycleScope.launch {
            playerController.playProgress.collectLatest { progress ->
                if (isDraggingProgress.not()) {
                    viewBinding.controlLayout.sbProgress.progress = progress.toInt()
                }
                
                // 歌词时间更新节流优化 - 避免频繁更新导致卡顿
                val currentTime = System.currentTimeMillis()
                val progressDiff = kotlin.math.abs(progress - lastLrcProgress)
                
                if (viewBinding.lrcView.hasLrc() && 
                    (currentTime - lastLrcUpdateTime >= lrcUpdateInterval || progressDiff >= 1000)) {
                    // 只在时间间隔达到100ms或进度跳跃超过1秒时更新歌词
                    viewBinding.lrcView.updateTime(progress)
                    lastLrcUpdateTime = currentTime
                    lastLrcProgress = progress
                }

                // 检查VIP试听限制
                checkVipTrialLimit(progress)
            }
        }

        lifecycleScope.launch {
            playerController.bufferingPercent.collectLatest { percent ->
                viewBinding.controlLayout.sbProgress.secondaryProgress =
                    viewBinding.controlLayout.sbProgress.max * percent / 100
            }
        }

        // 监听爱心状态变化，确保状态同步
        lifecycleScope.launch {
            likeSongProcessor.likeStateChanged.collectLatest { changedSongId ->
                changedSongId ?: return@collectLatest
                val currentSong = playerController.currentSong.value
                if (currentSong != null && currentSong.getSongId() == changedSongId) {
                    // 当前播放歌曲的爱心状态发生变化，更新UI
                    updateOnlineActionsState(currentSong)
                }
            }
        }
    }

    private fun updateCover(song: MediaItem) {
        currentCoverUrl = ""
        setDefaultCover()
        
        // 立即设置歌词为白色，防止封面加载过程中显示红色
        viewBinding.lrcView.setCurrentColor(android.graphics.Color.WHITE)
        viewBinding.lrcView.setNormalColor(android.graphics.Color.WHITE)
        viewBinding.lrcView.invalidate()
        
        val coverUrl = song.getLargeCover()
        ImageUtils.loadBitmap(coverUrl) {
            if (it.isSuccessWithData()) {
                val bitmap = it.getDataOrThrow()

                // 保存当前封面URL，用于歌词颜色时序修复
                currentCoverUrl = coverUrl

                viewBinding.albumCoverView.setCoverBitmap(bitmap)
                Blurry.with(this).sampling(10).from(bitmap).into(viewBinding.ivPlayingBg)
                updateLrcMask()

                // 动态更新歌词高亮颜色
                updateLrcHighlightColor(bitmap, coverUrl)
            }
        }
    }

    private fun setDefaultCover() {
        currentCoverUrl = ""

        viewBinding.albumCoverView.setCoverBitmap(defaultCoverBitmap)
        viewBinding.ivPlayingBg.setImageBitmap(defaultBgBitmap)
        updateLrcMask()

        // 使用默认封面时也更新歌词颜色
        updateLrcHighlightColor(defaultCoverBitmap, "")
    }

    /**
     * 动态更新歌词高亮颜色
     * 从专辑封面提取主色调并应用到歌词高亮显示
     * 注意：只更新高亮颜色，默认歌词颜色保持白色
     * 只在有歌词内容时才更新颜色，确保状态文本（如"歌词加载中…"）始终为白色
     * @param bitmap 专辑封面Bitmap
     * @param coverUrl 专辑封面URL，用于缓存
     */
    private fun updateLrcHighlightColor(bitmap: Bitmap?, coverUrl: String) {
        lifecycleScope.launch {
            val startTime = System.currentTimeMillis() // 性能监控开始时间
            try {
                // 检查歌词加载状态，如果没有加载则延迟更新
                if (!viewBinding.lrcView.hasLrc()) {
                    Log.d(TAG, "⏳ 歌词还未加载，延迟500ms后重试颜色更新")
                    // 延迟重试，给歌词加载更多时间
                    kotlinx.coroutines.delay(500)
                    
                    // 再次检查，如果还是没有歌词，也强制更新颜色
                    if (!viewBinding.lrcView.hasLrc()) {
                        Log.d(TAG, "⚠️ 歌词仍未加载，但强制进行颜色更新")
                    } else {
                        Log.d(TAG, "✅ 延迟后歌词已加载")
                    }
                }

                // 检查缓存
                val cacheKey = coverUrl.hashCode().toString()
                val cachedColor = colorCache[cacheKey]

                val highlightColor = if (cachedColor != null) {
                    cachedColor
                } else if (bitmap != null) {
                    // 优化：将bitmap操作完全移到IO线程，避免阻塞主线程
                    val extractedColor = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                        extractSmartColor(bitmap)
                    }
                    
                    // 回到主线程更新缓存
                    kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                        // 优化缓存管理：使用LRU策略
                        if (colorCache.size >= maxCacheSize) {
                            // 清理最旧的缓存项（简单实现）
                            val oldestKey = colorCache.keys.firstOrNull()
                            oldestKey?.let { colorCache.remove(it) }
                        }
                        colorCache[cacheKey] = extractedColor
                    }
                    extractedColor
                } else {
                    android.graphics.Color.WHITE
                }

                // 批量更新UI：避免重复的setColor调用
                updateLrcColors(highlightColor)

                // 性能监控：记录颜色更新性能
                val updateTime = System.currentTimeMillis() - startTime
                Log.d(TAG, "✅ 歌词高亮色更新完成: ${Integer.toHexString(highlightColor)} (缓存命中: ${cachedColor != null}, 耗时: ${updateTime}ms)")
                Log.d(TAG, "📊 缓存状态: ${colorCache.size}/$maxCacheSize")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update lyric highlight color", e)
                // 失败时使用默认白色
                updateLrcColors(android.graphics.Color.WHITE)
            }
        }
    }
    
    /**
     * 强制更新歌词高亮颜色 - 跳过hasLrc检查
     * 用于歌词加载完成后强制进行颜色计算
     */
    private fun forceUpdateLrcHighlightColor(bitmap: Bitmap?, coverUrl: String) {
        lifecycleScope.launch {
            val startTime = System.currentTimeMillis()
            try {
                Log.d(TAG, "🚀 强制更新歌词高亮颜色 - 跳过hasLrc检查")

                // 检查缓存
                val cacheKey = coverUrl.hashCode().toString()
                val cachedColor = colorCache[cacheKey]

                val highlightColor = if (cachedColor != null) {
                    cachedColor
                } else if (bitmap != null) {
                    // 优化：将bitmap操作完全移到IO线程，避免阻塞主线程
                    val extractedColor = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                        extractSmartColor(bitmap)
                    }
                    
                    // 回到主线程更新缓存
                    kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                        // 优化缓存管理：使用LRU策略
                        if (colorCache.size >= maxCacheSize) {
                            // 清理最旧的缓存项（简单实现）
                            val oldestKey = colorCache.keys.firstOrNull()
                            oldestKey?.let { colorCache.remove(it) }
                        }
                        colorCache[cacheKey] = extractedColor
                    }
                    extractedColor
                } else {
                    android.graphics.Color.WHITE
                }

                // 强制更新UI
                updateLrcColors(highlightColor)

                val updateTime = System.currentTimeMillis() - startTime
                Log.d(TAG, "✅ 强制歌词高亮色更新完成: ${Integer.toHexString(highlightColor)} (缓存命中: ${cachedColor != null}, 耗时: ${updateTime}ms)")
            } catch (e: Exception) {
                Log.e(TAG, "强制更新歌词高亮颜色失败", e)
                updateLrcColors(android.graphics.Color.WHITE)
            }
        }
    }

    /**
     * 批量更新歌词颜色 - 避免重复UI操作
     * 彻底覆盖LrcView的默认颜色，确保普通歌词始终为白色
     */
    private fun updateLrcColors(highlightColor: Int) {
        Log.d(TAG, "🎨 updateLrcColors() 被调用")
        Log.d(TAG, "🌈 设置高亮颜色: #${Integer.toHexString(highlightColor)}")

        // 确保在主线程更新UI
        runOnUiThread {
            // 设置高亮颜色（可以是自适应颜色）
            viewBinding.lrcView.setCurrentColor(highlightColor)
            // 强制设置普通歌词颜色为白色，彻底覆盖库的默认设置
            viewBinding.lrcView.setNormalColor(android.graphics.Color.WHITE)
            // 确保时间线文本颜色也正确
            viewBinding.lrcView.setTimelineTextColor(ContextCompat.getColor(this@PlayingActivity, R.color.lrc_timeline_highlight_color))
            viewBinding.lrcView.setTimeTextColor(android.graphics.Color.WHITE)

            // 强制刷新视图，确保颜色更改立即生效
            viewBinding.lrcView.invalidate()
            
            // 再次延迟设置，确保完全覆盖
            viewBinding.lrcView.postDelayed({
                viewBinding.lrcView.setCurrentColor(highlightColor)
                viewBinding.lrcView.setNormalColor(android.graphics.Color.WHITE)
                viewBinding.lrcView.invalidate()
            }, 100)
        }

        Log.d(TAG, "✅ 歌词颜色强制设置完成 - 普通歌词: 白色, 高亮歌词: #${Integer.toHexString(highlightColor)}")
    }

    /**
     * 高性能颜色提取方法
     * 优化版：减少采样点，提升性能，从9点减少到4点采样
     */
    private fun extractSmartColor(bitmap: Bitmap): Int {
        return try {
            val width = bitmap.width
            val height = bitmap.height

            // 优化：只从4个关键点采样颜色，减少70%的计算量
            val samplePoints = listOf(
                Pair(width / 3, height / 3),         // 左上区域  
                Pair(width * 2 / 3, height / 3),     // 右上区域
                Pair(width / 3, height * 2 / 3),     // 左下区域
                Pair(width * 2 / 3, height * 2 / 3)  // 右下区域
            )

            // 直接计算最优颜色，避免创建临时列表
            var bestColor = android.graphics.Color.WHITE
            var maxSaturation = 0

            samplePoints.forEach { (x, y) ->
                val color = bitmap.getPixel(x, y)
                val red = android.graphics.Color.red(color)
                val green = android.graphics.Color.green(color)
                val blue = android.graphics.Color.blue(color)
                
                // 快速饱和度计算，避免复杂的数学运算
                val saturation = maxOf(red, green, blue) - minOf(red, green, blue)
                if (saturation > maxSaturation) {
                    maxSaturation = saturation
                    bestColor = color
                }
            }

            // 快速颜色调整，避免复杂的调整算法
            adjustColorForVisibilityFast(bestColor)
        } catch (e: Exception) {
            android.graphics.Color.WHITE
        }
    }

    /**
     * 快速颜色调整方法 - 性能优化版本
     * 简化算法，减少计算复杂度，提升性能
     */
    private fun adjustColorForVisibilityFast(color: Int): Int {
        val red = android.graphics.Color.red(color)
        val green = android.graphics.Color.green(color)  
        val blue = android.graphics.Color.blue(color)

        // 快速亮度估算，避免浮点运算
        val brightness = (red + green + blue) / 3

        return when {
            brightness < 100 -> {
                // 暗色：简单增亮
                android.graphics.Color.rgb(
                    minOf(red * 2, 255),
                    minOf(green * 2, 255),
                    minOf(blue * 2, 255)
                )
            }
            brightness > 200 -> {
                // 亮色：适度调暗
                android.graphics.Color.rgb(
                    maxOf(red * 4 / 5, 120),
                    maxOf(green * 4 / 5, 120),
                    maxOf(blue * 4 / 5, 120)
                )
            }
            else -> {
                // 中等亮度：直接使用原色
                color
            }
        }
    }
    
    /**
     * 调整颜色以确保在深色背景上的可见性和美观性（保留原方法作为备用）
     */
    private fun adjustColorForVisibility(color: Int): Int {
        val red = android.graphics.Color.red(color)
        val green = android.graphics.Color.green(color)
        val blue = android.graphics.Color.blue(color)

        // 计算感知亮度
        val brightness = (0.299 * red + 0.587 * green + 0.114 * blue).toInt()

        return when {
            brightness < 80 -> {
                // 非常暗的颜色：大幅增亮并增加饱和度
                val factor = 2.5f
                android.graphics.Color.rgb(
                    (red * factor).coerceAtMost(255f).toInt(),
                    (green * factor).coerceAtMost(255f).toInt(),
                    (blue * factor).coerceAtMost(255f).toInt()
                )
            }
            brightness < 150 -> {
                // 中等暗度：适度增亮
                val factor = 1.8f
                android.graphics.Color.rgb(
                    (red * factor).coerceAtMost(255f).toInt(),
                    (green * factor).coerceAtMost(255f).toInt(),
                    (blue * factor).coerceAtMost(255f).toInt()
                )
            }
            brightness > 220 -> {
                // 太亮的颜色：适度降低亮度但保持鲜艳
                val factor = 0.8f
                android.graphics.Color.rgb(
                    (red * factor).coerceAtLeast(100f).toInt(),
                    (green * factor).coerceAtLeast(100f).toInt(),
                    (blue * factor).coerceAtLeast(100f).toInt()
                )
            }
            else -> {
                // 亮度适中：直接使用，但确保最小亮度
                android.graphics.Color.rgb(
                    red.coerceAtLeast(120),
                    green.coerceAtLeast(120),
                    blue.coerceAtLeast(120)
                )
            }
        }
    }

    private fun updateLrcMask() {
        viewBinding.ivLrcTopMask?.let { updateLrcMask(it, true) }
        viewBinding.ivLrcBottomMask?.let { updateLrcMask(it, false) }
    }

    private fun updateLrcMask(maskView: ImageView, topToBottom: Boolean) {
        maskView.doOnLayout {
            val bitmap = com.blankj.utilcode.util.ImageUtils.view2Bitmap(viewBinding.flBackground)
            val location = IntArray(2)
            maskView.getLocationOnScreen(location)
            val clippedBitmap = com.blankj.utilcode.util.ImageUtils.clip(
                bitmap,
                location[0],
                location[1],
                maskView.width,
                maskView.height,
                true
            )
            val alphaBitmap = clippedBitmap.transAlpha(topToBottom)
            clippedBitmap.recycle()
            maskView.setImageBitmap(alphaBitmap)
        }
    }

    private fun updateLrc(song: MediaItem) {
        loadLrcJob?.cancel()
        loadLrcJob = null
        
        // 立即设置歌词为白色，防止加载过程中显示红色
        viewBinding.lrcView.setCurrentColor(android.graphics.Color.WHITE)
        viewBinding.lrcView.setNormalColor(android.graphics.Color.WHITE)
        viewBinding.lrcView.invalidate()
        
        val lrcPath = LrcCache.getLrcFilePath(song)
        if (lrcPath?.isNotEmpty() == true) {
            loadLrc(lrcPath)
            return
        }
        viewBinding.lrcView.loadLrc("")
        if (song.isLocal()) {
            setLrcLabel("暂无歌词")
        } else {
            setLrcLabel("歌词加载中…")
            loadLrcJob = lifecycleScope.launch {
                kotlin.runCatching {
                    val lrcWrap = DiscoverApi.get().getLrc(song.getSongId())
                    if (lrcWrap.code == 200 && lrcWrap.lrc.isValid()) {
                        lrcWrap
                    } else {
                        throw IllegalStateException("lrc is invalid")
                    }
                }.onSuccess { lrcWrap ->
                    // 保存主歌词文件
                    val file = LrcCache.saveLrcFile(song, lrcWrap.lrc.lyric)

                    // 加载双语歌词（如果有翻译歌词）
                    if (lrcWrap.tlyric.isValid()) {
                        // 使用LrcView的双语歌词文本加载方法
                        viewBinding.lrcView.loadLrc(lrcWrap.lrc.lyric, lrcWrap.tlyric.lyric)
                        setLrcLabel("")
                        Log.d(TAG, "Loading dual language lyrics")

                        // 双语歌词加载完成后，立即强制设置白色
                        viewBinding.lrcView.post {
                            viewBinding.lrcView.setCurrentColor(android.graphics.Color.WHITE)
                            viewBinding.lrcView.setNormalColor(android.graphics.Color.WHITE)
                            Log.d(TAG, "🎨 双语歌词加载完成后强制设置白色")
                            
                            // 延迟触发颜色更新，确保歌词已完全加载
                            viewBinding.lrcView.postDelayed({
                                triggerLrcColorUpdate()
                            }, 200)
                        }
                    } else {
                        // 只有主歌词时使用文件路径加载
                        loadLrc(file.path)
                        Log.d(TAG, "Loading single language lyrics")
                    }
                }.onFailure {
                    Log.e(TAG, "load lrc error", it)
                    setLrcLabel("歌词加载失败")
                }
            }
        }
    }

    private fun loadLrc(path: String) {
        val file = File(path)
        viewBinding.lrcView.loadLrc(file)

        // 歌词加载完成后，立即强制设置白色，防止显示默认红色
        viewBinding.lrcView.post {
            viewBinding.lrcView.setCurrentColor(android.graphics.Color.WHITE)
            viewBinding.lrcView.setNormalColor(android.graphics.Color.WHITE)
            Log.d(TAG, "🎨 歌词加载完成后强制设置白色")
            
            // 延迟触发颜色更新，确保歌词已完全加载
            viewBinding.lrcView.postDelayed({
                triggerLrcColorUpdate()
            }, 200)
        }
    }

    private fun setLrcLabel(label: String) {
        viewBinding.lrcView.setLabel(label)
        // 无论什么情况都强制设置白色，彻底覆盖LrcView的默认颜色
        viewBinding.lrcView.setCurrentColor(android.graphics.Color.WHITE)
        viewBinding.lrcView.setNormalColor(android.graphics.Color.WHITE)
        Log.d(TAG, "🔤 强制设置歌词颜色为白色: $label")
    }

    /**
     * 触发歌词颜色更新 - 用于歌词加载完成后的时序修复
     * 当歌词真正加载完成后，重新加载封面并计算自适应颜色
     */
    private fun triggerLrcColorUpdate() {
        Log.d(TAG, "🔄 triggerLrcColorUpdate() 被调用")
        Log.d(TAG, "📝 hasLrc(): ${viewBinding.lrcView.hasLrc()}")
        Log.d(TAG, "🖼️ currentCoverUrl: $currentCoverUrl")

        // 强制进行颜色计算，不依赖hasLrc()状态
        // 颜色计算只依赖封面，不应该受歌词加载状态影响
        Log.d(TAG, "🎨 强制进行颜色计算")

        if (currentCoverUrl.isNotEmpty()) {
            Log.d(TAG, "🔄 重新加载封面: $currentCoverUrl")
            // 重新加载封面进行颜色计算，利用ImageUtils的缓存机制
            ImageUtils.loadBitmap(currentCoverUrl) { result ->
                if (result.isSuccessWithData()) {
                    val bitmap = result.getDataOrThrow()
                    Log.d(TAG, "✅ 封面加载成功，开始颜色提取")
                    // 强制进行颜色更新，跳过hasLrc检查
                    forceUpdateLrcHighlightColor(bitmap, currentCoverUrl)
                } else {
                    Log.w(TAG, "⚠️ 封面加载失败，使用默认封面")
                    // 加载失败时使用默认封面
                    forceUpdateLrcHighlightColor(defaultCoverBitmap, "")
                }
            }
        } else {
            Log.d(TAG, "🎨 使用默认封面进行颜色计算")
            // 没有封面URL时使用默认封面
            forceUpdateLrcHighlightColor(defaultCoverBitmap, "")
        }
    }



    private fun switchCoverLrc(showCover: Boolean) {
        if (resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            viewBinding.albumCoverView.isVisible = showCover
            viewBinding.lrcLayout.isVisible = showCover.not()
        }
    }

    private fun switchPlayMode() {
        val mode = when (playerController.playMode.value) {
            PlayMode.Loop -> PlayMode.Shuffle
            PlayMode.Shuffle -> PlayMode.Single
            PlayMode.Single -> PlayMode.Loop
        }
        toast(mode.nameRes)
        playerController.setPlayMode(mode)
    }

    private fun updatePlayState(playState: PlayState) {
        when (playState) {
            PlayState.Preparing -> {
                viewBinding.controlLayout.flPlay.isEnabled = false
                viewBinding.controlLayout.ivPlay.isSelected = false
                viewBinding.controlLayout.loadingProgress.isVisible = true
                viewBinding.albumCoverView.pause()
            }

            PlayState.Playing -> {
                viewBinding.controlLayout.flPlay.isEnabled = true
                viewBinding.controlLayout.ivPlay.isSelected = true
                viewBinding.controlLayout.loadingProgress.isVisible = false
                viewBinding.albumCoverView.start()
            }

            else -> {
                viewBinding.controlLayout.flPlay.isEnabled = true
                viewBinding.controlLayout.ivPlay.isSelected = false
                viewBinding.controlLayout.loadingProgress.isVisible = false
                viewBinding.albumCoverView.pause()
            }
        }
    }

    private fun updateOnlineActionsState(song: MediaItem) {
        viewBinding.controlLayout.llActions.isVisible = song.isLocal().not()
        viewBinding.controlLayout.ivLike.isSelected = likeSongProcessor.isLiked(song.getSongId())
    }

    /**
     * 重写返回键行为 - 禁用返回键，只能通过关闭按钮返回
     */
    override fun onBackPressed() {
        // 不执行任何操作，禁用返回键
        // 用户只能通过点击关闭按钮返回
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(volumeReceiver)
        defaultCoverBitmap.recycle()
        defaultBgBitmap.recycle()
        // 取消duration监听任务
        durationWatchJob?.cancel()
        durationWatchJob = null
    }

    /**
     * 设置系统音量，区分Android Automotive和普通Android
     *
     * Android Automotive OS音量控制流程：
     * 1. 应用调用AudioManager.setStreamVolume(STREAM_MUSIC, volume, flags)
     * 2. AudioFlinger接收音量设置请求
     * 3. CarAudioService拦截并处理音量设置
     * 4. 根据car_audio_configuration.xml配置映射到对应的音量组
     * 5. 音量组控制硬件放大器实现真正的音量调节
     * 6. 支持多音区和音频上下文管理
     */
    private fun setSystemVolume(volume: Int) {
        try {
            if (isAndroidAutomotive()) {
                // Android Automotive OS：使用STREAM_MUSIC进行媒体音量控制
                // 根据AAOS架构，这会触发以下流程：
                // AudioManager -> AudioFlinger -> CarAudioService -> VolumeGroup -> Hardware Amplifier
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    volume,
                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE
                )
                Log.d(TAG, "AAOS media volume set to: $volume")
                Log.d(TAG, "Volume routing: STREAM_MUSIC -> CarAudioService -> VolumeGroup -> Hardware Amplifier")
            } else {
                // 普通Android：使用标准媒体音量控制
                // 直接通过AudioFlinger控制软件混音器音量
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    volume,
                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE
                )
                Log.d(TAG, "Standard Android volume set to: $volume")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to set volume to: $volume", e)
        }
    }

    private val volumeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            try {
                val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                viewBinding.volumeLayout.sbVolume.progress = currentVolume
                Log.d(TAG, "Volume receiver updated to: $currentVolume")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update volume from receiver", e)
            }
        }
    }

    /**
     * 启动跑马灯效果
     * 确保歌曲名和歌手名能够完整滚动显示
     */
    private fun startMarqueeEffect() {
        // 启动歌曲标题跑马灯
        viewBinding.tvSongTitle?.let { titleView ->
            titleView.isSelected = true
            titleView.requestFocus()
        }

        // 启动歌手名跑马灯
        viewBinding.tvSongArtist?.let { artistView ->
            artistView.isSelected = true
            artistView.requestFocus()
        }
    }

    /**
     * 更新VIP标签显示
     * 根据歌曲的fee字段显示相应的VIP标签，使用优化的渲染方法
     */
    private fun updateVipLabel(song: MediaItem) {
        val fee = VipUtils.getSongFee(song)

        viewBinding.tvVipLabel?.let { labelView ->
            VipUtils.updateVipLabelOptimized(labelView, fee)
        }
    }

    /**
     * 更新VIP试听标记
     * 在进度条上显示试听终点标记
     */
    private fun updateVipTrialMark(song: MediaItem) {
        val needTrial = VipUtils.needTrialLimit(song)
        if (needTrial) {
            val trialProgress = VipUtils.getTrialProgress(song)
            viewBinding.controlLayout.sbProgress.setTrialMark(trialProgress, true)
        } else {
            viewBinding.controlLayout.sbProgress.setTrialMark(0f, false)
        }
    }

    /**
     * 检查VIP试听限制
     * 当到达试听终点时显示VIP提示对话框
     */
    private var hasShownVipDialog = false  // 防止重复弹出对话框

    private fun checkVipTrialLimit(currentProgress: Long) {
        val currentSong = playerController.currentSong.value ?: return

        if (VipUtils.isTrialEndReached(currentSong, currentProgress) && !hasShownVipDialog) {
            // 检查是否已经显示过VIP对话框（全局状态）
            if (ConfigPreferences.vipDialogShown) {
                return  // 已经显示过，不再显示
            }

            hasShownVipDialog = true

            // 暂停播放（只有在播放状态时才暂停）
            if (playerController.playState.value == PlayState.Playing) {
                playerController.playPause()
            }

            // 显示VIP试听对话框
            showVipTrialDialog()
        }
    }

    /**
     * 显示VIP试听提示对话框
     */
    private fun showVipTrialDialog() {
        val dialog = VipTrialDialog.newInstance()
            .setOnVipClickListener {
                // TODO: 跳转到VIP开通页面
                toast("VIP功能开发中...")
            }
            .setOnNextClickListener {
                // 播放下一首
                playerController.next()
            }
            .setOnDismissListener {
                // 对话框关闭时重置状态，并标记全局已显示
                hasShownVipDialog = false
                ConfigPreferences.vipDialogShown = true
            }

        dialog.show(supportFragmentManager, "VipTrialDialog")
    }



    /**
     * 更新进度条和时间显示
     * 统一处理进度条初始化和更新逻辑
     */
    private fun updateProgressBar(song: MediaItem, duration: Long) {
        val maxProgress = if (duration > 0) {
            duration.toInt()
        } else {
            // 如果 duration 无效，设置为1避免进度条除零错误
            Log.w(TAG, "歌曲 duration 无效: $duration，暂时设置为1")
            1
        }

        viewBinding.controlLayout.sbProgress.max = maxProgress
        viewBinding.controlLayout.sbProgress.progress = 0  // 立即重置为0，提供即时反馈
        viewBinding.controlLayout.sbProgress.secondaryProgress = 0

        // 设置VIP试听标记
        updateVipTrialMark(song)

        // 重置VIP对话框状态
        hasShownVipDialog = false
        lastProgress = 0

        // 立即更新时间显示
        viewBinding.controlLayout.tvCurrentTime.text = TimeUtils.formatMs(0)
        val totalTimeText = if (duration > 0) {
            TimeUtils.formatMs(duration)
        } else {
            "--:--" // 如果 duration 无效，显示占位符
        }
        viewBinding.controlLayout.tvTotalTime.text = totalTimeText

        Log.d(TAG, "进度条初始化完成: max=$maxProgress, duration=$duration, totalTime=$totalTimeText")
        
        // 如果duration无效，设置定时任务监听duration更新
        if (duration <= 0) {
            watchForDurationUpdate(song)
        }
    }
    
    /**
     * 监听duration更新的协程任务
     */
    private var durationWatchJob: kotlinx.coroutines.Job? = null
    
    /**
     * 监听播放器duration更新
     */
    private fun watchForDurationUpdate(song: MediaItem) {
        // 取消之前的监听任务
        durationWatchJob?.cancel()
        
        durationWatchJob = lifecycleScope.launch {
            // 每500ms检查一次，最多检查20次（10秒）
            repeat(20) { attempt ->
                delay(500)
                val currentSong = playerController.currentSong.value
                if (currentSong?.mediaId == song.mediaId) {
                    // 尝试从MediaMetadata获取duration
                    val mediaDuration = currentSong.mediaMetadata.durationMs ?: currentSong.mediaMetadata.getDuration()
                    
                    if (mediaDuration > 0) {
                        Log.d(TAG, "Duration更新成功 (尝试 ${attempt + 1}): $mediaDuration")
                        updateProgressBar(currentSong, mediaDuration)
                        return@launch
                    }
                }
            }
            Log.w(TAG, "无法获取有效的duration，歌曲可能还在加载中")
        }
    }

    /**
     * 为歌曲信息设置渐变文字效果
     * 歌曲标题：白色到浅蓝色的渐变
     * 艺术家名称：同上
     */
    private fun setupGradientText() {
        viewBinding.tvSongTitle?.let { titleView ->
            titleView.post {
                val width = titleView.width.toFloat()
                if (width > 0) {
                    val gradient = android.graphics.LinearGradient(
                        0f, 0f, width, 0f,
                        intArrayOf(
                            android.graphics.Color.WHITE,
                            android.graphics.Color.parseColor("#E3F2FD"),
                            android.graphics.Color.parseColor("#BBDEFB")
                        ),
                        floatArrayOf(0f, 0.5f, 1f),
                        android.graphics.Shader.TileMode.CLAMP
                    )
                    titleView.paint.shader = gradient
                    titleView.invalidate()
                }
            }
        }

        viewBinding.tvSongArtist?.let { artistView ->
            artistView.post {
                val width = artistView.width.toFloat()
                if (width > 0) {
                    val gradient = android.graphics.LinearGradient(
                        0f, 0f, width, 0f,
                        intArrayOf(
                            android.graphics.Color.WHITE,
                            android.graphics.Color.parseColor("#E3F2FD"),
                            android.graphics.Color.parseColor("#BBDEFB")
                        ),
                        floatArrayOf(0f, 0.5f, 1f),
                        android.graphics.Shader.TileMode.CLAMP
                    )
                    artistView.paint.shader = gradient
                    artistView.invalidate()
                }
            }
        }
    }

    companion object {
        private const val TAG = "PlayingActivity"
        private const val VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION"
    }
}