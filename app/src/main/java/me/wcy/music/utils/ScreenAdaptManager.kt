package me.wcy.music.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.core.content.getSystemService

/**
 * 屏幕适配管理器
 * 处理横竖屏切换、多屏幕尺寸适配、Android Automotive适配
 * Created by wangchenyan.top on 2024/12/20.
 */
object ScreenAdaptManager {
    
    // 设计稿基准宽度（dp）
    private const val DESIGN_WIDTH_DP = 360f
    private const val DESIGN_WIDTH_LANDSCAPE_DP = 640f
    
    // 屏幕类型
    enum class ScreenType {
        PHONE_PORTRAIT,      // 手机竖屏
        PHONE_LANDSCAPE,     // 手机横屏
        TABLET_PORTRAIT,     // 平板竖屏
        TABLET_LANDSCAPE,    // 平板横屏
        AUTOMOTIVE           // 车机屏幕
    }
    
    /**
     * 初始化屏幕适配
     */
    fun init(activity: Activity) {
        adaptScreen(activity)
    }
    
    /**
     * 适配屏幕密度
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
     */
    private fun adaptForPortrait(displayMetrics: DisplayMetrics) {
        val targetDensity = displayMetrics.widthPixels / DESIGN_WIDTH_DP
        val targetDensityDpi = (targetDensity * 160).toInt()
        
        displayMetrics.density = targetDensity
        displayMetrics.densityDpi = targetDensityDpi
        displayMetrics.scaledDensity = targetDensity
    }
    
    /**
     * 获取屏幕类型
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
     * 检查是否为Android Automotive
     */
    private fun isAndroidAutomotive(context: Context): Boolean {
        return context.packageManager.hasSystemFeature("android.hardware.type.automotive")
    }
    
    /**
     * 获取屏幕宽度（px）
     */
    fun getScreenWidth(context: Context): Int {
        val windowManager = context.getSystemService<WindowManager>()
        val displayMetrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }
    
    /**
     * 获取屏幕高度（px）
     */
    fun getScreenHeight(context: Context): Int {
        val windowManager = context.getSystemService<WindowManager>()
        val displayMetrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
    
    /**
     * dp转px
     */
    fun dp2px(context: Context, dp: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }
    
    /**
     * px转dp
     */
    fun px2dp(context: Context, px: Float): Float {
        val density = context.resources.displayMetrics.density
        return px / density
    }
} 