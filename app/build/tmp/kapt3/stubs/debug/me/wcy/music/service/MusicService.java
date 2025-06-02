package me.wcy.music.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.OptIn;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.DefaultLoadControl;
import androidx.media3.exoplayer.upstream.DefaultAllocator;
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory;
import androidx.media3.session.DefaultMediaNotificationProvider;
import androidx.media3.session.MediaSession;
import androidx.media3.session.MediaSessionService;
import com.blankj.utilcode.util.IntentUtils;
import me.wcy.music.R;
import me.wcy.music.net.datasource.MusicDataSource;
import me.wcy.music.utils.MusicUtils;
import top.wangchenyan.common.CommonApp;

/**
 * Created by wangchenyan.top on 2024/3/26.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\b\u0010\t\u001a\u00020\nH\u0017J\b\u0010\u000b\u001a\u00020\nH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0012\u0010\u000f\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\b\u0010\u0012\u001a\u00020\nH\u0003J\b\u0010\u0013\u001a\u00020\nH\u0003J\b\u0010\u0014\u001a\u00020\nH\u0003R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lme/wcy/music/service/MusicService;", "Landroidx/media3/session/MediaSessionService;", "()V", "player", "Landroidx/media3/common/Player;", "session", "Landroidx/media3/session/MediaSession;", "createOptimizedLoadControl", "Landroidx/media3/exoplayer/DefaultLoadControl;", "onCreate", "", "onDestroy", "onGetSession", "controllerInfo", "Landroidx/media3/session/MediaSession$ControllerInfo;", "onTaskRemoved", "rootIntent", "Landroid/content/Intent;", "setupAutomotiveNotification", "setupNotificationProvider", "setupPhoneNotification", "Companion", "app_debug"})
public final class MusicService extends androidx.media3.session.MediaSessionService {
    private androidx.media3.common.Player player;
    private androidx.media3.session.MediaSession session;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "MusicService";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String EXTRA_NOTIFICATION = null;
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.service.MusicService.Companion Companion = null;
    
    public MusicService() {
        super();
    }
    
    @java.lang.Override()
    @androidx.annotation.OptIn(markerClass = {androidx.media3.common.util.UnstableApi.class})
    public void onCreate() {
    }
    
    /**
     * 根据系统类型配置通知栏
     * Android Automotive系统使用专用的AutomotiveMediaNotificationProvider
     * 手机Android系统使用原始的DefaultMediaNotificationProvider
     */
    @androidx.annotation.OptIn(markerClass = {androidx.media3.common.util.UnstableApi.class})
    private final void setupNotificationProvider() {
    }
    
    /**
     * 配置Android Automotive专用的通知栏
     * 确保通知栏在车载环境下的稳定显示和一致性
     */
    @androidx.annotation.OptIn(markerClass = {androidx.media3.common.util.UnstableApi.class})
    private final void setupAutomotiveNotification() {
    }
    
    /**
     * 配置手机Android系统的原始通知栏
     * 使用标准的DefaultMediaNotificationProvider，适配手机系统
     */
    @androidx.annotation.OptIn(markerClass = {androidx.media3.common.util.UnstableApi.class})
    private final void setupPhoneNotification() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public androidx.media3.session.MediaSession onGetSession(@org.jetbrains.annotations.NotNull()
    androidx.media3.session.MediaSession.ControllerInfo controllerInfo) {
        return null;
    }
    
    @java.lang.Override()
    public void onTaskRemoved(@org.jetbrains.annotations.Nullable()
    android.content.Intent rootIntent) {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    /**
     * 创建优化的LoadControl配置，平衡性能与用户体验
     */
    private final androidx.media3.exoplayer.DefaultLoadControl createOptimizedLoadControl() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lme/wcy/music/service/MusicService$Companion;", "", "()V", "EXTRA_NOTIFICATION", "", "getEXTRA_NOTIFICATION", "()Ljava/lang/String;", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getEXTRA_NOTIFICATION() {
            return null;
        }
    }
}