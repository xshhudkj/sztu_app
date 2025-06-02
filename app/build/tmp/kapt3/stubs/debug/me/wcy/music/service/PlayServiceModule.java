package me.wcy.music.service;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.media3.common.Player;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import me.wcy.music.storage.db.MusicDatabase;

/**
 * Created by wangchenyan.top on 2024/3/26.
 */
@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001:\u0001\u0014B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u000bJ\n\u0010\f\u001a\u00020\r*\u00020\u0013R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001f\u0010\u0007\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lme/wcy/music/service/PlayServiceModule;", "", "()V", "_isPlayerReady", "Landroidx/lifecycle/MutableLiveData;", "", "kotlin.jvm.PlatformType", "isPlayerReady", "Landroidx/lifecycle/LiveData;", "()Landroidx/lifecycle/LiveData;", "player", "Landroidx/media3/common/Player;", "playerController", "Lme/wcy/music/service/PlayerController;", "providerPlayerController", "db", "Lme/wcy/music/storage/db/MusicDatabase;", "setPlayer", "", "Landroid/app/Application;", "PlayerControllerEntryPoint", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class PlayServiceModule {
    @org.jetbrains.annotations.Nullable()
    private static androidx.media3.common.Player player;
    @org.jetbrains.annotations.Nullable()
    private static me.wcy.music.service.PlayerController playerController;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isPlayerReady = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.lifecycle.LiveData<java.lang.Boolean> isPlayerReady = null;
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.service.PlayServiceModule INSTANCE = null;
    
    private PlayServiceModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isPlayerReady() {
        return null;
    }
    
    public final void setPlayer(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.Player player) {
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.service.PlayerController providerPlayerController(@org.jetbrains.annotations.NotNull()
    me.wcy.music.storage.db.MusicDatabase db) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.service.PlayerController playerController(@org.jetbrains.annotations.NotNull()
    android.app.Application $this$playerController) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&\u00a8\u0006\u0004"}, d2 = {"Lme/wcy/music/service/PlayServiceModule$PlayerControllerEntryPoint;", "", "playerController", "Lme/wcy/music/service/PlayerController;", "app_debug"})
    @dagger.hilt.EntryPoint()
    @dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
    public static abstract interface PlayerControllerEntryPoint {
        
        @org.jetbrains.annotations.NotNull()
        public abstract me.wcy.music.service.PlayerController playerController();
    }
}