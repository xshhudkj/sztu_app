package me.wcy.music.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.core.content.res.ResourcesCompat;
import com.blankj.utilcode.util.SizeUtils;
import me.wcy.music.R;
import me.wcy.music.utils.ImageUtils;

/**
 * 专辑封面
 * Created by wcy on 2015/11/30.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 W2\u00020\u0001:\u0001WB\'\b\u0007\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010G\u001a\u00020H2\u0006\u0010.\u001a\u00020/J\b\u0010I\u001a\u00020HH\u0002J\u0010\u0010J\u001a\u00020H2\u0006\u0010K\u001a\u00020LH\u0014J(\u0010M\u001a\u00020H2\u0006\u0010N\u001a\u00020\u00072\u0006\u0010O\u001a\u00020\u00072\u0006\u0010P\u001a\u00020\u00072\u0006\u0010Q\u001a\u00020\u0007H\u0014J\u0006\u0010R\u001a\u00020HJ\u0006\u0010S\u001a\u00020HJ\u000e\u0010T\u001a\u00020H2\u0006\u0010U\u001a\u00020\fJ\u0006\u0010V\u001a\u00020HR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0012\u001a\u0004\b\u0015\u0010\u0016R\u001b\u0010\u0018\u001a\u00020\u00198BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0012\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001d\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u001e\u001a\u00020\u00148BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b \u0010\u0012\u001a\u0004\b\u001f\u0010\u0016R\u0016\u0010!\u001a\n \"*\u0004\u0018\u00010\f0\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010#\u001a\u00020\u00148BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b%\u0010\u0012\u001a\u0004\b$\u0010\u0016R\u001b\u0010&\u001a\u00020\u00198BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b(\u0010\u0012\u001a\u0004\b\'\u0010\u001bR\u000e\u0010)\u001a\u00020*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010+\u001a\u00020\u00148BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b-\u0010\u0012\u001a\u0004\b,\u0010\u0016R\u000e\u0010.\u001a\u00020/X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u00100\u001a\n \"*\u0004\u0018\u00010\f0\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u00101\u001a\u00020\u00148BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b3\u0010\u0012\u001a\u0004\b2\u0010\u0016R\u001b\u00104\u001a\u00020\u00198BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b6\u0010\u0012\u001a\u0004\b5\u0010\u001bR\u000e\u00107\u001a\u00020*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u00108\u001a\u00020\u00148BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b:\u0010\u0012\u001a\u0004\b9\u0010\u0016R#\u0010;\u001a\n \"*\u0004\u0018\u00010<0<8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b?\u0010\u0012\u001a\u0004\b=\u0010>R#\u0010@\u001a\n \"*\u0004\u0018\u00010<0<8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\bB\u0010\u0012\u001a\u0004\bA\u0010>R#\u0010C\u001a\n \"*\u0004\u0018\u00010<0<8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\bE\u0010\u0012\u001a\u0004\bD\u0010>R\u000e\u0010F\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006X"}, d2 = {"Lme/wcy/music/widget/AlbumCoverView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "animationUpdateListener", "Landroid/animation/ValueAnimator$AnimatorUpdateListener;", "coverBitmap", "Landroid/graphics/Bitmap;", "coverBorder", "Landroid/graphics/drawable/Drawable;", "getCoverBorder", "()Landroid/graphics/drawable/Drawable;", "coverBorder$delegate", "Lkotlin/Lazy;", "coverCenterPoint", "Landroid/graphics/Point;", "getCoverCenterPoint", "()Landroid/graphics/Point;", "coverCenterPoint$delegate", "coverMatrix", "Landroid/graphics/Matrix;", "getCoverMatrix", "()Landroid/graphics/Matrix;", "coverMatrix$delegate", "coverSize", "coverStartPoint", "getCoverStartPoint", "coverStartPoint$delegate", "discBitmap", "kotlin.jvm.PlatformType", "discCenterPoint", "getDiscCenterPoint", "discCenterPoint$delegate", "discMatrix", "getDiscMatrix", "discMatrix$delegate", "discRotation", "", "discStartPoint", "getDiscStartPoint", "discStartPoint$delegate", "isPlaying", "", "needleBitmap", "needleCenterPoint", "getNeedleCenterPoint", "needleCenterPoint$delegate", "needleMatrix", "getNeedleMatrix", "needleMatrix$delegate", "needleRotation", "needleStartPoint", "getNeedleStartPoint", "needleStartPoint$delegate", "pauseAnimator", "Landroid/animation/ValueAnimator;", "getPauseAnimator", "()Landroid/animation/ValueAnimator;", "pauseAnimator$delegate", "playAnimator", "getPlayAnimator", "playAnimator$delegate", "rotationAnimator", "getRotationAnimator", "rotationAnimator$delegate", "rotationUpdateListener", "initNeedle", "", "initSize", "onDraw", "canvas", "Landroid/graphics/Canvas;", "onSizeChanged", "w", "h", "oldw", "oldh", "pause", "reset", "setCoverBitmap", "bitmap", "start", "Companion", "app_debug"})
public final class AlbumCoverView extends android.view.View {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy coverBorder$delegate = null;
    private android.graphics.Bitmap discBitmap;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy discMatrix$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy discStartPoint$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy discCenterPoint$delegate = null;
    private float discRotation = 0.0F;
    private android.graphics.Bitmap needleBitmap;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy needleMatrix$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy needleStartPoint$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy needleCenterPoint$delegate = null;
    private float needleRotation = 0.0F;
    @org.jetbrains.annotations.Nullable()
    private android.graphics.Bitmap coverBitmap;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy coverMatrix$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy coverStartPoint$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy coverCenterPoint$delegate = null;
    private int coverSize = 0;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy rotationAnimator$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy playAnimator$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy pauseAnimator$delegate = null;
    private boolean isPlaying = false;
    @org.jetbrains.annotations.NotNull()
    private final android.animation.ValueAnimator.AnimatorUpdateListener rotationUpdateListener = null;
    @org.jetbrains.annotations.NotNull()
    private final android.animation.ValueAnimator.AnimatorUpdateListener animationUpdateListener = null;
    private static final float NEEDLE_ROTATION_PLAY = 0.0F;
    private static final float NEEDLE_ROTATION_PAUSE = -25.0F;
    private static final int COVER_BORDER_WIDTH = 0;
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.widget.AlbumCoverView.Companion Companion = null;
    
    @kotlin.jvm.JvmOverloads()
    public AlbumCoverView(@org.jetbrains.annotations.Nullable()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    private final android.graphics.drawable.Drawable getCoverBorder() {
        return null;
    }
    
    private final android.graphics.Matrix getDiscMatrix() {
        return null;
    }
    
    private final android.graphics.Point getDiscStartPoint() {
        return null;
    }
    
    private final android.graphics.Point getDiscCenterPoint() {
        return null;
    }
    
    private final android.graphics.Matrix getNeedleMatrix() {
        return null;
    }
    
    private final android.graphics.Point getNeedleStartPoint() {
        return null;
    }
    
    private final android.graphics.Point getNeedleCenterPoint() {
        return null;
    }
    
    private final android.graphics.Matrix getCoverMatrix() {
        return null;
    }
    
    private final android.graphics.Point getCoverStartPoint() {
        return null;
    }
    
    private final android.graphics.Point getCoverCenterPoint() {
        return null;
    }
    
    private final android.animation.ValueAnimator getRotationAnimator() {
        return null;
    }
    
    private final android.animation.ValueAnimator getPlayAnimator() {
        return null;
    }
    
    private final android.animation.ValueAnimator getPauseAnimator() {
        return null;
    }
    
    @java.lang.Override()
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    }
    
    private final void initSize() {
    }
    
    @java.lang.Override()
    protected void onDraw(@org.jetbrains.annotations.NotNull()
    android.graphics.Canvas canvas) {
    }
    
    public final void initNeedle(boolean isPlaying) {
    }
    
    public final void setCoverBitmap(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap bitmap) {
    }
    
    public final void start() {
    }
    
    public final void pause() {
    }
    
    public final void reset() {
    }
    
    @kotlin.jvm.JvmOverloads()
    public AlbumCoverView(@org.jetbrains.annotations.Nullable()
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public AlbumCoverView(@org.jetbrains.annotations.Nullable()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lme/wcy/music/widget/AlbumCoverView$Companion;", "", "()V", "COVER_BORDER_WIDTH", "", "NEEDLE_ROTATION_PAUSE", "", "NEEDLE_ROTATION_PLAY", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}