package me.wcy.music.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import me.wcy.music.R

/**
 * 支持VIP试听标记的进度条
 * 在试听终点位置显示红色标记
 * 
 * 功能说明：
 * 1. 继承自SeekBar，保持原有功能
 * 2. 在试听终点位置绘制红色标记线
 * 3. 支持动态显示/隐藏试听标记
 * 4. 适配车载大屏触摸操作
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
            }
            MotionEvent.ACTION_MOVE -> {
                // 检查是否超过试听终点
                if (showTrialMark && trialPosition > 0f) {
                    val maxAllowedProgress = (max * trialPosition).toInt()
                    if (progress > maxAllowedProgress) {
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
                        // 超过试听终点，回退到拖拽开始前的原播放位置
                        animateBackToOriginalPosition(dragStartProgress)
                        return true
                    }
                }
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

        // 创建平滑回退动画
        val animator = ValueAnimator.ofInt(startProgress, targetProgress).apply {
            duration = 200 // 200ms动画时长，确保流畅体验
            interpolator = DecelerateInterpolator() // 减速插值器，更自然的动画效果

            addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Int
                progress = animatedValue

                // 触发进度变化监听器，确保UI同步更新
                seekBarChangeListener?.onProgressChanged(this@VipTrialSeekBar, animatedValue, false)
            }

            addListener(object : android.animation.Animator.AnimatorListener {
                override fun onAnimationStart(animation: android.animation.Animator) {}
                override fun onAnimationRepeat(animation: android.animation.Animator) {}
                override fun onAnimationCancel(animation: android.animation.Animator) {
                    isAnimating = false
                }
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    isAnimating = false
                    // 动画结束后，触发拖拽结束事件
                    seekBarChangeListener?.onStopTrackingTouch(this@VipTrialSeekBar)
                }
            })
        }

        // 添加触觉反馈，提升用户体验
        performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY)

        animator.start()
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
