package me.wcy.music.storage.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import me.wcy.music.storage.db.entity.SongEntity;

/**
 * Created by wangchenyan.top on 2023/8/29.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\'J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0016\u0010\b\u001a\u00020\u00032\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\'J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\'J\u0012\u0010\f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00020\u000eH\'\u00a8\u0006\u000f"}, d2 = {"Lme/wcy/music/storage/db/dao/PlaylistDao;", "", "clear", "", "delete", "entity", "Lme/wcy/music/storage/db/entity/SongEntity;", "insert", "insertAll", "list", "", "queryAll", "queryByUniqueId", "uniqueId", "", "app_debug"})
@androidx.room.Dao()
public abstract interface PlaylistDao {
    
    @androidx.room.Insert(onConflict = 1)
    public abstract void insert(@org.jetbrains.annotations.NotNull()
    me.wcy.music.storage.db.entity.SongEntity entity);
    
    @androidx.room.Insert(onConflict = 1)
    public abstract void insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<me.wcy.music.storage.db.entity.SongEntity> list);
    
    @androidx.room.Query(value = "SELECT * FROM play_list")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<me.wcy.music.storage.db.entity.SongEntity> queryAll();
    
    @androidx.room.Query(value = "SELECT * FROM play_list WHERE unique_id = :uniqueId")
    @org.jetbrains.annotations.Nullable()
    public abstract me.wcy.music.storage.db.entity.SongEntity queryByUniqueId(@org.jetbrains.annotations.NotNull()
    java.lang.String uniqueId);
    
    @androidx.room.Delete()
    public abstract void delete(@org.jetbrains.annotations.NotNull()
    me.wcy.music.storage.db.entity.SongEntity entity);
    
    @androidx.room.Query(value = "DELETE FROM play_list")
    public abstract void clear();
}