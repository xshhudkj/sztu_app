package me.wcy.music.mine.home;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import com.blankj.utilcode.util.SizeUtils;
import dagger.hilt.android.AndroidEntryPoint;
import me.wcy.music.R;
import me.wcy.music.account.service.UserService;
import me.wcy.music.common.ApiDomainDialog;
import me.wcy.music.common.BaseMusicFragment;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.databinding.FragmentMineBinding;
import me.wcy.music.main.MainActivity;
import me.wcy.music.mine.home.viewmodel.MineViewModel;
import me.wcy.music.mine.playlist.UserPlaylistItemBinder;
import me.wcy.radapter3.RAdapter;
import me.wcy.router.CRouter;
import top.wangchenyan.common.widget.decoration.SpacingDecoration;
import top.wangchenyan.common.widget.dialog.BottomItemsDialogBuilder;
import me.wcy.music.account.VipApi;
import me.wcy.music.account.service.UserStateManager;
import me.wcy.music.common.bean.VipInfoData;
import me.wcy.music.common.bean.UserLevelData;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/8/21.
 */
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001:\u0001<B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020 H\u0014J\b\u0010!\u001a\u00020\u001eH\u0002J\b\u0010\"\u001a\u00020\u001eH\u0003J\b\u0010#\u001a\u00020\u001eH\u0002J\b\u0010$\u001a\u00020\u001eH\u0002J\b\u0010%\u001a\u00020\u001eH\u0002J\b\u0010&\u001a\u00020\u001eH\u0002J\b\u0010\'\u001a\u00020\u001eH\u0014J\b\u0010(\u001a\u00020\u001eH\u0016J\b\u0010)\u001a\u00020\u001eH\u0002J\u0012\u0010*\u001a\u00020\u001e2\b\b\u0002\u0010+\u001a\u00020,H\u0002J\b\u0010-\u001a\u00020\u001eH\u0002J\b\u0010.\u001a\u00020\u001eH\u0002J\b\u0010/\u001a\u00020\u001eH\u0002J\u0010\u00100\u001a\u00020\u001e2\u0006\u00101\u001a\u000202H\u0002J\u0010\u00103\u001a\u00020\u001e2\u0006\u00104\u001a\u000205H\u0002J\u0010\u00106\u001a\u00020\u001e2\u0006\u00104\u001a\u000205H\u0002J\b\u00107\u001a\u00020\u001eH\u0002J\u0010\u00108\u001a\u00020\u001e2\u0006\u00109\u001a\u00020:H\u0002J\u0010\u0010;\u001a\u00020\u001e2\u0006\u00104\u001a\u000205H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\f\u001a\u00020\r8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u001b\u0010\u0018\u001a\u00020\u00198BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0017\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006="}, d2 = {"Lme/wcy/music/mine/home/MineFragment;", "Lme/wcy/music/common/BaseMusicFragment;", "()V", "AUTO_REFRESH_INTERVAL", "", "lastAutoRefreshTime", "userService", "Lme/wcy/music/account/service/UserService;", "getUserService", "()Lme/wcy/music/account/service/UserService;", "setUserService", "(Lme/wcy/music/account/service/UserService;)V", "userStateManager", "Lme/wcy/music/account/service/UserStateManager;", "getUserStateManager", "()Lme/wcy/music/account/service/UserStateManager;", "setUserStateManager", "(Lme/wcy/music/account/service/UserStateManager;)V", "viewBinding", "Lme/wcy/music/databinding/FragmentMineBinding;", "getViewBinding", "()Lme/wcy/music/databinding/FragmentMineBinding;", "viewBinding$delegate", "Lkotlin/Lazy;", "viewModel", "Lme/wcy/music/mine/home/viewmodel/MineViewModel;", "getViewModel", "()Lme/wcy/music/mine/home/viewmodel/MineViewModel;", "viewModel$delegate", "clearUserDisplay", "", "getRootView", "Landroid/view/View;", "initLocalMusic", "initPlaylist", "initProfile", "initSwipeRefresh", "initTitle", "initVipInfo", "onLazyCreate", "onResume", "performLogout", "refreshUserInfo", "isManualRefresh", "", "refreshUserProfile", "showLogoutConfirmDialog", "showUserMenu", "updateLevelLabel", "levelData", "Lme/wcy/music/common/bean/UserLevelData;", "updateLevelLabelFromUserDetail", "userDetail", "Lme/wcy/music/account/bean/UserDetailData;", "updateUserInfo", "updateVipInfo", "updateVipLabel", "vipInfo", "Lme/wcy/music/common/bean/VipInfoData;", "updateVipLabelFromUserDetail", "ItemClickListener", "app_debug"})
public final class MineFragment extends me.wcy.music.common.BaseMusicFragment {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewBinding$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @javax.inject.Inject()
    public me.wcy.music.account.service.UserService userService;
    @javax.inject.Inject()
    public me.wcy.music.account.service.UserStateManager userStateManager;
    private long lastAutoRefreshTime = 0L;
    private final long AUTO_REFRESH_INTERVAL = 300000L;
    
    public MineFragment() {
        super();
    }
    
    private final me.wcy.music.databinding.FragmentMineBinding getViewBinding() {
        return null;
    }
    
    private final me.wcy.music.mine.home.viewmodel.MineViewModel getViewModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.account.service.UserService getUserService() {
        return null;
    }
    
    public final void setUserService(@org.jetbrains.annotations.NotNull()
    me.wcy.music.account.service.UserService p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.account.service.UserStateManager getUserStateManager() {
        return null;
    }
    
    public final void setUserStateManager(@org.jetbrains.annotations.NotNull()
    me.wcy.music.account.service.UserStateManager p0) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected android.view.View getRootView() {
        return null;
    }
    
    @java.lang.Override()
    protected void onLazyCreate() {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    private final void initTitle() {
    }
    
    private final void initProfile() {
    }
    
    /**
     * 显示用户操作菜单
     * 包含退出登录等功能
     */
    private final void showUserMenu() {
    }
    
    /**
     * 显示退出登录确认对话框
     */
    private final void showLogoutConfirmDialog() {
    }
    
    /**
     * 执行退出登录操作
     */
    private final void performLogout() {
    }
    
    /**
     * 初始化VIP信息和用户等级显示
     * 获取用户VIP状态和等级信息，在用户名右侧显示相应标签
     */
    private final void initVipInfo() {
    }
    
    /**
     * 初始化下拉刷新功能
     */
    private final void initSwipeRefresh() {
    }
    
    /**
     * 刷新用户信息
     * 完整刷新用户头像、昵称、VIP状态、等级等所有信息
     * @param isManualRefresh 是否为手动刷新（如下拉刷新），手动刷新会忽略时间间隔限制
     */
    private final void refreshUserInfo(boolean isManualRefresh) {
    }
    
    /**
     * 刷新用户基本Profile信息
     * 确保头像和昵称是最新的
     */
    private final void refreshUserProfile() {
    }
    
    /**
     * 清空用户显示信息
     */
    private final void clearUserDisplay() {
    }
    
    /**
     * 更新用户信息显示
     * 根据用户详情数据更新头像、昵称等基本信息
     */
    private final void updateUserInfo(me.wcy.music.account.bean.UserDetailData userDetail) {
    }
    
    /**
     * 更新VIP信息显示
     * 获取并显示用户的VIP状态和等级信息
     */
    private final void updateVipInfo() {
    }
    
    /**
     * 基于用户详情更新VIP标签显示
     * @param userDetail 用户详情数据
     */
    private final void updateVipLabelFromUserDetail(me.wcy.music.account.bean.UserDetailData userDetail) {
    }
    
    /**
     * 更新VIP标签显示（保留原方法以兼容）
     * @param vipInfo VIP信息数据
     */
    private final void updateVipLabel(me.wcy.music.common.bean.VipInfoData vipInfo) {
    }
    
    /**
     * 基于用户详情更新等级标签显示
     * @param userDetail 用户详情数据
     */
    private final void updateLevelLabelFromUserDetail(me.wcy.music.account.bean.UserDetailData userDetail) {
    }
    
    /**
     * 更新等级标签显示（保留原方法以兼容）
     * @param levelData 用户等级数据
     */
    private final void updateLevelLabel(me.wcy.music.common.bean.UserLevelData levelData) {
    }
    
    private final void initLocalMusic() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void initPlaylist() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lme/wcy/music/mine/home/MineFragment$ItemClickListener;", "Lme/wcy/music/mine/playlist/UserPlaylistItemBinder$OnItemClickListener;", "isMine", "", "isLike", "(Lme/wcy/music/mine/home/MineFragment;ZZ)V", "onItemClick", "", "item", "Lme/wcy/music/common/bean/PlaylistData;", "onMoreClick", "app_debug"})
    public final class ItemClickListener implements me.wcy.music.mine.playlist.UserPlaylistItemBinder.OnItemClickListener {
        private final boolean isMine = false;
        private final boolean isLike = false;
        
        public ItemClickListener(boolean isMine, boolean isLike) {
            super();
        }
        
        @java.lang.Override()
        public void onItemClick(@org.jetbrains.annotations.NotNull()
        me.wcy.music.common.bean.PlaylistData item) {
        }
        
        @java.lang.Override()
        public void onMoreClick(@org.jetbrains.annotations.NotNull()
        me.wcy.music.common.bean.PlaylistData item) {
        }
    }
}