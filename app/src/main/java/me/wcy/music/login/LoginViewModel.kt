package me.wcy.music.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.wcy.music.account.service.UserService
import me.wcy.music.common.BaseViewModel
import javax.inject.Inject

/**
 * 登录页面ViewModel
 * 
 * 职责：
 * 1. 管理登录状态和用户数据
 * 2. 处理登录相关的业务逻辑
 * 3. 提供响应式数据流给UI层
 * 
 * 遵循MVVM架构原则：
 * - 继承BaseViewModel，使用统一的状态管理
 * - ViewModel不持有View的直接引用
 * - 通过StateFlow提供响应式数据
 * - 处理业务逻辑，不涉及UI操作
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userService: UserService
) : BaseViewModel() {

    /**
     * 检查用户是否已登录
     */
    fun isUserLoggedIn(): Boolean {
        return userService.isLogin()
    }

    /**
     * 获取用户profile
     */
    fun getUserProfile() = userService.profile

    /**
     * 执行登录操作
     * 使用BaseViewModel的safeExecute方法，自动处理加载状态和错误
     */
    fun performLogin(username: String, password: String) {
        viewModelScope.launch {
            safeExecute(
                operation = {
                    // 这里应该调用实际的登录API
                    // userService.login(username, password)
                    // 为了演示，暂时模拟
                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        "登录成功"
                    } else {
                        throw IllegalArgumentException("用户名或密码不能为空")
                    }
                },
                onSuccess = { result ->
                    // 登录成功处理
                },
                onError = { message, isNetworkError ->
                    // 使用BaseViewModel的错误处理
                    setError(message, isNetworkError)
                }
            )
        }
    }
} 