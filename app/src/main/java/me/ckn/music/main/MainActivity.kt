/**
 * WhisperPlay Music Player
 *
 * 文件描述：应用的主活动，承载核心UI和导航功能。
 * File Description: The main activity of the application, hosting the core UI and navigation features.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.main

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.ckn.music.R
import me.ckn.music.account.service.UserService
import me.ckn.music.common.ApiDomainDialog
import me.ckn.music.common.BaseMusicActivity
import me.ckn.music.common.ImmersiveDialogHelper
import me.ckn.music.consts.RoutePath
import me.ckn.music.databinding.ActivityMainBinding
import me.ckn.music.databinding.NavigationHeaderBinding
import me.ckn.music.databinding.TabItemBinding
import me.ckn.music.service.MusicService
import me.ckn.music.service.PlayServiceModule.playerController
import me.ckn.music.storage.preference.ConfigPreferences
import me.ckn.music.utils.QuitTimer
import me.ckn.music.utils.TimeUtils
import android.os.Handler
import android.os.Looper
import me.wcy.router.CRouter
import top.wangchenyan.common.ext.getColorEx
import me.ckn.music.common.showImmersiveConfirmDialog
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.ext.viewBindings
import top.wangchenyan.common.widget.pager.CustomTabPager
import javax.inject.Inject

/**
 * 主活动
 * Main Activity
 *
 * 主要功能：
 * Main Functions:
 * - 承载主要的UI界面，包括导航和ViewPager / Host the main UI, including navigation and ViewPager.
 * - 处理抽屉菜单的交互逻辑 / Handle drawer menu interactions.
 * - 管理定时退出功能 / Manage the quit timer.
 * - 检查并执行启动时自动播放 / Check and execute auto-play on startup.
 *
 * @author ckn
 */
@AndroidEntryPoint
class MainActivity : BaseMusicActivity() {
    private val viewBinding by viewBindings<ActivityMainBinding>()
    private val quitTimer by lazy {
        QuitTimer(onTimerListener)
    }
    private var timerItem: MenuItem? = null

    @Inject
    lateinit var userService: UserService

    /**
     * 活动创建时的回调
     * Initializes the activity, sets up the UI, and configures event listeners.
     * @param savedInstanceState 保存的实例状态 / Saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        CustomTabPager(lifecycle, supportFragmentManager, viewBinding.viewPager).apply {
            NaviTab.ALL.onEach {
                val tabItem = getTabItem(it.icon, it.name)
                addFragment(it.newFragment(), tabItem.root)
            }
            setScrollable(false)
            setup()
        }

        initDrawer()
        parseIntent()
        checkAndExecuteAutoPlay()

        configWindowInsets {
            navBarColor = getColorEx(R.color.tab_bg)
        }
    }

    /**
     * 处理新的Intent
     * Handles new intents, such as from notifications.
     * @param intent 新的Intent / The new Intent.
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        parseIntent()
    }

    /**
     * 初始化抽屉菜单
     * Initializes the navigation drawer.
     */
    private fun initDrawer() {
        val navigationHeaderBinding = NavigationHeaderBinding.inflate(
            LayoutInflater.from(this),
            viewBinding.navigationView,
            false
        )
        viewBinding.navigationView.addHeaderView(navigationHeaderBinding.root)
        viewBinding.navigationView.setNavigationItemSelectedListener(onMenuSelectListener)
        lifecycleScope.launch {
            userService.profile.collectLatest { profile ->
                val menuLogin = viewBinding.navigationView.menu.findItem(R.id.action_login)
                val menuLogout = viewBinding.navigationView.menu.findItem(R.id.action_logout)
                
                if (profile != null) {
                    menuLogin.isVisible = false
                    menuLogout.isVisible = true
                } else {
                    menuLogin.isVisible = true
                    menuLogout.isVisible = false
                }
            }
        }
    }

    /**
     * 打开抽屉菜单
     * Opens the navigation drawer.
     */
    fun openDrawer() {
        if (viewBinding.drawerLayout.isDrawerOpen(GravityCompat.START).not()) {
            viewBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    /**
     * 解析Intent，处理来自通知的跳转
     * Parses the intent to handle navigation from notifications.
     */
    private fun parseIntent() {
        val intent = intent
        if (intent.hasExtra(MusicService.EXTRA_NOTIFICATION)) {
            if (application.playerController().currentSong.value != null) {
                CRouter.with(this).url(RoutePath.PLAYING).start()
            }
            setIntent(Intent())
        }
    }

    private val onMenuSelectListener = object : NavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            viewBinding.drawerLayout.closeDrawers()
            lifecycleScope.launch {
                delay(1000)
                item.isChecked = false
            }
            when (item.itemId) {
                R.id.action_domain_setting -> {
                    ApiDomainDialog(this@MainActivity).show()
                    return true
                }
                R.id.action_setting -> {
                    CRouter.with(this@MainActivity).url("/settings").start()
                    return true
                }
                R.id.action_timer -> {
                    timerDialog()
                    return true
                }
                R.id.action_login -> {
                    CRouter.with(this@MainActivity)
                        .url(RoutePath.LOGIN)
                        .start()
                    return true
                }
                R.id.action_logout -> {
                    logout()
                    return true
                }
                R.id.action_exit -> {
                    exitApp()
                    return true
                }
                R.id.action_about -> {
                    startActivity(Intent(this@MainActivity, AboutActivity::class.java))
                    return true
                }
            }
            return false
        }
    }

    private val onTimerListener = object : QuitTimer.OnTimerListener {
        override fun onTick(remain: Long) {
            if (timerItem == null) {
                timerItem = viewBinding.navigationView.menu.findItem(R.id.action_timer)
            }
            val title = getString(R.string.menu_timer)
            timerItem?.title = if (remain == 0L) {
                title
            } else {
                TimeUtils.formatTime("$title(mm:ss)", remain)
            }
        }

        override fun onTimeEnd() {
            exitApp()
        }
    }

    private fun timerDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.menu_timer)
            .setItems(resources.getStringArray(R.array.timer_text)) { dialog: DialogInterface?, which: Int ->
                val times = resources.getIntArray(R.array.timer_int)
                startTimer(times[which])
            }
            .create()
        ImmersiveDialogHelper.enableImmersiveForDialog(dialog)
        dialog.show()
    }

    private fun startTimer(minute: Int) {
        quitTimer.start((minute * 60 * 1000).toLong())
        if (minute > 0) {
            toast(getString(R.string.timer_set, minute.toString()))
        } else {
            toast(R.string.timer_cancel)
        }
    }

    /**
     * 处理用户登出逻辑
     * Handles the user logout process.
     */
    private fun logout() {
        showImmersiveConfirmDialog(message = "确认退出登录？") {
            lifecycleScope.launch {
                try {
                    userService.logout()
                    toast("已退出登录")
                    CRouter.with(this@MainActivity)
                        .url(RoutePath.LOGIN)
                        .start()
                    finish()
                } catch (e: Exception) {
                    toast("退出登录异常：${e.message}")
                }
            }
        }
    }

    /**
     * 退出应用
     * Exits the application.
     */
    private fun exitApp() {
        application.playerController().stop()
        finish()
    }

    private fun getTabItem(@DrawableRes icon: Int, text: CharSequence): TabItemBinding {
        val binding = TabItemBinding.inflate(layoutInflater, viewBinding.tabBar, true)
        binding.ivIcon.setImageResource(icon)
        binding.tvTitle.text = text
        return binding
    }

    /**
     * 检查并执行自动播放功能
     * Checks and executes the auto-play feature on startup.
     */
    private fun checkAndExecuteAutoPlay() {
        if (!ConfigPreferences.autoPlayOnStartup) {
            android.util.Log.d("MainActivity", "自动播放功能已关闭，跳过自动播放")
            return
        }

        android.util.Log.d("MainActivity", "自动播放功能已启用，准备执行自动播放")

        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                try {
                    val playerController = application.playerController()
                    val currentPlaylist = playerController.playlist.value
                    if (currentPlaylist.isNullOrEmpty()) {
                        android.util.Log.d("MainActivity", "播放列表为空，无法执行自动播放")
                        return@launch
                    }
                    val currentPlayState = playerController.playState.value
                    if (currentPlayState.isPlaying) {
                        android.util.Log.d("MainActivity", "音乐已在播放中，跳过自动播放")
                        return@launch
                    }
                    playerController.playPause()
                    android.util.Log.d("MainActivity", "自动播放已执行")
                } catch (e: Exception) {
                    android.util.Log.e("MainActivity", "自动播放执行失败: ${e.message}", e)
                }
            }
        }, 1500)
    }

    /**
     * 活动销毁时的回调
     * Cleans up resources when the activity is destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()
        quitTimer.stop()
    }
}
