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
import me.wcy.music.utils.VipUtils;
import me.wcy.music.widget.VipTrialDialog;
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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 o2\u00020\u0001:\u0001oB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u00106\u001a\u00020\f2\u0006\u00107\u001a\u00020\fH\u0002J\u0010\u00108\u001a\u00020\f2\u0006\u00107\u001a\u00020\fH\u0002J\u0010\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020\u001bH\u0002J\u0010\u0010<\u001a\u00020\f2\u0006\u0010=\u001a\u00020\u000eH\u0002J\b\u0010>\u001a\u00020:H\u0002J\b\u0010?\u001a\u00020:H\u0002J\b\u0010@\u001a\u00020:H\u0002J\b\u0010A\u001a\u00020:H\u0002J\b\u0010B\u001a\u00020:H\u0002J\b\u0010C\u001a\u00020:H\u0002J\b\u0010D\u001a\u00020:H\u0002J\b\u0010E\u001a\u00020:H\u0002J\b\u0010F\u001a\u00020:H\u0002J\b\u0010G\u001a\u00020:H\u0002J\b\u0010H\u001a\u00020:H\u0002J\b\u0010I\u001a\u00020\u0017H\u0002J\u0010\u0010J\u001a\u00020:2\u0006\u0010K\u001a\u00020\u000bH\u0002J\b\u0010L\u001a\u00020:H\u0016J\u0012\u0010M\u001a\u00020:2\b\u0010N\u001a\u0004\u0018\u00010OH\u0014J\b\u0010P\u001a\u00020:H\u0014J\b\u0010Q\u001a\u00020:H\u0002J\b\u0010R\u001a\u00020:H\u0002J\u0010\u0010S\u001a\u00020:2\u0006\u0010T\u001a\u00020\u000bH\u0002J\u0010\u0010U\u001a\u00020:2\u0006\u0010V\u001a\u00020\fH\u0002J\b\u0010W\u001a\u00020:H\u0002J\b\u0010X\u001a\u00020:H\u0002J\b\u0010Y\u001a\u00020:H\u0002J\u0010\u0010Z\u001a\u00020:2\u0006\u0010[\u001a\u00020\u0017H\u0002J\b\u0010\\\u001a\u00020:H\u0002J\u0010\u0010]\u001a\u00020:2\u0006\u0010^\u001a\u00020_H\u0002J\u0010\u0010`\u001a\u00020:2\u0006\u0010^\u001a\u00020_H\u0002J\u0010\u0010a\u001a\u00020:2\u0006\u0010b\u001a\u00020\fH\u0002J\u001a\u0010c\u001a\u00020:2\b\u0010=\u001a\u0004\u0018\u00010\u000e2\u0006\u0010d\u001a\u00020\u000bH\u0002J\b\u0010e\u001a\u00020:H\u0002J\u0018\u0010e\u001a\u00020:2\u0006\u0010f\u001a\u00020g2\u0006\u0010h\u001a\u00020\u0017H\u0002J\u0010\u0010i\u001a\u00020:2\u0006\u0010^\u001a\u00020_H\u0002J\u0010\u0010j\u001a\u00020:2\u0006\u0010k\u001a\u00020lH\u0002J\u0010\u0010m\u001a\u00020:2\u0006\u0010^\u001a\u00020_H\u0002J\u0010\u0010n\u001a\u00020:2\u0006\u0010^\u001a\u00020_H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010\r\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0010\u0010\u0011R#\u0010\u0013\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0015\u0010\b\u001a\u0004\b\u0014\u0010\u0011R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u001f\u001a\u00020 8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\u001bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\fX\u0082D\u00a2\u0006\u0002\n\u0000R\u001e\u0010)\u001a\u00020*8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001b\u0010/\u001a\u0002008BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b3\u0010\b\u001a\u0004\b1\u00102R\u000e\u00104\u001a\u000205X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006p"}, d2 = {"Lme/wcy/music/main/playing/PlayingActivity;", "Lme/wcy/music/common/BaseMusicActivity;", "()V", "audioManager", "Landroid/media/AudioManager;", "getAudioManager", "()Landroid/media/AudioManager;", "audioManager$delegate", "Lkotlin/Lazy;", "colorCache", "", "", "", "defaultBgBitmap", "Landroid/graphics/Bitmap;", "kotlin.jvm.PlatformType", "getDefaultBgBitmap", "()Landroid/graphics/Bitmap;", "defaultBgBitmap$delegate", "defaultCoverBitmap", "getDefaultCoverBitmap", "defaultCoverBitmap$delegate", "hasShownVipDialog", "", "isDraggingProgress", "isUpdatingUI", "lastLrcProgress", "", "lastLrcUpdateTime", "lastProgress", "lastUpdateSongId", "likeSongProcessor", "Lme/wcy/music/service/likesong/LikeSongProcessor;", "getLikeSongProcessor", "()Lme/wcy/music/service/likesong/LikeSongProcessor;", "setLikeSongProcessor", "(Lme/wcy/music/service/likesong/LikeSongProcessor;)V", "loadLrcJob", "Lkotlinx/coroutines/Job;", "lrcUpdateInterval", "maxCacheSize", "playerController", "Lme/wcy/music/service/PlayerController;", "getPlayerController", "()Lme/wcy/music/service/PlayerController;", "setPlayerController", "(Lme/wcy/music/service/PlayerController;)V", "viewBinding", "Lme/wcy/music/databinding/ActivityPlayingBinding;", "getViewBinding", "()Lme/wcy/music/databinding/ActivityPlayingBinding;", "viewBinding$delegate", "volumeReceiver", "Landroid/content/BroadcastReceiver;", "adjustColorForVisibility", "color", "adjustColorForVisibilityFast", "checkVipTrialLimit", "", "currentProgress", "extractSmartColor", "bitmap", "finishWithAnimation", "initActions", "initAutomotiveVolume", "initCover", "initData", "initLrc", "initPlayControl", "initStandardVolume", "initTitle", "initVolume", "initWindowInsets", "isAndroidAutomotive", "loadLrc", "path", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "resetProgressUI", "setDefaultCover", "setLrcLabel", "label", "setSystemVolume", "volume", "setupGradientText", "showVipTrialDialog", "startMarqueeEffect", "switchCoverLrc", "showCover", "switchPlayMode", "updateCover", "song", "Landroidx/media3/common/MediaItem;", "updateLrc", "updateLrcColors", "highlightColor", "updateLrcHighlightColor", "coverUrl", "updateLrcMask", "maskView", "Landroid/widget/ImageView;", "topToBottom", "updateOnlineActionsState", "updatePlayState", "playState", "Lme/wcy/music/service/PlayState;", "updateVipLabel", "updateVipTrialMark", "Companion", "app_debug"})
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
    private final java.util.Map<java.lang.String, java.lang.Integer> colorCache = null;
    private final int maxCacheSize = 50;
    private long lastUpdateSongId = -1L;
    private boolean isUpdatingUI = false;
    private long lastLrcUpdateTime = 0L;
    private final long lrcUpdateInterval = 100L;
    private long lastLrcProgress = -1L;
    @org.jetbrains.annotations.NotNull()
    private final android.content.BroadcastReceiver volumeReceiver = null;
    
    /**
     * 检查VIP试听限制
     * 当到达试听终点时显示VIP提示对话框
     */
    private boolean hasShownVipDialog = false;
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
    
    private final void initVolume() {
    }
    
    /**
     * 检查是否为Android Automotive平台
     */
    private final boolean isAndroidAutomotive() {
        return false;
    }
    
    /**
     * 初始化Android Automotive音量控制
     *
     * 根据Android官方文档和CSDN技术博客分析：
     * 1. Android Automotive OS使用固定音量(Fixed Volume)架构
     * 2. 音量通过硬件放大器控制，而非软件混音器
     * 3. AudioManager的STREAM_MUSIC会自动映射到AAOS的音量组和音区
     * 4. CarAudioService负责将音频路由到AUDIO_DEVICE_OUT_BUS设备
     *
     * 参考文档：
     * - https://source.android.com/docs/automotive/audio/archive
     * - https://blog.csdn.net/LJX646566715/article/details/127611501
     * - https://blog.csdn.net/tq08g2z/article/details/129329989
     * - https://developer.android.com/training/cars/apps
     */
    private final void initAutomotiveVolume() {
    }
    
    /**
     * 初始化标准Android音量控制
     */
    private final void initStandardVolume() {
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
    
    /**
     * 动态更新歌词高亮颜色
     * 从专辑封面提取主色调并应用到歌词高亮显示
     * 注意：只更新高亮颜色，默认歌词颜色保持白色
     * 只在有歌词内容时才更新颜色，确保状态文本（如"歌词加载中…"）始终为白色
     * @param bitmap 专辑封面Bitmap
     * @param coverUrl 专辑封面URL，用于缓存
     */
    private final void updateLrcHighlightColor(android.graphics.Bitmap bitmap, java.lang.String coverUrl) {
    }
    
    /**
     * 批量更新歌词颜色 - 避免重复UI操作
     */
    private final void updateLrcColors(int highlightColor) {
    }
    
    /**
     * 立即重置进度UI - 切换歌曲时使用
     */
    private final void resetProgressUI() {
    }
    
    /**
     * 高性能颜色提取方法
     * 优化版：减少采样点，提升性能，从9点减少到4点采样
     */
    private final int extractSmartColor(android.graphics.Bitmap bitmap) {
        return 0;
    }
    
    /**
     * 快速颜色调整方法 - 性能优化版本
     * 简化算法，减少计算复杂度，提升性能
     */
    private final int adjustColorForVisibilityFast(int color) {
        return 0;
    }
    
    /**
     * 调整颜色以确保在深色背景上的可见性和美观性（保留原方法作为备用）
     */
    private final int adjustColorForVisibility(int color) {
        return 0;
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
    
    /**
     * 重写返回键行为 - 禁用返回键，只能通过关闭按钮返回
     */
    @java.lang.Override()
    public void onBackPressed() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    /**
     * 设置系统音量，区分Android Automotive和普通Android
     *
     * Android Automotive OS音量控制流程：
     * 1. 应用调用AudioManager.setStreamVolume(STREAM_MUSIC, volume, flags)
     * 2. AudioFlinger接收音量设置请求
     * 3. CarAudioService拦截并处理音量设置
     * 4. 根据car_audio_configuration.xml配置映射到对应的音量组
     * 5. 音量组控制硬件放大器实现真正的音量调节
     * 6. 支持多音区和音频上下文管理
     */
    private final void setSystemVolume(int volume) {
    }
    
    /**
     * 启动跑马灯效果
     * 确保歌曲名和歌手名能够完整滚动显示
     */
    private final void startMarqueeEffect() {
    }
    
    /**
     * 更新VIP标签显示
     * 根据歌曲的fee字段显示相应的VIP标签，使用优化的渲染方法
     */
    private final void updateVipLabel(androidx.media3.common.MediaItem song) {
    }
    
    /**
     * 更新VIP试听标记
     * 在进度条上显示试听终点标记
     */
    private final void updateVipTrialMark(androidx.media3.common.MediaItem song) {
    }
    
    private final void checkVipTrialLimit(long currentProgress) {
    }
    
    /**
     * 显示VIP试听提示对话框
     */
    private final void showVipTrialDialog() {
    }
    
    /**
     * 为歌曲信息设置渐变文字效果
     * 歌曲标题：白色到浅蓝色的渐变
     * 艺术家名称：同上
     */
    private final void setupGradientText() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lme/wcy/music/main/playing/PlayingActivity$Companion;", "", "()V", "TAG", "", "VOLUME_CHANGED_ACTION", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}