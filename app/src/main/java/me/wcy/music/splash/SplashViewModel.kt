package me.wcy.music.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.wcy.music.common.BaseViewModel
import javax.inject.Inject

/**
 * 启动页ViewModel
 * 
 * 职责：
 * 1. 管理启动页的业务逻辑
 * 2. 控制启动序列的执行
 * 3. 提供导航状态给View层
 * 
 * 使用BaseViewModel统一架构模式
 * Created for SplashLogin Module
 */
@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent.asStateFlow()

    /**
     * 启动页导航事件
     */
    sealed class NavigationEvent {
        object NavigateToMain : NavigationEvent()
    }

    /**
     * 开始启动序列
     * 等待指定时间后触发导航到主界面
     * 使用BaseViewModel的状态管理
     */
    fun startSplashSequence(delayMillis: Long = 2000L) {
        viewModelScope.launch {
            safeExecute(
                operation = {
                    delay(delayMillis)
                    NavigationEvent.NavigateToMain
                },
                onSuccess = { navigationEvent ->
                    _navigationEvent.value = navigationEvent
                },
                showLoading = true
            )
        }
    }

    /**
     * 清除导航事件
     */
    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }
} 