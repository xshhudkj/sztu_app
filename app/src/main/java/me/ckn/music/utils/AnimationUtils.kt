package me.ckn.music.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator

/**
 * 动画工具类，为车载环境优化的交互动画
 *
 * Original: Created by wangchenyan.top on 2024/3/26
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：动画工具类
 * File Description: Animation utility class
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
object AnimationUtils {

    /**
     * 为View添加点击缩放动画
     * @param view 目标View
     * @param scaleDown 按下时的缩放比例，默认0.95
     * @param duration 动画时长，默认150ms
     */
    fun addClickScaleAnimation(
        view: View,
        scaleDown: Float = 0.95f,
        duration: Long = 150L
    ) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    // 按下时缩小
                    val scaleDownAnimator = AnimatorSet().apply {
                        playTogether(
                            ObjectAnimator.ofFloat(v, "scaleX", 1f, scaleDown),
                            ObjectAnimator.ofFloat(v, "scaleY", 1f, scaleDown)
                        )
                        this.duration = duration
                        interpolator = AccelerateDecelerateInterpolator()
                    }
                    scaleDownAnimator.start()
                }
                android.view.MotionEvent.ACTION_UP,
                android.view.MotionEvent.ACTION_CANCEL -> {
                    // 抬起时恢复
                    val scaleUpAnimator = AnimatorSet().apply {
                        playTogether(
                            ObjectAnimator.ofFloat(v, "scaleX", scaleDown, 1f),
                            ObjectAnimator.ofFloat(v, "scaleY", scaleDown, 1f)
                        )
                        this.duration = duration
                        interpolator = OvershootInterpolator(1.2f)
                    }
                    scaleUpAnimator.start()
                }
            }
            false // 不消费事件，让原有的点击事件继续执行
        }
    }

    /**
     * 为View添加涟漪效果（适用于车载大屏）
     * @param view 目标View
     * @param rippleColor 涟漪颜色，默认为半透明白色
     */
    fun addRippleEffect(view: View, rippleColor: Int = 0x30FFFFFF) {
        val rippleDrawable = android.graphics.drawable.RippleDrawable(
            android.content.res.ColorStateList.valueOf(rippleColor),
            view.background,
            null
        )
        view.background = rippleDrawable
    }

    /**
     * 为按钮添加完整的交互动画（缩放 + 涟漪）
     * @param view 目标View
     * @param scaleDown 按下时的缩放比例
     * @param duration 动画时长
     * @param rippleColor 涟漪颜色
     */
    fun addButtonAnimation(
        view: View,
        scaleDown: Float = 0.95f,
        duration: Long = 150L,
        rippleColor: Int = 0x30FFFFFF
    ) {
        addRippleEffect(view, rippleColor)
        addClickScaleAnimation(view, scaleDown, duration)
    }

    /**
     * 为列表项添加点击动画
     * @param view 目标View
     */
    fun addListItemAnimation(view: View) {
        addButtonAnimation(view, scaleDown = 0.98f, duration = 120L, rippleColor = 0x20FFFFFF)
    }

    /**
     * 为播放控制按钮添加特殊动画
     * @param view 目标View
     */
    fun addPlayControlAnimation(view: View) {
        addButtonAnimation(view, scaleDown = 0.9f, duration = 200L, rippleColor = 0x40FFFFFF)
    }
}
