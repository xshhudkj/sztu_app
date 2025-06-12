package me.ckn.music.search.artist

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.ckn.music.common.SimpleMusicRefreshFragment
import me.ckn.music.common.bean.ArtistData
import me.ckn.music.consts.Consts
import me.ckn.music.search.SearchApi
import me.ckn.music.search.SearchViewModel
import me.wcy.radapter3.RAdapter
import top.wangchenyan.common.model.CommonResult
import top.wangchenyan.common.net.apiCall

/**
 * 搜索歌手Fragment
 *
 * Original: Created by wangchenyan.top on 2024/12/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：搜索歌手Fragment
 * File Description: Search Artist Fragment
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@AndroidEntryPoint
class SearchArtistFragment : SimpleMusicRefreshFragment<ArtistData>() {
    private val viewModel by activityViewModels<SearchViewModel>()
    private val itemBinder by lazy {
        SearchArtistItemBinder()
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
                    autoRefresh(true)
                }
            }
        }
    }

    override fun initAdapter(adapter: RAdapter<ArtistData>) {
        adapter.register(itemBinder)
    }

    override suspend fun getData(page: Int): CommonResult<List<ArtistData>> {
        val keywords = viewModel.keywords.value
        if (keywords.isEmpty()) {
            return CommonResult.success(emptyList())
        }
        val res = apiCall {
            SearchApi.get().search(100, keywords, Consts.PAGE_COUNT, (page - 1) * Consts.PAGE_COUNT)
        }
        return if (res.isSuccessWithData()) {
            CommonResult.success(res.getDataOrThrow().artists)
        } else {
            CommonResult.fail(res.code, res.msg)
        }
    }
}
