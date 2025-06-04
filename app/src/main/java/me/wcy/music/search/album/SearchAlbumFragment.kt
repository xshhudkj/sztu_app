package me.wcy.music.search.album

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.wcy.music.common.SimpleMusicRefreshFragment
import me.wcy.music.common.bean.AlbumData
import me.wcy.music.consts.Consts
import me.wcy.music.search.SearchApi
import me.wcy.music.search.SearchViewModel
import me.wcy.radapter3.RAdapter
import top.wangchenyan.common.model.CommonResult
import top.wangchenyan.common.net.apiCall

/**
 * 搜索专辑Fragment
 * Created by wangchenyan.top on 2024/12/20.
 */
@AndroidEntryPoint
class SearchAlbumFragment : SimpleMusicRefreshFragment<AlbumData>() {
    private val viewModel by activityViewModels<SearchViewModel>()
    private val itemBinder by lazy {
        SearchAlbumItemBinder()
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

    override fun initAdapter(adapter: RAdapter<AlbumData>) {
        adapter.register(itemBinder)
    }

    override suspend fun getData(page: Int): CommonResult<List<AlbumData>> {
        val keywords = viewModel.keywords.value
        if (keywords.isEmpty()) {
            return CommonResult.success(emptyList())
        }
        val res = apiCall {
            SearchApi.get().search(10, keywords, Consts.PAGE_COUNT, (page - 1) * Consts.PAGE_COUNT)
        }
        return if (res.isSuccessWithData()) {
            CommonResult.success(res.getDataOrThrow().albums)
        } else {
            CommonResult.fail(res.code, res.msg)
        }
    }
}
