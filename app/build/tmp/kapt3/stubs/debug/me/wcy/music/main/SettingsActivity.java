package me.wcy.music.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import dagger.hilt.android.AndroidEntryPoint;
import me.wcy.music.R;
import me.wcy.music.common.BaseMusicActivity;
import me.wcy.music.common.DarkModeService;
import me.wcy.music.consts.PreferenceName;
import me.wcy.music.service.PlayerController;
import me.wcy.music.storage.preference.ConfigPreferences;
import me.wcy.music.utils.MusicUtils;
import me.wcy.music.widget.CacheClearDialog;
import me.wcy.music.widget.AutoCacheCleanSettingsDialog;
import me.wcy.router.annotation.Route;
import androidx.preference.SwitchPreference;
import androidx.preference.ListPreference;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import me.wcy.music.service.AutoCacheCleanService;
import me.wcy.music.utils.SmartCacheManager;
import javax.inject.Inject;

@me.wcy.router.annotation.Route(value = "/settings")
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002:\u0001\u0013B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0012\u0010\b\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0012\u0010\t\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0012\u0010\n\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\b\u0010\u000b\u001a\u00020\u0005H\u0002J\u0012\u0010\f\u001a\u00020\u00052\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\b\u0010\u000f\u001a\u00020\u0005H\u0014J\u001c\u0010\u0010\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016\u00a8\u0006\u0014"}, d2 = {"Lme/wcy/music/main/SettingsActivity;", "Lme/wcy/music/common/BaseMusicActivity;", "Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;", "()V", "handleAutoCacheCleanIntervalChange", "", "sharedPreferences", "Landroid/content/SharedPreferences;", "handleAutoCacheCleanThresholdChange", "handleAutoCacheCleanToggle", "handleCacheLimitChange", "initAutoCacheCleanService", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onSharedPreferenceChanged", "key", "", "SettingsFragment", "app_debug"})
public final class SettingsActivity extends me.wcy.music.common.BaseMusicActivity implements android.content.SharedPreferences.OnSharedPreferenceChangeListener {
    
    public SettingsActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * 初始化自动清理缓存服务
     */
    private final void initAutoCacheCleanService() {
    }
    
    @java.lang.Override()
    public void onSharedPreferenceChanged(@org.jetbrains.annotations.Nullable()
    android.content.SharedPreferences sharedPreferences, @org.jetbrains.annotations.Nullable()
    java.lang.String key) {
    }
    
    /**
     * 处理自动清理开关变化
     */
    private final void handleAutoCacheCleanToggle(android.content.SharedPreferences sharedPreferences) {
    }
    
    /**
     * 处理自动清理间隔变化
     */
    private final void handleAutoCacheCleanIntervalChange(android.content.SharedPreferences sharedPreferences) {
    }
    
    /**
     * 处理自动清理阈值变化
     */
    private final void handleAutoCacheCleanThresholdChange(android.content.SharedPreferences sharedPreferences) {
    }
    
    /**
     * 处理缓存限制变化
     */
    private final void handleCacheLimitChange(android.content.SharedPreferences sharedPreferences) {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @dagger.hilt.android.AndroidEntryPoint()
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J \u00100\u001a\u0002012\u0006\u00102\u001a\u0002012\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u000204H\u0002J\b\u00106\u001a\u000207H\u0002J\b\u00108\u001a\u000207H\u0002J\b\u00109\u001a\u000207H\u0002J\b\u0010:\u001a\u000207H\u0002J\b\u0010;\u001a\u000207H\u0002J\b\u0010<\u001a\u000207H\u0002J\b\u0010=\u001a\u000207H\u0002J\b\u0010>\u001a\u000207H\u0002J\b\u0010?\u001a\u000207H\u0002J\b\u0010@\u001a\u000207H\u0002J\u001c\u0010A\u001a\u0002072\b\u0010B\u001a\u0004\u0018\u00010C2\b\u0010D\u001a\u0004\u0018\u000101H\u0016J\b\u0010E\u001a\u000207H\u0002J\b\u0010F\u001a\u000207H\u0002J\b\u0010G\u001a\u000207H\u0002J\b\u0010H\u001a\u000207H\u0002J\b\u0010I\u001a\u000207H\u0002J\b\u0010J\u001a\u000207H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\n\u0010\u0006R\u001b\u0010\f\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\b\u001a\u0004\b\r\u0010\u0006R\u001b\u0010\u000f\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\b\u001a\u0004\b\u0010\u0010\u0006R\u001b\u0010\u0012\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0014\u0010\b\u001a\u0004\b\u0013\u0010\u0006R\u001e\u0010\u0015\u001a\u00020\u00168\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001b\u0010\u001b\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001d\u0010\b\u001a\u0004\b\u001c\u0010\u0006R\u001b\u0010\u001e\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b \u0010\b\u001a\u0004\b\u001f\u0010\u0006R\u001b\u0010!\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b#\u0010\b\u001a\u0004\b\"\u0010\u0006R\u001b\u0010$\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b&\u0010\b\u001a\u0004\b%\u0010\u0006R\u001e\u0010\'\u001a\u00020(8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001b\u0010-\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b/\u0010\b\u001a\u0004\b.\u0010\u0006\u00a8\u0006K"}, d2 = {"Lme/wcy/music/main/SettingsActivity$SettingsFragment;", "Landroidx/preference/PreferenceFragmentCompat;", "()V", "autoCacheClean", "Landroidx/preference/Preference;", "getAutoCacheClean", "()Landroidx/preference/Preference;", "autoCacheClean$delegate", "Lkotlin/Lazy;", "autoPlayOnStartup", "getAutoPlayOnStartup", "autoPlayOnStartup$delegate", "cacheClear", "getCacheClear", "cacheClear$delegate", "cacheLimit", "getCacheLimit", "cacheLimit$delegate", "darkMode", "getDarkMode", "darkMode$delegate", "darkModeService", "Lme/wcy/music/common/DarkModeService;", "getDarkModeService", "()Lme/wcy/music/common/DarkModeService;", "setDarkModeService", "(Lme/wcy/music/common/DarkModeService;)V", "downloadSoundQuality", "getDownloadSoundQuality", "downloadSoundQuality$delegate", "filterSize", "getFilterSize", "filterSize$delegate", "filterTime", "getFilterTime", "filterTime$delegate", "playSoundQuality", "getPlaySoundQuality", "playSoundQuality$delegate", "playerController", "Lme/wcy/music/service/PlayerController;", "getPlayerController", "()Lme/wcy/music/service/PlayerController;", "setPlayerController", "(Lme/wcy/music/service/PlayerController;)V", "soundEffect", "getSoundEffect", "soundEffect$delegate", "getSummary", "", "value", "entryArray", "", "entryValueArray", "initAutoCacheClean", "", "initAutoPlayOnStartup", "initCacheClear", "initCacheLimit", "initCacheManagement", "initDarkMode", "initDownloadSoundQuality", "initFilter", "initPlaySoundQuality", "initSoundEffect", "onCreatePreferences", "savedInstanceState", "Landroid/os/Bundle;", "rootKey", "showAutoCacheCleanDialog", "showCacheManagementDialog", "startEqualizer", "updateAutoCacheCleanSummary", "updateAutoPlayOnStartupUI", "updateCacheLimitSummary", "app_debug"})
    public static final class SettingsFragment extends androidx.preference.PreferenceFragmentCompat {
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy darkMode$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy playSoundQuality$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy soundEffect$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy autoPlayOnStartup$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy downloadSoundQuality$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy filterSize$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy filterTime$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy cacheClear$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy cacheLimit$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy autoCacheClean$delegate = null;
        @javax.inject.Inject()
        public me.wcy.music.service.PlayerController playerController;
        @javax.inject.Inject()
        public me.wcy.music.common.DarkModeService darkModeService;
        
        public SettingsFragment() {
            super();
        }
        
        private final androidx.preference.Preference getDarkMode() {
            return null;
        }
        
        private final androidx.preference.Preference getPlaySoundQuality() {
            return null;
        }
        
        private final androidx.preference.Preference getSoundEffect() {
            return null;
        }
        
        private final androidx.preference.Preference getAutoPlayOnStartup() {
            return null;
        }
        
        private final androidx.preference.Preference getDownloadSoundQuality() {
            return null;
        }
        
        private final androidx.preference.Preference getFilterSize() {
            return null;
        }
        
        private final androidx.preference.Preference getFilterTime() {
            return null;
        }
        
        private final androidx.preference.Preference getCacheClear() {
            return null;
        }
        
        private final androidx.preference.Preference getCacheLimit() {
            return null;
        }
        
        private final androidx.preference.Preference getAutoCacheClean() {
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
        public final me.wcy.music.common.DarkModeService getDarkModeService() {
            return null;
        }
        
        public final void setDarkModeService(@org.jetbrains.annotations.NotNull()
        me.wcy.music.common.DarkModeService p0) {
        }
        
        @java.lang.Override()
        public void onCreatePreferences(@org.jetbrains.annotations.Nullable()
        android.os.Bundle savedInstanceState, @org.jetbrains.annotations.Nullable()
        java.lang.String rootKey) {
        }
        
        private final void initDarkMode() {
        }
        
        private final void initPlaySoundQuality() {
        }
        
        private final void initSoundEffect() {
        }
        
        /**
         * 初始化应用启动时自动播放音乐设置
         */
        private final void initAutoPlayOnStartup() {
        }
        
        /**
         * 更新自动播放设置的UI显示
         */
        private final void updateAutoPlayOnStartupUI() {
        }
        
        private final void initDownloadSoundQuality() {
        }
        
        private final void initFilter() {
        }
        
        /**
         * 初始化缓存管理功能 - 包含三行完整功能
         */
        private final void initCacheManagement() {
        }
        
        /**
         * 初始化缓存清理功能
         */
        private final void initCacheClear() {
        }
        
        /**
         * 初始化缓存限制功能
         */
        private final void initCacheLimit() {
        }
        
        /**
         * 更新缓存限制的摘要显示
         */
        private final void updateCacheLimitSummary() {
        }
        
        /**
         * 初始化定期自动清理功能
         */
        private final void initAutoCacheClean() {
        }
        
        /**
         * 更新自动清理的摘要显示
         * 修正：支持应用退出时清理的显示
         */
        private final void updateAutoCacheCleanSummary() {
        }
        
        /**
         * 显示自动清理设置对话框
         */
        private final void showAutoCacheCleanDialog() {
        }
        
        /**
         * 显示缓存清理对话框
         */
        private final void showCacheManagementDialog() {
        }
        
        private final void startEqualizer() {
        }
        
        private final java.lang.String getSummary(java.lang.String value, int entryArray, int entryValueArray) {
            return null;
        }
    }
}