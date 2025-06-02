package me.wcy.music.mine.local;

import android.view.View;
import com.blankj.utilcode.util.ConvertUtils;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.Dispatchers;
import me.wcy.music.R;
import me.wcy.music.common.BaseMusicFragment;
import me.wcy.music.common.OnItemClickListener2;
import me.wcy.music.common.dialog.songmenu.SimpleMenuItem;
import me.wcy.music.common.dialog.songmenu.SongMoreMenuDialog;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.databinding.FragmentLocalMusicBinding;
import me.wcy.music.service.PlayerController;
import me.wcy.music.storage.db.entity.SongEntity;
import me.wcy.music.utils.TimeUtils;
import me.wcy.radapter3.RAdapter;
import me.wcy.router.CRouter;
import me.wcy.router.annotation.Route;
import top.wangchenyan.common.permission.Permissioner;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/8/30.
 */
@me.wcy.router.annotation.Route(value = "/local_music")
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0014J\b\u0010\u001c\u001a\u00020\u001bH\u0014J\b\u0010\u001d\u001a\u00020\u001eH\u0014J\b\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020 H\u0014J\b\u0010\"\u001a\u00020 H\u0014R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\t\u001a\u0004\b\f\u0010\rR\u001e\u0010\u000f\u001a\u00020\u00108\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0015\u001a\u00020\u00168BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0019\u0010\t\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006#"}, d2 = {"Lme/wcy/music/mine/local/LocalMusicFragment;", "Lme/wcy/music/common/BaseMusicFragment;", "()V", "adapter", "Lme/wcy/radapter3/RAdapter;", "Lme/wcy/music/storage/db/entity/SongEntity;", "getAdapter", "()Lme/wcy/radapter3/RAdapter;", "adapter$delegate", "Lkotlin/Lazy;", "localMusicLoader", "Lme/wcy/music/mine/local/LocalMusicLoader;", "getLocalMusicLoader", "()Lme/wcy/music/mine/local/LocalMusicLoader;", "localMusicLoader$delegate", "playerController", "Lme/wcy/music/service/PlayerController;", "getPlayerController", "()Lme/wcy/music/service/PlayerController;", "setPlayerController", "(Lme/wcy/music/service/PlayerController;)V", "viewBinding", "Lme/wcy/music/databinding/FragmentLocalMusicBinding;", "getViewBinding", "()Lme/wcy/music/databinding/FragmentLocalMusicBinding;", "viewBinding$delegate", "getLoadSirTarget", "Landroid/view/View;", "getRootView", "isUseLoadSir", "", "loadData", "", "onLazyCreate", "onReload", "app_debug"})
public final class LocalMusicFragment extends me.wcy.music.common.BaseMusicFragment {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewBinding$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy localMusicLoader$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy adapter$delegate = null;
    @javax.inject.Inject()
    public me.wcy.music.service.PlayerController playerController;
    
    public LocalMusicFragment() {
        super();
    }
    
    private final me.wcy.music.databinding.FragmentLocalMusicBinding getViewBinding() {
        return null;
    }
    
    private final me.wcy.music.mine.local.LocalMusicLoader getLocalMusicLoader() {
        return null;
    }
    
    private final me.wcy.radapter3.RAdapter<me.wcy.music.storage.db.entity.SongEntity> getAdapter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.service.PlayerController getPlayerController() {
        return null;
    }
    
    public final void setPlayerController(@org.jetbrains.annotations.NotNull()
    me.wcy.music.service.PlayerController p0) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected android.view.View getRootView() {
        return null;
    }
    
    @java.lang.Override()
    protected boolean isUseLoadSir() {
        return false;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected android.view.View getLoadSirTarget() {
        return null;
    }
    
    @java.lang.Override()
    protected void onReload() {
    }
    
    @java.lang.Override()
    protected void onLazyCreate() {
    }
    
    private final void loadData() {
    }
}