package me.ckn.music.mine.home

import android.annotation.SuppressLint
import android.util.Log
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
import me.ckn.music.account.VipApi
import me.ckn.music.account.service.UserStateManager
import me.ckn.music.common.bean.VipInfoData
import me.ckn.music.common.bean.UserLevelData
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

    @Inject
    lateinit var userStateManager: UserStateManager

    // 刷新间隔控制 - 5分钟内不重复自动刷新
    private var lastAutoRefreshTime = 0L
    private val AUTO_REFRESH_INTERVAL = 5 * 60 * 1000L // 5分钟

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
        initSwipeRefresh()
        viewModel.updatePlaylistFromCache()

        // 初始化用户状态管理器
        userStateManager.init()
    }

    override fun onResume() {
        super.onResume()
        
        // 智能刷新用户信息
        if (userService.isLogin()) {
            val currentTime = System.currentTimeMillis()
            val shouldAutoRefresh = when {
                // 首次进入（从未刷新过）
                lastAutoRefreshTime == 0L -> true
                // 超过间隔时间
                currentTime - lastAutoRefreshTime > AUTO_REFRESH_INTERVAL -> true
                // 用户信息为空（可能登录状态刚变化）
                userStateManager.userDetail.value == null -> true
                // 其他情况不自动刷新
                else -> false
            }
            
            if (shouldAutoRefresh) {
                android.util.Log.d("MineFragment", "触发自动刷新用户信息")
                refreshUserInfo()
                lastAutoRefreshTime = currentTime
            } else {
                android.util.Log.d("MineFragment", "跳过自动刷新，距离上次刷新${(currentTime - lastAutoRefreshTime) / 1000}秒")
            }
        }
        
        // 刷新播放列表（保持原有逻辑）
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
                // 强制重新加载头像，确保显示最新头像
                if (profile?.avatarUrl != null) {
                    com.bumptech.glide.Glide.with(this@MineFragment)
                        .load(profile.avatarUrl)
                        .placeholder(me.ckn.music.R.drawable.ic_launcher_round)
                        .error(me.ckn.music.R.drawable.ic_launcher_round)
                        .skipMemoryCache(true) // 跳过内存缓存
                        .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE) // 跳过磁盘缓存
                        .circleCrop()
                        .into(viewBinding.ivAvatar)
                } else {
                    viewBinding.ivAvatar.setImageResource(me.ckn.music.R.drawable.ic_launcher_round)
                }
                
                viewBinding.tvNickName.text = profile?.nickname
                
                // 设置头像区域点击事件
                viewBinding.flProfile.setOnClickListener {
                    if (ApiDomainDialog.checkApiDomain(requireActivity())) {
                        if (userService.isLogin().not()) {
                            // 未登录时跳转到登录页面
                            CRouter.with(requireActivity())
                                .url(RoutePath.LOGIN)
                                .start()
                        } else {
                            // 已登录时显示用户操作菜单
                            showUserMenu()
                        }
                    }
                }

                // 用户登录后立即更新VIP信息
                if (profile != null && userService.isLogin()) {
                    updateVipInfo()
                    // 立即刷新用户详细信息
                    lifecycleScope.launch {
                        userStateManager.refreshUserState(forceRefresh = true)
                    }
                }
            }
        }
    }

    /**
     * 显示用户操作菜单
     * 包含退出登录等功能
     */
    private fun showUserMenu() {
        val dialog = BottomItemsDialogBuilder(requireActivity())
            .items(listOf("退出登录"))
            .onClickListener { dialog, which ->
                when (which) {
                    0 -> showLogoutConfirmDialog()
                }
            }
            .build()

        // 应用全屏沉浸式模式
        dialog.enableImmersiveMode()
        dialog.show()
    }

    /**
     * 显示退出登录确认对话框
     */
    private fun showLogoutConfirmDialog() {
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireActivity())
            .setTitle("退出登录")
            .setMessage("确定要退出登录吗？")
            .setPositiveButton("确定") { _, _ ->
                performLogout()
            }
            .setNegativeButton("取消", null)
            .create()

        // 应用全屏沉浸式模式
        dialog.enableImmersiveMode()
        dialog.show()
    }

    /**
     * 执行退出登录操作
     */
    private fun performLogout() {
        lifecycleScope.launch {
            try {
                showLoading()
                userService.logout()
                dismissLoading()
                
                toast("已退出登录")
                // 跳转到登录页面
                CRouter.with(requireActivity())
                    .url(RoutePath.LOGIN)
                    .start()
            } catch (e: Exception) {
                dismissLoading()
                toast("退出登录异常：${e.message}")
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

        // 监听用户详情变化
        lifecycleScope.launch {
            userStateManager.userDetail.collectLatest { userDetail ->
                if (userDetail != null) {
                    updateUserInfo(userDetail)
                }
            }
        }

        // 监听用户详情变化，更新VIP标签
        lifecycleScope.launch {
            userStateManager.userDetail.collectLatest { userDetail ->
                if (userDetail != null) {
                    updateVipLabelFromUserDetail(userDetail)
                } else {
                    viewBinding.tvVipLabel.visibility = android.view.View.GONE
                }
            }
        }

        // 监听用户详情变化，更新等级标签
        lifecycleScope.launch {
            userStateManager.userDetail.collectLatest { userDetail ->
                if (userDetail != null) {
                    updateLevelLabelFromUserDetail(userDetail)
                } else {
                    viewBinding.tvLevelLabel.visibility = android.view.View.GONE
                }
            }
        }
    }

    /**
     * 初始化下拉刷新功能
     */
    private fun initSwipeRefresh() {
        viewBinding.swipeRefreshLayout.apply {
            // 设置刷新颜色
            setColorSchemeResources(
                me.ckn.music.R.color.common_theme_color,
                me.ckn.music.R.color.red_500,
                me.ckn.music.R.color.red_700
            )

            // 设置刷新监听
            setOnRefreshListener {
                refreshUserInfo(isManualRefresh = true)
            }
        }

        // 监听刷新状态
        lifecycleScope.launch {
            userStateManager.isRefreshing.collectLatest { isRefreshing ->
                viewBinding.swipeRefreshLayout.isRefreshing = isRefreshing
            }
        }
    }

    /**
     * 刷新用户信息
     * 完整刷新用户头像、昵称、VIP状态、等级等所有信息
     * @param isManualRefresh 是否为手动刷新（如下拉刷新），手动刷新会忽略时间间隔限制
     */
    private fun refreshUserInfo(isManualRefresh: Boolean = false) {
        if (!userService.isLogin()) {
            return
        }
        
        // 更新刷新时间（只有在实际执行刷新时才更新）
        if (isManualRefresh) {
            lastAutoRefreshTime = System.currentTimeMillis()
        }
        
        lifecycleScope.launch {
            try {
                // 强制刷新用户状态，确保获取最新信息
                val result = userStateManager.refreshUserState(forceRefresh = true)
                when (result) {
                    is UserStateManager.RefreshResult.Success -> {
                        // 刷新成功后，手动触发用户信息更新
                        userStateManager.userDetail.value?.let { userDetail ->
                            updateUserInfo(userDetail)
                            updateVipLabelFromUserDetail(userDetail)
                            updateLevelLabelFromUserDetail(userDetail)
                        }
                        
                        // 同时刷新播放列表
                        viewModel.updatePlaylist()
                        
                        // 刷新用户基本Profile信息
                        refreshUserProfile()
                        
                        android.util.Log.d("MineFragment", "用户信息刷新完成${if (isManualRefresh) "（手动刷新）" else "（自动刷新）"}")
                    }
                    is UserStateManager.RefreshResult.NotLoggedIn -> {
                        // 用户未登录，清空显示
                        clearUserDisplay()
                    }
                    is UserStateManager.RefreshResult.Failed -> {
                        // 刷新失败，显示错误信息（可选）
                        if (isManualRefresh) {
                            // 手动刷新失败时提示用户
                            android.widget.Toast.makeText(requireContext(), "刷新失败，请稍后重试", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                // 异常处理，不显示错误给用户，避免干扰体验
                android.util.Log.e("MineFragment", "刷新用户信息异常", e)
                if (isManualRefresh) {
                    android.widget.Toast.makeText(requireContext(), "刷新异常，请稍后重试", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * 刷新用户基本Profile信息
     * 确保头像和昵称是最新的
     */
    private fun refreshUserProfile() {
        lifecycleScope.launch {
            try {
                // 重新获取用户Profile信息
                val currentProfile = userService.profile.value
                if (currentProfile != null) {
                    // 强制重新加载头像
                    if (currentProfile.avatarUrl != null) {
                        com.bumptech.glide.Glide.with(this@MineFragment)
                            .load(currentProfile.avatarUrl)
                            .placeholder(me.ckn.music.R.drawable.ic_launcher_round)
                            .error(me.ckn.music.R.drawable.ic_launcher_round)
                            .skipMemoryCache(true) // 跳过内存缓存
                            .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE) // 跳过磁盘缓存
                            .circleCrop()
                            .into(viewBinding.ivAvatar)
                    }
                    
                    // 更新昵称
                    viewBinding.tvNickName.text = currentProfile.nickname
                    
                    android.util.Log.d("MineFragment", "用户Profile刷新完成: ${currentProfile.nickname}")
                }
            } catch (e: Exception) {
                android.util.Log.e("MineFragment", "刷新用户Profile异常", e)
            }
        }
    }

    /**
     * 清空用户显示信息
     */
    private fun clearUserDisplay() {
        viewBinding.ivAvatar.setImageResource(R.drawable.ic_launcher_round)
        viewBinding.tvNickName.text = "未登录"
        viewBinding.tvVipLabel.visibility = android.view.View.GONE
        viewBinding.tvLevelLabel.visibility = android.view.View.GONE
    }

    /**
     * 更新用户信息显示
     * 根据用户详情数据更新头像、昵称等基本信息
     */
    private fun updateUserInfo(userDetail: me.ckn.music.account.bean.UserDetailData) {
        userDetail.profile?.let { profile ->
            // 更新用户昵称
            viewBinding.tvNickName.text = profile.nickname

            // 强制重新加载用户头像，清除缓存确保显示最新头像
            profile.avatarUrl?.let { avatarUrl ->
                // 使用Glide强制重新加载头像，跳过缓存
                com.bumptech.glide.Glide.with(this)
                    .load(avatarUrl)
                    .placeholder(me.ckn.music.R.drawable.ic_launcher_round)
                    .error(me.ckn.music.R.drawable.ic_launcher_round)
                    .skipMemoryCache(true) // 跳过内存缓存
                    .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE) // 跳过磁盘缓存
                    .circleCrop()
                    .into(viewBinding.ivAvatar)
                    
                android.util.Log.d("MineFragment", "强制重新加载头像: $avatarUrl")
            }

            android.util.Log.d("MineFragment", "用户信息更新完成: 昵称=${profile.nickname}, 头像=${profile.avatarUrl}")
            android.util.Log.d("MineFragment", "用户详情: 等级=${userDetail.level}, 听歌数=${userDetail.listenSongs}")
        }
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
     * 基于用户详情更新VIP标签显示
     * @param userDetail 用户详情数据
     */
    private fun updateVipLabelFromUserDetail(userDetail: me.ckn.music.account.bean.UserDetailData) {
        userDetail.profile?.let { profile ->
            val isVip = profile.vipType > 0
            val vipTypeName = when (profile.vipType) {
                0 -> ""
                1 -> "VIP"
                11 -> "黑胶VIP"
                else -> if (profile.vipType > 0) "VIP" else ""
            }

            viewBinding.tvVipLabel.apply {
                if (isVip && vipTypeName.isNotEmpty()) {
                    visibility = android.view.View.VISIBLE
                    text = vipTypeName
                    setTextColor(resources.getColor(me.ckn.music.R.color.white, null))
                    // VIP用户显示现代化金色渐变标签
                    setBackgroundResource(me.ckn.music.R.drawable.bg_vip_label_modern)
                    // 强制刷新视图
                    invalidate()
                    requestLayout()

                    android.util.Log.d("MineFragment", "VIP标签更新完成: vipType=${profile.vipType}, 显示文本=$vipTypeName")
                } else {
                    visibility = android.view.View.GONE
                    android.util.Log.d("MineFragment", "非VIP用户，隐藏VIP标签: vipType=${profile.vipType}")
                }
            }
        }
    }

    /**
     * 更新VIP标签显示（保留原方法以兼容）
     * @param vipInfo VIP信息数据
     */
    private fun updateVipLabel(vipInfo: VipInfoData) {
        val vipData = vipInfo.data
        viewBinding.tvVipLabel.apply {
            visibility = android.view.View.VISIBLE
            text = "VIP"
            setTextColor(resources.getColor(me.ckn.music.R.color.white, null))

            if (vipData != null && vipData.isVip) {
                // VIP用户显示金色标签
                setBackgroundResource(me.ckn.music.R.drawable.bg_vip_label_gold)
            } else {
                // 非VIP用户显示灰色标签
                setBackgroundResource(me.ckn.music.R.drawable.bg_vip_label_gray)
            }
        }
    }

    /**
     * 基于用户详情更新等级标签显示
     * @param userDetail 用户详情数据
     */
    private fun updateLevelLabelFromUserDetail(userDetail: me.ckn.music.account.bean.UserDetailData) {
        val level = userDetail.level ?: 0
        // 修改逻辑：只要有等级信息就显示，包括0级
        if (level >= 0) {
            viewBinding.tvLevelLabel.apply {
                visibility = android.view.View.VISIBLE
                text = "Lv.$level"
                setBackgroundResource(me.ckn.music.R.drawable.bg_level_label_modern)
                setTextColor(resources.getColor(me.ckn.music.R.color.white, null))
                // 强制刷新视图
                invalidate()
                requestLayout()

                android.util.Log.d("MineFragment", "等级标签更新完成: level=$level")
            }
        } else {
            viewBinding.tvLevelLabel.visibility = android.view.View.GONE
            android.util.Log.d("MineFragment", "等级信息无效，隐藏等级标签")
        }
    }

    /**
     * 更新等级标签显示（保留原方法以兼容）
     * @param levelData 用户等级数据
     */
    private fun updateLevelLabel(levelData: UserLevelData) {
        val levelInfo = levelData.data
        if (levelInfo != null && levelInfo.level > 0) {
            viewBinding.tvLevelLabel.apply {
                visibility = android.view.View.VISIBLE
                text = "Lv.${levelInfo.level}"
                setBackgroundResource(me.ckn.music.R.drawable.bg_level_label)
                setTextColor(resources.getColor(me.ckn.music.R.color.white, null))
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