package me.wcy.music.mine.collect.song;

import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import me.wcy.music.account.service.UserService;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.mine.MineApi;
import top.wangchenyan.common.model.CommonResult;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2024/3/20.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u0019\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u001a\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0017H\u0086@\u00a2\u0006\u0002\u0010\u001cR\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0010\u001a\u00020\u00118\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015\u00a8\u0006\u001d"}, d2 = {"Lme/wcy/music/mine/collect/song/CollectSongViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_myPlaylists", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lme/wcy/music/common/bean/PlaylistData;", "myPlaylists", "getMyPlaylists", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "songId", "", "getSongId", "()J", "setSongId", "(J)V", "userService", "Lme/wcy/music/account/service/UserService;", "getUserService", "()Lme/wcy/music/account/service/UserService;", "setUserService", "(Lme/wcy/music/account/service/UserService;)V", "collectSong", "Ltop/wangchenyan/common/model/CommonResult;", "", "pid", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMyPlayList", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CollectSongViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<me.wcy.music.common.bean.PlaylistData>> _myPlaylists = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<me.wcy.music.common.bean.PlaylistData>> myPlaylists = null;
    private long songId = 0L;
    @javax.inject.Inject()
    public me.wcy.music.account.service.UserService userService;
    
    @javax.inject.Inject()
    public CollectSongViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<me.wcy.music.common.bean.PlaylistData>> getMyPlaylists() {
        return null;
    }
    
    public final long getSongId() {
        return 0L;
    }
    
    public final void setSongId(long p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.account.service.UserService getUserService() {
        return null;
    }
    
    public final void setUserService(@org.jetbrains.annotations.NotNull()
    me.wcy.music.account.service.UserService p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getMyPlayList(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.model.CommonResult<java.util.List<me.wcy.music.common.bean.PlaylistData>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object collectSong(long pid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.model.CommonResult<kotlin.Unit>> $completion) {
        return null;
    }
}