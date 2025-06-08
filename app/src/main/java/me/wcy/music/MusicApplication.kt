package me.wcy.music

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
import me.wcy.music.account.service.UserService
import me.wcy.music.common.DarkModeService
import me.wcy.music.common.MusicFragmentContainerActivity
import me.wcy.music.service.MusicService
import me.wcy.music.service.PlayServiceModule
import me.wcy.music.service.likesong.LikeSongProcessor
import me.wcy.router.CRouter
import me.wcy.router.RouterClient
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.ext.findActivity
import javax.inject.Inject

/**
 * 自定义Application
 * Created by wcy on 2015/11/27.
 */
@HiltAndroidApp
class MusicApplication : Application() {
    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var darkModeService: DarkModeService

    @Inject
    lateinit var likeSongProcessor: LikeSongProcessor

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
        
        // 注册应用生命周期监听，用于判断启动页显示逻辑
        registerActivityLifecycleCallbacks(AppLifecycleCallbacks())

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
                .baseUrl("app://music")
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
}