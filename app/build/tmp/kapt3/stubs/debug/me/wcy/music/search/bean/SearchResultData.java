package me.wcy.music.search.bean;

import com.google.gson.annotations.SerializedName;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.common.bean.SongData;

/**
 * Created by wangchenyan.top on 2023/9/20.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\nJ\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0006H\u00c6\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0006H\u00c6\u0003J=\u0010\u0015\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\b\b\u0002\u0010\t\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u0006H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001R\u0016\u0010\t\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e\u00a8\u0006\u001c"}, d2 = {"Lme/wcy/music/search/bean/SearchResultData;", "", "songs", "", "Lme/wcy/music/common/bean/SongData;", "songCount", "", "playlists", "Lme/wcy/music/common/bean/PlaylistData;", "playlistCount", "(Ljava/util/List;ILjava/util/List;I)V", "getPlaylistCount", "()I", "getPlaylists", "()Ljava/util/List;", "getSongCount", "getSongs", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
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
    
    public SearchResultData(@org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.SongData> songs, int songCount, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.PlaylistData> playlists, int playlistCount) {
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
    
    public SearchResultData() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.common.bean.SongData> component1() {
        return null;
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
    public final me.wcy.music.search.bean.SearchResultData copy(@org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.SongData> songs, int songCount, @org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.common.bean.PlaylistData> playlists, int playlistCount) {
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