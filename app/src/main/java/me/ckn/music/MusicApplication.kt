/**
 * WhisperPlay Music Player
 *
 * 文件描述：应用程序的入口点，负责初始化全局服务和配置。
 * File Description: The entry point of the application, responsible for initializing global services and configurations.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.blankj.utilcode.util.ActivityUtils
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.HiltAndroidApp
import me.ckn.music.account.service.UserService
import me.ckn.music.common.DarkModeService
import me.ckn.music.common.MusicFragmentContainerActivity
import me.ckn.music.service.MusicService
import me.ckn.music.service.PlayServiceModule
import me.ckn.music.service.likesong.LikeSongProcessor
import me.wcy.router.CRouter
import me.wcy.router.RouterClient
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.ext.findActivity
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import me.ckn.music.service.AutoCacheCleanService
import me.ckn.music.storage.preference.ConfigPreferences
import me.ckn.music.utils.SmartCacheManager
import me.ckn.music.utils.LogUtils

/**
 * WhisperPlay 应用
 * WhisperPlay Application
 *
 * 主要功能：
 * Main Functions:
 * - 初始化Hilt依赖注入 / Initialize Hilt dependency injection
 * - 初始化通用模块 (CommonApp) / Initialize common modules (CommonApp)
 * - 初始化CRouter路由 / Initialize CRouter
 * - 初始化深色模式服务 / Initialize DarkModeService
 * - 初始化喜欢歌曲处理器 / Initialize LikeSongProcessor
 * - 跟踪应用生命周期并执行自动缓存清理 / Track application lifecycle and perform automatic cache cleaning
 *
 * 使用示例：
 * Usage Example:
 * ```xml
 * <application
 *     android:name=".MusicApplication"
 *     ... >
 * </application>
 * ```
 *
 * @author ckn
 * @since 2025-06-10
 */
@HiltAndroidApp
class MusicApplication : Application() {
    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var darkModeService: DarkModeService

    @Inject
    lateinit var likeSongProcessor: LikeSongProcessor

    // 应用级协程作用域，用于生命周期相关的异步操作
    private val applicationScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // 应用生命周期跟踪器
    private lateinit var appLifecycleTracker: AppLifecycleTracker

    companion object {
        private const val PREFS_NAME = "app_launch_state"
        private const val KEY_LAST_PAUSE_TIME = "last_pause_time"
    }

    override fun onCreate() {
        super.onCreate()

        CommonApp.init {
            test = BuildConfig.DEBUG
            isDarkMode = { darkModeService.isDarkMode() }
            titleLayoutConfig {
                isStatusBarDarkFontWhenAuto = { darkModeService.isDarkMode().not() }
                textColorAuto = { R.color.common_text_h1_color }
                textColorBlack = { R.color.common_text_h1_color }
                isTitleCenter = false
            }
            imageLoaderConfig {
                placeholderAvatar = R.drawable.ic_launcher_round
            }
            apiConfig({}) {
                codeJsonNames = listOf("code")
                msgJsonNames = listOf("message", "msg")
                dataJsonNames = listOf("data", "result")
                successCode = 200
            }
        }
        initCRouter()
        darkModeService.init()
        likeSongProcessor.init()
        
        // 初始化应用生命周期跟踪器，用于监听应用退出并执行自动清理
        appLifecycleTracker = AppLifecycleTracker(applicationScope)

        // 注册应用生命周期监听，用于判断启动页显示逻辑和自动清理
        registerActivityLifecycleCallbacks(AppLifecycleCallbacks())
        registerActivityLifecycleCallbacks(appLifecycleTracker)

        val sessionToken =
            SessionToken(this, ComponentName(this, MusicService::class.java))
        val mediaControllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        mediaControllerFuture.addListener({
            val player = mediaControllerFuture.get()
            PlayServiceModule.setPlayer(player)
        }, MoreExecutors.directExecutor())
    }

    private fun initCRouter() {
        CRouter.setRouterClient(
            RouterClient.Builder()
                .baseUrl("app://whisperplay")  // 修改为whisperplay
                .loginProvider { context, callback ->
                    var activity = context.findActivity()
                    if (activity == null) {
                        activity = ActivityUtils.getTopActivity()
                    }
                    if (activity != null) {
                        userService.checkLogin(activity) {
                            callback()
                        }
                    }
                }
                .fragmentContainerIntentProvider {
                    Intent(it, MusicFragmentContainerActivity::class.java)
                }
                .build()
        )
    }

    /**
     * 应用生命周期监听器
     * 用于记录应用进入后台的时间，判断是否需要显示启动页
     */
    inner class AppLifecycleCallbacks : ActivityLifecycleCallbacks {
        private var activityReferences = 0
        private var isActivityChangingConfigurations = false

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

        override fun onActivityStarted(activity: Activity) {
            if (++activityReferences == 1 && !isActivityChangingConfigurations) {
                // 应用从后台回到前台
                // 这里不需要特殊处理，启动页逻辑在SplashActivity中处理
            }
        }

        override fun onActivityResumed(activity: Activity) {}

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStopped(activity: Activity) {
            isActivityChangingConfigurations = activity.isChangingConfigurations
            if (--activityReferences == 0 && !isActivityChangingConfigurations) {
                // 应用进入后台，记录时间
                recordAppPauseTime()
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {}

        private fun recordAppPauseTime() {
            val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit()
                .putLong(KEY_LAST_PAUSE_TIME, System.currentTimeMillis())
                .apply()
        }
    }

    /**
     * 应用生命周期跟踪器
     * 负责监听应用真正退出的时机，并在退出时执行自动清理
     *
     * 设计思路：
     * 1. 跟踪活跃Activity数量
     * 2. 当所有Activity都被销毁且应用进入后台时，启动退出检测
     * 3. 延迟一定时间后确认应用真正退出，执行清理操作
     * 4. 避免频繁的前后台切换触发不必要的清理
     */
    private class AppLifecycleTracker(
        private val scope: CoroutineScope
    ) : Application.ActivityLifecycleCallbacks {

        // 当前活跃的Activity数量
        private var activeActivityCount = 0

        // 应用是否在前台
        private var isAppInForeground = false

        // 退出检测任务
        private var exitDetectionJob: kotlinx.coroutines.Job? = null

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            // Activity创建时不需要特殊处理
        }

        override fun onActivityStarted(activity: Activity) {
            activeActivityCount++

            // 如果之前应用在后台，现在有Activity启动，说明应用回到前台
            if (!isAppInForeground) {
                isAppInForeground = true
                // 取消之前的退出检测
                exitDetectionJob?.cancel()
                exitDetectionJob = null
                LogUtils.d("AppLifecycle", "应用回到前台，取消退出检测")
            }
        }

        override fun onActivityResumed(activity: Activity) {
            // Activity恢复时不需要特殊处理
        }

        override fun onActivityPaused(activity: Activity) {
            // Activity暂停时不需要特殊处理
        }

        override fun onActivityStopped(activity: Activity) {
            activeActivityCount--

            // 当没有活跃Activity时，应用进入后台
            if (activeActivityCount <= 0) {
                isAppInForeground = false
                startExitDetection()
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            // 保存状态时不需要特殊处理
        }

        override fun onActivityDestroyed(activity: Activity) {
            // Activity销毁时不需要特殊处理，因为onActivityStopped已经处理了计数
        }

        /**
         * 启动退出检测
         * 延迟一定时间后检查应用是否真正退出，如果是则执行自动清理
         */
        private fun startExitDetection() {
            // 取消之前的检测任务
            exitDetectionJob?.cancel()

            exitDetectionJob = scope.launch {
                try {
                    // 延迟5秒，避免快速切换应用时误触发清理
                    kotlinx.coroutines.delay(5000)

                    // 再次检查应用状态
                    if (!isAppInForeground && activeActivityCount <= 0) {
                        LogUtils.i("AppLifecycle", "检测到应用真正退出，开始执行自动清理")
                        performAppExitCleanup()
                    }
                } catch (e: kotlinx.coroutines.CancellationException) {
                    // 任务被取消，说明应用重新回到前台
                    LogUtils.d("AppLifecycle", "退出检测被取消")
                }
            }
        }

        /**
         * 执行应用退出时的自动清理
         */
        private suspend fun performAppExitCleanup() {
            try {
                // 检查是否启用了应用退出时自动清理
                if (ConfigPreferences.isAppExitAutoCacheCleanEnabled()) {
                    LogUtils.i("AppLifecycle", "开始执行应用退出时自动清理")

                    val context = CommonApp.app
                    val smartCacheManager = SmartCacheManager(context)

                    // 获取清理前的缓存信息
                    val beforeStats = smartCacheManager.getCacheStats()

                    // 执行清理
                    val cleanSuccess = smartCacheManager.clearCache()

                    if (cleanSuccess) {
                        // 获取清理后的缓存信息
                        val afterStats = smartCacheManager.getCacheStats()
                        val freedSpace = beforeStats.totalSize - afterStats.totalSize

                        LogUtils.i("AppLifecycle", "应用退出时自动清理完成，释放空间: ${formatFileSize(freedSpace)}")

                        // 更新最后清理时间
                        ConfigPreferences.setLastAutoCacheCleanTime(System.currentTimeMillis())
                    } else {
                        LogUtils.w("AppLifecycle", "应用退出时自动清理失败")
                    }
                }
            } catch (e: Exception) {
                LogUtils.e("AppLifecycle", "应用退出时自动清理异常", e)
            }
        }

        /**
         * 格式化文件大小显示
         */
        private fun formatFileSize(bytes: Long): String {
            return when {
                bytes < 1024 -> "${bytes}B"
                bytes < 1024 * 1024 -> "${bytes / 1024}KB"
                bytes < 1024 * 1024 * 1024 -> "${bytes / (1024 * 1024)}MB"
                else -> "${bytes / (1024 * 1024 * 1024)}GB"
            }
        }
    }
}