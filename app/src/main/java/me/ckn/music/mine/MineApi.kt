package me.ckn.music.mine

import me.ckn.music.discover.playlist.square.bean.PlaylistListData
import me.ckn.music.mine.collect.song.bean.CollectSongResult
import me.ckn.music.net.HttpClient
import me.ckn.music.service.likesong.bean.LikeSongListData
import me.ckn.music.storage.preference.ConfigPreferences
import retrofit2.Retrofit
import retrofit2.http.POST
import retrofit2.http.Query
import top.wangchenyan.common.net.NetResult
import top.wangchenyan.common.net.gson.GsonConverterFactory
import top.wangchenyan.common.utils.GsonUtils
import top.wangchenyan.common.utils.ServerTime

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/26
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：我的页面API
 * File Description: Mine page API
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
interface MineApi {

    @POST("user/playlist")
    suspend fun getUserPlaylist(
        @Query("uid") uid: Long,
        @Query("limit") limit: Int = 1000,
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): PlaylistListData

    /**
     * 收藏/取消收藏歌单
     * @param id 歌单 id
     * @param t 类型,1:收藏,2:取消收藏
     */
    @POST("playlist/subscribe")
    suspend fun collectPlaylist(
        @Query("id") id: Long,
        @Query("t") t: Int,
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): NetResult<Any>

    /**
     * 对歌单添加歌曲
     * @param op 从歌单增加单曲为 add, 删除为 del
     * @param pid 歌单 id
     * @param tracks 歌曲 id,可多个,用逗号隔开
     */
    @POST("playlist/tracks")
    suspend fun collectSong(
        @Query("pid") pid: Long,
        @Query("tracks") tracks: String,
        @Query("op") op: String = "add",
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): CollectSongResult

    /**
     * 喜欢音乐
     * @param id 歌曲 id
     * @param like 默认为 true 即喜欢 , 若传 false, 则取消喜欢
     */
    @POST("like")
    suspend fun likeSong(
        @Query("id") id: Long,
        @Query("like") like: Boolean = true,
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): NetResult<Any>

    /**
     * 喜欢音乐列表
     */
    @POST("likelist")
    suspend fun getMyLikeSongList(
        @Query("uid") uid: Long,
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): LikeSongListData

    companion object {
        private val api: MineApi by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(ConfigPreferences.apiDomain)
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.gson, true))
                .client(HttpClient.okHttpClient)
                .build()
            retrofit.create(MineApi::class.java)
        }

        fun get(): MineApi = api
    }
}