package me.wcy.music.account.login;

import android.view.View;
import me.wcy.music.common.BaseMusicFragment;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.databinding.FragmentLoginRouteBinding;
import me.wcy.router.CRouter;
import me.wcy.router.RouteResultListener;
import me.wcy.router.annotation.Route;

/**
 * Created by wangchenyan.top on 2024/1/3.
 *
 * 已废弃：现在使用LoginActivity代替
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u0014H\u0014J\b\u0010\u0015\u001a\u00020\tH\u0014J\b\u0010\u0016\u001a\u00020\tH\u0002J\b\u0010\u0017\u001a\u00020\tH\u0002R-\u0010\u0003\u001a!\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\t0\u0004j\u0002`\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0019"}, d2 = {"Lme/wcy/music/account/login/LoginRouteFragment;", "Lme/wcy/music/common/BaseMusicFragment;", "()V", "routeResultListener", "Lkotlin/Function1;", "Lme/wcy/router/RouteResult;", "Lkotlin/ParameterName;", "name", "routeResult", "", "Lme/wcy/router/RouteResultListener;", "viewBinding", "Lme/wcy/music/databinding/FragmentLoginRouteBinding;", "getViewBinding", "()Lme/wcy/music/databinding/FragmentLoginRouteBinding;", "viewBinding$delegate", "Lkotlin/Lazy;", "getRootView", "Landroid/view/View;", "isLazy", "", "onLazyCreate", "startPhone", "startQrCode", "Companion", "app_debug"})
public final class LoginRouteFragment extends me.wcy.music.common.BaseMusicFragment {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewBinding$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<me.wcy.router.RouteResult, kotlin.Unit> routeResultListener = null;
    public static final int RESULT_SWITCH_QRCODE = 100;
    public static final int RESULT_SWITCH_PHONE = 200;
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.account.login.LoginRouteFragment.Companion Companion = null;
    
    public LoginRouteFragment() {
        super();
    }
    
    private final me.wcy.music.databinding.FragmentLoginRouteBinding getViewBinding() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected android.view.View getRootView() {
        return null;
    }
    
    @java.lang.Override()
    protected boolean isLazy() {
        return false;
    }
    
    @java.lang.Override()
    protected void onLazyCreate() {
    }
    
    private final void startPhone() {
    }
    
    private final void startQrCode() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lme/wcy/music/account/login/LoginRouteFragment$Companion;", "", "()V", "RESULT_SWITCH_PHONE", "", "RESULT_SWITCH_QRCODE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}