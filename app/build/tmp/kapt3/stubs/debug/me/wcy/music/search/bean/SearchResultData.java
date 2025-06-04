package me.wcy.music.search.bean;

import com.google.gson.annotations.SerializedName;
import me.wcy.music.common.bean.AlbumData;
import me.wcy.music.common.bean.ArtistData;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.common.bean.SongData;

/**
 * Created by wangchenyan.top on 2023/9/20.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0087\u0001\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0013J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0006H\u00c6\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0006H\u00c6\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0006H\u00c6\u0003J\u000f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0006H\u00c6\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00110\u0003H\u00c6\u0003J\u008b\u0001\u0010*\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\b\b\u0002\u0010\t\u001a\u00020\u00062\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00032\b\b\u0002\u0010\f\u001a\u00020\u00062\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00062\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010.\u001a\u00020\u0006H\u00d6\u0001J\t\u0010/\u001a\u000200H\u00d6\u0001R\u0016\u0010\u000f\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\f\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0016\u0010\t\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0015R\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u0016\u0010\u0012\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0017\u00a8\u00061"}, d2 = {"Lme/wcy/music/search/bean/SearchResultData;", "", "songs", "", "Lme/wcy/music/common/bean/SongData;", "songCount", "", "playlists", "Lme/wcy/music/common/bean/PlaylistData;", "playlistCount", "artists", "Lme/wcy/music/common/bean/ArtistData;", "artistCount", "albums", "Lme/wcy/music/common/bean/AlbumData;", "albumCount", "userprofiles", "Lme/wcy/music/search/bean/UserData;", "userprofileCount", "(Ljava/util/List;ILjava/util/List;ILjava/util/List;ILjava/util/List;ILjava/util/List;I)V", "getAlbumCount", "()I", "getAlbums", "()Ljava/util/List;", "getArtistCount", "getArtists", "getPlaylistCount", "getPlaylists", "getSongCount", "getSongs", "getUserprofileCount", "getUserprofiles", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class SearchResultData {
    @com.google.gson.annotations.SerializedName(value = "songs")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<me.wcy.music.common.bean.SongData> songs = null;
    @com.google.gson.annotations.SerializedName(value = "songCount")
    private final int songCount = 0;
    @com.google.gson.annotations.SerializedName(value = "playlists")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<me.wcy.music.common.bean.PlaylistData> playlists = null;
    @com.google.gson.annotations.SerializedName(value = "playlistCount")
    private final int playlistCount = 0;
    @com.google.gson.annotations.SerializedName(value = "artists")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<me.wcy.music.common.bean.ArtistData> artists = null;
    @com.google.gson.annotations.SerializedName(value = "artistCount")
    private final int artistCount = 0;
    @com.google.gson.annotations.SerializedName(value = "albums")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<me.wcy.music.common.bean.AlbumData> albums = null;
    @com.google.gson.annotations.SerializedName(value = "albumCount")
    private final int albumCount = 0;
    @com.google.gson.annotations.SerializedName(value = "userprofiles")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<me.wcy.music.search.bean.UserData> userprofiles = null;
    @com.google.gson.annotations.SerializedName(value = "userprofileCount")
    private final int userprofileCount = 0;
    
    public SearchResultData(@org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.SongData> songs, int songCount, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.PlaylistData> playlists, int playlistCount, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.ArtistData> artists, int artistCount, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.AlbumData> albums, int albumCount, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.search.bean.UserData> userprofiles, int userprofileCount) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.SongData> getSongs() {
        return null;
    }
    
    public final int getSongCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.PlaylistData> getPlaylists() {
        return null;
    }
    
    public final int getPlaylistCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.ArtistData> getArtists() {
        return null;
    }
    
    public final int getArtistCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.AlbumData> getAlbums() {
        return null;
    }
    
    public final int getAlbumCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.search.bean.UserData> getUserprofiles() {
        return null;
    }
    
    public final int getUserprofileCount() {
        return 0;
    }
    
    public SearchResultData() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.SongData> component1() {
        return null;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.PlaylistData> component3() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.ArtistData> component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.AlbumData> component7() {
        return null;
    }
    
    public final int component8() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.search.bean.UserData> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.search.bean.SearchResultData copy(@org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.SongData> songs, int songCount, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.PlaylistData> playlists, int playlistCount, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.ArtistData> artists, int artistCount, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.AlbumData> albums, int albumCount, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.search.bean.UserData> userprofiles, int userprofileCount) {
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