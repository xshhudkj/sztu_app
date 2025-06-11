/**
 * WhisperPlay Music Player
 *
 * 文件描述：统一日志管理工具
 * File Description: Unified log management tool.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.utils

import android.util.Log
import me.ckn.music.BuildConfig

/**
 * 统一日志管理工具
 * Unified Log Management Tool
 *
 * 主要功能：
 * Main Functions:
 * - 自动检测构建类型，生产环境关闭Debug日志 / Automatically detect build type, disable Debug logs in production.
 * - 支持格式化输出和异常堆栈 / Support formatted output and exception stack traces.
 * - 最小化字符串拼接的性能开销 / Minimize performance overhead from string concatenation.
 *
 * @author ckn
 */
object LogUtils {

    // 日志开关：仅在Debug构建时启用
    @JvmStatic
    val ENABLE_DEBUG_LOG = BuildConfig.DEBUG
    private const val ENABLE_INFO_LOG = true  // Info日志在生产环境保留，用于重要信息
    private const val ENABLE_WARN_LOG = true  // Warning日志保留
    private const val ENABLE_ERROR_LOG = true // Error日志必须保留

    /**
     * 输出Debug日志
     * Outputs a debug log.
     * @param tag 日志标签 / Log tag.
     * @param message 日志消息 / Log message.
     */
    @JvmStatic
    fun d(tag: String, message: String) {
        if (ENABLE_DEBUG_LOG) {
            Log.d(tag, message)
        }
    }

    /**
     * 输出带懒加载消息的Debug日志
     * Outputs a debug log with a lazy-loaded message.
     * @param tag 日志标签 / Log tag.
     * @param messageProvider 消息提供者 / Message provider.
     */
    @JvmStatic
    inline fun d(tag: String, messageProvider: () -> String) {
        if (ENABLE_DEBUG_LOG) {
            Log.d(tag, messageProvider())
        }
    }

    /**
     * 输出Info日志
     * Outputs an info log.
     * @param tag 日志标签 / Log tag.
     * @param message 日志消息 / Log message.
     */
    @JvmStatic
    fun i(tag: String, message: String) {
        if (ENABLE_INFO_LOG) {
            Log.i(tag, message)
        }
    }

    /**
     * 输出Warning日志
     * Outputs a warning log.
     * @param tag 日志标签 / Log tag.
     * @param message 日志消息 / Log message.
     */
    @JvmStatic
    fun w(tag: String, message: String) {
        if (ENABLE_WARN_LOG) {
            Log.w(tag, message)
        }
    }

    /**
     * 输出带异常的Warning日志
     * Outputs a warning log with a throwable.
     * @param tag 日志标签 / Log tag.
     * @param message 日志消息 / Log message.
     * @param throwable 异常 / Throwable.
     */
    @JvmStatic
    fun w(tag: String, message: String, throwable: Throwable) {
        if (ENABLE_WARN_LOG) {
            Log.w(tag, message, throwable)
        }
    }

    /**
     * 输出Error日志
     * Outputs an error log.
     * @param tag 日志标签 / Log tag.
     * @param message 日志消息 / Log message.
     */
    @JvmStatic
    fun e(tag: String, message: String) {
        if (ENABLE_ERROR_LOG) {
            Log.e(tag, message)
        }
    }

    /**
     * 输出带异常的Error日志
     * Outputs an error log with a throwable.
     * @param tag 日志标签 / Log tag.
     * @param message 日志消息 / Log message.
     * @param throwable 异常 / Throwable.
     */
    @JvmStatic
    fun e(tag: String, message: String, throwable: Throwable) {
        if (ENABLE_ERROR_LOG) {
            Log.e(tag, message, throwable)
        }
    }

    /**
     * 输出性能监控日志
     * Outputs a performance monitoring log.
     * @param tag 日志标签 / Log tag.
     * @param operation 操作名称 / Operation name.
     * @param duration 持续时间（毫秒） / Duration in milliseconds.
     */
    @JvmStatic
    fun performance(tag: String, operation: String, duration: Long) {
        if (ENABLE_DEBUG_LOG) {
            Log.d("$tag-Performance", "$operation 耗时: ${duration}ms")
        }
    }

    /**
     * 输出带懒加载消息的性能监控日志
     * Outputs a performance monitoring log with a lazy-loaded message.
     * @param tag 日志标签 / Log tag.
     * @param messageProvider 消息提供者 / Message provider.
     */
    @JvmStatic
    inline fun performance(tag: String, messageProvider: () -> String) {
        if (ENABLE_DEBUG_LOG) {
            Log.d("$tag-Performance", messageProvider())
        }
    }

    /**
     * 输出播放状态日志
     * Outputs a playback state log.
     * @param tag 日志标签 / Log tag.
     * @param event 事件名称 / Event name.
     * @param details 事件详情 / Event details.
     */
    @JvmStatic
    fun playback(tag: String, event: String, details: String = "") {
        if (ENABLE_INFO_LOG) {
            val message = if (details.isNotEmpty()) "$event - $details" else event
            Log.i("$tag-Playback", message)
        }
    }

    /**
     * 输出缓存状态日志
     * Outputs a cache state log.
     * @param tag 日志标签 / Log tag.
     * @param message 日志消息 / Log message.
     */
    @JvmStatic
    fun cache(tag: String, message: String) {
        if (ENABLE_DEBUG_LOG) {
            Log.d("$tag-Cache", message)
        }
    }

    /**
     * 输出带懒加载消息的缓存状态日志
     * Outputs a cache state log with a lazy-loaded message.
     * @param tag 日志标签 / Log tag.
     * @param messageProvider 消息提供者 / Message provider.
     */
    @JvmStatic
    inline fun cache(tag: String, messageProvider: () -> String) {
        if (ENABLE_DEBUG_LOG) {
            Log.d("$tag-Cache", messageProvider())
        }
    }

    /**
     * 输出网络请求日志
     * Outputs a network request log.
     * @param tag 日志标签 / Log tag.
     * @param message 日志消息 / Log message.
     */
    @JvmStatic
    fun network(tag: String, message: String) {
        if (ENABLE_DEBUG_LOG) {
            Log.d("$tag-Network", message)
        }
    }

    /**
     * 输出带懒加载消息的网络请求日志
     * Outputs a network request log with a lazy-loaded message.
     * @param tag 日志标签 / Log tag.
     * @param messageProvider 消息提供者 / Message provider.
     */
    @JvmStatic
    inline fun network(tag: String, messageProvider: () -> String) {
        if (ENABLE_DEBUG_LOG) {
            Log.d("$tag-Network", messageProvider())
        }
    }
}