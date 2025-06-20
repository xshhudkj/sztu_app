package me.ckn.music.discover.playlist.detail.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import me.ckn.music.common.bean.PlaylistData
import me.ckn.music.common.bean.SongData
import me.ckn.music.discover.DiscoverApi
import me.ckn.music.mine.MineApi
import me.ckn.music.service.likesong.LikeSongProcessor
import top.wangchenyan.common.ext.toUnMutable
import top.wangchenyan.common.model.CommonResult
import top.wangchenyan.common.net.apiCall
import top.wangchenyan.common.utils.ServerTime
import javax.inject.Inject

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/22
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：歌单详情ViewModel
 * File Description: Playlist detail ViewModel
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@HiltViewModel
class PlaylistViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var likeSongProcessor: LikeSongProcessor

    private val _playlistData = MutableStateFlow<PlaylistData?>(null)
    val playlistData = _playlistData.toUnMutable()

    private val _songList = MutableStateFlow<List<SongData>>(emptyList())
    val songList = _songList.toUnMutable()

    private var playlistId = 0L
    private var realtimeData = false
    private var isLike = false

    fun init(playlistId: Long, realtimeData: Boolean, isLike: Boolean) {
        this.playlistId = playlistId
        this.realtimeData = realtimeData
        this.isLike = isLike
    }

    suspend fun loadData(): CommonResult<Unit> {
        val detailRes = kotlin.runCatching {
            DiscoverApi.get().getPlaylistDetail(playlistId)
        }
        val songListRes = kotlin.runCatching {
            val timestamp = if (realtimeData) ServerTime.currentTimeMillis() else null
            DiscoverApi.getFullPlaylistSongList(playlistId, timestamp = timestamp)
        }
        return if (detailRes.isSuccess.not() || detailRes.getOrThrow().code != 200) {
            CommonResult.fail(msg = detailRes.exceptionOrNull()?.message)
        } else if (songListRes.isSuccess.not() || songListRes.getOrThrow().code != 200) {
            CommonResult.fail(msg = songListRes.exceptionOrNull()?.message)
        } else {
            _playlistData.value = detailRes.getOrThrow().playlist
            _songList.value = songListRes.getOrThrow().songs
            CommonResult.success(Unit)
        }
    }

    suspend fun collect(): CommonResult<Unit> {
        val playlistData = _playlistData.value ?: return CommonResult.fail()
        if (playlistData.subscribed) {
            val res = apiCall {
                MineApi.get().collectPlaylist(playlistData.id, 2)
            }
            return if (res.isSuccess()) {
                _playlistData.value = playlistData.copy(subscribed = false)
                CommonResult.success(Unit)
            } else {
                CommonResult.fail(res.code, res.msg)
            }
        } else {
            val res = apiCall {
                MineApi.get().collectPlaylist(playlistData.id, 1)
            }
            return if (res.isSuccess()) {
                _playlistData.value = playlistData.copy(subscribed = true)
                CommonResult.success(Unit)
            } else {
                CommonResult.fail(res.code, res.msg)
            }
        }
    }

    fun removeSong(songData: SongData) {
        val songList = _songList.value.toMutableList()
        songList.remove(songData)
        _songList.value = songList
        if (isLike) {
            likeSongProcessor.updateLikeSongList()
        }
    }
}