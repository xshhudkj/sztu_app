package me.wcy.music.account;

import top.wangchenyan.common.net.NetResult;
import top.wangchenyan.common.net.gson.GsonConverterFactory;
import top.wangchenyan.common.utils.GsonUtils;
import top.wangchenyan.common.utils.ServerTime;
import me.wcy.music.account.bean.LoginResultData;
import me.wcy.music.account.bean.LoginStatusData;
import me.wcy.music.account.bean.QrCodeData;
import me.wcy.music.account.bean.QrCodeKeyData;
import me.wcy.music.account.bean.SendCodeResult;
import me.wcy.music.net.HttpClient;
import me.wcy.music.storage.preference.ConfigPreferences;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wangchenyan.top on 2023/8/25.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aJ,\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0003\u0010\u0006\u001a\u00020\u00072\b\b\u0003\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ(\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0003\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0018\u0010\u000f\u001a\u00020\u00102\b\b\u0003\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\f2\b\b\u0003\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0011J,\u0010\u0014\u001a\u00020\u00032\b\b\u0001\u0010\u0015\u001a\u00020\u00052\b\b\u0001\u0010\u0016\u001a\u00020\u00052\b\b\u0003\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\"\u0010\u0018\u001a\u00020\u00192\b\b\u0001\u0010\u0015\u001a\u00020\u00052\b\b\u0003\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000e\u00a8\u0006\u001b"}, d2 = {"Lme/wcy/music/account/AccountApi;", "", "checkLoginStatus", "Lme/wcy/music/account/bean/LoginResultData;", "key", "", "timestamp", "", "noCookie", "", "(Ljava/lang/String;JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLoginQrCode", "Ltop/wangchenyan/common/net/NetResult;", "Lme/wcy/music/account/bean/QrCodeData;", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLoginStatus", "Lme/wcy/music/account/bean/LoginStatusData;", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQrCodeKey", "Lme/wcy/music/account/bean/QrCodeKeyData;", "phoneLogin", "phone", "captcha", "(Ljava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendPhoneCode", "Lme/wcy/music/account/bean/SendCodeResult;", "Companion", "app_debug"})
public abstract interface AccountApi {
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.account.AccountApi.Companion Companion = null;
    
    @retrofit2.http.GET(value = "captcha/sent")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sendPhoneCode(@retrofit2.http.Query(value = "phone")
    @org.jetbrains.annotations.NotNull()
    java.lang.String phone, @retrofit2.http.Query(value = "timestamp")
    long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.account.bean.SendCodeResult> $completion);
    
    @retrofit2.http.GET(value = "login/cellphone")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object phoneLogin(@retrofit2.http.Query(value = "phone")
    @org.jetbrains.annotations.NotNull()
    java.lang.String phone, @retrofit2.http.Query(value = "captcha")
    @org.jetbrains.annotations.NotNull()
    java.lang.String captcha, @retrofit2.http.Query(value = "timestamp")
    long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.account.bean.LoginResultData> $completion);
    
    @retrofit2.http.GET(value = "login/qr/key")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQrCodeKey(@retrofit2.http.Query(value = "timestamp")
    long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.net.NetResult<me.wcy.music.account.bean.QrCodeKeyData>> $completion);
    
    @retrofit2.http.GET(value = "login/qr/create")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLoginQrCode(@retrofit2.http.Query(value = "key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String key, @retrofit2.http.Query(value = "timestamp")
    long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.net.NetResult<me.wcy.music.account.bean.QrCodeData>> $completion);
    
    @retrofit2.http.GET(value = "login/qr/check")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object checkLoginStatus(@retrofit2.http.Query(value = "key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String key, @retrofit2.http.Query(value = "timestamp")
    long timestamp, @retrofit2.http.Query(value = "noCookie")
    boolean noCookie, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.account.bean.LoginResultData> $completion);
    
    @retrofit2.http.POST(value = "login/status")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLoginStatus(@retrofit2.http.Query(value = "timestamp")
    long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.account.bean.LoginStatusData> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\u0004R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\n"}, d2 = {"Lme/wcy/music/account/AccountApi$Companion;", "", "()V", "api", "Lme/wcy/music/account/AccountApi;", "getApi", "()Lme/wcy/music/account/AccountApi;", "api$delegate", "Lkotlin/Lazy;", "get", "app_debug"})
    public static final class Companion {
        @org.jetbrains.annotations.NotNull()
        private static final kotlin.Lazy<?> api$delegate = null;
        
        private Companion() {
            super();
        }
        
        private final me.wcy.music.account.AccountApi getApi() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final me.wcy.music.account.AccountApi get() {
            return null;
        }
    }
    
    /**
     * Created by wangchenyan.top on 2023/8/25.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}