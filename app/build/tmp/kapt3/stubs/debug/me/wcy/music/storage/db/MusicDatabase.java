package me.wcy.music.storage.db;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import me.wcy.music.storage.db.dao.PlaylistDao;
import me.wcy.music.storage.db.entity.SongEntity;

/**
 * Created by wangchenyan.top on 2023/8/29.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0005"}, d2 = {"Lme/wcy/music/storage/db/MusicDatabase;", "Landroidx/room/RoomDatabase;", "()V", "playlistDao", "Lme/wcy/music/storage/db/dao/PlaylistDao;", "app_debug"})
@androidx.room.Database(entities = {me.wcy.music.storage.db.entity.SongEntity.class}, version = 2, autoMigrations = {@androidx.room.AutoMigration(from = 1, to = 2)})
public abstract class MusicDatabase extends androidx.room.RoomDatabase {
    
    public MusicDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract me.wcy.music.storage.db.dao.PlaylistDao playlistDao();
}