package me.ckn.music.utils

import android.content.Context
import androidx.media3.common.MediaItem
import kotlinx.coroutines.*
import me.ckn.music.net.datasource.MusicUrlCache
import me.ckn.music.discover.DiscoverApi
import me.ckn.music.storage.preference.ConfigPreferences
import me.ckn.music.utils.VipUtils
import top.wangchenyan.common.net.apiCall
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.min

/**
 * 首次播放启动优化器
 * 
 * 专门解决用户反馈的"首次播放启动要等很久"的问题
 * 
 * 优化策略：
 * 1. **智能URL预加载**：用户点击前预测并提前获取播放URL
 * 2. **网络请求优化**：并发请求、快速失败、连接池复用
 * 3. **缓存预热**：智能预热热门歌曲URL缓存
 * 4. **播放器优化**：激进的LoadControl配置，最小化起播延迟
 * 5. **用户行为预测**：基于用户操作预加载可能播放的歌曲
 *
 * Original: Created by wangchenyan.top on 2024/12/21
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：首次播放优化器
 * File Description: First play optimizer
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class FirstPlayOptimizer(
    private val context: Context
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // 正在预加载的歌曲URL
    private val preloadingUrls = ConcurrentHashMap<Long, Job>()
    
    // 用户操作预测
    private val userBehaviorPredictor = UserBehaviorPredictor()
    
    /**
     * 后台URL优化（非阻塞）
     * 用于在播放开始后异步优化URL
     */
    suspend fun optimizeFirstPlay(
        songId: Long,
        fee: Int = 0
    ): String? = withContext(Dispatchers.IO) {
        val startTime = System.currentTimeMillis()

        try {
            // 策略1：优先使用已缓存的URL
            val cachedUrl = MusicUrlCache.getCachedUrl(songId, fee)
            if (!cachedUrl.isNullOrEmpty()) {
                val elapsedTime = System.currentTimeMillis() - startTime
                LogUtils.performance("FirstPlayOptimizer") {
                    "后台优化-缓存命中: songId=$songId, 用时=${elapsedTime}ms"
                }
                return@withContext cachedUrl
            }

            // 策略2：快速网络获取（不等待预加载）
            val url = fetchUrlWithTimeout(songId, fee, FAST_FETCH_TIMEOUT)

            val elapsedTime = System.currentTimeMillis() - startTime
            LogUtils.performance("FirstPlayOptimizer") {
                "后台优化-网络获取: songId=$songId, 用时=${elapsedTime}ms, URL=${if(url.isNotEmpty()) "成功" else "失败"}"
            }

            return@withContext url

        } catch (e: Exception) {
            val elapsedTime = System.currentTimeMillis() - startTime
            LogUtils.w("FirstPlayOptimizer", "后台URL优化失败: songId=$songId, 用时=${elapsedTime}ms", e)
            return@withContext ""
        }
    }
    
    /**
     * 用户行为预测预加载
     * 在用户浏览歌单时预加载可能播放的歌曲
     */
    fun predictivePreload(songs: List<MediaItem>) {
        if (songs.isEmpty()) return
        
        scope.launch {
            try {
                // 预测用户最可能点击的歌曲（前3首）
                val topCandidates = userBehaviorPredictor.predictPlayOrder(songs).take(3)
                
                LogUtils.performance("FirstPlayOptimizer") { 
                    "预测性预加载: ${topCandidates.size}首歌曲" 
                }
                
                // 并发预加载，但限制并发数
                topCandidates.chunked(2).forEach { batch ->
                    batch.map { mediaItem ->
                        async {
                            val songId = mediaItem.getSongId()
                            val fee = mediaItem.getFee()
                            preloadUrlInBackground(songId, fee)
                        }
                    }.awaitAll()
                }
                
            } catch (e: Exception) {
                LogUtils.w("FirstPlayOptimizer", "预测性预加载失败", e)
            }
        }
    }
    
    /**
     * 智能预加载热门歌曲
     * 在应用启动时预加载用户常听的歌曲URL
     */
    fun preloadPopularSongs() {
        scope.launch {
            try {
                // 这里可以从用户听歌历史、收藏等获取热门歌曲
                // 目前先实现框架，后续可以接入推荐算法
                LogUtils.d("FirstPlayOptimizer", "开始预加载热门歌曲URL")
                
                // TODO: 实现热门歌曲获取逻辑
                
            } catch (e: Exception) {
                LogUtils.w("FirstPlayOptimizer", "预加载热门歌曲失败", e)
            }
        }
    }
    
    /**
     * 获取播放器极速启动建议配置
     */
    fun getOptimalLoadControlConfig(): LoadControlConfig {
        return LoadControlConfig(
            // 超激进配置：最小化首次播放延迟
            minBufferMs = 500,      // 500ms最小缓存（比当前1秒更激进）
            maxBufferMs = 8_000,    // 8秒最大缓存（比当前15秒更激进）
            bufferForPlaybackMs = 200,  // 200ms起播缓存（比当前1秒更激进）
            bufferForPlaybackAfterRebufferMs = 500, // 500ms重缓冲起播（比当前1.5秒更激进）
            targetBufferBytes = 1 * 1024 * 1024,    // 1MB目标缓存（比当前2MB更激进）
            prioritizeTimeOverSizeThresholds = true
        )
    }
    
    /**
     * 超时获取URL
     */
    private suspend fun fetchUrlWithTimeout(
        songId: Long, 
        fee: Int, 
        timeoutMs: Long
    ): String = withContext(Dispatchers.IO) {
        try {
            withTimeout(timeoutMs) {
                val quality = getOptimalQuality(fee)
                
                val res = apiCall {
                    DiscoverApi.get().getSongUrl(songId, quality)
                }
                
                if (res.isSuccessWithData() && res.getDataOrThrow().isNotEmpty()) {
                    val url = res.getDataOrThrow().first().url
                    if (url.isNotEmpty()) {
                        // 立即缓存
                        MusicUrlCache.cacheUrl(songId, fee, url, quality)
                        return@withTimeout url
                    }
                }
                
                return@withTimeout ""
            }
        } catch (e: TimeoutCancellationException) {
            LogUtils.w("FirstPlayOptimizer", "获取URL超时: songId=$songId, timeout=${timeoutMs}ms")
            ""
        } catch (e: Exception) {
            LogUtils.w("FirstPlayOptimizer", "获取URL失败: songId=$songId", e)
            ""
        }
    }
    
    /**
     * 后台预加载URL
     */
    private fun preloadUrlInBackground(songId: Long, fee: Int) {
        // 避免重复预加载
        if (preloadingUrls.containsKey(songId)) return
        
        val job = scope.launch {
            try {
                // 检查是否已缓存
                val cachedUrl = MusicUrlCache.getCachedUrl(songId, fee)
                if (!cachedUrl.isNullOrEmpty()) return@launch
                
                // 预加载URL
                val url = fetchUrlWithTimeout(songId, fee, PRELOAD_FETCH_TIMEOUT)
                if (url.isNotEmpty()) {
                    LogUtils.performance("FirstPlayOptimizer") { 
                        "后台预加载成功: songId=$songId" 
                    }
                }
            } catch (e: Exception) {
                LogUtils.w("FirstPlayOptimizer", "后台预加载失败: songId=$songId", e)
            } finally {
                preloadingUrls.remove(songId)
            }
        }
        
        preloadingUrls[songId] = job
    }
    
    /**
     * 获取最优音质
     */
    private fun getOptimalQuality(fee: Int): String {
        return if (VipUtils.needQualityDowngrade(fee) &&
                   VipUtils.isHighQuality(ConfigPreferences.playSoundQuality)) {
            VipUtils.getDowngradedQuality(ConfigPreferences.playSoundQuality, fee)
        } else {
            ConfigPreferences.playSoundQuality
        }
    }
    
    /**
     * 清理资源
     */
    fun cleanup() {
        scope.cancel()
        preloadingUrls.clear()
    }
    
    companion object {
        private const val FAST_FETCH_TIMEOUT = 3000L      // 3秒快速获取超时
        private const val PRELOAD_FETCH_TIMEOUT = 8000L   // 8秒预加载超时
    }
}

/**
 * 用户行为预测器
 * 基于歌曲位置、流行度等预测用户点击概率
 */
private class UserBehaviorPredictor {
    
    /**
     * 预测用户播放歌曲的顺序
     * 返回按点击概率排序的歌曲列表
     */
    fun predictPlayOrder(songs: List<MediaItem>): List<MediaItem> {
        if (songs.isEmpty()) return emptyList()
        
        // 简单的预测算法：
        // 1. 列表前面的歌曲更可能被点击
        // 2. 知名艺术家的歌曲更可能被点击
        // 3. 用户历史偏好（待实现）
        
        return songs.mapIndexed { index, song ->
            val positionScore = (songs.size - index).toFloat() / songs.size  // 位置得分
            val titleScore = calculateTitleScore(song.mediaMetadata.title?.toString() ?: "")
            
            song to (positionScore * 0.7f + titleScore * 0.3f)
        }
        .sortedByDescending { it.second }
        .map { it.first }
    }
    
    /**
     * 计算歌曲标题得分（简单实现）
     */
    private fun calculateTitleScore(title: String): Float {
        // 简单规则：包含热门关键词的歌曲得分更高
        val hotKeywords = listOf("爱", "心", "梦", "青春", "经典", "流行")
        var score = 0.5f
        
        hotKeywords.forEach { keyword ->
            if (title.contains(keyword)) {
                score += 0.1f
            }
        }
        
        return min(score, 1.0f)
    }
}

/**
 * LoadControl配置
 */
data class LoadControlConfig(
    val minBufferMs: Int,
    val maxBufferMs: Int,
    val bufferForPlaybackMs: Int,
    val bufferForPlaybackAfterRebufferMs: Int,
    val targetBufferBytes: Int,
    val prioritizeTimeOverSizeThresholds: Boolean
) 