package me.ckn.music.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import me.ckn.music.storage.preference.ConfigPreferences
import me.ckn.music.utils.LogUtils
import me.ckn.music.utils.SmartCacheManager
import top.wangchenyan.common.ext.toast

/**
 * 自动缓存清理服务
 * 负责定期检查和清理缓存，提供后台自动管理功能
 *
 * Original: Created by wangchenyan.top on 2024/12/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：自动缓存清理服务
 * File Description: Auto cache clean service
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class AutoCacheCleanService : Service() {
    
    private val smartCacheManager by lazy { SmartCacheManager(this) }
    
    // 使用自定义的协程作用域
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    
    override fun onCreate() {
        super.onCreate()
        LogUtils.i(TAG, "AutoCacheCleanService onCreate")
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_AUTO_CLEAN -> {
                LogUtils.i(TAG, "启动自动清理服务")
                startAutoCleanTask()
            }
            ACTION_STOP_AUTO_CLEAN -> {
                LogUtils.i(TAG, "停止自动清理服务")
                stopSelf()
            }
            ACTION_FORCE_CLEAN -> {
                LogUtils.i(TAG, "强制执行清理")
                performCleanNow()
            }
        }
        
        return START_STICKY
    }
    
    /**
     * 启动自动清理任务
     */
    private fun startAutoCleanTask() {
        serviceScope.launch {
            while (isActive && ConfigPreferences.isAutoCacheCleanEnabled()) {
                try {
                    checkAndPerformAutoClean()
                    
                    // 等待检查间隔（较短，用于频繁检查状态）
                    delay(CHECK_INTERVAL)
                } catch (e: Exception) {
                    LogUtils.e(TAG, "自动清理任务异常", e)
                    delay(CHECK_INTERVAL * 2) // 出错时延长等待时间
                }
            }
            
            LogUtils.i(TAG, "自动清理任务结束")
            stopSelf()
        }
    }
    
    /**
     * 检查并执行自动清理
     */
    private suspend fun checkAndPerformAutoClean() {
        try {
            val cacheStats = smartCacheManager.getCacheStats()
            val shouldClean = ConfigPreferences.shouldPerformAutoClean(cacheStats.usagePercentage)
            
            if (shouldClean) {
                LogUtils.i(TAG, "满足自动清理条件，开始清理缓存")
                LogUtils.i(TAG, "当前缓存使用率: ${cacheStats.usagePercentage}%")
                
                val cleanSuccess = smartCacheManager.clearCache()
                
                if (cleanSuccess) {
                    // 更新最后清理时间
                    ConfigPreferences.setLastAutoCacheCleanTime(System.currentTimeMillis())
                    
                    val newStats = smartCacheManager.getCacheStats()
                    val freedSpace = cacheStats.totalSize - newStats.totalSize
                    
                    LogUtils.i(TAG, "自动清理完成，释放空间: ${formatFileSize(freedSpace)}")
                    
                    // 可选：显示通知给用户（在后台运行时）
                    showCleanNotification(freedSpace)
                } else {
                    LogUtils.w(TAG, "自动清理失败")
                }
            }
        } catch (e: Exception) {
            LogUtils.e(TAG, "检查自动清理时发生异常", e)
        }
    }
    
    /**
     * 立即执行清理
     */
    private fun performCleanNow() {
        serviceScope.launch {
            try {
                val cacheStats = smartCacheManager.getCacheStats()
                val cleanSuccess = smartCacheManager.clearCache()
                
                if (cleanSuccess) {
                    ConfigPreferences.setLastAutoCacheCleanTime(System.currentTimeMillis())
                    val newStats = smartCacheManager.getCacheStats()
                    val freedSpace = cacheStats.totalSize - newStats.totalSize
                    
                    LogUtils.i(TAG, "手动清理完成，释放空间: ${formatFileSize(freedSpace)}")
                    toast("缓存清理完成，释放了 ${formatFileSize(freedSpace)} 空间")
                } else {
                    LogUtils.w(TAG, "手动清理失败")
                    toast("缓存清理失败，请重试")
                }
            } catch (e: Exception) {
                LogUtils.e(TAG, "手动清理时发生异常", e)
                toast("缓存清理异常")
            }
        }
    }
    
    /**
     * 显示清理通知
     */
    private fun showCleanNotification(freedSpace: Long) {
        // TODO: 实现通知显示逻辑
        // 可以显示一个简单的通知告知用户自动清理的结果
    }
    
    /**
     * 格式化文件大小
     */
    private fun formatFileSize(bytes: Long): String {
        return when {
            bytes < 1024 -> "${bytes}B"
            bytes < 1024 * 1024 -> "${bytes / 1024}KB"
            bytes < 1024 * 1024 * 1024 -> "${bytes / (1024 * 1024)}MB"
            else -> "${bytes / (1024 * 1024 * 1024)}GB"
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        LogUtils.i(TAG, "AutoCacheCleanService onDestroy")
    }
    
    companion object {
        private const val TAG = "AutoCacheCleanService"
        private const val CHECK_INTERVAL = 60 * 60 * 1000L // 1小时检查一次
        
        const val ACTION_START_AUTO_CLEAN = "start_auto_clean"
        const val ACTION_STOP_AUTO_CLEAN = "stop_auto_clean"
        const val ACTION_FORCE_CLEAN = "force_clean"
        const val EXTRA_INTERVAL_HOURS = "interval_hours"

        /**
         * 启动自动清理服务
         * @param intervalHours 清理间隔（小时）
         */
        fun startService(context: Context, intervalHours: Int = 168) {
            val intent = Intent(context, AutoCacheCleanService::class.java)
            intent.action = ACTION_START_AUTO_CLEAN
            intent.putExtra(EXTRA_INTERVAL_HOURS, intervalHours)
            context.startService(intent)
        }
        
        /**
         * 停止自动清理服务
         */
        fun stopService(context: Context) {
            val intent = Intent(context, AutoCacheCleanService::class.java)
            intent.action = ACTION_STOP_AUTO_CLEAN
            context.startService(intent)
        }
        
        /**
         * 立即执行清理
         */
        fun performCleanNow(context: Context) {
            val intent = Intent(context, AutoCacheCleanService::class.java)
            intent.action = ACTION_FORCE_CLEAN
            context.startService(intent)
        }
    }
} 