package me.wcy.music.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.abs

/**
 * 智能UI更新管理器
 * 
 * 功能：
 * - 自适应更新频率：根据设备性能和UI状态动态调整
 * - 防抖动更新：避免频繁的微小数值变化导致的UI刷新
 * - 性能感知：检测设备性能自动优化更新策略
 * - 内存友好：避免过度UI更新导致的内存压力
 * 
 * Created by wangchenyan.top on 2024/12/21.
 */
class SmartUIUpdateManager(
    private val context: Context
) : DefaultLifecycleObserver {
    
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    
    // 设备性能等级
    private var devicePerformanceLevel: PerformanceLevel = PerformanceLevel.MEDIUM
    
    // 动态更新间隔
    private var progressUpdateInterval: Long = DEFAULT_PROGRESS_UPDATE_INTERVAL
    private var bufferUpdateInterval: Long = DEFAULT_BUFFER_UPDATE_INTERVAL
    
    // UI可见状态
    private var isUIVisible = true
    private var isUserInteracting = true
    
    init {
        evaluateDevicePerformance()
        adjustUpdateIntervals()
    }
    
    /**
     * 智能进度更新器
     * 支持防抖动和自适应频率
     */
    class SmartProgressUpdater(
        private val updateInterval: Long,
        private val threshold: Long = 500L // 500ms的变化阈值
    ) {
        private var lastValue: Long = 0
        private var lastUpdateTime: Long = 0
        
        fun shouldUpdate(currentValue: Long, currentTime: Long): Boolean {
            val timeDiff = currentTime - lastUpdateTime
            val valueDiff = abs(currentValue - lastValue)
            
            return when {
                // 强制更新：时间间隔达到或值变化较大
                timeDiff >= updateInterval -> true
                valueDiff >= threshold -> true
                else -> false
            }
        }
        
        fun markUpdated(value: Long, time: Long) {
            lastValue = value
            lastUpdateTime = time
        }
    }
    
    /**
     * 智能缓冲更新器
     * 避免频繁的缓冲百分比更新
     */
    class SmartBufferUpdater(
        private val updateInterval: Long,
        private val percentageThreshold: Int = 2 // 2%的变化阈值
    ) {
        private var lastPercentage: Int = 0
        private var lastUpdateTime: Long = 0
        
        fun shouldUpdate(currentPercentage: Int, currentTime: Long): Boolean {
            val timeDiff = currentTime - lastUpdateTime
            val percentageDiff = abs(currentPercentage - lastPercentage)
            
            return when {
                timeDiff >= updateInterval -> true
                percentageDiff >= percentageThreshold -> true
                else -> false
            }
        }
        
        fun markUpdated(percentage: Int, time: Long) {
            lastPercentage = percentage
            lastUpdateTime = time
        }
    }
    
    /**
     * 创建智能进度更新器
     */
    fun createProgressUpdater(): SmartProgressUpdater {
        return SmartProgressUpdater(progressUpdateInterval)
    }
    
    /**
     * 创建智能缓冲更新器
     */
    fun createBufferUpdater(): SmartBufferUpdater {
        return SmartBufferUpdater(bufferUpdateInterval)
    }
    
    /**
     * 获取当前推荐的主循环间隔
     */
    fun getOptimalLoopInterval(): Long {
        return when (devicePerformanceLevel) {
            PerformanceLevel.HIGH -> 50L      // 高性能设备：50ms
            PerformanceLevel.MEDIUM -> 100L   // 中等设备：100ms
            PerformanceLevel.LOW -> 200L      // 低端设备：200ms
        }
    }
    
    /**
     * 获取当前推荐的进度更新间隔
     */
    fun getProgressUpdateInterval(): Long = progressUpdateInterval
    
    /**
     * 获取当前推荐的缓冲更新间隔
     */
    fun getBufferUpdateInterval(): Long = bufferUpdateInterval
    
    /**
     * 设置UI可见状态
     * 当UI不可见时降低更新频率
     */
    fun setUIVisible(visible: Boolean) {
        if (isUIVisible != visible) {
            isUIVisible = visible
            adjustUpdateIntervals()
            LogUtils.performance("SmartUIUpdate") { "UI可见状态变更: $visible, 调整更新间隔" }
        }
    }
    
    /**
     * 设置用户交互状态
     * 用户不交互时可以降低更新频率
     */
    fun setUserInteracting(interacting: Boolean) {
        if (isUserInteracting != interacting) {
            isUserInteracting = interacting
            adjustUpdateIntervals()
            LogUtils.performance("SmartUIUpdate") { "用户交互状态变更: $interacting" }
        }
    }
    
    /**
     * 评估设备性能等级
     */
    private fun evaluateDevicePerformance() {
        try {
            val memInfo = ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memInfo)
            
            val totalRAM = memInfo.totalMem / (1024 * 1024) // MB
            
            devicePerformanceLevel = when {
                totalRAM >= 6144 -> PerformanceLevel.HIGH      // 6GB+
                totalRAM >= 3072 -> PerformanceLevel.MEDIUM    // 3GB+
                else -> PerformanceLevel.LOW                    // <3GB
            }
            
            LogUtils.performance("SmartUIUpdate") { 
                "设备性能评估: RAM=${totalRAM}MB, 等级=$devicePerformanceLevel" 
            }
        } catch (e: Exception) {
            LogUtils.w("SmartUIUpdate", "设备性能评估失败，使用默认设置", e)
            devicePerformanceLevel = PerformanceLevel.MEDIUM
        }
    }
    
    /**
     * 根据设备性能和UI状态调整更新间隔
     */
    private fun adjustUpdateIntervals() {
        val baseProgressInterval = when (devicePerformanceLevel) {
            PerformanceLevel.HIGH -> 100L     // 高性能：100ms
            PerformanceLevel.MEDIUM -> 200L   // 中等：200ms
            PerformanceLevel.LOW -> 400L      // 低端：400ms
        }
        
        val baseBufferInterval = when (devicePerformanceLevel) {
            PerformanceLevel.HIGH -> 500L     // 高性能：500ms
            PerformanceLevel.MEDIUM -> 1000L  // 中等：1s
            PerformanceLevel.LOW -> 2000L     // 低端：2s
        }
        
        // 根据UI状态调整
        val uiMultiplier = if (!isUIVisible) 4 else if (!isUserInteracting) 2 else 1
        
        progressUpdateInterval = baseProgressInterval * uiMultiplier
        bufferUpdateInterval = baseBufferInterval * uiMultiplier
        
        LogUtils.performance("SmartUIUpdate") { 
            "更新间隔调整: 进度=${progressUpdateInterval}ms, 缓冲=${bufferUpdateInterval}ms, UI可见=$isUIVisible, 用户交互=$isUserInteracting" 
        }
    }
    
    /**
     * 获取性能报告
     */
    fun getPerformanceReport(): UIPerformanceReport {
        return UIPerformanceReport(
            devicePerformanceLevel = devicePerformanceLevel,
            progressUpdateInterval = progressUpdateInterval,
            bufferUpdateInterval = bufferUpdateInterval,
            isUIVisible = isUIVisible,
            isUserInteracting = isUserInteracting,
            optimalLoopInterval = getOptimalLoopInterval()
        )
    }
    
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        setUIVisible(true)
        setUserInteracting(true)
    }
    
    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        setUIVisible(false)
        setUserInteracting(false)
    }
    
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        scope.cancel()
    }
    
    companion object {
        private const val DEFAULT_PROGRESS_UPDATE_INTERVAL = 200L // 200ms
        private const val DEFAULT_BUFFER_UPDATE_INTERVAL = 1000L  // 1s
    }
}

/**
 * 设备性能等级
 */
enum class PerformanceLevel {
    HIGH,    // 高性能设备
    MEDIUM,  // 中等性能设备
    LOW      // 低性能设备
}

/**
 * UI性能报告
 */
data class UIPerformanceReport(
    val devicePerformanceLevel: PerformanceLevel,
    val progressUpdateInterval: Long,
    val bufferUpdateInterval: Long,
    val isUIVisible: Boolean,
    val isUserInteracting: Boolean,
    val optimalLoopInterval: Long
) 