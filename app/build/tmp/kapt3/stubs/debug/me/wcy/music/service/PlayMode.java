package me.wcy.music.service;

import androidx.annotation.StringRes;
import me.wcy.music.R;

/**
 * 播放模式
 * Created by wcy on 2015/12/26.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \t2\u00020\u0001:\u0004\t\n\u000b\fB\u0019\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u0082\u0001\u0003\r\u000e\u000f\u00a8\u0006\u0010"}, d2 = {"Lme/wcy/music/service/PlayMode;", "", "value", "", "nameRes", "(II)V", "getNameRes", "()I", "getValue", "Companion", "Loop", "Shuffle", "Single", "Lme/wcy/music/service/PlayMode$Loop;", "Lme/wcy/music/service/PlayMode$Shuffle;", "Lme/wcy/music/service/PlayMode$Single;", "app_debug"})
public abstract class PlayMode {
    private final int value = 0;
    private final int nameRes = 0;
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.service.PlayMode.Companion Companion = null;
    
    private PlayMode(int value, @androidx.annotation.StringRes()
    int nameRes) {
        super();
    }
    
    public final int getValue() {
        return 0;
    }
    
    public final int getNameRes() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lme/wcy/music/service/PlayMode$Companion;", "", "()V", "valueOf", "Lme/wcy/music/service/PlayMode;", "value", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final me.wcy.music.service.PlayMode valueOf(int value) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lme/wcy/music/service/PlayMode$Loop;", "Lme/wcy/music/service/PlayMode;", "()V", "app_debug"})
    public static final class Loop extends me.wcy.music.service.PlayMode {
        @org.jetbrains.annotations.NotNull()
        public static final me.wcy.music.service.PlayMode.Loop INSTANCE = null;
        
        private Loop() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lme/wcy/music/service/PlayMode$Shuffle;", "Lme/wcy/music/service/PlayMode;", "()V", "app_debug"})
    public static final class Shuffle extends me.wcy.music.service.PlayMode {
        @org.jetbrains.annotations.NotNull()
        public static final me.wcy.music.service.PlayMode.Shuffle INSTANCE = null;
        
        private Shuffle() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lme/wcy/music/service/PlayMode$Single;", "Lme/wcy/music/service/PlayMode;", "()V", "app_debug"})
    public static final class Single extends me.wcy.music.service.PlayMode {
        @org.jetbrains.annotations.NotNull()
        public static final me.wcy.music.service.PlayMode.Single INSTANCE = null;
        
        private Single() {
        }
    }
}