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
import android.widget.TextView
import androidx.core.view.isVisible
import me.ckn.music.utils.toMediaItem
import me.ckn.music.service.PlayerController

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

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var playerController: PlayerController

    private var timerTextView: TextView? = null

    /**
     * 活动创建时的回调
     * Initializes the activity, sets up the UI, and configures event listeners.
     * @param savedInstanceState 保存的实例状态 / Saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        CustomTabPager(lifecycle, supportFragmentManager, viewBinding.viewPager).apply {
            // 添加"发现"按钮
            val discoverTab = getTabItem(me.ckn.music.R.drawable.ic_tab_discover, "发现")
            discoverTab.root.setOnClickListener {
                viewBinding.viewPager.currentItem = 0
            }
            viewBinding.tabBar.addView(discoverTab.root)
            addFragment(me.ckn.music.discover.home.DiscoverFragment(), discoverTab.root)

            // 添加"发现"与"漫游"之间的空白
            val space1 = android.view.View(this@MainActivity)
            space1.layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f
            )
            viewBinding.tabBar.addView(space1)

            // 添加"漫游"按钮（不addFragment）
            val roamTab = getTabItem(me.ckn.music.R.drawable.ic_tab_roam, "漫游")
            roamTab.root.setOnClickListener {
                lifecycleScope.launch {
                    try {
                        val playlists = me.ckn.music.discover.DiscoverApi.get().getRecommendPlaylists().playlists
                        if (playlists.isNullOrEmpty()) {
                            toast("暂无推荐歌单")
                            return@launch
                        }
                        val randomPlaylist = playlists.random()
                        val songListData = me.ckn.music.discover.DiscoverApi.get().getPlaylistSongList(randomPlaylist.id)
                        val songs = songListData.songs
                        if (songs.isNullOrEmpty()) {
                            toast("该歌单没有歌曲")
                            return@launch
                        }
                        val mediaItems = songs.map { it.toMediaItem() }
                        playerController.replaceAll(mediaItems, mediaItems.first())
                        startActivity(Intent(this@MainActivity, me.ckn.music.main.playing.PlayingActivity::class.java))
                    } catch (e: Exception) {
                        toast("漫游播放失败：${e.message}")
                    }
                }
            }
            viewBinding.tabBar.addView(roamTab.root)

            // 添加"漫游"与"我的"之间的空白
            val space2 = android.view.View(this@MainActivity)
            space2.layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f
            )
            viewBinding.tabBar.addView(space2)

            // 添加"我的"按钮
            val mineTab = getTabItem(me.ckn.music.R.drawable.ic_tab_mine, "我的")
            mineTab.root.setOnClickListener {
                viewBinding.viewPager.currentItem = 1
            }
            viewBinding.tabBar.addView(mineTab.root)
            addFragment(me.ckn.music.mine.home.MineFragment(), mineTab.root)

            // 添加底部空白
            val bottomSpace = android.view.View(this@MainActivity)
            bottomSpace.layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                resources.displayMetrics.density.times(160).toInt() // 160dp
            )
            viewBinding.tabBar.addView(bottomSpace)

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

    private fun initDrawer() {
        timerTextView = viewBinding.navTimer

        viewBinding.navTimer.setOnClickListener {
            viewBinding.drawerLayout.closeDrawers()
            timerDialog()
        }
        viewBinding.navRecentPlay.setOnClickListener {
            viewBinding.drawerLayout.closeDrawers()
            startActivity(Intent(this@MainActivity, me.ckn.music.mine.recent.RecentPlayActivity::class.java))
        }
        viewBinding.navDomainSetting.setOnClickListener {
            viewBinding.drawerLayout.closeDrawers()
            ApiDomainDialog(this@MainActivity).show()
        }
        viewBinding.navSetting.setOnClickListener {
            viewBinding.drawerLayout.closeDrawers()
            CRouter.with(this@MainActivity).url("/settings").start()
        }
        viewBinding.navAbout.setOnClickListener {
            viewBinding.drawerLayout.closeDrawers()
            startActivity(Intent(this@MainActivity, AboutActivity::class.java))
        }
        viewBinding.navLogin.setOnClickListener {
            viewBinding.drawerLayout.closeDrawers()
            CRouter.with(this@MainActivity)
                .url(RoutePath.LOGIN)
                .start()
        }
        viewBinding.navLogout.setOnClickListener {
            viewBinding.drawerLayout.closeDrawers()
            logout()
        }
        viewBinding.navExit.setOnClickListener {
            viewBinding.drawerLayout.closeDrawers()
            exitApp()
        }

        lifecycleScope.launch {
            userService.profile.collectLatest { profile ->
                if (profile != null) {
                    viewBinding.navLogin.isVisible = false
                    viewBinding.navLogout.isVisible = true
                } else {
                    viewBinding.navLogin.isVisible = true
                    viewBinding.navLogout.isVisible = false
                }
            }
        }
    }

    fun openDrawer() {
        if (viewBinding.drawerLayout.isDrawerOpen(GravityCompat.START).not()) {
            viewBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private val onTimerListener = object : QuitTimer.OnTimerListener {
        override fun onTick(remain: Long) {
            val title = getString(R.string.menu_timer)
            timerTextView?.text = if (remain == 0L) {
                title
            } else {
                TimeUtils.formatTime("$title(mm:ss)", remain)
            }
        }

        override fun onTimeEnd() {
            exitApp()
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

    private fun getTabItem(@DrawableRes icon: Int, text: CharSequence): TabItemBinding {
        val binding = TabItemBinding.inflate(layoutInflater, viewBinding.tabBar, false)
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

    private fun timerDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.menu_timer)
            .setItems(resources.getStringArray(R.array.timer_text)) { dialog, which ->
                val times = resources.getIntArray(R.array.timer_int)
                startTimer(times[which])
            }
            .create()
        ImmersiveDialogHelper.enableImmersiveForDialog(dialog)
        dialog.show()
    }

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

    private fun exitApp() {
        application.playerController().stop()
        finish()
    }

    private fun startTimer(minute: Int) {
        quitTimer.start((minute * 60 * 1000).toLong())
        if (minute > 0) {
            toast(getString(R.string.timer_set, minute.toString()))
        } else {
            toast(R.string.timer_cancel)
        }
    }
}
