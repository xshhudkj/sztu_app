package me.ckn.music.service

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.Player
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.ckn.music.ext.accessEntryPoint
import me.ckn.music.service.likesong.LikeSongProcessor
import me.ckn.music.storage.db.MusicDatabase
import top.wangchenyan.common.ext.toUnMutable

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2024/3/26
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：播放服务模块
 * File Description: Play service module
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@Module
@InstallIn(SingletonComponent::class)
object PlayServiceModule {
    private var player: Player? = null
    private var playerController: PlayerController? = null

    private val _isPlayerReady = MutableLiveData(false)
    val isPlayerReady = _isPlayerReady.toUnMutable()

    fun setPlayer(player: Player) {
        this.player = player
        _isPlayerReady.value = true
    }

    @Provides
    fun providerPlayerController(db: MusicDatabase): PlayerController {
        return playerController ?: synchronized(this) {
            // 双重检查锁定模式
            playerController ?: run {
                val player = player ?: run {
                    // 等待播放器准备，但不阻塞太久
                    android.util.Log.w("PlayServiceModule", "Player not ready yet, waiting briefly...")
                    var retryCount = 0
                    val maxRetries = 5 // 只等待500ms
                    while (retryCount < maxRetries) {
                        this.player?.let { return@run it }
                        try {
                            Thread.sleep(100)
                        } catch (e: InterruptedException) {
                            Thread.currentThread().interrupt()
                            break
                        }
                        retryCount++
                    }
                    this.player ?: throw IllegalStateException("Player not prepared after ${maxRetries * 100}ms! Please restart the app.")
                }
                PlayerControllerImpl(player, db).also {
                    playerController = it
                    android.util.Log.d("PlayServiceModule", "PlayerController created successfully")
                }
            }
        }
    }

    fun Application.playerController(): PlayerController {
        return accessEntryPoint<PlayerControllerEntryPoint>().playerController()
    }

    fun Application.likeSongProcessor(): LikeSongProcessor {
        return accessEntryPoint<LikeSongProcessorEntryPoint>().likeSongProcessor()
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface PlayerControllerEntryPoint {
        fun playerController(): PlayerController
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface LikeSongProcessorEntryPoint {
        fun likeSongProcessor(): LikeSongProcessor
    }
}
