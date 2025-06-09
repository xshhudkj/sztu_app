package me.wcy.music.storage.db.entity;

import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import kotlinx.parcelize.IgnoredOnParcel;
import kotlinx.parcelize.Parcelize;
import me.wcy.music.utils.CoverUtils;

/**
 * Created by wangchenyan.top on 2023/8/29.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b4\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 I2\u00020\u0001:\u0001IB\u0087\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0007\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0012J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\u0007H\u00c6\u0003J\t\u0010.\u001a\u00020\u0007H\u00c6\u0003J\t\u0010/\u001a\u00020\u0007H\u00c6\u0003J\t\u00100\u001a\u00020\u0005H\u00c6\u0003J\t\u00101\u001a\u00020\u0005H\u00c6\u0003J\t\u00102\u001a\u00020\u0007H\u00c6\u0003J\t\u00103\u001a\u00020\u0007H\u00c6\u0003J\t\u00104\u001a\u00020\u0005H\u00c6\u0003J\t\u00105\u001a\u00020\u0007H\u00c6\u0003J\t\u00106\u001a\u00020\u0005H\u00c6\u0003J\t\u00107\u001a\u00020\u0007H\u00c6\u0003J\t\u00108\u001a\u00020\u0005H\u00c6\u0003J\u008b\u0001\u00109\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00072\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u00072\b\b\u0002\u0010\u000f\u001a\u00020\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u00072\b\b\u0002\u0010\u0011\u001a\u00020\u0005H\u00c6\u0001J\t\u0010:\u001a\u00020\u0003H\u00d6\u0001J\u0013\u0010;\u001a\u00020<2\b\u0010=\u001a\u0004\u0018\u00010>H\u0096\u0002J\u0006\u0010?\u001a\u00020\u0007J\u0006\u0010@\u001a\u00020\u0007J\b\u0010A\u001a\u00020\u0003H\u0016J\u0006\u0010B\u001a\u00020<J\t\u0010C\u001a\u00020\u0007H\u00d6\u0001J\u0019\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\n\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\f\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0014R\u0016\u0010\u000b\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0016\u0010\t\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0016\u0010\r\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019R\u0016\u0010\u0010\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0014R\u0016\u0010\u0011\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0019R\u001e\u0010\u000f\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0014\"\u0004\b \u0010!R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0019R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0014R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R$\u0010&\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0014\n\u0000\u0012\u0004\b\'\u0010\u0016\u001a\u0004\b(\u0010\u0014\"\u0004\b)\u0010!R\u001e\u0010\u000e\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0014\"\u0004\b+\u0010!\u00a8\u0006J"}, d2 = {"Lme/wcy/music/storage/db/entity/SongEntity;", "Landroid/os/Parcelable;", "type", "", "songId", "", "title", "", "artist", "artistId", "album", "albumId", "albumCover", "duration", "uri", "path", "fileName", "fileSize", "(IJLjava/lang/String;Ljava/lang/String;JLjava/lang/String;JLjava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V", "getAlbum", "()Ljava/lang/String;", "getAlbumCover$annotations", "()V", "getAlbumCover", "getAlbumId", "()J", "getArtist", "getArtistId", "getDuration", "getFileName", "getFileSize", "getPath", "setPath", "(Ljava/lang/String;)V", "getSongId", "getTitle", "getType", "()I", "uniqueId", "getUniqueId$annotations", "getUniqueId", "setUniqueId", "getUri", "setUri", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "describeContents", "equals", "", "other", "", "getLargeCover", "getSmallCover", "hashCode", "isLocal", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "Companion", "app_debug"})
@kotlinx.parcelize.Parcelize()
@androidx.room.Entity(tableName = "play_list", indices = {@androidx.room.Index(value = {"title"}), @androidx.room.Index(value = {"artist"}), @androidx.room.Index(value = {"album"})})
public final class SongEntity implements android.os.Parcelable {
    @androidx.room.ColumnInfo(name = "type")
    private final int type = 0;
    @androidx.room.ColumnInfo(name = "song_id")
    private final long songId = 0L;
    @androidx.room.ColumnInfo(name = "title")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String title = null;
    @androidx.room.ColumnInfo(name = "artist")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String artist = null;
    @androidx.room.ColumnInfo(name = "artist_id")
    private final long artistId = 0L;
    @androidx.room.ColumnInfo(name = "album")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String album = null;
    @androidx.room.ColumnInfo(name = "album_id")
    private final long albumId = 0L;
    @androidx.room.ColumnInfo(name = "album_cover")
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    private final java.lang.String albumCover = null;
    @androidx.room.ColumnInfo(name = "duration")
    private final long duration = 0L;
    @androidx.room.ColumnInfo(name = "uri", defaultValue = "")
    @org.jetbrains.annotations.NotNull()
    private java.lang.String uri;
    @androidx.room.ColumnInfo(name = "path")
    @org.jetbrains.annotations.NotNull()
    private java.lang.String path;
    @androidx.room.ColumnInfo(name = "file_name")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String fileName = null;
    @androidx.room.ColumnInfo(name = "file_size")
    private final long fileSize = 0L;
    @androidx.room.PrimaryKey()
    @androidx.room.ColumnInfo(name = "unique_id")
    @org.jetbrains.annotations.NotNull()
    private java.lang.String uniqueId;
    public static final int LOCAL = 0;
    public static final int ONLINE = 1;
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.storage.db.entity.SongEntity.Companion Companion = null;
    
    public SongEntity(int type, long songId, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String artist, long artistId, @org.jetbrains.annotations.NotNull()
    java.lang.String album, long albumId, @org.jetbrains.annotations.NotNull()
    java.lang.String albumCover, long duration, @org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    java.lang.String path, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName, long fileSize) {
        super();
    }
    
    public final int getType() {
        return 0;
    }
    
    public final long getSongId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getArtist() {
        return null;
    }
    
    public final long getArtistId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAlbum() {
        return null;
    }
    
    public final long getAlbumId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public final java.lang.String getAlbumCover() {
        return null;
    }
    
    @java.lang.Deprecated()
    public static void getAlbumCover$annotations() {
    }
    
    public final long getDuration() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUri() {
        return null;
    }
    
    public final void setUri(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPath() {
        return null;
    }
    
    public final void setPath(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFileName() {
        return null;
    }
    
    public final long getFileSize() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUniqueId() {
        return null;
    }
    
    @kotlinx.parcelize.IgnoredOnParcel()
    @java.lang.Deprecated()
    public static void getUniqueId$annotations() {
    }
    
    public final void setUniqueId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    public final boolean isLocal() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSmallCover() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLargeCover() {
        return null;
    }
    
    public SongEntity() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    public final long component13() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    public final long component5() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    public final long component7() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    public final long component9() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.storage.db.entity.SongEntity copy(int type, long songId, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String artist, long artistId, @org.jetbrains.annotations.NotNull()
    java.lang.String album, long albumId, @org.jetbrains.annotations.NotNull()
    java.lang.String albumCover, long duration, @org.jetbrains.annotations.NotNull()
    java.lang.String uri, @org.jetbrains.annotations.NotNull()
    java.lang.String path, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName, long fileSize) {
        return null;
    }
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @java.lang.Override()
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel, int flags) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lme/wcy/music/storage/db/entity/SongEntity$Companion;", "", "()V", "LOCAL", "", "ONLINE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}