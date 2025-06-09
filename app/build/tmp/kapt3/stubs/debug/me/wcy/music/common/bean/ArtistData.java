package me.wcy.music.common.bean;

import com.google.gson.annotations.SerializedName;
import me.wcy.music.search.SearchAdapterBase;

/**
 * Created by wangchenyan.top on 2023/9/6.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b$\b\u0086\b\u0018\u00002\u00020\u0001B\u007f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0013J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u000eH\u00c6\u0003J\t\u0010(\u001a\u00020\u0012H\u00c6\u0003J\t\u0010)\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010*\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\u000f\u0010+\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010,\u001a\u00020\u0005H\u00c6\u0003J\t\u0010-\u001a\u00020\u0005H\u00c6\u0003J\t\u0010.\u001a\u00020\u0005H\u00c6\u0003J\t\u0010/\u001a\u00020\u000eH\u00c6\u0003J\t\u00100\u001a\u00020\u000eH\u00c6\u0003J\u0083\u0001\u00101\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u000e2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u00c6\u0001J\u0013\u00102\u001a\u00020\u00122\b\u00103\u001a\u0004\u0018\u00010\bH\u00d6\u0003J\t\u00104\u001a\u00020\u000eH\u00d6\u0001J\t\u00105\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\r\u001a\u00020\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\f\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u0011\u001a\u00020\u00128\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\u000b\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0019R\u0016\u0010\u000f\u001a\u00020\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015R\u0016\u0010\u0010\u001a\u00020\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0015R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019R\u0016\u0010\n\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0019R\u0014\u0010#\u001a\u00020\u00038VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b$\u0010\u001dR\u001c\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0017\u00a8\u00066"}, d2 = {"Lme/wcy/music/common/bean/ArtistData;", "Lme/wcy/music/search/SearchAdapterBase$SearchItem;", "id", "", "name", "", "tns", "", "", "alias", "picUrl", "img1v1Url", "briefDesc", "albumSize", "", "musicSize", "mvSize", "followed", "", "(JLjava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIZ)V", "getAlbumSize", "()I", "getAlias", "()Ljava/util/List;", "getBriefDesc", "()Ljava/lang/String;", "getFollowed", "()Z", "getId", "()J", "getImg1v1Url", "getMusicSize", "getMvSize", "getName", "getPicUrl", "searchId", "getSearchId", "getTns", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class ArtistData implements me.wcy.music.search.SearchAdapterBase.SearchItem {
    @com.google.gson.annotations.SerializedName(value = "id")
    private final long id = 0L;
    @com.google.gson.annotations.SerializedName(value = "name")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @com.google.gson.annotations.SerializedName(value = "tns")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Object> tns = null;
    @com.google.gson.annotations.SerializedName(value = "alias")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Object> alias = null;
    @com.google.gson.annotations.SerializedName(value = "picUrl")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String picUrl = null;
    @com.google.gson.annotations.SerializedName(value = "img1v1Url")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String img1v1Url = null;
    @com.google.gson.annotations.SerializedName(value = "briefDesc")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String briefDesc = null;
    @com.google.gson.annotations.SerializedName(value = "albumSize")
    private final int albumSize = 0;
    @com.google.gson.annotations.SerializedName(value = "musicSize")
    private final int musicSize = 0;
    @com.google.gson.annotations.SerializedName(value = "mvSize")
    private final int mvSize = 0;
    @com.google.gson.annotations.SerializedName(value = "followed")
    private final boolean followed = false;
    
    public ArtistData(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends java.lang.Object> tns, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends java.lang.Object> alias, @org.jetbrains.annotations.NotNull()
    java.lang.String picUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String img1v1Url, @org.jetbrains.annotations.NotNull()
    java.lang.String briefDesc, int albumSize, int musicSize, int mvSize, boolean followed) {
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
    public final java.util.List<java.lang.Object> getTns() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Object> getAlias() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPicUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getImg1v1Url() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBriefDesc() {
        return null;
    }
    
    public final int getAlbumSize() {
        return 0;
    }
    
    public final int getMusicSize() {
        return 0;
    }
    
    public final int getMvSize() {
        return 0;
    }
    
    public final boolean getFollowed() {
        return false;
    }
    
    @java.lang.Override()
    public long getSearchId() {
        return 0L;
    }
    
    public ArtistData() {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final boolean component11() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Object> component3() {
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
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.ArtistData copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends java.lang.Object> tns, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends java.lang.Object> alias, @org.jetbrains.annotations.NotNull()
    java.lang.String picUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String img1v1Url, @org.jetbrains.annotations.NotNull()
    java.lang.String briefDesc, int albumSize, int musicSize, int mvSize, boolean followed) {
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