package me.ckn.music.discover.ranking.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.ckn.music.discover.DiscoverApi
import top.wangchenyan.common.ext.toUnMutable
import top.wangchenyan.common.model.CommonResult

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/10/25
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：排行榜ViewModel
 * File Description: Ranking ViewModel
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class RankingViewModel : ViewModel() {
    private val _rankingList = MutableLiveData<List<Any>>()
    val rankingList = _rankingList.toUnMutable()

    suspend fun loadData(): CommonResult<Unit> {
        val rankingListRes = kotlin.runCatching {
            DiscoverApi.get().getRankingList()
        }
        if (rankingListRes.getOrNull()?.code == 200) {
            val rankingList = rankingListRes.getOrThrow().playlists
            val officialList = rankingList.filter { it.toplistType.isNotEmpty() }
            val selectedList = rankingList.filter { it.toplistType.isEmpty() }
            val finalList =
                listOf(TitleData("官方榜")) + officialList + listOf(TitleData("精选榜")) + selectedList
            _rankingList.value = finalList
            viewModelScope.launch {
                officialList.forEach {
                    val d = async {
                        val songListRes = kotlin.runCatching {
                            DiscoverApi.get().getPlaylistSongList(it.id, limit = 3)
                        }
                        if (songListRes.getOrNull()?.code == 200) {
                            it.songList = songListRes.getOrThrow().songs
                            _rankingList.value = finalList
                        }
                    }
                }
            }
            return CommonResult.success(Unit)
        } else {
            return CommonResult.fail(msg = rankingListRes.exceptionOrNull()?.message)
        }
    }

    data class TitleData(val title: CharSequence)
}