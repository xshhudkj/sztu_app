/**
 * WhisperPlay Music Player
 *
 * 文件描述：屏幕适配管理器，用于处理不同设备和方向的UI适配。
 * File Description: Screen adaptation manager for handling UI adaptation for different devices and orientations.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.core.content.getSystemService

/**
 * 屏幕适配管理器
 * Screen Adapt Manager
 *
 * 主要功能：
 * Main Functions:
 * - 动态调整应用屏幕密度以适应不同尺寸的屏幕 / Dynamically adjust the application's screen density to fit different screen sizes.
 * - 区分手机、平板和车载设备 / Differentiate between phones, tablets, and automotive devices.
 * - 提供DP和PX单位转换工具 / Provide DP and PX unit conversion tools.
 *
 * 使用示例：
 * Usage Example:
 * ```kotlin
 * // 在 Activity 的 onCreate 方法中初始化
 * // Initialize in the onCreate method of the Activity
 * ScreenAdaptManager.init(this)
 * ```
 *
 * @author ckn
 * @since 2025-06-10
 */
object ScreenAdaptManager {

    // 设计稿基准宽度（dp）
    private const val DESIGN_WIDTH_DP = 360f
    private const val DESIGN_WIDTH_LANDSCAPE_DP = 640f

    /**
     * 屏幕类型枚举
     * Screen type enumeration
     */
    enum class ScreenType {
        PHONE_PORTRAIT,      // 手机竖屏 / Phone Portrait
        PHONE_LANDSCAPE,     // 手机横屏 / Phone Landscape
        TABLET_PORTRAIT,     // 平板竖屏 / Tablet Portrait
        TABLET_LANDSCAPE,    // 平板横屏 / Tablet Landscape
        AUTOMOTIVE           // 车机屏幕 / Automotive Screen
    }

    /**
     * 初始化屏幕适配
     * Initialize screen adaptation
     *
     * @param activity 当前 Activity / The current Activity
     */
    fun init(activity: Activity) {
        adaptScreen(activity)
    }

    /**
     * 适配屏幕密度
     * Adapt screen density
     */
    private fun adaptScreen(activity: Activity) {
        val displayMetrics = activity.resources.displayMetrics
        val configuration = activity.resources.configuration

        // 获取屏幕类型
        val screenType = getScreenType(activity)

        // 根据屏幕类型设置不同的适配策略
        when (screenType) {
            ScreenType.AUTOMOTIVE -> {
                // 车机屏幕使用固定的横屏适配
                adaptForAutomotive(displayMetrics)
            }
            ScreenType.PHONE_LANDSCAPE, ScreenType.TABLET_LANDSCAPE -> {
                // 横屏模式
                adaptForLandscape(displayMetrics)
            }
            else -> {
                // 竖屏模式
                adaptForPortrait(displayMetrics)
            }
        }

        // 更新配置
        activity.resources.updateConfiguration(configuration, displayMetrics)
    }

    /**
     * 车机屏幕适配
     * Adapt for automotive screen
     */
    private fun adaptForAutomotive(displayMetrics: DisplayMetrics) {
        val targetDensity = displayMetrics.widthPixels / DESIGN_WIDTH_LANDSCAPE_DP
        val targetDensityDpi = (targetDensity * 160).toInt()

        displayMetrics.density = targetDensity
        displayMetrics.densityDpi = targetDensityDpi
        displayMetrics.scaledDensity = targetDensity
    }

    /**
     * 横屏适配
     * Adapt for landscape
     */
    private fun adaptForLandscape(displayMetrics: DisplayMetrics) {
        val targetDensity = displayMetrics.widthPixels / DESIGN_WIDTH_LANDSCAPE_DP
        val targetDensityDpi = (targetDensity * 160).toInt()

        displayMetrics.density = targetDensity
        displayMetrics.densityDpi = targetDensityDpi
        displayMetrics.scaledDensity = targetDensity
    }

    /**
     * 竖屏适配
     * Adapt for portrait
     */
    private fun adaptForPortrait(displayMetrics: DisplayMetrics) {
        val targetDensity = displayMetrics.widthPixels / DESIGN_WIDTH_DP
        val targetDensityDpi = (targetDensity * 160).toInt()

        displayMetrics.density = targetDensity
        displayMetrics.densityDpi = targetDensityDpi
        displayMetrics.scaledDensity = targetDensity
    }

    /**
     * 获取当前屏幕类型
     * Get the current screen type
     *
     * @param context 上下文 / Context
     * @return 屏幕类型 / ScreenType
     */
    fun getScreenType(context: Context): ScreenType {
        // 检查是否为Android Automotive
        if (isAndroidAutomotive(context)) {
            return ScreenType.AUTOMOTIVE
        }

        val configuration = context.resources.configuration
        val screenLayout = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        val isTablet = screenLayout >= Configuration.SCREENLAYOUT_SIZE_LARGE

        return when {
            isTablet && configuration.orientation == Configuration.ORIENTATION_LANDSCAPE -> {
                ScreenType.TABLET_LANDSCAPE
            }
            isTablet && configuration.orientation == Configuration.ORIENTATION_PORTRAIT -> {
                ScreenType.TABLET_PORTRAIT
            }
            configuration.orientation == Configuration.ORIENTATION_LANDSCAPE -> {
                ScreenType.PHONE_LANDSCAPE
            }
            else -> {
                ScreenType.PHONE_PORTRAIT
            }
        }
    }

    /**
     * 检查是否为Android Automotive设备
     * Check if the device is Android Automotive
     *
     * @param context 上下文 / Context
     * @return 如果是车载设备则返回 true，否则返回 false / True if it is an automotive device, false otherwise
     */
    private fun isAndroidAutomotive(context: Context): Boolean {
        return context.packageManager.hasSystemFeature("android.hardware.type.automotive")
    }

    /**
     * 获取屏幕宽度（像素）
     * Get screen width in pixels
     *
     * @param context 上下文 / Context
     * @return 屏幕宽度（像素） / Screen width in pixels
     */
    fun getScreenWidth(context: Context): Int {
        val windowManager = context.getSystemService<WindowManager>()
        val displayMetrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    /**
     * 获取屏幕高度（像素）
     * Get screen height in pixels
     *
     * @param context 上下文 / Context
     * @return 屏幕高度（像素） / Screen height in pixels
     */
    fun getScreenHeight(context: Context): Int {
        val windowManager = context.getSystemService<WindowManager>()
        val displayMetrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    /**
     * 将 dp 值转换为 px 值
     * Convert dp value to px value
     *
     * @param context 上下文 / Context
     * @param dp dp 值 / dp value
     * @return 转换后的 px 值 / Converted px value
     */
    fun dp2px(context: Context, dp: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }

    /**
     * 将 px 值转换为 dp 值
     * Convert px value to dp value
     *
     * @param context 上下文 / Context
     * @param px px 值 / px value
     * @return 转换后的 dp 值 / Converted dp value
     */
    fun px2dp(context: Context, px: Float): Float {
        val density = context.resources.displayMetrics.density
        return px / density
    }
}