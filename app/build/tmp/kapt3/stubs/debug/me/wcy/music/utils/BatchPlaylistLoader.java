package me.wcy.music.utils;

import me.wcy.music.discover.DiscoverApi;
import me.wcy.music.service.PlayerController;

/**
 * 分批播放列表加载器
 * 智能处理歌单的分批加载和播放
 * Created by wangchenyan.top on 2024/3/26.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J@\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\b0\u0010H\u0082@\u00a2\u0006\u0002\u0010\u0012J,\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000eH\u0082@\u00a2\u0006\u0002\u0010\u0014J\u001e\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\fH\u0082@\u00a2\u0006\u0002\u0010\u0017J4\u0010\u0018\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000eH\u0082@\u00a2\u0006\u0002\u0010\u001aJ<\u0010\u001b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000e2\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\b0\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lme/wcy/music/utils/BatchPlaylistLoader;", "", "playerController", "Lme/wcy/music/service/PlayerController;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "(Lme/wcy/music/service/PlayerController;Lkotlinx/coroutines/CoroutineScope;)V", "fallbackToFullLoad", "", "playlistId", "", "songPosition", "", "onPlayStarted", "Lkotlin/Function0;", "onError", "Lkotlin/Function1;", "", "(JILkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadFirstBatchAndPlay", "(JILkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadRemainingPlaylistSongs", "loadedCount", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadTargetBatchAndPlay", "batchSize", "(JIILkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "playPlaylistSong", "app_debug"})
public final class BatchPlaylistLoader {
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.service.PlayerController playerController = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    
    public BatchPlaylistLoader(@org.jetbrains.annotations.NotNull()
    me.wcy.music.service.PlayerController playerController, @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope scope) {
        super();
    }
    
    /**
     * 智能播放歌单中的指定歌曲
     * @param playlistId 歌单ID
     * @param songPosition 用户点击的歌曲位置
     * @param onPlayStarted 播放开始回调
     * @param onError 错误回调
     */
    public final void playPlaylistSong(long playlistId, int songPosition, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPlayStarted, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> onError) {
    }
    
    private final java.lang.Object loadFirstBatchAndPlay(long playlistId, int songPosition, kotlin.jvm.functions.Function0<kotlin.Unit> onPlayStarted, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object loadTargetBatchAndPlay(long playlistId, int songPosition, int batchSize, kotlin.jvm.functions.Function0<kotlin.Unit> onPlayStarted, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object fallbackToFullLoad(long playlistId, int songPosition, kotlin.jvm.functions.Function0<kotlin.Unit> onPlayStarted, kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> onError, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object loadRemainingPlaylistSongs(long playlistId, int loadedCount, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}