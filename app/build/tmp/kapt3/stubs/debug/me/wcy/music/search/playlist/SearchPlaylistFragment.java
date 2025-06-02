package me.wcy.music.search.playlist;

import dagger.hilt.android.AndroidEntryPoint;
import top.wangchenyan.common.model.CommonResult;
import me.wcy.music.common.SimpleMusicRefreshFragment;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.consts.Consts;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.search.SearchApi;
import me.wcy.music.search.SearchViewModel;
import me.wcy.radapter3.RAdapter;
import me.wcy.router.CRouter;

/**
 * Created by wangchenyan.top on 2023/9/20.
 */
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\"\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0096@\u00a2\u0006\u0002\u0010\u0014J\u0016\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0014J\b\u0010\u001b\u001a\u00020\u001aH\u0014J\b\u0010\u001c\u001a\u00020\u0016H\u0014R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\t\u001a\u0004\b\f\u0010\r\u00a8\u0006\u001d"}, d2 = {"Lme/wcy/music/search/playlist/SearchPlaylistFragment;", "Lme/wcy/music/common/SimpleMusicRefreshFragment;", "Lme/wcy/music/common/bean/PlaylistData;", "()V", "itemBinder", "Lme/wcy/music/search/playlist/SearchPlaylistItemBinder;", "getItemBinder", "()Lme/wcy/music/search/playlist/SearchPlaylistItemBinder;", "itemBinder$delegate", "Lkotlin/Lazy;", "viewModel", "Lme/wcy/music/search/SearchViewModel;", "getViewModel", "()Lme/wcy/music/search/SearchViewModel;", "viewModel$delegate", "getData", "Ltop/wangchenyan/common/model/CommonResult;", "", "page", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "initAdapter", "", "adapter", "Lme/wcy/radapter3/RAdapter;", "isRefreshEnabled", "", "isShowTitle", "onLazyCreate", "app_debug"})
public final class SearchPlaylistFragment extends me.wcy.music.common.SimpleMusicRefreshFragment<me.wcy.music.common.bean.PlaylistData> {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy itemBinder$delegate = null;
    
    public SearchPlaylistFragment() {
        super();
    }
    
    private final me.wcy.music.search.SearchViewModel getViewModel() {
        return null;
    }
    
    private final me.wcy.music.search.playlist.SearchPlaylistItemBinder getItemBinder() {
        return null;
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
    me.wcy.radapter3.RAdapter<me.wcy.music.common.bean.PlaylistData> adapter) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getData(int page, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.model.CommonResult<java.util.List<me.wcy.music.common.bean.PlaylistData>>> $completion) {
        return null;
    }
}