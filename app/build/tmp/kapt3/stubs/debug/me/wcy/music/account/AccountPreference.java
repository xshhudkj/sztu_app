package me.wcy.music.account;

import top.wangchenyan.common.CommonApp;
import top.wangchenyan.common.storage.IPreferencesFile;
import top.wangchenyan.common.storage.PreferencesFile;
import me.wcy.music.account.bean.ProfileData;
import me.wcy.music.consts.PreferenceName;

/**
 * Created by wangchenyan.top on 2023/8/28.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0013\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\t\u0010\u0014\u001a\u00020\u0015H\u0096\u0001J\u001b\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00042\b\b\u0002\u0010\u0019\u001a\u00020\u0017H\u0096\u0001J\u001b\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u00042\b\b\u0002\u0010\u0019\u001a\u00020\u001bH\u0096\u0001J\u001b\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0018\u001a\u00020\u00042\b\b\u0002\u0010\u0019\u001a\u00020\u001dH\u0096\u0001J-\u0010\u001e\u001a\n\u0012\u0004\u0012\u0002H \u0018\u00010\u001f\"\u0004\b\u0000\u0010 2\u0006\u0010\u0018\u001a\u00020\u00042\f\u0010!\u001a\b\u0012\u0004\u0012\u0002H 0\"H\u0096\u0001J\u001b\u0010#\u001a\u00020$2\u0006\u0010\u0018\u001a\u00020\u00042\b\b\u0002\u0010\u0019\u001a\u00020$H\u0096\u0001J,\u0010%\u001a\u0004\u0018\u0001H \"\u0004\b\u0000\u0010 2\u0006\u0010\u0018\u001a\u00020\u00042\f\u0010!\u001a\b\u0012\u0004\u0012\u0002H 0\"H\u0096\u0001\u00a2\u0006\u0002\u0010&J\u001b\u0010\'\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00042\b\b\u0002\u0010\u0019\u001a\u00020\u0004H\u0096\u0001J\u0019\u0010(\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u0017H\u0096\u0001J\u0019\u0010*\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u001bH\u0096\u0001J\u0019\u0010+\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u001dH\u0096\u0001J\'\u0010,\u001a\u00020\u0015\"\u0004\b\u0000\u0010 2\u0006\u0010\u0018\u001a\u00020\u00042\u000e\u0010-\u001a\n\u0012\u0004\u0012\u0002H \u0018\u00010\u001fH\u0096\u0001J\u0019\u0010.\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010)\u001a\u00020$H\u0096\u0001J&\u0010/\u001a\u00020\u0015\"\u0004\b\u0000\u0010 2\u0006\u0010\u0018\u001a\u00020\u00042\b\u00100\u001a\u0004\u0018\u0001H H\u0096\u0001\u00a2\u0006\u0002\u00101J\u0019\u00102\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u0004H\u0096\u0001J\u0011\u00103\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0004H\u0096\u0001J\u0017\u00103\u001a\u00020\u00152\f\u00104\u001a\b\u0012\u0004\u0012\u00020\u00040\u001fH\u0096\u0001J\u0017\u00105\u001a\u00020\u00152\f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00040\u001fH\u0096\u0001R+\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR/\u0010\r\u001a\u0004\u0018\u00010\f2\b\u0010\u0003\u001a\u0004\u0018\u00010\f8F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\u00a8\u00067"}, d2 = {"Lme/wcy/music/account/AccountPreference;", "Ltop/wangchenyan/common/storage/IPreferencesFile;", "()V", "<set-?>", "", "cookie", "getCookie", "()Ljava/lang/String;", "setCookie", "(Ljava/lang/String;)V", "cookie$delegate", "Ltop/wangchenyan/common/storage/IPreferencesFile$StringProperty;", "Lme/wcy/music/account/bean/ProfileData;", "profile", "getProfile", "()Lme/wcy/music/account/bean/ProfileData;", "setProfile", "(Lme/wcy/music/account/bean/ProfileData;)V", "profile$delegate", "Ltop/wangchenyan/common/storage/IPreferencesFile$ObjectProperty;", "clear", "", "getBoolean", "", "key", "defValue", "getFloat", "", "getInt", "", "getList", "", "T", "clazz", "Ljava/lang/Class;", "getLong", "", "getModel", "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;", "getString", "putBoolean", "value", "putFloat", "putInt", "putList", "list", "putLong", "putModel", "t", "(Ljava/lang/String;Ljava/lang/Object;)V", "putString", "remove", "keys", "removeExcept", "exceptKeys", "app_debug"})
public final class AccountPreference implements top.wangchenyan.common.storage.IPreferencesFile {
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.StringProperty cookie$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final top.wangchenyan.common.storage.IPreferencesFile.ObjectProperty profile$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.account.AccountPreference INSTANCE = null;
    
    private AccountPreference() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCookie() {
        return null;
    }
    
    public final void setCookie(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final me.wcy.music.account.bean.ProfileData getProfile() {
        return null;
    }
    
    public final void setProfile(@org.jetbrains.annotations.Nullable()
    me.wcy.music.account.bean.ProfileData p0) {
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