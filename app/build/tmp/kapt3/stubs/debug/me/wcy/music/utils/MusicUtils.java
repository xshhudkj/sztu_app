package me.wcy.music.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.audiofx.AudioEffect;
import android.text.TextUtils;
import me.wcy.music.utils.LogUtils;
import me.wcy.music.R;

/**
 * 歌曲工具类
 * Created by wcy on 2015/11/27.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0018\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004J\u0014\u0010\u0014\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\n\u0010\u0017\u001a\u00020\u0004*\u00020\u0004J\n\u0010\u0018\u001a\u00020\u0004*\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lme/wcy/music/utils/MusicUtils;", "", "()V", "TAG", "", "getArtistAndAlbum", "artist", "album", "isAndroidAutomotive", "", "context", "Landroid/content/Context;", "isAudioControlPanelAvailable", "isIntentAvailable", "intent", "Landroid/content/Intent;", "keywordsTint", "", "text", "keywords", "appendImageSize", "size", "", "asLargeCover", "asSmallCover", "app_debug"})
public final class MusicUtils {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "MusicUtils";
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.utils.MusicUtils INSTANCE = null;
    
    private MusicUtils() {
        super();
    }
    
    public final boolean isAudioControlPanelAvailable(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    private final boolean isIntentAvailable(android.content.Context context, android.content.Intent intent) {
        return false;
    }
    
    /**
     * 检测是否为Android Automotive系统
     * 通过检查系统特性和包管理器来判断
     */
    public final boolean isAndroidAutomotive(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getArtistAndAlbum(@org.jetbrains.annotations.Nullable()
    java.lang.String artist, @org.jetbrains.annotations.Nullable()
    java.lang.String album) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.CharSequence keywordsTint(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    java.lang.String keywords) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String asSmallCover(@org.jetbrains.annotations.NotNull()
    java.lang.String $this$asSmallCover) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String asLargeCover(@org.jetbrains.annotations.NotNull()
    java.lang.String $this$asLargeCover) {
        return null;
    }
    
    private final java.lang.String appendImageSize(java.lang.String $this$appendImageSize, int size) {
        return null;
    }
}