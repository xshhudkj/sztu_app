package me.wcy.music.service

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.Player
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.wcy.music.ext.accessEntryPoint
import me.wcy.music.service.likesong.LikeSongProcessor
import me.wcy.music.storage.db.MusicDatabase
import top.wangchenyan.common.ext.toUnMutable

/**
 * Created by wangchenyan.top on 2024/3/26.
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
        return playerController ?: run {
            val player = player ?: run {
                // 如果播放器还没准备好，等待一下再尝试
                android.util.Log.w("PlayServiceModule", "Player not ready yet, waiting...")
                Thread.sleep(100) // 等待100ms
                this.player ?: throw IllegalStateException("Player not prepared after waiting!")
            }
            PlayerControllerImpl(player, db).also {
                playerController = it
                android.util.Log.d("PlayServiceModule", "PlayerController created successfully")
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
