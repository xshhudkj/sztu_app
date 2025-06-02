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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0004\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0005\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0006\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0007\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\b\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\t\u001a@\u0010\n\u001a\u00020\u0001*\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\r2\b\b\u0002\u0010\u0010\u001a\u00020\r2\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0012\u00a8\u0006\u0013"}, d2 = {"enableCarImmersiveMode", "", "Landroid/app/Activity;", "enableImmersiveMode", "Landroid/app/Dialog;", "Landroidx/appcompat/app/AlertDialog;", "Lcom/google/android/material/bottomsheet/BottomSheetDialogFragment;", "Ltop/wangchenyan/common/widget/dialog/BottomDialog;", "Ltop/wangchenyan/common/widget/dialog/BottomItemsDialog;", "Ltop/wangchenyan/common/widget/dialog/CenterDialog;", "showImmersiveConfirmDialog", "Landroid/content/Context;", "title", "", "message", "confirmButton", "cancelButton", "onConfirm", "Lkotlin/Function0;", "app_debug"})
public final class ImmersiveUtilsKt {
    
    /**
     * Activity扩展函数，简化使用
     */
    public static final void enableImmersiveMode(@org.jetbrains.annotations.NotNull()
    android.app.Activity $this$enableImmersiveMode) {
    }
    
    public static final void enableCarImmersiveMode(@org.jetbrains.annotations.NotNull()
    android.app.Activity $this$enableCarImmersiveMode) {
    }
    
    /**
     * BottomSheetDialogFragment扩展函数，简化使用
     */
    public static final void enableImmersiveMode(@org.jetbrains.annotations.NotNull()
    com.google.android.material.bottomsheet.BottomSheetDialogFragment $this$enableImmersiveMode) {
    }
    
    /**
     * Context扩展函数：显示全屏沉浸式确认对话框
     */
    public static final void showImmersiveConfirmDialog(@org.jetbrains.annotations.NotNull()
    android.content.Context $this$showImmersiveConfirmDialog, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    java.lang.String confirmButton, @org.jetbrains.annotations.NotNull()
    java.lang.String cancelButton, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onConfirm) {
    }
    
    /**
     * Dialog扩展函数：为任意Dialog启用沉浸式模式
     */
    public static final void enableImmersiveMode(@org.jetbrains.annotations.NotNull()
    android.app.Dialog $this$enableImmersiveMode) {
    }
    
    /**
     * AlertDialog扩展函数：为AlertDialog启用沉浸式模式
     */
    public static final void enableImmersiveMode(@org.jetbrains.annotations.NotNull()
    androidx.appcompat.app.AlertDialog $this$enableImmersiveMode) {
    }
    
    /**
     * BottomDialog扩展函数：为BottomDialog启用沉浸式模式
     */
    public static final void enableImmersiveMode(@org.jetbrains.annotations.NotNull()
    top.wangchenyan.common.widget.dialog.BottomDialog $this$enableImmersiveMode) {
    }
    
    /**
     * CenterDialog扩展函数：为CenterDialog启用沉浸式模式
     */
    public static final void enableImmersiveMode(@org.jetbrains.annotations.NotNull()
    top.wangchenyan.common.widget.dialog.CenterDialog $this$enableImmersiveMode) {
    }
    
    /**
     * BottomItemsDialog扩展函数：为BottomItemsDialog启用沉浸式模式
     */
    public static final void enableImmersiveMode(@org.jetbrains.annotations.NotNull()
    top.wangchenyan.common.widget.dialog.BottomItemsDialog $this$enableImmersiveMode) {
    }
}