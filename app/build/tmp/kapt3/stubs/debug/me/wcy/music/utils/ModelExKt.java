package me.wcy.music.utils;

import android.net.Uri;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import me.wcy.music.common.bean.SongData;
import me.wcy.music.storage.db.entity.SongEntity;
import top.wangchenyan.common.CommonApp;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000D\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r\u001a\f\u0010\u000e\u001a\u0004\u0018\u00010\u0001*\u00020\u000f\u001a\n\u0010\u0010\u001a\u00020\r*\u00020\u000f\u001a\n\u0010\u0011\u001a\u00020\u000b*\u00020\u0012\u001a\n\u0010\u0011\u001a\u00020\u000b*\u00020\u000f\u001a\n\u0010\u0013\u001a\u00020\u0001*\u00020\u000f\u001a\n\u0010\u0014\u001a\u00020\u0001*\u00020\u000f\u001a\n\u0010\u0015\u001a\u00020\r*\u00020\u000f\u001a\n\u0010\u0016\u001a\u00020\u0001*\u00020\u0012\u001a\n\u0010\u0017\u001a\u00020\u0001*\u00020\u0018\u001a\n\u0010\u0019\u001a\u00020\u0001*\u00020\u0012\u001a\n\u0010\u001a\u001a\u00020\r*\u00020\u0012\u001a\n\u0010\u001b\u001a\u00020\u000b*\u00020\u0012\u001a\n\u0010\u001c\u001a\u00020\u001d*\u00020\u0012\u001a\u0012\u0010\u001e\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010 \u001a\u00020\u0001\u001a\u0012\u0010!\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010\"\u001a\u00020\r\u001a\u0012\u0010#\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010$\u001a\u00020\u000b\u001a\u0012\u0010%\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010&\u001a\u00020\u0001\u001a\u0012\u0010\'\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010 \u001a\u00020\u0001\u001a\u0012\u0010(\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010)\u001a\u00020\r\u001a\n\u0010*\u001a\u00020\u0012*\u00020\u0018\u001a\n\u0010*\u001a\u00020\u0012*\u00020+\u001a\n\u0010,\u001a\u00020+*\u00020\u0012\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"EXTRA_BASE_COVER", "", "EXTRA_DURATION", "EXTRA_FEE", "EXTRA_FILE_NAME", "EXTRA_FILE_PATH", "EXTRA_FILE_SIZE", "PARAM_ID", "SCHEME_NETEASE", "generateUniqueId", "type", "", "songId", "", "getBaseCover", "Landroidx/media3/common/MediaMetadata;", "getDuration", "getFee", "Landroidx/media3/common/MediaItem;", "getFileName", "getFilePath", "getFileSize", "getLargeCover", "getSimpleArtist", "Lme/wcy/music/common/bean/SongData;", "getSmallCover", "getSongId", "getSongType", "isLocal", "", "setBaseCover", "Landroidx/media3/common/MediaMetadata$Builder;", "value", "setDuration", "duration", "setFee", "fee", "setFileName", "name", "setFilePath", "setFileSize", "size", "toMediaItem", "Lme/wcy/music/storage/db/entity/SongEntity;", "toSongEntity", "app_debug"})
public final class ModelExKt {
    
    /**
     * Created by wangchenyan.top on 2023/9/18.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SCHEME_NETEASE = "netease";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PARAM_ID = "id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_DURATION = "duration";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_FILE_PATH = "file_path";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_FILE_NAME = "file_name";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_FILE_SIZE = "file_size";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_BASE_COVER = "base_cover";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_FEE = "fee";
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getSimpleArtist(@org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.SongData $this$getSimpleArtist) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.media3.common.MediaItem toMediaItem(@org.jetbrains.annotations.NotNull()
    me.wcy.music.storage.db.entity.SongEntity $this$toMediaItem) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.storage.db.entity.SongEntity toSongEntity(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem $this$toSongEntity) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.media3.common.MediaItem toMediaItem(@org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.SongData $this$toMediaItem) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String generateUniqueId(int type, long songId) {
        return null;
    }
    
    public static final boolean isLocal(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem $this$isLocal) {
        return false;
    }
    
    public static final int getSongType(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem $this$getSongType) {
        return 0;
    }
    
    public static final long getSongId(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem $this$getSongId) {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.media3.common.MediaMetadata.Builder setDuration(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaMetadata.Builder $this$setDuration, long duration) {
        return null;
    }
    
    public static final long getDuration(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaMetadata $this$getDuration) {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.media3.common.MediaMetadata.Builder setFilePath(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaMetadata.Builder $this$setFilePath, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getFilePath(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaMetadata $this$getFilePath) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.media3.common.MediaMetadata.Builder setFileName(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaMetadata.Builder $this$setFileName, @org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getFileName(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaMetadata $this$getFileName) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.media3.common.MediaMetadata.Builder setFileSize(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaMetadata.Builder $this$setFileSize, long size) {
        return null;
    }
    
    public static final long getFileSize(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaMetadata $this$getFileSize) {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.media3.common.MediaMetadata.Builder setBaseCover(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaMetadata.Builder $this$setBaseCover, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final java.lang.String getBaseCover(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaMetadata $this$getBaseCover) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.media3.common.MediaMetadata.Builder setFee(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaMetadata.Builder $this$setFee, int fee) {
        return null;
    }
    
    public static final int getFee(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaMetadata $this$getFee) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getSmallCover(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem $this$getSmallCover) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getLargeCover(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem $this$getLargeCover) {
        return null;
    }
    
    public static final int getFee(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem $this$getFee) {
        return 0;
    }
}