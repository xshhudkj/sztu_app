package me.ckn.music.ext

import android.view.View
import me.ckn.music.utils.AnimationUtils

/**
 * View扩展函数，提供便捷的动画方法
 *
 * Original: Created by wangchenyan.top on 2024/3/26
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：View扩展函数
 * File Description: View extension functions
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */

/**
 * 为View添加按钮点击动画
 */
fun View.addButtonAnimation(
    scaleDown: Float = 0.95f,
    duration: Long = 150L,
    rippleColor: Int = 0x30FFFFFF
) {
    AnimationUtils.addButtonAnimation(this, scaleDown, duration, rippleColor)
}

/**
 * 为View添加列表项点击动画
 */
fun View.addListItemAnimation() {
    AnimationUtils.addListItemAnimation(this)
}

/**
 * 为View添加播放控制按钮动画
 */
fun View.addPlayControlAnimation() {
    AnimationUtils.addPlayControlAnimation(this)
}

/**
 * 为View添加点击缩放动画
 */
fun View.addClickScaleAnimation(
    scaleDown: Float = 0.95f,
    duration: Long = 150L
) {
    AnimationUtils.addClickScaleAnimation(this, scaleDown, duration)
}

/**
 * 为View添加涟漪效果
 */
fun View.addRippleEffect(rippleColor: Int = 0x30FFFFFF) {
    AnimationUtils.addRippleEffect(this, rippleColor)
}
