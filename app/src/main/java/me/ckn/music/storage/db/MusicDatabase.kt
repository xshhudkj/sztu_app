package me.ckn.music.storage.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import me.ckn.music.storage.db.dao.PlaylistDao
import me.ckn.music.storage.db.entity.SongEntity

/**
 * Created by wangchenyan.top on 2023/8/29.
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