package me.wcy.music.main.playing

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
import me.wcy.music.R
import me.wcy.music.common.BaseMusicActivity
import me.wcy.music.consts.RoutePath
import me.wcy.music.databinding.ActivityPlayingBinding
import me.wcy.music.discover.DiscoverApi

import me.wcy.music.ext.registerReceiverCompat
import me.wcy.music.main.playlist.CurrentPlaylistFragment
import me.wcy.music.service.PlayMode
import me.wcy.music.service.PlayState
import me.wcy.music.service.PlayerController
import me.wcy.music.service.likesong.LikeSongProcessor
import me.wcy.music.storage.LrcCache
import me.wcy.music.storage.preference.ConfigPreferences
import me.wcy.music.utils.ImageUtils.transAlpha
import me.wcy.music.utils.TimeUtils
import me.wcy.music.utils.VipUtils
import me.wcy.music.utils.getDuration
import me.wcy.music.utils.getLargeCover
import me.wcy.music.utils.getSongId
import me.wcy.music.utils.isLocal
import me.wcy.music.widget.VipTrialDialog
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
 * Created by wangchenyan.top on 2023/9/4.
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

    // 简单的颜色缓存，避免重复计算
    private val colorCache = mutableMapOf<String, Int>()
    private val maxCacheSize = 20



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
        // 喜欢按钮 - 移除波纹效果，只保留点击事件
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

        // 下载按钮 - 移除波纹效果，只保留点击事件
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

        // 播放模式按钮 - 移除波纹效果，只保留点击事件
        viewBinding.controlLayout.ivMode.setOnClickListener {
            switchPlayMode()
        }

        // 播放按钮 - 移除波纹效果，只保留点击事件
        viewBinding.controlLayout.flPlay.setOnClickListener {
            playerController.playPause()
        }

        // 上一首按钮 - 移除波纹效果，只保留点击事件
        viewBinding.controlLayout.ivPrev.setOnClickListener {
            playerController.prev()
        }

        // 下一首按钮 - 移除波纹效果，只保留点击事件
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
                    playerController.seekTo(progress)
                    if (viewBinding.lrcView.hasLrc()) {
                        viewBinding.lrcView.updateTime(progress.toLong())
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

                viewBinding.controlLayout.sbProgress.max = song.mediaMetadata.getDuration().toInt()
                viewBinding.controlLayout.sbProgress.progress =
                    playerController.playProgress.value.toInt()
                viewBinding.controlLayout.sbProgress.secondaryProgress = 0

                // 设置VIP试听标记
                updateVipTrialMark(song)

                // 重置VIP对话框状态
                hasShownVipDialog = false
                lastProgress = 0
                viewBinding.controlLayout.tvCurrentTime.text =
                    TimeUtils.formatMs(playerController.playProgress.value)
                viewBinding.controlLayout.tvTotalTime.text =
                    TimeUtils.formatMs(song.mediaMetadata.getDuration())
                updateCover(song)
                updateLrc(song)
                viewBinding.albumCoverView.reset()
                updatePlayState(playerController.playState.value)
                updateOnlineActionsState(song)
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
                if (viewBinding.lrcView.hasLrc()) {
                    viewBinding.lrcView.updateTime(progress)
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
    }

    private fun updateCover(song: MediaItem) {
        setDefaultCover()
        ImageUtils.loadBitmap(song.getLargeCover()) {
            if (it.isSuccessWithData()) {
                val bitmap = it.getDataOrThrow()
                viewBinding.albumCoverView.setCoverBitmap(bitmap)
                Blurry.with(this).sampling(10).from(bitmap).into(viewBinding.ivPlayingBg)
                updateLrcMask()

                // 动态更新歌词高亮颜色
                updateLrcHighlightColor(bitmap, song.getLargeCover())
            }
        }
    }

    private fun setDefaultCover() {
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
            try {
                // 只在有歌词内容时才更新动态颜色，避免影响状态文本显示
                if (!viewBinding.lrcView.hasLrc()) {
                    // 没有歌词时，确保状态文本为白色
                    viewBinding.lrcView.setCurrentColor(android.graphics.Color.WHITE)
                    viewBinding.lrcView.setNormalColor(android.graphics.Color.WHITE)
                    return@launch
                }

                // 检查缓存
                val cacheKey = coverUrl.hashCode().toString()
                val cachedColor = colorCache[cacheKey]

                val highlightColor = if (cachedColor != null) {
                    cachedColor
                } else if (bitmap != null) {
                    val extractedColor = extractSmartColor(bitmap)
                    // 缓存结果
                    if (colorCache.size >= maxCacheSize) {
                        // 清理最旧的缓存项
                        val oldestKey = colorCache.keys.first()
                        colorCache.remove(oldestKey)
                    }
                    colorCache[cacheKey] = extractedColor
                    extractedColor
                } else {
                    android.graphics.Color.WHITE
                }

                // 只更新高亮颜色，保持默认歌词颜色为白色
                viewBinding.lrcView.setCurrentColor(highlightColor)
                // 确保默认文本颜色保持白色
                viewBinding.lrcView.setNormalColor(android.graphics.Color.WHITE)

                Log.d(TAG, "Updated lyric highlight color: ${Integer.toHexString(highlightColor)} (cached: ${cachedColor != null})")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update lyric highlight color", e)
                // 失败时使用默认白色
                viewBinding.lrcView.setCurrentColor(android.graphics.Color.WHITE)
                viewBinding.lrcView.setNormalColor(android.graphics.Color.WHITE)
            }
        }
    }

    /**
     * 智能颜色提取方法
     * 从bitmap多个区域采样并选择最佳颜色作为歌词高亮色
     */
    private fun extractSmartColor(bitmap: Bitmap): Int {
        return try {
            // 多点采样获取更准确的主色调
            val colors = mutableListOf<Int>()
            val width = bitmap.width
            val height = bitmap.height

            // 从9个关键点采样颜色
            val samplePoints = listOf(
                Pair(width / 4, height / 4),       // 左上
                Pair(width / 2, height / 4),       // 中上
                Pair(width * 3 / 4, height / 4),   // 右上
                Pair(width / 4, height / 2),       // 左中
                Pair(width / 2, height / 2),       // 中心
                Pair(width * 3 / 4, height / 2),   // 右中
                Pair(width / 4, height * 3 / 4),   // 左下
                Pair(width / 2, height * 3 / 4),   // 中下
                Pair(width * 3 / 4, height * 3 / 4) // 右下
            )

            samplePoints.forEach { (x, y) ->
                colors.add(bitmap.getPixel(x, y))
            }

            // 选择最鲜艳的颜色作为基础色
            val dominantColor = colors.maxByOrNull { color ->
                val red = android.graphics.Color.red(color)
                val green = android.graphics.Color.green(color)
                val blue = android.graphics.Color.blue(color)
                // 计算颜色饱和度
                val max = maxOf(red, green, blue)
                val min = minOf(red, green, blue)
                max - min
            } ?: android.graphics.Color.WHITE

            // 智能调整颜色以确保在深色背景上的可见性
            adjustColorForVisibility(dominantColor)
        } catch (e: Exception) {
            android.graphics.Color.WHITE
        }
    }

    /**
     * 调整颜色以确保在深色背景上的可见性和美观性
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
        updateLrcMask(viewBinding.ivLrcTopMask, true)
        updateLrcMask(viewBinding.ivLrcBottomMask, false)
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
    }

    private fun setLrcLabel(label: String) {
        viewBinding.lrcView.setLabel(label)
        // 确保歌词状态文本（如"歌词加载中…"、"暂无歌词"等）始终显示为白色
        if (label.isNotEmpty()) {
            viewBinding.lrcView.setCurrentColor(android.graphics.Color.WHITE)
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