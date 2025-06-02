package me.wcy.music.service.likesong;

import android.app.Activity;
import top.wangchenyan.common.model.CommonResult;

/**
 * Created by wangchenyan.top on 2024/3/21.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J$\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u0007H\u00a6@\u00a2\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u00020\u0003H&\u00a8\u0006\u000e"}, d2 = {"Lme/wcy/music/service/likesong/LikeSongProcessor;", "", "init", "", "isLiked", "", "id", "", "like", "Ltop/wangchenyan/common/model/CommonResult;", "activity", "Landroid/app/Activity;", "(Landroid/app/Activity;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateLikeSongList", "app_debug"})
public abstract interface LikeSongProcessor {
    
    public abstract void init();
    
    public abstract void updateLikeSongList();
    
    public abstract boolean isLiked(long id);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object like(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.model.CommonResult<kotlin.Unit>> $completion);
}