/**
 * WhisperPlay Music Player
 *
 * 文件描述：专辑封面自定义View，实现了旋转的黑胶唱片和唱臂动画。
 * File Description: Custom view for the album cover, implementing a rotating vinyl record and tonearm animation.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.widget

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.res.ResourcesCompat
import com.blankj.utilcode.util.SizeUtils
import me.ckn.music.R
import me.ckn.music.utils.ImageUtils
import top.wangchenyan.common.ext.startOrResume
import kotlin.math.min

/**
 * 专辑封面视图
 * Album Cover View
 *
 * 主要功能：
 * Main Functions:
 * - 显示专辑封面、黑胶唱片和唱臂 / Display album cover, vinyl record, and tonearm.
 * - 实现播放时唱片旋转和唱臂移动的动画 / Animate the record rotation and tonearm movement during playback.
 * - 控制播放和暂停状态的动画切换 / Control animation transitions between play and pause states.
 *
 * 使用示例：
 * Usage Example:
 * ```xml
 * <me.ckn.music.widget.AlbumCoverView
 *     android:id="@+id/album_cover_view"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content" />
 * ```
 *
 * @author ckn
 * @since 2025-06-10
 */
class AlbumCoverView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val coverBorder: Drawable by lazy {
        ResourcesCompat.getDrawable(resources, R.drawable.bg_playing_cover_border, null)!!
    }

    private var discBitmap = BitmapFactory.decodeResource(resources, R.drawable.bg_playing_disc)
    private val discMatrix by lazy { Matrix() }
    private val discStartPoint by lazy { Point() } // 图片起始坐标
    private val discCenterPoint by lazy { Point() } // 旋转中心坐标
    private var discRotation = 0.0f

    private var needleBitmap =
        BitmapFactory.decodeResource(resources, R.drawable.ic_playing_needle)
    private val needleMatrix by lazy { Matrix() }
    private val needleStartPoint by lazy { Point() }
    private val needleCenterPoint by lazy { Point() }
    private var needleRotation = NEEDLE_ROTATION_PLAY

    private var coverBitmap: Bitmap? = null
    private val coverMatrix by lazy { Matrix() }
    private val coverStartPoint by lazy { Point() }
    private val coverCenterPoint by lazy { Point() }
    private var coverSize = 0

    private val rotationAnimator by lazy {
        ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 20000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener(rotationUpdateListener)
        }
    }
    private val playAnimator by lazy {
        ValueAnimator.ofFloat(NEEDLE_ROTATION_PAUSE, NEEDLE_ROTATION_PLAY).apply {
            duration = 300
            addUpdateListener(animationUpdateListener)
        }
    }
    private val pauseAnimator by lazy {
        ValueAnimator.ofFloat(NEEDLE_ROTATION_PLAY, NEEDLE_ROTATION_PAUSE).apply {
            duration = 300
            addUpdateListener(animationUpdateListener)
        }
    }

    private var isPlaying = false

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) {
            initSize()
        }
    }

    private fun initSize() {
        // 黑胶大小恢复，唱臂继续往下移，唱片往上移
        val unit = min(width / 8, height / 9)

        // 唱臂：保持尺寸，和封面一起上移20像素（8+12）
        needleBitmap = ImageUtils.resizeImage(needleBitmap, (unit * 2.1).toInt(), (unit * 3.4).toInt())
        needleStartPoint.x = (width / 2 - needleBitmap.width / 5.5f).toInt()
        needleStartPoint.y = 15 // 从27再上移12到15（总共从35上移20）
        needleCenterPoint.x = width / 2
        needleCenterPoint.y = (needleBitmap.width / 5.5f).toInt() + 5 // 从17再上移12到5（总共从25上移20）

        // 黑胶唱片：恢复原始尺寸，和唱臂一起上移20像素
        discBitmap = ImageUtils.resizeImage(discBitmap, (unit * 6.2).toInt(), (unit * 6.2).toInt())
        val discOffsetY = (needleBitmap.height / 1.2).toInt() - 20 // 总共上移20像素
        discStartPoint.x = (width - discBitmap.width) / 2
        discStartPoint.y = discOffsetY
        discCenterPoint.x = width / 2
        discCenterPoint.y = discBitmap.height / 2 + discOffsetY

        // 封面：恢复原始尺寸
        coverSize = (unit * 4.1).toInt()
        coverStartPoint.x = (width - coverSize) / 2
        coverStartPoint.y = discOffsetY + (discBitmap.height - coverSize) / 2
        coverCenterPoint.x = discCenterPoint.x
        coverCenterPoint.y = discCenterPoint.y
    }

    override fun onDraw(canvas: Canvas) {
        // 1.绘制封面
        val cover = coverBitmap
        if (cover != null) {
            coverMatrix.setRotate(
                discRotation,
                coverCenterPoint.x.toFloat(),
                coverCenterPoint.y.toFloat()
            )
            coverMatrix.preTranslate(coverStartPoint.x.toFloat(), coverStartPoint.y.toFloat())
            coverMatrix.preScale(
                coverSize.toFloat() / cover.width,
                coverSize.toFloat() / cover.height
            )
            canvas.drawBitmap(cover, coverMatrix, null)
        }

        // 2.绘制黑胶唱片外侧半透明边框
        coverBorder.setBounds(
            discStartPoint.x - COVER_BORDER_WIDTH,
            discStartPoint.y - COVER_BORDER_WIDTH,
            discStartPoint.x + discBitmap.width + COVER_BORDER_WIDTH,
            discStartPoint.y + discBitmap.height + COVER_BORDER_WIDTH
        )
        coverBorder.draw(canvas)

        // 3.绘制黑胶
        // 设置旋转中心和旋转角度，setRotate和preTranslate顺序很重要
        discMatrix.setRotate(
            discRotation,
            discCenterPoint.x.toFloat(),
            discCenterPoint.y.toFloat()
        )
        // 设置图片起始坐标
        discMatrix.preTranslate(discStartPoint.x.toFloat(), discStartPoint.y.toFloat())
        canvas.drawBitmap(discBitmap, discMatrix, null)

        // 4.绘制指针
        needleMatrix.setRotate(
            needleRotation,
            needleCenterPoint.x.toFloat(),
            needleCenterPoint.y.toFloat()
        )
        needleMatrix.preTranslate(needleStartPoint.x.toFloat(), needleStartPoint.y.toFloat())
        canvas.drawBitmap(needleBitmap, needleMatrix, null)
    }

    /**
     * 初始化唱臂位置
     * Initialize the position of the needle.
     *
     * @param isPlaying 当前是否正在播放 / Whether it is currently playing.
     */
    fun initNeedle(isPlaying: Boolean) {
        needleRotation = if (isPlaying) NEEDLE_ROTATION_PLAY else NEEDLE_ROTATION_PAUSE
        invalidate()
    }

    /**
     * 设置专辑封面图片
     * Set the album cover image.
     *
     * @param bitmap 专辑封面位图 / The album cover bitmap.
     */
    fun setCoverBitmap(bitmap: Bitmap) {
        coverBitmap = bitmap
        invalidate()
    }

    /**
     * 开始播放动画
     * Start the playback animation.
     */
    fun start() {
        if (isPlaying) {
            return
        }
        isPlaying = true
        rotationAnimator.startOrResume()
        playAnimator.start()
    }

    /**
     * 暂停播放动画
     * Pause the playback animation.
     */
    fun pause() {
        if (!isPlaying) {
            return
        }
        isPlaying = false
        rotationAnimator.pause()
        pauseAnimator.start()
    }

    /**
     * 重置视图状态
     * Reset the view state.
     */
    fun reset() {
        isPlaying = false
        discRotation = 0.0f
        rotationAnimator.cancel()
        invalidate()
    }

    private val rotationUpdateListener = AnimatorUpdateListener { animation ->
        discRotation = animation.animatedValue as Float
        invalidate()
    }

    private val animationUpdateListener = AnimatorUpdateListener { animation ->
        needleRotation = animation.animatedValue as Float
        invalidate()
    }

    companion object {
        private const val NEEDLE_ROTATION_PLAY = 0.0f
        private const val NEEDLE_ROTATION_PAUSE = -25.0f

        private val COVER_BORDER_WIDTH = SizeUtils.dp2px(6f)
    }
}