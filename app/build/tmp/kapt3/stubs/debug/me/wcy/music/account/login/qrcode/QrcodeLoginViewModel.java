package me.wcy.music.account.login.qrcode;

import android.graphics.Bitmap;
import androidx.lifecycle.ViewModel;
import cn.bertsir.zbar.utils.QRUtils;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.Dispatchers;
import me.wcy.music.account.AccountApi;
import me.wcy.music.account.bean.LoginResultData;
import me.wcy.music.account.service.UserService;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/8/28.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0015\u001a\u00020\u0016R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0019\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0019\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lme/wcy/music/account/login/qrcode/QrcodeLoginViewModel;", "Landroidx/lifecycle/ViewModel;", "userService", "Lme/wcy/music/account/service/UserService;", "(Lme/wcy/music/account/service/UserService;)V", "_loginStatus", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lme/wcy/music/account/bean/LoginResultData;", "_qrCode", "Landroid/graphics/Bitmap;", "job", "Lkotlinx/coroutines/Job;", "loginStatus", "Lkotlinx/coroutines/flow/StateFlow;", "getLoginStatus", "()Lkotlinx/coroutines/flow/StateFlow;", "qrCode", "getQrCode", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "qrCodeKey", "", "getLoginQrCode", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class QrcodeLoginViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.account.service.UserService userService = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String qrCodeKey = "";
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<android.graphics.Bitmap> _qrCode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<android.graphics.Bitmap> qrCode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<me.wcy.music.account.bean.LoginResultData> _loginStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<me.wcy.music.account.bean.LoginResultData> loginStatus = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job job;
    
    @javax.inject.Inject()
    public QrcodeLoginViewModel(@org.jetbrains.annotations.NotNull()
    me.wcy.music.account.service.UserService userService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<android.graphics.Bitmap> getQrCode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<me.wcy.music.account.bean.LoginResultData> getLoginStatus() {
        return null;
    }
    
    public final void getLoginQrCode() {
    }
}