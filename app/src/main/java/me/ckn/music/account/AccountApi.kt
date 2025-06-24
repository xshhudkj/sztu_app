/**
 * WhisperPlay Music Player
 *
 * 文件描述：定义账户相关的API接口
 * File Description: Defines API interfaces related to user accounts.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.account

import me.ckn.music.account.bean.LoginResultData
import me.ckn.music.account.bean.LoginStatusData
import me.ckn.music.account.bean.QrCodeData
import me.ckn.music.account.bean.QrCodeKeyData
import me.ckn.music.account.bean.SendCodeResult
import me.ckn.music.net.HttpClient
import me.ckn.music.storage.preference.ConfigPreferences
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import top.wangchenyan.common.net.NetResult
import top.wangchenyan.common.net.gson.GsonConverterFactory
import top.wangchenyan.common.utils.GsonUtils
import top.wangchenyan.common.utils.ServerTime

/**
 * 账户相关API接口
 * Account-related API interface
 *
 * 主要功能：
 * Main Functions:
 * - 提供用户登录、注册、状态检查等网络请求 / Provides network requests for user login, registration, status check, etc.
 *
 * @author ckn
 * @since 2025-06-10
 */
interface AccountApi {

    /**
     * 发送手机验证码
     * Send phone verification code
     *
     * @param phone 手机号 / Phone number
     * @param timestamp 时间戳 / Timestamp
     * @return 发送结果 / Send result
     */
    @GET("captcha/sent")
    suspend fun sendPhoneCode(
        @Query("phone") phone: String,
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): SendCodeResult

    /**
     * 手机号登录
     * Login with phone number
     *
     * @param phone 手机号 / Phone number
     * @param captcha 验证码 / Verification code
     * @param timestamp 时间戳 / Timestamp
     * @return 登录结果 / Login result
     */
    @GET("login/cellphone")
    suspend fun phoneLogin(
        @Query("phone") phone: String,
        @Query("captcha") captcha: String,
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): LoginResultData

    /**
     * 获取二维码登录的key
     * Get the key for QR code login
     *
     * @param timestamp 时间戳 / Timestamp
     * @return 二维码 key / QR code key
     */
    @GET("login/qr/key")
    suspend fun getQrCodeKey(
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): NetResult<QrCodeKeyData>

    /**
     * 获取登录二维码
     * Get login QR code
     *
     * @param key 二维码 key / QR code key
     * @param timestamp 时间戳 / Timestamp
     * @return 登录二维码信息 / Login QR code information
     */
    @GET("login/qr/create")
    suspend fun getLoginQrCode(
        @Query("key") key: String,
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): NetResult<QrCodeData>

    /**
     * 检查二维码登录状态
     * Check QR code login status
     *
     * @param key 二维码 key / QR code key
     * @param timestamp 时间戳 / Timestamp
     * @param noCookie 是否不携带cookie / Whether to not carry cookie
     * @return 登录结果 / Login result
     */
    @GET("login/qr/check")
    suspend fun checkLoginStatus(
        @Query("key") key: String,
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis(),
        @Query("noCookie") noCookie: Boolean = true
    ): LoginResultData

    /**
     * 获取登录状态
     * Get login status
     *
     * @param timestamp 时间戳 / Timestamp
     * @return 登录状态信息 / Login status information
     */
    @POST("login/status")
    suspend fun getLoginStatus(
        @Query("timestamp") timestamp: Long = ServerTime.currentTimeMillis()
    ): LoginStatusData

    companion object {
        private val api: AccountApi by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(ConfigPreferences.apiDomain)
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.gson, true))
                .client(HttpClient.okHttpClient)
                .build()
            retrofit.create(AccountApi::class.java)
        }

        fun get(): AccountApi = api
    }
}