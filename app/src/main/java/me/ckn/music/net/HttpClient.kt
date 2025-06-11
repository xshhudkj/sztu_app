package me.ckn.music.net

import android.util.Log
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.utils.ServerTime
import me.ckn.music.consts.FilePath
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by wangchenyan.top on 2022/6/22.
 */
object HttpClient {

    val okHttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            // 优化网络超时配置，提升播放启动速度
            .connectTimeout(8, TimeUnit.SECONDS)  // 连接超时：8秒
            .readTimeout(15, TimeUnit.SECONDS)    // 读取超时：15秒
            .writeTimeout(10, TimeUnit.SECONDS)   // 写入超时：10秒
            // 忽略host验证
            .hostnameVerifier { hostname, session -> true }
            // 增加HTTP缓存大小，提升重复请求性能
            .cache(Cache(File(FilePath.httpCache), 50 * 1024 * 1024)) // 50MB缓存
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(ServerTime)
        if (CommonApp.test) {
            builder.addInterceptor(
                LoggingInterceptor.Builder()
                    .tag("MusicHttp")
                    .setLevel(Level.BASIC)
                    .log(Log.WARN)
                    .build()
            )
        }
        builder.build()
    }
}