package me.ckn.music.account

import top.wangchenyan.common.net.NetResult
import top.wangchenyan.common.net.gson.GsonConverterFactory
import top.wangchenyan.common.utils.GsonUtils
import top.wangchenyan.common.utils.ServerTime
import me.ckn.music.common.bean.VipInfoData
import me.ckn.music.common.bean.UserLevelData
import me.ckn.music.account.bean.UserDetailData
import me.ckn.music.net.HttpClient
import me.ckn.music.storage.preference.ConfigPreferences
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * VIP和用户等级相关API
 * Created by wangchenyan.top on 2024/12/21.
 */
interface VipApi {

    /**
     * 获取VIP信息
     * @param uid 用户ID，可选参数
     */
    @POST("vip/info")
    suspend fun getVipInfo(
        @Query("uid") uid: Long? = null,
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): VipInfoData

    /**
     * 获取VIP信息(app端)
     * @param uid 用户ID，可选参数
     */
    @POST("vip/info/v2")
    suspend fun getVipInfoV2(
        @Query("uid") uid: Long? = null,
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): VipInfoData

    /**
     * 获取用户等级信息
     */
    @POST("user/level")
    suspend fun getUserLevel(
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): UserLevelData

    /**
     * 获取用户详情
     * @param uid 用户ID
     */
    @POST("user/detail")
    suspend fun getUserDetail(
        @Query("uid") uid: Long,
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): UserDetailData

    companion object {
        private val api: VipApi by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(ConfigPreferences.apiDomain)
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.gson, true))
                .client(HttpClient.okHttpClient)
                .build()
            retrofit.create(VipApi::class.java)
        }

        fun get(): VipApi = api
    }
}
