package me.ckn.music.service

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.media3.common.MediaItem
import kotlinx.coroutines.flow.StateFlow

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2024/3/26
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：播放控制器接口
 * File Description: Player controller interface
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
interface PlayerController {
    val playlist: LiveData<List<MediaItem>>
    val currentSong: LiveData<MediaItem?>
    val playState: StateFlow<PlayState>
    val playProgress: StateFlow<Long>
    val bufferingPercent: StateFlow<Int>
    val playMode: StateFlow<PlayMode>

    @MainThread
    fun addAndPlay(song: MediaItem)

    @MainThread
    fun replaceAll(songList: List<MediaItem>, song: MediaItem)

    @MainThread
    fun appendToPlaylist(songList: List<MediaItem>)

    @MainThread
    fun play(mediaId: String)

    @MainThread
    fun delete(song: MediaItem)

    @MainThread
    fun clearPlaylist()

    @MainThread
    fun playPause()

    @MainThread
    fun next()

    @MainThread
    fun prev()

    @MainThread
    fun seekTo(msec: Int)

    @MainThread
    fun getAudioSessionId(): Int

    @MainThread
    fun setPlayMode(mode: PlayMode)

    @MainThread
    fun stop()
}