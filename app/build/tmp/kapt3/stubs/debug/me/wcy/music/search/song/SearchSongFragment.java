package me.wcy.music.search.song;

import dagger.hilt.android.AndroidEntryPoint;
import me.wcy.music.common.OnItemClickListener2;
import me.wcy.music.common.SimpleMusicRefreshFragment;
import me.wcy.music.common.bean.SongData;
import me.wcy.music.common.dialog.songmenu.SongMoreMenuDialog;
import me.wcy.music.common.dialog.songmenu.items.AlbumMenuItem;
import me.wcy.music.common.dialog.songmenu.items.ArtistMenuItem;
import me.wcy.music.common.dialog.songmenu.items.CollectMenuItem;
import me.wcy.music.common.dialog.songmenu.items.CommentMenuItem;
import me.wcy.music.consts.Consts;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.search.SearchApi;
import me.wcy.music.search.SearchViewModel;
import me.wcy.music.service.PlayerController;
import me.wcy.radapter3.RAdapter;
import me.wcy.router.CRouter;
import top.wangchenyan.common.model.CommonResult;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/9/20.
 */
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\"\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00170\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0096@\u00a2\u0006\u0002\u0010\u001aJ\u0016\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020 H\u0014J\b\u0010!\u001a\u00020 H\u0014J\b\u0010\"\u001a\u00020\u001cH\u0014R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\n\u001a\u00020\u000b8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001b\u0010\u0010\u001a\u00020\u00118BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0014\u0010\t\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006#"}, d2 = {"Lme/wcy/music/search/song/SearchSongFragment;", "Lme/wcy/music/common/SimpleMusicRefreshFragment;", "Lme/wcy/music/common/bean/SongData;", "()V", "itemBinder", "Lme/wcy/music/search/song/SearchSongItemBinder;", "getItemBinder", "()Lme/wcy/music/search/song/SearchSongItemBinder;", "itemBinder$delegate", "Lkotlin/Lazy;", "playerController", "Lme/wcy/music/service/PlayerController;", "getPlayerController", "()Lme/wcy/music/service/PlayerController;", "setPlayerController", "(Lme/wcy/music/service/PlayerController;)V", "viewModel", "Lme/wcy/music/search/SearchViewModel;", "getViewModel", "()Lme/wcy/music/search/SearchViewModel;", "viewModel$delegate", "getData", "Ltop/wangchenyan/common/model/CommonResult;", "", "page", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "initAdapter", "", "adapter", "Lme/wcy/radapter3/RAdapter;", "isRefreshEnabled", "", "isShowTitle", "onLazyCreate", "app_debug"})
public final class SearchSongFragment extends me.wcy.music.common.SimpleMusicRefreshFragment<me.wcy.music.common.bean.SongData> {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy itemBinder$delegate = null;
    @javax.inject.Inject()
    public me.wcy.music.service.PlayerController playerController;
    
    public SearchSongFragment() {
        super();
    }
    
    private final me.wcy.music.search.SearchViewModel getViewModel() {
        return null;
    }
    
    private final me.wcy.music.search.song.SearchSongItemBinder getItemBinder() {
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
    protected boolean isShowTitle() {
        return false;
    }
    
    @java.lang.Override()
    protected boolean isRefreshEnabled() {
        return false;
    }
    
    @java.lang.Override()
    protected void onLazyCreate() {
    }
    
    @java.lang.Override()
    public void initAdapter(@org.jetbrains.annotations.NotNull()
    me.wcy.radapter3.RAdapter<me.wcy.music.common.bean.SongData> adapter) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getData(int page, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.model.CommonResult<java.util.List<me.wcy.music.common.bean.SongData>>> $completion) {
        return null;
    }
}