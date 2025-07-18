package me.ckn.music.discover.playlist.square.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import me.ckn.music.discover.DiscoverApi
import top.wangchenyan.common.ext.toUnMutable
import top.wangchenyan.common.model.CommonResult

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/26
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：歌单广场ViewModel
 * File Description: Playlist square ViewModel
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class PlaylistSquareViewModel : ViewModel() {
    private val _tagList = MutableStateFlow<List<String>>(emptyList())
    val tagList = _tagList.toUnMutable()

    suspend fun loadTagList(): CommonResult<Unit> {
        val res = kotlin.runCatching {
            DiscoverApi.get().getPlaylistTagList()
        }
        return if (res.getOrNull()?.code == 200) {
            val list = res.getOrThrow().tags.map { it.name }.toMutableList()
            list.add(0, "全部")
            _tagList.value = list
            CommonResult.success(Unit)
        } else {
            CommonResult.fail(msg = res.exceptionOrNull()?.message)
        }
    }
}