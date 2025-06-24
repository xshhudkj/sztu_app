package me.ckn.music.storage.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import me.ckn.music.storage.db.dao.PlaylistDao
import me.ckn.music.storage.db.entity.SongEntity

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/8/29
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：应用数据库
 * File Description: Application database
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@Database(
    entities = [
        SongEntity::class,
    ],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun playlistDao(): PlaylistDao
}