package me.wcy.music.search;

import top.wangchenyan.common.CommonApp;
import top.wangchenyan.common.storage.IPreferencesFile;
import top.wangchenyan.common.storage.PreferencesFile;
import me.wcy.music.consts.PreferenceName;

/**
 * Created by wangchenyan.top on 2023/9/21.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0013\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\t\u0010\r\u001a\u00020\u000eH\u0096\u0001J\u001b\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\u0010H\u0096\u0001J\u001b\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\u0014H\u0096\u0001J\u001b\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\u0016H\u0096\u0001J-\u0010\u0017\u001a\n\u0012\u0004\u0012\u0002H\u0018\u0018\u00010\u0004\"\u0004\b\u0000\u0010\u00182\u0006\u0010\u0011\u001a\u00020\u00052\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001aH\u0096\u0001J\u001b\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\u001cH\u0096\u0001J,\u0010\u001d\u001a\u0004\u0018\u0001H\u0018\"\u0004\b\u0000\u0010\u00182\u0006\u0010\u0011\u001a\u00020\u00052\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001aH\u0096\u0001\u00a2\u0006\u0002\u0010\u001eJ\u001b\u0010\u001f\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\u0005H\u0096\u0001J\u0019\u0010 \u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u0010H\u0096\u0001J\u0019\u0010\"\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u0014H\u0096\u0001J\u0019\u0010#\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u0016H\u0096\u0001J\'\u0010$\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00182\u0006\u0010\u0011\u001a\u00020\u00052\u000e\u0010%\u001a\n\u0012\u0004\u0012\u0002H\u0018\u0018\u00010\u0004H\u0096\u0001J\u0019\u0010&\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u001cH\u0096\u0001J&\u0010\'\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00182\u0006\u0010\u0011\u001a\u00020\u00052\b\u0010(\u001a\u0004\u0018\u0001H\u0018H\u0096\u0001\u00a2\u0006\u0002\u0010)J\u0019\u0010*\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u0005H\u0096\u0001J\u0011\u0010+\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0005H\u0096\u0001J\u0017\u0010+\u001a\u00020\u000e2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0096\u0001J\u0017\u0010-\u001a\u00020\u000e2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0096\u0001R;\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00042\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u000b\u0010\f\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u00a8\u0006/"}, d2 = {"Lme/wcy/music/search/SearchPreference;", "Ltop/wangchenyan/common/storage/IPreferencesFile;", "()V", "<set-?>", "", "", "historyKeywords", "getHistoryKeywords", "()Ljava/util/List;", "setHistoryKeywords", "(Ljava/util/List;)V", "historyKeywords$delegate", "Ltop/wangchenyan/common/storage/IPreferencesFile$ListProperty;", "clear", "", "getBoolean", "", "key", "defValue", "getFloat", "", "getInt", "", "getList", "T", "clazz", "Ljava/lang/Class;", "getLong", "", "getModel", "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;", "getString", "putBoolean", "value", "putFloat", "putInt", "putList", "list", "putLong", "putModel", "t", "(Ljava/lang/String;Ljava/lang/Object;)V", "putString", "remove", "keys", "removeExcept", "exceptKeys", "app_debug"})
public final class SearchPreference implements top.wangchenyan.common.storage.IPreferencesFile {
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.ListProperty historyKeywords$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.search.SearchPreference INSTANCE = null;
    
    private SearchPreference() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.String> getHistoryKeywords() {
        return null;
    }
    
    public final void setHistoryKeywords(@org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> p0) {
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