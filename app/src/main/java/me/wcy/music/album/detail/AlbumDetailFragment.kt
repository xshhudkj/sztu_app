package me.wcy.music.album.detail

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
import me.wcy.music.databinding.FragmentPlaylistDetailBinding
import me.wcy.music.discover.playlist.detail.item.PlaylistSongItemBinder
import me.wcy.music.service.PlayerController
import me.wcy.music.utils.ImageUtils.loadCover
import me.wcy.music.utils.toMediaItem
import me.wcy.radapter3.RAdapter
import me.wcy.router.CRouter
import me.wcy.router.annotation.Route
import top.wangchenyan.common.ext.getColor
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.ext.viewBindings
import top.wangchenyan.common.insets.WindowInsetsUtils.getStatusBarHeight
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * 专辑详情页面
 * Created by wangchenyan.top on 2024/12/20.
 */
@Route(RoutePath.ALBUM_DETAIL)
@AndroidEntryPoint
class AlbumDetailFragment : BaseMusicFragment() {
    private val viewBinding by viewBindings<FragmentPlaylistDetailBinding>()
    private val viewModel by viewModels<AlbumDetailViewModel>()
    private val adapter by lazy { RAdapter<SongData>() }
    private var collectMenu: View? = null

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
        initAlbumInfo()
        initSongList()
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
        collectMenu = getTitleLayout()?.addImageMenu(R.drawable.ic_favorite_selector, false)
        collectMenu?.setOnClickListener {
            viewModel.albumData.value ?: return@setOnClickListener
            userService.checkLogin(requireActivity()) {
                lifecycleScope.launch {
                    showLoading()
                    val res = viewModel.toggleSubscribe()
                    dismissLoading()
                    if (res.isSuccess()) {
                        toast("操作成功")
                    } else {
                        toast(res.msg)
                    }
                }
            }
        }

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

        lifecycleScope.launch {
            userService.profile.collectLatest { profile ->
                updateCollectState()
            }
        }
    }

    private fun initAlbumInfo() {
        lifecycleScope.launch {
            viewModel.albumData.collectLatest { albumData ->
                albumData ?: return@collectLatest
                getTitleLayout()?.setTitleText(albumData.name)
                updateCollectState()
                viewBinding.ivCover.loadCover(albumData.picUrl, SizeUtils.dp2px(6f))

                // 隐藏播放次数（专辑没有播放次数概念）
                viewBinding.tvPlayCount.isVisible = false

                viewBinding.tvName.text = albumData.name

                // 隐藏创建者头像，显示专辑艺术家信息
                viewBinding.ivCreatorAvatar.isVisible = false
                val artistName = when {
                    albumData.artist != null -> albumData.artist.name
                    albumData.artists.isNotEmpty() -> albumData.artists.joinToString(" / ") { it.name }
                    else -> "未知艺术家"
                }
                viewBinding.tvCreatorName.text = artistName
                viewBinding.tvSongCount.text = "(${albumData.size})"

                // 隐藏标签区域（专辑通常没有标签）
                viewBinding.flTags.isVisible = false

                // 显示专辑描述，如果没有描述则显示专辑信息
                val description = if (albumData.description.isNotEmpty()) {
                    albumData.description
                } else {
                    buildString {
                        if (albumData.company.isNotEmpty()) {
                            append("发行公司：${albumData.company}\n")
                        }
                        if (albumData.publishTime > 0) {
                            val publishDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                .format(Date(albumData.publishTime))
                            append("发行时间：$publishDate\n")
                        }
                        if (albumData.subType.isNotEmpty()) {
                            append("专辑类型：${albumData.subType}\n")
                        }
                        if (albumData.size > 0) {
                            append("歌曲数量：${albumData.size}首")
                        }
                        if (isEmpty()) {
                            append("暂无专辑简介")
                        }
                    }
                }
                viewBinding.tvDesc.text = description
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
            }
        }
    }

    private fun updateCollectState() {
        val albumData = viewModel.albumData.value
        if (albumData == null) {
            collectMenu?.isVisible = false
            return
        }
        collectMenu?.isVisible = true
        collectMenu?.isSelected = viewModel.isSubscribed.value
    }
}
