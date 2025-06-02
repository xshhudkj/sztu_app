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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u001b\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0013\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\t\u0010,\u001a\u00020-H\u0096\u0001J\u001b\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u00042\b\b\u0002\u00101\u001a\u00020/H\u0096\u0001J\u001b\u00102\u001a\u0002032\u0006\u00100\u001a\u00020\u00042\b\b\u0002\u00101\u001a\u000203H\u0096\u0001J\u001b\u00104\u001a\u00020 2\u0006\u00100\u001a\u00020\u00042\b\b\u0002\u00101\u001a\u00020 H\u0096\u0001J-\u00105\u001a\n\u0012\u0004\u0012\u0002H7\u0018\u000106\"\u0004\b\u0000\u001072\u0006\u00100\u001a\u00020\u00042\f\u00108\u001a\b\u0012\u0004\u0012\u0002H709H\u0096\u0001J\u001b\u0010:\u001a\u00020;2\u0006\u00100\u001a\u00020\u00042\b\b\u0002\u00101\u001a\u00020;H\u0096\u0001J,\u0010<\u001a\u0004\u0018\u0001H7\"\u0004\b\u0000\u001072\u0006\u00100\u001a\u00020\u00042\f\u00108\u001a\b\u0012\u0004\u0012\u0002H709H\u0096\u0001\u00a2\u0006\u0002\u0010=J\u001b\u0010>\u001a\u00020\u00042\u0006\u00100\u001a\u00020\u00042\b\b\u0002\u00101\u001a\u00020\u0004H\u0096\u0001J\u0019\u0010?\u001a\u00020-2\u0006\u00100\u001a\u00020\u00042\u0006\u0010@\u001a\u00020/H\u0096\u0001J\u0019\u0010A\u001a\u00020-2\u0006\u00100\u001a\u00020\u00042\u0006\u0010@\u001a\u000203H\u0096\u0001J\u0019\u0010B\u001a\u00020-2\u0006\u00100\u001a\u00020\u00042\u0006\u0010@\u001a\u00020 H\u0096\u0001J\'\u0010C\u001a\u00020-\"\u0004\b\u0000\u001072\u0006\u00100\u001a\u00020\u00042\u000e\u0010D\u001a\n\u0012\u0004\u0012\u0002H7\u0018\u000106H\u0096\u0001J\u0019\u0010E\u001a\u00020-2\u0006\u00100\u001a\u00020\u00042\u0006\u0010@\u001a\u00020;H\u0096\u0001J&\u0010F\u001a\u00020-\"\u0004\b\u0000\u001072\u0006\u00100\u001a\u00020\u00042\b\u0010G\u001a\u0004\u0018\u0001H7H\u0096\u0001\u00a2\u0006\u0002\u0010HJ\u0019\u0010I\u001a\u00020-2\u0006\u00100\u001a\u00020\u00042\u0006\u0010@\u001a\u00020\u0004H\u0096\u0001J\u0011\u0010J\u001a\u00020-2\u0006\u00100\u001a\u00020\u0004H\u0096\u0001J\u0017\u0010J\u001a\u00020-2\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u000406H\u0096\u0001J\u0017\u0010L\u001a\u00020-2\f\u0010M\u001a\b\u0012\u0004\u0012\u00020\u000406H\u0096\u0001R+\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR+\u0010\f\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u000b\u001a\u0004\b\r\u0010\u0007\"\u0004\b\u000e\u0010\tR+\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u000b\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR+\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u000b\u001a\u0004\b\u0015\u0010\u0007\"\u0004\b\u0016\u0010\tR+\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u001b\u0010\u000b\u001a\u0004\b\u0019\u0010\u0007\"\u0004\b\u001a\u0010\tR+\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u001f\u0010\u000b\u001a\u0004\b\u001d\u0010\u0007\"\u0004\b\u001e\u0010\tR+\u0010!\u001a\u00020 2\u0006\u0010\u0003\u001a\u00020 8F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b&\u0010\'\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R+\u0010(\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b+\u0010\u000b\u001a\u0004\b)\u0010\u0007\"\u0004\b*\u0010\t\u00a8\u0006N"}, d2 = {"Lme/wcy/music/storage/preference/ConfigPreferences;", "Ltop/wangchenyan/common/storage/IPreferencesFile;", "()V", "<set-?>", "", "apiDomain", "getApiDomain", "()Ljava/lang/String;", "setApiDomain", "(Ljava/lang/String;)V", "apiDomain$delegate", "Ltop/wangchenyan/common/storage/IPreferencesFile$StringProperty;", "currentSongId", "getCurrentSongId", "setCurrentSongId", "currentSongId$delegate", "darkMode", "getDarkMode", "setDarkMode", "darkMode$delegate", "downloadSoundQuality", "getDownloadSoundQuality", "setDownloadSoundQuality", "downloadSoundQuality$delegate", "filterSize", "getFilterSize", "setFilterSize", "filterSize$delegate", "filterTime", "getFilterTime", "setFilterTime", "filterTime$delegate", "", "playMode", "getPlayMode", "()I", "setPlayMode", "(I)V", "playMode$delegate", "Ltop/wangchenyan/common/storage/IPreferencesFile$IntProperty;", "playSoundQuality", "getPlaySoundQuality", "setPlaySoundQuality", "playSoundQuality$delegate", "clear", "", "getBoolean", "", "key", "defValue", "getFloat", "", "getInt", "getList", "", "T", "clazz", "Ljava/lang/Class;", "getLong", "", "getModel", "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;", "getString", "putBoolean", "value", "putFloat", "putInt", "putList", "list", "putLong", "putModel", "t", "(Ljava/lang/String;Ljava/lang/Object;)V", "putString", "remove", "keys", "removeExcept", "exceptKeys", "app_debug"})
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
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.IntProperty playMode$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.StringProperty currentSongId$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.StringProperty apiDomain$delegate = null;
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