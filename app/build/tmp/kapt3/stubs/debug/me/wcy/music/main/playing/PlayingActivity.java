package me.wcy.music.main.playing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import dagger.hilt.android.AndroidEntryPoint;
import jp.wasabeef.blurry.Blurry;
import me.wcy.lrcview.LrcView;
import me.wcy.music.R;
import me.wcy.music.common.BaseMusicActivity;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.databinding.ActivityPlayingBinding;
import me.wcy.music.discover.DiscoverApi;
import me.wcy.music.main.playlist.CurrentPlaylistFragment;
import me.wcy.music.service.PlayMode;
import me.wcy.music.service.PlayState;
import me.wcy.music.service.PlayerController;
import me.wcy.music.service.likesong.LikeSongProcessor;
import me.wcy.music.storage.LrcCache;
import me.wcy.music.storage.preference.ConfigPreferences;
import me.wcy.music.utils.TimeUtils;
import me.wcy.router.annotation.Route;
import top.wangchenyan.common.utils.LaunchUtils;
import top.wangchenyan.common.utils.image.ImageUtils;
import java.io.File;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/9/4.
 */
@me.wcy.router.annotation.Route(value = "/playing")
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 P2\u00020\u0001:\u0001PB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010+\u001a\u00020,H\u0016J\b\u0010-\u001a\u00020,H\u0002J\b\u0010.\u001a\u00020,H\u0002J\b\u0010/\u001a\u00020,H\u0002J\b\u00100\u001a\u00020,H\u0002J\b\u00101\u001a\u00020,H\u0002J\b\u00102\u001a\u00020,H\u0002J\b\u00103\u001a\u00020,H\u0002J\b\u00104\u001a\u00020,H\u0002J\b\u00105\u001a\u00020,H\u0002J\u0010\u00106\u001a\u00020,2\u0006\u00107\u001a\u000208H\u0002J\b\u00109\u001a\u00020,H\u0017J\u0012\u0010:\u001a\u00020,2\b\u0010;\u001a\u0004\u0018\u00010<H\u0014J\b\u0010=\u001a\u00020,H\u0014J\b\u0010>\u001a\u00020,H\u0002J\u0010\u0010?\u001a\u00020,2\u0006\u0010@\u001a\u000208H\u0002J\u0010\u0010A\u001a\u00020,2\u0006\u0010B\u001a\u00020\u0013H\u0002J\b\u0010C\u001a\u00020,H\u0002J\u0010\u0010D\u001a\u00020,2\u0006\u0010E\u001a\u00020FH\u0002J\u0010\u0010G\u001a\u00020,2\u0006\u0010E\u001a\u00020FH\u0002J\b\u0010H\u001a\u00020,H\u0002J\u0018\u0010H\u001a\u00020,2\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020\u0013H\u0002J\u0010\u0010L\u001a\u00020,2\u0006\u0010E\u001a\u00020FH\u0002J\u0010\u0010M\u001a\u00020,2\u0006\u0010N\u001a\u00020OH\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R#\u0010\t\u001a\n \u000b*\u0004\u0018\u00010\n0\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\b\u001a\u0004\b\f\u0010\rR#\u0010\u000f\u001a\n \u000b*\u0004\u0018\u00010\n0\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\b\u001a\u0004\b\u0010\u0010\rR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0016\u001a\u00020\u00178\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u001e\u001a\u00020\u001f8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001b\u0010$\u001a\u00020%8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b(\u0010\b\u001a\u0004\b&\u0010\'R\u000e\u0010)\u001a\u00020*X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006Q"}, d2 = {"Lme/wcy/music/main/playing/PlayingActivity;", "Lme/wcy/music/common/BaseMusicActivity;", "()V", "audioManager", "Landroid/media/AudioManager;", "getAudioManager", "()Landroid/media/AudioManager;", "audioManager$delegate", "Lkotlin/Lazy;", "defaultBgBitmap", "Landroid/graphics/Bitmap;", "kotlin.jvm.PlatformType", "getDefaultBgBitmap", "()Landroid/graphics/Bitmap;", "defaultBgBitmap$delegate", "defaultCoverBitmap", "getDefaultCoverBitmap", "defaultCoverBitmap$delegate", "isDraggingProgress", "", "lastProgress", "", "likeSongProcessor", "Lme/wcy/music/service/likesong/LikeSongProcessor;", "getLikeSongProcessor", "()Lme/wcy/music/service/likesong/LikeSongProcessor;", "setLikeSongProcessor", "(Lme/wcy/music/service/likesong/LikeSongProcessor;)V", "loadLrcJob", "Lkotlinx/coroutines/Job;", "playerController", "Lme/wcy/music/service/PlayerController;", "getPlayerController", "()Lme/wcy/music/service/PlayerController;", "setPlayerController", "(Lme/wcy/music/service/PlayerController;)V", "viewBinding", "Lme/wcy/music/databinding/ActivityPlayingBinding;", "getViewBinding", "()Lme/wcy/music/databinding/ActivityPlayingBinding;", "viewBinding$delegate", "volumeReceiver", "Landroid/content/BroadcastReceiver;", "finish", "", "finishWithAnimation", "initActions", "initCover", "initData", "initLrc", "initPlayControl", "initTitle", "initVolume", "initWindowInsets", "loadLrc", "path", "", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "setDefaultCover", "setLrcLabel", "label", "switchCoverLrc", "showCover", "switchPlayMode", "updateCover", "song", "Landroidx/media3/common/MediaItem;", "updateLrc", "updateLrcMask", "maskView", "Landroid/widget/ImageView;", "topToBottom", "updateOnlineActionsState", "updatePlayState", "playState", "Lme/wcy/music/service/PlayState;", "Companion", "app_debug"})
public final class PlayingActivity extends me.wcy.music.common.BaseMusicActivity {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewBinding$delegate = null;
    @javax.inject.Inject()
    public me.wcy.music.service.PlayerController playerController;
    @javax.inject.Inject()
    public me.wcy.music.service.likesong.LikeSongProcessor likeSongProcessor;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy audioManager$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy defaultCoverBitmap$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy defaultBgBitmap$delegate = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job loadLrcJob;
    private int lastProgress = 0;
    private boolean isDraggingProgress = false;
    @org.jetbrains.annotations.NotNull()
    private final android.content.BroadcastReceiver volumeReceiver = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "PlayingActivity";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.main.playing.PlayingActivity.Companion Companion = null;
    
    public PlayingActivity() {
        super();
    }
    
    private final me.wcy.music.databinding.ActivityPlayingBinding getViewBinding() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.service.PlayerController getPlayerController() {
        return null;
    }
    
    public final void setPlayerController(@org.jetbrains.annotations.NotNull()
    me.wcy.music.service.PlayerController p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.service.likesong.LikeSongProcessor getLikeSongProcessor() {
        return null;
    }
    
    public final void setLikeSongProcessor(@org.jetbrains.annotations.NotNull()
    me.wcy.music.service.likesong.LikeSongProcessor p0) {
    }
    
    private final android.media.AudioManager getAudioManager() {
        return null;
    }
    
    private final android.graphics.Bitmap getDefaultCoverBitmap() {
        return null;
    }
    
    private final android.graphics.Bitmap getDefaultBgBitmap() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initWindowInsets() {
    }
    
    private final void initTitle() {
    }
    
    private final void finishWithAnimation() {
    }
    
    @java.lang.Override()
    @java.lang.Deprecated()
    public void onBackPressed() {
    }
    
    @java.lang.Override()
    public void finish() {
    }
    
    private final void initVolume() {
    }
    
    private final void initCover() {
    }
    
    private final void initLrc() {
    }
    
    private final void initActions() {
    }
    
    private final void initPlayControl() {
    }
    
    private final void initData() {
    }
    
    private final void updateCover(androidx.media3.common.MediaItem song) {
    }
    
    private final void setDefaultCover() {
    }
    
    private final void updateLrcMask() {
    }
    
    private final void updateLrcMask(android.widget.ImageView maskView, boolean topToBottom) {
    }
    
    private final void updateLrc(androidx.media3.common.MediaItem song) {
    }
    
    private final void loadLrc(java.lang.String path) {
    }
    
    private final void setLrcLabel(java.lang.String label) {
    }
    
    private final void switchCoverLrc(boolean showCover) {
    }
    
    private final void switchPlayMode() {
    }
    
    private final void updatePlayState(me.wcy.music.service.PlayState playState) {
    }
    
    private final void updateOnlineActionsState(androidx.media3.common.MediaItem song) {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lme/wcy/music/main/playing/PlayingActivity$Companion;", "", "()V", "TAG", "", "VOLUME_CHANGED_ACTION", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}