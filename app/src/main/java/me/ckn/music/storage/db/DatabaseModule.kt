package me.ckn.music.storage.db

import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import top.wangchenyan.common.CommonApp

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/7/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：数据库模块
 * File Description: Database module
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(): MusicDatabase {
        return Room.databaseBuilder(
            CommonApp.app,
            MusicDatabase::class.java,
            "music_db"
        ).build()
    }
}