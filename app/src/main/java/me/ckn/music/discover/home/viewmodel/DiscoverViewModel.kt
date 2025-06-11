/**
 * WhisperPlay Music Player
 *
 * 文件描述：发现页面的视图模型，负责获取和管理发现页的各类数据。
 * File Description: ViewModel for the Discover page, responsible for fetching and managing various data for the Discover page.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.discover.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.ckn.music.account.service.UserService
import me.ckn.music.common.bean.PlaylistData
import me.ckn.music.discover.DiscoverApi
import me.ckn.music.discover.banner.BannerData
import me.ckn.music.net.NetCache
import me.ckn.music.storage.preference.ConfigPreferences
import top.wangchenyan.common.ext.toUnMutable
import javax.inject.Inject

/**
 * 发现页面视图模型
 * Discover Page ViewModel
 *
 * 主要功能：
 * Main Functions:
 * - 获取轮播图数据 / Fetches banner data.
 * - 获取推荐歌单数据 / Fetches recommended playlist data.
 * - 获取排行榜数据 / Fetches ranking list data.
 * - 管理数据的缓存与更新 / Manages data caching and updates.
 *
 * @author ckn
 */
@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val userService: UserService
) : ViewModel() {
    private val _bannerList = MutableStateFlow<List<BannerData>>(emptyList())
    val bannerList = _bannerList.toUnMutable()

    private val _recommendPlaylist = MutableStateFlow<List<PlaylistData>>(emptyList())
    val recommendPlaylist = _recommendPlaylist.toUnMutable()

    private val _rankingList = MutableLiveData<List<PlaylistData>>(emptyList())
    val rankingList = _rankingList.toUnMutable()

    init {
        loadCache()
        viewModelScope.launch {
            userService.profile.collectLatest { profile ->
                if (profile != null && ConfigPreferences.apiDomain.isNotEmpty()) {
                    loadRecommendPlaylist()
                }
            }
        }
        loadBanner()
        loadRankingList()
    }

    /**
     * 从缓存加载数据
     * Loads data from the cache.
     */
    private fun loadCache() {
        viewModelScope.launch {
            val list = NetCache.globalCache.getJsonArray(CACHE_KEY_BANNER, BannerData::class.java)
                ?: return@launch
            _bannerList.value = list
        }
        if (userService.isLogin()) {
            viewModelScope.launch {
                val list = NetCache.userCache.getJsonArray(
                    CACHE_KEY_REC_PLAYLIST,
                    PlaylistData::class.java
                ) ?: return@launch
                _recommendPlaylist.value = list
            }
        }
        viewModelScope.launch {
            val list =
                NetCache.globalCache.getJsonArray(CACHE_KEY_RANKING_LIST, PlaylistData::class.java)
                    ?: return@launch
            _rankingList.postValue(list)
        }
    }

    /**
     * 加载轮播图数据
     * Loads banner data.
     */
    private fun loadBanner() {
        viewModelScope.launch {
            kotlin.runCatching {
                DiscoverApi.get().getBannerList()
            }.onSuccess {
                _bannerList.value = it.banners
                NetCache.globalCache.putJson(CACHE_KEY_BANNER, it.banners)
            }.onFailure {
            }
        }
    }

    /**
     * 加载推荐歌单数据
     * Loads recommended playlist data.
     */
    private fun loadRecommendPlaylist() {
        viewModelScope.launch {
            kotlin.runCatching {
                DiscoverApi.get().getRecommendPlaylists()
            }.onSuccess {
                _recommendPlaylist.value = it.playlists
                NetCache.userCache.putJson(CACHE_KEY_REC_PLAYLIST, it.playlists)
            }.onFailure {
            }
        }
    }

    /**
     * 加载排行榜数据
     * Loads ranking list data.
     */
    private fun loadRankingList() {
        viewModelScope.launch {
            kotlin.runCatching {
                DiscoverApi.get().getRankingList()
            }.onSuccess {
                val rankingList = it.playlists.take(5)
                val deferredList = mutableListOf<Deferred<*>>()
                rankingList.forEach {
                    val d = async {
                        val songListRes = kotlin.runCatching {
                            DiscoverApi.get().getPlaylistSongList(it.id, limit = 3)
                        }
                        if (songListRes.getOrNull()?.code == 200) {
                            it.songList = songListRes.getOrThrow().songs
                        }
                    }
                    deferredList.add(d)
                }
                deferredList.forEach { d ->
                    d.await()
                }
                _rankingList.postValue(rankingList)
                NetCache.globalCache.putJson(CACHE_KEY_RANKING_LIST, rankingList)
            }.onFailure {
            }
        }
    }

    companion object {
        const val CACHE_KEY_BANNER = "discover_banner"
        const val CACHE_KEY_REC_PLAYLIST = "discover_recommend_playlist"
        const val CACHE_KEY_RANKING_LIST = "discover_ranking_list"
    }
}