package me.wcy.music.account.service

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.wcy.music.account.VipApi
import me.wcy.music.account.bean.ProfileData
import me.wcy.music.account.bean.UserDetailData
import me.wcy.music.common.bean.VipInfoData
import me.wcy.music.common.bean.UserLevelData
import me.wcy.music.utils.VipUtils
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 全局用户状态管理器
 * 统一管理用户信息、VIP状态、等级信息的获取和更新
 * 
 * 功能特性：
 * - 统一的用户状态管理
 * - 支持手动刷新和自动更新
 * - 响应式数据流，实时通知UI更新
 * - 错误处理和重试机制
 * - 缓存机制，避免频繁网络请求
 * 
 * Created by wangchenyan.top on 2024/12/20.
 */
@Singleton
class UserStateManager @Inject constructor(
    private val userService: UserService
) {
    companion object {
        private const val TAG = "UserStateManager"
        private const val CACHE_DURATION_MS = 5 * 60 * 1000L // 5分钟缓存
    }

    // 协程作用域，使用SupervisorJob确保单个任务失败不影响其他任务
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // 用户详情状态
    private val _userDetail = MutableStateFlow<UserDetailData?>(null)
    val userDetail: StateFlow<UserDetailData?> = _userDetail.asStateFlow()

    // 注意：VIP信息和用户等级信息现在都从用户详情中获取，不再需要单独的状态

    // 刷新状态
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    // 最后更新时间
    private var lastUpdateTime = 0L

    // 错误状态
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    /**
     * 初始化用户状态管理器
     * 监听用户登录状态变化，自动更新用户信息
     */
    fun init() {
        // 设置VIP状态提供者，让VipUtils能够获取真实的VIP状态
        VipUtils.setVipStatusProvider { isVip() }

        scope.launch {
            userService.profile.collect { profile ->
                if (profile != null) {
                    Log.d(TAG, "用户登录状态变化，自动更新用户信息: ${profile.nickname}")
                    refreshUserState(forceRefresh = false)
                } else {
                    Log.d(TAG, "用户已登出，清空用户状态")
                    clearUserState()
                }
            }
        }
    }

    /**
     * 刷新用户状态
     * @param forceRefresh 是否强制刷新，忽略缓存
     */
    suspend fun refreshUserState(forceRefresh: Boolean = true): RefreshResult {
        if (!userService.isLogin()) {
            Log.w(TAG, "用户未登录，跳过状态刷新")
            return RefreshResult.NotLoggedIn
        }

        // 检查缓存是否有效
        if (!forceRefresh && isCacheValid()) {
            Log.d(TAG, "缓存有效，跳过网络请求")
            return RefreshResult.Success
        }

        _isRefreshing.value = true
        _error.value = null

        return try {
            Log.d(TAG, "开始刷新用户状态...")

            // 只使用用户详情接口获取所有信息，确保数据来源统一
            val userDetailResult = kotlin.runCatching {
                VipApi.get().getUserDetail(userService.getUserId())
            }

            var hasSuccess = false
            var errorMessage: String? = null

            // 处理用户详情结果，从中提取所有需要的信息
            userDetailResult.fold(
                onSuccess = { userDetail ->
                    if (userDetail.code == 200) {
                        _userDetail.value = userDetail
                        hasSuccess = true

                        Log.d(TAG, "用户详情更新成功: ${userDetail.profile?.nickname}")
                        Log.d(TAG, "用户信息详情: 昵称=${userDetail.profile?.nickname}, 头像=${userDetail.profile?.avatarUrl}, 等级=${userDetail.level}, 听歌数=${userDetail.listenSongs}")

                        userDetail.profile?.let { profile ->
                            Log.d(TAG, "用户Profile信息: VIP类型=${profile.vipType}, 用户ID=${profile.userId}")

                            // 基于vipType判断VIP状态和等级
                            val isVip = profile.vipType > 0
                            val vipLevel = when (profile.vipType) {
                                0 -> 0  // 非VIP
                                1 -> 1  // 普通VIP
                                11 -> 2 // 黑胶VIP
                                else -> 1 // 其他VIP类型默认为1级
                            }

                            Log.d(TAG, "VIP状态判断: vipType=${profile.vipType}, isVip=$isVip, vipLevel=$vipLevel")
                        }
                    } else {
                        val errorMsg = "用户详情获取失败: code=${userDetail.code}"
                        errorMessage = errorMsg
                        Log.e(TAG, errorMsg)
                    }
                },
                onFailure = { exception ->
                    val errorMsg = "用户详情网络请求失败: ${exception.message}"
                    errorMessage = errorMsg
                    Log.e(TAG, errorMsg, exception)
                }
            )

            // 更新缓存时间
            if (hasSuccess) {
                lastUpdateTime = System.currentTimeMillis()
                Log.d(TAG, "用户状态刷新完成，更新缓存时间")
            }

            // 设置错误信息
            _error.value = if (!hasSuccess) errorMessage else null

            if (hasSuccess) RefreshResult.Success else RefreshResult.Failed(errorMessage ?: "未知错误")

        } catch (e: Exception) {
            val errorMsg = "用户状态刷新异常: ${e.message}"
            Log.e(TAG, errorMsg, e)
            _error.value = errorMsg
            RefreshResult.Failed(errorMsg)
        } finally {
            _isRefreshing.value = false
        }
    }

    /**
     * 清空用户状态
     */
    private fun clearUserState() {
        _userDetail.value = null
        _error.value = null
        lastUpdateTime = 0L
    }

    /**
     * 检查缓存是否有效
     */
    private fun isCacheValid(): Boolean {
        return System.currentTimeMillis() - lastUpdateTime < CACHE_DURATION_MS
    }

    /**
     * 获取当前VIP状态
     * 基于用户详情API返回的vipType字段判断
     */
    fun isVip(): Boolean {
        val userProfile = _userDetail.value?.profile
        return userProfile?.vipType?.let { it > 0 } ?: false
    }

    /**
     * 获取VIP等级
     * 基于vipType返回对应的VIP等级
     */
    fun getVipLevel(): Int {
        val userProfile = _userDetail.value?.profile
        return when (userProfile?.vipType) {
            0 -> 0  // 非VIP
            1 -> 1  // 普通VIP
            11 -> 2 // 黑胶VIP
            else -> if ((userProfile?.vipType ?: 0) > 0) 1 else 0 // 其他VIP类型默认为1级
        }
    }

    /**
     * 获取VIP类型名称
     */
    fun getVipTypeName(): String {
        val userProfile = _userDetail.value?.profile
        return when (userProfile?.vipType) {
            0 -> ""
            1 -> "VIP"
            11 -> "黑胶VIP"
            else -> if ((userProfile?.vipType ?: 0) > 0) "VIP" else ""
        }
    }

    /**
     * 获取用户等级（从用户详情中获取）
     */
    fun getUserLevel(): Int {
        return _userDetail.value?.level ?: 0
    }

    /**
     * 获取用户详情信息
     */
    fun getUserDetail(): UserDetailData? {
        return _userDetail.value
    }

    /**
     * 获取用户昵称
     */
    fun getUserNickname(): String? {
        return _userDetail.value?.profile?.nickname
    }

    /**
     * 获取用户头像URL
     */
    fun getUserAvatarUrl(): String? {
        return _userDetail.value?.profile?.avatarUrl
    }

    /**
     * 获取用户听歌数量
     */
    fun getListenSongs(): Int {
        return _userDetail.value?.listenSongs ?: 0
    }

    /**
     * 刷新结果枚举
     */
    sealed class RefreshResult {
        object Success : RefreshResult()
        object NotLoggedIn : RefreshResult()
        data class Failed(val message: String) : RefreshResult()
    }
}
