package me.wcy.music.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * 动画工具类，为车载环境优化的交互动画
 * Created by wangchenyan.top on 2024/3/26.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fJ\"\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nJ\u000e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0018\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\f\u00a8\u0006\u0011"}, d2 = {"Lme/wcy/music/utils/AnimationUtils;", "", "()V", "addButtonAnimation", "", "view", "Landroid/view/View;", "scaleDown", "", "duration", "", "rippleColor", "", "addClickScaleAnimation", "addListItemAnimation", "addPlayControlAnimation", "addRippleEffect", "app_debug"})
public final class AnimationUtils {
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.utils.AnimationUtils INSTANCE = null;
    
    private AnimationUtils() {
        super();
    }
    
    /**
     * 为View添加点击缩放动画
     * @param view 目标View
     * @param scaleDown 按下时的缩放比例，默认0.95
     * @param duration 动画时长，默认150ms
     */
    public final void addClickScaleAnimation(@org.jetbrains.annotations.NotNull()
    android.view.View view, float scaleDown, long duration) {
    }
    
    /**
     * 为View添加涟漪效果（适用于车载大屏）
     * @param view 目标View
     * @param rippleColor 涟漪颜色，默认为半透明白色
     */
    public final void addRippleEffect(@org.jetbrains.annotations.NotNull()
    android.view.View view, int rippleColor) {
    }
    
    /**
     * 为按钮添加完整的交互动画（缩放 + 涟漪）
     * @param view 目标View
     * @param scaleDown 按下时的缩放比例
     * @param duration 动画时长
     * @param rippleColor 涟漪颜色
     */
    public final void addButtonAnimation(@org.jetbrains.annotations.NotNull()
    android.view.View view, float scaleDown, long duration, int rippleColor) {
    }
    
    /**
     * 为列表项添加点击动画
     * @param view 目标View
     */
    public final void addListItemAnimation(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    /**
     * 为播放控制按钮添加特殊动画
     * @param view 目标View
     */
    public final void addPlayControlAnimation(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
}