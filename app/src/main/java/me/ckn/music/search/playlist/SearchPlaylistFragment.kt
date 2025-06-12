package me.ckn.music.search.playlist

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import top.wangchenyan.common.model.CommonResult
import top.wangchenyan.common.net.apiCall
import me.ckn.music.common.SimpleMusicRefreshFragment
import me.ckn.music.common.bean.PlaylistData
import me.ckn.music.consts.Consts
import me.ckn.music.consts.RoutePath
import me.ckn.music.search.SearchApi
import me.ckn.music.search.SearchViewModel
import me.wcy.radapter3.RAdapter
import me.wcy.router.CRouter

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：搜索歌单Fragment
 * File Description: Search Playlist Fragment
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@AndroidEntryPoint
class SearchPlaylistFragment : SimpleMusicRefreshFragment<PlaylistData>() {
    private val viewModel by activityViewModels<SearchViewModel>()
    private val itemBinder by lazy {
        SearchPlaylistItemBinder { item ->
            CRouter.with(requireActivity())
                .url(RoutePath.PLAYLIST_DETAIL)
                .extra("id", item.id)
                .start()
        }.apply {
            keywords = viewModel.keywords.value
        }
    }

    override fun isShowTitle(): Boolean {
        return false
    }

    override fun isRefreshEnabled(): Boolean {
        return false
    }

    override fun onLazyCreate() {
        super.onLazyCreate()
        lifecycleScope.launch {
            viewModel.keywords.collectLatest {
                if (it.isNotEmpty()) {
                    showLoadSirLoading()
                    itemBinder.keywords = it
                    autoRefresh(true)
                }
            }
        }
    }

    override fun initAdapter(adapter: RAdapter<PlaylistData>) {
        adapter.register(itemBinder)
    }

    override suspend fun getData(page: Int): CommonResult<List<PlaylistData>> {
        val keywords = viewModel.keywords.value
        if (keywords.isEmpty()) {
            return CommonResult.success(emptyList())
        }
        val res = apiCall {
            SearchApi.get()
                .search(1000, keywords, Consts.PAGE_COUNT, (page - 1) * Consts.PAGE_COUNT)
        }
        return if (res.isSuccessWithData()) {
            CommonResult.success(res.getDataOrThrow().playlists)
        } else {
            CommonResult.fail(res.code, res.msg)
        }
    }
}