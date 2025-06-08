package me.wcy.music.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.wcy.music.account.service.UserService
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
 * - ViewModel不持有View的直接引用
 * - 通过StateFlow提供响应式数据
 * - 处理业务逻辑，不涉及UI操作
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userService: UserService
) : ViewModel() {

    // 登录加载状态
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // 错误信息
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

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
     * 清除错误信息
     */
    fun clearError() {
        _errorMessage.value = null
    }

    /**
     * 设置加载状态
     */
    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    /**
     * 设置错误信息
     */
    fun setError(message: String) {
        _errorMessage.value = message
    }
} 