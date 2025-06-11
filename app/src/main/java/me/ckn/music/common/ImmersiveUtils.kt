package me.ckn.music.common

import android.app.Activity
import android.app.Dialog
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * 标准全屏沉浸式工具类
 * 基于主题配置和WindowInsetsControllerCompat的最佳实践
 *
 * 使用方式：
 * 1. Activity使用AppTheme.CarImmersive主题
 * 2. 在onCreate中调用enableImmersiveMode()
 * 3. 使用setupWindowInsets()处理内容区域适配
 *
 * Created by wangchenyan.top on 2023/12/20.
 */
object ImmersiveUtils {

    /**
     * 为Activity启用全屏沉浸式模式
     * 基于WindowInsetsControllerCompat的标准实现
     * 完全符合官方最佳实践
     */
    fun enableImmersiveMode(activity: Activity) {
        val window = activity.window

        // 1. 设置窗口不适配系统窗口
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 2. 使用WindowInsetsControllerCompat隐藏系统栏
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.apply {
            // 隐藏状态栏和导航栏
            hide(WindowInsetsCompat.Type.systemBars())
            // 设置系统栏行为：通过滑动手势临时显示半透明系统栏
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        // 3. 车载环境优化
        enableCarOptimizations(window)

        // 4. 添加WindowInsets监听器以处理系统栏状态变化
        setupSystemBarListener(activity, controller)
    }

    /**
     * 为Dialog启用全屏沉浸式模式
     * 符合官方最佳实践，确保Dialog也保持沉浸式状态
     */
    fun enableImmersiveMode(dialog: Dialog) {
        dialog.window?.let { window ->
            // 设置窗口不适配系统窗口
            WindowCompat.setDecorFitsSystemWindows(window, false)

            // 获取WindowInsetsController并隐藏系统栏
            val controller = WindowCompat.getInsetsController(window, window.decorView)
            controller.apply {
                hide(WindowInsetsCompat.Type.systemBars())
                // 设置系统栏行为，与Activity保持一致
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

            // 设置窗口标志，确保Dialog可以覆盖系统栏区域
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )

            // 设置透明系统栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = android.graphics.Color.TRANSPARENT
                window.navigationBarColor = android.graphics.Color.TRANSPARENT
            }
        }
    }

    /**
     * 为BottomSheetDialogFragment启用全屏沉浸式模式
     */
    fun enableImmersiveMode(bottomSheetFragment: BottomSheetDialogFragment) {
        bottomSheetFragment.dialog?.let { dialog ->
            enableImmersiveMode(dialog)
        }
    }

    /**
     * 为PopupWindow启用全屏沉浸式模式
     * 注意：PopupWindow本身不支持全屏沉浸式，但可以确保其所在Activity保持全屏状态
     */
    fun enableImmersiveMode(@Suppress("UNUSED_PARAMETER") popupWindow: PopupWindow, activity: Activity) {
        // PopupWindow显示时确保Activity保持全屏状态
        enableCarImmersiveMode(activity)
    }

    /**
     * 设置系统栏状态监听器
     * 符合官方最佳实践的WindowInsets处理
     */
    private fun setupSystemBarListener(activity: Activity, controller: WindowInsetsControllerCompat) {
        ViewCompat.setOnApplyWindowInsetsListener(activity.window.decorView) { view, windowInsets ->
            // 检查系统栏可见性状态
            val isSystemBarsVisible = windowInsets.isVisible(WindowInsetsCompat.Type.systemBars())

            // 如果系统栏意外显示，重新隐藏（确保沉浸式状态稳定）
            if (isSystemBarsVisible) {
                controller.hide(WindowInsetsCompat.Type.systemBars())
            }

            // 返回原始insets，让其他组件正常处理
            ViewCompat.onApplyWindowInsets(view, windowInsets)
        }
    }

    /**
     * 车载环境优化设置
     * 符合Android Automotive最佳实践
     */
    private fun enableCarOptimizations(window: android.view.Window) {
        // 保持屏幕常亮（车载环境必需）
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // 处理刘海屏和异形屏（确保内容不被遮挡）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        // 防止系统UI意外覆盖（车载环境稳定性优化）
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    /**
     * 为View设置WindowInsets处理
     * 推荐在根布局上调用
     * 符合官方edge-to-edge最佳实践
     */
    fun setupWindowInsets(view: View, applyPadding: Boolean = false) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            if (applyPadding) {
                // 获取系统栏和显示切口的insets
                val systemBarsInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                val cutoutInsets = windowInsets.getInsets(WindowInsetsCompat.Type.displayCutout())

                // 合并insets，确保内容不被遮挡
                val left = maxOf(systemBarsInsets.left, cutoutInsets.left)
                val top = maxOf(systemBarsInsets.top, cutoutInsets.top)
                val right = maxOf(systemBarsInsets.right, cutoutInsets.right)
                val bottom = maxOf(systemBarsInsets.bottom, cutoutInsets.bottom)

                v.setPadding(left, top, right, bottom)
            }
            windowInsets
        }
    }

    /**
     * 临时显示系统栏（如软键盘弹出时）
     */
    fun showSystemBarsTemporarily(activity: Activity) {
        val controller = WindowCompat.getInsetsController(activity.window, activity.window.decorView)
        controller.show(WindowInsetsCompat.Type.systemBars())
    }

    /**
     * 检查当前是否处于全屏模式
     * 符合官方WindowInsets检查最佳实践
     */
    fun isImmersiveMode(activity: Activity): Boolean {
        val rootWindowInsets = ViewCompat.getRootWindowInsets(activity.window.decorView)
        return rootWindowInsets?.let { insets ->
            // 检查状态栏和导航栏是否都不可见
            !insets.isVisible(WindowInsetsCompat.Type.statusBars()) &&
            !insets.isVisible(WindowInsetsCompat.Type.navigationBars())
        } ?: false
    }

    /**
     * 智能切换沉浸式模式
     * 根据当前状态自动切换显示/隐藏系统栏
     */
    fun toggleImmersiveMode(activity: Activity) {
        val controller = WindowCompat.getInsetsController(activity.window, activity.window.decorView)
        if (isImmersiveMode(activity)) {
            // 当前是沉浸式，显示系统栏
            controller.show(WindowInsetsCompat.Type.systemBars())
        } else {
            // 当前不是沉浸式，隐藏系统栏
            controller.hide(WindowInsetsCompat.Type.systemBars())
        }
    }

    /**
     * 为车载环境优化的全屏配置
     */
    fun enableCarImmersiveMode(activity: Activity) {
        enableImmersiveMode(activity)
    }
}

/**
 * Activity扩展函数，简化使用
 */
fun Activity.enableImmersiveMode() {
    ImmersiveUtils.enableImmersiveMode(this)
}

fun Activity.enableCarImmersiveMode() {
    ImmersiveUtils.enableCarImmersiveMode(this)
}

/**
 * BottomSheetDialogFragment扩展函数，简化使用
 */
fun BottomSheetDialogFragment.enableImmersiveMode() {
    ImmersiveUtils.enableImmersiveMode(this)
}

/**
 * Dialog全屏沉浸式辅助类
 * 提供各种类型对话框的沉浸式支持
 */
object ImmersiveDialogHelper {

    /**
     * 为Dialog启用全屏沉浸式模式
     */
    fun enableImmersiveForDialog(dialog: Dialog) {
        ImmersiveUtils.enableImmersiveMode(dialog)
    }

    /**
     * 为AlertDialog启用全屏沉浸式模式
     */
    fun enableImmersiveForAlertDialog(dialog: androidx.appcompat.app.AlertDialog) {
        ImmersiveUtils.enableImmersiveMode(dialog)
    }

    /**
     * 为BottomDialog启用全屏沉浸式模式
     */
    fun enableImmersiveForBottomDialog(dialog: top.wangchenyan.common.widget.dialog.BottomDialog) {
        ImmersiveUtils.enableImmersiveMode(dialog)
    }

    /**
     * 为CenterDialog启用全屏沉浸式模式
     */
    fun enableImmersiveForCenterDialog(dialog: top.wangchenyan.common.widget.dialog.CenterDialog) {
        ImmersiveUtils.enableImmersiveMode(dialog)
    }
}

/**
 * Context扩展函数：显示全屏沉浸式确认对话框
 */
fun android.content.Context.showImmersiveConfirmDialog(
    title: String = "",
    message: String,
    confirmButton: String = "确定",
    cancelButton: String = "取消",
    onConfirm: () -> Unit = {}
) {
    val builder = androidx.appcompat.app.AlertDialog.Builder(this)
    if (title.isNotEmpty()) {
        builder.setTitle(title)
    }
    builder.setMessage(message)

    if (confirmButton.isNotEmpty()) {
        builder.setPositiveButton(confirmButton) { _, _ ->
            onConfirm()
        }
    }

    if (cancelButton.isNotEmpty()) {
        builder.setNegativeButton(cancelButton) { dialog, _ ->
            dialog.dismiss()
        }
    }

    val dialog = builder.create()
    ImmersiveDialogHelper.enableImmersiveForDialog(dialog)
    dialog.show()
}

/**
 * Dialog扩展函数：为任意Dialog启用沉浸式模式
 */
fun Dialog.enableImmersiveMode() {
    ImmersiveDialogHelper.enableImmersiveForDialog(this)
}

/**
 * AlertDialog扩展函数：为AlertDialog启用沉浸式模式
 */
fun androidx.appcompat.app.AlertDialog.enableImmersiveMode() {
    ImmersiveDialogHelper.enableImmersiveForAlertDialog(this)
}

/**
 * BottomDialog扩展函数：为BottomDialog启用沉浸式模式
 */
fun top.wangchenyan.common.widget.dialog.BottomDialog.enableImmersiveMode() {
    ImmersiveDialogHelper.enableImmersiveForBottomDialog(this)
}

/**
 * CenterDialog扩展函数：为CenterDialog启用沉浸式模式
 */
fun top.wangchenyan.common.widget.dialog.CenterDialog.enableImmersiveMode() {
    ImmersiveDialogHelper.enableImmersiveForCenterDialog(this)
}

/**
 * BottomItemsDialog扩展函数：为BottomItemsDialog启用沉浸式模式
 */
fun top.wangchenyan.common.widget.dialog.BottomItemsDialog.enableImmersiveMode() {
    ImmersiveDialogHelper.enableImmersiveForDialog(this)
}
