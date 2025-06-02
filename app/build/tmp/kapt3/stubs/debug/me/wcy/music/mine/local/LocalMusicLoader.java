package me.wcy.music.mine.local;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import me.wcy.music.storage.db.entity.SongEntity;
import me.wcy.music.storage.preference.ConfigPreferences;

/**
 * Created by wangchenyan.top on 2023/8/30.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\fR\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lme/wcy/music/mine/local/LocalMusicLoader;", "", "()V", "projection", "", "", "[Ljava/lang/String;", "sortOrder", "load", "", "Lme/wcy/music/storage/db/entity/SongEntity;", "context", "Landroid/content/Context;", "app_debug"})
public final class LocalMusicLoader {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String[] projection = {"_id", "is_music", "title", "artist", "album", "album_id", "_data", "_display_name", "_size", "duration"};
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sortOrder = "date_modified DESC";
    
    public LocalMusicLoader() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<me.wcy.music.storage.db.entity.SongEntity> load(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
}