package me.ckn.music.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.ckn.music.discover.DiscoverApi
import me.ckn.music.service.PlayerController

/**
 * 分批播放列表加载器
 * 智能处理歌单的分批加载和播放
 * Created by wangchenyan.top on 2024/3/26.
 */
class BatchPlaylistLoader(
    private val playerController: PlayerController,
    private val scope: CoroutineScope
) {

    /**
     * 智能播放歌单中的指定歌曲
     * @param playlistId 歌单ID
     * @param songPosition 用户点击的歌曲位置
     * @param onPlayStarted 播放开始回调
     * @param onError 错误回调
     */
    fun playPlaylistSong(
        playlistId: Long,
        songPosition: Int,
        onPlayStarted: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        scope.launch {
            try {
                when {
                    songPosition < 3 -> {
                        // 前3首歌曲：快速加载前3首
                        loadFirstBatchAndPlay(playlistId, songPosition, onPlayStarted)
                    }
                    songPosition < 20 -> {
                        // 4-20首歌曲：加载前20首
                        loadTargetBatchAndPlay(playlistId, songPosition, 20, onPlayStarted)
                    }
                    else -> {
                        // 20首以后：按50首为单位加载到目标位置
                        val targetBatchSize = ((songPosition / 50) + 1) * 50
                        loadTargetBatchAndPlay(playlistId, songPosition, targetBatchSize, onPlayStarted)
                    }
                }
            } catch (e: Exception) {
                onError(e)
                // 失败时回退到完整加载
                fallbackToFullLoad(playlistId, songPosition, onPlayStarted, onError)
            }
        }
    }

    private suspend fun loadFirstBatchAndPlay(
        playlistId: Long,
        songPosition: Int,
        onPlayStarted: () -> Unit
    ) {
        val firstBatchData = DiscoverApi.getPlaylistSongListBatch(playlistId, limit = 3, offset = 0)
        if (firstBatchData.code == 200 && firstBatchData.songs.isNotEmpty()) {
            val firstBatchSongs = firstBatchData.songs.map { it.toMediaItem() }
            val targetSong = if (songPosition < firstBatchSongs.size) {
                firstBatchSongs[songPosition]
            } else {
                firstBatchSongs[0]
            }

            // 立即播放用户选择的歌曲
            playerController.replaceAll(firstBatchSongs, targetSong)
            onPlayStarted()

            // 后台异步加载剩余歌曲
            loadRemainingPlaylistSongs(playlistId, firstBatchSongs.size)
        }
    }

    private suspend fun loadTargetBatchAndPlay(
        playlistId: Long,
        songPosition: Int,
        batchSize: Int,
        onPlayStarted: () -> Unit
    ) {
        val batchData = DiscoverApi.getPlaylistSongListBatch(playlistId, limit = batchSize, offset = 0)
        if (batchData.code == 200 && batchData.songs.isNotEmpty()) {
            val songs = batchData.songs.map { it.toMediaItem() }
            val targetSong = if (songPosition < songs.size) {
                songs[songPosition]
            } else {
                songs[0]
            }

            // 立即播放用户选择的歌曲
            playerController.replaceAll(songs, targetSong)
            onPlayStarted()

            // 后台异步加载剩余歌曲
            loadRemainingPlaylistSongs(playlistId, songs.size)
        }
    }

    private suspend fun fallbackToFullLoad(
        playlistId: Long,
        songPosition: Int,
        onPlayStarted: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        try {
            val songListData = DiscoverApi.getFullPlaylistSongList(playlistId)
            if (songListData.code == 200 && songListData.songs.isNotEmpty()) {
                val songs = songListData.songs.map { it.toMediaItem() }
                playerController.replaceAll(songs, songs.getOrElse(songPosition) { songs[0] })
                onPlayStarted()
            }
        } catch (e: Exception) {
            onError(e)
        }
    }

    private suspend fun loadRemainingPlaylistSongs(playlistId: Long, loadedCount: Int) {
        try {
            val fullSongListData = DiscoverApi.getFullPlaylistSongList(playlistId)
            if (fullSongListData.code == 200 && fullSongListData.songs.size > loadedCount) {
                val remainingSongs = fullSongListData.songs.drop(loadedCount).map { it.toMediaItem() }
                if (remainingSongs.isNotEmpty()) {
                    // 分批添加剩余歌曲，避免一次性添加太多导致卡顿
                    val batchSize = 20
                    remainingSongs.chunked(batchSize).forEachIndexed { index, batch ->
                        // 延时添加，避免阻塞UI
                        delay(index * 200L)
                        playerController.appendToPlaylist(batch)
                    }
                }
            }
        } catch (e: Exception) {
            // 静默处理错误
        }
    }
}
