package me.wcy.music.mine.home

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.wcy.music.R
import me.wcy.music.account.service.UserService
import me.wcy.music.common.ApiDomainDialog
import me.wcy.music.common.BaseMusicFragment
import me.wcy.music.common.bean.PlaylistData
import me.wcy.music.consts.RoutePath
import me.wcy.music.databinding.FragmentMineBinding
import me.wcy.music.main.MainActivity
import me.wcy.music.mine.home.viewmodel.MineViewModel
import me.wcy.music.mine.playlist.UserPlaylistItemBinder
import me.wcy.radapter3.RAdapter
import me.wcy.router.CRouter
import top.wangchenyan.common.ext.loadAvatar
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.ext.viewBindings
import top.wangchenyan.common.widget.decoration.SpacingDecoration
import top.wangchenyan.common.widget.dialog.BottomItemsDialogBuilder
import me.wcy.music.common.enableImmersiveMode
import me.wcy.music.account.VipApi
import me.wcy.music.common.bean.VipInfoData
import me.wcy.music.common.bean.UserLevelData
import top.wangchenyan.common.net.apiCall
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import javax.inject.Inject

/**
 * Created by wangchenyan.top on 2023/8/21.
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

        initTitle()
        initProfile()
        initVipInfo()
        initLocalMusic()
        initPlaylist()
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
                viewBinding.ivAvatar.loadAvatar(profile?.avatarUrl)
                viewBinding.tvNickName.text = profile?.nickname
                viewBinding.flProfile.setOnClickListener {
                    if (ApiDomainDialog.checkApiDomain(requireActivity())) {
                        if (userService.isLogin().not()) {
                            CRouter.with(requireActivity())
                                .url(RoutePath.LOGIN)
                                .start()
                        }
                    }
                }

                // 用户登录后更新VIP信息
                if (profile != null && userService.isLogin()) {
                    updateVipInfo()
                }
            }
        }
    }

    /**
     * 初始化VIP信息和用户等级显示
     * 获取用户VIP状态和等级信息，在用户名右侧显示相应标签
     */
    private fun initVipInfo() {
        // 初始状态隐藏所有标签
        viewBinding.tvVipLabel.visibility = android.view.View.GONE
        viewBinding.tvLevelLabel.visibility = android.view.View.GONE
    }

    /**
     * 更新VIP信息显示
     * 获取并显示用户的VIP状态和等级信息
     */
    private fun updateVipInfo() {
        lifecycleScope.launch {
            try {
                // 并行获取VIP信息和用户等级信息
                val vipInfoDeferred = async {
                    VipApi.get().getVipInfoV2()
                }
                val userLevelDeferred = async {
                    VipApi.get().getUserLevel()
                }

                val vipInfoResult = vipInfoDeferred.await()
                val userLevelResult = userLevelDeferred.await()

                // 更新VIP标签
                if (vipInfoResult.code == 200) {
                    updateVipLabel(vipInfoResult)
                }

                // 更新等级标签
                if (userLevelResult.code == 200) {
                    updateLevelLabel(userLevelResult)
                }
            } catch (e: Exception) {
                // 获取VIP信息失败时，隐藏标签
                viewBinding.tvVipLabel.visibility = android.view.View.GONE
                viewBinding.tvLevelLabel.visibility = android.view.View.GONE
            }
        }
    }

    /**
     * 更新VIP标签显示
     * @param vipInfo VIP信息数据
     */
    private fun updateVipLabel(vipInfo: VipInfoData) {
        val vipData = vipInfo.data
        viewBinding.tvVipLabel.apply {
            visibility = android.view.View.VISIBLE
            text = "VIP"
            setTextColor(resources.getColor(me.wcy.music.R.color.white, null))

            if (vipData != null && vipData.isVip) {
                // VIP用户显示金色标签
                setBackgroundResource(me.wcy.music.R.drawable.bg_vip_label_gold)
            } else {
                // 非VIP用户显示灰色标签
                setBackgroundResource(me.wcy.music.R.drawable.bg_vip_label_gray)
            }
        }
    }

    /**
     * 更新等级标签显示
     * @param levelData 用户等级数据
     */
    private fun updateLevelLabel(levelData: UserLevelData) {
        val levelInfo = levelData.data
        if (levelInfo != null && levelInfo.level > 0) {
            viewBinding.tvLevelLabel.apply {
                visibility = android.view.View.VISIBLE
                text = "Lv.${levelInfo.level}"
                setBackgroundResource(me.wcy.music.R.drawable.bg_level_label)
                setTextColor(resources.getColor(me.wcy.music.R.color.white, null))
            }
        } else {
            viewBinding.tvLevelLabel.visibility = android.view.View.GONE
        }
    }

    private fun initLocalMusic() {
        viewBinding.localMusic.setOnClickListener {
            CRouter.with().url(RoutePath.LOCAL_SONG).start()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initPlaylist() {
        val likePlaylistAdapter = RAdapter<PlaylistData>().apply {
            register(UserPlaylistItemBinder(true, ItemClickListener(true, isLike = true)))
        }
        val myPlaylistAdapter = RAdapter<PlaylistData>().apply {
            register(UserPlaylistItemBinder(true, ItemClickListener(true, isLike = false)))
        }
        val collectPlaylistAdapter = RAdapter<PlaylistData>().apply {
            register(UserPlaylistItemBinder(false, ItemClickListener(false, isLike = false)))
        }

        val spacingDecoration = SpacingDecoration(SizeUtils.dp2px(10f))
        viewBinding.rvLikePlaylist.adapter = likePlaylistAdapter
        viewBinding.rvMyPlaylist.addItemDecoration(spacingDecoration)
        viewBinding.rvMyPlaylist.adapter = myPlaylistAdapter
        viewBinding.rvCollectPlaylist.addItemDecoration(spacingDecoration)
        viewBinding.rvCollectPlaylist.adapter = collectPlaylistAdapter

        val updateVisible = {
            viewBinding.llLikePlaylist.isVisible = viewModel.likePlaylist.value != null
            viewBinding.llMyPlaylist.isVisible = viewModel.myPlaylists.value.isNotEmpty()
            viewBinding.llCollectPlaylist.isVisible = viewModel.collectPlaylists.value.isNotEmpty()
        }

        lifecycleScope.launch {
            viewModel.likePlaylist.collectLatest { likePlaylist ->
                updateVisible()
                if (likePlaylist != null) {
                    likePlaylistAdapter.refresh(listOf(likePlaylist))
                }
            }
        }
        lifecycleScope.launch {
            viewModel.myPlaylists.collectLatest { myPlaylists ->
                updateVisible()
                viewBinding.tvMyPlaylist.text = "创建歌单(${myPlaylists.size})"
                myPlaylistAdapter.refresh(myPlaylists)
            }
        }
        lifecycleScope.launch {
            viewModel.collectPlaylists.collectLatest { collectPlaylists ->
                updateVisible()
                viewBinding.tvCollectPlaylist.text = "收藏歌单(${collectPlaylists.size})"
                collectPlaylistAdapter.refresh(collectPlaylists)
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

        override fun onMoreClick(item: PlaylistData) {
            val dialog = BottomItemsDialogBuilder(requireActivity())
                .items(listOf("删除"))
                .onClickListener { dialog, which ->
                    lifecycleScope.launch {
                        showLoading()
                        val res = viewModel.removeCollect(item.id)
                        dismissLoading()
                        if (res.isSuccess().not()) {
                            toast(res.msg)
                        }
                    }
                }
                .build()

            // 应用全屏沉浸式模式，确保Dialog符合官方最佳实践
            dialog.enableImmersiveMode()
            dialog.show()
        }
    }
}