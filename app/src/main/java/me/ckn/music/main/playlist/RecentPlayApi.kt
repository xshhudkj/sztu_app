package me.ckn.music.main.playlist

import me.ckn.music.main.playlist.bean.RecentPlayData
import me.ckn.music.net.HttpClient
import me.ckn.music.storage.preference.ConfigPreferences
import retrofit2.Retrofit
import retrofit2.http.POST
import retrofit2.http.Query
import top.wangchenyan.common.net.gson.GsonConverterFactory
import top.wangchenyan.common.utils.GsonUtils
import top.wangchenyan.common.utils.ServerTime

/**
 * WhisperPlay Music Player
 *
 * Created by ckn on 2025-06-11
 *
 * 文件描述：最近播放API
 * File Description: Recent play API
 *
 * @author ckn
 * @since 2025-06-11
 * @version 2.3.0
 */
interface RecentPlayApi {

    /**
     * 获取最近播放歌曲
     * @param limit 返回数量，默认为100
     */
    @POST("record/recent/song")
    suspend fun getRecentPlaySongs(
        @Query("limit") limit: Int = 100,
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): RecentPlayData

    companion object {
        private val api: RecentPlayApi by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(ConfigPreferences.apiDomain)
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.gson, true))
                .client(HttpClient.okHttpClient)
                .build()
            retrofit.create(RecentPlayApi::class.java)
        }

        fun get(): RecentPlayApi = api
    }
}
