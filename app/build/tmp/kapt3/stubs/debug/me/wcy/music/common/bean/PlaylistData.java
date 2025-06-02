package me.wcy.music.common.bean;

import com.google.gson.annotations.SerializedName;
import me.wcy.music.account.bean.ProfileData;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b#\b\u0086\b\u0018\u00002\u00020\u0001B\u00a1\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\f\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\n\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0017J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\fH\u00c6\u0003J\t\u00106\u001a\u00020\u0005H\u00c6\u0003J\u000f\u00107\u001a\b\u0012\u0004\u0012\u00020\u00050\u0013H\u00c6\u0003J\t\u00108\u001a\u00020\nH\u00c6\u0003J\t\u00109\u001a\u00020\u0005H\u00c6\u0003J\t\u0010:\u001a\u00020\u0005H\u00c6\u0003J\t\u0010;\u001a\u00020\u0005H\u00c6\u0003J\t\u0010<\u001a\u00020\u0005H\u00c6\u0003J\t\u0010=\u001a\u00020\bH\u00c6\u0003J\t\u0010>\u001a\u00020\nH\u00c6\u0003J\t\u0010?\u001a\u00020\fH\u00c6\u0003J\t\u0010@\u001a\u00020\u0003H\u00c6\u0003J\t\u0010A\u001a\u00020\u0003H\u00c6\u0003J\t\u0010B\u001a\u00020\u0003H\u00c6\u0003J\u00a5\u0001\u0010C\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\f2\b\b\u0002\u0010\u0011\u001a\u00020\u00052\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u00132\b\b\u0002\u0010\u0014\u001a\u00020\n2\b\b\u0002\u0010\u0015\u001a\u00020\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010D\u001a\u00020\n2\b\u0010E\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u0010F\u001a\u00020\u0005J\u0006\u0010G\u001a\u00020\u0005J\t\u0010H\u001a\u00020\fH\u00d6\u0001J\t\u0010I\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\u000f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001c\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0016\u0010\u0011\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001dR\u0016\u0010\u0014\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0019R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001dR\u0016\u0010\u000e\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0019R$\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\u00138\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u0016\u0010\u0010\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\"R\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010)R\u0016\u0010\u0016\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\u001dR\u0016\u0010\u000b\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010-R\u0016\u0010\u0015\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010\u001dR\u0016\u0010\r\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010\u0019\u00a8\u0006J"}, d2 = {"Lme/wcy/music/common/bean/PlaylistData;", "", "id", "", "name", "", "coverImgUrl", "creator", "Lme/wcy/music/account/bean/ProfileData;", "subscribed", "", "trackCount", "", "userId", "playCount", "bookCount", "specialType", "description", "tags", "", "highQuality", "updateFrequency", "toplistType", "(JLjava/lang/String;Ljava/lang/String;Lme/wcy/music/account/bean/ProfileData;ZIJJJILjava/lang/String;Ljava/util/List;ZLjava/lang/String;Ljava/lang/String;)V", "getBookCount", "()J", "getCoverImgUrl$annotations", "()V", "getCoverImgUrl", "()Ljava/lang/String;", "getCreator", "()Lme/wcy/music/account/bean/ProfileData;", "getDescription", "getHighQuality", "()Z", "getId", "getName", "getPlayCount", "songList", "Lme/wcy/music/common/bean/SongData;", "getSongList", "()Ljava/util/List;", "setSongList", "(Ljava/util/List;)V", "getSpecialType", "()I", "getSubscribed", "getTags", "getToplistType", "getTrackCount", "getUpdateFrequency", "getUserId", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "getLargeCover", "getSmallCover", "hashCode", "toString", "app_debug"})
public final class PlaylistData {
    @com.google.gson.annotations.SerializedName(value = "id")
    private final long id = 0L;
    @com.google.gson.annotations.SerializedName(value = "name")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @com.google.gson.annotations.SerializedName(value = "coverImgUrl", alternate = {"picUrl"})
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    private final java.lang.String coverImgUrl = null;
    @com.google.gson.annotations.SerializedName(value = "creator")
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.account.bean.ProfileData creator = null;
    @com.google.gson.annotations.SerializedName(value = "subscribed")
    private final boolean subscribed = false;
    @com.google.gson.annotations.SerializedName(value = "trackCount")
    private final int trackCount = 0;
    @com.google.gson.annotations.SerializedName(value = "userId")
    private final long userId = 0L;
    @com.google.gson.annotations.SerializedName(value = "playCount", alternate = {"playcount"})
    private final long playCount = 0L;
    @com.google.gson.annotations.SerializedName(value = "bookCount")
    private final long bookCount = 0L;
    @com.google.gson.annotations.SerializedName(value = "specialType")
    private final int specialType = 0;
    @com.google.gson.annotations.SerializedName(value = "description")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    @com.google.gson.annotations.SerializedName(value = "tags")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> tags = null;
    @com.google.gson.annotations.SerializedName(value = "highQuality")
    private final boolean highQuality = false;
    @com.google.gson.annotations.SerializedName(value = "updateFrequency")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String updateFrequency = null;
    @com.google.gson.annotations.SerializedName(value = "ToplistType")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String toplistType = null;
    @com.google.gson.annotations.SerializedName(value = "_songList")
    @org.jetbrains.annotations.NotNull()
    private java.util.List<me.wcy.music.common.bean.SongData> songList;
    
    public PlaylistData(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String coverImgUrl, @org.jetbrains.annotations.NotNull()
    me.wcy.music.account.bean.ProfileData creator, boolean subscribed, int trackCount, long userId, long playCount, long bookCount, int specialType, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> tags, boolean highQuality, @org.jetbrains.annotations.NotNull()
    java.lang.String updateFrequency, @org.jetbrains.annotations.NotNull()
    java.lang.String toplistType) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public final java.lang.String getCoverImgUrl() {
        return null;
    }
    
    @java.lang.Deprecated()
    public static void getCoverImgUrl$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.account.bean.ProfileData getCreator() {
        return null;
    }
    
    public final boolean getSubscribed() {
        return false;
    }
    
    public final int getTrackCount() {
        return 0;
    }
    
    public final long getUserId() {
        return 0L;
    }
    
    public final long getPlayCount() {
        return 0L;
    }
    
    public final long getBookCount() {
        return 0L;
    }
    
    public final int getSpecialType() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getTags() {
        return null;
    }
    
    public final boolean getHighQuality() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUpdateFrequency() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getToplistType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.SongData> getSongList() {
        return null;
    }
    
    public final void setSongList(@org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.SongData> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSmallCover() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLargeCover() {
        return null;
    }
    
    public PlaylistData() {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final int component10() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component12() {
        return null;
    }
    
    public final boolean component13() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.account.bean.ProfileData component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final long component7() {
        return 0L;
    }
    
    public final long component8() {
        return 0L;
    }
    
    public final long component9() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.PlaylistData copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String coverImgUrl, @org.jetbrains.annotations.NotNull()
    me.wcy.music.account.bean.ProfileData creator, boolean subscribed, int trackCount, long userId, long playCount, long bookCount, int specialType, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> tags, boolean highQuality, @org.jetbrains.annotations.NotNull()
    java.lang.String updateFrequency, @org.jetbrains.annotations.NotNull()
    java.lang.String toplistType) {
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