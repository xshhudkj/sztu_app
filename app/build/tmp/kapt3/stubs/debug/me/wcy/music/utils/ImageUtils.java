package me.wcy.music.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import me.wcy.music.R;

/**
 * 图像工具类
 * Created by wcy on 2015/11/29.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u001c\u0010\t\u001a\u00020\n*\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u00012\u0006\u0010\r\u001a\u00020\u0007J\u0012\u0010\u000e\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010\u00a8\u0006\u0011"}, d2 = {"Lme/wcy/music/utils/ImageUtils;", "", "()V", "resizeImage", "Landroid/graphics/Bitmap;", "source", "dstWidth", "", "dstHeight", "loadCover", "", "Landroid/widget/ImageView;", "url", "corners", "transAlpha", "topToBottom", "", "app_debug"})
public final class ImageUtils {
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.utils.ImageUtils INSTANCE = null;
    
    private ImageUtils() {
        super();
    }
    
    /**
     * 将图片放大或缩小到指定尺寸
     */
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap resizeImage(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap source, int dstWidth, int dstHeight) {
        return null;
    }
    
    public final void loadCover(@org.jetbrains.annotations.NotNull()
    android.widget.ImageView $this$loadCover, @org.jetbrains.annotations.Nullable()
    java.lang.Object url, int corners) {
    }
    
    /**
     * 设置图片的透明度从上到下渐变
     * @param topToBottom 是否为从上到下变为透明
     */
    @org.jetbrains.annotations.NotNull()
    public final android.graphics.Bitmap transAlpha(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap $this$transAlpha, boolean topToBottom) {
        return null;
    }
}