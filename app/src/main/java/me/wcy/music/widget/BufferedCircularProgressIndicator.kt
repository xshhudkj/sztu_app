package me.wcy.music.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import me.wcy.music.R

/**
 * 支持缓存进度显示的圆形进度条
 * Created by wangchenyan.top on 2024/3/26.
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
    
    // 进度值 (0-100)
    var progress: Int = 0
        set(value) {
            field = value.coerceIn(0, 100)
            invalidate()
        }
    
    // 缓存进度值 (0-100)
    var bufferProgress: Int = 0
        set(value) {
            field = value.coerceIn(0, 100)
            invalidate()
        }
    
    // 最大值
    var max: Int = 100
        set(value) {
            field = value.coerceAtLeast(1)
            invalidate()
        }

    // 线条粗细
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
