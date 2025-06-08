package me.wcy.music.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresApi
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import me.wcy.music.net.datasource.MusicUrlCache
import me.wcy.music.net.datasource.ModernMusicCacheDataSourceFactory
import me.wcy.music.utils.LogUtils
import kotlinx.coroutines.*
import kotlin.math.max
import kotlin.math.min

/**
 * 智能缓存管理器
 * 
 * 功能：
 * - 基于网络状况动态调整缓存策略
 * - 智能内存压力监听和缓存清理
 * - 性能监控和优化建议
 * - 自适应缓存领先时间调整
 * 
 * Created by wangchenyan.top on 2024/12/21.
 */
class SmartCacheManager(
    private val context: Context
) : DefaultLifecycleObserver {
    
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    
    // 网络状态
    private var networkType: NetworkType = NetworkType.UNKNOWN
    private var networkSpeed: NetworkSpeed = NetworkSpeed.UNKNOWN
    
    // 动态缓存参数
    private var dynamicCacheLeadTime: Long = DEFAULT_CACHE_LEAD_TIME
    private var cacheCheckInterval: Long = DEFAULT_CACHE_CHECK_INTERVAL
    
    // 性能监控
    private val performanceMonitor = CachePerformanceMonitor()
    
    init {
        updateNetworkStatus()
        startPerformanceMonitoring()
    }
    
    /**
     * 获取当前建议的缓存领先时间
     */
    fun getOptimalCacheLeadTime(): Long = dynamicCacheLeadTime
    
    /**
     * 获取当前建议的缓存检查间隔
     */
    fun getOptimalCacheCheckInterval(): Long = cacheCheckInterval
    
    /**
     * 根据当前播放状态调整缓存策略
     */
    fun adjustCacheStrategy(
        currentPosition: Long,
        bufferedPosition: Long,
        duration: Long,
        isPlaying: Boolean
    ): CacheStrategy {
        updateNetworkStatus()
        
        val cacheLeadTime = bufferedPosition - currentPosition
        val playbackProgress = if (duration > 0) currentPosition.toFloat() / duration else 0f
        
        // 基于网络状况和播放进度智能调整
        val strategy = when {
            // 网络较差时采用激进缓存策略
            networkSpeed == NetworkSpeed.SLOW -> {
                dynamicCacheLeadTime = max(15_000L, (DEFAULT_CACHE_LEAD_TIME * 1.5).toLong())
                cacheCheckInterval = max(1_000L, DEFAULT_CACHE_CHECK_INTERVAL / 2)
                CacheStrategy.AGGRESSIVE
            }
            
            // 网络良好时采用平衡策略
            networkSpeed == NetworkSpeed.FAST -> {
                dynamicCacheLeadTime = min(8_000L, (DEFAULT_CACHE_LEAD_TIME * 0.8).toLong())
                cacheCheckInterval = min(3_000L, (DEFAULT_CACHE_CHECK_INTERVAL * 1.2).toLong())
                CacheStrategy.BALANCED
            }
            
            // 播放接近结尾时减少缓存
            playbackProgress > 0.8f -> {
                dynamicCacheLeadTime = DEFAULT_CACHE_LEAD_TIME / 2
                cacheCheckInterval = DEFAULT_CACHE_CHECK_INTERVAL * 2
                CacheStrategy.CONSERVATIVE
            }
            
            // 默认策略
            else -> {
                dynamicCacheLeadTime = DEFAULT_CACHE_LEAD_TIME
                cacheCheckInterval = DEFAULT_CACHE_CHECK_INTERVAL
                CacheStrategy.BALANCED
            }
        }
        
        // 记录性能数据
        performanceMonitor.recordCacheEvent(
            cacheLeadTime = cacheLeadTime,
            networkType = networkType,
            strategy = strategy
        )
        
        return strategy
    }
    
    /**
     * 智能缓存清理
     * 基于内存压力和使用情况进行清理
     */
    fun performSmartCleanup() {
        scope.launch {
            try {
                val memoryInfo = getMemoryInfo()
                
                when {
                    memoryInfo.lowMemory -> {
                        LogUtils.i(TAG, "检测到内存压力，执行激进缓存清理")
                        // 激进清理：清理所有过期缓存和部分最近未使用的缓存
                        MusicUrlCache.cleanExpiredCache()
                        ModernMusicCacheDataSourceFactory.clearExpiredCache(context)
                    }
                    
                    memoryInfo.memoryRatio > 0.8f -> {
                        LogUtils.i(TAG, "内存使用率过高，执行温和缓存清理")
                        // 温和清理：仅清理过期缓存
                        MusicUrlCache.cleanExpiredCache()
                    }
                    
                    else -> {
                        LogUtils.d(TAG, "内存使用正常，跳过缓存清理")
                    }
                }
            } catch (e: Exception) {
                LogUtils.e(TAG, "智能缓存清理失败", e)
            }
        }
    }
    
    /**
     * 获取缓存性能报告
     */
    fun getPerformanceReport(): CachePerformanceReport {
        val urlCacheStats = MusicUrlCache.getCacheStats()
        val diskCacheStats = ModernMusicCacheDataSourceFactory.getCacheStats(context)
        
        return CachePerformanceReport(
            urlCacheInfo = urlCacheStats,
            diskCacheInfo = diskCacheStats.toString(),
            networkType = networkType,
            currentStrategy = getCurrentStrategy(),
            recommendations = generateOptimizationRecommendations()
        )
    }
    
    /**
     * 获取简化的缓存统计信息（兼容旧接口）
     */
    suspend fun getCacheStats(): CacheInfo = withContext(Dispatchers.IO) {
        try {
            val stats = ModernMusicCacheDataSourceFactory.getCacheStats(context)
            CacheInfo(
                totalSize = stats.cacheSize,
                maxSize = stats.maxCacheSize,
                usagePercentage = stats.usagePercentage,
                availableSpace = stats.availableSpace,
                isHealthy = stats.usagePercentage < 90.0
            )
        } catch (e: Exception) {
            LogUtils.e(TAG, "获取缓存统计信息失败", e)
            CacheInfo.empty()
        }
    }
    
    /**
     * 清理缓存（兼容旧接口）
     */
    suspend fun clearCache(): Boolean = withContext(Dispatchers.IO) {
        try {
            ModernMusicCacheDataSourceFactory.clearCache()
            LogUtils.i(TAG, "缓存清理成功")
            true
        } catch (e: Exception) {
            LogUtils.e(TAG, "缓存清理失败", e)
            false
        }
    }
    
    /**
     * 更新网络状态
     */
    private fun updateNetworkStatus() {
        try {
            val activeNetwork = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            
            networkType = when {
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> NetworkType.WIFI
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> NetworkType.CELLULAR
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> NetworkType.ETHERNET
                else -> NetworkType.UNKNOWN
            }
            
            // 简单的网络速度评估（可以后续添加更复杂的测速逻辑）
            networkSpeed = when (networkType) {
                NetworkType.WIFI, NetworkType.ETHERNET -> NetworkSpeed.FAST
                NetworkType.CELLULAR -> NetworkSpeed.MEDIUM
                NetworkType.UNKNOWN -> NetworkSpeed.SLOW
            }
            
        } catch (e: Exception) {
            LogUtils.w(TAG, "更新网络状态失败", e)
            networkType = NetworkType.UNKNOWN
            networkSpeed = NetworkSpeed.UNKNOWN
        }
    }
    
    /**
     * 获取内存信息
     */
    private fun getMemoryInfo(): MemoryInfo {
        return try {
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
            val memInfo = android.app.ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memInfo)
            
            val totalMemory = memInfo.totalMem
            val availableMemory = memInfo.availMem
            val usedMemory = totalMemory - availableMemory
            val memoryRatio = usedMemory.toFloat() / totalMemory
            
            MemoryInfo(
                totalMemory = totalMemory,
                availableMemory = availableMemory,
                usedMemory = usedMemory,
                memoryRatio = memoryRatio,
                lowMemory = memInfo.lowMemory
            )
        } catch (e: Exception) {
            LogUtils.w(TAG, "获取内存信息失败", e)
            MemoryInfo()
        }
    }
    
    /**
     * 开始性能监控
     */
    private fun startPerformanceMonitoring() {
        scope.launch {
            while (true) {
                try {
                    performSmartCleanup()
                    updateNetworkStatus()
                    delay(MONITORING_INTERVAL)
                } catch (e: Exception) {
                    LogUtils.e(TAG, "性能监控异常", e)
                    delay(MONITORING_INTERVAL * 2) // 出错时延长间隔
                }
            }
        }
    }
    
    /**
     * 获取当前策略
     */
    private fun getCurrentStrategy(): CacheStrategy {
        return when {
            dynamicCacheLeadTime > DEFAULT_CACHE_LEAD_TIME -> CacheStrategy.AGGRESSIVE
            dynamicCacheLeadTime < DEFAULT_CACHE_LEAD_TIME -> CacheStrategy.CONSERVATIVE
            else -> CacheStrategy.BALANCED
        }
    }
    
    /**
     * 生成优化建议
     */
    private fun generateOptimizationRecommendations(): List<String> {
        val recommendations = mutableListOf<String>()
        
        when (networkType) {
            NetworkType.CELLULAR -> {
                recommendations.add("检测到移动网络，建议在WiFi环境下预加载更多内容")
            }
            NetworkType.UNKNOWN -> {
                recommendations.add("网络状态不明，建议检查网络连接")
            }
            else -> {}
        }
        
        val memoryInfo = getMemoryInfo()
        if (memoryInfo.lowMemory) {
            recommendations.add("设备内存不足，已自动优化缓存策略")
        }
        
        if (recommendations.isEmpty()) {
            recommendations.add("缓存性能良好，无需优化")
        }
        
        return recommendations
    }
    
    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        performSmartCleanup()
    }
    
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        scope.cancel()
    }
    
    companion object {
        private const val TAG = "SmartCacheManager"
        private const val DEFAULT_CACHE_LEAD_TIME = 10_000L // 10秒
        private const val DEFAULT_CACHE_CHECK_INTERVAL = 3_000L // 3秒（从2秒增加到3秒）
        private const val MONITORING_INTERVAL = 30_000L // 30秒监控间隔
    }
}

/**
 * 网络类型
 */
enum class NetworkType {
    WIFI, CELLULAR, ETHERNET, UNKNOWN
}

/**
 * 网络速度
 */
enum class NetworkSpeed {
    FAST, MEDIUM, SLOW, UNKNOWN
}

/**
 * 缓存策略
 */
enum class CacheStrategy {
    AGGRESSIVE,    // 激进缓存：网络差时多缓存
    BALANCED,      // 平衡缓存：正常策略
    CONSERVATIVE   // 保守缓存：网络好或接近结尾时少缓存
}

/**
 * 内存信息
 */
data class MemoryInfo(
    val totalMemory: Long = 0,
    val availableMemory: Long = 0,
    val usedMemory: Long = 0,
    val memoryRatio: Float = 0f,
    val lowMemory: Boolean = false
)

/**
 * 缓存信息（简化版）
 */
data class CacheInfo(
    val totalSize: Long,
    val maxSize: Long,
    val usagePercentage: Double,
    val availableSpace: Long,
    val isHealthy: Boolean
) {
    val totalSizeFormatted: String get() = formatBytes(totalSize)
    val maxSizeFormatted: String get() = formatBytes(maxSize)
    val availableSpaceFormatted: String get() = formatBytes(availableSpace)
    
    companion object {
        fun empty() = CacheInfo(0, 0, 0.0, 0, true)
        
        private fun formatBytes(bytes: Long): String {
            return when {
                bytes >= 1024 * 1024 * 1024 -> String.format("%.1f GB", bytes / (1024.0 * 1024.0 * 1024.0))
                bytes >= 1024 * 1024 -> String.format("%.1f MB", bytes / (1024.0 * 1024.0))
                bytes >= 1024 -> String.format("%.1f KB", bytes / 1024.0)
                else -> "$bytes B"
            }
        }
    }
}

/**
 * 缓存性能报告
 */
data class CachePerformanceReport(
    val urlCacheInfo: String,
    val diskCacheInfo: String,
    val networkType: NetworkType,
    val currentStrategy: CacheStrategy,
    val recommendations: List<String>
)

/**
 * 缓存性能监控器
 */
private class CachePerformanceMonitor {
    private val cacheEvents = mutableListOf<CacheEvent>()
    
    fun recordCacheEvent(
        cacheLeadTime: Long,
        networkType: NetworkType,
        strategy: CacheStrategy
    ) {
        // 保持最近100个事件，避免内存泄漏
        if (cacheEvents.size >= 100) {
            cacheEvents.removeAt(0)  // 替换removeFirst()以兼容Android API
        }
        
        cacheEvents.add(CacheEvent(
            timestamp = System.currentTimeMillis(),
            cacheLeadTime = cacheLeadTime,
            networkType = networkType,
            strategy = strategy
        ))
    }
    
    fun getAverageCacheLeadTime(): Long {
        return if (cacheEvents.isNotEmpty()) {
            cacheEvents.map { it.cacheLeadTime }.average().toLong()
        } else {
            0L
        }
    }
}

/**
 * 缓存事件
 */
private data class CacheEvent(
    val timestamp: Long,
    val cacheLeadTime: Long,
    val networkType: NetworkType,
    val strategy: CacheStrategy
)