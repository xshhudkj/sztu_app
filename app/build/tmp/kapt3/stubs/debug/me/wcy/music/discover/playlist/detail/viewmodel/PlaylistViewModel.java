package me.wcy.music.discover.playlist.detail.viewmodel;

import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.common.bean.SongData;
import me.wcy.music.discover.DiscoverApi;
import me.wcy.music.mine.MineApi;
import me.wcy.music.service.likesong.LikeSongProcessor;
import top.wangchenyan.common.model.CommonResult;
import top.wangchenyan.common.utils.ServerTime;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/9/22.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u001e\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\nJ\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u000e\u0010 \u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\bR\u0016\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u00020\f8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014\u00a8\u0006\""}, d2 = {"Lme/wcy/music/discover/playlist/detail/viewmodel/PlaylistViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_playlistData", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lme/wcy/music/common/bean/PlaylistData;", "_songList", "", "Lme/wcy/music/common/bean/SongData;", "isLike", "", "likeSongProcessor", "Lme/wcy/music/service/likesong/LikeSongProcessor;", "getLikeSongProcessor", "()Lme/wcy/music/service/likesong/LikeSongProcessor;", "setLikeSongProcessor", "(Lme/wcy/music/service/likesong/LikeSongProcessor;)V", "playlistData", "Lkotlinx/coroutines/flow/StateFlow;", "getPlaylistData", "()Lkotlinx/coroutines/flow/StateFlow;", "playlistId", "", "realtimeData", "songList", "getSongList", "collect", "Ltop/wangchenyan/common/model/CommonResult;", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "init", "loadData", "removeSong", "songData", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PlaylistViewModel extends androidx.lifecycle.ViewModel {
    @javax.inject.Inject()
    public me.wcy.music.service.likesong.LikeSongProcessor likeSongProcessor;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<me.wcy.music.common.bean.PlaylistData> _playlistData = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<me.wcy.music.common.bean.PlaylistData> playlistData = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<me.wcy.music.common.bean.SongData>> _songList = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<me.wcy.music.common.bean.SongData>> songList = null;
    private long playlistId = 0L;
    private boolean realtimeData = false;
    private boolean isLike = false;
    
    @javax.inject.Inject()
    public PlaylistViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.service.likesong.LikeSongProcessor getLikeSongProcessor() {
        return null;
    }
    
    public final void setLikeSongProcessor(@org.jetbrains.annotations.NotNull()
    me.wcy.music.service.likesong.LikeSongProcessor p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<me.wcy.music.common.bean.PlaylistData> getPlaylistData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<me.wcy.music.common.bean.SongData>> getSongList() {
        return null;
    }
    
    public final void init(long playlistId, boolean realtimeData, boolean isLike) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loadData(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.model.CommonResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object collect(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.model.CommonResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    public final void removeSong(@org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.SongData songData) {
    }
}