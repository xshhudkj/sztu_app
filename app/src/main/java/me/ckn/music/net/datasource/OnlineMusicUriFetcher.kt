package me.ckn.music.net.datasource

import android.net.Uri
import android.util.Log
import kotlinx.coroutines.*
import me.ckn.music.discover.DiscoverApi
import me.ckn.music.storage.preference.ConfigPreferences
import me.ckn.music.utils.VipUtils
import top.wangchenyan.common.net.apiCall
import java.util.concurrent.ConcurrentHashMap

/**
 * 在线音乐URI获取器
 * 负责获取在线歌曲的播放链接，支持缓存机制和异步获取
 *
 * Original: Created by wangchenyan.top on 2024/3/26
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：在线音乐URI获取器
 * File Description: Online music URI fetcher
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
object OnlineMusicUriFetcher {
    private const val TAG = "OnlineMusicUriFetcher"

    // URL获取任务缓存，避免重复请求同一首歌曲
    private val fetchingTasks = ConcurrentHashMap<String, Deferred<String>>()

    // 协程作用域，用于管理异步任务
    private val fetchScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun fetchPlayUrl(uri: Uri): String {
        val songId = uri.getQueryParameter("id")?.toLongOrNull() ?: return uri.toString()
        val fee = uri.getQueryParameter("fee")?.toIntOrNull() ?: 0
        val taskKey = "$songId-$fee"

        return try {
            // 优化：减少超时时间，使用更激进的策略提高响应速度
            runBlocking {
                withTimeout(5_000) { // 减少到5秒超时，快速失败策略
                    // 检查是否已有正在进行的获取任务
                    val existingTask = fetchingTasks[taskKey]
                    if (existingTask != null && existingTask.isActive) {
                        Log.d(TAG, "复用正在进行的获取任务: songId=$songId")
                        return@withTimeout existingTask.await()
                    }

                    // 创建新的获取任务，使用IO调度器优化
                    val fetchTask = fetchScope.async(Dispatchers.IO) {
                        fetchUrlInternal(songId, fee)
                    }

                    // 缓存任务
                    fetchingTasks[taskKey] = fetchTask

                    try {
                        val result = fetchTask.await()
                        Log.d(TAG, "URL获取完成: songId=$songId, url=${if (result.isNotEmpty()) "成功" else "失败"}")
                        result
                    } finally {
                        // 清理完成的任务
                        fetchingTasks.remove(taskKey)
                    }
                }
            }
        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "URL获取超时: songId=$songId", e)
            // 清理超时的任务
            fetchingTasks.remove(taskKey)
            ""
        } catch (e: Exception) {
            Log.e(TAG, "URL获取失败: songId=$songId", e)
            // 清理失败的任务
            fetchingTasks.remove(taskKey)
            ""
        }
    }

    /**
     * 内部URL获取逻辑
     */
    private suspend fun fetchUrlInternal(songId: Long, fee: Int): String {
        try {
            // 优先使用缓存的URL
            val cachedUrl = MusicUrlCache.getCachedUrl(songId, fee)
            if (!cachedUrl.isNullOrEmpty()) {
                Log.d(TAG, "使用缓存的URL: songId=$songId")
                return cachedUrl
            }

            Log.d(TAG, "缓存未命中，从网络获取URL: songId=$songId")
            // 缓存未命中，从网络获取
            // 根据歌曲fee字段决定音质
            val quality = if (VipUtils.needQualityDowngrade(fee) &&
                             VipUtils.isHighQuality(ConfigPreferences.playSoundQuality)) {
                // fee=8且用户设置高音质时，自动降级到standard
                VipUtils.getDowngradedQuality(ConfigPreferences.playSoundQuality, fee)
            } else {
                // 其他情况使用用户设置的音质
                ConfigPreferences.playSoundQuality
            }

            val res = apiCall {
                DiscoverApi.get().getSongUrl(songId, quality)
            }

            return if (res.isSuccessWithData() && res.getDataOrThrow().isNotEmpty()) {
                val url = res.getDataOrThrow().first().url
                // 将获取到的URL缓存起来
                if (url.isNotEmpty()) {
                    MusicUrlCache.cacheUrl(songId, fee, url, quality)
                }
                url
            } else {
                ""
            }
        } catch (e: Exception) {
            Log.e(TAG, "获取播放URL失败: songId=$songId", e)
            return ""
        }
    }
}