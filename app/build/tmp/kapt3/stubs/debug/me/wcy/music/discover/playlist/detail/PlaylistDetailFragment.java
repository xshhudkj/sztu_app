package me.wcy.music.discover.playlist.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.blankj.utilcode.util.SizeUtils;
import dagger.hilt.android.AndroidEntryPoint;
import me.wcy.music.R;
import me.wcy.music.account.service.UserService;
import me.wcy.music.common.BaseMusicFragment;
import me.wcy.music.common.OnItemClickListener2;
import me.wcy.music.common.bean.SongData;
import me.wcy.music.common.dialog.songmenu.SongMoreMenuDialog;
import me.wcy.music.common.dialog.songmenu.items.AlbumMenuItem;
import me.wcy.music.common.dialog.songmenu.items.ArtistMenuItem;
import me.wcy.music.common.dialog.songmenu.items.CollectMenuItem;
import me.wcy.music.common.dialog.songmenu.items.CommentMenuItem;
import me.wcy.music.common.dialog.songmenu.items.DeletePlaylistSongMenuItem;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.databinding.FragmentPlaylistDetailBinding;
import me.wcy.music.databinding.ItemPlaylistTagBinding;
import me.wcy.music.discover.playlist.detail.item.PlaylistSongItemBinder;
import me.wcy.music.discover.playlist.detail.viewmodel.PlaylistViewModel;
import me.wcy.music.service.PlayerController;
import me.wcy.music.utils.ConvertUtils;
import me.wcy.radapter3.RAdapter;
import me.wcy.router.CRouter;
import me.wcy.router.annotation.Route;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/9/22.
 */
@me.wcy.router.annotation.Route(value = "/playlist/detail")
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\"\u001a\u00020\u000bH\u0014J\b\u0010#\u001a\u00020\u000bH\u0014J\b\u0010$\u001a\u00020%H\u0002J\b\u0010&\u001a\u00020%H\u0002J\b\u0010\'\u001a\u00020%H\u0002J\b\u0010(\u001a\u00020)H\u0014J\b\u0010*\u001a\u00020%H\u0002J\b\u0010+\u001a\u00020%H\u0014J\b\u0010,\u001a\u00020%H\u0014J\b\u0010-\u001a\u00020%H\u0002R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\f\u001a\u00020\r8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0012\u001a\u00020\u00138\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0018\u001a\u00020\u00198BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001c\u0010\t\u001a\u0004\b\u001a\u0010\u001bR\u001b\u0010\u001d\u001a\u00020\u001e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b!\u0010\t\u001a\u0004\b\u001f\u0010 \u00a8\u0006."}, d2 = {"Lme/wcy/music/discover/playlist/detail/PlaylistDetailFragment;", "Lme/wcy/music/common/BaseMusicFragment;", "()V", "adapter", "Lme/wcy/radapter3/RAdapter;", "Lme/wcy/music/common/bean/SongData;", "getAdapter", "()Lme/wcy/radapter3/RAdapter;", "adapter$delegate", "Lkotlin/Lazy;", "collectMenu", "Landroid/view/View;", "playerController", "Lme/wcy/music/service/PlayerController;", "getPlayerController", "()Lme/wcy/music/service/PlayerController;", "setPlayerController", "(Lme/wcy/music/service/PlayerController;)V", "userService", "Lme/wcy/music/account/service/UserService;", "getUserService", "()Lme/wcy/music/account/service/UserService;", "setUserService", "(Lme/wcy/music/account/service/UserService;)V", "viewBinding", "Lme/wcy/music/databinding/FragmentPlaylistDetailBinding;", "getViewBinding", "()Lme/wcy/music/databinding/FragmentPlaylistDetailBinding;", "viewBinding$delegate", "viewModel", "Lme/wcy/music/discover/playlist/detail/viewmodel/PlaylistViewModel;", "getViewModel", "()Lme/wcy/music/discover/playlist/detail/viewmodel/PlaylistViewModel;", "viewModel$delegate", "getLoadSirTarget", "getRootView", "initPlaylistInfo", "", "initSongList", "initTitle", "isUseLoadSir", "", "loadData", "onLazyCreate", "onReload", "updateCollectState", "app_debug"})
public final class PlaylistDetailFragment extends me.wcy.music.common.BaseMusicFragment {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewBinding$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy adapter$delegate = null;
    @org.jetbrains.annotations.Nullable()
    private android.view.View collectMenu;
    @javax.inject.Inject()
    public me.wcy.music.account.service.UserService userService;
    @javax.inject.Inject()
    public me.wcy.music.service.PlayerController playerController;
    
    public PlaylistDetailFragment() {
        super();
    }
    
    private final me.wcy.music.databinding.FragmentPlaylistDetailBinding getViewBinding() {
        return null;
    }
    
    private final me.wcy.music.discover.playlist.detail.viewmodel.PlaylistViewModel getViewModel() {
        return null;
    }
    
    private final me.wcy.radapter3.RAdapter<me.wcy.music.common.bean.SongData> getAdapter() {
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
    
    private final void initTitle() {
    }
    
    private final void initPlaylistInfo() {
    }
    
    private final void initSongList() {
    }
    
    private final void updateCollectState() {
    }
}