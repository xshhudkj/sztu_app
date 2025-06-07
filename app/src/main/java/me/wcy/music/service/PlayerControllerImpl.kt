package me.wcy.music.service

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
import me.wcy.music.storage.db.MusicDatabase
import me.wcy.music.storage.preference.ConfigPreferences
import me.wcy.music.utils.toMediaItem
import me.wcy.music.utils.toSongEntity
import me.wcy.music.utils.getSongId
import me.wcy.music.utils.getFee
import me.wcy.music.utils.VipUtils
import top.wangchenyan.common.ext.toUnMutable
import android.util.Log
import me.wcy.music.net.datasource.MusicUrlCache
import top.wangchenyan.common.ext.toast

/**
 * Created by wangchenyan.top on 2024/3/27.
 */
class PlayerControllerImpl(
    private val player: Player,
    private val db: MusicDatabase,
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

    // 持续缓存相关变量
    private var lastProgressCheckTime = 0L
    private val progressCheckInterval = 1000L // 1秒检查一次进度，降低CPU负担
    private var lastCacheCheckTime = 0L
    private val cacheCheckInterval = 3000L // 3秒检查一次缓存状态，减少频繁检查
    private val targetCacheLeadTime = 15_000L // 目标缓存领先时间：15秒，优化内存占用
    private var isNextSongCaching = false // 是否正在缓存下一首歌曲

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
                _currentSong.value = playlist.find { it.mediaId == mediaItem.mediaId }
                
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
                Log.e(TAG, "Player error occurred: ${error.errorCodeName}, ${error.localizedMessage}", error)

                // 不要立即停止播放，尝试播放下一首歌曲
                if (player.mediaItemCount > 1) {
                    Log.d(TAG, "Attempting to play next song due to playback error")
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
                    _playProgress.value = player.currentPosition

                    // 检查持续缓存状态
                    if (currentTime - lastProgressCheckTime >= progressCheckInterval) {
                        checkContinuousCache()
                        lastProgressCheckTime = currentTime
                    }
                }

                // 定期更新缓存进度
                if (currentTime - lastCacheCheckTime >= cacheCheckInterval) {
                    updateBufferingPercent()
                    lastCacheCheckTime = currentTime
                }

                delay(1000) // 1秒检查一次，平衡响应性与性能
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

            // 确保目标歌曲在播放列表中
            val targetSong = songList.find { it.mediaId == song.mediaId } ?: songList.firstOrNull()
            if (targetSong != null) {
                _currentSong.value = targetSong
                play(targetSong.mediaId)
                Log.d(TAG, "Successfully replaced playlist and started playing: ${targetSong.mediaMetadata.title}")
            } else {
                Log.e(TAG, "Target song not found in playlist, using first song")
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

        // 优化播放逻辑，减少不必要的stop操作
        val currentIndex = player.currentMediaItemIndex
        if (currentIndex != index) {
            // 只有在切换歌曲时才停止当前播放
            if (player.isPlaying) {
                player.pause()
            }
            player.seekTo(index, 0)
        }

        // 异步准备播放器，避免阻塞主线程
        if (player.playbackState == Player.STATE_IDLE) {
            player.prepare()
        }

        // 立即开始播放，不等待缓存完成
        player.play()

        _currentSong.value = playlist[index]
        _playProgress.value = 0
        _bufferingPercent.value = 0

        // 重置缓存状态
        resetCacheState()
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
        when (player.playbackState) {
            Player.STATE_IDLE -> {
                player.prepare()
            }

            Player.STATE_BUFFERING -> {
                stop()
            }

            Player.STATE_READY -> {
                if (player.isPlaying) {
                    player.pause()
                    _playState.value = PlayState.Pause
                } else {
                    player.play()
                    _playState.value = PlayState.Playing
                }
            }

            Player.STATE_ENDED -> {
                player.seekToNextMediaItem()
                player.prepare()
            }
        }
    }

    @MainThread
    override fun next() {
        if (player.mediaItemCount == 0) return
        player.seekToNextMediaItem()
        player.prepare()
        _playProgress.value = 0
        _bufferingPercent.value = 0

        // 重置缓存状态
        resetCacheState()
    }

    @MainThread
    override fun prev() {
        if (player.mediaItemCount == 0) return
        player.seekToPreviousMediaItem()
        player.prepare()
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
            _bufferingPercent.value = percent

            // 检查缓存是否领先播放进度足够时间
            val currentPosition = player.currentPosition
            val cacheLeadTime = bufferedPosition - currentPosition

            Log.d(TAG, "缓存状态 - 当前位置: ${currentPosition}ms, 缓存位置: ${bufferedPosition}ms, 领先时间: ${cacheLeadTime}ms, 歌曲总长: ${duration}ms")
        }
    }

    /**
     * 检查持续缓存状态
     */
    private fun checkContinuousCache() {
        if (player.duration <= 0) return

        val currentPosition = player.currentPosition
        val bufferedPosition = player.bufferedPosition
        val duration = player.duration
        val cacheLeadTime = bufferedPosition - currentPosition

        Log.d(TAG, "持续缓存检查 - 当前位置: ${currentPosition}ms, 缓存位置: ${bufferedPosition}ms, 领先时间: ${cacheLeadTime}ms")

        // 如果当前歌曲已完全缓存
        if (bufferedPosition >= duration) {
            Log.d(TAG, "当前歌曲已完全缓存")

            // 如果缓存领先时间仍不足15秒，开始缓存下一首歌曲
            if (cacheLeadTime < targetCacheLeadTime && !isNextSongCaching) {
                val remainingCacheTime = targetCacheLeadTime - cacheLeadTime
                Log.d(TAG, "缓存领先时间不足，需要缓存下一首歌曲 ${remainingCacheTime}ms")
                startNextSongCache(remainingCacheTime)
            }
        } else {
            // 当前歌曲未完全缓存，继续缓存当前歌曲
            if (cacheLeadTime < targetCacheLeadTime) {
                Log.d(TAG, "继续缓存当前歌曲，缓存领先时间不足: ${cacheLeadTime}ms < ${targetCacheLeadTime}ms")
            }
        }
    }

    /**
     * 开始缓存下一首歌曲
     */
    private fun startNextSongCache(remainingCacheTime: Long) {
        // 单曲循环模式下不缓存下一首歌曲
        if (_playMode.value == PlayMode.Single) {
            Log.d(TAG, "单曲循环模式，跳过下一首歌曲缓存")
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
                Log.d(TAG, "开始缓存下一首歌曲: ${nextSong.mediaMetadata.title} (索引: $nextIndex, 需要缓存时间: ${remainingCacheTime}ms)")

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
            Log.d(TAG, "跳过本地歌曲缓存: ${mediaItem.mediaMetadata.title}")
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
        Log.d(TAG, "缓存下一首歌曲数据: ${mediaItem.mediaMetadata.title} (缓存时长: ${cacheTime}ms)")

        // 模拟缓存完成后重置状态
        launch {
            delay(1000) // 模拟缓存时间
            isNextSongCaching = false
            Log.d(TAG, "下一首歌曲缓存完成: ${mediaItem.mediaMetadata.title}")
        }
    }

    /**
     * 重置缓存状态（切换歌曲时调用）
     */
    private fun resetCacheState() {
        lastProgressCheckTime = 0L
        lastCacheCheckTime = 0L
        isNextSongCaching = false
        Log.d(TAG, "重置缓存状态")
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
                
                // 获取接下来的3首歌曲
                val nextSongs = mutableListOf<Pair<Long, Int>>()
                for (i in 1..3) {
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
                    Log.d(TAG, "预加载${nextSongs.size}首歌曲的URL")
                }
            } catch (e: Exception) {
                Log.e(TAG, "预加载URL失败", e)
            }
        }
    }

    /**
     * 清理资源，停止缓存监听
     */
    fun cleanup() {
        resetCacheState()
        // 清理URL缓存
        MusicUrlCache.cleanExpiredCache()
    }

    companion object {
        private const val TAG = "PlayerControllerImpl"
    }
}

