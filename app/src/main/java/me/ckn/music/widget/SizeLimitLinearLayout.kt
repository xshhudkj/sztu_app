/**
 * WhisperPlay Music Player
 *
 * 文件描述：一个可以限制最大宽度和高度的LinearLayout。
 * File Description: A LinearLayout that can limit its maximum width and height.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import me.ckn.music.R

/**
 * 尺寸限制的LinearLayout
 * Size-limited LinearLayout
 *
 * 主要功能：
 * Main Functions:
 * - 允许在XML布局中或以编程方式设置最大宽度和最大高度 / Allows setting maxWidth and maxHeight in XML layouts or programmatically.
 *
 * 使用示例：
 * Usage Example:
 * ```xml
 * <me.ckn.music.widget.SizeLimitLinearLayout
 *     android:layout_width="wrap_content"
 *     android:layout_height="wrap_content"
 *     app:maxWidth="200dp"
 *     app:maxHeight="100dp">
 *
 *     <!-- 子视图 / Child views -->
 *
 * </me.ckn.music.widget.SizeLimitLinearLayout>
 * ```
 *
 * @author ckn
 * @since 2025-06-10
 */
class SizeLimitLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var maxWidth: Int = 0
    private var maxHeight: Int = 0

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.SizeLimitLinearLayout)
        maxWidth = ta.getDimensionPixelSize(R.styleable.SizeLimitLinearLayout_maxWidth, 0)
        maxHeight = ta.getDimensionPixelSize(R.styleable.SizeLimitLinearLayout_maxHeight, 0)
        ta.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthSpec = widthMeasureSpec
        var heightSpec = heightMeasureSpec
        if (maxWidth > 0 && MeasureSpec.getSize(widthSpec) > maxWidth) {
            widthSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.AT_MOST)
        }
        if (maxHeight > 0 && MeasureSpec.getSize(heightSpec) > maxHeight) {
            heightSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST)
        }
        super.onMeasure(widthSpec, heightSpec)
    }

    /**
     * 设置最大宽度
     * Set the maximum width.
     *
     * @param value 最大宽度（像素） / Maximum width in pixels.
     */
    fun setMaxWidth(value: Int) {
        this.maxWidth = value
        requestLayout()
    }

    /**
     * 设置最大高度
     * Set the maximum height.
     *
     * @param value 最大高度（像素） / Maximum height in pixels.
     */
    fun setMaxHeight(value: Int) {
        this.maxHeight = value
        requestLayout()
    }
}