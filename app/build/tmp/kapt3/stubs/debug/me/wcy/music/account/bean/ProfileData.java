package me.wcy.music.account.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangchenyan.top on 2023/8/28.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\bT\b\u0086\b\u0018\u00002\u00020\u0001B\u00bb\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\f\u001a\u00020\u0007\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0019\u0012\b\b\u0002\u0010 \u001a\u00020\u0003\u0012\b\b\u0002\u0010!\u001a\u00020\u0007\u0012\b\b\u0002\u0010\"\u001a\u00020\u0003\u0012\b\b\u0002\u0010#\u001a\u00020\u0005\u0012\b\b\u0002\u0010$\u001a\u00020\u0019\u00a2\u0006\u0002\u0010%J\t\u0010I\u001a\u00020\u0003H\u00c6\u0003J\t\u0010J\u001a\u00020\u0007H\u00c6\u0003J\t\u0010K\u001a\u00020\u0005H\u00c6\u0003J\t\u0010L\u001a\u00020\u0007H\u00c6\u0003J\t\u0010M\u001a\u00020\u0003H\u00c6\u0003J\t\u0010N\u001a\u00020\u0005H\u00c6\u0003J\t\u0010O\u001a\u00020\u0005H\u00c6\u0003J\t\u0010P\u001a\u00020\u0005H\u00c6\u0003J\t\u0010Q\u001a\u00020\u0005H\u00c6\u0003J\t\u0010R\u001a\u00020\u0005H\u00c6\u0003J\t\u0010S\u001a\u00020\u0005H\u00c6\u0003J\t\u0010T\u001a\u00020\u0005H\u00c6\u0003J\t\u0010U\u001a\u00020\u0019H\u00c6\u0003J\t\u0010V\u001a\u00020\u0005H\u00c6\u0003J\t\u0010W\u001a\u00020\u0005H\u00c6\u0003J\t\u0010X\u001a\u00020\u0005H\u00c6\u0003J\t\u0010Y\u001a\u00020\u0019H\u00c6\u0003J\t\u0010Z\u001a\u00020\u0019H\u00c6\u0003J\t\u0010[\u001a\u00020\u0019H\u00c6\u0003J\t\u0010\\\u001a\u00020\u0003H\u00c6\u0003J\t\u0010]\u001a\u00020\u0007H\u00c6\u0003J\t\u0010^\u001a\u00020\u0003H\u00c6\u0003J\t\u0010_\u001a\u00020\u0007H\u00c6\u0003J\t\u0010`\u001a\u00020\u0005H\u00c6\u0003J\t\u0010a\u001a\u00020\u0019H\u00c6\u0003J\t\u0010b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010c\u001a\u00020\u0007H\u00c6\u0003J\t\u0010d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010e\u001a\u00020\u0007H\u00c6\u0003J\t\u0010f\u001a\u00020\u0007H\u00c6\u0003J\t\u0010g\u001a\u00020\u0003H\u00c6\u0003J\u00bf\u0002\u0010h\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00072\b\b\u0002\u0010\f\u001a\u00020\u00072\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00072\b\b\u0002\u0010\u000f\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u00072\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u00052\b\b\u0002\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u00052\b\b\u0002\u0010\u0015\u001a\u00020\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u00052\b\b\u0002\u0010\u0017\u001a\u00020\u00052\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00052\b\b\u0002\u0010\u001b\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u00052\b\b\u0002\u0010\u001d\u001a\u00020\u00192\b\b\u0002\u0010\u001e\u001a\u00020\u00192\b\b\u0002\u0010\u001f\u001a\u00020\u00192\b\b\u0002\u0010 \u001a\u00020\u00032\b\b\u0002\u0010!\u001a\u00020\u00072\b\b\u0002\u0010\"\u001a\u00020\u00032\b\b\u0002\u0010#\u001a\u00020\u00052\b\b\u0002\u0010$\u001a\u00020\u0019H\u00c6\u0001J\u0013\u0010i\u001a\u00020\u00192\b\u0010j\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010k\u001a\u00020\u0005H\u00d6\u0001J\t\u0010l\u001a\u00020\u0007H\u00d6\u0001R\u0016\u0010\u0014\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0016\u0010\u000f\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\'R\u0016\u0010$\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0016\u0010\u0017\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\'R\u0016\u0010\u001f\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010*R\u0016\u0010#\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\'R\u0016\u0010\u0012\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\'R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0016\u0010\t\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u00100R\u0016\u0010\u000b\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u00102R\u0016\u0010\u0011\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u00100R\u0016\u0010\u0016\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010\'R\u0016\u0010\r\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u00100R\u0016\u0010\u0018\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010*R\u0016\u0010\u001a\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010\'R\u0016\u0010\u001d\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010*R\u0016\u0010\u0013\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010\'R\u0016\u0010!\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u00102R\u0016\u0010 \u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u00100R\u0016\u0010\u001b\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010\'R\u0016\u0010\u001e\u001a\u00020\u00198\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010*R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u00102R\u0016\u0010\u0015\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bA\u0010\'R\u0016\u0010\u0010\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u00102R\u0016\u0010\f\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u00102R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bD\u00100R\u0016\u0010\u000e\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bE\u00102R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bF\u0010\'R\u0016\u0010\u001c\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bG\u0010\'R\u0016\u0010\"\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bH\u00100\u00a8\u0006m"}, d2 = {"Lme/wcy/music/account/bean/ProfileData;", "", "userId", "", "userType", "", "nickname", "", "avatarImgId", "avatarUrl", "backgroundImgId", "backgroundUrl", "signature", "createTime", "userName", "accountType", "shortUserName", "birthday", "authority", "gender", "accountStatus", "province", "city", "authStatus", "defaultAvatar", "", "djStatus", "locationStatus", "vipType", "followed", "mutual", "authenticated", "lastLoginTime", "lastLoginIP", "viptypeVersion", "authenticationTypes", "anchor", "(JILjava/lang/String;JLjava/lang/String;JLjava/lang/String;Ljava/lang/String;JLjava/lang/String;ILjava/lang/String;JIIIIIIZIIIZZZJLjava/lang/String;JIZ)V", "getAccountStatus", "()I", "getAccountType", "getAnchor", "()Z", "getAuthStatus", "getAuthenticated", "getAuthenticationTypes", "getAuthority", "getAvatarImgId", "()J", "getAvatarUrl", "()Ljava/lang/String;", "getBackgroundImgId", "getBackgroundUrl", "getBirthday", "getCity", "getCreateTime", "getDefaultAvatar", "getDjStatus", "getFollowed", "getGender", "getLastLoginIP", "getLastLoginTime", "getLocationStatus", "getMutual", "getNickname", "getProvince", "getShortUserName", "getSignature", "getUserId", "getUserName", "getUserType", "getVipType", "getViptypeVersion", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class ProfileData {
    @com.google.gson.annotations.SerializedName(value = "userId")
    private final long userId = 0L;
    @com.google.gson.annotations.SerializedName(value = "userType")
    private final int userType = 0;
    @com.google.gson.annotations.SerializedName(value = "nickname")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nickname = null;
    @com.google.gson.annotations.SerializedName(value = "avatarImgId")
    private final long avatarImgId = 0L;
    @com.google.gson.annotations.SerializedName(value = "avatarUrl")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String avatarUrl = null;
    @com.google.gson.annotations.SerializedName(value = "backgroundImgId")
    private final long backgroundImgId = 0L;
    @com.google.gson.annotations.SerializedName(value = "backgroundUrl")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String backgroundUrl = null;
    @com.google.gson.annotations.SerializedName(value = "signature")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String signature = null;
    @com.google.gson.annotations.SerializedName(value = "createTime")
    private final long createTime = 0L;
    @com.google.gson.annotations.SerializedName(value = "userName")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String userName = null;
    @com.google.gson.annotations.SerializedName(value = "accountType")
    private final int accountType = 0;
    @com.google.gson.annotations.SerializedName(value = "shortUserName")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String shortUserName = null;
    @com.google.gson.annotations.SerializedName(value = "birthday")
    private final long birthday = 0L;
    @com.google.gson.annotations.SerializedName(value = "authority")
    private final int authority = 0;
    @com.google.gson.annotations.SerializedName(value = "gender")
    private final int gender = 0;
    @com.google.gson.annotations.SerializedName(value = "accountStatus")
    private final int accountStatus = 0;
    @com.google.gson.annotations.SerializedName(value = "province")
    private final int province = 0;
    @com.google.gson.annotations.SerializedName(value = "city")
    private final int city = 0;
    @com.google.gson.annotations.SerializedName(value = "authStatus")
    private final int authStatus = 0;
    @com.google.gson.annotations.SerializedName(value = "defaultAvatar")
    private final boolean defaultAvatar = false;
    @com.google.gson.annotations.SerializedName(value = "djStatus")
    private final int djStatus = 0;
    @com.google.gson.annotations.SerializedName(value = "locationStatus")
    private final int locationStatus = 0;
    @com.google.gson.annotations.SerializedName(value = "vipType")
    private final int vipType = 0;
    @com.google.gson.annotations.SerializedName(value = "followed")
    private final boolean followed = false;
    @com.google.gson.annotations.SerializedName(value = "mutual")
    private final boolean mutual = false;
    @com.google.gson.annotations.SerializedName(value = "authenticated")
    private final boolean authenticated = false;
    @com.google.gson.annotations.SerializedName(value = "lastLoginTime")
    private final long lastLoginTime = 0L;
    @com.google.gson.annotations.SerializedName(value = "lastLoginIP")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String lastLoginIP = null;
    @com.google.gson.annotations.SerializedName(value = "viptypeVersion")
    private final long viptypeVersion = 0L;
    @com.google.gson.annotations.SerializedName(value = "authenticationTypes")
    private final int authenticationTypes = 0;
    @com.google.gson.annotations.SerializedName(value = "anchor")
    private final boolean anchor = false;
    
    public ProfileData(long userId, int userType, @org.jetbrains.annotations.NotNull()
    java.lang.String nickname, long avatarImgId, @org.jetbrains.annotations.NotNull()
    java.lang.String avatarUrl, long backgroundImgId, @org.jetbrains.annotations.NotNull()
    java.lang.String backgroundUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String signature, long createTime, @org.jetbrains.annotations.NotNull()
    java.lang.String userName, int accountType, @org.jetbrains.annotations.NotNull()
    java.lang.String shortUserName, long birthday, int authority, int gender, int accountStatus, int province, int city, int authStatus, boolean defaultAvatar, int djStatus, int locationStatus, int vipType, boolean followed, boolean mutual, boolean authenticated, long lastLoginTime, @org.jetbrains.annotations.NotNull()
    java.lang.String lastLoginIP, long viptypeVersion, int authenticationTypes, boolean anchor) {
        super();
    }
    
    public final long getUserId() {
        return 0L;
    }
    
    public final int getUserType() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNickname() {
        return null;
    }
    
    public final long getAvatarImgId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAvatarUrl() {
        return null;
    }
    
    public final long getBackgroundImgId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBackgroundUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSignature() {
        return null;
    }
    
    public final long getCreateTime() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUserName() {
        return null;
    }
    
    public final int getAccountType() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getShortUserName() {
        return null;
    }
    
    public final long getBirthday() {
        return 0L;
    }
    
    public final int getAuthority() {
        return 0;
    }
    
    public final int getGender() {
        return 0;
    }
    
    public final int getAccountStatus() {
        return 0;
    }
    
    public final int getProvince() {
        return 0;
    }
    
    public final int getCity() {
        return 0;
    }
    
    public final int getAuthStatus() {
        return 0;
    }
    
    public final boolean getDefaultAvatar() {
        return false;
    }
    
    public final int getDjStatus() {
        return 0;
    }
    
    public final int getLocationStatus() {
        return 0;
    }
    
    public final int getVipType() {
        return 0;
    }
    
    public final boolean getFollowed() {
        return false;
    }
    
    public final boolean getMutual() {
        return false;
    }
    
    public final boolean getAuthenticated() {
        return false;
    }
    
    public final long getLastLoginTime() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLastLoginIP() {
        return null;
    }
    
    public final long getViptypeVersion() {
        return 0L;
    }
    
    public final int getAuthenticationTypes() {
        return 0;
    }
    
    public final boolean getAnchor() {
        return false;
    }
    
    public ProfileData() {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    public final int component11() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    public final long component13() {
        return 0L;
    }
    
    public final int component14() {
        return 0;
    }
    
    public final int component15() {
        return 0;
    }
    
    public final int component16() {
        return 0;
    }
    
    public final int component17() {
        return 0;
    }
    
    public final int component18() {
        return 0;
    }
    
    public final int component19() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final boolean component20() {
        return false;
    }
    
    public final int component21() {
        return 0;
    }
    
    public final int component22() {
        return 0;
    }
    
    public final int component23() {
        return 0;
    }
    
    public final boolean component24() {
        return false;
    }
    
    public final boolean component25() {
        return false;
    }
    
    public final boolean component26() {
        return false;
    }
    
    public final long component27() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component28() {
        return null;
    }
    
    public final long component29() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    public final int component30() {
        return 0;
    }
    
    public final boolean component31() {
        return false;
    }
    
    public final long component4() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    public final long component6() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    public final long component9() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.account.bean.ProfileData copy(long userId, int userType, @org.jetbrains.annotations.NotNull()
    java.lang.String nickname, long avatarImgId, @org.jetbrains.annotations.NotNull()
    java.lang.String avatarUrl, long backgroundImgId, @org.jetbrains.annotations.NotNull()
    java.lang.String backgroundUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String signature, long createTime, @org.jetbrains.annotations.NotNull()
    java.lang.String userName, int accountType, @org.jetbrains.annotations.NotNull()
    java.lang.String shortUserName, long birthday, int authority, int gender, int accountStatus, int province, int city, int authStatus, boolean defaultAvatar, int djStatus, int locationStatus, int vipType, boolean followed, boolean mutual, boolean authenticated, long lastLoginTime, @org.jetbrains.annotations.NotNull()
    java.lang.String lastLoginIP, long viptypeVersion, int authenticationTypes, boolean anchor) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}