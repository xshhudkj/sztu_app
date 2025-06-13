package me.ckn.music.service

import androidx.annotation.MainThread
import androidx.annotation.OptIn
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.async
import me.ckn.music.storage.db.MusicDatabase
import me.ckn.music.storage.preference.ConfigPreferences
import me.ckn.music.utils.toMediaItem
import me.ckn.music.utils.toSongEntity
import me.ckn.music.utils.getSongId
import me.ckn.music.utils.getFee
import me.ckn.music.utils.VipUtils
import top.wangchenyan.common.ext.toUnMutable
import me.ckn.music.net.datasource.MusicUrlCache
import me.ckn.music.utils.LogUtils
import me.ckn.music.service.SmartPreloadManager
import me.ckn.music.utils.SmartCacheManager
import me.ckn.music.utils.CacheStrategy
import me.ckn.music.utils.SmartUIUpdateManager
import me.ckn.music.utils.PerformanceLevel
import me.ckn.music.utils.FirstPlayOptimizer
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.ext.toast
import me.ckn.music.main.playlist.RecentPlayRepository
import me.ckn.music.common.bean.SongData
import me.ckn.music.common.bean.ArtistData
import me.ckn.music.common.bean.AlbumData

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2024/3/27
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：播放控制器实现
 * File Description: Player controller implementation
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class PlayerControllerImpl(
    private val player: Player,
    private val db: MusicDatabase,
    private val recentPlayRepository: RecentPlayRepository,
) : PlayerController, CoroutineScope by MainScope() {

    private val _playlist = MutableLiveData(emptyList<MediaItem>())
    override val playlist = _playlist.toUnMutable()

    private val _currentSong = MutableLiveData<MediaItem?>(null)
    override val currentSong = _currentSong.toUnMutable()

    private val _playState = MutableStateFlow<PlayState>(PlayState.Idle)
    override val playState = _playState.toUnMutable()

    private val _playProgress = MutableStateFlow<Long>(0)
    override val playProgress = _playProgress.toUnMutable()

    private val _bufferingPercent = MutableStateFlow(0)
    override val bufferingPercent = _bufferingPercent.toUnMutable()

    private val _playMode = MutableStateFlow(PlayMode.valueOf(ConfigPreferences.playMode))
    override val playMode: StateFlow<PlayMode> = _playMode

    private var audioSessionId = 0

    // 智能预加载管理器
    private val smartPreloadManager = SmartPreloadManager()
    
    // 智能缓存管理器
    private val smartCacheManager = SmartCacheManager(CommonApp.app.applicationContext)
    
    // 智能UI更新管理器
    private val smartUIUpdateManager = SmartUIUpdateManager(CommonApp.app.applicationContext)
    
    // 首次播放优化器 - 解决用户最关心的首次播放慢问题
    private val firstPlayOptimizer = FirstPlayOptimizer(CommonApp.app.applicationContext)

    // 持续缓存相关变量 - 优化更新频率
    private var lastProgressCheckTime = 0L
    private val progressCheckInterval = 500L // 减少到500ms，提高响应性
    private var lastCacheCheckTime = 0L
    private var isNextSongCaching = false // 是否正在缓存下一首歌曲

    // 智能UI更新器
    private val smartProgressUpdater = smartUIUpdateManager.createProgressUpdater()
    private val smartBufferUpdater = smartUIUpdateManager.createBufferUpdater()

    init {
        player.playWhenReady = false
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when (playbackState) {
                    Player.STATE_IDLE -> {
                        _playState.value = PlayState.Idle
                        _playProgress.value = 0
                        _bufferingPercent.value = 0
                    }

                    Player.STATE_BUFFERING -> {
                        _playState.value = PlayState.Preparing
                    }

                    Player.STATE_READY -> {
                        player.play()
                        _playState.value = PlayState.Playing
                    }

                    Player.STATE_ENDED -> {
                    }
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                if (player.playbackState == Player.STATE_READY) {
                    _playState.value = if (isPlaying) PlayState.Playing else PlayState.Pause
                }
            }

            override fun onLoadingChanged(isLoading: Boolean) {
                super.onLoadingChanged(isLoading)
                // 当加载状态改变时，更新缓存进度
                updateBufferingPercent()
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                mediaItem ?: return
                val playlist = _playlist.value ?: return
                val currentSong = playlist.find { it.mediaId == mediaItem.mediaId }
                _currentSong.value = currentSong

                // 当歌曲切换时（不是因为播放列表变化），记录到最近播放
                if (reason == Player.MEDIA_ITEM_TRANSITION_REASON_AUTO ||
                    reason == Player.MEDIA_ITEM_TRANSITION_REASON_SEEK) {
                    currentSong?.let { song ->
                        addToRecentPlay(song)
                    }
                }

                // 预加载下一首歌曲的URL
                preloadNextSongUrls()
            }

            @OptIn(UnstableApi::class)
            override fun onAudioSessionIdChanged(audioSessionId: Int) {
                super.onAudioSessionIdChanged(audioSessionId)
                this@PlayerControllerImpl.audioSessionId = audioSessionId
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                LogUtils.e(TAG, "Player error occurred: ${error.errorCodeName}, ${error.localizedMessage}", error)

                // 不要立即停止播放，尝试播放下一首歌曲
                if (player.mediaItemCount > 1) {
                    LogUtils.playback(TAG, "Attempting to play next song due to playback error")
                    next()
                } else {
                    // 只有在没有其他歌曲时才停止
                    stop()
                    toast("播放失败(${error.errorCodeName},${error.localizedMessage})")
                }
            }
        })
        setPlayMode(PlayMode.valueOf(ConfigPreferences.playMode))

        launch(Dispatchers.Main.immediate) {
            val playlist = withContext(Dispatchers.IO) {
                db.playlistDao()
                    .queryAll()
                    .onEach {
                        // 兼容老版本数据库
                        if (it.uri.isEmpty()) {
                            it.uri = it.path
                        }
                    }
                    .map { it.toMediaItem() }
            }
            if (playlist.isNotEmpty()) {
                _playlist.value = playlist
                player.setMediaItems(playlist)
                val currentSongId = ConfigPreferences.currentSongId
                if (currentSongId.isNotEmpty()) {
                    val currentSongIndex = playlist.indexOfFirst {
                        it.mediaId == currentSongId
                    }.coerceAtLeast(0)
                    _currentSong.value = playlist[currentSongIndex]
                    player.seekTo(currentSongIndex, 0)
                }
            }

            _currentSong.observeForever {
                ConfigPreferences.currentSongId = it?.mediaId ?: ""
            }
        }

        launch {
            while (true) {
                val currentTime = System.currentTimeMillis()
                
                if (player.isPlaying) {
                    // 智能进度更新：根据设备性能和变化幅度决定是否更新
                    val currentPosition = player.currentPosition
                    if (smartProgressUpdater.shouldUpdate(currentPosition, currentTime)) {
                        _playProgress.value = currentPosition
                        smartProgressUpdater.markUpdated(currentPosition, currentTime)
                    }

                    // 检查持续缓存状态
                    if (currentTime - lastProgressCheckTime >= progressCheckInterval) {
                        checkContinuousCache()

                        // 智能预加载检查
                        checkSmartPreload()

                        lastProgressCheckTime = currentTime
                    }
                }

                // 使用智能缓存管理器的动态检查间隔
                val dynamicCacheCheckInterval = smartCacheManager.getOptimalCacheCheckInterval()
                if (currentTime - lastCacheCheckTime >= dynamicCacheCheckInterval) {
                    updateBufferingPercent()
                    checkContinuousCache()
                    lastCacheCheckTime = currentTime
                }

                // 使用智能UI管理器的最优循环间隔
                delay(smartUIUpdateManager.getOptimalLoopInterval())
            }
        }
    }

    @MainThread
    override fun addAndPlay(song: MediaItem) {
        launch(Dispatchers.Main.immediate) {
            val newPlaylist = _playlist.value?.toMutableList() ?: mutableListOf()
            val index = newPlaylist.indexOfFirst { it.mediaId == song.mediaId }
            if (index >= 0) {
                newPlaylist[index] = song
                player.replaceMediaItem(index, song)
            } else {
                newPlaylist.add(song)
                player.addMediaItem(song)
            }
            withContext(Dispatchers.IO) {
                db.playlistDao().clear()
                db.playlistDao().insertAll(newPlaylist.map { it.toSongEntity() })
            }
            _playlist.value = newPlaylist
            play(song.mediaId)
        }
    }

    @MainThread
    override fun replaceAll(songList: List<MediaItem>, song: MediaItem) {
        launch(Dispatchers.Main.immediate) {
            withContext(Dispatchers.IO) {
                db.playlistDao().clear()
                db.playlistDao().insertAll(songList.map { it.toSongEntity() })
            }
            stop()
            player.setMediaItems(songList)
            _playlist.value = songList

            // 启动预测性预加载，为首次播放加速做准备
            firstPlayOptimizer.predictivePreload(songList)

            // 确保目标歌曲在播放列表中
            val targetSong = songList.find { it.mediaId == song.mediaId } ?: songList.firstOrNull()
            if (targetSong != null) {
                _currentSong.value = targetSong
                play(targetSong.mediaId)
                LogUtils.playback(TAG, "Successfully replaced playlist and started playing", targetSong.mediaMetadata.title.toString())
            } else {
                LogUtils.e(TAG, "Target song not found in playlist, using first song")
                if (songList.isNotEmpty()) {
                    _currentSong.value = songList.first()
                    play(songList.first().mediaId)
                }
            }
        }
    }

    @MainThread
    override fun appendToPlaylist(songList: List<MediaItem>) {
        launch(Dispatchers.Main.immediate) {
            val currentPlaylist = _playlist.value.orEmpty().toMutableList()
            val newSongs = songList.filter { newSong ->
                currentPlaylist.none { it.mediaId == newSong.mediaId }
            }
            if (newSongs.isNotEmpty()) {
                currentPlaylist.addAll(newSongs)
                withContext(Dispatchers.IO) {
                    db.playlistDao().insertAll(newSongs.map { it.toSongEntity() })
                }
                // 分批添加到ExoPlayer，避免一次性添加太多导致卡顿
                newSongs.chunked(10).forEach { batch ->
                    player.addMediaItems(batch)
                }
                _playlist.value = currentPlaylist
            }
        }
    }

    /**
     * 智能播放指定歌曲，如果歌曲不在当前播放列表中则等待
     * @param mediaId 歌曲ID
     * @param maxWaitTime 最大等待时间（毫秒）
     */
    @MainThread
    fun playWhenAvailable(mediaId: String, maxWaitTime: Long = 5000) {
        launch(Dispatchers.Main.immediate) {
            val startTime = System.currentTimeMillis()
            while (System.currentTimeMillis() - startTime < maxWaitTime) {
                val playlist = _playlist.value
                if (playlist?.any { it.mediaId == mediaId } == true) {
                    play(mediaId)
                    return@launch
                }
                delay(100) // 每100ms检查一次
            }
            // 超时后播放当前播放列表的第一首
            val playlist = _playlist.value
            if (!playlist.isNullOrEmpty()) {
                play(playlist[0].mediaId)
            }
        }
    }

    @MainThread
    override fun play(mediaId: String) {
        val playlist = _playlist.value
        if (playlist.isNullOrEmpty()) {
            return
        }
        val index = playlist.indexOfFirst { it.mediaId == mediaId }
        if (index < 0) {
            return
        }

        // 标记当前歌曲开始播放，清理预加载记录
        val songId = playlist[index].getSongId()
        val songFee = playlist[index].getFee()
        smartPreloadManager.markSongAsPlayed(songId)

        // 🚀 零延时播放优化：立即播放，后台优化URL
        launch(Dispatchers.Main.immediate) {
            try {
                val optimizeStartTime = System.currentTimeMillis()
                LogUtils.performance(TAG) { "开始零延时播放优化: songId=$songId" }

                // 立即设置播放状态和UI
                val mediaItem = playlist[index]
                _currentSong.value = mediaItem
                _playProgress.value = 0
                _bufferingPercent.value = 0

                // 记录到最近播放（用户主动点击播放）
                addToRecentPlay(mediaItem)

                // 极速播放优化：最小化状态切换操作
                val currentIndex = player.currentMediaItemIndex
                if (currentIndex != index) {
                    player.seekTo(index, 0)
                }

                // 智能准备播放器：只在必要时准备
                when (player.playbackState) {
                    Player.STATE_IDLE -> player.prepare()
                    Player.STATE_ENDED -> player.prepare()
                    // 其他状态直接播放，减少准备时间
                }

                // 立即开始播放，不等待URL优化
                player.play()

                // 后台异步优化URL（不阻塞播放）
                launch(Dispatchers.IO) {
                    try {
                        val optimizedUrl = firstPlayOptimizer.optimizeFirstPlay(songId, songFee)
                        LogUtils.performance(TAG) {
                            "后台URL优化完成: songId=$songId, URL=${if(!optimizedUrl.isNullOrEmpty()) "成功" else "使用默认"}"
                        }
                    } catch (e: Exception) {
                        LogUtils.w(TAG, "后台URL优化失败，继续使用当前播放: songId=$songId", e)
                    }
                }

                val totalOptimizeTime = System.currentTimeMillis() - optimizeStartTime
                LogUtils.performance(TAG) {
                    "零延时播放启动完成: songId=$songId, 启动用时=${totalOptimizeTime}ms"
                }

                // 重置缓存状态
                resetCacheState()

            } catch (e: Exception) {
                LogUtils.e(TAG, "零延时播放失败，回退到传统方式: songId=$songId", e)
                // 回退到传统播放方式
                fallbackPlaybackStart(playlist, index, songId)
            }
        }
    }

    @MainThread
    override fun delete(song: MediaItem) {
        launch(Dispatchers.Main.immediate) {
            val playlist = _playlist.value?.toMutableList() ?: mutableListOf()
            val index = playlist.indexOfFirst { it.mediaId == song.mediaId }
            if (index < 0) return@launch
            if (playlist.size == 1) {
                clearPlaylist()
            } else {
                playlist.removeAt(index)
                _playlist.value = playlist
                withContext(Dispatchers.IO) {
                    db.playlistDao().delete(song.toSongEntity())
                }
                player.removeMediaItem(index)
            }
        }
    }

    @MainThread
    override fun clearPlaylist() {
        launch(Dispatchers.Main.immediate) {
            withContext(Dispatchers.IO) {
                db.playlistDao().clear()
            }
            stop()
            player.clearMediaItems()
            _playlist.value = emptyList()
            _currentSong.value = null
        }
    }

    @MainThread
    override fun playPause() {
        if (player.mediaItemCount == 0) return

        // 简化状态管理：直接根据播放状态切换
        if (player.isPlaying) {
            player.pause()
            _playState.value = PlayState.Pause
        } else {
            // 智能播放：根据状态决定是否需要准备
            when (player.playbackState) {
                Player.STATE_IDLE, Player.STATE_ENDED -> {
                    player.prepare()
                }
                Player.STATE_BUFFERING -> {
                    // 缓冲中时暂停，避免重复操作
                    return
                }
            }
            player.play()
            _playState.value = PlayState.Playing
        }
    }

    @MainThread
    override fun next() {
        if (player.mediaItemCount == 0) return

        // 清理预加载记录
        val playlist = _playlist.value
        if (!playlist.isNullOrEmpty()) {
            val nextIndex = (player.currentMediaItemIndex + 1) % playlist.size
            val nextSong = playlist[nextIndex]
            val songId = nextSong.getSongId()
            smartPreloadManager.markSongAsPlayed(songId)
        }

        // 智能切换：只在必要时准备播放器
        player.seekToNextMediaItem()
        if (player.playbackState == Player.STATE_IDLE || player.playbackState == Player.STATE_ENDED) {
            player.prepare()
        }
        _playProgress.value = 0
        _bufferingPercent.value = 0

        // 重置缓存状态
        resetCacheState()
    }

    @MainThread
    override fun prev() {
        if (player.mediaItemCount == 0) return

        // 清理预加载记录
        val playlist = _playlist.value
        if (!playlist.isNullOrEmpty()) {
            val prevIndex = if (player.currentMediaItemIndex > 0) {
                player.currentMediaItemIndex - 1
            } else {
                playlist.size - 1
            }
            val prevSong = playlist[prevIndex]
            val songId = prevSong.getSongId()
            smartPreloadManager.markSongAsPlayed(songId)
        }

        // 智能切换：只在必要时准备播放器
        player.seekToPreviousMediaItem()
        if (player.playbackState == Player.STATE_IDLE || player.playbackState == Player.STATE_ENDED) {
            player.prepare()
        }
        _playProgress.value = 0
        _bufferingPercent.value = 0

        // 重置缓存状态
        resetCacheState()
    }

    @MainThread
    override fun seekTo(msec: Int) {
        if (player.playbackState == Player.STATE_READY) {
            val currentSong = _currentSong.value
            if (currentSong != null) {
                // 检查VIP试听限制
                val needTrial = VipUtils.needTrialLimit(currentSong)
                if (needTrial) {
                    val trialEndTime = VipUtils.getTrialEndTime(currentSong)
                    // 如果seek位置在试听范围内，直接seek，不重新加载
                    if (msec.toLong() <= trialEndTime) {
                        player.seekTo(msec.toLong())
                        return
                    } else {
                        // 超出试听范围，不允许seek
                        return
                    }
                }
            }
            // 非VIP歌曲或VIP用户，正常seek
            player.seekTo(msec.toLong())
        }
    }

    @MainThread
    override fun getAudioSessionId(): Int {
        return audioSessionId
    }

    @MainThread
    override fun setPlayMode(mode: PlayMode) {
        ConfigPreferences.playMode = mode.value
        _playMode.value = mode
        when (mode) {
            PlayMode.Loop -> {
                player.repeatMode = Player.REPEAT_MODE_ALL
                player.shuffleModeEnabled = false
            }

            PlayMode.Shuffle -> {
                player.repeatMode = Player.REPEAT_MODE_ALL
                player.shuffleModeEnabled = true
            }

            PlayMode.Single -> {
                player.repeatMode = Player.REPEAT_MODE_ONE
                player.shuffleModeEnabled = false
            }
        }
    }

    @MainThread
    override fun stop() {
        player.stop()
        _playState.value = PlayState.Idle
    }

    /**
     * 更新缓存进度百分比
     */
    private fun updateBufferingPercent() {
        if (player.duration > 0) {
            val bufferedPosition = player.bufferedPosition
            val duration = player.duration
            val percent = ((bufferedPosition * 100) / duration).toInt().coerceIn(0, 100)
            
            // 使用智能缓冲更新器，避免频繁的微小变化更新
            val currentTime = System.currentTimeMillis()
            if (smartBufferUpdater.shouldUpdate(percent, currentTime)) {
                _bufferingPercent.value = percent
                smartBufferUpdater.markUpdated(percent, currentTime)
            }

            // 检查缓存是否领先播放进度足够时间
            val currentPosition = player.currentPosition
            val cacheLeadTime = bufferedPosition - currentPosition

            LogUtils.cache(TAG) { "缓存状态 - 当前位置: ${currentPosition}ms, 缓存位置: ${bufferedPosition}ms, 领先时间: ${cacheLeadTime}ms, 歌曲总长: ${duration}ms" }
        }
    }

    /**
     * 检查持续缓存状态 - 智能优化版本
     */
    private fun checkContinuousCache() {
        if (player.duration <= 0) return

        val currentPosition = player.currentPosition
        val bufferedPosition = player.bufferedPosition
        val duration = player.duration
        val isPlaying = _playState.value == PlayState.Playing

        // 使用智能缓存管理器调整策略
        val strategy = smartCacheManager.adjustCacheStrategy(
            currentPosition = currentPosition,
            bufferedPosition = bufferedPosition,
            duration = duration,
            isPlaying = isPlaying
        )
        
        val dynamicCacheLeadTime = smartCacheManager.getOptimalCacheLeadTime()
        val cacheLeadTime = bufferedPosition - currentPosition

        LogUtils.cache(TAG) { "智能缓存检查 - 当前位置: ${currentPosition}ms, 缓存位置: ${bufferedPosition}ms, 领先时间: ${cacheLeadTime}ms, 策略: $strategy, 目标领先时间: ${dynamicCacheLeadTime}ms" }

        // 如果当前歌曲已完全缓存
        if (bufferedPosition >= duration) {
            LogUtils.cache(TAG) { "当前歌曲已完全缓存" }

            // 根据智能策略决定是否缓存下一首歌曲
            if (cacheLeadTime < dynamicCacheLeadTime && !isNextSongCaching && strategy != CacheStrategy.CONSERVATIVE) {
                val remainingCacheTime = dynamicCacheLeadTime - cacheLeadTime
                LogUtils.cache(TAG) { "缓存领先时间不足，需要缓存下一首歌曲 ${remainingCacheTime}ms" }
                startNextSongCache(remainingCacheTime)
            }
        } else {
            // 当前歌曲未完全缓存，继续缓存当前歌曲
            if (cacheLeadTime < dynamicCacheLeadTime) {
                LogUtils.cache(TAG) { "继续缓存当前歌曲，缓存领先时间不足: ${cacheLeadTime}ms < ${dynamicCacheLeadTime}ms" }
            }
        }
    }

    /**
     * 开始缓存下一首歌曲
     */
    private fun startNextSongCache(remainingCacheTime: Long) {
        // 单曲循环模式下不缓存下一首歌曲
        if (_playMode.value == PlayMode.Single) {
            LogUtils.cache(TAG) { "单曲循环模式，跳过下一首歌曲缓存" }
            return
        }

        val currentSong = _currentSong.value ?: return
        val playlist = _playlist.value ?: return
        val currentIndex = playlist.indexOfFirst { it.mediaId == currentSong.mediaId }

        if (currentIndex < 0) return

        val nextIndex = getNextSongIndex(currentIndex, playlist)
        if (nextIndex >= 0 && nextIndex < playlist.size) {
            val nextSong = playlist[nextIndex]

            // 检查是否为在线歌曲
            if (shouldCacheNextSong(nextSong)) {
                isNextSongCaching = true
                LogUtils.cache(TAG) { "开始缓存下一首歌曲: ${nextSong.mediaMetadata.title} (索引: $nextIndex, 需要缓存时间: ${remainingCacheTime}ms)" }

                // 这里可以实现具体的下一首歌曲缓存逻辑
                // 目前主要是记录状态和日志
                cacheNextSongData(nextSong, remainingCacheTime)
            }
        }
    }

    /**
     * 获取下一首歌曲的索引
     */
    private fun getNextSongIndex(currentIndex: Int, playlist: List<MediaItem>): Int {
        return when (_playMode.value) {
            PlayMode.Loop -> (currentIndex + 1) % playlist.size
            PlayMode.Shuffle -> {
                // 随机模式下，使用ExoPlayer的内部逻辑
                // 这里简化处理，实际应该使用ExoPlayer的shuffle逻辑
                if (currentIndex + 1 < playlist.size) currentIndex + 1 else -1
            }
            PlayMode.Single -> -1 // 单曲循环不需要下一首
        }
    }

    /**
     * 检查是否应该缓存下一首歌曲
     */
    private fun shouldCacheNextSong(mediaItem: MediaItem): Boolean {
        // 避免缓存本地歌曲（本地歌曲不需要网络缓存）
        val uri = mediaItem.localConfiguration?.uri
        if (uri?.scheme == "file" || uri?.scheme == "content") {
            LogUtils.cache(TAG) { "跳过本地歌曲缓存: ${mediaItem.mediaMetadata.title}" }
            return false
        }

        return true
    }

    /**
     * 缓存下一首歌曲的数据
     */
    private fun cacheNextSongData(mediaItem: MediaItem, cacheTime: Long) {
        // 这里可以实现具体的下一首歌曲缓存逻辑
        // 例如：预先获取音频URL、缓存指定时长的音频数据等
        // 由于ExoPlayer的缓存机制比较复杂，这里主要是记录缓存状态
        LogUtils.cache(TAG) { "缓存下一首歌曲数据: ${mediaItem.mediaMetadata.title} (缓存时长: ${cacheTime}ms)" }

        // 模拟缓存完成后重置状态
        launch {
            delay(1000) // 模拟缓存时间
            isNextSongCaching = false
            LogUtils.cache(TAG) { "下一首歌曲缓存完成: ${mediaItem.mediaMetadata.title}" }
        }
    }

    /**
     * 重置缓存状态（切换歌曲时调用）
     */
    private fun resetCacheState() {
        lastProgressCheckTime = 0L
        lastCacheCheckTime = 0L
        isNextSongCaching = false
        LogUtils.cache(TAG) { "重置缓存状态" }
    }

    /**
     * 预加载下一首歌曲的URL
     */
    private fun preloadNextSongUrls() {
        launch(Dispatchers.IO) {
            try {
                val currentSong = _currentSong.value ?: return@launch
                val playlist = _playlist.value ?: return@launch
                val currentIndex = playlist.indexOfFirst { it.mediaId == currentSong.mediaId }
                
                if (currentIndex < 0) return@launch
                
                // 优化：只预加载接下来的2首歌曲，减少网络负担
                val nextSongs = mutableListOf<Pair<Long, Int>>()
                for (i in 1..2) {
                    val nextIndex = when (_playMode.value) {
                        PlayMode.Loop -> (currentIndex + i) % playlist.size
                        PlayMode.Single -> currentIndex // 单曲循环不预加载
                        PlayMode.Shuffle -> {
                            // 随机模式下预加载较难，暂时跳过
                            -1
                        }
                    }
                    
                    if (nextIndex >= 0 && nextIndex < playlist.size) {
                        val nextSong = playlist[nextIndex]
                        // 只预加载在线歌曲
                        val uri = nextSong.localConfiguration?.uri
                        if (uri?.scheme != "file" && uri?.scheme != "content") {
                            nextSongs.add(nextSong.getSongId() to nextSong.getFee())
                        }
                    }
                }
                
                // 批量预加载URL
                if (nextSongs.isNotEmpty()) {
                    MusicUrlCache.preloadUrls(nextSongs)
                    LogUtils.network(TAG) { "预加载${nextSongs.size}首歌曲的URL" }
                }
            } catch (e: Exception) {
                LogUtils.e(TAG, "预加载URL失败", e)
            }
        }
    }

    /**
     * 传统播放启动方式（回退方案）
     */
    private fun fallbackPlaybackStart(playlist: List<MediaItem>, index: Int, songId: Long) {
        LogUtils.playback(TAG, "使用传统播放启动方式", "songId=$songId")

        // 极速播放优化：最小化状态切换操作
        val currentIndex = player.currentMediaItemIndex
        if (currentIndex != index) {
            // 直接切换到目标歌曲，不暂停当前播放
            player.seekTo(index, 0)
        }

        // 智能准备播放器：只在必要时准备
        when (player.playbackState) {
            Player.STATE_IDLE -> player.prepare()
            Player.STATE_ENDED -> player.prepare()
            // 其他状态直接播放，减少准备时间
        }

        // 立即开始播放，ExoPlayer会自动处理缓冲
        player.play()

        _currentSong.value = playlist[index]
        _playProgress.value = 0
        _bufferingPercent.value = 0

        // 重置缓存状态
        resetCacheState()
    }

    /**
     * 智能预加载检查
     * 根据当前播放进度智能决定是否预加载下一首歌曲
     */
    private fun checkSmartPreload() {
        val currentSong = _currentSong.value ?: return
        val playlist = _playlist.value ?: return

        if (player.duration <= 0) return

        val currentProgress = player.currentPosition.toFloat() / player.duration.toFloat()
        val currentIndex = playlist.indexOfFirst { it.mediaId == currentSong.mediaId }

        if (currentIndex >= 0) {
            // 获取下一首歌曲
            val nextSongs = mutableListOf<MediaItem>()
            for (i in 1..2) { // 最多预加载2首
                val nextIndex = when (_playMode.value) {
                    PlayMode.Loop -> (currentIndex + i) % playlist.size
                    PlayMode.Single -> -1 // 单曲循环不预加载
                    PlayMode.Shuffle -> -1 // 随机模式暂时不预加载
                }

                if (nextIndex >= 0 && nextIndex < playlist.size) {
                    val nextSong = playlist[nextIndex]
                    // 只预加载在线歌曲
                    val uri = nextSong.localConfiguration?.uri
                    if (uri?.scheme != "file" && uri?.scheme != "content") {
                        nextSongs.add(nextSong)
                    }
                }
            }

            // 使用智能预加载管理器
            smartPreloadManager.checkPreload(currentSong, nextSongs, currentProgress)
        }
    }

    /**
     * 清理资源，停止缓存监听
     */
    fun cleanup() {
        resetCacheState()
        // 清理URL缓存
        MusicUrlCache.cleanExpiredCache()
        // 清理智能预加载
        smartPreloadManager.clearAll()
        // 执行智能缓存清理
        smartCacheManager.performSmartCleanup()
    }
    
    /**
     * 获取缓存性能报告
     */
    fun getCachePerformanceReport() = smartCacheManager.getPerformanceReport()
    
    /**
     * 获取UI性能报告
     */
    fun getUIPerformanceReport() = smartUIUpdateManager.getPerformanceReport()
    
    /**
     * 设置UI可见状态（供Activity调用）
     */
    fun setUIVisible(visible: Boolean) = smartUIUpdateManager.setUIVisible(visible)
    
    /**
     * 设置用户交互状态（供Activity调用）
     */
    fun setUserInteracting(interacting: Boolean) = smartUIUpdateManager.setUserInteracting(interacting)

    // 记录最近添加的歌曲ID和时间，避免短时间内重复添加
    private var lastAddedSongId: Long = 0L
    private var lastAddedTime: Long = 0L
    private val addCooldownMs = 2000L // 2秒冷却时间

    /**
     * 添加歌曲到最近播放记录，带重复检查和即时响应
     */
    private fun addToRecentPlay(mediaItem: MediaItem) {
        launch(Dispatchers.IO) {
            try {
                val songId = mediaItem.getSongId()
                val currentTime = System.currentTimeMillis()

                // 检查是否是短时间内的重复添加
                if (songId == lastAddedSongId && (currentTime - lastAddedTime) < addCooldownMs) {
                    LogUtils.d(TAG, "跳过重复添加歌曲到最近播放: songId=$songId")
                    return@launch
                }

                // 将MediaItem转换为SongData
                val songData = mediaItemToSongData(mediaItem)

                // 立即添加到最近播放（响应式数据流会自动更新UI）
                recentPlayRepository.addToRecentPlay(songData)

                // 更新最近添加记录
                lastAddedSongId = songId
                lastAddedTime = currentTime

                LogUtils.d(TAG, "已添加歌曲到最近播放并触发UI更新: ${songData.name}")
            } catch (e: Exception) {
                LogUtils.e(TAG, "添加歌曲到最近播放失败", e)
            }
        }
    }

    /**
     * 将MediaItem转换为SongData，确保数据完整性
     */
    private fun mediaItemToSongData(mediaItem: MediaItem): SongData {
        val metadata = mediaItem.mediaMetadata
        return SongData(
            id = mediaItem.getSongId(),
            name = metadata.title?.toString() ?: "",
            ar = listOf(ArtistData(name = metadata.artist?.toString() ?: "")),
            al = AlbumData(
                name = metadata.albumTitle?.toString() ?: "",
                picUrl = metadata.artworkUri?.toString() ?: "",
                id = 0L // 保持默认值
            ),
            dt = metadata.durationMs ?: 0L,
            fee = mediaItem.getFee(),
            // 保留其他重要字段的默认值，确保数据结构完整
            pst = 0,
            t = 0,
            pop = 0,
            st = 0,
            rt = "",
            v = 0,
            cf = ""
        )
    }

    companion object {
        private const val TAG = "PlayerControllerImpl"
    }
}

