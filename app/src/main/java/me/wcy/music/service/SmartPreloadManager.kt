package me.wcy.music.service

import androidx.media3.common.MediaItem
import kotlinx.coroutines.*
import me.wcy.music.net.datasource.MusicUrlCache
import me.wcy.music.utils.getSongId
import me.wcy.music.utils.getFee
import me.wcy.music.utils.LogUtils
import java.util.concurrent.ConcurrentHashMap

/**
 * 智能预加载管理器
 * 根据播放进度和用户行为智能预加载下一首歌曲的URL
 * 
 * 特性：
 * - 智能预加载时机：在当前歌曲播放到70%时开始预加载
 * - 避免重复预加载：同一首歌曲只预加载一次
 * - 自动清理：清理过期的预加载任务
 * - 性能优化：限制并发预加载数量
 * 
 * Created by wangchenyan.top on 2024/12/20.
 */
class SmartPreloadManager {
    private val TAG = "SmartPreloadManager"
    
    // 预加载作用域
    private val preloadScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // 正在预加载的歌曲ID集合
    private val preloadingTasks = ConcurrentHashMap<Long, Job>()
    
    // 已预加载的歌曲ID集合
    private val preloadedSongs = mutableSetOf<Long>()
    
    // 预加载配置
    private companion object {
        const val PRELOAD_THRESHOLD = 0.7f // 播放到70%时开始预加载
        const val MAX_CONCURRENT_PRELOADS = 2 // 最大并发预加载数量
    }
    
    /**
     * 检查是否需要预加载下一首歌曲
     * @param currentSong 当前播放的歌曲
     * @param nextSongs 接下来的歌曲列表
     * @param currentProgress 当前播放进度（0.0-1.0）
     */
    fun checkPreload(
        currentSong: MediaItem?,
        nextSongs: List<MediaItem>,
        currentProgress: Float
    ) {
        if (currentSong == null || nextSongs.isEmpty()) {
            return
        }
        
        // 只有在播放进度达到阈值时才开始预加载
        if (currentProgress < PRELOAD_THRESHOLD) {
            return
        }
        
        // 限制并发预加载数量
        if (preloadingTasks.size >= MAX_CONCURRENT_PRELOADS) {
            LogUtils.d(TAG, "达到最大并发预加载数量，跳过预加载")
            return
        }
        
        // 预加载下一首歌曲
        val nextSong = nextSongs.firstOrNull()
        if (nextSong != null) {
            preloadSong(nextSong)
        }
    }
    
    /**
     * 预加载指定歌曲
     */
    private fun preloadSong(song: MediaItem) {
        val songId = song.getSongId()
        val fee = song.getFee()
        
        // 避免重复预加载
        if (preloadedSongs.contains(songId) || preloadingTasks.containsKey(songId)) {
            LogUtils.d(TAG) { "歌曲已预加载或正在预加载: songId=$songId" }
            return
        }
        
        LogUtils.d(TAG) { "开始预加载歌曲: songId=$songId, title=${song.mediaMetadata.title}" }
        
        val preloadJob = preloadScope.launch {
            try {
                // 预加载URL
                val url = MusicUrlCache.getCachedUrl(songId, fee)
                if (!url.isNullOrEmpty()) {
                    preloadedSongs.add(songId)
                    LogUtils.d(TAG) { "预加载完成: songId=$songId" }
                } else {
                    LogUtils.w(TAG, "预加载失败: songId=$songId")
                }
            } catch (e: Exception) {
                LogUtils.e(TAG, "预加载异常: songId=$songId", e)
            } finally {
                preloadingTasks.remove(songId)
            }
        }
        
        preloadingTasks[songId] = preloadJob
    }
    
    /**
     * 强制预加载指定歌曲列表
     * 用于播放列表切换等场景
     */
    fun forcePreload(songs: List<MediaItem>, maxCount: Int = 2) {
        preloadScope.launch {
            songs.take(maxCount).forEach { song ->
                val songId = song.getSongId()
                val fee = song.getFee()
                
                if (!preloadedSongs.contains(songId)) {
                    try {
                        val url = MusicUrlCache.getCachedUrl(songId, fee)
                        if (!url.isNullOrEmpty()) {
                            preloadedSongs.add(songId)
                            LogUtils.d(TAG) { "强制预加载完成: songId=$songId" }
                        }
                    } catch (e: Exception) {
                        LogUtils.e(TAG, "强制预加载失败: songId=$songId", e)
                    }
                }
            }
        }
    }
    
    /**
     * 清理已预加载的歌曲记录
     * 当歌曲开始播放时调用
     */
    fun markSongAsPlayed(songId: Long) {
        preloadedSongs.remove(songId)
        preloadingTasks[songId]?.cancel()
        preloadingTasks.remove(songId)
        LogUtils.d(TAG) { "清理预加载记录: songId=$songId" }
    }
    
    /**
     * 清理所有预加载任务和记录
     */
    fun clearAll() {
        preloadingTasks.values.forEach { it.cancel() }
        preloadingTasks.clear()
        preloadedSongs.clear()
        LogUtils.d(TAG, "清理所有预加载任务")
    }
    
    /**
     * 获取预加载状态信息
     */
    fun getPreloadStatus(): String {
        return "正在预加载: ${preloadingTasks.size}, 已预加载: ${preloadedSongs.size}"
    }
    
    /**
     * 定期清理过期的预加载记录
     */
    fun cleanupExpired() {
        preloadScope.launch {
            // 清理已完成但失败的任务
            val iterator = preloadingTasks.entries.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next()
                if (entry.value.isCompleted && !entry.value.isCancelled) {
                    iterator.remove()
                }
            }
            
            // 限制预加载记录数量，避免内存泄漏
            if (preloadedSongs.size > 50) {
                val toRemove = preloadedSongs.take(preloadedSongs.size - 30)
                preloadedSongs.removeAll(toRemove.toSet())
                LogUtils.d(TAG) { "清理过期预加载记录: ${toRemove.size}个" }
            }
        }
    }
}
