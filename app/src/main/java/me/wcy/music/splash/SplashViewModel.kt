package me.wcy.music.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 启动页ViewModel
 * 
 * 职责：
 * 1. 管理启动页的业务逻辑
 * 2. 控制启动序列的执行
 * 3. 提供导航状态给View层
 * 
 * Created for SplashLogin Module
 */
@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    /**
     * 启动页导航事件
     */
    sealed class NavigationEvent {
        object NavigateToMain : NavigationEvent()
    }

    /**
     * 开始启动序列
     * 等待指定时间后触发导航到主界面
     */
    fun startSplashSequence(delayMillis: Long = 2000L) {
        viewModelScope.launch {
            _isLoading.value = true
            
            // 等待指定时间
            delay(delayMillis)
            
            _isLoading.value = false
            _navigationEvent.value = NavigationEvent.NavigateToMain
        }
    }

    /**
     * 清除导航事件
     */
    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }
} 