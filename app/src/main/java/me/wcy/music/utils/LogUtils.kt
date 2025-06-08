package me.wcy.music.utils

import android.util.Log
import me.wcy.music.BuildConfig

/**
 * 统一日志管理工具
 * 提供高性能的条件日志输出，避免生产环境的性能开销
 * 
 * 特性：
 * - 自动检测构建类型，生产环境关闭Debug日志
 * - 支持格式化输出和异常堆栈
 * - 最小化字符串拼接的性能开销
 * - 提供不同级别的日志控制
 * 
 * Created by wangchenyan.top on 2024/12/21.
 */
object LogUtils {
    
    // 日志开关：仅在Debug构建时启用
    @JvmStatic
    val ENABLE_DEBUG_LOG = BuildConfig.DEBUG
    private const val ENABLE_INFO_LOG = true  // Info日志在生产环境保留，用于重要信息
    private const val ENABLE_WARN_LOG = true  // Warning日志保留
    private const val ENABLE_ERROR_LOG = true // Error日志必须保留
    
    /**
     * Debug日志 - 仅在Debug构建时输出
     * 用于详细的调试信息，生产环境完全关闭
     */
    @JvmStatic
    fun d(tag: String, message: String) {
        if (ENABLE_DEBUG_LOG) {
            Log.d(tag, message)
        }
    }
    
    /**
     * Debug日志 - 支持懒加载消息
     * 避免不必要的字符串拼接开销
     */
    @JvmStatic
    inline fun d(tag: String, messageProvider: () -> String) {
        if (ENABLE_DEBUG_LOG) {
            Log.d(tag, messageProvider())
        }
    }
    
    /**
     * Info日志 - 重要信息，生产环境保留
     */
    @JvmStatic
    fun i(tag: String, message: String) {
        if (ENABLE_INFO_LOG) {
            Log.i(tag, message)
        }
    }
    
    /**
     * Warning日志 - 警告信息
     */
    @JvmStatic
    fun w(tag: String, message: String) {
        if (ENABLE_WARN_LOG) {
            Log.w(tag, message)
        }
    }
    
    /**
     * Warning日志 - 带异常
     */
    @JvmStatic
    fun w(tag: String, message: String, throwable: Throwable) {
        if (ENABLE_WARN_LOG) {
            Log.w(tag, message, throwable)
        }
    }
    
    /**
     * Error日志 - 错误信息，必须保留
     */
    @JvmStatic
    fun e(tag: String, message: String) {
        if (ENABLE_ERROR_LOG) {
            Log.e(tag, message)
        }
    }
    
    /**
     * Error日志 - 带异常堆栈
     */
    @JvmStatic
    fun e(tag: String, message: String, throwable: Throwable) {
        if (ENABLE_ERROR_LOG) {
            Log.e(tag, message, throwable)
        }
    }
    
    /**
     * 性能监控日志 - 用于播放性能分析
     * 仅在需要性能调试时启用
     */
    @JvmStatic
    fun performance(tag: String, operation: String, duration: Long) {
        if (ENABLE_DEBUG_LOG) {
            Log.d("$tag-Performance", "$operation 耗时: ${duration}ms")
        }
    }
    
    /**
     * 性能监控日志 - 支持懒加载消息
     */
    @JvmStatic
    inline fun performance(tag: String, messageProvider: () -> String) {
        if (ENABLE_DEBUG_LOG) {
            Log.d("$tag-Performance", messageProvider())
        }
    }
    
    /**
     * 播放状态日志 - 关键播放事件记录
     * 生产环境保留，用于用户反馈问题诊断
     */
    @JvmStatic
    fun playback(tag: String, event: String, details: String = "") {
        if (ENABLE_INFO_LOG) {
            val message = if (details.isNotEmpty()) "$event - $details" else event
            Log.i("$tag-Playback", message)
        }
    }
    
    /**
     * 缓存状态日志 - 缓存相关信息
     * Debug模式下启用，用于缓存性能分析
     */
    @JvmStatic
    fun cache(tag: String, message: String) {
        if (ENABLE_DEBUG_LOG) {
            Log.d("$tag-Cache", message)
        }
    }
    
    /**
     * 缓存状态日志 - 支持懒加载消息
     */
    @JvmStatic
    inline fun cache(tag: String, messageProvider: () -> String) {
        if (ENABLE_DEBUG_LOG) {
            Log.d("$tag-Cache", messageProvider())
        }
    }
    
    /**
     * 网络请求日志 - 网络相关信息
     */
    @JvmStatic
    fun network(tag: String, message: String) {
        if (ENABLE_DEBUG_LOG) {
            Log.d("$tag-Network", message)
        }
    }
    
    /**
     * 网络请求日志 - 支持懒加载消息
     */
    @JvmStatic
    inline fun network(tag: String, messageProvider: () -> String) {
        if (ENABLE_DEBUG_LOG) {
            Log.d("$tag-Network", messageProvider())
        }
    }
} 