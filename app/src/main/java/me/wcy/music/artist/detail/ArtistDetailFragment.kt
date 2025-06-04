package me.wcy.music.artist.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.wcy.music.R
import me.wcy.music.account.service.UserService
import me.wcy.music.common.BaseMusicFragment
import me.wcy.music.common.OnItemClickListener2
import me.wcy.music.common.bean.SongData
import me.wcy.music.common.dialog.songmenu.SongMoreMenuDialog
import me.wcy.music.common.dialog.songmenu.items.AlbumMenuItem
import me.wcy.music.common.dialog.songmenu.items.ArtistMenuItem
import me.wcy.music.common.dialog.songmenu.items.CollectMenuItem
import me.wcy.music.common.dialog.songmenu.items.CommentMenuItem
import me.wcy.music.consts.RoutePath
import me.wcy.music.databinding.FragmentArtistDetailBinding
import me.wcy.music.discover.playlist.detail.item.PlaylistSongItemBinder
import me.wcy.music.service.PlayerController
import me.wcy.music.utils.ImageUtils.loadCover
import me.wcy.music.utils.toMediaItem
import me.wcy.radapter3.RAdapter
import me.wcy.router.CRouter
import me.wcy.router.annotation.Route
import top.wangchenyan.common.ext.getColor
import top.wangchenyan.common.ext.loadAvatar
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.ext.viewBindings
import top.wangchenyan.common.insets.WindowInsetsUtils.getStatusBarHeight
import javax.inject.Inject

/**
 * 歌手详情页面
 * Created by wangchenyan.top on 2024/12/20.
 */
@Route(RoutePath.ARTIST_DETAIL)
@AndroidEntryPoint
class ArtistDetailFragment : BaseMusicFragment() {
    private val viewBinding by viewBindings<FragmentArtistDetailBinding>()
    private val viewModel by viewModels<ArtistDetailViewModel>()
    private val adapter by lazy { RAdapter<SongData>() }

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var playerController: PlayerController

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun isUseLoadSir(): Boolean {
        return true
    }

    override fun getLoadSirTarget(): View {
        return viewBinding.coordinatorLayout
    }

    override fun onReload() {
        super.onReload()
        loadData()
    }

    override fun onLazyCreate() {
        super.onLazyCreate()

        val id = getRouteArguments().getLongExtra("id", 0)
        if (id <= 0) {
            finish()
            return
        }

        configWindowInsets {
            navBarColor = getColor(R.color.play_bar_bg)
        }

        viewModel.init(id)

        initTitle()
        initArtistInfo()
        initSongList()
        initFavorite()
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            showLoadSirLoading()
            val res = viewModel.loadData()
            if (res.isSuccess()) {
                showLoadSirSuccess()
            } else {
                showLoadSirError(res.msg)
            }
        }
    }

    private fun initTitle() {
        view?.getStatusBarHeight {
            viewBinding.titlePlaceholder.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = it
            }
            viewBinding.toolbarPlaceholder.updateLayoutParams<ViewGroup.LayoutParams> {
                height = resources.getDimensionPixelSize(R.dimen.common_title_bar_size) + it
            }
        }

        viewBinding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            getTitleLayout()?.updateScroll(-verticalOffset)
        }
    }

    private fun initArtistInfo() {
        lifecycleScope.launch {
            viewModel.artistData.collectLatest { artistData ->
                artistData ?: return@collectLatest
                getTitleLayout()?.setTitleText(artistData.name)
                viewBinding.ivCover.loadCover(artistData.picUrl, SizeUtils.dp2px(6f))
                viewBinding.tvName.text = artistData.name
                viewBinding.tvAlias.text = if (artistData.alias.isNotEmpty()) {
                    artistData.alias.joinToString(" / ")
                } else {
                    "歌手"
                }
                viewBinding.tvDesc.text = "歌手简介"
            }
        }
    }

    private fun initSongList() {
        viewBinding.llPlayAll.setOnClickListener {
            val songList = viewModel.songList.value.map { it.toMediaItem() }
            if (songList.isNotEmpty()) {
                playerController.replaceAll(songList, songList.first())
                CRouter.with(requireContext()).url(RoutePath.PLAYING).start()
            }
        }

        adapter.register(PlaylistSongItemBinder(object : OnItemClickListener2<SongData> {
            override fun onItemClick(item: SongData, position: Int) {
                val songList = viewModel.songList.value.map { it.toMediaItem() }
                if (songList.isNotEmpty()) {
                    playerController.replaceAll(songList, songList[position])
                    CRouter.with(requireContext()).url(RoutePath.PLAYING).start()
                }
            }

            override fun onMoreClick(item: SongData, position: Int) {
                val items = mutableListOf(
                    CollectMenuItem(lifecycleScope, item),
                    CommentMenuItem(item),
                    ArtistMenuItem(item),
                    AlbumMenuItem(item)
                )
                SongMoreMenuDialog(requireActivity(), item)
                    .setItems(items)
                    .show()
            }
        }))
        viewBinding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.songList.collectLatest { songList ->
                adapter.refresh(songList)
                viewBinding.tvSongCount.text = "(${songList.size})"
            }
        }
    }

    private fun initFavorite() {
        viewBinding.ivFavorite.setOnClickListener {
            lifecycleScope.launch {
                val result = viewModel.toggleSubscribe()
                if (result.isSuccess()) {
                    val isSubscribed = viewModel.isSubscribed.value
                    toast(if (isSubscribed) "收藏成功" else "取消收藏成功")
                } else {
                    toast(result.msg ?: "操作失败")
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isSubscribed.collectLatest { isSubscribed ->
                viewBinding.ivFavorite.setImageResource(
                    if (isSubscribed) R.drawable.ic_favorite_fill else R.drawable.ic_favorite
                )
            }
        }
    }
}
