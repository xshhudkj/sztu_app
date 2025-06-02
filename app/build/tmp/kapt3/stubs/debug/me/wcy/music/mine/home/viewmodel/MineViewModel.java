package me.wcy.music.mine.home.viewmodel;

import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import me.wcy.music.account.service.UserService;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.mine.MineApi;
import me.wcy.music.net.NetCache;
import top.wangchenyan.common.model.CommonResult;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/9/28.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u0000 %2\u00020\u0001:\u0001%B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0002J\u001c\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001b0 2\u0006\u0010!\u001a\u00020\u001dH\u0086@\u00a2\u0006\u0002\u0010\"J\u0006\u0010#\u001a\u00020\u001bJ\u0010\u0010#\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0006\u0010$\u001a\u00020\u001bR\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0019\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000bR\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0014\u001a\u00020\u00158\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019\u00a8\u0006&"}, d2 = {"Lme/wcy/music/mine/home/viewmodel/MineViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_collectPlaylists", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lme/wcy/music/common/bean/PlaylistData;", "_likePlaylist", "_myPlaylists", "collectPlaylists", "getCollectPlaylists", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "likePlaylist", "Lkotlinx/coroutines/flow/StateFlow;", "getLikePlaylist", "()Lkotlinx/coroutines/flow/StateFlow;", "myPlaylists", "getMyPlaylists", "updateJob", "Lkotlinx/coroutines/Job;", "userService", "Lme/wcy/music/account/service/UserService;", "getUserService", "()Lme/wcy/music/account/service/UserService;", "setUserService", "(Lme/wcy/music/account/service/UserService;)V", "notifyPlaylist", "", "uid", "", "list", "removeCollect", "Ltop/wangchenyan/common/model/CommonResult;", "id", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePlaylist", "updatePlaylistFromCache", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MineViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<me.wcy.music.common.bean.PlaylistData> _likePlaylist = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<me.wcy.music.common.bean.PlaylistData> likePlaylist = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<me.wcy.music.common.bean.PlaylistData>> _myPlaylists = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<me.wcy.music.common.bean.PlaylistData>> myPlaylists = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<me.wcy.music.common.bean.PlaylistData>> _collectPlaylists = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<me.wcy.music.common.bean.PlaylistData>> collectPlaylists = null;
    @javax.inject.Inject()
    public me.wcy.music.account.service.UserService userService;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job updateJob;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CACHE_KEY = "my_playlist";
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.mine.home.viewmodel.MineViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public MineViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<me.wcy.music.common.bean.PlaylistData> getLikePlaylist() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<me.wcy.music.common.bean.PlaylistData>> getMyPlaylists() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<me.wcy.music.common.bean.PlaylistData>> getCollectPlaylists() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.account.service.UserService getUserService() {
        return null;
    }
    
    public final void setUserService(@org.jetbrains.annotations.NotNull()
    me.wcy.music.account.service.UserService p0) {
    }
    
    public final void updatePlaylistFromCache() {
    }
    
    public final void updatePlaylist() {
    }
    
    private final void updatePlaylist(long uid) {
    }
    
    private final void notifyPlaylist(long uid, java.util.List<me.wcy.music.common.bean.PlaylistData> list) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeCollect(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.model.CommonResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lme/wcy/music/mine/home/viewmodel/MineViewModel$Companion;", "", "()V", "CACHE_KEY", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}