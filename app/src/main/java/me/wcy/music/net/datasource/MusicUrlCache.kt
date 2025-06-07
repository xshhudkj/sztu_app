package me.wcy.music.net.datasource

import android.util.LruCache
import kotlinx.coroutines.*
import me.wcy.music.discover.DiscoverApi
import me.wcy.music.storage.preference.ConfigPreferences
import me.wcy.music.utils.VipUtils
import top.wangchenyan.common.net.apiCall
import java.util.concurrent.TimeUnit

/**
 * 音频URL缓存管理器
 * 用于缓存歌曲播放链接，避免重复网络请求，提升播放启动速度
 * Created by wangchenyan.top on 2024/12/20.
 */
object MusicUrlCache {
    // URL缓存数据类
    data class CachedUrl(
        val url: String,
        val timestamp: Long,
        val quality: String,
        val isTrialSong: Boolean = false,  // 是否为试听歌曲
        val trialEndTime: Long = 0L        // 试听终点时间（毫秒）
    )
    
    // LRU缓存，增加缓存容量提高命中率
    private val urlCache = LruCache<String, CachedUrl>(200)

    // 缓存有效期：延长到30分钟，减少重复网络请求
    private const val CACHE_DURATION = 30 * 60 * 1000L
    
    // 预加载队列，用于后台预加载下一首歌曲的URL
    private val preloadQueue = mutableSetOf<Long>()
    
    /**
     * 获取歌曲播放URL（带缓存）
     */
    suspend fun getCachedUrl(songId: Long, fee: Int = 0): String? = withContext(Dispatchers.IO) {
        val quality = getQualityForSong(fee)
        val cacheKey = "$songId-$quality"

        // 检查缓存
        val cached = urlCache.get(cacheKey)
        if (cached != null && isUrlValid(cached)) {
            return@withContext cached.url
        }

        // 缓存失效或不存在，重新获取
        val url = fetchUrlFromNetwork(songId, quality)
        if (url.isNotEmpty()) {
            // 检查是否为VIP试听歌曲
            val isTrialSong = VipUtils.isVipSong(fee) && !VipUtils.isUserVip()
            val trialEndTime = if (isTrialSong) VipUtils.TRIAL_DURATION_MS else 0L

            // 更新缓存，包含试听信息
            urlCache.put(cacheKey, CachedUrl(
                url = url,
                timestamp = System.currentTimeMillis(),
                quality = quality,
                isTrialSong = isTrialSong,
                trialEndTime = trialEndTime
            ))
        }

        return@withContext url
    }

    /**
     * 缓存URL
     */
    fun cacheUrl(songId: Long, fee: Int, url: String, quality: String) {
        val cacheKey = "$songId-$quality"
        val isTrialSong = VipUtils.isVipSong(fee) && !VipUtils.isUserVip()
        val trialEndTime = if (isTrialSong) VipUtils.TRIAL_DURATION_MS else 0L

        urlCache.put(cacheKey, CachedUrl(
            url = url,
            timestamp = System.currentTimeMillis(),
            quality = quality,
            isTrialSong = isTrialSong,
            trialEndTime = trialEndTime
        ))
    }

    /**
     * 获取缓存的试听信息
     */
    fun getTrialInfo(songId: Long, fee: Int = 0): Pair<Boolean, Long>? {
        val quality = getQualityForSong(fee)
        val cacheKey = "$songId-$quality"
        val cached = urlCache.get(cacheKey)
        return if (cached != null && isUrlValid(cached)) {
            Pair(cached.isTrialSong, cached.trialEndTime)
        } else {
            null
        }
    }
    
    /**
     * 预加载歌曲URL
     * 在后台提前获取下一首歌曲的播放链接
     */
    suspend fun preloadUrl(songId: Long, fee: Int = 0) = withContext(Dispatchers.IO) {
        // 避免重复预加载
        if (preloadQueue.contains(songId)) {
            return@withContext
        }
        
        preloadQueue.add(songId)
        try {
            getCachedUrl(songId, fee)
        } finally {
            preloadQueue.remove(songId)
        }
    }
    
    /**
     * 批量预加载URL - 优化版本
     * 用于播放列表场景，智能预加载接下来的几首歌曲
     * 使用并发预加载提高效率
     */
    suspend fun preloadUrls(songIds: List<Pair<Long, Int>>, maxCount: Int = 2) = withContext(Dispatchers.IO) {
        // 减少预加载数量到2首，避免过度预加载影响当前播放
        val songsToPreload = songIds.take(maxCount)

        // 并发预加载，提高效率
        songsToPreload.map { (songId, fee) ->
            async {
                preloadUrl(songId, fee)
            }
        }.awaitAll()
    }
    
    /**
     * 清理过期缓存
     */
    fun cleanExpiredCache() {
        val snapshot = urlCache.snapshot()
        val currentTime = System.currentTimeMillis()
        
        snapshot.forEach { (key, cached) ->
            if (!isUrlValid(cached)) {
                urlCache.remove(key)
            }
        }
    }
    
    /**
     * 清空所有缓存
     */
    fun clearCache() {
        urlCache.evictAll()
        preloadQueue.clear()
    }
    
    /**
     * 检查URL是否有效
     */
    private fun isUrlValid(cached: CachedUrl): Boolean {
        val elapsed = System.currentTimeMillis() - cached.timestamp
        return elapsed < CACHE_DURATION && cached.url.isNotEmpty()
    }
    
    /**
     * 根据fee字段获取合适的音质
     */
    private fun getQualityForSong(fee: Int): String {
        return if (VipUtils.needQualityDowngrade(fee) &&
                   VipUtils.isHighQuality(ConfigPreferences.playSoundQuality)) {
            VipUtils.getDowngradedQuality(ConfigPreferences.playSoundQuality, fee)
        } else {
            ConfigPreferences.playSoundQuality
        }
    }
    
    /**
     * 从网络获取URL
     */
    private suspend fun fetchUrlFromNetwork(songId: Long, quality: String): String {
        val res = apiCall {
            DiscoverApi.get().getSongUrl(songId, quality)
        }
        
        return if (res.isSuccessWithData() && res.getDataOrThrow().isNotEmpty()) {
            res.getDataOrThrow().first().url
        } else {
            ""
        }
    }
    
    /**
     * 获取缓存统计信息
     */
    fun getCacheStats(): String {
        return "缓存数量: ${urlCache.size()}/${urlCache.maxSize()}, " +
               "命中率: ${urlCache.hitCount()}/${urlCache.hitCount() + urlCache.missCount()}"
    }
} 