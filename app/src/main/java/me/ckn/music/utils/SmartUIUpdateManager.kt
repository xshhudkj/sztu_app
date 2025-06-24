/**
 * WhisperPlay Music Player
 *
 * 文件描述：智能UI更新管理器，用于优化UI刷新性能。
 * File Description: Smart UI Update Manager for optimizing UI refresh performance.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.utils

import android.app.ActivityManager
import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*
import kotlin.math.abs

/**
 * 智能UI更新管理器
 * Smart UI Update Manager
 *
 * 主要功能：
 * Main Functions:
 * - 根据设备性能、UI可见性和用户交互状态动态调整UI更新频率 / Dynamically adjusts UI update frequency based on device performance, UI visibility, and user interaction state.
 * - 提供带有防抖动功能的智能更新器，减少不必要的UI刷新 / Provides smart updaters with debouncing to reduce unnecessary UI refreshes.
 * - 绑定生命周期，自动管理更新状态 / Binds to lifecycle to automatically manage update states.
 *
 * 使用示例：
 * Usage Example:
 * ```kotlin
 * // 在 Activity/Fragment 中创建实例
 * // Create an instance in an Activity/Fragment
 * val uiUpdateManager = SmartUIUpdateManager(requireContext())
 * lifecycle.addObserver(uiUpdateManager)
 *
 * // 获取更新器
 * // Get updaters
 * val progressUpdater = uiUpdateManager.createProgressUpdater()
 * val bufferUpdater = uiUpdateManager.createBufferUpdater()
 *
 * // 在更新循环中检查是否需要更新
 * // Check if an update is needed in the update loop
 * if (progressUpdater.shouldUpdate(newProgress, System.currentTimeMillis())) {
 *     // 更新进度条 / Update progress bar
 *     progressUpdater.markUpdated(newProgress, System.currentTimeMillis())
 * }
 * ```
 *
 * @author ckn
 * @since 2025-06-10
 */
class SmartUIUpdateManager(
    private val context: Context
) : DefaultLifecycleObserver {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    private var devicePerformanceLevel: PerformanceLevel = PerformanceLevel.MEDIUM
    private var progressUpdateInterval: Long = DEFAULT_PROGRESS_UPDATE_INTERVAL
    private var bufferUpdateInterval: Long = DEFAULT_BUFFER_UPDATE_INTERVAL
    private var isUIVisible = true
    private var isUserInteracting = true

    init {
        evaluateDevicePerformance()
        adjustUpdateIntervals()
    }

    /**
     * 智能进度更新器
     * Smart Progress Updater
     *
     * @param updateInterval 基础更新间隔 / Base update interval
     * @param threshold 数值变化阈值，超过此阈值则强制更新 / Value change threshold, forces update if exceeded
     */
    class SmartProgressUpdater(
        private val updateInterval: Long,
        private val threshold: Long = 500L // 500ms
    ) {
        private var lastValue: Long = 0
        private var lastUpdateTime: Long = 0

        /**
         * 判断是否应该更新UI
         * Determines whether the UI should be updated.
         *
         * @param currentValue 当前值 / Current value
         * @param currentTime 当前时间 / Current time
         * @return 如果需要更新则返回 true / True if an update is needed
         */
        fun shouldUpdate(currentValue: Long, currentTime: Long): Boolean {
            val timeDiff = currentTime - lastUpdateTime
            val valueDiff = abs(currentValue - lastValue)

            return when {
                timeDiff >= updateInterval -> true
                valueDiff >= threshold -> true
                else -> false
            }
        }

        /**
         * 标记UI已更新
         * Marks the UI as updated.
         *
         * @param value 更新后的值 / The updated value
         * @param time 更新时间 / The update time
         */
        fun markUpdated(value: Long, time: Long) {
            lastValue = value
            lastUpdateTime = time
        }
    }

    /**
     * 智能缓冲更新器
     * Smart Buffer Updater
     *
     * @param updateInterval 基础更新间隔 / Base update interval
     * @param percentageThreshold 百分比变化阈值 / Percentage change threshold
     */
    class SmartBufferUpdater(
        private val updateInterval: Long,
        private val percentageThreshold: Int = 2 // 2%
    ) {
        private var lastPercentage: Int = 0
        private var lastUpdateTime: Long = 0

        /**
         * 判断是否应该更新UI
         * Determines whether the UI should be updated.
         *
         * @param currentPercentage 当前百分比 / Current percentage
         * @param currentTime 当前时间 / Current time
         * @return 如果需要更新则返回 true / True if an update is needed
         */
        fun shouldUpdate(currentPercentage: Int, currentTime: Long): Boolean {
            val timeDiff = currentTime - lastUpdateTime
            val percentageDiff = abs(currentPercentage - lastPercentage)

            return when {
                timeDiff >= updateInterval -> true
                percentageDiff >= percentageThreshold -> true
                else -> false
            }
        }

        /**
         * 标记UI已更新
         * Marks the UI as updated.
         *
         * @param percentage 更新后的百分比 / The updated percentage
         * @param time 更新时间 / The update time
         */
        fun markUpdated(percentage: Int, time: Long) {
            lastPercentage = percentage
            lastUpdateTime = time
        }
    }

    /**
     * 创建一个智能进度更新器实例
     * Creates a SmartProgressUpdater instance.
     *
     * @return [SmartProgressUpdater]
     */
    fun createProgressUpdater(): SmartProgressUpdater {
        return SmartProgressUpdater(progressUpdateInterval)
    }

    /**
     * 创建一个智能缓冲更新器实例
     * Creates a SmartBufferUpdater instance.
     *
     * @return [SmartBufferUpdater]
     */
    fun createBufferUpdater(): SmartBufferUpdater {
        return SmartBufferUpdater(bufferUpdateInterval)
    }

    /**
     * 获取当前设备性能下最优的主循环间隔
     * Gets the optimal main loop interval for the current device performance.
     *
     * @return 循环间隔（毫秒） / Loop interval in milliseconds
     */
    fun getOptimalLoopInterval(): Long {
        return when (devicePerformanceLevel) {
            PerformanceLevel.HIGH -> 50L
            PerformanceLevel.MEDIUM -> 100L
            PerformanceLevel.LOW -> 200L
        }
    }

    /**
     * 获取当前推荐的进度更新间隔
     * Gets the currently recommended progress update interval.
     *
     * @return 间隔（毫秒） / Interval in milliseconds
     */
    fun getProgressUpdateInterval(): Long = progressUpdateInterval

    /**
     * 获取当前推荐的缓冲更新间隔
     * Gets the currently recommended buffer update interval.
     *
     * @return 间隔（毫秒） / Interval in milliseconds
     */
    fun getBufferUpdateInterval(): Long = bufferUpdateInterval

    /**
     * 设置UI可见状态
     * Sets the UI visibility state.
     *
     * @param visible UI是否可见 / Whether the UI is visible
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
     * Sets the user interaction state.
     *
     * @param interacting 用户是否正在交互 / Whether the user is interacting
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
     * Evaluates the device performance level.
     */
    private fun evaluateDevicePerformance() {
        try {
            val memInfo = ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memInfo)

            val totalRAM = memInfo.totalMem / (1024 * 1024) // MB

            devicePerformanceLevel = when {
                totalRAM >= 6144 -> PerformanceLevel.HIGH
                totalRAM >= 3072 -> PerformanceLevel.MEDIUM
                else -> PerformanceLevel.LOW
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
     * Adjusts update intervals based on device performance and UI state.
     */
    private fun adjustUpdateIntervals() {
        val baseProgressInterval = when (devicePerformanceLevel) {
            PerformanceLevel.HIGH -> 100L
            PerformanceLevel.MEDIUM -> 200L
            PerformanceLevel.LOW -> 400L
        }

        val baseBufferInterval = when (devicePerformanceLevel) {
            PerformanceLevel.HIGH -> 500L
            PerformanceLevel.MEDIUM -> 1000L
            PerformanceLevel.LOW -> 2000L
        }

        val uiMultiplier = if (!isUIVisible) 4 else if (!isUserInteracting) 2 else 1

        progressUpdateInterval = baseProgressInterval * uiMultiplier
        bufferUpdateInterval = baseBufferInterval * uiMultiplier

        LogUtils.performance("SmartUIUpdate") {
            "更新间隔调整: 进度=${progressUpdateInterval}ms, 缓冲=${bufferUpdateInterval}ms, UI可见=$isUIVisible, 用户交互=$isUserInteracting"
        }
    }

    /**
     * 获取当前的性能报告
     * Gets the current performance report.
     *
     * @return [UIPerformanceReport]
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
 * Device Performance Level
 */
enum class PerformanceLevel {
    HIGH,    // 高性能设备 / High-performance device
    MEDIUM,  // 中等性能设备 / Medium-performance device
    LOW      // 低性能设备 / Low-performance device
}

/**
 * UI性能报告数据类
 * Data class for UI performance report.
 */
data class UIPerformanceReport(
    val devicePerformanceLevel: PerformanceLevel,
    val progressUpdateInterval: Long,
    val bufferUpdateInterval: Long,
    val isUIVisible: Boolean,
    val isUserInteracting: Boolean,
    val optimalLoopInterval: Long
)