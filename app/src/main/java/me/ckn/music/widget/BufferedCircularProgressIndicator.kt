/**
 * WhisperPlay Music Player
 *
 * 文件描述：支持缓存进度显示的圆形进度条
 * File Description: Circular progress indicator that supports displaying buffer progress.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import me.ckn.music.R

/**
 * 带缓冲的圆形进度指示器
 * Buffered Circular Progress Indicator
 *
 * 主要功能：
 * Main Functions:
 * - 显示主进度和缓冲进度 / Displays primary progress and buffer progress.
 * - 可自定义颜色和线条宽度 / Customizable colors and stroke width.
 *
 * 使用示例：
 * Usage Example:
 * ```xml
 * <me.ckn.music.widget.BufferedCircularProgressIndicator
 *     android:id="@+id/progress_indicator"
 *     android:layout_width="48dp"
 *     android:layout_height="48dp"
 *     app:progress="50"
 *     app:bufferProgress="80" />
 * ```
 *
 * @author ckn
 * @since 2025-06-10
 */
class BufferedCircularProgressIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val bufferPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val rectF = RectF()

    /**
     * 主进度 (0-100)
     * Primary progress (0-100)
     */
    var progress: Int = 0
        set(value) {
            field = value.coerceIn(0, 100)
            invalidate()
        }

    /**
     * 缓冲进度 (0-100)
     * Buffer progress (0-100)
     */
    var bufferProgress: Int = 0
        set(value) {
            field = value.coerceIn(0, 100)
            invalidate()
        }

    /**
     * 最大值
     * Maximum value
     */
    var max: Int = 100
        set(value) {
            field = value.coerceAtLeast(1)
            invalidate()
        }

    /**
     * 进度条线条宽度
     * Stroke width of the progress bar
     */
    var strokeWidth: Float = 8f
        set(value) {
            field = value
            backgroundPaint.strokeWidth = value
            bufferPaint.strokeWidth = value
            progressPaint.strokeWidth = value
            invalidate()
        }

    init {
        // 设置默认颜色
        backgroundPaint.color = ContextCompat.getColor(context, R.color.common_divider)
        bufferPaint.color = ContextCompat.getColor(context, R.color.translucent_white_p50)
        progressPaint.color = ContextCompat.getColor(context, R.color.common_theme_color)

        strokeWidth = 8f
    }

    /**
     * 设置进度条颜色
     * Set the colors of the progress bar.
     *
     * @param backgroundColor 背景颜色 / Background color.
     * @param bufferColor 缓冲颜色 / Buffer color.
     * @param progressColor 进度颜色 / Progress color.
     */
    fun setColors(backgroundColor: Int, bufferColor: Int, progressColor: Int) {
        backgroundPaint.color = backgroundColor
        bufferPaint.color = bufferColor
        progressPaint.color = progressColor
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val padding = strokeWidth / 2
        rectF.set(padding, padding, w - padding, h - padding)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        // 绘制背景圆环
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2, backgroundPaint)
        
        // 绘制缓存进度
        if (bufferProgress > 0) {
            val bufferAngle = 360f * bufferProgress / max
            canvas.drawArc(rectF, -90f, bufferAngle, false, bufferPaint)
        }
        
        // 绘制播放进度
        if (progress > 0) {
            val progressAngle = 360f * progress / max
            canvas.drawArc(rectF, -90f, progressAngle, false, progressPaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = 56 // 默认大小 dp
        val defaultSize = (size * resources.displayMetrics.density).toInt()
        
        val width = resolveSize(defaultSize, widthMeasureSpec)
        val height = resolveSize(defaultSize, heightMeasureSpec)
        
        // 保持正方形
        val finalSize = minOf(width, height)
        setMeasuredDimension(finalSize, finalSize)
    }
}
