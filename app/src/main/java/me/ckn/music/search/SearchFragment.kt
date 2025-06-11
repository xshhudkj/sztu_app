package me.ckn.music.search

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.KeyboardUtils
import com.google.android.material.tabs.TabLayout
import com.blankj.utilcode.util.SizeUtils
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.ckn.music.R
import me.ckn.music.common.BaseMusicFragment
import me.ckn.music.consts.RoutePath
import me.ckn.music.databinding.FragmentSearchBinding
import me.ckn.music.databinding.ItemSearchHistoryBinding
import me.ckn.music.databinding.TitleSearchBinding
import me.ckn.music.discover.banner.BannerData
import me.ckn.music.discover.DiscoverApi
import me.ckn.music.search.album.SearchAlbumFragment
import me.ckn.music.search.artist.SearchArtistFragment
import me.ckn.music.search.bean.HotSearchDetailData
import me.ckn.music.search.bean.SearchSuggestItem
import me.ckn.music.search.playlist.SearchPlaylistFragment
import me.ckn.music.search.song.SearchSongFragment
import me.ckn.music.search.user.SearchUserFragment
import me.ckn.music.service.PlayerController
import me.ckn.music.utils.ImageUtils.loadCover
import me.ckn.music.utils.toMediaItem
import me.wcy.router.CRouter
import me.wcy.router.annotation.Route
import top.wangchenyan.common.ext.getColor
import top.wangchenyan.common.ext.load
import top.wangchenyan.common.ext.viewBindings
import top.wangchenyan.common.utils.LaunchUtils
import top.wangchenyan.common.widget.pager.TabLayoutPager
import javax.inject.Inject

/**
 * Created by wangchenyan.top on 2023/9/20.
 */
@Route(RoutePath.SEARCH)
@AndroidEntryPoint
class SearchFragment : BaseMusicFragment() {
    private val viewBinding by viewBindings<FragmentSearchBinding>()
    private val titleBinding by lazy {
        TitleSearchBinding.bind(getTitleLayout()!!.getContentView()!!)
    }
    private val viewModel by activityViewModels<SearchViewModel>()
    private val menuSearch by lazy {
        getTitleLayout()?.addTextMenu("搜索", false)!!
    }

    @Inject
    lateinit var playerController: PlayerController

    private var searchSuggestJob: Job? = null

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun onLazyCreate() {
        super.onLazyCreate()

        configWindowInsets {
            fillIme = true
            navBarColor = getColor(R.color.play_bar_bg)
        }

        initTitle()
        initTab()
        initHistory()
        initHotSearch()
        initSearchBanner()
        initBackPressedHandler()

        lifecycleScope.launch {
            viewModel.showResult.collectLatest { showResult ->
                updateViewVisibility(showResult)
            }
        }

        lifecycleScope.launch {
            delay(200)
            KeyboardUtils.showSoftInput(titleBinding.etSearch)
        }
    }

    private fun initTitle() {
        titleBinding.etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                menuSearch.performClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        // 添加文本变化监听器，实现搜索建议
        titleBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val keywords = s?.toString()?.trim() ?: ""
                handleSearchInput(keywords)
            }
        })

        // 添加焦点变化监听器，支持搜索结果后重新点击输入框
        titleBinding.etSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // 如果当前在搜索结果页面，点击输入框时切换到搜索页面
                if (viewModel.showResult.value) {
                    viewModel.showHistory()
                }
                val keywords = titleBinding.etSearch.text?.toString()?.trim() ?: ""
                handleSearchInput(keywords)
            }
        }

        // 添加点击监听器，确保在搜索结果页面点击搜索框时能够切换
        titleBinding.etSearch.setOnClickListener {
            Log.d("SearchFragment", "搜索框被点击，当前showResult: ${viewModel.showResult.value}")
            if (viewModel.showResult.value) {
                Log.d("SearchFragment", "从搜索结果页面切换到搜索页面")
                viewModel.showHistory()
                val keywords = titleBinding.etSearch.text?.toString()?.trim() ?: ""
                handleSearchInput(keywords)
            }
        }

        menuSearch.setOnClickListener {
            val keywords = titleBinding.etSearch.text?.trim()?.toString() ?: ""
            if (keywords.isNotEmpty()) {
                KeyboardUtils.hideSoftInput(requireActivity())
                viewModel.search(keywords)
            }
        }
    }

    private fun handleSearchInput(keywords: String) {
        // 取消之前的搜索建议请求
        searchSuggestJob?.cancel()

        if (keywords.isEmpty()) {
            // 输入为空，显示历史记录，隐藏搜索建议
            showHistoryAndHideSuggest()
        } else if (keywords.length >= 1) {
            // 有输入内容，显示搜索建议，隐藏历史记录
            showSuggestAndHideHistory()

            // 延迟100ms发起搜索建议请求，避免频繁请求
            searchSuggestJob = lifecycleScope.launch {
                delay(100)
                loadSearchSuggest(keywords)
            }
        }
    }

    private fun updateViewVisibility(showResult: Boolean) {
        if (showResult) {
            // 显示搜索结果，隐藏历史记录页面
            viewBinding.svHistory.isVisible = false
            viewBinding.llResult.isVisible = true
        } else {
            // 显示历史记录页面，隐藏搜索结果
            viewBinding.svHistory.isVisible = true
            viewBinding.llResult.isVisible = false

            // 根据当前输入状态决定显示历史记录还是搜索建议
            val keywords = titleBinding.etSearch.text?.toString()?.trim() ?: ""
            if (keywords.isEmpty()) {
                showHistoryAndHideSuggest()
            } else {
                showSuggestAndHideHistory()
            }
        }
    }

    private fun showHistoryAndHideSuggest() {
        viewBinding.llHistoryHeader.isVisible = true
        viewBinding.flHistory.isVisible = true
        viewBinding.tvSuggestTitle.isVisible = false
        viewBinding.flSearchSuggest.isVisible = false
    }

    private fun showSuggestAndHideHistory() {
        viewBinding.llHistoryHeader.isVisible = false
        viewBinding.flHistory.isVisible = false
        viewBinding.tvSuggestTitle.isVisible = true
        viewBinding.flSearchSuggest.isVisible = true
    }

    private fun initTab() {
        val pager = TabLayoutPager(
            lifecycle,
            childFragmentManager,
            viewBinding.viewPage2,
            viewBinding.tabLayout
        )
        pager.addFragment(SearchSongFragment(), "歌曲")
        pager.addFragment(SearchArtistFragment(), "歌手")
        pager.addFragment(SearchPlaylistFragment(), "歌单")
        pager.addFragment(SearchAlbumFragment(), "专辑")
        pager.addFragment(SearchUserFragment(), "用户")
        pager.setup()

        // 设置预加载页面数量
        viewBinding.viewPage2.offscreenPageLimit = 5

        // 优化标签切换：直接跳转，不滑动中间页面
        viewBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewBinding.viewPage2.setCurrentItem(it.position, false) // false表示不使用平滑滚动
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initHistory() {
        // 设置清空按钮点击事件
        viewBinding.tvClearHistory.setOnClickListener {
            viewModel.clearAllHistory()
        }

        lifecycleScope.launch {
            viewModel.historyKeywords.collectLatest { list ->
                viewBinding.flHistory.removeAllViews()

                // 根据历史记录数量显示/隐藏清空按钮
                viewBinding.tvClearHistory.isVisible = list.isNotEmpty()

                Log.d("SearchFragment", "显示搜索历史，共${list.size}个项目")
                list.forEach { text ->
                    ItemSearchHistoryBinding.inflate(
                        LayoutInflater.from(context),
                        viewBinding.flHistory,
                        true
                    ).apply {
                        root.text = text
                        // 搜索历史使用普通字体，保持原有样式
                        root.setTypeface(root.typeface, android.graphics.Typeface.NORMAL)
                        // 确保使用原有的背景样式
                        root.setBackgroundResource(R.drawable.bg_search_history_item)
                        root.setOnClickListener {
                            titleBinding.etSearch.setText(text)
                            titleBinding.etSearch.setSelection(text.length)
                            menuSearch.performClick()
                        }
                    }
                }
            }
        }
    }

    private fun initHotSearch() {
        // 加载热门搜索数据
        lifecycleScope.launch {
            kotlin.runCatching {
                SearchApi.get().getHotSearchDetailList()
            }.onSuccess { hotSearchData ->
                if (hotSearchData.data.isNotEmpty()) {
                    viewBinding.tvHotSearchTitle.isVisible = true
                    setupHotSearchList(hotSearchData.data)
                } else {
                    viewBinding.tvHotSearchTitle.isVisible = false
                }
            }.onFailure {
                viewBinding.tvHotSearchTitle.isVisible = false
            }
        }
    }

    private fun setupHotSearchList(hotSearchList: List<HotSearchDetailData>) {
        viewBinding.llHotSearchLeft.removeAllViews()
        viewBinding.llHotSearchRight.removeAllViews()

        // 取前20个热门搜索，左右各10个
        val maxCount = minOf(20, hotSearchList.size)
        val leftCount = maxCount / 2

        for (i in 0 until maxCount) {
            val hotSearch = hotSearchList[i]
            val itemView = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_hot_search, null, false)

            // 设置排名
            val tvRank = itemView.findViewById<TextView>(R.id.tvRank)
            tvRank.text = (i + 1).toString()

            // 设置排名颜色（前3名特殊颜色）
            when (i) {
                0 -> tvRank.setTextColor(getColor(R.color.rank_first))
                1 -> tvRank.setTextColor(getColor(R.color.rank_second))
                2 -> tvRank.setTextColor(getColor(R.color.rank_third))
                else -> tvRank.setTextColor(getColor(R.color.common_text_h1_color))
            }

            // 设置搜索词
            val tvSearchWord = itemView.findViewById<TextView>(R.id.tvSearchWord)
            tvSearchWord.text = hotSearch.searchWord

            // 设置内容描述
            val tvContent = itemView.findViewById<TextView>(R.id.tvContent)
            if (hotSearch.content.isNotEmpty()) {
                tvContent.text = hotSearch.content
                tvContent.isVisible = true
            } else {
                tvContent.isVisible = false
            }

            // 设置标签
            val tvHotTag = itemView.findViewById<TextView>(R.id.tvHotTag)
            val tvNewTag = itemView.findViewById<TextView>(R.id.tvNewTag)

            when (hotSearch.iconType) {
                1 -> {
                    tvHotTag.isVisible = true
                    tvNewTag.isVisible = false
                }
                2 -> {
                    tvHotTag.isVisible = false
                    tvNewTag.isVisible = true
                }
                else -> {
                    tvHotTag.isVisible = false
                    tvNewTag.isVisible = false
                }
            }

            // 设置点击事件
            itemView.setOnClickListener {
                titleBinding.etSearch.setText(hotSearch.searchWord)
                titleBinding.etSearch.setSelection(hotSearch.searchWord.length)
                menuSearch.performClick()
            }

            // 添加到对应的容器
            if (i < leftCount) {
                viewBinding.llHotSearchLeft.addView(itemView)
            } else {
                viewBinding.llHotSearchRight.addView(itemView)
            }
        }
    }

    private fun loadSearchSuggest(keywords: String) {
        lifecycleScope.launch {
            kotlin.runCatching {
                SearchApi.get().getSearchSuggest(keywords, "mobile")
            }.onSuccess { suggestData ->
                if (suggestData.result != null) {
                    setupSearchSuggest(suggestData.result!!.allMatch)
                } else {
                    viewBinding.flSearchSuggest.removeAllViews()
                }
            }.onFailure {
                viewBinding.flSearchSuggest.removeAllViews()
            }
        }
    }

    private fun setupSearchSuggest(suggestList: List<SearchSuggestItem>) {
        viewBinding.flSearchSuggest.removeAllViews()

        // 最多显示10个搜索建议
        val maxCount = minOf(10, suggestList.size)

        for (i in 0 until maxCount) {
            val suggest = suggestList[i]
            ItemSearchHistoryBinding.inflate(
                LayoutInflater.from(context),
                viewBinding.flSearchSuggest,
                true
            ).apply {
                root.text = suggest.keyword
                // 搜索建议使用粗体文字区分
                root.setTypeface(root.typeface, android.graphics.Typeface.BOLD)
                // 搜索建议使用稍微不同的背景色来区分
                root.setBackgroundResource(R.drawable.bg_search_suggest_item)
                root.setOnClickListener {
                    titleBinding.etSearch.setText(suggest.keyword)
                    titleBinding.etSearch.setSelection(suggest.keyword.length)
                    menuSearch.performClick()
                }
            }
        }
    }

    private fun initSearchBanner() {
        viewBinding.searchBanner.addBannerLifecycleObserver(this)
            .setIndicator(CircleIndicator(requireContext()))
            .setIndicatorGravity(IndicatorConfig.Direction.CENTER)
            .setIndicatorMargins(IndicatorConfig.Margins().apply {
                bottomMargin = SizeUtils.dp2px(16f)
            })
            .setAdapter(object : BannerImageAdapter<BannerData>(emptyList()) {
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: BannerData?,
                    position: Int,
                    size: Int
                ) {
                    holder?.imageView?.apply {
                        // 移除内边距，让图片充满整个Banner区域
                        setPadding(0, 0, 0, 0)
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        load(data?.pic ?: "", SizeUtils.dp2px(16f))
                        setOnClickListener {
                            data ?: return@setOnClickListener
                            if (data.song != null) {
                                playerController.addAndPlay(data.song.toMediaItem())
                                CRouter.with(context).url(RoutePath.PLAYING).start()
                            } else if (data.url.isNotEmpty()) {
                                LaunchUtils.launchBrowser(requireContext(), data.url)
                            } else if (data.targetId > 0) {
                                CRouter.with(requireActivity())
                                    .url(RoutePath.PLAYLIST_DETAIL)
                                    .extra("id", data.targetId)
                                    .start()
                            }
                        }
                    }
                }
            })

        // 加载轮播图数据
        lifecycleScope.launch {
            kotlin.runCatching {
                DiscoverApi.get().getBannerList()
            }.onSuccess { bannerListData ->
                viewBinding.searchBanner.isVisible = bannerListData.banners.isNotEmpty()
                viewBinding.searchBannerPlaceholder.isVisible = bannerListData.banners.isEmpty()
                if (bannerListData.banners.isNotEmpty()) {
                    viewBinding.searchBanner.setDatas(bannerListData.banners)
                }
            }.onFailure {
                viewBinding.searchBanner.isVisible = false
                viewBinding.searchBannerPlaceholder.isVisible = true
            }
        }
    }

    override fun onInterceptBackEvent(): Boolean {
        if (viewModel.showResult.value) {
            // 在搜索结果页面，返回到搜索页面
            viewModel.showHistory()
            return true
        } else if (viewBinding.flSearchSuggest.isVisible) {
            // 如果当前显示搜索建议，直接返回两次（不返回到搜索历史）
            return false // 让系统处理返回事件，直接退出
        }
        return super.onInterceptBackEvent()
    }

    /**
     * 初始化返回键处理逻辑
     * 在显示搜索建议时，点击返回键直接退出，不返回到搜索历史
     */
    private fun initBackPressedHandler() {
        // 这个方法现在不需要额外的逻辑，因为onInterceptBackEvent已经处理了
    }
}