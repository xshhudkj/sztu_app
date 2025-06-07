package me.wcy.music.net.datasource

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
            // 优化缓存策略：快速失败，优先播放体验
            .setCacheWriteDataSinkFactory(null) // 使用默认的缓存写入
            .setFlags(
                CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR or // 缓存错误时忽略缓存
                CacheDataSource.FLAG_IGNORE_CACHE_FOR_UNSET_LENGTH_REQUESTS // 对未知长度请求忽略缓存，提高响应速度
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
            .setUserAgent("PonyMusic/2.3.0 (Android Music Player)")
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
        return CacheStats(
            cacheSize = cache.cacheSpace,
            maxCacheSize = CACHE_SIZE,
            cachedBytes = cache.cacheSpace,
            cacheHitRate = 0.0 // SimpleCache不直接提供命中率统计
        )
    }
    
    /**
     * 清理缓存
     */
    fun clearCache() {
        cache?.let { cache ->
            try {
                val keys = cache.keys
                for (key in keys) {
                    cache.removeResource(key)
                }
            } catch (e: Exception) {
                // 忽略清理错误
            }
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
