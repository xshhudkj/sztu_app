package me.wcy.music.service;

import androidx.annotation.MainThread;
import androidx.lifecycle.LiveData;
import androidx.media3.common.MediaItem;
import kotlinx.coroutines.flow.StateFlow;

/**
 * Created by wangchenyan.top on 2024/3/26.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\t\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\tH\'J\u0016\u0010\u001b\u001a\u00020\u00192\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\u0016H\'J\b\u0010\u001d\u001a\u00020\u0019H\'J\u0010\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\tH\'J\b\u0010\u001f\u001a\u00020\u0004H\'J\b\u0010 \u001a\u00020\u0019H\'J\u0010\u0010!\u001a\u00020\u00192\u0006\u0010\"\u001a\u00020#H\'J\b\u0010$\u001a\u00020\u0019H\'J\b\u0010%\u001a\u00020\u0019H\'J\u001e\u0010&\u001a\u00020\u00192\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\u00162\u0006\u0010\u001a\u001a\u00020\tH\'J\u0010\u0010\'\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\u0004H\'J\u0010\u0010)\u001a\u00020\u00192\u0006\u0010*\u001a\u00020\rH\'J\b\u0010+\u001a\u00020\u0019H\'R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u0006R\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0006R\u0018\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0006R\u001e\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00160\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u000b\u00a8\u0006,"}, d2 = {"Lme/wcy/music/service/PlayerController;", "", "bufferingPercent", "Lkotlinx/coroutines/flow/StateFlow;", "", "getBufferingPercent", "()Lkotlinx/coroutines/flow/StateFlow;", "currentSong", "Landroidx/lifecycle/LiveData;", "Landroidx/media3/common/MediaItem;", "getCurrentSong", "()Landroidx/lifecycle/LiveData;", "playMode", "Lme/wcy/music/service/PlayMode;", "getPlayMode", "playProgress", "", "getPlayProgress", "playState", "Lme/wcy/music/service/PlayState;", "getPlayState", "playlist", "", "getPlaylist", "addAndPlay", "", "song", "appendToPlaylist", "songList", "clearPlaylist", "delete", "getAudioSessionId", "next", "play", "mediaId", "", "playPause", "prev", "replaceAll", "seekTo", "msec", "setPlayMode", "mode", "stop", "app_debug"})
public abstract interface PlayerController {
    
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<androidx.media3.common.MediaItem>> getPlaylist();
    
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<androidx.media3.common.MediaItem> getCurrentSong();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.StateFlow<me.wcy.music.service.PlayState> getPlayState();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.StateFlow<java.lang.Long> getPlayProgress();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getBufferingPercent();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.StateFlow<me.wcy.music.service.PlayMode> getPlayMode();
    
    @androidx.annotation.MainThread()
    public abstract void addAndPlay(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem song);
    
    @androidx.annotation.MainThread()
    public abstract void replaceAll(@org.jetbrains.annotations.NotNull()
    java.util.List<androidx.media3.common.MediaItem> songList, @org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem song);
    
    @androidx.annotation.MainThread()
    public abstract void appendToPlaylist(@org.jetbrains.annotations.NotNull()
    java.util.List<androidx.media3.common.MediaItem> songList);
    
    @androidx.annotation.MainThread()
    public abstract void play(@org.jetbrains.annotations.NotNull()
    java.lang.String mediaId);
    
    @androidx.annotation.MainThread()
    public abstract void delete(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem song);
    
    @androidx.annotation.MainThread()
    public abstract void clearPlaylist();
    
    @androidx.annotation.MainThread()
    public abstract void playPause();
    
    @androidx.annotation.MainThread()
    public abstract void next();
    
    @androidx.annotation.MainThread()
    public abstract void prev();
    
    @androidx.annotation.MainThread()
    public abstract void seekTo(int msec);
    
    @androidx.annotation.MainThread()
    public abstract int getAudioSessionId();
    
    @androidx.annotation.MainThread()
    public abstract void setPlayMode(@org.jetbrains.annotations.NotNull()
    me.wcy.music.service.PlayMode mode);
    
    @androidx.annotation.MainThread()
    public abstract void stop();
}