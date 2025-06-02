package me.wcy.music.account.login.qrcode;

import android.view.View;
import dagger.hilt.android.AndroidEntryPoint;
import me.wcy.music.account.bean.LoginResultData;
import me.wcy.music.account.login.LoginRouteFragment;
import me.wcy.music.account.service.UserService;
import me.wcy.music.common.BaseMusicFragment;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.databinding.FragmentQrcodeLoginBinding;
import me.wcy.router.annotation.Route;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/8/28.
 */
@me.wcy.router.annotation.Route(value = "/login/qrcode")
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0014J\b\u0010\u001a\u001a\u00020\u0015H\u0002J\b\u0010\u001b\u001a\u00020\u0015H\u0014R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001c"}, d2 = {"Lme/wcy/music/account/login/qrcode/QrcodeLoginFragment;", "Lme/wcy/music/common/BaseMusicFragment;", "()V", "userService", "Lme/wcy/music/account/service/UserService;", "getUserService", "()Lme/wcy/music/account/service/UserService;", "setUserService", "(Lme/wcy/music/account/service/UserService;)V", "viewBinding", "Lme/wcy/music/databinding/FragmentQrcodeLoginBinding;", "getViewBinding", "()Lme/wcy/music/databinding/FragmentQrcodeLoginBinding;", "viewBinding$delegate", "Lkotlin/Lazy;", "viewModel", "Lme/wcy/music/account/login/qrcode/QrcodeLoginViewModel;", "getViewModel", "()Lme/wcy/music/account/login/qrcode/QrcodeLoginViewModel;", "viewModel$delegate", "getProfile", "", "cookie", "", "getRootView", "Landroid/view/View;", "loadQrCode", "onLazyCreate", "app_debug"})
public final class QrcodeLoginFragment extends me.wcy.music.common.BaseMusicFragment {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewBinding$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @javax.inject.Inject()
    public me.wcy.music.account.service.UserService userService;
    
    public QrcodeLoginFragment() {
        super();
    }
    
    private final me.wcy.music.databinding.FragmentQrcodeLoginBinding getViewBinding() {
        return null;
    }
    
    private final me.wcy.music.account.login.qrcode.QrcodeLoginViewModel getViewModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.account.service.UserService getUserService() {
        return null;
    }
    
    public final void setUserService(@org.jetbrains.annotations.NotNull()
    me.wcy.music.account.service.UserService p0) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected android.view.View getRootView() {
        return null;
    }
    
    @java.lang.Override()
    protected void onLazyCreate() {
    }
    
    private final void loadQrCode() {
    }
    
    private final void getProfile(java.lang.String cookie) {
    }
}