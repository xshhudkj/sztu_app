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
 * Dialog全屏沉浸式辅助类
 * 提供各种类型对话框的沉浸式支持
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\f\u00a8\u0006\r"}, d2 = {"Lme/wcy/music/common/ImmersiveDialogHelper;", "", "()V", "enableImmersiveForAlertDialog", "", "dialog", "Landroidx/appcompat/app/AlertDialog;", "enableImmersiveForBottomDialog", "Ltop/wangchenyan/common/widget/dialog/BottomDialog;", "enableImmersiveForCenterDialog", "Ltop/wangchenyan/common/widget/dialog/CenterDialog;", "enableImmersiveForDialog", "Landroid/app/Dialog;", "app_debug"})
public final class ImmersiveDialogHelper {
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.common.ImmersiveDialogHelper INSTANCE = null;
    
    private ImmersiveDialogHelper() {
        super();
    }
    
    /**
     * 为Dialog启用全屏沉浸式模式
     */
    public final void enableImmersiveForDialog(@org.jetbrains.annotations.NotNull()
    android.app.Dialog dialog) {
    }
    
    /**
     * 为AlertDialog启用全屏沉浸式模式
     */
    public final void enableImmersiveForAlertDialog(@org.jetbrains.annotations.NotNull()
    androidx.appcompat.app.AlertDialog dialog) {
    }
    
    /**
     * 为BottomDialog启用全屏沉浸式模式
     */
    public final void enableImmersiveForBottomDialog(@org.jetbrains.annotations.NotNull()
    top.wangchenyan.common.widget.dialog.BottomDialog dialog) {
    }
    
    /**
     * 为CenterDialog启用全屏沉浸式模式
     */
    public final void enableImmersiveForCenterDialog(@org.jetbrains.annotations.NotNull()
    top.wangchenyan.common.widget.dialog.CenterDialog dialog) {
    }
}