package me.wcy.music.common

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
     */
    fun enableImmersiveMode(activity: Activity) {
        val window = activity.window
        
        // 1. 设置窗口不适配系统窗口
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        // 2. 使用WindowInsetsControllerCompat隐藏系统栏
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        
        // 3. 车载环境优化
        enableCarOptimizations(window)
    }

    /**
     * 为Dialog启用全屏沉浸式模式
     */
    fun enableImmersiveMode(dialog: Dialog) {
        dialog.window?.let { window ->
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val controller = WindowCompat.getInsetsController(window, window.decorView)
            controller.hide(WindowInsetsCompat.Type.systemBars())
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
     * 车载环境优化设置
     */
    private fun enableCarOptimizations(window: android.view.Window) {
        // 保持屏幕常亮
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        
        // 处理刘海屏和异形屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode = 
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
    }

    /**
     * 为View设置WindowInsets处理
     * 推荐在根布局上调用
     */
    fun setupWindowInsets(view: View, applyPadding: Boolean = false) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            if (applyPadding) {
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(insets.left, insets.top, insets.right, insets.bottom)
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
     */
    fun isImmersiveMode(activity: Activity): Boolean {
        val rootWindowInsets = ViewCompat.getRootWindowInsets(activity.window.decorView)
        return rootWindowInsets?.let { insets ->
            !insets.isVisible(WindowInsetsCompat.Type.systemBars())
        } ?: false
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
 */
object ImmersiveDialogHelper {

    /**
     * 为Dialog启用全屏沉浸式模式
     */
    fun enableImmersiveForDialog(dialog: Dialog) {
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
