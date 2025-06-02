package me.wcy.music.common.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangchenyan.top on 2023/9/6.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\nH\u00c6\u0003J7\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001d\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001c\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001e"}, d2 = {"Lme/wcy/music/common/bean/OriginSongSimpleData;", "", "songId", "", "name", "", "artists", "", "Lme/wcy/music/common/bean/ArtistData;", "albumMeta", "Lme/wcy/music/common/bean/AlbumData;", "(ILjava/lang/String;Ljava/util/List;Lme/wcy/music/common/bean/AlbumData;)V", "getAlbumMeta", "()Lme/wcy/music/common/bean/AlbumData;", "getArtists", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", "getSongId", "()I", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class OriginSongSimpleData {
    @com.google.gson.annotations.SerializedName(value = "songId")
    private final int songId = 0;
    @com.google.gson.annotations.SerializedName(value = "name")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @com.google.gson.annotations.SerializedName(value = "artists")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<me.wcy.music.common.bean.ArtistData> artists = null;
    @com.google.gson.annotations.SerializedName(value = "albumMeta")
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.common.bean.AlbumData albumMeta = null;
    
    public OriginSongSimpleData(int songId, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.ArtistData> artists, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.AlbumData albumMeta) {
        super();
    }
    
    public final int getSongId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.ArtistData> getArtists() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.AlbumData getAlbumMeta() {
        return null;
    }
    
    public OriginSongSimpleData() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.ArtistData> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.AlbumData component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.bean.OriginSongSimpleData copy(int songId, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.ArtistData> artists, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.AlbumData albumMeta) {
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