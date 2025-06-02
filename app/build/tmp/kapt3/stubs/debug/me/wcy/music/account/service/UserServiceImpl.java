package me.wcy.music.account.service;

import android.app.Activity;
import kotlinx.coroutines.Dispatchers;
import me.wcy.music.account.AccountApi;
import me.wcy.music.account.AccountPreference;
import me.wcy.music.account.bean.ProfileData;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.net.NetCache;
import me.wcy.router.CRouter;
import top.wangchenyan.common.model.CommonResult;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by wangchenyan.top on 2023/8/25.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J8\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u000e\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u00112\u000e\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0011H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u000fH\u0016J\u001c\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u00192\u0006\u0010\u001a\u001a\u00020\u0014H\u0096@\u00a2\u0006\u0002\u0010\u001bJ\u000e\u0010\u001c\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\u001dR\u0016\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u001e"}, d2 = {"Lme/wcy/music/account/service/UserServiceImpl;", "Lme/wcy/music/account/service/UserService;", "()V", "_profile", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lme/wcy/music/account/bean/ProfileData;", "profile", "Lkotlinx/coroutines/flow/StateFlow;", "getProfile", "()Lkotlinx/coroutines/flow/StateFlow;", "checkLogin", "", "activity", "Landroid/app/Activity;", "showDialog", "", "onCancel", "Lkotlin/Function0;", "onLogin", "getCookie", "", "getUserId", "", "isLogin", "login", "Ltop/wangchenyan/common/model/CommonResult;", "cookie", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class UserServiceImpl implements me.wcy.music.account.service.UserService {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<me.wcy.music.account.bean.ProfileData> _profile = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<me.wcy.music.account.bean.ProfileData> profile = null;
    
    @javax.inject.Inject()
    public UserServiceImpl() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.StateFlow<me.wcy.music.account.bean.ProfileData> getProfile() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String getCookie() {
        return null;
    }
    
    @java.lang.Override()
    public boolean isLogin() {
        return false;
    }
    
    @java.lang.Override()
    public long getUserId() {
        return 0L;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object login(@org.jetbrains.annotations.NotNull()
    java.lang.String cookie, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.model.CommonResult<me.wcy.music.account.bean.ProfileData>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    public void checkLogin(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, boolean showDialog, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCancel, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogin) {
    }
}