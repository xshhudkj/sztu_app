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
import androidx.media3.datasource.DataSource
import com.blankj.utilcode.util.IntentUtils
import me.wcy.music.R
import me.wcy.music.net.datasource.MusicDataSource
import me.wcy.music.net.datasource.ModernMusicCacheDataSourceFactory
import me.wcy.music.service.AutomotiveMediaNotificationProvider
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
     * 创建现代音乐流媒体优化的LoadControl配置
     * 基于ExoPlayer最佳实践，针对音乐播放场景优化
     * 参考：Akamai、Pinterest等公司的音乐流媒体优化经验
     */
    private fun createOptimizedLoadControl(): DefaultLoadControl {
        return DefaultLoadControl.Builder()
            // 现代音乐流媒体缓存策略：快速启动 + 适度缓存
            .setBufferDurationsMs(
                // 最小缓存：3秒（保证基本播放连续性，避免频繁重新缓冲）
                3_000,
                // 最大缓存：20秒（音乐流媒体不需要过长缓存，避免内存浪费）
                20_000,
                // 起播缓存：1.5秒（现代音乐应用标准，快速启动体验）
                1_500,
                // 重新缓冲后起播：2秒（网络恢复后快速恢复播放）
                2_000
            )
            // 目标缓存字节数：3MB（音频文件相对较小，3MB足够缓存多首歌曲片段）
            .setTargetBufferBytes(3 * 1024 * 1024)
            // 分配器配置：64KB块大小，适合音频流的特点，提高内存利用效率
            .setAllocator(DefaultAllocator(true, 64 * 1024))
            // 优先时间阈值：当缓存时间不足时优先保证时间而非大小
            .setPrioritizeTimeOverSizeThresholds(true)
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