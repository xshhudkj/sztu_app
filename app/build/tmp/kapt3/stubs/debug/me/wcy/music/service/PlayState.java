package me.wcy.music.service;

/**
 * Created by wangchenyan.top on 2023/9/18.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\t\n\u000b\fB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0005R\u0011\u0010\u0007\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0011\u0010\b\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0005\u0082\u0001\u0004\r\u000e\u000f\u0010\u00a8\u0006\u0011"}, d2 = {"Lme/wcy/music/service/PlayState;", "", "()V", "isIdle", "", "()Z", "isPausing", "isPlaying", "isPreparing", "Idle", "Pause", "Playing", "Preparing", "Lme/wcy/music/service/PlayState$Idle;", "Lme/wcy/music/service/PlayState$Pause;", "Lme/wcy/music/service/PlayState$Playing;", "Lme/wcy/music/service/PlayState$Preparing;", "app_debug"})
public abstract class PlayState {
    
    private PlayState() {
        super();
    }
    
    public final boolean isIdle() {
        return false;
    }
    
    public final boolean isPreparing() {
        return false;
    }
    
    public final boolean isPlaying() {
        return false;
    }
    
    public final boolean isPausing() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lme/wcy/music/service/PlayState$Idle;", "Lme/wcy/music/service/PlayState;", "()V", "app_debug"})
    public static final class Idle extends me.wcy.music.service.PlayState {
        @org.jetbrains.annotations.NotNull()
        public static final me.wcy.music.service.PlayState.Idle INSTANCE = null;
        
        private Idle() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lme/wcy/music/service/PlayState$Pause;", "Lme/wcy/music/service/PlayState;", "()V", "app_debug"})
    public static final class Pause extends me.wcy.music.service.PlayState {
        @org.jetbrains.annotations.NotNull()
        public static final me.wcy.music.service.PlayState.Pause INSTANCE = null;
        
        private Pause() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lme/wcy/music/service/PlayState$Playing;", "Lme/wcy/music/service/PlayState;", "()V", "app_debug"})
    public static final class Playing extends me.wcy.music.service.PlayState {
        @org.jetbrains.annotations.NotNull()
        public static final me.wcy.music.service.PlayState.Playing INSTANCE = null;
        
        private Playing() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lme/wcy/music/service/PlayState$Preparing;", "Lme/wcy/music/service/PlayState;", "()V", "app_debug"})
    public static final class Preparing extends me.wcy.music.service.PlayState {
        @org.jetbrains.annotations.NotNull()
        public static final me.wcy.music.service.PlayState.Preparing INSTANCE = null;
        
        private Preparing() {
        }
    }
}