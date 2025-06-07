package me.wcy.music.service;

import androidx.annotation.MainThread;
import androidx.annotation.OptIn;
import androidx.lifecycle.MutableLiveData;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.StateFlow;
import me.wcy.music.storage.db.MusicDatabase;
import me.wcy.music.storage.preference.ConfigPreferences;
import me.wcy.music.utils.VipUtils;
import android.util.Log;
import me.wcy.music.net.datasource.MusicUrlCache;
import me.wcy.music.service.SmartPreloadManager;

/**
 * Created by wangchenyan.top on 2024/3/27.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0014\n\u0002\u0010\u000e\n\u0002\b\u0012\u0018\u0000 ^2\u00020\u00012\u00020\u0002:\u0001^B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0010\u00107\u001a\u0002082\u0006\u00109\u001a\u00020\rH\u0017J\u0016\u0010:\u001a\u0002082\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\r0\u0015H\u0017J\u0018\u0010<\u001a\u0002082\u0006\u0010=\u001a\u00020\r2\u0006\u0010>\u001a\u00020\u0011H\u0002J\b\u0010?\u001a\u000208H\u0002J\b\u0010@\u001a\u000208H\u0002J\u0006\u0010A\u001a\u000208J\b\u0010B\u001a\u000208H\u0017J\u0010\u0010C\u001a\u0002082\u0006\u00109\u001a\u00020\rH\u0017J&\u0010D\u001a\u0002082\f\u00100\u001a\b\u0012\u0004\u0012\u00020\r0\u00152\u0006\u0010E\u001a\u00020\n2\u0006\u0010F\u001a\u00020\u0011H\u0002J\b\u0010G\u001a\u00020\nH\u0017J\u001e\u0010H\u001a\u00020\n2\u0006\u0010I\u001a\u00020\n2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\r0\u0015H\u0002J\b\u0010J\u001a\u000208H\u0017J\u0010\u0010K\u001a\u0002082\u0006\u0010L\u001a\u00020MH\u0017J\b\u0010N\u001a\u000208H\u0017J\u001a\u0010O\u001a\u0002082\u0006\u0010L\u001a\u00020M2\b\b\u0002\u0010P\u001a\u00020\u0011H\u0007J\b\u0010Q\u001a\u000208H\u0002J\b\u0010R\u001a\u000208H\u0017J\u001e\u0010S\u001a\u0002082\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\r0\u00152\u0006\u00109\u001a\u00020\rH\u0017J\b\u0010T\u001a\u000208H\u0002J\u0010\u0010U\u001a\u0002082\u0006\u0010V\u001a\u00020\nH\u0017J\u0010\u0010W\u001a\u0002082\u0006\u0010X\u001a\u00020\u000fH\u0017J\u0010\u0010Y\u001a\u00020&2\u0006\u0010=\u001a\u00020\rH\u0002J\u0010\u0010Z\u001a\u0002082\u0006\u0010[\u001a\u00020\u0011H\u0002J\b\u0010\\\u001a\u000208H\u0017J\b\u0010]\u001a\u000208H\u0002R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R(\u0010\u0014\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\r \u0016*\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u00150\u00150\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u0019X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u0011X\u0082D\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u001d\u001a\u00020\u001eX\u0096\u0005\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u001c\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\"X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010*\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0019X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001bR\u001a\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00110\u0019X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001bR\u001a\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00130\u0019X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R.\u00100\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\r \u0016*\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u00150\u00150\"X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010$R\u000e\u00102\u001a\u00020\u0011X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0011X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u00104\u001a\u000205X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u0011X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006_"}, d2 = {"Lme/wcy/music/service/PlayerControllerImpl;", "Lme/wcy/music/service/PlayerController;", "Lkotlinx/coroutines/CoroutineScope;", "player", "Landroidx/media3/common/Player;", "db", "Lme/wcy/music/storage/db/MusicDatabase;", "(Landroidx/media3/common/Player;Lme/wcy/music/storage/db/MusicDatabase;)V", "_bufferingPercent", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_currentSong", "Landroidx/lifecycle/MutableLiveData;", "Landroidx/media3/common/MediaItem;", "_playMode", "Lme/wcy/music/service/PlayMode;", "_playProgress", "", "_playState", "Lme/wcy/music/service/PlayState;", "_playlist", "", "kotlin.jvm.PlatformType", "audioSessionId", "bufferingPercent", "Lkotlinx/coroutines/flow/StateFlow;", "getBufferingPercent", "()Lkotlinx/coroutines/flow/StateFlow;", "cacheCheckInterval", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "currentSong", "Landroidx/lifecycle/LiveData;", "getCurrentSong", "()Landroidx/lifecycle/LiveData;", "isNextSongCaching", "", "lastCacheCheckTime", "lastProgressCheckTime", "lastProgressUpdate", "playMode", "getPlayMode", "playProgress", "getPlayProgress", "playState", "getPlayState", "playlist", "getPlaylist", "progressCheckInterval", "progressUpdateInterval", "smartPreloadManager", "Lme/wcy/music/service/SmartPreloadManager;", "targetCacheLeadTime", "addAndPlay", "", "song", "appendToPlaylist", "songList", "cacheNextSongData", "mediaItem", "cacheTime", "checkContinuousCache", "checkSmartPreload", "cleanup", "clearPlaylist", "delete", "fallbackPlaybackStart", "index", "songId", "getAudioSessionId", "getNextSongIndex", "currentIndex", "next", "play", "mediaId", "", "playPause", "playWhenAvailable", "maxWaitTime", "preloadNextSongUrls", "prev", "replaceAll", "resetCacheState", "seekTo", "msec", "setPlayMode", "mode", "shouldCacheNextSong", "startNextSongCache", "remainingCacheTime", "stop", "updateBufferingPercent", "Companion", "app_debug"})
public final class PlayerControllerImpl implements me.wcy.music.service.PlayerController, kotlinx.coroutines.CoroutineScope {
    @org.jetbrains.annotations.NotNull()
    private final androidx.media3.common.Player player = null;
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.storage.db.MusicDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<androidx.media3.common.MediaItem>> _playlist = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<androidx.media3.common.MediaItem>> playlist = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<androidx.media3.common.MediaItem> _currentSong = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<androidx.media3.common.MediaItem> currentSong = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<me.wcy.music.service.PlayState> _playState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<me.wcy.music.service.PlayState> playState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> _playProgress = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> playProgress = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _bufferingPercent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> bufferingPercent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<me.wcy.music.service.PlayMode> _playMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<me.wcy.music.service.PlayMode> playMode = null;
    private int audioSessionId = 0;
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.service.SmartPreloadManager smartPreloadManager = null;
    private long lastProgressCheckTime = 0L;
    private final long progressCheckInterval = 500L;
    private long lastCacheCheckTime = 0L;
    private final long cacheCheckInterval = 2000L;
    private final long targetCacheLeadTime = 10000L;
    private boolean isNextSongCaching = false;
    private long lastProgressUpdate = 0L;
    private final long progressUpdateInterval = 200L;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "PlayerControllerImpl";
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.service.PlayerControllerImpl.Companion Companion = null;
    
    public PlayerControllerImpl(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.Player player, @org.jetbrains.annotations.NotNull()
    me.wcy.music.storage.db.MusicDatabase db) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public androidx.lifecycle.LiveData<java.util.List<androidx.media3.common.MediaItem>> getPlaylist() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public androidx.lifecycle.LiveData<androidx.media3.common.MediaItem> getCurrentSong() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.StateFlow<me.wcy.music.service.PlayState> getPlayState() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.StateFlow<java.lang.Long> getPlayProgress() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getBufferingPercent() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.StateFlow<me.wcy.music.service.PlayMode> getPlayMode() {
        return null;
    }
    
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public void addAndPlay(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem song) {
    }
    
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public void replaceAll(@org.jetbrains.annotations.NotNull()
    java.util.List<androidx.media3.common.MediaItem> songList, @org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem song) {
    }
    
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public void appendToPlaylist(@org.jetbrains.annotations.NotNull()
    java.util.List<androidx.media3.common.MediaItem> songList) {
    }
    
    /**
     * 智能播放指定歌曲，如果歌曲不在当前播放列表中则等待
     * @param mediaId 歌曲ID
     * @param maxWaitTime 最大等待时间（毫秒）
     */
    @androidx.annotation.MainThread()
    public final void playWhenAvailable(@org.jetbrains.annotations.NotNull()
    java.lang.String mediaId, long maxWaitTime) {
    }
    
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public void play(@org.jetbrains.annotations.NotNull()
    java.lang.String mediaId) {
    }
    
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public void delete(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem song) {
    }
    
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public void clearPlaylist() {
    }
    
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public void playPause() {
    }
    
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public void next() {
    }
    
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public void prev() {
    }
    
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public void seekTo(int msec) {
    }
    
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public int getAudioSessionId() {
        return 0;
    }
    
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public void setPlayMode(@org.jetbrains.annotations.NotNull()
    me.wcy.music.service.PlayMode mode) {
    }
    
    @java.lang.Override()
    @androidx.annotation.MainThread()
    public void stop() {
    }
    
    /**
     * 更新缓存进度百分比
     */
    private final void updateBufferingPercent() {
    }
    
    /**
     * 检查持续缓存状态
     */
    private final void checkContinuousCache() {
    }
    
    /**
     * 开始缓存下一首歌曲
     */
    private final void startNextSongCache(long remainingCacheTime) {
    }
    
    /**
     * 获取下一首歌曲的索引
     */
    private final int getNextSongIndex(int currentIndex, java.util.List<androidx.media3.common.MediaItem> playlist) {
        return 0;
    }
    
    /**
     * 检查是否应该缓存下一首歌曲
     */
    private final boolean shouldCacheNextSong(androidx.media3.common.MediaItem mediaItem) {
        return false;
    }
    
    /**
     * 缓存下一首歌曲的数据
     */
    private final void cacheNextSongData(androidx.media3.common.MediaItem mediaItem, long cacheTime) {
    }
    
    /**
     * 重置缓存状态（切换歌曲时调用）
     */
    private final void resetCacheState() {
    }
    
    /**
     * 预加载下一首歌曲的URL
     */
    private final void preloadNextSongUrls() {
    }
    
    /**
     * 传统播放启动方式（回退方案）
     */
    private final void fallbackPlaybackStart(java.util.List<androidx.media3.common.MediaItem> playlist, int index, long songId) {
    }
    
    /**
     * 智能预加载检查
     * 根据当前播放进度智能决定是否预加载下一首歌曲
     */
    private final void checkSmartPreload() {
    }
    
    /**
     * 清理资源，停止缓存监听
     */
    public final void cleanup() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlin.coroutines.CoroutineContext getCoroutineContext() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lme/wcy/music/service/PlayerControllerImpl$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}