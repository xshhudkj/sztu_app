package me.wcy.music.search.user

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.wcy.music.common.SimpleMusicRefreshFragment
import me.wcy.music.consts.Consts
import me.wcy.music.search.SearchApi
import me.wcy.music.search.SearchViewModel
import me.wcy.music.search.bean.UserData
import me.wcy.radapter3.RAdapter
import top.wangchenyan.common.model.CommonResult
import top.wangchenyan.common.net.apiCall

/**
 * 搜索用户Fragment
 * Created by wangchenyan.top on 2024/12/20.
 */
@AndroidEntryPoint
class SearchUserFragment : SimpleMusicRefreshFragment<UserData>() {
    private val viewModel by activityViewModels<SearchViewModel>()
    private val itemBinder by lazy {
        SearchUserItemBinder(lifecycleScope)
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

    override fun initAdapter(adapter: RAdapter<UserData>) {
        adapter.register(itemBinder)
    }

    override suspend fun getData(page: Int): CommonResult<List<UserData>> {
        val keywords = viewModel.keywords.value
        if (keywords.isEmpty()) {
            return CommonResult.success(emptyList())
        }
        val res = apiCall {
            SearchApi.get().search(1002, keywords, Consts.PAGE_COUNT, (page - 1) * Consts.PAGE_COUNT)
        }
        return if (res.isSuccessWithData()) {
            CommonResult.success(res.getDataOrThrow().userprofiles)
        } else {
            CommonResult.fail(res.code, res.msg)
        }
    }
}
