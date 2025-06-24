package me.ckn.music.discover.home

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.ckn.music.R
import me.ckn.music.account.service.UserService
import me.ckn.music.common.ApiDomainDialog
import me.ckn.music.common.BaseMusicFragment
import me.ckn.music.common.bean.PlaylistData
import me.ckn.music.consts.RoutePath
import me.ckn.music.databinding.FragmentDiscoverBinding
import me.ckn.music.discover.DiscoverApi
import me.ckn.music.discover.banner.BannerData
import me.ckn.music.discover.home.viewmodel.DiscoverViewModel
import me.ckn.music.discover.playlist.square.item.PlaylistItemBinder
import me.ckn.music.discover.ranking.discover.item.DiscoverRankingItemBinder
import me.ckn.music.ext.addButtonAnimation
import me.ckn.music.main.MainActivity
import me.ckn.music.service.PlayerController
import me.ckn.music.storage.preference.ConfigPreferences
import me.ckn.music.utils.BatchPlaylistLoader
import me.ckn.music.utils.toMediaItem
import me.wcy.radapter3.RAdapter
import me.wcy.router.CRouter
import top.wangchenyan.common.ext.load
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.ext.viewBindings
import top.wangchenyan.common.utils.LaunchUtils
import top.wangchenyan.common.widget.decoration.SpacingDecoration
import javax.inject.Inject

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/8/21
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：发现页面Fragment
 * File Description: Discover page Fragment
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@AndroidEntryPoint
class DiscoverFragment : BaseMusicFragment() {
    private val viewBinding by viewBindings<FragmentDiscoverBinding>()
    private val viewModel by viewModels<DiscoverViewModel>()

    private val recommendPlaylistAdapter by lazy {
        RAdapter<PlaylistData>()
    }
    private val rankingListAdapter by lazy {
        RAdapter<PlaylistData>()
    }

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var playerController: PlayerController

    private val batchPlaylistLoader by lazy {
        BatchPlaylistLoader(playerController, lifecycleScope)
    }

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun isUseLoadSir(): Boolean {
        return true
    }

    override fun getLoadSirTarget(): View {
        return viewBinding.content
    }

    override fun onReload() {
        super.onReload()
        checkApiDomain(true)
    }

    override fun onLazyCreate() {
        super.onLazyCreate()

        initTitle()
        initBanner()
        initTopButton()
        initRecommendPlaylist()
        initRankingList()
        checkApiDomain(false)
    }

    private fun initTitle() {
        getTitleLayout()?.run {
            addImageMenu(
                R.drawable.ic_menu,
                isDayNight = true,
                isLeft = true
            ).setOnClickListener {
                val activity = requireActivity()
                if (activity is MainActivity) {
                    activity.openDrawer()
                }
            }
        }
        getTitleLayout()?.getContentView()?.setOnClickListener {
            if (ApiDomainDialog.checkApiDomain(requireContext())) {
                CRouter.with(requireActivity()).url(RoutePath.SEARCH).start()
            }
        }
    }

    private fun initBanner() {
        viewBinding.banner.addBannerLifecycleObserver(this)
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
        lifecycleScope.launch {
            viewModel.bannerList.collectLatest {
                viewBinding.banner.isVisible = it.isNotEmpty()
                viewBinding.bannerPlaceholder.isVisible = it.isEmpty()
                if (it.isNotEmpty()) {
                    viewBinding.banner.setDatas(it)
                }
            }
        }
    }

    private fun initTopButton() {
        // 为推荐歌曲按钮添加按钮动画
        viewBinding.btnRecommendSong.addButtonAnimation()
        viewBinding.btnRecommendSong.setOnClickListener {
            CRouter.with(requireActivity()).url(RoutePath.RECOMMEND_SONG).start()
        }

        // 为私人FM按钮添加按钮动画
        viewBinding.btnPrivateFm.addButtonAnimation()
        viewBinding.btnPrivateFm.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val playlists = DiscoverApi.get().getRecommendPlaylists().playlists
                    if (playlists.isNullOrEmpty()) {
                        toast("暂无推荐歌单")
                        return@launch
                    }
                    val randomPlaylist = playlists.random()
                    val songListData = DiscoverApi.get().getPlaylistSongList(randomPlaylist.id)
                    val songs = songListData.songs
                    if (songs.isNullOrEmpty()) {
                        toast("该歌单没有歌曲")
                        return@launch
                    }
                    val mediaItems = songs.map { it.toMediaItem() }
                    playerController.replaceAll(mediaItems, mediaItems.first())
                    CRouter.with(requireContext()).url(RoutePath.PLAYING).start()
                } catch (e: Exception) {
                    toast("漫游播放失败：${e.message}")
                }
            }
        }

        // 为推荐歌单按钮添加按钮动画
        viewBinding.btnRecommendPlaylist.addButtonAnimation()
        viewBinding.btnRecommendPlaylist.setOnClickListener {
            CRouter.with(requireActivity())
                .url(RoutePath.PLAYLIST_SQUARE)
                .start()
        }

        // 为排行榜按钮添加按钮动画
        viewBinding.btnRank.addButtonAnimation()
        viewBinding.btnRank.setOnClickListener {
            CRouter.with(requireActivity()).url(RoutePath.RANKING).start()
        }
    }

    private fun initRecommendPlaylist() {
        viewBinding.tvRecommendPlaylist.setOnClickListener {
            CRouter.with(requireActivity())
                .url(RoutePath.PLAYLIST_SQUARE)
                .start()
        }
        val itemWidth = (ScreenUtils.getAppScreenWidth() - SizeUtils.dp2px(20f)) / 6
        recommendPlaylistAdapter.register(PlaylistItemBinder(itemWidth, true, object :
            PlaylistItemBinder.OnItemClickListener {
            override fun onItemClick(item: PlaylistData) {
                CRouter.with(requireActivity())
                    .url(RoutePath.PLAYLIST_DETAIL)
                    .extra("id", item.id)
                    .start()
            }

            override fun onPlayClick(item: PlaylistData) {
                playPlaylist(item, 0)
            }
        }))
        viewBinding.rvRecommendPlaylist.adapter = recommendPlaylistAdapter
        viewBinding.rvRecommendPlaylist.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewBinding.rvRecommendPlaylist.addItemDecoration(
            SpacingDecoration(SizeUtils.dp2px(10f))
        )

        val updateVisibility = {
            if (userService.isLogin() && viewModel.recommendPlaylist.value.isNotEmpty()) {
                viewBinding.tvRecommendPlaylist.isVisible = true
                viewBinding.rvRecommendPlaylist.isVisible = true
            } else {
                viewBinding.tvRecommendPlaylist.isVisible = false
                viewBinding.rvRecommendPlaylist.isVisible = false
            }
        }

        lifecycleScope.launch {
            userService.profile.collectLatest {
                updateVisibility()
            }
        }

        lifecycleScope.launch {
            viewModel.recommendPlaylist.collectLatest { recommendPlaylist ->
                updateVisibility()
                recommendPlaylistAdapter.refresh(recommendPlaylist)
            }
        }
    }

    private fun initRankingList() {
        viewBinding.tvRankingList.setOnClickListener {
            CRouter.with(requireActivity())
                .url(RoutePath.RANKING)
                .start()
        }
        rankingListAdapter.register(DiscoverRankingItemBinder(object :
            DiscoverRankingItemBinder.OnItemClickListener {
            override fun onItemClick(item: PlaylistData, position: Int) {
                CRouter.with(requireActivity())
                    .url(RoutePath.PLAYLIST_DETAIL)
                    .extra("id", item.id)
                    .start()
            }

            override fun onSongClick(item: PlaylistData, songPosition: Int) {
                playPlaylist(item, songPosition)
            }
        }))
        viewBinding.rvRankingList.apply {
            // 使用水平LinearLayoutManager实现横向滚动
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = rankingListAdapter

            // 添加间距装饰器
            addItemDecoration(SpacingDecoration(SizeUtils.dp2px(10f)))
        }

        viewModel.rankingList.observe(this) { rankingList ->
            rankingList ?: return@observe
            if (viewModel.rankingList.value?.isNotEmpty() == true) {
                viewBinding.tvRankingList.isVisible = true
                viewBinding.rvRankingList.isVisible = true
            } else {
                viewBinding.tvRankingList.isVisible = false
                viewBinding.rvRankingList.isVisible = false
            }
            rankingListAdapter.refresh(rankingList)
        }
    }

    private fun checkApiDomain(isReload: Boolean) {
        if (ConfigPreferences.apiDomain.isNotEmpty()) {
            showLoadSirSuccess()
        } else {
            showLoadSirError("请先设置云音乐API域名")
            if (isReload) {
                ApiDomainDialog.checkApiDomain(requireContext())
            }
        }
    }

    private fun playPlaylist(playlistData: PlaylistData, songPosition: Int) {
        batchPlaylistLoader.playPlaylistSong(
            playlistId = playlistData.id,
            songPosition = songPosition,
            onPlayStarted = {
                // 播放开始后自动弹出播放页面
                CRouter.with(requireContext()).url(RoutePath.PLAYING).start()
            },
            onError = {
                // 静默处理错误，不显示弹窗
            }
        )
    }
}