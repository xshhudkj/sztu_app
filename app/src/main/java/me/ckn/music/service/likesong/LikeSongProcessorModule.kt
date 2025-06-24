package me.ckn.music.service.likesong

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.ckn.music.ext.accessEntryPoint

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2024/3/21
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：喜欢歌曲处理器模块
 * File Description: Like song processor module
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class LikeSongProcessorModule {

    @Binds
    abstract fun bindLikeSongProcessor(
        likeSongProcessor: LikeSongProcessorImpl
    ): LikeSongProcessor

    companion object {
        fun Application.audioPlayer(): LikeSongProcessor {
            return accessEntryPoint<LikeSongProcessorEntryPoint>().likeSongProcessor()
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface LikeSongProcessorEntryPoint {
        fun likeSongProcessor(): LikeSongProcessor
    }
}