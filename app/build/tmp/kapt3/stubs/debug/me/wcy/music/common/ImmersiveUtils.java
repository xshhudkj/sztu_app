package me.wcy.music.common;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0002J\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\n\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u0006J\u0018\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u0012J\u000e\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u001c"}, d2 = {"Lme/wcy/music/common/ImmersiveUtils;", "", "()V", "enableCarImmersiveMode", "", "activity", "Landroid/app/Activity;", "enableCarOptimizations", "window", "Landroid/view/Window;", "enableImmersiveMode", "dialog", "Landroid/app/Dialog;", "popupWindow", "Landroid/widget/PopupWindow;", "bottomSheetFragment", "Lcom/google/android/material/bottomsheet/BottomSheetDialogFragment;", "isImmersiveMode", "", "setupSystemBarListener", "controller", "Landroidx/core/view/WindowInsetsControllerCompat;", "setupWindowInsets", "view", "Landroid/view/View;", "applyPadding", "showSystemBarsTemporarily", "toggleImmersiveMode", "app_debug"})
public final class ImmersiveUtils {
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.common.ImmersiveUtils INSTANCE = null;
    
    private ImmersiveUtils() {
        super();
    }
    
    /**
     * 为Activity启用全屏沉浸式模式
     * 基于WindowInsetsControllerCompat的标准实现
     * 完全符合官方最佳实践
     */
    public final void enableImmersiveMode(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    /**
     * 为Dialog启用全屏沉浸式模式
     * 符合官方最佳实践，确保Dialog也保持沉浸式状态
     */
    public final void enableImmersiveMode(@org.jetbrains.annotations.NotNull()
    android.app.Dialog dialog) {
    }
    
    /**
     * 为BottomSheetDialogFragment启用全屏沉浸式模式
     */
    public final void enableImmersiveMode(@org.jetbrains.annotations.NotNull()
    com.google.android.material.bottomsheet.BottomSheetDialogFragment bottomSheetFragment) {
    }
    
    /**
     * 为PopupWindow启用全屏沉浸式模式
     * 注意：PopupWindow本身不支持全屏沉浸式，但可以确保其所在Activity保持全屏状态
     */
    public final void enableImmersiveMode(@kotlin.Suppress(names = {"UNUSED_PARAMETER"})
    @org.jetbrains.annotations.NotNull()
    android.widget.PopupWindow popupWindow, @org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    /**
     * 设置系统栏状态监听器
     * 符合官方最佳实践的WindowInsets处理
     */
    private final void setupSystemBarListener(android.app.Activity activity, androidx.core.view.WindowInsetsControllerCompat controller) {
    }
    
    /**
     * 车载环境优化设置
     * 符合Android Automotive最佳实践
     */
    private final void enableCarOptimizations(android.view.Window window) {
    }
    
    /**
     * 为View设置WindowInsets处理
     * 推荐在根布局上调用
     * 符合官方edge-to-edge最佳实践
     */
    public final void setupWindowInsets(@org.jetbrains.annotations.NotNull()
    android.view.View view, boolean applyPadding) {
    }
    
    /**
     * 临时显示系统栏（如软键盘弹出时）
     */
    public final void showSystemBarsTemporarily(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    /**
     * 检查当前是否处于全屏模式
     * 符合官方WindowInsets检查最佳实践
     */
    public final boolean isImmersiveMode(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
        return false;
    }
    
    /**
     * 智能切换沉浸式模式
     * 根据当前状态自动切换显示/隐藏系统栏
     */
    public final void toggleImmersiveMode(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    /**
     * 为车载环境优化的全屏配置
     */
    public final void enableCarImmersiveMode(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
}