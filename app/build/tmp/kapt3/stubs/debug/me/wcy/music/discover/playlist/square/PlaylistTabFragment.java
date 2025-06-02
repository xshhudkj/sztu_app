package me.wcy.music.discover.playlist.square;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import dagger.hilt.android.AndroidEntryPoint;
import top.wangchenyan.common.model.CommonResult;
import top.wangchenyan.common.widget.decoration.GridSpacingDecoration;
import me.wcy.music.common.SimpleMusicRefreshFragment;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.consts.Consts;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.discover.DiscoverApi;
import me.wcy.music.discover.playlist.square.item.PlaylistItemBinder;
import me.wcy.radapter3.RAdapter;
import me.wcy.router.CRouter;

/**
 * Created by wangchenyan.top on 2023/9/26.
 */
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\"\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0011H\u0014J\u0016\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0014J\b\u0010\u0018\u001a\u00020\u0017H\u0014J\b\u0010\u0019\u001a\u00020\u0013H\u0014R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u001a"}, d2 = {"Lme/wcy/music/discover/playlist/square/PlaylistTabFragment;", "Lme/wcy/music/common/SimpleMusicRefreshFragment;", "Lme/wcy/music/common/bean/PlaylistData;", "()V", "cat", "", "getCat", "()Ljava/lang/String;", "cat$delegate", "Lkotlin/Lazy;", "getData", "Ltop/wangchenyan/common/model/CommonResult;", "", "page", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLayoutManager", "Landroidx/recyclerview/widget/RecyclerView$LayoutManager;", "initAdapter", "", "adapter", "Lme/wcy/radapter3/RAdapter;", "isRefreshEnabled", "", "isShowTitle", "onLazyCreate", "app_debug"})
public final class PlaylistTabFragment extends me.wcy.music.common.SimpleMusicRefreshFragment<me.wcy.music.common.bean.PlaylistData> {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy cat$delegate = null;
    
    public PlaylistTabFragment() {
        super();
    }
    
    private final java.lang.String getCat() {
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
    @org.jetbrains.annotations.NotNull()
    protected androidx.recyclerview.widget.RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }
    
    @java.lang.Override()
    public void initAdapter(@org.jetbrains.annotations.NotNull()
    me.wcy.radapter3.RAdapter<me.wcy.music.common.bean.PlaylistData> adapter) {
    }
    
    @java.lang.Override()
    protected void onLazyCreate() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getData(int page, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.model.CommonResult<java.util.List<me.wcy.music.common.bean.PlaylistData>>> $completion) {
        return null;
    }
}