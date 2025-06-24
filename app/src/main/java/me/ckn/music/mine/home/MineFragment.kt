package me.ckn.music.mine.home

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.ckn.music.R
import me.ckn.music.account.service.UserService
import me.ckn.music.common.ApiDomainDialog
import me.ckn.music.common.BaseMusicFragment
import me.ckn.music.common.bean.PlaylistData
import me.ckn.music.consts.RoutePath
import me.ckn.music.databinding.FragmentMineBinding
import me.ckn.music.main.MainActivity
import me.ckn.music.mine.home.viewmodel.MineViewModel
import me.ckn.music.mine.playlist.UserPlaylistItemBinder
import me.wcy.radapter3.RAdapter
import me.wcy.router.CRouter
import top.wangchenyan.common.ext.loadAvatar
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.ext.viewBindings
import top.wangchenyan.common.widget.decoration.SpacingDecoration
import top.wangchenyan.common.widget.dialog.BottomItemsDialogBuilder
import me.ckn.music.common.enableImmersiveMode
import javax.inject.Inject
import androidx.fragment.app.commit
import me.ckn.music.mine.local.LocalMusicFragment
import me.ckn.music.mine.home.MineDetailPlaceholderFragment
import me.ckn.music.discover.playlist.detail.PlaylistDetailFragment
import me.ckn.music.mine.recent.RecentPlayFragment

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/8/21
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：我的页面Fragment
 * File Description: Mine page Fragment
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@AndroidEntryPoint
class MineFragment : BaseMusicFragment() {
    private val viewBinding by viewBindings<FragmentMineBinding>()
    private val viewModel by viewModels<MineViewModel>()

    @Inject
    lateinit var userService: UserService

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun onLazyCreate() {
        super.onLazyCreate()

        // 默认右侧加载空白页
        childFragmentManager.commit {
            replace(R.id.mineDetailContainer, MineDetailPlaceholderFragment())
        }
        
        initTitle()
        initProfile()
        initHdSidebar()
        viewModel.updatePlaylistFromCache()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updatePlaylist()
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
            addImageMenu(
                R.drawable.ic_menu_search,
                isDayNight = true,
                isLeft = false
            ).setOnClickListener {
                if (ApiDomainDialog.checkApiDomain(requireContext())) {
                    CRouter.with(requireActivity()).url(RoutePath.SEARCH).start()
                }
            }
        }
    }

    private fun initProfile() {
        lifecycleScope.launch {
            userService.profile.collectLatest { profile ->
                viewBinding.mineSidebar.ivAvatar.loadAvatar(profile?.avatarUrl)
                viewBinding.mineSidebar.tvNickName.text = profile?.nickname
                viewBinding.mineSidebar.flProfile.setOnClickListener {
                    if (ApiDomainDialog.checkApiDomain(requireActivity())) {
                        if (userService.isLogin().not()) {
                            CRouter.with(requireActivity())
                                .url(RoutePath.LOGIN)
                                .start()
                        }
                    }
                }
            }
        }
    }

    private fun initHdSidebar() {
        // 本地音乐
        viewBinding.mineSidebar.localMusic.setOnClickListener {
            childFragmentManager.commit {
                replace(R.id.mineDetailContainer, LocalMusicFragment())
            }
        }
        // 最近播放
        viewBinding.mineSidebar.recentPlay.setOnClickListener {
            childFragmentManager.commit {
                replace(R.id.mineDetailContainer, RecentPlayFragment())
            }
        }
        // 歌单
        setupHdPlaylistClicks()
    }

    private fun setupHdPlaylistClicks() {
        // 只需处理歌单item点击，复用原有逻辑
        val likePlaylistAdapter = RAdapter<PlaylistData>().apply {
            register(UserPlaylistItemBinder(true, HdItemClickListener(true, isLike = true)))
        }
        val myPlaylistAdapter = RAdapter<PlaylistData>().apply {
            register(UserPlaylistItemBinder(true, HdItemClickListener(true, isLike = false)))
        }
        val collectPlaylistAdapter = RAdapter<PlaylistData>().apply {
            register(UserPlaylistItemBinder(false, HdItemClickListener(false, isLike = false)))
        }
        val spacingDecoration = SpacingDecoration(SizeUtils.dp2px(10f))
        viewBinding.mineSidebar.rvLikePlaylist.adapter = likePlaylistAdapter
        viewBinding.mineSidebar.rvMyPlaylist.addItemDecoration(spacingDecoration)
        viewBinding.mineSidebar.rvMyPlaylist.adapter = myPlaylistAdapter
        viewBinding.mineSidebar.rvCollectPlaylist.addItemDecoration(spacingDecoration)
        viewBinding.mineSidebar.rvCollectPlaylist.adapter = collectPlaylistAdapter
        val updateVisible = {
            viewBinding.mineSidebar.llLikePlaylist.isVisible = likePlaylistAdapter.itemCount > 0
            viewBinding.mineSidebar.llMyPlaylist.isVisible = myPlaylistAdapter.itemCount > 0
            viewBinding.mineSidebar.llCollectPlaylist.isVisible = collectPlaylistAdapter.itemCount > 0
        }
        lifecycleScope.launch {
            viewModel.myPlaylists.collectLatest {
                val likePlaylist = it.firstOrNull() { it.specialType == 5 }
                myPlaylistAdapter.refresh(it.filter { it.specialType != 5 })
                updateVisible()
                if (likePlaylist != null) {
                    likePlaylistAdapter.refresh(listOf(likePlaylist))
                }
            }
        }
        lifecycleScope.launch {
            viewModel.collectPlaylists.collectLatest {
                collectPlaylistAdapter.refresh(it)
                updateVisible()
            }
        }
    }

    inner class ItemClickListener(private val isMine: Boolean, private val isLike: Boolean) :
        UserPlaylistItemBinder.OnItemClickListener {
        override fun onItemClick(item: PlaylistData) {
            CRouter.with(requireActivity())
                .url(RoutePath.PLAYLIST_DETAIL)
                .extra("id", item.id)
                .extra("realtime_data", isMine)
                .extra("is_like", isLike)
                .start()
        }
    }

    inner class HdItemClickListener(private val isMine: Boolean, private val isLike: Boolean) :
        UserPlaylistItemBinder.OnItemClickListener {
        override fun onItemClick(item: PlaylistData) {
            val fragment = PlaylistDetailFragment().apply {
                arguments = android.os.Bundle().apply {
                    putLong("id", item.id)
                    putBoolean("realtime_data", isMine)
                    putBoolean("is_like", isLike)
                }
            }
            childFragmentManager.commit {
                replace(R.id.mineDetailContainer, fragment)
            }
        }
    }
}