package me.wcy.music.service.likesong;

import android.app.Activity;
import me.wcy.music.account.service.UserService;
import me.wcy.music.mine.MineApi;
import top.wangchenyan.common.model.CommonResult;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by wangchenyan.top on 2024/3/21.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH\u0016J$\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020\fH\u0096@\u00a2\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u000eH\u0016R\u0012\u0010\u0006\u001a\u00020\u0007X\u0096\u0005\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lme/wcy/music/service/likesong/LikeSongProcessorImpl;", "Lme/wcy/music/service/likesong/LikeSongProcessor;", "Lkotlinx/coroutines/CoroutineScope;", "userService", "Lme/wcy/music/account/service/UserService;", "(Lme/wcy/music/account/service/UserService;)V", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "likeSongSet", "", "", "init", "", "isLiked", "", "id", "like", "Ltop/wangchenyan/common/model/CommonResult;", "activity", "Landroid/app/Activity;", "(Landroid/app/Activity;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLikeSongList", "app_debug"})
public final class LikeSongProcessorImpl implements me.wcy.music.service.likesong.LikeSongProcessor, kotlinx.coroutines.CoroutineScope {
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.account.service.UserService userService = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.Long> likeSongSet = null;
    
    @javax.inject.Inject()
    public LikeSongProcessorImpl(@org.jetbrains.annotations.NotNull()
    me.wcy.music.account.service.UserService userService) {
        super();
    }
    
    @java.lang.Override()
    public void init() {
    }
    
    @java.lang.Override()
    public void updateLikeSongList() {
    }
    
    @java.lang.Override()
    public boolean isLiked(long id) {
        return false;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object like(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.model.CommonResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlin.coroutines.CoroutineContext getCoroutineContext() {
        return null;
    }
}