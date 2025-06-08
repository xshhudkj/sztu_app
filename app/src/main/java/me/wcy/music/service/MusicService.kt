package me.wcy.music.service

import android.app.PendingIntent
import android.content.Intent
import me.wcy.music.utils.LogUtils
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
import me.wcy.music.R
import me.wcy.music.net.datasource.MusicDataSource
import me.wcy.music.net.datasource.ModernMusicCacheDataSourceFactory
import me.wcy.music.service.AutomotiveMediaNotificationProvider
import me.wcy.music.utils.MusicUtils
import me.wcy.music.utils.FirstPlayOptimizer
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
        LogUtils.i(TAG, "MusicService onCreate() - 初始化Android Automotive音乐服务")

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

        // 根据系统类型配置通知栏
        setupNotificationProvider()
        LogUtils.i(TAG, "MusicService onCreate() - 通知栏配置完成")
    }

    /**
     * 根据系统类型配置通知栏
     * Android Automotive系统使用专用的AutomotiveMediaNotificationProvider
     * 手机Android系统使用原始的DefaultMediaNotificationProvider
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
     * 确保通知栏在车载环境下的稳定显示和一致性
     */
    @OptIn(UnstableApi::class)
    private fun setupAutomotiveNotification() {
        try {
            // 使用自定义的AutomotiveMediaNotificationProvider
            // 解决Android Automotive环境下通知栏背景颜色不稳定的问题
            val automotiveNotificationProvider = AutomotiveMediaNotificationProvider(applicationContext)
            setMediaNotificationProvider(automotiveNotificationProvider)
            LogUtils.i(TAG, "setupAutomotiveNotification() - Android Automotive专用通知栏配置成功")
        } catch (e: Exception) {
            LogUtils.e(TAG, "setupAutomotiveNotification() - 通知栏配置失败，回退到默认配置", e)
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
            LogUtils.i(TAG, "setupPhoneNotification() - 手机Android原始通知栏配置成功")
        } catch (e: Exception) {
            LogUtils.e(TAG, "setupPhoneNotification() - 手机通知栏配置失败", e)
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return session
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        LogUtils.i(TAG, "onTaskRemoved() - 任务被移除，播放状态: ${player.playWhenReady}")
        if (!player.playWhenReady) {
            LogUtils.i(TAG, "onTaskRemoved() - 停止播放并停止服务")
            player.stop()
            stopSelf()
        }
    }

    override fun onDestroy() {
        LogUtils.i(TAG, "onDestroy() - 销毁MusicService")
        super.onDestroy()
        player.release()
        session.release()
    }

    /**
     * 创建极速启动优化的LoadControl配置
     * 🔥 使用FirstPlayOptimizer的超激进配置，专门解决用户反馈的首次播放慢问题
     * 目标：实现500ms内播放启动，最大化响应速度
     */
    private fun createOptimizedLoadControl(): DefaultLoadControl {
        // 获取首次播放优化器的建议配置
        val firstPlayOptimizer = FirstPlayOptimizer(applicationContext)
        val config = firstPlayOptimizer.getOptimalLoadControlConfig()
        
        LogUtils.i(TAG, "createOptimizedLoadControl() - 应用超激进LoadControl配置: 起播缓存=${config.bufferForPlaybackMs}ms, 最小缓存=${config.minBufferMs}ms")
        
        return DefaultLoadControl.Builder()
            // 🔥 超激进缓存策略：最大化首次播放启动速度
            .setBufferDurationsMs(
                config.minBufferMs,        // 500ms最小缓存（更激进）
                config.maxBufferMs,        // 8秒最大缓存（更激进）
                config.bufferForPlaybackMs, // 200ms起播缓存（极度激进）
                config.bufferForPlaybackAfterRebufferMs // 500ms重新缓冲起播（激进）
            )
            // 目标缓存字节数：1MB（更激进的缓存大小）
            .setTargetBufferBytes(config.targetBufferBytes)
            // 分配器配置：16KB块大小，更小的块提高分配效率和响应速度
            .setAllocator(DefaultAllocator(true, 16 * 1024))
            // 优先时间阈值：极度激进的时间优先策略
            .setPrioritizeTimeOverSizeThresholds(config.prioritizeTimeOverSizeThresholds)
            .build()
    }

    /**
     * 创建现代音乐流媒体数据源工厂
     * 集成磁盘缓存和网络优化，提供最佳的音乐播放体验
     */
    private fun createModernDataSourceFactory(): DataSource.Factory {
        // 使用现代缓存数据源工厂，集成磁盘缓存
        val cacheDataSourceFactory = ModernMusicCacheDataSourceFactory.getCacheDataSourceFactory(applicationContext)

        // 包装为MusicDataSource.Factory以支持自定义URI方案（如netease://）
        return MusicDataSource.Factory(applicationContext, cacheDataSourceFactory)
    }

    companion object {
        private const val TAG = "MusicService"
        val EXTRA_NOTIFICATION = "${CommonApp.app.packageName}.notification"
    }
}