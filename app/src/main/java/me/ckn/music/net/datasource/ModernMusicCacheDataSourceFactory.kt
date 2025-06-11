package me.ckn.music.net.datasource

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.CacheEvictor
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import java.io.File
import me.ckn.music.utils.LogUtils

/**
 * 现代音乐流媒体缓存数据源工厂
 * 基于ExoPlayer最佳实践，针对音乐播放场景优化
 * 
 * 特性：
 * - 智能磁盘缓存：LRU策略，自动清理过期缓存
 * - 网络优化：针对音乐流媒体优化的HTTP配置
 * - 错误处理：网络错误时自动回退到缓存
 * - 内存效率：合理的缓存大小和清理策略
 * 
 * Created by wangchenyan.top on 2024/12/20.
 */
@UnstableApi
object ModernMusicCacheDataSourceFactory {
    
    private const val CACHE_DIR_NAME = "music_cache"
    private const val CACHE_SIZE = 80L * 1024 * 1024 // 减少到80MB，优化内存使用
    
    // 网络配置：针对快速启动优化，减少超时时间
    private const val CONNECT_TIMEOUT_MS = 5000 // 5秒连接超时，快速失败策略
    private const val READ_TIMEOUT_MS = 6000 // 6秒读取超时，平衡速度和稳定性
    
    @Volatile
    private var cache: SimpleCache? = null
    
    @Volatile
    private var cacheDataSourceFactory: CacheDataSource.Factory? = null
    
    /**
     * 获取优化的缓存数据源工厂
     * 单例模式，确保缓存实例的唯一性
     */
    @Synchronized
    fun getCacheDataSourceFactory(context: Context): CacheDataSource.Factory {
        if (cacheDataSourceFactory == null) {
            cacheDataSourceFactory = createCacheDataSourceFactory(context)
        }
        return cacheDataSourceFactory!!
    }
    
    /**
     * 创建缓存数据源工厂
     */
    private fun createCacheDataSourceFactory(context: Context): CacheDataSource.Factory {
        val cache = getOrCreateCache(context)
        val upstreamFactory = createOptimizedHttpDataSourceFactory()
        
        return CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(upstreamFactory)
            // 优化缓存策略：启用音频缓存，提升播放体验
            // 不设置CacheWriteDataSinkFactory，让它使用默认的文件写入器
            .setFlags(
                CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR or // 缓存错误时忽略缓存
                CacheDataSource.FLAG_BLOCK_ON_CACHE // 阻塞直到缓存可用，确保缓存写入
            )
    }
    
    /**
     * 获取或创建缓存实例
     */
    @Synchronized
    private fun getOrCreateCache(context: Context): SimpleCache {
        if (cache == null) {
            val cacheDir = File(context.cacheDir, CACHE_DIR_NAME)
            val databaseProvider = StandaloneDatabaseProvider(context)
            val cacheEvictor: CacheEvictor = LeastRecentlyUsedCacheEvictor(CACHE_SIZE)

            // 确保缓存目录存在
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }

            cache = SimpleCache(cacheDir, cacheEvictor, databaseProvider)
        }
        return cache!!
    }
    
    /**
     * 创建优化的HTTP数据源工厂
     * 针对音乐流媒体场景优化网络参数
     */
    private fun createOptimizedHttpDataSourceFactory(): DataSource.Factory {
        return DefaultHttpDataSource.Factory()
            // 用户代理：标识为音乐应用
            .setUserAgent("WhisperPlay/2.3.0 (Android Music Player)")
            // 连接超时：8秒，平衡启动速度和稳定性
            .setConnectTimeoutMs(CONNECT_TIMEOUT_MS)
            // 读取超时：8秒，避免长时间等待
            .setReadTimeoutMs(READ_TIMEOUT_MS)
            // 允许跨协议重定向：支持HTTP到HTTPS的重定向
            .setAllowCrossProtocolRedirects(true)
            // 保持连接：提高后续请求效率
            .setKeepPostFor302Redirects(true)
    }
    
    /**
     * 获取缓存统计信息
     */
    fun getCacheStats(context: Context): CacheStats {
        val cache = getOrCreateCache(context)
        val cacheSize = cache.cacheSpace
        val keys = cache.keys
        return CacheStats(
            cacheSize = cacheSize,
            maxCacheSize = CACHE_SIZE,
            cachedBytes = cacheSize,
            cacheHitRate = 0.0 // SimpleCache不直接提供命中率统计
        )
    }
    
    /**
     * 清理缓存
     * 真实清理SimpleCache中的所有缓存文件和数据库记录
     */
    fun clearCache() {
        cache?.let { cacheInstance ->
            try {
                LogUtils.i("CacheFactory", "开始清理缓存...")
                
                // 获取所有缓存的key
                val keys = cacheInstance.keys.toList()
                LogUtils.i("CacheFactory", "找到 ${keys.size} 个缓存条目")
                
                // 逐个移除缓存资源
                var removedCount = 0
                for (key in keys) {
                    try {
                        cacheInstance.removeResource(key)
                        removedCount++
                    } catch (e: Exception) {
                        LogUtils.w("CacheFactory", "移除缓存资源失败: $key", e)
                    }
                }
                
                LogUtils.i("CacheFactory", "缓存清理完成，移除了 $removedCount 个缓存条目")
                
                // 额外清理：直接删除缓存目录中的文件
                clearCacheDirectory()
                
            } catch (e: Exception) {
                LogUtils.e("CacheFactory", "清理缓存时发生异常", e)
            }
        }
    }
    
    /**
     * 直接清理缓存目录
     * 作为额外的清理手段，确保所有缓存文件都被删除
     */
    private fun clearCacheDirectory() {
        try {
            cache?.let { cacheInstance ->
                // 获取缓存目录
                val cacheField = cacheInstance.javaClass.getDeclaredField("cacheDir")
                cacheField.isAccessible = true
                val cacheDir = cacheField.get(cacheInstance) as? File
                
                if (cacheDir?.exists() == true) {
                    val sizeBefore = calculateDirectorySize(cacheDir)
                    deleteDirectoryContents(cacheDir)
                    val sizeAfter = calculateDirectorySize(cacheDir)
                    val freedSize = sizeBefore - sizeAfter
                    
                    LogUtils.i("CacheFactory", "直接清理缓存目录完成，释放: ${formatBytes(freedSize)}")
                }
            }
        } catch (e: Exception) {
            LogUtils.w("CacheFactory", "直接清理缓存目录时发生异常", e)
        }
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
            LogUtils.w("CacheFactory", "计算目录大小时发生异常: ${directory.absolutePath}", e)
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
                            LogUtils.w("CacheFactory", "无法删除文件: ${file.absolutePath}")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            LogUtils.w("CacheFactory", "删除目录内容时发生异常: ${directory.absolutePath}", e)
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
                LogUtils.w("CacheFactory", "无法删除目录: ${directory.absolutePath}")
            }
        } catch (e: Exception) {
            LogUtils.w("CacheFactory", "递归删除目录时发生异常: ${directory.absolutePath}", e)
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
     * 清理过期缓存
     * 根据LRU策略自动清理最少使用的缓存条目
     */
    fun clearExpiredCache(context: Context) {
        try {
            val cache = getOrCreateCache(context)
            // SimpleCache会根据LRU策略自动清理过期内容
            // 这里我们可以获取缓存空间使用情况，但不需要手动清理
            val usedSpace = cache.cacheSpace
            val maxSpace = CACHE_SIZE
            
            // 当缓存使用超过90%时，LRU会自动清理
        } catch (e: Exception) {
            LogUtils.w("CacheFactory", "清理过期缓存失败", e)
        }
    }
    
    /**
     * 释放缓存资源
     * 应用退出时调用
     */
    fun release() {
        cache?.release()
        cache = null
        cacheDataSourceFactory = null
    }
    
    /**
     * 缓存统计信息
     */
    data class CacheStats(
        val cacheSize: Long,
        val maxCacheSize: Long,
        val cachedBytes: Long,
        val cacheHitRate: Double
    ) {
        val usagePercentage: Double
            get() = if (maxCacheSize > 0) (cacheSize.toDouble() / maxCacheSize) * 100 else 0.0
            
        val availableSpace: Long
            get() = maxCacheSize - cacheSize
    }
}
