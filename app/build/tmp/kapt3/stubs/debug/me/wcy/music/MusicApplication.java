package me.wcy.music;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.media3.session.MediaController;
import androidx.media3.session.SessionToken;
import com.blankj.utilcode.util.ActivityUtils;
import com.google.common.util.concurrent.MoreExecutors;
import dagger.hilt.android.HiltAndroidApp;
import me.wcy.music.account.service.UserService;
import me.wcy.music.common.DarkModeService;
import me.wcy.music.common.MusicFragmentContainerActivity;
import me.wcy.music.service.MusicService;
import me.wcy.music.service.PlayServiceModule;
import me.wcy.music.service.likesong.LikeSongProcessor;
import me.wcy.router.CRouter;
import me.wcy.router.RouterClient;
import top.wangchenyan.common.CommonApp;
import javax.inject.Inject;

/**
 * 自定义Application
 * Created by wcy on 2015/11/27.
 */
@dagger.hilt.android.HiltAndroidApp()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 \u00192\u00020\u0001:\u0002\u0018\u0019B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0016H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u00020\u00108\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001a"}, d2 = {"Lme/wcy/music/MusicApplication;", "Landroid/app/Application;", "()V", "darkModeService", "Lme/wcy/music/common/DarkModeService;", "getDarkModeService", "()Lme/wcy/music/common/DarkModeService;", "setDarkModeService", "(Lme/wcy/music/common/DarkModeService;)V", "likeSongProcessor", "Lme/wcy/music/service/likesong/LikeSongProcessor;", "getLikeSongProcessor", "()Lme/wcy/music/service/likesong/LikeSongProcessor;", "setLikeSongProcessor", "(Lme/wcy/music/service/likesong/LikeSongProcessor;)V", "userService", "Lme/wcy/music/account/service/UserService;", "getUserService", "()Lme/wcy/music/account/service/UserService;", "setUserService", "(Lme/wcy/music/account/service/UserService;)V", "initCRouter", "", "onCreate", "AppLifecycleCallbacks", "Companion", "app_debug"})
public final class MusicApplication extends android.app.Application {
    @javax.inject.Inject()
    public me.wcy.music.account.service.UserService userService;
    @javax.inject.Inject()
    public me.wcy.music.common.DarkModeService darkModeService;
    @javax.inject.Inject()
    public me.wcy.music.service.likesong.LikeSongProcessor likeSongProcessor;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "app_launch_state";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_LAST_PAUSE_TIME = "last_pause_time";
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.MusicApplication.Companion Companion = null;
    
    public MusicApplication() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.account.service.UserService getUserService() {
        return null;
    }
    
    public final void setUserService(@org.jetbrains.annotations.NotNull()
    me.wcy.music.account.service.UserService p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.DarkModeService getDarkModeService() {
        return null;
    }
    
    public final void setDarkModeService(@org.jetbrains.annotations.NotNull()
    me.wcy.music.common.DarkModeService p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.service.likesong.LikeSongProcessor getLikeSongProcessor() {
        return null;
    }
    
    public final void setLikeSongProcessor(@org.jetbrains.annotations.NotNull()
    me.wcy.music.service.likesong.LikeSongProcessor p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    private final void initCRouter() {
    }
    
    /**
     * 应用生命周期监听器
     * 用于记录应用进入后台的时间，判断是否需要显示启动页
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u0010\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\fH\u0016J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u0014\u001a\u00020\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lme/wcy/music/MusicApplication$AppLifecycleCallbacks;", "Landroid/app/Application$ActivityLifecycleCallbacks;", "(Lme/wcy/music/MusicApplication;)V", "activityReferences", "", "isActivityChangingConfigurations", "", "onActivityCreated", "", "activity", "Landroid/app/Activity;", "savedInstanceState", "Landroid/os/Bundle;", "onActivityDestroyed", "onActivityPaused", "onActivityResumed", "onActivitySaveInstanceState", "outState", "onActivityStarted", "onActivityStopped", "recordAppPauseTime", "app_debug"})
    public final class AppLifecycleCallbacks implements android.app.Application.ActivityLifecycleCallbacks {
        private int activityReferences = 0;
        private boolean isActivityChangingConfigurations = false;
        
        public AppLifecycleCallbacks() {
            super();
        }
        
        @java.lang.Override()
        public void onActivityCreated(@org.jetbrains.annotations.NotNull()
        android.app.Activity activity, @org.jetbrains.annotations.Nullable()
        android.os.Bundle savedInstanceState) {
        }
        
        @java.lang.Override()
        public void onActivityStarted(@org.jetbrains.annotations.NotNull()
        android.app.Activity activity) {
        }
        
        @java.lang.Override()
        public void onActivityResumed(@org.jetbrains.annotations.NotNull()
        android.app.Activity activity) {
        }
        
        @java.lang.Override()
        public void onActivityPaused(@org.jetbrains.annotations.NotNull()
        android.app.Activity activity) {
        }
        
        @java.lang.Override()
        public void onActivityStopped(@org.jetbrains.annotations.NotNull()
        android.app.Activity activity) {
        }
        
        @java.lang.Override()
        public void onActivitySaveInstanceState(@org.jetbrains.annotations.NotNull()
        android.app.Activity activity, @org.jetbrains.annotations.NotNull()
        android.os.Bundle outState) {
        }
        
        @java.lang.Override()
        public void onActivityDestroyed(@org.jetbrains.annotations.NotNull()
        android.app.Activity activity) {
        }
        
        private final void recordAppPauseTime() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lme/wcy/music/MusicApplication$Companion;", "", "()V", "KEY_LAST_PAUSE_TIME", "", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}