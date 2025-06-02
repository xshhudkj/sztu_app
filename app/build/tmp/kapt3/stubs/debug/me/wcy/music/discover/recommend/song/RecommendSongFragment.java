package me.wcy.music.discover.recommend.song;

import android.view.View;
import dagger.hilt.android.AndroidEntryPoint;
import me.wcy.music.R;
import me.wcy.music.common.BaseMusicFragment;
import me.wcy.music.common.OnItemClickListener2;
import me.wcy.music.common.bean.SongData;
import me.wcy.music.common.dialog.songmenu.SongMoreMenuDialog;
import me.wcy.music.common.dialog.songmenu.items.AlbumMenuItem;
import me.wcy.music.common.dialog.songmenu.items.ArtistMenuItem;
import me.wcy.music.common.dialog.songmenu.items.CollectMenuItem;
import me.wcy.music.common.dialog.songmenu.items.CommentMenuItem;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.databinding.FragmentRecommendSongBinding;
import me.wcy.music.discover.DiscoverApi;
import me.wcy.music.discover.recommend.song.item.RecommendSongItemBinder;
import me.wcy.music.service.PlayerController;
import me.wcy.radapter3.RAdapter;
import me.wcy.router.CRouter;
import me.wcy.router.annotation.Route;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/9/15.
 */
@me.wcy.router.annotation.Route(value = "/recommend_song", needLogin = true)
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0014J\b\u0010\u0017\u001a\u00020\u0016H\u0014J\b\u0010\u0018\u001a\u00020\u0019H\u0014J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u001bH\u0014J\b\u0010\u001d\u001a\u00020\u001bH\u0014R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\n\u001a\u00020\u000b8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001b\u0010\u0010\u001a\u00020\u00118BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0014\u0010\t\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001e"}, d2 = {"Lme/wcy/music/discover/recommend/song/RecommendSongFragment;", "Lme/wcy/music/common/BaseMusicFragment;", "()V", "adapter", "Lme/wcy/radapter3/RAdapter;", "Lme/wcy/music/common/bean/SongData;", "getAdapter", "()Lme/wcy/radapter3/RAdapter;", "adapter$delegate", "Lkotlin/Lazy;", "playerController", "Lme/wcy/music/service/PlayerController;", "getPlayerController", "()Lme/wcy/music/service/PlayerController;", "setPlayerController", "(Lme/wcy/music/service/PlayerController;)V", "viewBinding", "Lme/wcy/music/databinding/FragmentRecommendSongBinding;", "getViewBinding", "()Lme/wcy/music/databinding/FragmentRecommendSongBinding;", "viewBinding$delegate", "getLoadSirTarget", "Landroid/view/View;", "getRootView", "isUseLoadSir", "", "loadData", "", "onLazyCreate", "onReload", "app_debug"})
public final class RecommendSongFragment extends me.wcy.music.common.BaseMusicFragment {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewBinding$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy adapter$delegate = null;
    @javax.inject.Inject()
    public me.wcy.music.service.PlayerController playerController;
    
    public RecommendSongFragment() {
        super();
    }
    
    private final me.wcy.music.databinding.FragmentRecommendSongBinding getViewBinding() {
        return null;
    }
    
    private final me.wcy.radapter3.RAdapter<me.wcy.music.common.bean.SongData> getAdapter() {
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