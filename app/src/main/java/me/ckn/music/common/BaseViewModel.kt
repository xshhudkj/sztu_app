package me.ckn.music.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel基类
 * 提供统一的状态管理、错误处理和加载状态管理
 * 
 * 设计原则：
 * 1. 统一Loading/Success/Error状态模式
 * 2. 标准化错误处理机制
 * 3. 简化子类实现
 * 4. 支持Android Automotive优化
 * 
 * Created for Architecture Optimization
 */
abstract class BaseViewModel : ViewModel() {

    // 通用加载状态
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // 通用错误信息
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // 网络状态
    private val _isNetworkError = MutableStateFlow(false)
    val isNetworkError: StateFlow<Boolean> = _isNetworkError.asStateFlow()

    /**
     * UI状态封装
     * 统一Loading/Success/Error状态模式
     */
    sealed class UiState<out T> {
        object Loading : UiState<Nothing>()
        data class Success<T>(val data: T) : UiState<T>()
        data class Error(val message: String, val isNetworkError: Boolean = false) : UiState<Nothing>()
        object Empty : UiState<Nothing>()
    }

    /**
     * 设置加载状态
     */
    protected fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    /**
     * 设置错误信息
     * @param message 错误消息
     * @param isNetworkError 是否为网络错误
     */
    protected fun setError(message: String, isNetworkError: Boolean = false) {
        _errorMessage.value = message
        _isNetworkError.value = isNetworkError
        _isLoading.value = false
    }

    /**
     * 清除错误信息
     */
    fun clearError() {
        _errorMessage.value = null
        _isNetworkError.value = false
    }

    /**
     * 创建成功状态的StateFlow
     */
    protected fun <T> createSuccessState(data: T): StateFlow<UiState<T>> {
        return MutableStateFlow<UiState<T>>(UiState.Success(data)).asStateFlow()
    }

    /**
     * 创建加载状态的StateFlow
     */
    protected fun <T> createLoadingState(): StateFlow<UiState<T>> {
        return MutableStateFlow<UiState<T>>(UiState.Loading).asStateFlow()
    }

    /**
     * 创建错误状态的StateFlow
     */
    protected fun <T> createErrorState(message: String, isNetworkError: Boolean = false): StateFlow<UiState<T>> {
        return MutableStateFlow<UiState<T>>(UiState.Error(message, isNetworkError)).asStateFlow()
    }

    /**
     * 安全执行操作，自动处理错误
     * 专为Android Automotive优化，减少UI阻塞
     */
    protected suspend fun <T> safeExecute(
        operation: suspend () -> T,
        onSuccess: (T) -> Unit = {},
        onError: (String, Boolean) -> Unit = { msg, isNetwork -> setError(msg, isNetwork) },
        showLoading: Boolean = true
    ) {
        try {
            if (showLoading) setLoading(true)
            val result = operation()
            onSuccess(result)
        } catch (e: Exception) {
            val isNetworkError = isNetworkException(e)
            val errorMessage = getErrorMessage(e)
            onError(errorMessage, isNetworkError)
        } finally {
            if (showLoading) setLoading(false)
        }
    }

    /**
     * 判断是否为网络异常
     */
    private fun isNetworkException(exception: Exception): Boolean {
        return when (exception) {
            is java.net.UnknownHostException,
            is java.net.SocketTimeoutException,
            is java.net.ConnectException -> true
            else -> exception.message?.contains("network", ignoreCase = true) == true
        }
    }

    /**
     * 获取用户友好的错误消息
     */
    private fun getErrorMessage(exception: Exception): String {
        return when (exception) {
            is java.net.UnknownHostException -> "网络连接失败，请检查网络设置"
            is java.net.SocketTimeoutException -> "网络请求超时，请重试"
            is java.net.ConnectException -> "无法连接到服务器"
            else -> exception.message ?: "操作失败，请重试"
        }
    }

    /**
     * 重试操作
     * 提供标准化的重试机制
     */
    open fun retry() {
        clearError()
        // 子类可以重写此方法实现具体的重试逻辑
    }
} 