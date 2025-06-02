package me.wcy.music.service

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
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
import com.blankj.utilcode.util.IntentUtils
import me.wcy.music.R
import me.wcy.music.net.datasource.MusicDataSource
import me.wcy.music.utils.MusicUtils
import top.wangchenyan.common.CommonApp

/**
 * Created by wangchenyan.top on 2024/3/26.
 */
class MusicService : MediaSessionService() {
    private lateinit var player: Player
    private lateinit var session: MediaSession

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "MusicService onCreate() - 初始化Android Automotive音乐服务")

        @OptIn(UnstableApi::class)
        player = ExoPlayer.Builder(applicationContext)
            // 自动处理音频焦点
            .setAudioAttributes(AudioAttributes.DEFAULT, true)
            // 自动暂停播放
            .setHandleAudioBecomingNoisy(true)
            // 设置优化的LoadControl配置，支持智能预加载
            .setLoadControl(createOptimizedLoadControl())
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(applicationContext)
                    .setDataSourceFactory(MusicDataSource.Factory(applicationContext))
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

        // 根据系统类型配置通知栏
        setupNotificationProvider()
        Log.d(TAG, "MusicService onCreate() - 通知栏配置完成")
    }

    /**
     * 根据系统类型配置通知栏
     * Android Automotive系统使用专用的AutomotiveMediaNotificationProvider
     * 手机Android系统使用原始的DefaultMediaNotificationProvider
     */
    @OptIn(UnstableApi::class)
    private fun setupNotificationProvider() {
        val isAutomotive = MusicUtils.isAndroidAutomotive(applicationContext)
        Log.d(TAG, "setupNotificationProvider() - 检测到系统类型: ${if (isAutomotive) "Android Automotive" else "手机Android"}")

        if (isAutomotive) {
            setupAutomotiveNotification()
        } else {
            setupPhoneNotification()
        }
    }

    /**
     * 配置Android Automotive专用的通知栏
     * 确保通知栏在车载环境下的稳定显示和一致性
     */
    @OptIn(UnstableApi::class)
    private fun setupAutomotiveNotification() {
        try {
            // 使用自定义的AutomotiveMediaNotificationProvider
            // 解决Android Automotive环境下通知栏背景颜色不稳定的问题
            val automotiveNotificationProvider = AutomotiveMediaNotificationProvider(applicationContext)
            setMediaNotificationProvider(automotiveNotificationProvider)
            Log.d(TAG, "setupAutomotiveNotification() - Android Automotive专用通知栏配置成功")
        } catch (e: Exception) {
            Log.e(TAG, "setupAutomotiveNotification() - 通知栏配置失败，回退到默认配置", e)
            // 回退到默认配置
            setupPhoneNotification()
        }
    }

    /**
     * 配置手机Android系统的原始通知栏
     * 使用标准的DefaultMediaNotificationProvider，适配手机系统
     */
    @OptIn(UnstableApi::class)
    private fun setupPhoneNotification() {
        try {
            // 使用原始的DefaultMediaNotificationProvider，适配手机Android系统
            val defaultProvider = DefaultMediaNotificationProvider.Builder(applicationContext)
                .setChannelId("music_player_channel")
                .setChannelName(R.string.app_name)
                .build()
                .apply {
                    setSmallIcon(R.drawable.ic_notification)
                }
            setMediaNotificationProvider(defaultProvider)
            Log.d(TAG, "setupPhoneNotification() - 手机Android原始通知栏配置成功")
        } catch (e: Exception) {
            Log.e(TAG, "setupPhoneNotification() - 手机通知栏配置失败", e)
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return session
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d(TAG, "onTaskRemoved() - 任务被移除，播放状态: ${player.playWhenReady}")
        if (!player.playWhenReady) {
            Log.d(TAG, "onTaskRemoved() - 停止播放并停止服务")
            player.stop()
            stopSelf()
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy() - 销毁MusicService")
        super.onDestroy()
        player.release()
        session.release()
    }

    /**
     * 创建优化的LoadControl配置，平衡性能与用户体验
     */
    private fun createOptimizedLoadControl(): DefaultLoadControl {
        return DefaultLoadControl.Builder()
            // 设置缓存参数，优化车载环境性能
            .setBufferDurationsMs(
                // 最小缓存时间：15秒，保证基本播放连续性
                15_000,
                // 最大缓存时间：60秒，避免过度缓存影响性能
                60_000,
                // 播放开始前的缓存时间：1.6秒，更快启动播放
                1_600,
                // 重新缓存时的缓存时间：8秒，网络恢复后快速恢复
                8_000
            )
            // 设置目标缓存字节数，控制内存使用
            .setTargetBufferBytes(
                // 最大缓存：8MB，平衡性能与内存使用
                8 * 1024 * 1024
            )
            // 优先基于时间的缓存策略，适合音频流
            .setPrioritizeTimeOverSizeThresholds(true)
            // 设置分配器，优化内存分配
            .setAllocator(DefaultAllocator(true, 64 * 1024)) // 64KB块大小
            .build()
    }

    companion object {
        private const val TAG = "MusicService"
        val EXTRA_NOTIFICATION = "${CommonApp.app.packageName}.notification"
    }
}