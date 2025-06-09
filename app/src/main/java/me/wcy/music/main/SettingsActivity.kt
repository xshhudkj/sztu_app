package me.wcy.music.main

import android.content.ActivityNotFoundException
import android.content.Intent
import android.media.audiofx.AudioEffect
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint
import me.wcy.music.R
import me.wcy.music.common.BaseMusicActivity
import me.wcy.music.common.DarkModeService
import me.wcy.music.consts.PreferenceName
import me.wcy.music.service.PlayerController
import me.wcy.music.storage.preference.ConfigPreferences
import me.wcy.music.utils.MusicUtils
import me.wcy.music.widget.CacheClearDialog
import me.wcy.music.widget.AutoCacheCleanSettingsDialog
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.wcy.router.annotation.Route
import top.wangchenyan.common.ext.toast
import androidx.preference.SwitchPreference
import androidx.preference.ListPreference
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import me.wcy.music.service.AutoCacheCleanService
import me.wcy.music.utils.SmartCacheManager

import javax.inject.Inject

@Route("/settings")
@AndroidEntryPoint
class SettingsActivity : BaseMusicActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        
        // 初始化PreferenceFragment
        val fragment = SettingsFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitAllowingStateLoss()
            
        // 注册偏好设置监听器
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
            
        // 初始化自动清理服务
        initAutoCacheCleanService()
    }
    
    /**
     * 初始化自动清理缓存服务
     */
    private fun initAutoCacheCleanService() {
        if (ConfigPreferences.isAutoCacheCleanEnabled()) {
            AutoCacheCleanService.startService(this)
        }
    }
    
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            getString(R.string.auto_cache_clean_enabled) -> {
                handleAutoCacheCleanToggle(sharedPreferences)
            }
            getString(R.string.auto_cache_clean_interval) -> {
                handleAutoCacheCleanIntervalChange(sharedPreferences)
            }
            getString(R.string.auto_cache_clean_threshold) -> {
                handleAutoCacheCleanThresholdChange(sharedPreferences)
            }
            getString(R.string.setting_key_cache_limit) -> {
                handleCacheLimitChange(sharedPreferences)
            }
        }
    }
    
    /**
     * 处理自动清理开关变化
     */
    private fun handleAutoCacheCleanToggle(sharedPreferences: SharedPreferences?) {
        val enabled = sharedPreferences?.getBoolean(getString(R.string.auto_cache_clean_enabled), false) ?: false
        ConfigPreferences.setAutoCacheCleanEnabled(enabled)
        
        if (enabled) {
            AutoCacheCleanService.startService(this)
            toast(getString(R.string.cache_auto_clean_enabled))
        } else {
            AutoCacheCleanService.stopService(this)
            toast(getString(R.string.cache_auto_clean_disabled))
        }
    }
    
    /**
     * 处理自动清理间隔变化
     */
    private fun handleAutoCacheCleanIntervalChange(sharedPreferences: SharedPreferences?) {
        val intervalString = sharedPreferences?.getString(getString(R.string.auto_cache_clean_interval), "604800000") ?: "604800000"
        val intervalMs = intervalString.toLongOrNull() ?: 604800000L // 默认1周
        ConfigPreferences.setAutoCacheCleanInterval(intervalMs)
        
        // 如果自动清理已启用，重启服务以应用新间隔
        if (ConfigPreferences.isAutoCacheCleanEnabled()) {
            AutoCacheCleanService.stopService(this)
            AutoCacheCleanService.startService(this)
        }
    }
    
    /**
     * 处理自动清理阈值变化
     */
    private fun handleAutoCacheCleanThresholdChange(sharedPreferences: SharedPreferences?) {
        val thresholdString = sharedPreferences?.getString(getString(R.string.auto_cache_clean_threshold), "80") ?: "80"
        val threshold = thresholdString.toIntOrNull() ?: 80
        ConfigPreferences.setAutoCacheCleanThreshold(threshold)
    }
    
    /**
     * 处理缓存限制变化
     */
    private fun handleCacheLimitChange(sharedPreferences: SharedPreferences?) {
        val limitString = sharedPreferences?.getString(getString(R.string.setting_key_cache_limit), "-1") ?: "-1"
        val limitBytes = limitString.toLongOrNull() ?: -1L
        ConfigPreferences.setCacheLimitBytes(limitBytes)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // 注销偏好设置监听器
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    @AndroidEntryPoint
    class SettingsFragment : PreferenceFragmentCompat() {
        private val darkMode: Preference by lazy {
            findPreference(getString(R.string.setting_key_dark_mode))!!
        }
        private val playSoundQuality: Preference by lazy {
            findPreference(getString(R.string.setting_key_play_sound_quality))!!
        }
        private val soundEffect: Preference by lazy {
            findPreference(getString(R.string.setting_key_sound_effect))!!
        }
        private val autoPlayOnStartup: Preference by lazy {
            findPreference(getString(R.string.setting_key_auto_play_on_startup))!!
        }
        private val downloadSoundQuality: Preference by lazy {
            findPreference(getString(R.string.setting_key_download_sound_quality))!!
        }
        private val filterSize: Preference by lazy {
            findPreference(getString(R.string.setting_key_filter_size))!!
        }
        private val filterTime: Preference by lazy {
            findPreference(getString(R.string.setting_key_filter_time))!!
        }
        
        // 缓存管理相关Preference
        private val cacheClear: Preference by lazy {
            val key = getString(R.string.setting_key_cache_clear)
            android.util.Log.d("SettingsActivity", "Looking for preference with key: $key")
            val pref = findPreference<Preference>(key)
            android.util.Log.d("SettingsActivity", "Found preference: $pref")
            pref!!
        }
        private val cacheLimit: Preference by lazy {
            findPreference(getString(R.string.setting_key_cache_limit))!!
        }
        private val autoCacheClean: Preference by lazy {
            findPreference(getString(R.string.setting_key_auto_cache_clean))!!
        }

        @Inject
        lateinit var playerController: PlayerController

        @Inject
        lateinit var darkModeService: DarkModeService

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            android.util.Log.d("SettingsActivity", "onCreatePreferences called")
            setPreferencesFromResource(R.xml.preference_setting, rootKey)

            initDarkMode()
            initPlaySoundQuality()
            initSoundEffect()
            initAutoPlayOnStartup()
            initDownloadSoundQuality()
            initFilter()
            android.util.Log.d("SettingsActivity", "About to call initCacheManagement")
            initCacheManagement()
            android.util.Log.d("SettingsActivity", "initCacheManagement completed")
        }

        private fun initDarkMode() {
            darkMode.summary = getSummary(
                ConfigPreferences.darkMode,
                R.array.dark_mode_entries,
                R.array.dark_mode_values
            )
            darkMode.setOnPreferenceChangeListener { preference, newValue ->
                val value = newValue.toString()
                filterSize.summary = getSummary(
                    value,
                    R.array.dark_mode_entries,
                    R.array.dark_mode_values
                )
                val mode = DarkModeService.DarkMode.fromValue(value)
                darkModeService.setDarkMode(mode)
                true
            }
        }

        private fun initPlaySoundQuality() {
            playSoundQuality.summary = getSummary(
                ConfigPreferences.playSoundQuality,
                R.array.sound_quality_entries,
                R.array.sound_quality_entry_values
            )
            playSoundQuality.setOnPreferenceChangeListener { preference, newValue ->
                val value = newValue.toString()
                playSoundQuality.summary = getSummary(
                    value,
                    R.array.sound_quality_entries,
                    R.array.sound_quality_entry_values
                )
                true
            }
        }

        private fun initSoundEffect() {
            soundEffect.setOnPreferenceClickListener {
                startEqualizer()
                true
            }
        }

        /**
         * 初始化应用启动时自动播放音乐设置
         */
        private fun initAutoPlayOnStartup() {
            // 更新UI显示
            updateAutoPlayOnStartupUI()

            // 监听点击事件
            autoPlayOnStartup.setOnPreferenceClickListener {
                // 切换状态
                val newState = !ConfigPreferences.autoPlayOnStartup
                ConfigPreferences.autoPlayOnStartup = newState

                // 更新UI显示
                updateAutoPlayOnStartupUI()

                android.util.Log.d("SettingsActivity", "自动播放设置已更新: $newState")
                true
            }
        }

        /**
         * 更新自动播放设置的UI显示
         */
        private fun updateAutoPlayOnStartupUI() {
            val isEnabled = ConfigPreferences.autoPlayOnStartup
            if (isEnabled) {
                autoPlayOnStartup.title = getString(R.string.auto_play_on_startup_title_enabled)
                autoPlayOnStartup.summary = getString(R.string.auto_play_on_startup_summary)
            } else {
                autoPlayOnStartup.title = getString(R.string.auto_play_on_startup_title_disabled)
                autoPlayOnStartup.summary = null // 关闭时不显示副标题
            }
        }

        private fun initDownloadSoundQuality() {
            downloadSoundQuality.summary = getSummary(
                ConfigPreferences.downloadSoundQuality,
                R.array.sound_quality_entries,
                R.array.sound_quality_entry_values
            )
            downloadSoundQuality.setOnPreferenceChangeListener { preference, newValue ->
                val value = newValue.toString()
                downloadSoundQuality.summary = getSummary(
                    value,
                    R.array.sound_quality_entries,
                    R.array.sound_quality_entry_values
                )
                true
            }
        }

        private fun initFilter() {
            filterSize.summary = getSummary(
                ConfigPreferences.filterSize,
                R.array.filter_size_entries,
                R.array.filter_size_entry_values
            )
            filterSize.setOnPreferenceChangeListener { preference, newValue ->
                val value = newValue.toString()
                filterSize.summary = getSummary(
                    value,
                    R.array.filter_size_entries,
                    R.array.filter_size_entry_values
                )
                true
            }

            filterTime.summary = getSummary(
                ConfigPreferences.filterTime,
                R.array.filter_time_entries,
                R.array.filter_time_entry_values
            )
            filterTime.setOnPreferenceChangeListener { preference, newValue ->
                val value = newValue.toString()
                filterTime.summary = getSummary(
                    value,
                    R.array.filter_time_entries,
                    R.array.filter_time_entry_values
                )
                true
            }
        }

        /**
         * 初始化缓存管理功能 - 包含三行完整功能
         */
        private fun initCacheManagement() {
            // 初始化缓存清理功能
            initCacheClear()

            // 初始化缓存限制功能
            initCacheLimit()
            
            // 初始化定期自动清理功能
            initAutoCacheClean()
        }

        /**
         * 初始化缓存清理功能
         */
        private fun initCacheClear() {
            android.util.Log.d("SettingsActivity", "initCacheClear called")
            cacheClear.setOnPreferenceClickListener {
                android.util.Log.d("SettingsActivity", "Cache clear clicked")
                showCacheManagementDialog()
                true
            }
        }
        


        /**
         * 初始化缓存限制功能
         */
        private fun initCacheLimit() {
            // 设置初始摘要
            updateCacheLimitSummary()
            
            cacheLimit.setOnPreferenceChangeListener { preference, newValue ->
                val value = newValue.toString()
                
                // 更新配置
                ConfigPreferences.cacheLimit = value
                
                // 更新摘要显示
                updateCacheLimitSummary()
                
                // 显示设置成功提示
                val limitText = getSummary(
                    value,
                    R.array.cache_limit_entries,
                    R.array.cache_limit_values
                )
                toast("缓存限制已设置为：$limitText")
                
                true
            }
        }

        /**
         * 更新缓存限制的摘要显示
         */
        private fun updateCacheLimitSummary() {
            val currentValue = ConfigPreferences.cacheLimit
            val summary = getSummary(
                currentValue,
                R.array.cache_limit_entries,
                R.array.cache_limit_values
            )
            cacheLimit.summary = summary
        }

        /**
         * 初始化定期自动清理功能
         */
        private fun initAutoCacheClean() {
            // 设置初始摘要
            updateAutoCacheCleanSummary()
            
            autoCacheClean.setOnPreferenceClickListener {
                showAutoCacheCleanDialog()
                true
            }
        }

        /**
         * 更新自动清理的摘要显示
         * 修正：支持应用退出时清理的显示
         */
        private fun updateAutoCacheCleanSummary() {
            val summary = when {
                ConfigPreferences.isAppExitAutoCacheCleanEnabled() -> {
                    "已启用，间隔：应用关闭时"
                }
                ConfigPreferences.isAutoCacheCleanEnabled() -> {
                    val intervalHours = ConfigPreferences.getAutoCacheCleanIntervalHours()
                    when (intervalHours) {
                        24 -> "已启用（每天清理）"
                        168 -> "已启用（每周清理）"
                        720 -> "已启用（每月清理）"
                        else -> "已启用（自定义间隔）"
                    }
                }
                else -> {
                    "已关闭"
                }
            }
            autoCacheClean.summary = summary
        }

        /**
         * 显示自动清理设置对话框
         */
        private fun showAutoCacheCleanDialog() {
            try {
                val smartCacheManager = SmartCacheManager(requireContext())
                lifecycleScope.launch {
                    try {
                        val cacheInfo = smartCacheManager.getCacheStats()
                        val dialog = AutoCacheCleanSettingsDialog.newInstance(cacheInfo) { enabled, intervalHours ->
                            // 更新摘要显示
                            updateAutoCacheCleanSummary()
                            
                            // 显示设置结果提示
                            val message = when {
                                intervalHours == -1 -> "自动清理已启用，间隔：应用关闭时"
                                enabled -> {
                                    val intervalText = when (intervalHours) {
                                        24 -> "每天"
                                        168 -> "每周"
                                        720 -> "每月"
                                        else -> "自定义间隔"
                                    }
                                    "自动清理已启用，间隔：$intervalText"
                                }
                                else -> "自动清理已关闭"
                            }
                            toast(message)
                        }
                        dialog.show(parentFragmentManager, "AutoCacheCleanDialog")
                    } catch (e: Exception) {
                        toast("获取缓存信息失败")
                    }
                }
            } catch (e: Exception) {
                toast("显示自动清理设置失败")
            }
        }

        /**
         * 显示缓存清理对话框
         */
        private fun showCacheManagementDialog() {
            try {
                val dialog = CacheClearDialog.newInstance()
                dialog.show(parentFragmentManager, "CacheClearDialog")
            } catch (e: Exception) {
                toast("显示缓存清理对话框失败")
            }
        }

        private fun startEqualizer() {
            val audioSessionId = playerController.getAudioSessionId()
            try {
                val intent = Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL).apply {
                    putExtra(AudioEffect.EXTRA_AUDIO_SESSION, audioSessionId)
                    putExtra(AudioEffect.EXTRA_CONTENT_TYPE, AudioEffect.CONTENT_TYPE_MUSIC)
                    putExtra(AudioEffect.EXTRA_PACKAGE_NAME, requireActivity().packageName)
                }
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                toast(getString(R.string.device_not_support))
            }
        }



        private fun getSummary(value: String, entryArray: Int, entryValueArray: Int): String {
            return try {
                val entries = resources.getStringArray(entryArray)
                val entryValues = resources.getStringArray(entryValueArray)
                val index = entryValues.indexOf(value)
                if (index >= 0) entries[index] else ""
            } catch (e: Exception) {
                ""
            }
        }
    }
}