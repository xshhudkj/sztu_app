package me.ckn.music.storage.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.ckn.music.storage.db.entity.SongEntity

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/8/29
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：播放列表 DAO
 * File Description: Playlist DAO
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: SongEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SongEntity>)

    @Query("SELECT * FROM play_list")
    fun queryAll(): List<SongEntity>

    @Query("SELECT * FROM play_list WHERE unique_id = :uniqueId")
    fun queryByUniqueId(uniqueId: String): SongEntity?

    @Delete
    fun delete(entity: SongEntity)

    @Query("DELETE FROM play_list")
    fun clear()
}