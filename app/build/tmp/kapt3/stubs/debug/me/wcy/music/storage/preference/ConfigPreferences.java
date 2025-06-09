package me.wcy.music.storage.preference;

import com.blankj.utilcode.util.StringUtils;
import me.wcy.music.R;
import me.wcy.music.common.DarkModeService;
import me.wcy.music.consts.PreferenceName;
import top.wangchenyan.common.CommonApp;
import top.wangchenyan.common.storage.IPreferencesFile;
import top.wangchenyan.common.storage.PreferencesFile;

/**
 * SharedPreferences工具类
 * Created by wcy on 2015/11/28.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b9\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b(\n\u0002\u0010\u0006\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\t\u0010H\u001a\u00020IH\u0096\u0001J\u0006\u0010J\u001a\u00020\rJ\u0006\u0010K\u001a\u00020\rJ\u0006\u0010L\u001a\u00020\u000fJ\u0006\u0010M\u001a\u00020\u000fJ\u001b\u0010N\u001a\u00020\n2\u0006\u0010O\u001a\u00020\u00042\b\b\u0002\u0010P\u001a\u00020\nH\u0096\u0001J\u0006\u0010Q\u001a\u00020\rJ\u001b\u0010R\u001a\u00020S2\u0006\u0010O\u001a\u00020\u00042\b\b\u0002\u0010P\u001a\u00020SH\u0096\u0001J\u001b\u0010T\u001a\u00020\u000f2\u0006\u0010O\u001a\u00020\u00042\b\b\u0002\u0010P\u001a\u00020\u000fH\u0096\u0001J\u0006\u0010U\u001a\u00020\rJ-\u0010V\u001a\n\u0012\u0004\u0012\u0002HX\u0018\u00010W\"\u0004\b\u0000\u0010X2\u0006\u0010O\u001a\u00020\u00042\f\u0010Y\u001a\b\u0012\u0004\u0012\u0002HX0ZH\u0096\u0001J\u001b\u0010[\u001a\u00020\r2\u0006\u0010O\u001a\u00020\u00042\b\b\u0002\u0010P\u001a\u00020\rH\u0096\u0001J,\u0010\\\u001a\u0004\u0018\u0001HX\"\u0004\b\u0000\u0010X2\u0006\u0010O\u001a\u00020\u00042\f\u0010Y\u001a\b\u0012\u0004\u0012\u0002HX0ZH\u0096\u0001\u00a2\u0006\u0002\u0010]J\u0006\u0010^\u001a\u00020\rJ\u001b\u0010_\u001a\u00020\u00042\u0006\u0010O\u001a\u00020\u00042\b\b\u0002\u0010P\u001a\u00020\u0004H\u0096\u0001J\u0006\u0010`\u001a\u00020\nJ\u0006\u0010a\u001a\u00020\nJ\u0016\u0010b\u001a\u00020\n2\u0006\u0010c\u001a\u00020\r2\u0006\u0010d\u001a\u00020\rJ\u0019\u0010e\u001a\u00020I2\u0006\u0010O\u001a\u00020\u00042\u0006\u0010f\u001a\u00020\nH\u0096\u0001J\u0019\u0010g\u001a\u00020I2\u0006\u0010O\u001a\u00020\u00042\u0006\u0010f\u001a\u00020SH\u0096\u0001J\u0019\u0010h\u001a\u00020I2\u0006\u0010O\u001a\u00020\u00042\u0006\u0010f\u001a\u00020\u000fH\u0096\u0001J\'\u0010i\u001a\u00020I\"\u0004\b\u0000\u0010X2\u0006\u0010O\u001a\u00020\u00042\u000e\u0010j\u001a\n\u0012\u0004\u0012\u0002HX\u0018\u00010WH\u0096\u0001J\u0019\u0010k\u001a\u00020I2\u0006\u0010O\u001a\u00020\u00042\u0006\u0010f\u001a\u00020\rH\u0096\u0001J&\u0010l\u001a\u00020I\"\u0004\b\u0000\u0010X2\u0006\u0010O\u001a\u00020\u00042\b\u0010m\u001a\u0004\u0018\u0001HXH\u0096\u0001\u00a2\u0006\u0002\u0010nJ\u0019\u0010o\u001a\u00020I2\u0006\u0010O\u001a\u00020\u00042\u0006\u0010f\u001a\u00020\u0004H\u0096\u0001J\u0011\u0010p\u001a\u00020I2\u0006\u0010O\u001a\u00020\u0004H\u0096\u0001J\u0017\u0010p\u001a\u00020I2\f\u0010q\u001a\b\u0012\u0004\u0012\u00020\u00040WH\u0096\u0001J\u0017\u0010r\u001a\u00020I2\f\u0010s\u001a\b\u0012\u0004\u0012\u00020\u00040WH\u0096\u0001J\u000e\u0010t\u001a\u00020I2\u0006\u0010u\u001a\u00020\nJ\u000e\u0010v\u001a\u00020I2\u0006\u0010u\u001a\u00020\nJ\u000e\u0010w\u001a\u00020I2\u0006\u0010x\u001a\u00020\rJ\u000e\u0010y\u001a\u00020I2\u0006\u0010z\u001a\u00020\u000fJ\u000e\u0010{\u001a\u00020I2\u0006\u0010|\u001a\u00020\u000fJ\u000e\u0010}\u001a\u00020I2\u0006\u0010~\u001a\u00020\rJ\u000f\u0010\u007f\u001a\u00020I2\u0007\u0010\u0080\u0001\u001a\u00020\rJ\u0011\u0010\u0081\u0001\u001a\u00020\n2\b\u0010\u0082\u0001\u001a\u00030\u0083\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R+\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R+\u0010\u001a\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\n8F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR+\u0010!\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b$\u0010\u0019\u001a\u0004\b\"\u0010\u0015\"\u0004\b#\u0010\u0017R+\u0010%\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b(\u0010\u0019\u001a\u0004\b&\u0010\u0015\"\u0004\b\'\u0010\u0017R+\u0010)\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b,\u0010\u0019\u001a\u0004\b*\u0010\u0015\"\u0004\b+\u0010\u0017R+\u0010-\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b0\u0010\u0019\u001a\u0004\b.\u0010\u0015\"\u0004\b/\u0010\u0017R+\u00101\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b4\u0010\u0019\u001a\u0004\b2\u0010\u0015\"\u0004\b3\u0010\u0017R+\u00105\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b8\u0010\u0019\u001a\u0004\b6\u0010\u0015\"\u0004\b7\u0010\u0017R+\u00109\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000f8F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b>\u0010?\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R+\u0010@\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\bC\u0010\u0019\u001a\u0004\bA\u0010\u0015\"\u0004\bB\u0010\u0017R+\u0010D\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\n8F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\bG\u0010 \u001a\u0004\bE\u0010\u001c\"\u0004\bF\u0010\u001e\u00a8\u0006\u0084\u0001"}, d2 = {"Lme/wcy/music/storage/preference/ConfigPreferences;", "Ltop/wangchenyan/common/storage/IPreferencesFile;", "()V", "APP_EXIT_AUTO_CACHE_CLEAN_ENABLED_KEY", "", "AUTO_CACHE_CLEAN_ENABLED_KEY", "AUTO_CACHE_CLEAN_INTERVAL_KEY", "AUTO_CACHE_CLEAN_THRESHOLD_KEY", "CACHE_LIMIT_KEY", "DEFAULT_APP_EXIT_AUTO_CLEAN_ENABLED", "", "DEFAULT_AUTO_CLEAN_ENABLED", "DEFAULT_AUTO_CLEAN_INTERVAL", "", "DEFAULT_AUTO_CLEAN_THRESHOLD", "", "DEFAULT_CACHE_LIMIT", "LAST_AUTO_CLEAN_TIME_KEY", "<set-?>", "apiDomain", "getApiDomain", "()Ljava/lang/String;", "setApiDomain", "(Ljava/lang/String;)V", "apiDomain$delegate", "Ltop/wangchenyan/common/storage/IPreferencesFile$StringProperty;", "autoPlayOnStartup", "getAutoPlayOnStartup", "()Z", "setAutoPlayOnStartup", "(Z)V", "autoPlayOnStartup$delegate", "Ltop/wangchenyan/common/storage/IPreferencesFile$BooleanProperty;", "cacheLimit", "getCacheLimit", "setCacheLimit", "cacheLimit$delegate", "currentSongId", "getCurrentSongId", "setCurrentSongId", "currentSongId$delegate", "darkMode", "getDarkMode", "setDarkMode", "darkMode$delegate", "downloadSoundQuality", "getDownloadSoundQuality", "setDownloadSoundQuality", "downloadSoundQuality$delegate", "filterSize", "getFilterSize", "setFilterSize", "filterSize$delegate", "filterTime", "getFilterTime", "setFilterTime", "filterTime$delegate", "playMode", "getPlayMode", "()I", "setPlayMode", "(I)V", "playMode$delegate", "Ltop/wangchenyan/common/storage/IPreferencesFile$IntProperty;", "playSoundQuality", "getPlaySoundQuality", "setPlaySoundQuality", "playSoundQuality$delegate", "vipDialogShown", "getVipDialogShown", "setVipDialogShown", "vipDialogShown$delegate", "clear", "", "getAudioCacheLimitBytes", "getAutoCacheCleanInterval", "getAutoCacheCleanIntervalHours", "getAutoCacheCleanThreshold", "getBoolean", "key", "defValue", "getCacheLimitBytes", "getFloat", "", "getInt", "getLastAutoCacheCleanTime", "getList", "", "T", "clazz", "Ljava/lang/Class;", "getLong", "getModel", "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;", "getOtherCacheLimitBytes", "getString", "isAppExitAutoCacheCleanEnabled", "isAutoCacheCleanEnabled", "isCacheOverLimit", "currentAudioCacheSize", "currentOtherCacheSize", "putBoolean", "value", "putFloat", "putInt", "putList", "list", "putLong", "putModel", "t", "(Ljava/lang/String;Ljava/lang/Object;)V", "putString", "remove", "keys", "removeExcept", "exceptKeys", "setAppExitAutoCacheCleanEnabled", "enabled", "setAutoCacheCleanEnabled", "setAutoCacheCleanInterval", "intervalMs", "setAutoCacheCleanIntervalHours", "hours", "setAutoCacheCleanThreshold", "threshold", "setCacheLimitBytes", "limitBytes", "setLastAutoCacheCleanTime", "time", "shouldPerformAutoClean", "currentUsagePercentage", "", "app_debug"})
public final class ConfigPreferences implements top.wangchenyan.common.storage.IPreferencesFile {
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.StringProperty playSoundQuality$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.StringProperty downloadSoundQuality$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.StringProperty filterSize$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.StringProperty filterTime$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.StringProperty darkMode$delegate = null;
    
    /**
     * 缓存限制设置（字节数）
     * 0表示无限制，其他值为具体的字节限制
     */
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.StringProperty cacheLimit$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.IntProperty playMode$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.StringProperty currentSongId$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.StringProperty apiDomain$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.BooleanProperty vipDialogShown$delegate = null;
    
    /**
     * 应用启动时自动播放音乐设置
     * true: 启动时自动播放, false: 不自动播放
     */
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.BooleanProperty autoPlayOnStartup$delegate = null;
    private static final long DEFAULT_CACHE_LIMIT = -1L;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CACHE_LIMIT_KEY = "cache_limit";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String AUTO_CACHE_CLEAN_ENABLED_KEY = "auto_cache_clean_enabled";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String AUTO_CACHE_CLEAN_INTERVAL_KEY = "auto_cache_clean_interval";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String AUTO_CACHE_CLEAN_THRESHOLD_KEY = "auto_cache_clean_threshold";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String LAST_AUTO_CLEAN_TIME_KEY = "last_auto_clean_time";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String APP_EXIT_AUTO_CACHE_CLEAN_ENABLED_KEY = "app_exit_auto_cache_clean_enabled";
    private static final boolean DEFAULT_AUTO_CLEAN_ENABLED = false;
    private static final long DEFAULT_AUTO_CLEAN_INTERVAL = 604800000L;
    private static final int DEFAULT_AUTO_CLEAN_THRESHOLD = 80;
    private static final boolean DEFAULT_APP_EXIT_AUTO_CLEAN_ENABLED = false;
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.storage.preference.ConfigPreferences INSTANCE = null;
    
    private ConfigPreferences() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPlaySoundQuality() {
        return null;
    }
    
    public final void setPlaySoundQuality(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDownloadSoundQuality() {
        return null;
    }
    
    public final void setDownloadSoundQuality(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFilterSize() {
        return null;
    }
    
    public final void setFilterSize(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFilterTime() {
        return null;
    }
    
    public final void setFilterTime(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDarkMode() {
        return null;
    }
    
    public final void setDarkMode(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    /**
     * 缓存限制设置（字节数）
     * 0表示无限制，其他值为具体的字节限制
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCacheLimit() {
        return null;
    }
    
    /**
     * 缓存限制设置（字节数）
     * 0表示无限制，其他值为具体的字节限制
     */
    public final void setCacheLimit(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final int getPlayMode() {
        return 0;
    }
    
    public final void setPlayMode(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrentSongId() {
        return null;
    }
    
    public final void setCurrentSongId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getApiDomain() {
        return null;
    }
    
    public final void setApiDomain(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final boolean getVipDialogShown() {
        return false;
    }
    
    public final void setVipDialogShown(boolean p0) {
    }
    
    /**
     * 应用启动时自动播放音乐设置
     * true: 启动时自动播放, false: 不自动播放
     */
    public final boolean getAutoPlayOnStartup() {
        return false;
    }
    
    /**
     * 应用启动时自动播放音乐设置
     * true: 启动时自动播放, false: 不自动播放
     */
    public final void setAutoPlayOnStartup(boolean p0) {
    }
    
    /**
     * 获取缓存限制大小（字节）
     * @return 缓存限制大小，-1表示无限制
     */
    public final long getCacheLimitBytes() {
        return 0L;
    }
    
    /**
     * 设置缓存限制大小
     */
    public final void setCacheLimitBytes(long limitBytes) {
    }
    
    /**
     * 获取音频缓存限制（总缓存限制的分配部分）
     * 音频缓存占总缓存的70%，其他缓存占30%
     */
    public final long getAudioCacheLimitBytes() {
        return 0L;
    }
    
    /**
     * 获取其他缓存限制（图片、临时文件等）
     * 其他缓存占总缓存的30%
     */
    public final long getOtherCacheLimitBytes() {
        return 0L;
    }
    
    /**
     * 检查当前缓存使用是否超出限制
     */
    public final boolean isCacheOverLimit(long currentAudioCacheSize, long currentOtherCacheSize) {
        return false;
    }
    
    /**
     * 是否启用自动清理缓存
     */
    public final boolean isAutoCacheCleanEnabled() {
        return false;
    }
    
    /**
     * 设置自动清理缓存开关
     */
    public final void setAutoCacheCleanEnabled(boolean enabled) {
    }
    
    /**
     * 获取自动清理间隔（毫秒）
     */
    public final long getAutoCacheCleanInterval() {
        return 0L;
    }
    
    /**
     * 设置自动清理间隔
     */
    public final void setAutoCacheCleanInterval(long intervalMs) {
    }
    
    /**
     * 获取自动清理间隔（小时）
     */
    public final int getAutoCacheCleanIntervalHours() {
        return 0;
    }
    
    /**
     * 设置自动清理间隔（小时）
     */
    public final void setAutoCacheCleanIntervalHours(int hours) {
    }
    
    /**
     * 获取自动清理阈值（百分比）
     */
    public final int getAutoCacheCleanThreshold() {
        return 0;
    }
    
    /**
     * 设置自动清理阈值
     */
    public final void setAutoCacheCleanThreshold(int threshold) {
    }
    
    /**
     * 获取上次自动清理时间
     */
    public final long getLastAutoCacheCleanTime() {
        return 0L;
    }
    
    /**
     * 设置上次自动清理时间
     */
    public final void setLastAutoCacheCleanTime(long time) {
    }
    
    /**
     * 是否需要执行自动清理
     */
    public final boolean shouldPerformAutoClean(double currentUsagePercentage) {
        return false;
    }
    
    /**
     * 是否启用应用退出时自动清理缓存
     */
    public final boolean isAppExitAutoCacheCleanEnabled() {
        return false;
    }
    
    /**
     * 设置应用退出时自动清理缓存开关
     */
    public final void setAppExitAutoCacheCleanEnabled(boolean enabled) {
    }
    
    @java.lang.Override()
    public void clear() {
    }
    
    @java.lang.Override()
    public boolean getBoolean(@org.jetbrains.annotations.NotNull()
    java.lang.String key, boolean defValue) {
        return false;
    }
    
    @java.lang.Override()
    public float getFloat(@org.jetbrains.annotations.NotNull()
    java.lang.String key, float defValue) {
        return 0.0F;
    }
    
    @java.lang.Override()
    public int getInt(@org.jetbrains.annotations.NotNull()
    java.lang.String key, int defValue) {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public <T extends java.lang.Object>java.util.List<T> getList(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    java.lang.Class<T> clazz) {
        return null;
    }
    
    @java.lang.Override()
    public long getLong(@org.jetbrains.annotations.NotNull()
    java.lang.String key, long defValue) {
        return 0L;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public <T extends java.lang.Object>T getModel(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    java.lang.Class<T> clazz) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String getString(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    java.lang.String defValue) {
        return null;
    }
    
    @java.lang.Override()
    public void putBoolean(@org.jetbrains.annotations.NotNull()
    java.lang.String key, boolean value) {
    }
    
    @java.lang.Override()
    public void putFloat(@org.jetbrains.annotations.NotNull()
    java.lang.String key, float value) {
    }
    
    @java.lang.Override()
    public void putInt(@org.jetbrains.annotations.NotNull()
    java.lang.String key, int value) {
    }
    
    @java.lang.Override()
    public <T extends java.lang.Object>void putList(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.Nullable()
    java.util.List<? extends T> list) {
    }
    
    @java.lang.Override()
    public void putLong(@org.jetbrains.annotations.NotNull()
    java.lang.String key, long value) {
    }
    
    @java.lang.Override()
    public <T extends java.lang.Object>void putModel(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.Nullable()
    T t) {
    }
    
    @java.lang.Override()
    public void putString(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @java.lang.Override()
    public void remove(@org.jetbrains.annotations.NotNull()
    java.lang.String key) {
    }
    
    @java.lang.Override()
    public void remove(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> keys) {
    }
    
    @java.lang.Override()
    public void removeExcept(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> exceptKeys) {
    }
}