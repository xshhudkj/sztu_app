package me.wcy.music.account.service;

import android.app.Activity;
import kotlinx.coroutines.flow.StateFlow;
import top.wangchenyan.common.model.CommonResult;
import me.wcy.music.account.bean.ProfileData;

/**
 * Created by wangchenyan.top on 2023/9/18.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J>\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\u0010\b\u0002\u0010\r\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u000e2\u0010\b\u0002\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u000eH&J\b\u0010\u0010\u001a\u00020\u0011H&J\b\u0010\u0012\u001a\u00020\u0013H&J\b\u0010\u0014\u001a\u00020\fH&J\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u00162\u0006\u0010\u0017\u001a\u00020\u0011H\u00a6@\u00a2\u0006\u0002\u0010\u0018J\u000e\u0010\u0019\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u0010\u001aR\u001a\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u001b"}, d2 = {"Lme/wcy/music/account/service/UserService;", "", "profile", "Lkotlinx/coroutines/flow/StateFlow;", "Lme/wcy/music/account/bean/ProfileData;", "getProfile", "()Lkotlinx/coroutines/flow/StateFlow;", "checkLogin", "", "activity", "Landroid/app/Activity;", "showDialog", "", "onCancel", "Lkotlin/Function0;", "onLogin", "getCookie", "", "getUserId", "", "isLogin", "login", "Ltop/wangchenyan/common/model/CommonResult;", "cookie", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface UserService {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.StateFlow<me.wcy.music.account.bean.ProfileData> getProfile();
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.lang.String getCookie();
    
    public abstract boolean isLogin();
    
    public abstract long getUserId();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object login(@org.jetbrains.annotations.NotNull()
    java.lang.String cookie, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.model.CommonResult<me.wcy.music.account.bean.ProfileData>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    public abstract void checkLogin(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, boolean showDialog, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCancel, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogin);
    
    /**
     * Created by wangchenyan.top on 2023/9/18.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}