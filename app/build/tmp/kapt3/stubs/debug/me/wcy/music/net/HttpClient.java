package me.wcy.music.net;

import android.util.Log;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import top.wangchenyan.common.CommonApp;
import top.wangchenyan.common.utils.ServerTime;
import me.wcy.music.consts.FilePath;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangchenyan.top on 2022/6/22.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2 = {"Lme/wcy/music/net/HttpClient;", "", "()V", "okHttpClient", "Lokhttp3/OkHttpClient;", "getOkHttpClient", "()Lokhttp3/OkHttpClient;", "okHttpClient$delegate", "Lkotlin/Lazy;", "app_debug"})
public final class HttpClient {
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy okHttpClient$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.net.HttpClient INSTANCE = null;
    
    private HttpClient() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.OkHttpClient getOkHttpClient() {
        return null;
    }
}