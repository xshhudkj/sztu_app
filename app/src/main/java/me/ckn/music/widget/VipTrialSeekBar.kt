/**
 * WhisperPlay Music Player
 *
 * 文件描述：支持VIP试听标记的进度条
 * File Description: SeekBar that supports VIP trial markers.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import me.ckn.music.R

/**
 * VIP试听进度条
 * VIP Trial SeekBar
 *
 * 主要功能：
 * Main Functions:
 * - 在进度条上标记VIP试听结束位置 / Marks the end of the VIP trial on the seek bar.
 * - 阻止用户拖动进度条超过试听位置 / Prevents the user from dragging the seek bar beyond the trial position.
 * - 提供平滑的回弹动画效果 / Provides a smooth spring-back animation effect.
 *
 * 使用示例：
 * Usage Example:
 * ```xml
 * <me.ckn.music.widget.VipTrialSeekBar
 *     android:id="@+id/vip_trial_seek_bar"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content" />
 * ```
 *
 * @author ckn
 * @since 2025-06-10
 */
class VipTrialSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SeekBar(context, attrs, defStyleAttr) {

    /**
     * 试听位置（0-1）
     */
    private var trialPosition: Float = 0f
    
    /**
     * 是否显示试听标记
     */
    private var showTrialMark: Boolean = false

    /**
     * 是否正在执行回退动画
     */
    private var isAnimating: Boolean = false

    /**
     * 拖拽开始时的进度值
     */
    private var dragStartProgress: Int = 0

    /**
     * 保存SeekBar的监听器引用
     */
    private var seekBarChangeListener: OnSeekBarChangeListener? = null
    
    /**
     * 试听标记画笔（红色圆点）
     */
    private val trialMarkPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.red_500)
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    
    /**
     * 试听线画笔（红色竖线）
     */
    private val trialLinePaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.red_500)
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    /**
     * 设置试听标记位置
     * @param position 试听位置（0-1）
     * @param show 是否显示标记
     */
    fun setTrialMark(position: Float, show: Boolean) {
        this.trialPosition = position.coerceIn(0f, 1f)
        this.showTrialMark = show
        invalidate()
    }

    override fun setOnSeekBarChangeListener(l: OnSeekBarChangeListener?) {
        super.setOnSeekBarChangeListener(l)
        this.seekBarChangeListener = l
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 如果正在执行动画，禁用触摸事件
        if (isAnimating) {
            return true
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 记录拖拽开始时的进度
                dragStartProgress = progress
                android.util.Log.d("VipTrialSeekBar", "Touch down, dragStartProgress: $dragStartProgress")
            }
            MotionEvent.ACTION_MOVE -> {
                // 检查是否超过试听终点
                if (showTrialMark && trialPosition > 0f) {
                    val maxAllowedProgress = (max * trialPosition).toInt()
                    if (progress > maxAllowedProgress) {
                        android.util.Log.d("VipTrialSeekBar", "Progress exceeded trial limit: $progress > $maxAllowedProgress")
                        // 超过试听终点，回退到拖拽开始前的原播放位置
                        animateBackToOriginalPosition(dragStartProgress)
                        return true
                    }
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // 拖拽结束，检查最终位置
                if (showTrialMark && trialPosition > 0f) {
                    val maxAllowedProgress = (max * trialPosition).toInt()
                    if (progress > maxAllowedProgress) {
                        android.util.Log.d("VipTrialSeekBar", "Final progress exceeded trial limit: $progress > $maxAllowedProgress")
                        // 超过试听终点，回退到拖拽开始前的原播放位置
                        animateBackToOriginalPosition(dragStartProgress)
                        return true
                    }
                }
                android.util.Log.d("VipTrialSeekBar", "Touch up/cancel, final progress: $progress")
            }
        }

        return super.onTouchEvent(event)
    }

    /**
     * 执行回退到原播放位置的平滑动画
     * @param targetProgress 目标进度值（拖拽开始前的原播放位置）
     */
    private fun animateBackToOriginalPosition(targetProgress: Int) {
        if (isAnimating) return

        val startProgress = progress
        isAnimating = true

        android.util.Log.d("VipTrialSeekBar", "Starting animation from $startProgress to $targetProgress")

        // 创建平滑回退动画
        val animator = ValueAnimator.ofInt(startProgress, targetProgress).apply {
            duration = 200 // 200ms动画时长，确保流畅体验
            interpolator = DecelerateInterpolator() // 减速插值器，更自然的动画效果

            addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Int
                // 直接设置进度，不触发监听器，避免重新加载
                super@VipTrialSeekBar.setProgress(animatedValue)
            }

            addListener(object : android.animation.Animator.AnimatorListener {
                override fun onAnimationStart(animation: android.animation.Animator) {
                    android.util.Log.d("VipTrialSeekBar", "Animation started")
                }
                override fun onAnimationRepeat(animation: android.animation.Animator) {}
                override fun onAnimationCancel(animation: android.animation.Animator) {
                    android.util.Log.d("VipTrialSeekBar", "Animation cancelled")
                    isAnimating = false
                    // 恢复正常的进度条更新
                    restoreProgressUpdates()
                }
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    android.util.Log.d("VipTrialSeekBar", "Animation ended, restoring progress updates")
                    isAnimating = false
                    // 动画结束后恢复正常的进度条更新机制
                    restoreProgressUpdates()
                }
            })
        }

        // 添加触觉反馈，提升用户体验
        performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY)

        animator.start()
    }

    /**
     * 恢复正常的进度条更新机制
     * 修复VIP试听回退后进度条不更新的问题
     */
    private fun restoreProgressUpdates() {
        // 确保拖拽状态被正确重置
        // 通过模拟onStopTrackingTouch来重置PlayingActivity中的isDraggingProgress状态
        seekBarChangeListener?.onStopTrackingTouch(this)

        // 通知监听器当前进度，确保后续更新正常
        seekBarChangeListener?.onProgressChanged(this, progress, false)

        // 强制重绘，确保UI状态正确
        invalidate()

        // 添加日志以便调试
        android.util.Log.d("VipTrialSeekBar", "Progress updates restored, current progress: $progress")
    }

    override fun onDraw(canvas: Canvas) {
        // 如果是VIP歌曲且显示试听标记，需要限制缓冲进度
        if (showTrialMark && trialPosition > 0f) {
            // 获取当前缓冲进度
            val currentSecondaryProgress = secondaryProgress
            val maxAllowedProgress = (max * trialPosition).toInt()

            // 如果缓冲进度超过试听终点，限制它
            if (currentSecondaryProgress > maxAllowedProgress) {
                secondaryProgress = maxAllowedProgress
            }
        }

        super.onDraw(canvas)

        if (showTrialMark && trialPosition > 0f) {
            drawTrialMark(canvas)
        }
    }

    /**
     * 绘制试听标记
     * 在试听终点位置绘制红色竖线和圆点
     */
    private fun drawTrialMark(canvas: Canvas) {
        // 计算进度条的实际绘制区域
        val progressWidth = width - paddingLeft - paddingRight
        val progressHeight = height - paddingTop - paddingBottom
        val progressTop = paddingTop.toFloat()
        val progressBottom = (height - paddingBottom).toFloat()
        
        // 计算试听标记的X坐标
        val markX = paddingLeft + progressWidth * trialPosition
        
        // 绘制红色竖线（从进度条顶部到底部）
        canvas.drawLine(
            markX,
            progressTop + progressHeight * 0.2f, // 稍微缩短线条
            markX,
            progressBottom - progressHeight * 0.2f,
            trialLinePaint
        )
        
        // 绘制红色圆点（在进度条中央）- 缩小尺寸
        val markY = progressTop + progressHeight * 0.5f
        val radius = 4f  // 从8f减小到4f
        canvas.drawCircle(markX, markY, radius, trialMarkPaint)

        // 绘制白色内圆（增强视觉效果）
        val innerPaint = Paint().apply {
            color = ContextCompat.getColor(context, android.R.color.white)
            isAntiAlias = true
            style = Paint.Style.FILL
        }
        canvas.drawCircle(markX, markY, radius * 0.5f, innerPaint)  // 调整内圆比例
    }
}
