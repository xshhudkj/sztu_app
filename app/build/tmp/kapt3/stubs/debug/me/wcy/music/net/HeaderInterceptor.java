package me.wcy.music.net;

import android.util.Log;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.JsonObject;
import top.wangchenyan.common.CommonApp;
import okhttp3.Interceptor;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.IOException;

/**
 * Created by wcy on 2018/7/15.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0002\u00a8\u0006\u000b"}, d2 = {"Lme/wcy/music/net/HeaderInterceptor;", "Lokhttp3/Interceptor;", "()V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "bodyToString", "", "Lokhttp3/RequestBody;", "Companion", "app_debug"})
public final class HeaderInterceptor implements okhttp3.Interceptor {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "HeaderInterceptor";
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.net.HeaderInterceptor.Companion Companion = null;
    
    public HeaderInterceptor() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public okhttp3.Response intercept(@org.jetbrains.annotations.NotNull()
    okhttp3.Interceptor.Chain chain) {
        return null;
    }
    
    @kotlin.jvm.Throws(exceptionClasses = {java.io.IOException.class})
    private final java.lang.String bodyToString(okhttp3.RequestBody $this$bodyToString) throws java.io.IOException {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lme/wcy/music/net/HeaderInterceptor$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}