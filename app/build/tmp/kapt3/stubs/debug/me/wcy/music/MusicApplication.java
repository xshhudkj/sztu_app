package me.wcy.music;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0016H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u00020\u00108\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0018"}, d2 = {"Lme/wcy/music/MusicApplication;", "Landroid/app/Application;", "()V", "darkModeService", "Lme/wcy/music/common/DarkModeService;", "getDarkModeService", "()Lme/wcy/music/common/DarkModeService;", "setDarkModeService", "(Lme/wcy/music/common/DarkModeService;)V", "likeSongProcessor", "Lme/wcy/music/service/likesong/LikeSongProcessor;", "getLikeSongProcessor", "()Lme/wcy/music/service/likesong/LikeSongProcessor;", "setLikeSongProcessor", "(Lme/wcy/music/service/likesong/LikeSongProcessor;)V", "userService", "Lme/wcy/music/account/service/UserService;", "getUserService", "()Lme/wcy/music/account/service/UserService;", "setUserService", "(Lme/wcy/music/account/service/UserService;)V", "initCRouter", "", "onCreate", "app_debug"})
public final class MusicApplication extends android.app.Application {
    @javax.inject.Inject()
    public me.wcy.music.account.service.UserService userService;
    @javax.inject.Inject()
    public me.wcy.music.common.DarkModeService darkModeService;
    @javax.inject.Inject()
    public me.wcy.music.service.likesong.LikeSongProcessor likeSongProcessor;
    
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
}