/**
 * WhisperPlay Music Player
 *
 * 文件描述：音乐播放后台服务，负责管理媒体会话和播放器生命周期。
 * File Description: Background service for music playback, managing the media session and player lifecycle.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.service

import android.app.PendingIntent
import android.content.Intent
import me.ckn.music.utils.LogUtils
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.upstream.DefaultAllocator
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.datasource.DataSource
import com.blankj.utilcode.util.IntentUtils
import me.ckn.music.R
import me.ckn.music.net.datasource.MusicDataSource
import me.ckn.music.net.datasource.ModernMusicCacheDataSourceFactory
import me.ckn.music.service.AutomotiveMediaNotificationProvider
import me.ckn.music.utils.MusicUtils
import me.ckn.music.utils.FirstPlayOptimizer
import top.wangchenyan.common.CommonApp

/**
 * 音乐播放服务
 * Music Playback Service
 *
 * 主要功能：
 * Main Functions:
 * - 管理ExoPlayer实例和MediaSession的生命周期 / Manages the lifecycle of the ExoPlayer instance and MediaSession.
 * - 根据系统环境（手机或车载）配置媒体通知 / Configures media notifications based on the system environment (phone or automotive).
 * - 提供优化的媒体加载和数据源配置 / Provides optimized media loading and data source configurations.
 *
 * @author ckn
 */
class MusicService : MediaSessionService() {
    private lateinit var player: Player
    private lateinit var session: MediaSession

    /**
     * 服务创建时的回调
     * Initializes the service, player, and media session.
     */
    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        LogUtils.i(TAG, "MusicService onCreate() - 初始化Android Automotive音乐服务")

        @OptIn(UnstableApi::class)
        player = ExoPlayer.Builder(applicationContext)
            .setAudioAttributes(AudioAttributes.DEFAULT, true)
            .setHandleAudioBecomingNoisy(true)
            .setLoadControl(createOptimizedLoadControl())
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(applicationContext)
                    .setDataSourceFactory(createModernDataSourceFactory())
            )
            .build()

        session = MediaSession.Builder(this, player)
            .setSessionActivity(
                PendingIntent.getActivity(
                    this,
                    0,
                    IntentUtils.getLaunchAppIntent(packageName).apply {
                        putExtra(EXTRA_NOTIFICATION, true)
                        action = Intent.ACTION_VIEW
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    },
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
            .build()

        setupNotificationProvider()
        LogUtils.i(TAG, "MusicService onCreate() - 通知栏配置完成")
    }

    /**
     * 根据系统类型配置通知栏
     * Configures media notifications based on the system type.
     */
    @OptIn(UnstableApi::class)
    private fun setupNotificationProvider() {
        val isAutomotive = MusicUtils.isAndroidAutomotive(applicationContext)
        LogUtils.i(TAG, "setupNotificationProvider() - 检测到系统类型: ${if (isAutomotive) "Android Automotive" else "手机Android"}")

        if (isAutomotive) {
            setupAutomotiveNotification()
        } else {
            setupPhoneNotification()
        }
    }

    /**
     * 配置Android Automotive专用的通知栏
     * Configures notifications specifically for Android Automotive.
     */
    @OptIn(UnstableApi::class)
    private fun setupAutomotiveNotification() {
        try {
            val automotiveNotificationProvider = AutomotiveMediaNotificationProvider(applicationContext)
            setMediaNotificationProvider(automotiveNotificationProvider)
            LogUtils.i(TAG, "setupAutomotiveNotification() - Android Automotive专用通知栏配置成功")
        } catch (e: Exception) {
            LogUtils.e(TAG, "setupAutomotiveNotification() - 通知栏配置失败，回退到默认配置", e)
            setupPhoneNotification()
        }
    }

    /**
     * 配置手机Android系统的原始通知栏
     * Configures standard notifications for regular Android phones.
     */
    @OptIn(UnstableApi::class)
    private fun setupPhoneNotification() {
        try {
            val defaultProvider = DefaultMediaNotificationProvider.Builder(applicationContext)
                .setChannelId("music_player_channel")
                .setChannelName(R.string.app_name)
                .build()
                .apply {
                    setSmallIcon(R.drawable.ic_notification)
                }
            setMediaNotificationProvider(defaultProvider)
            LogUtils.i(TAG, "setupPhoneNotification() - 手机Android原始通知栏配置成功")
        } catch (e: Exception) {
            LogUtils.e(TAG, "setupPhoneNotification() - 手机通知栏配置失败", e)
        }
    }

    /**
     * 返回媒体会话实例
     * Returns the media session instance.
     * @param controllerInfo 控制器信息 / Controller information.
     * @return MediaSession实例 / The MediaSession instance.
     */
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return session
    }

    /**
     * 当任务被移除时的回调
     * Callback for when the task is removed.
     * @param rootIntent 根Intent / The root Intent.
     */
    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        LogUtils.i(TAG, "onTaskRemoved() - 任务被移除，播放状态: ${player.playWhenReady}")
        if (!player.playWhenReady) {
            LogUtils.i(TAG, "onTaskRemoved() - 停止播放并停止服务")
            player.stop()
            stopSelf()
        }
    }

    /**
     * 服务销毁时的回调
     * Cleans up resources when the service is destroyed.
     */
    override fun onDestroy() {
        LogUtils.i(TAG, "onDestroy() - 销毁MusicService")
        super.onDestroy()
        player.release()
        session.release()
    }

    /**
     * 创建优化的LoadControl配置
     * Creates an optimized LoadControl configuration.
     * @return DefaultLoadControl实例 / A DefaultLoadControl instance.
     */
    private fun createOptimizedLoadControl(): DefaultLoadControl {
        val firstPlayOptimizer = FirstPlayOptimizer(applicationContext)
        val config = firstPlayOptimizer.getOptimalLoadControlConfig()
        
        LogUtils.i(TAG, "createOptimizedLoadControl() - 应用超激进LoadControl配置: 起播缓存=${config.bufferForPlaybackMs}ms, 最小缓存=${config.minBufferMs}ms")
        
        return DefaultLoadControl.Builder()
            .setBufferDurationsMs(
                config.minBufferMs,
                config.maxBufferMs,
                config.bufferForPlaybackMs,
                config.bufferForPlaybackAfterRebufferMs
            )
            .setTargetBufferBytes(config.targetBufferBytes)
            .setAllocator(DefaultAllocator(true, 16 * 1024))
            .setPrioritizeTimeOverSizeThresholds(config.prioritizeTimeOverSizeThresholds)
            .build()
    }

    /**
     * 创建现代化的数据源工厂
     * Creates a modern data source factory.
     * @return DataSource.Factory实例 / A DataSource.Factory instance.
     */
    private fun createModernDataSourceFactory(): DataSource.Factory {
        val cacheDataSourceFactory = ModernMusicCacheDataSourceFactory.getCacheDataSourceFactory(applicationContext)
        return MusicDataSource.Factory(applicationContext, cacheDataSourceFactory)
    }

    companion object {
        private const val TAG = "MusicService"
        val EXTRA_NOTIFICATION = "${CommonApp.app.packageName}.notification"
    }
}