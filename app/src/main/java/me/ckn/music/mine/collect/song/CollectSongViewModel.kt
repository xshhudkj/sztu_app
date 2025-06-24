package me.ckn.music.mine.collect.song

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import me.ckn.music.account.service.UserService
import me.ckn.music.common.bean.PlaylistData
import me.ckn.music.mine.MineApi
import top.wangchenyan.common.model.CommonResult
import javax.inject.Inject

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2024/3/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：收藏歌曲ViewModel
 * File Description: Collect song ViewModel
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@HiltViewModel
class CollectSongViewModel @Inject constructor() : ViewModel() {
    private val _myPlaylists = MutableStateFlow<List<PlaylistData>>(emptyList())
    val myPlaylists = _myPlaylists

    var songId: Long = 0

    @Inject
    lateinit var userService: UserService

    suspend fun getMyPlayList(): CommonResult<List<PlaylistData>> {
        val uid = userService.profile.value?.userId ?: 0
        val res = kotlin.runCatching {
            MineApi.get().getUserPlaylist(uid)
        }
        val playlistData = res.getOrNull()
        return if (playlistData?.code == 200) {
            val list = playlistData.playlists.filter { it.userId == uid }
            _myPlaylists.value = list
            CommonResult.success(list)
        } else {
            CommonResult.fail(playlistData?.code ?: -1)
        }
    }

    suspend fun collectSong(pid: Long): CommonResult<Unit> {
        val res = kotlin.runCatching {
            MineApi.get().collectSong(pid, songId.toString())
        }
        return if (res.isSuccess) {
            val body = res.getOrThrow().body
            if (body.code == 200) {
                CommonResult.success(Unit)
            } else {
                CommonResult.fail(body.code, body.message)
            }
        } else {
            CommonResult.fail(msg = res.exceptionOrNull()?.message)
        }
    }
}