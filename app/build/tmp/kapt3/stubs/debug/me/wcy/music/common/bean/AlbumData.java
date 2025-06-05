package me.wcy.music.common.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangchenyan.top on 2023/9/6.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b+\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\b\u0018\u00002\u00020\u0001B\u00a9\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\b\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0016J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\u000f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u000f0\bH\u00c6\u0003J\t\u00100\u001a\u00020\u0012H\u00c6\u0003J\t\u00101\u001a\u00020\u0012H\u00c6\u0003J\t\u00102\u001a\u00020\u0005H\u00c6\u0003J\t\u00103\u001a\u00020\u0005H\u00c6\u0003J\t\u00104\u001a\u00020\u0005H\u00c6\u0003J\t\u00105\u001a\u00020\u0005H\u00c6\u0003J\u000f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u00c6\u0003J\t\u00107\u001a\u00020\u0005H\u00c6\u0003J\t\u00108\u001a\u00020\u0003H\u00c6\u0003J\t\u00109\u001a\u00020\u0005H\u00c6\u0003J\t\u0010:\u001a\u00020\u0003H\u00c6\u0003J\t\u0010;\u001a\u00020\u0005H\u00c6\u0003J\u00ad\u0001\u0010<\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\b2\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00122\b\b\u0002\u0010\u0014\u001a\u00020\u00052\b\b\u0002\u0010\u0015\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010=\u001a\u00020>2\b\u0010?\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u0010@\u001a\u00020\u0005J\u0006\u0010A\u001a\u00020\u0005J\t\u0010B\u001a\u00020\u0012H\u00d6\u0001J\t\u0010C\u001a\u00020\u0005H\u00d6\u0001R\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u000f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\r\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0016\u0010\u000b\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001cR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001cR\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001fR\u0016\u0010\t\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001cR\u001c\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b#\u0010$\u001a\u0004\b%\u0010\u001cR\u0016\u0010\f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001fR\u0016\u0010\u0011\u001a\u00020\u00128\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0016\u0010\u0013\u001a\u00020\u00128\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010(R\u0016\u0010\u0014\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001cR\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001aR\u0016\u0010\u0015\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001c\u00a8\u0006D"}, d2 = {"Lme/wcy/music/common/bean/AlbumData;", "", "id", "", "name", "", "picUrl", "tns", "", "picStr", "pic", "description", "publishTime", "company", "artist", "Lme/wcy/music/common/bean/ArtistData;", "artists", "size", "", "status", "subType", "type", "(JLjava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;JLjava/lang/String;JLjava/lang/String;Lme/wcy/music/common/bean/ArtistData;Ljava/util/List;IILjava/lang/String;Ljava/lang/String;)V", "getArtist", "()Lme/wcy/music/common/bean/ArtistData;", "getArtists", "()Ljava/util/List;", "getCompany", "()Ljava/lang/String;", "getDescription", "getId", "()J", "getName", "getPic", "getPicStr", "getPicUrl$annotations", "()V", "getPicUrl", "getPublishTime", "getSize", "()I", "getStatus", "getSubType", "getTns", "getType", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "getLargeCover", "getSmallCover", "hashCode", "toString", "app_debug"})
public final class AlbumData {
    @com.google.gson.annotations.SerializedName(value = "id")
    private final long id = 0L;
    @com.google.gson.annotations.SerializedName(value = "name")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @com.google.gson.annotations.SerializedName(value = "picUrl")
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    private final java.lang.String picUrl = null;
    @com.google.gson.annotations.SerializedName(value = "tns")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Object> tns = null;
    @com.google.gson.annotations.SerializedName(value = "pic_str")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String picStr = null;
    @com.google.gson.annotations.SerializedName(value = "pic")
    private final long pic = 0L;
    @com.google.gson.annotations.SerializedName(value = "description")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    @com.google.gson.annotations.SerializedName(value = "publishTime")
    private final long publishTime = 0L;
    @com.google.gson.annotations.SerializedName(value = "company")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String company = null;
    @com.google.gson.annotations.SerializedName(value = "artist")
    @org.jetbrains.annotations.Nullable()
    private final me.wcy.music.common.bean.ArtistData artist = null;
    @com.google.gson.annotations.SerializedName(value = "artists")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<me.wcy.music.common.bean.ArtistData> artists = null;
    @com.google.gson.annotations.SerializedName(value = "size")
    private final int size = 0;
    @com.google.gson.annotations.SerializedName(value = "status")
    private final int status = 0;
    @com.google.gson.annotations.SerializedName(value = "subType")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String subType = null;
    @com.google.gson.annotations.SerializedName(value = "type")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String type = null;
    
    public AlbumData(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String picUrl, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends java.lang.Object> tns, @org.jetbrains.annotations.NotNull()
    java.lang.String picStr, long pic, @org.jetbrains.annotations.NotNull()
    java.lang.String description, long publishTime, @org.jetbrains.annotations.NotNull()
    java.lang.String company, @org.jetbrains.annotations.Nullable()
    me.wcy.music.common.bean.ArtistData artist, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.ArtistData> artists, int size, int status, @org.jetbrains.annotations.NotNull()
    java.lang.String subType, @org.jetbrains.annotations.NotNull()
    java.lang.String type) {
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
    public final java.lang.String getPicUrl() {
        return null;
    }
    
    @java.lang.Deprecated()
    public static void getPicUrl$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Object> getTns() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPicStr() {
        return null;
    }
    
    public final long getPic() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    public final long getPublishTime() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCompany() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final me.wcy.music.common.bean.ArtistData getArtist() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.ArtistData> getArtists() {
        return null;
    }
    
    public final int getSize() {
        return 0;
    }
    
    public final int getStatus() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSubType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSmallCover() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLargeCover() {
        return null;
    }
    
    public AlbumData() {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final me.wcy.music.common.bean.ArtistData component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.ArtistData> component11() {
        return null;
    }
    
    public final int component12() {
        return 0;
    }
    
    public final int component13() {
        return 0;
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
    public final java.util.List<java.lang.Object> component4() {
        return null;
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
    
    public final long component8() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.AlbumData copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String picUrl, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends java.lang.Object> tns, @org.jetbrains.annotations.NotNull()
    java.lang.String picStr, long pic, @org.jetbrains.annotations.NotNull()
    java.lang.String description, long publishTime, @org.jetbrains.annotations.NotNull()
    java.lang.String company, @org.jetbrains.annotations.Nullable()
    me.wcy.music.common.bean.ArtistData artist, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.ArtistData> artists, int size, int status, @org.jetbrains.annotations.NotNull()
    java.lang.String subType, @org.jetbrains.annotations.NotNull()
    java.lang.String type) {
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