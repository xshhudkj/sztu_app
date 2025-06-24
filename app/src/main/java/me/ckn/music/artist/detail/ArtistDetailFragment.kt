package me.ckn.music.artist.detail

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
import me.ckn.music.R
import me.ckn.music.account.service.UserService
import me.ckn.music.common.BaseMusicFragment
import me.ckn.music.common.OnItemClickListener2
import me.ckn.music.common.bean.SongData
import me.ckn.music.common.dialog.songmenu.SongMoreMenuDialog
import me.ckn.music.common.dialog.songmenu.items.AlbumMenuItem
import me.ckn.music.common.dialog.songmenu.items.ArtistMenuItem
import me.ckn.music.common.dialog.songmenu.items.CollectMenuItem
import me.ckn.music.consts.RoutePath
import me.ckn.music.databinding.FragmentPlaylistDetailBinding
import me.ckn.music.discover.playlist.detail.item.PlaylistSongItemBinder
import me.ckn.music.service.PlayerController
import me.ckn.music.utils.ImageUtils.loadCover
import me.ckn.music.utils.toMediaItem
import me.wcy.radapter3.RAdapter
import me.wcy.router.CRouter
import me.wcy.router.annotation.Route
import top.wangchenyan.common.ext.getColor
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
    private val viewBinding by viewBindings<FragmentPlaylistDetailBinding>()
    private val viewModel by viewModels<ArtistDetailViewModel>()
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
        initArtistInfo()
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
            viewModel.artistData.value ?: return@setOnClickListener
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

        getTitleLayout()?.findViewById<View>(R.id.ivBack)?.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                parentFragmentManager.popBackStack()
            } else {
                finish()
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

    private fun initArtistInfo() {
        lifecycleScope.launch {
            viewModel.artistData.collectLatest { artistData ->
                artistData ?: return@collectLatest
                getTitleLayout()?.setTitleText(artistData.name)
                updateCollectState()
                
                // 加载歌手头像，添加图片尺寸参数优化显示效果
                val imageUrl = if (artistData.picUrl.isNotEmpty()) {
                    artistData.picUrl + "?param=400y400"
                } else {
                    ""
                }
                viewBinding.ivCover.loadCover(imageUrl, SizeUtils.dp2px(6f))
                
                // 隐藏播放次数（歌手没有播放次数概念）
                viewBinding.tvPlayCount.isVisible = false
                
                viewBinding.tvName.text = artistData.name
                
                // 隐藏创建者头像，显示歌手别名
                viewBinding.ivCreatorAvatar.isVisible = false
                viewBinding.tvCreatorName.text = if (artistData.alias.isNotEmpty()) {
                    artistData.alias.joinToString(" / ") { it.toString() }
                } else {
                    "歌手"
                }
                
                val songCount = viewModel.songList.value.size
                viewBinding.tvSongCount.text = "($songCount)"

                // 隐藏标签区域（歌手通常没有标签）
                viewBinding.flTags.isVisible = false

                // 显示歌手简介，如果没有简介则显示歌手统计信息
                val description = if (artistData.briefDesc.isNotEmpty()) {
                    artistData.briefDesc
                } else {
                    buildString {
                        if (artistData.musicSize > 0) {
                            append("单曲数量：${artistData.musicSize}首\n")
                        }
                        if (artistData.albumSize > 0) {
                            append("专辑数量：${artistData.albumSize}张\n")
                        }
                        if (artistData.mvSize > 0) {
                            append("MV数量：${artistData.mvSize}个")
                        }
                        if (isEmpty()) {
                            append("暂无歌手简介")
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
                // 更新歌曲数量显示
                viewBinding.tvSongCount.text = "(${songList.size})"
            }
        }
    }

    private fun updateCollectState() {
        val artistData = viewModel.artistData.value
        if (artistData == null) {
            collectMenu?.isVisible = false
            return
        }
        collectMenu?.isVisible = true
        collectMenu?.isSelected = viewModel.isSubscribed.value
    }
}
