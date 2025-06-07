package me.wcy.music.utils

import android.content.Context
import android.util.Log
import androidx.media3.common.util.UnstableApi
import me.wcy.music.net.datasource.ModernMusicCacheDataSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 音乐缓存管理器
 * 提供缓存监控、清理和性能优化功能
 * 
 * Created by wangchenyan.top on 2024/12/20.
 */
@UnstableApi
object MusicCacheManager {
    
    private const val TAG = "MusicCacheManager"
    
    /**
     * 获取缓存统计信息
     */
    suspend fun getCacheStats(context: Context): CacheInfo = withContext(Dispatchers.IO) {
        try {
            val stats = ModernMusicCacheDataSourceFactory.getCacheStats(context)
            CacheInfo(
                totalSize = stats.cacheSize,
                maxSize = stats.maxCacheSize,
                usagePercentage = stats.usagePercentage,
                availableSpace = stats.availableSpace,
                isHealthy = stats.usagePercentage < 90.0 // 使用率低于90%认为是健康的
            )
        } catch (e: Exception) {
            Log.e(TAG, "获取缓存统计信息失败", e)
            CacheInfo.empty()
        }
    }
    
    /**
     * 清理缓存
     */
    suspend fun clearCache(context: Context): Boolean = withContext(Dispatchers.IO) {
        try {
            ModernMusicCacheDataSourceFactory.clearCache()
            Log.i(TAG, "缓存清理成功")
            true
        } catch (e: Exception) {
            Log.e(TAG, "缓存清理失败", e)
            false
        }
    }
    
    /**
     * 检查缓存健康状态
     */
    suspend fun checkCacheHealth(context: Context): CacheHealthReport = withContext(Dispatchers.IO) {
        val cacheInfo = getCacheStats(context)
        
        val issues = mutableListOf<String>()
        val recommendations = mutableListOf<String>()
        
        // 检查缓存使用率
        when {
            cacheInfo.usagePercentage > 95.0 -> {
                issues.add("缓存使用率过高 (${String.format("%.1f", cacheInfo.usagePercentage)}%)")
                recommendations.add("建议清理缓存以释放空间")
            }
            cacheInfo.usagePercentage > 80.0 -> {
                recommendations.add("缓存使用率较高，建议定期清理")
            }
        }
        
        // 检查可用空间
        if (cacheInfo.availableSpace < 10 * 1024 * 1024) { // 小于10MB
            issues.add("可用缓存空间不足 (${formatBytes(cacheInfo.availableSpace)})")
            recommendations.add("建议清理缓存或增加缓存大小")
        }
        
        CacheHealthReport(
            isHealthy = issues.isEmpty(),
            cacheInfo = cacheInfo,
            issues = issues,
            recommendations = recommendations
        )
    }
    
    /**
     * 格式化字节数为可读字符串
     */
    private fun formatBytes(bytes: Long): String {
        return when {
            bytes >= 1024 * 1024 * 1024 -> String.format("%.1f GB", bytes / (1024.0 * 1024.0 * 1024.0))
            bytes >= 1024 * 1024 -> String.format("%.1f MB", bytes / (1024.0 * 1024.0))
            bytes >= 1024 -> String.format("%.1f KB", bytes / 1024.0)
            else -> "$bytes B"
        }
    }
    
    /**
     * 缓存信息
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
        }
    }
    
    /**
     * 缓存健康报告
     */
    data class CacheHealthReport(
        val isHealthy: Boolean,
        val cacheInfo: CacheInfo,
        val issues: List<String>,
        val recommendations: List<String>
    ) {
        val summary: String get() = if (isHealthy) "缓存状态良好" else "发现 ${issues.size} 个问题"
    }
}
