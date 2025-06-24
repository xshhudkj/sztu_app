package me.ckn.music.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresApi
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import me.ckn.music.net.datasource.MusicUrlCache
import me.ckn.music.net.datasource.ModernMusicCacheDataSourceFactory
import me.ckn.music.utils.LogUtils
import kotlinx.coroutines.*
import kotlin.math.max
import kotlin.math.min
import java.io.File
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 缓存清理结果数据类
 * 包含清理操作的详细信息
 */
@Parcelize
data class ClearResult(
    val success: Boolean,                    // 清理是否成功
    val sizeBefore: Long,                   // 清理前缓存大小（字节）
    val sizeAfter: Long,                    // 清理后缓存大小（字节）
    val freedSpace: Long,                   // 释放的空间大小（字节）
    val filesDeleted: Int,                  // 删除的文件数量
    val errorMessage: String? = null        // 错误信息（如果失败）
) : Parcelable {

    /**
     * 格式化释放的空间大小
     */
    val freedSpaceFormatted: String
        get() = formatBytes(freedSpace)

    /**
     * 格式化清理前大小
     */
    val sizeBeforeFormatted: String
        get() = formatBytes(sizeBefore)

    /**
     * 格式化清理后大小
     */
    val sizeAfterFormatted: String
        get() = formatBytes(sizeAfter)

    companion object {
        /**
         * 格式化字节大小
         */
        private fun formatBytes(bytes: Long): String {
            return when {
                bytes < 1024 -> "${bytes}B"
                bytes < 1024 * 1024 -> "${"%.1f".format(bytes / 1024.0)}KB"
                bytes < 1024 * 1024 * 1024 -> "${"%.1f".format(bytes / (1024.0 * 1024.0))}MB"
                else -> "${"%.1f".format(bytes / (1024.0 * 1024.0 * 1024.0))}GB"
            }
        }
    }
}

/**
 * 智能缓存管理器
 * 
 * 功能：
 * - 基于网络状况动态调整缓存策略
 * - 智能内存压力监听和缓存清理
 * - 性能监控和优化建议
 * - 自适应缓存领先时间调整
 *
 * Original: Created by wangchenyan.top on 2024/12/21
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：智能缓存管理器
 * File Description: Smart cache manager
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
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

    // 缓存限制监控
    private var cacheLimitBytes: Long = -1L
    private var isLimitMonitoringActive = false

    init {
        updateNetworkStatus()
        startPerformanceMonitoring()
        // 启动时检查是否有缓存限制设置
        initializeCacheLimitMonitoring()
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
                        // 激进清理：清理所有过期缓存和部分最近未使用的缓存
                        MusicUrlCache.cleanExpiredCache()
                        ModernMusicCacheDataSourceFactory.clearExpiredCache(context)
                    }

                    memoryInfo.memoryRatio > 0.8f -> {
                        // 温和清理：仅清理过期缓存
                        MusicUrlCache.cleanExpiredCache()
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
            val fileCount = calculateTotalFileCount()
            CacheInfo(
                totalSize = stats.cacheSize,
                maxSize = stats.maxCacheSize,
                usagePercentage = stats.usagePercentage,
                availableSpace = stats.availableSpace,
                isHealthy = stats.usagePercentage < 90.0,
                fileCount = fileCount
            )
        } catch (e: Exception) {
            LogUtils.e(TAG, "获取缓存统计信息失败", e)
            CacheInfo.empty()
        }
    }
    
    /**
     * 清理缓存（兼容旧接口）
     * 执行真实的缓存清理，包括磁盘文件和数据库记录
     */
    suspend fun clearCache(): Boolean = withContext(Dispatchers.IO) {
        val result = clearCacheDetailed()
        return@withContext result.success
    }

    /**
     * 详细的缓存清理方法
     * 返回清理的详细信息，包括释放空间、删除文件数等
     */
    suspend fun clearCacheDetailed(): ClearResult = withContext(Dispatchers.IO) {
        try {
            // 获取清理前的缓存信息
            val cacheInfoBefore = getCacheStats()
            val sizeBefore = cacheInfoBefore.totalSize
            val fileCountBefore = cacheInfoBefore.fileCount

            LogUtils.i(TAG, "开始缓存清理，清理前大小: ${formatBytes(sizeBefore)}, 文件数: $fileCountBefore")

            var totalClearedSize = 0L
            var totalFilesDeleted = 0

            // 1. 清理ExoPlayer的缓存
            try {
                ModernMusicCacheDataSourceFactory.clearCache()
                LogUtils.i(TAG, "ExoPlayer缓存清理完成")
            } catch (e: Exception) {
                LogUtils.w(TAG, "ExoPlayer缓存清理失败", e)
            }

            // 2. 清理URL缓存
            try {
                MusicUrlCache.clearCache()
                LogUtils.i(TAG, "URL缓存清理完成")
            } catch (e: Exception) {
                LogUtils.w(TAG, "URL缓存清理失败", e)
            }

            // 3. 清理应用自定义缓存目录
            val appCacheResult = clearApplicationCachesDetailed()
            totalClearedSize += appCacheResult.first
            totalFilesDeleted += appCacheResult.second
            LogUtils.i(TAG, "应用缓存清理完成，释放: ${formatBytes(appCacheResult.first)}, 删除文件: ${appCacheResult.second}")

            // 4. 清理临时文件
            val tempResult = clearTempFilesDetailed()
            totalClearedSize += tempResult.first
            totalFilesDeleted += tempResult.second
            LogUtils.i(TAG, "临时文件清理完成，释放: ${formatBytes(tempResult.first)}, 删除文件: ${tempResult.second}")

            // 获取清理后的缓存信息
            val cacheInfoAfter = getCacheStats()
            val sizeAfter = cacheInfoAfter.totalSize
            val actualFreedSpace = sizeBefore - sizeAfter

            LogUtils.i(TAG, "缓存清理成功，实际释放: ${formatBytes(actualFreedSpace)}, 删除文件: $totalFilesDeleted")

            ClearResult(
                success = true,
                sizeBefore = sizeBefore,
                sizeAfter = sizeAfter,
                freedSpace = actualFreedSpace,
                filesDeleted = totalFilesDeleted
            )
        } catch (e: Exception) {
            LogUtils.e(TAG, "缓存清理失败", e)
            ClearResult(
                success = false,
                sizeBefore = 0L,
                sizeAfter = 0L,
                freedSpace = 0L,
                filesDeleted = 0,
                errorMessage = e.message ?: "未知错误"
            )
        }
    }
    
    /**
     * 清理应用自定义缓存目录
     */
    private suspend fun clearApplicationCaches(): Long = withContext(Dispatchers.IO) {
        val result = clearApplicationCachesDetailed()
        return@withContext result.first
    }

    /**
     * 详细清理应用自定义缓存目录
     * @return Pair<释放大小, 删除文件数>
     */
    private suspend fun clearApplicationCachesDetailed(): Pair<Long, Int> = withContext(Dispatchers.IO) {
        try {
            var clearedSize = 0L
            var deletedFiles = 0

            // 清理主缓存目录
            val cacheDir = context.cacheDir
            if (cacheDir.exists()) {
                val sizeBefore = calculateDirectorySize(cacheDir)
                val filesBefore = calculateDirectoryFileCount(cacheDir)
                deleteDirectoryContents(cacheDir)
                val sizeAfter = calculateDirectorySize(cacheDir)
                val filesAfter = calculateDirectoryFileCount(cacheDir)
                clearedSize += (sizeBefore - sizeAfter)
                deletedFiles += (filesBefore - filesAfter)
            }

            // 清理外部缓存目录
            val externalCacheDir = context.externalCacheDir
            if (externalCacheDir?.exists() == true) {
                val sizeBefore = calculateDirectorySize(externalCacheDir)
                val filesBefore = calculateDirectoryFileCount(externalCacheDir)
                deleteDirectoryContents(externalCacheDir)
                val sizeAfter = calculateDirectorySize(externalCacheDir)
                val filesAfter = calculateDirectoryFileCount(externalCacheDir)
                clearedSize += (sizeBefore - sizeAfter)
                deletedFiles += (filesBefore - filesAfter)
            }

            Pair(clearedSize, deletedFiles)
        } catch (e: Exception) {
            LogUtils.w(TAG, "清理应用缓存时发生异常", e)
            Pair(0L, 0)
        }
    }
    
    /**
     * 清理临时文件
     */
    private suspend fun clearTempFiles(): Long = withContext(Dispatchers.IO) {
        val result = clearTempFilesDetailed()
        return@withContext result.first
    }

    /**
     * 详细清理临时文件
     * @return Pair<释放大小, 删除文件数>
     */
    private suspend fun clearTempFilesDetailed(): Pair<Long, Int> = withContext(Dispatchers.IO) {
        try {
            var clearedSize = 0L
            var deletedFiles = 0

            // 清理音乐下载的临时文件
            val musicTempDir = File(context.filesDir, "temp_music")
            if (musicTempDir.exists()) {
                val sizeBefore = calculateDirectorySize(musicTempDir)
                val filesBefore = calculateDirectoryFileCount(musicTempDir)
                deleteDirectoryContents(musicTempDir)
                clearedSize += sizeBefore
                deletedFiles += filesBefore
            }

            // 清理图片临时文件
            val imageTempDir = File(context.filesDir, "temp_images")
            if (imageTempDir.exists()) {
                val sizeBefore = calculateDirectorySize(imageTempDir)
                val filesBefore = calculateDirectoryFileCount(imageTempDir)
                deleteDirectoryContents(imageTempDir)
                clearedSize += sizeBefore
                deletedFiles += filesBefore
            }

            Pair(clearedSize, deletedFiles)
        } catch (e: Exception) {
            LogUtils.w(TAG, "清理临时文件时发生异常", e)
            Pair(0L, 0)
        }
    }

    /**
     * 清理全部缓存
     */
    suspend fun clearAllCache(): ClearResult = clearCacheDetailed()

    /**
     * 仅清理音频缓存
     */
    suspend fun clearAudioCache(): ClearResult = withContext(Dispatchers.IO) {
        try {
            val sizeBefore = getCacheStats().totalSize

            // 主要清理ExoPlayer的音频缓存
            ModernMusicCacheDataSourceFactory.clearCache()
            
            // 清理音乐相关的临时文件
            val musicTempDir = File(context.filesDir, "temp_music")
            var deletedFiles = 0
            if (musicTempDir.exists()) {
                deletedFiles = calculateDirectoryFileCount(musicTempDir)
                deleteDirectoryContents(musicTempDir)
            }

            val sizeAfter = getCacheStats().totalSize
            val freedSpace = sizeBefore - sizeAfter

            ClearResult(
                success = true,
                sizeBefore = sizeBefore,
                sizeAfter = sizeAfter,
                freedSpace = freedSpace,
                filesDeleted = deletedFiles
            )
        } catch (e: Exception) {
            ClearResult(
                success = false,
                sizeBefore = 0L,
                sizeAfter = 0L,
                freedSpace = 0L,
                filesDeleted = 0,
                errorMessage = e.message ?: "音频缓存清理失败"
            )
        }
    }

    /**
     * 仅清理图片缓存
     */
    suspend fun clearImageCache(): ClearResult = withContext(Dispatchers.IO) {
        try {
            val sizeBefore = getCacheStats().totalSize

            // 清理图片相关缓存
            val imageTempDir = File(context.filesDir, "temp_images")
            var freedSpace = 0L
            var deletedFiles = 0
            
            if (imageTempDir.exists()) {
                freedSpace = calculateDirectorySize(imageTempDir)
                deletedFiles = calculateDirectoryFileCount(imageTempDir)
                deleteDirectoryContents(imageTempDir)
            }

            // 清理Glide缓存（如果使用了的话）
            try {
                context.cacheDir.listFiles()?.forEach { file ->
                    if (file.name.contains("image") || file.name.contains("glide")) {
                        if (file.isDirectory) {
                            freedSpace += calculateDirectorySize(file)
                            deletedFiles += calculateDirectoryFileCount(file)
                            deleteDirectoryContents(file)
                        }
                    }
                }
            } catch (e: Exception) {
                LogUtils.w(TAG, "清理图片缓存异常", e)
            }

            val sizeAfter = getCacheStats().totalSize

            ClearResult(
                success = true,
                sizeBefore = sizeBefore,
                sizeAfter = sizeAfter,
                freedSpace = freedSpace,
                filesDeleted = deletedFiles
            )
        } catch (e: Exception) {
            ClearResult(
                success = false,
                sizeBefore = 0L,
                sizeAfter = 0L,
                freedSpace = 0L,
                filesDeleted = 0,
                errorMessage = e.message ?: "图片缓存清理失败"
            )
        }
    }

    /**
     * 清理旧缓存（指定天数之前的）
     */
    suspend fun clearOldCache(daysOld: Int): ClearResult = withContext(Dispatchers.IO) {
        try {
            val sizeBefore = getCacheStats().totalSize
            val cutoffTime = System.currentTimeMillis() - (daysOld * 24 * 60 * 60 * 1000L)
            var freedSpace = 0L
            var deletedFiles = 0

            // 清理指定天数前的缓存文件
            fun clearOldFilesInDirectory(directory: File) {
                if (!directory.exists()) return
                
                directory.listFiles()?.forEach { file ->
                    if (file.lastModified() < cutoffTime) {
                        if (file.isDirectory) {
                            freedSpace += calculateDirectorySize(file)
                            deletedFiles += calculateDirectoryFileCount(file)
                            deleteDirectoryContents(file)
                            file.delete()
                        } else {
                            freedSpace += file.length()
                            deletedFiles++
                            file.delete()
                        }
                    }
                }
            }

            // 清理各种缓存目录中的旧文件
            clearOldFilesInDirectory(context.cacheDir)
            context.externalCacheDir?.let { clearOldFilesInDirectory(it) }

            val sizeAfter = getCacheStats().totalSize

            ClearResult(
                success = true,
                sizeBefore = sizeBefore,
                sizeAfter = sizeAfter,
                freedSpace = freedSpace,
                filesDeleted = deletedFiles
            )
        } catch (e: Exception) {
            ClearResult(
                success = false,
                sizeBefore = 0L,
                sizeAfter = 0L,
                freedSpace = 0L,
                filesDeleted = 0,
                errorMessage = e.message ?: "旧缓存清理失败"
            )
        }
    }
    
    /**
     * 计算缓存文件总数
     */
    private fun calculateTotalFileCount(): Int {
        var totalCount = 0

        try {
            // 计算主缓存目录文件数
            val cacheDir = context.cacheDir
            if (cacheDir.exists()) {
                totalCount += calculateDirectoryFileCount(cacheDir)
            }

            // 计算外部缓存目录文件数
            val externalCacheDir = context.externalCacheDir
            if (externalCacheDir?.exists() == true) {
                totalCount += calculateDirectoryFileCount(externalCacheDir)
            }

            // 计算临时文件数
            val musicTempDir = File(context.filesDir, "temp_music")
            if (musicTempDir.exists()) {
                totalCount += calculateDirectoryFileCount(musicTempDir)
            }

            val imageTempDir = File(context.filesDir, "temp_images")
            if (imageTempDir.exists()) {
                totalCount += calculateDirectoryFileCount(imageTempDir)
            }

        } catch (e: Exception) {
            LogUtils.w(TAG, "计算文件数量时发生异常", e)
        }

        return totalCount
    }

    /**
     * 计算目录中的文件数量
     */
    private fun calculateDirectoryFileCount(directory: File): Int {
        if (!directory.exists() || !directory.isDirectory) {
            return 0
        }

        var count = 0
        try {
            val files = directory.listFiles()
            if (files != null) {
                for (file in files) {
                    count += if (file.isDirectory) {
                        calculateDirectoryFileCount(file)
                    } else {
                        1
                    }
                }
            }
        } catch (e: Exception) {
            LogUtils.w(TAG, "计算目录文件数量时发生异常: ${directory.absolutePath}", e)
        }

        return count
    }

    /**
     * 计算目录大小
     */
    private fun calculateDirectorySize(directory: File): Long {
        if (!directory.exists() || !directory.isDirectory) {
            return 0L
        }

        var size = 0L
        try {
            val files = directory.listFiles()
            if (files != null) {
                for (file in files) {
                    size += if (file.isDirectory) {
                        calculateDirectorySize(file)
                    } else {
                        file.length()
                    }
                }
            }
        } catch (e: Exception) {
            LogUtils.w(TAG, "计算目录大小时发生异常: ${directory.absolutePath}", e)
        }

        return size
    }
    
    /**
     * 删除目录内容但保留目录本身
     */
    private fun deleteDirectoryContents(directory: File) {
        if (!directory.exists() || !directory.isDirectory) {
            return
        }
        
        try {
            val files = directory.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file.isDirectory) {
                        deleteDirectoryRecursively(file)
                    } else {
                        if (!file.delete()) {
                            LogUtils.w(TAG, "无法删除文件: ${file.absolutePath}")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            LogUtils.w(TAG, "删除目录内容时发生异常: ${directory.absolutePath}", e)
        }
    }
    
    /**
     * 递归删除目录及其所有内容
     */
    private fun deleteDirectoryRecursively(directory: File) {
        if (!directory.exists()) {
            return
        }
        
        try {
            if (directory.isDirectory) {
                val files = directory.listFiles()
                if (files != null) {
                    for (file in files) {
                        deleteDirectoryRecursively(file)
                    }
                }
            }
            
            if (!directory.delete()) {
                LogUtils.w(TAG, "无法删除目录: ${directory.absolutePath}")
            }
        } catch (e: Exception) {
            LogUtils.w(TAG, "递归删除目录时发生异常: ${directory.absolutePath}", e)
        }
    }
    
    /**
     * 格式化字节大小
     */
    private fun formatBytes(bytes: Long): String {
        return when {
            bytes < 1024 -> "${bytes}B"
            bytes < 1024 * 1024 -> "${"%.1f".format(bytes / 1024.0)}KB"
            bytes < 1024 * 1024 * 1024 -> "${"%.1f".format(bytes / (1024.0 * 1024.0))}MB"
            else -> "${"%.1f".format(bytes / (1024.0 * 1024.0 * 1024.0))}GB"
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
     * 初始化缓存限制监控
     */
    private fun initializeCacheLimitMonitoring() {
        scope.launch {
            try {
                val limitBytes = me.ckn.music.storage.preference.ConfigPreferences.getCacheLimitBytes()
                if (limitBytes > 0) {
                    startCacheLimitMonitoring(limitBytes)
                }
            } catch (e: Exception) {
                LogUtils.w(TAG, "初始化缓存限制监控失败", e)
            }
        }
    }

    /**
     * 启动缓存限制监控
     * @param limitBytes 缓存限制大小（字节）
     */
    fun startCacheLimitMonitoring(limitBytes: Long) {
        this.cacheLimitBytes = limitBytes
        this.isLimitMonitoringActive = true

        scope.launch {
            while (isLimitMonitoringActive) {
                try {
                    checkAndEnforceCacheLimit()
                    delay(CACHE_LIMIT_CHECK_INTERVAL)
                } catch (e: Exception) {
                    LogUtils.e(TAG, "缓存限制监控异常", e)
                    delay(CACHE_LIMIT_CHECK_INTERVAL * 2)
                }
            }
        }
    }

    /**
     * 停止缓存限制监控
     */
    fun stopCacheLimitMonitoring() {
        isLimitMonitoringActive = false
        LogUtils.i(TAG, "停止缓存限制监控")
    }

    /**
     * 启动自动清理服务
     * @param intervalHours 清理间隔（小时）
     */
    fun startAutoCleanService(intervalHours: Int) {
        try {
            me.ckn.music.service.AutoCacheCleanService.startService(context, intervalHours)
            LogUtils.i(TAG, "启动自动清理服务，间隔: ${intervalHours}小时")
        } catch (e: Exception) {
            LogUtils.e(TAG, "启动自动清理服务失败", e)
        }
    }

    /**
     * 停止自动清理服务
     */
    fun stopAutoCleanService() {
        try {
            me.ckn.music.service.AutoCacheCleanService.stopService(context)
            LogUtils.i(TAG, "停止自动清理服务")
        } catch (e: Exception) {
            LogUtils.e(TAG, "停止自动清理服务失败", e)
        }
    }

    /**
     * 检查并执行缓存限制
     */
    private suspend fun checkAndEnforceCacheLimit() {
        if (cacheLimitBytes <= 0 || !isLimitMonitoringActive) {
            return
        }

        try {
            val cacheStats = getCacheStats()
            val currentSize = cacheStats.totalSize

            if (currentSize > cacheLimitBytes) {
                val excessSize = currentSize - cacheLimitBytes
                val targetCleanSize = excessSize + (cacheLimitBytes * 0.1).toLong() // 额外清理10%作为缓冲

                performSmartPartialCleanup(targetCleanSize)
            }
        } catch (e: Exception) {
            LogUtils.e(TAG, "检查缓存限制时发生异常", e)
        }
    }

    /**
     * 执行智能部分清理
     * 优先清理最旧的、最少使用的缓存文件
     * @param targetCleanSize 目标清理大小
     */
    private suspend fun performSmartPartialCleanup(targetCleanSize: Long): ClearResult = withContext(Dispatchers.IO) {
        try {
            val sizeBefore = getCacheStats().totalSize
            var cleanedSize = 0L
            var deletedFiles = 0

            // 1. 首先清理过期的URL缓存
            try {
                MusicUrlCache.cleanExpiredCache()
                LogUtils.d(TAG, "清理过期URL缓存完成")
            } catch (e: Exception) {
                LogUtils.w(TAG, "清理过期URL缓存失败", e)
            }

            // 2. 清理ExoPlayer过期缓存
            try {
                ModernMusicCacheDataSourceFactory.clearExpiredCache(context)
                LogUtils.d(TAG, "清理ExoPlayer过期缓存完成")
            } catch (e: Exception) {
                LogUtils.w(TAG, "清理ExoPlayer过期缓存失败", e)
            }

            // 3. 如果还需要更多清理，清理最旧的缓存文件
            val currentSize = getCacheStats().totalSize
            val remainingToClean = targetCleanSize - (sizeBefore - currentSize)

            if (remainingToClean > 0) {
                val partialResult = clearOldestCacheFiles(remainingToClean)
                cleanedSize += partialResult.first
                deletedFiles += partialResult.second
            }

            val sizeAfter = getCacheStats().totalSize
            val actualFreed = sizeBefore - sizeAfter

            ClearResult(
                success = true,
                sizeBefore = sizeBefore,
                sizeAfter = sizeAfter,
                freedSpace = actualFreed,
                filesDeleted = deletedFiles
            )
        } catch (e: Exception) {
            LogUtils.e(TAG, "智能部分清理失败", e)
            ClearResult(
                success = false,
                sizeBefore = 0L,
                sizeAfter = 0L,
                freedSpace = 0L,
                filesDeleted = 0,
                errorMessage = e.message ?: "未知错误"
            )
        }
    }

    /**
     * 清理最旧的缓存文件
     * @param targetSize 目标清理大小
     * @return Pair<清理大小, 删除文件数>
     */
    private suspend fun clearOldestCacheFiles(targetSize: Long): Pair<Long, Int> = withContext(Dispatchers.IO) {
        var cleanedSize = 0L
        var deletedFiles = 0

        try {
            // 获取所有缓存目录的文件，按修改时间排序
            val cacheFiles = mutableListOf<Pair<File, Long>>()

            // 收集主缓存目录文件
            val cacheDir = context.cacheDir
            if (cacheDir.exists()) {
                collectFilesWithTime(cacheDir, cacheFiles)
            }

            // 收集外部缓存目录文件
            val externalCacheDir = context.externalCacheDir
            if (externalCacheDir?.exists() == true) {
                collectFilesWithTime(externalCacheDir, cacheFiles)
            }

            // 按修改时间排序（最旧的在前）
            cacheFiles.sortBy { it.second }

            // 删除最旧的文件直到达到目标清理大小
            for ((file, _) in cacheFiles) {
                if (cleanedSize >= targetSize) break

                try {
                    val fileSize = file.length()
                    if (file.delete()) {
                        cleanedSize += fileSize
                        deletedFiles++
                        LogUtils.d(TAG, "删除旧缓存文件: ${file.name}, 大小: ${formatBytes(fileSize)}")
                    }
                } catch (e: Exception) {
                    LogUtils.w(TAG, "删除文件失败: ${file.absolutePath}", e)
                }
            }

        } catch (e: Exception) {
            LogUtils.w(TAG, "清理最旧缓存文件时发生异常", e)
        }

        Pair(cleanedSize, deletedFiles)
    }

    /**
     * 收集目录中的文件及其修改时间
     */
    private fun collectFilesWithTime(directory: File, fileList: MutableList<Pair<File, Long>>) {
        try {
            val files = directory.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file.isFile) {
                        fileList.add(Pair(file, file.lastModified()))
                    } else if (file.isDirectory) {
                        collectFilesWithTime(file, fileList)
                    }
                }
            }
        } catch (e: Exception) {
            LogUtils.w(TAG, "收集文件信息时发生异常: ${directory.absolutePath}", e)
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
        private const val CACHE_LIMIT_CHECK_INTERVAL = 60_000L // 60秒缓存限制检查间隔
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
@Parcelize
data class CacheInfo(
    val totalSize: Long,
    val maxSize: Long,
    val usagePercentage: Double,
    val availableSpace: Long,
    val isHealthy: Boolean,
    val fileCount: Int = 0
) : Parcelable {
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
 * 缓存事件数据类
 */
private data class CacheEvent(
    val timestamp: Long,
    val cacheLeadTime: Long,
    val networkType: NetworkType,
    val strategy: CacheStrategy
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

