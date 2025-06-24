/**
 * WhisperPlay Music Player
 *
 * 文件描述：时间格式化工具类
 * File Description: Time formatting utility class.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.utils

import android.text.format.DateUtils
import java.util.Locale

/**
 * 时间工具类
 * Time Utils
 *
 * 主要功能：
 * Main Functions:
 * - 格式化毫秒为 "mm:ss" 格式 / Format milliseconds to "mm:ss" format.
 * - 根据指定模式格式化时间 / Format time according to a specified pattern.
 *
 * 使用示例：
 * Usage Example:
 * ```kotlin
 * val formattedTime = TimeUtils.formatMs(60000L) // "01:00"
 * ```
 *
 * @author ckn
 * @since 2025-06-10
 */
object TimeUtils {
    /**
     * 格式化毫秒为 "mm:ss" 格式
     * Format milliseconds to "mm:ss" format.
     *
     * @param milli 要格式化的毫秒数 / Milliseconds to format.
     * @return 格式化后的字符串 / Formatted string.
     */
    fun formatMs(milli: Long): String {
        return formatTime("mm:ss", milli)
    }

    /**
     * 根据指定模式格式化时间
     * Format time according to a specified pattern.
     *
     * @param pattern 格式化模式，例如 "mm:ss" / Format pattern, e.g., "mm:ss".
     * @param milli 要格式化的毫秒数 / Milliseconds to format.
     * @return 格式化后的字符串 / Formatted string.
     */
    fun formatTime(pattern: String, milli: Long): String {
        val m = (milli / DateUtils.MINUTE_IN_MILLIS).toInt()
        val s = (milli / DateUtils.SECOND_IN_MILLIS % 60).toInt()
        val mm = String.format(Locale.getDefault(), "%02d", m)
        val ss = String.format(Locale.getDefault(), "%02d", s)
        return pattern.replace("mm", mm).replace("ss", ss)
    }
}