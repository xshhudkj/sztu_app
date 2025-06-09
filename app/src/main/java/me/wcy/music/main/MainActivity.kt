package me.wcy.music.main

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
import me.wcy.music.R
import me.wcy.music.account.service.UserService
import me.wcy.music.common.ApiDomainDialog
import me.wcy.music.common.BaseMusicActivity
import me.wcy.music.common.ImmersiveDialogHelper
import me.wcy.music.consts.RoutePath
import me.wcy.music.databinding.ActivityMainBinding
// import me.wcy.music.databinding.ActivitySplashBinding
import me.wcy.music.databinding.NavigationHeaderBinding
import me.wcy.music.databinding.TabItemBinding
import me.wcy.music.service.MusicService
import me.wcy.music.service.PlayServiceModule
import me.wcy.music.service.PlayServiceModule.playerController
import me.wcy.music.splash.SplashActivity
import me.wcy.music.storage.preference.ConfigPreferences
import me.wcy.music.utils.QuitTimer
import me.wcy.music.utils.TimeUtils
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.launch

import me.wcy.router.CRouter
import top.wangchenyan.common.ext.getColorEx
import me.wcy.music.common.showImmersiveConfirmDialog
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.ext.viewBindings

import top.wangchenyan.common.widget.pager.CustomTabPager
// 启动页相关导入已移除
import javax.inject.Inject

/**
 * Created by wangchenyan.top on 2023/8/21.
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

        // 检查并执行自动播放
        checkAndExecuteAutoPlay()

        configWindowInsets {
            navBarColor = getColorEx(R.color.tab_bg)
        }
    }



    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        parseIntent()
    }

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
                    // 已登录状态：显示退出登录，隐藏立即登录
                    menuLogin.isVisible = false
                    menuLogout.isVisible = true
                } else {
                    // 未登录状态：显示立即登录，隐藏退出登录
                    menuLogin.isVisible = true
                    menuLogout.isVisible = false
                }
            }
        }
    }

    fun openDrawer() {
        if (viewBinding.drawerLayout.isDrawerOpen(GravityCompat.START).not()) {
            viewBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

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
                    // 跳转到登录页面
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

        // 应用全屏沉浸式模式
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

    private fun logout() {
        showImmersiveConfirmDialog(message = "确认退出登录？") {
            lifecycleScope.launch {
                try {
                    userService.logout()
                    toast("已退出登录")
                    // 跳转到登录页面
                    CRouter.with(this@MainActivity)
                        .url(RoutePath.LOGIN)
                        .start()
                    finish() // 关闭当前MainActivity
                } catch (e: Exception) {
                    toast("退出登录异常：${e.message}")
                }
            }
        }
    }

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
     * 根据用户设置在应用启动时自动开始播放音乐
     */
    private fun checkAndExecuteAutoPlay() {
        // 检查是否启用了自动播放设置
        if (!ConfigPreferences.autoPlayOnStartup) {
            android.util.Log.d("MainActivity", "自动播放功能已关闭，跳过自动播放")
            return
        }

        android.util.Log.d("MainActivity", "自动播放功能已启用，准备执行自动播放")

        // 延迟执行自动播放，确保播放器和UI完全初始化
        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                try {
                    val playerController = application.playerController()

                    // 检查当前播放列表是否为空
                    val currentPlaylist = playerController.playlist.value
                    if (currentPlaylist.isNullOrEmpty()) {
                        android.util.Log.d("MainActivity", "播放列表为空，无法执行自动播放")
                        return@launch
                    }

                    // 检查当前是否已经在播放
                    val currentPlayState = playerController.playState.value
                    if (currentPlayState.isPlaying) {
                        android.util.Log.d("MainActivity", "音乐已在播放中，跳过自动播放")
                        return@launch
                    }

                    // 开始播放音乐
                    playerController.playPause()
                    android.util.Log.d("MainActivity", "自动播放已执行")

                } catch (e: Exception) {
                    android.util.Log.e("MainActivity", "自动播放执行失败: ${e.message}", e)
                    // 不显示错误提示给用户，避免影响启动体验
                }
            }
        }, 1500) // 延迟1.5秒，确保所有组件都已初始化完成
    }

    override fun onDestroy() {
        super.onDestroy()
        quitTimer.stop()
    }
}