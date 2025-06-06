package me.wcy.music.mine.home;

import android.annotation.SuppressLint;
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
import me.wcy.music.common.bean.VipInfoData;
import me.wcy.music.common.bean.UserLevelData;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/8/21.
 */
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001%B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0017H\u0003J\b\u0010\u0019\u001a\u00020\u0017H\u0002J\b\u0010\u001a\u001a\u00020\u0017H\u0002J\b\u0010\u001b\u001a\u00020\u0017H\u0002J\b\u0010\u001c\u001a\u00020\u0017H\u0014J\b\u0010\u001d\u001a\u00020\u0017H\u0016J\u0010\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020\u0017H\u0002J\u0010\u0010\"\u001a\u00020\u00172\u0006\u0010#\u001a\u00020$H\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006&"}, d2 = {"Lme/wcy/music/mine/home/MineFragment;", "Lme/wcy/music/common/BaseMusicFragment;", "()V", "userService", "Lme/wcy/music/account/service/UserService;", "getUserService", "()Lme/wcy/music/account/service/UserService;", "setUserService", "(Lme/wcy/music/account/service/UserService;)V", "viewBinding", "Lme/wcy/music/databinding/FragmentMineBinding;", "getViewBinding", "()Lme/wcy/music/databinding/FragmentMineBinding;", "viewBinding$delegate", "Lkotlin/Lazy;", "viewModel", "Lme/wcy/music/mine/home/viewmodel/MineViewModel;", "getViewModel", "()Lme/wcy/music/mine/home/viewmodel/MineViewModel;", "viewModel$delegate", "getRootView", "Landroid/view/View;", "initLocalMusic", "", "initPlaylist", "initProfile", "initTitle", "initVipInfo", "onLazyCreate", "onResume", "updateLevelLabel", "levelData", "Lme/wcy/music/common/bean/UserLevelData;", "updateVipInfo", "updateVipLabel", "vipInfo", "Lme/wcy/music/common/bean/VipInfoData;", "ItemClickListener", "app_debug"})
public final class MineFragment extends me.wcy.music.common.BaseMusicFragment {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewBinding$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @javax.inject.Inject()
    public me.wcy.music.account.service.UserService userService;
    
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
     * 初始化VIP信息和用户等级显示
     * 获取用户VIP状态和等级信息，在用户名右侧显示相应标签
     */
    private final void initVipInfo() {
    }
    
    /**
     * 更新VIP信息显示
     * 获取并显示用户的VIP状态和等级信息
     */
    private final void updateVipInfo() {
    }
    
    /**
     * 更新VIP标签显示
     * @param vipInfo VIP信息数据
     */
    private final void updateVipLabel(me.wcy.music.common.bean.VipInfoData vipInfo) {
    }
    
    /**
     * 更新等级标签显示
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