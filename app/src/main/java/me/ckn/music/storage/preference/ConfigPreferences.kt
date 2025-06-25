package me.ckn.music.storage.preference

import com.blankj.utilcode.util.StringUtils
import me.ckn.music.R
import me.ckn.music.common.DarkModeService
import me.ckn.music.consts.PreferenceName
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.storage.IPreferencesFile
import top.wangchenyan.common.storage.PreferencesFile

/**
 * SharedPreferences工具类
 * Created by wcy on 2015/11/28.
 */
object ConfigPreferences :
    IPreferencesFile by PreferencesFile(CommonApp.app, PreferenceName.CONFIG, false) {

    var playSoundQuality by IPreferencesFile.StringProperty(
        StringUtils.getString(R.string.setting_key_play_sound_quality),
        "standard"
    )

    var downloadSoundQuality by IPreferencesFile.StringProperty(
        StringUtils.getString(R.string.setting_key_download_sound_quality),
        "standard"
    )

    var filterSize by IPreferencesFile.StringProperty(
        StringUtils.getString(R.string.setting_key_filter_size),
        "0"
    )

    var filterTime by IPreferencesFile.StringProperty(
        StringUtils.getString(R.string.setting_key_filter_time),
        "0"
    )

    var darkMode by IPreferencesFile.StringProperty(
        "dark_mode",
        DarkModeService.DarkMode.Auto.value
    )

    /**
     * 缓存限制设置（字节数）
     * 0表示无限制，其他值为具体的字节限制
     */
    var cacheLimit by IPreferencesFile.StringProperty(
        StringUtils.getString(R.string.setting_key_cache_limit),
        "0"
    )

    var playMode: Int by IPreferencesFile.IntProperty("play_mode", 0)

    var currentSongId: String by IPreferencesFile.StringProperty("current_song_id", "")

    var apiDomain: String by IPreferencesFile.StringProperty("api_domain", "")

    var vipDialogShown: Boolean by IPreferencesFile.BooleanProperty("vip_dialog_shown", false)

    /**
     * 应用启动时自动播放音乐设置
     * true: 启动时自动播放, false: 不自动播放
     */
    var autoPlayOnStartup: Boolean by IPreferencesFile.BooleanProperty(
        StringUtils.getString(R.string.setting_key_auto_play_on_startup),
        true
    )

    /**
     * 进入播放页自动开启语音模式设置
     * true: 自动开启语音模式, false: 不自动开启
     */
    var voiceAutoEnable: Boolean by IPreferencesFile.BooleanProperty(
        "voice_auto_enable",
        true
    )

    /**
     * 语音识别灵敏度（相似度阈值）
     * 范围：0.2-0.8，值越小越灵敏，值越大越准确
     */
    var voiceSensitivity: Float by IPreferencesFile.FloatProperty(
        "voice_sensitivity",
        0.6f
    )

    /**
     * 智能上下文理解
     * true: 启用智能上下文理解, false: 禁用
     */
    var voiceSmartContext: Boolean by IPreferencesFile.BooleanProperty(
        "voice_smart_context",
        true
    )

    /**
     * 模糊匹配识别
     * true: 启用模糊匹配和容错识别, false: 仅精确匹配
     */
    var voiceFuzzyMatching: Boolean by IPreferencesFile.BooleanProperty(
        "voice_fuzzy_matching",
        true
    )
    
    private const val DEFAULT_CACHE_LIMIT = -1L // -1表示无限制
    private const val CACHE_LIMIT_KEY = "cache_limit"
    
    // 自动清理配置键
    private const val AUTO_CACHE_CLEAN_ENABLED_KEY = "auto_cache_clean_enabled"
    private const val AUTO_CACHE_CLEAN_INTERVAL_KEY = "auto_cache_clean_interval"
    private const val AUTO_CACHE_CLEAN_THRESHOLD_KEY = "auto_cache_clean_threshold"
    private const val LAST_AUTO_CLEAN_TIME_KEY = "last_auto_clean_time"
    private const val APP_EXIT_AUTO_CACHE_CLEAN_ENABLED_KEY = "app_exit_auto_cache_clean_enabled"
    
    // 默认值
    private const val DEFAULT_AUTO_CLEAN_ENABLED = false
    private const val DEFAULT_AUTO_CLEAN_INTERVAL = 604800000L // 1周
    private const val DEFAULT_AUTO_CLEAN_THRESHOLD = 80 // 80%
    private const val DEFAULT_APP_EXIT_AUTO_CLEAN_ENABLED = false
    
    /**
     * 获取缓存限制大小（字节）
     * @return 缓存限制大小，-1表示无限制
     */
    fun getCacheLimitBytes(): Long {
        return try {
            cacheLimit.toLongOrNull() ?: DEFAULT_CACHE_LIMIT
        } catch (e: Exception) {
            DEFAULT_CACHE_LIMIT
        }
    }
    
    /**
     * 设置缓存限制大小
     */
    fun setCacheLimitBytes(limitBytes: Long) {
        cacheLimit = limitBytes.toString()
    }

    /**
     * 获取音频缓存限制（总缓存限制的分配部分）
     * 音频缓存占总缓存的70%，其他缓存占30%
     */
    fun getAudioCacheLimitBytes(): Long {
        val totalLimit = getCacheLimitBytes()
        return if (totalLimit <= 0) {
            -1L // 无限制
        } else {
            (totalLimit * 0.7).toLong() // 70%分配给音频缓存
        }
    }

    /**
     * 获取其他缓存限制（图片、临时文件等）
     * 其他缓存占总缓存的30%
     */
    fun getOtherCacheLimitBytes(): Long {
        val totalLimit = getCacheLimitBytes()
        return if (totalLimit <= 0) {
            -1L // 无限制
        } else {
            (totalLimit * 0.3).toLong() // 30%分配给其他缓存
        }
    }

    /**
     * 检查当前缓存使用是否超出限制
     */
    fun isCacheOverLimit(currentAudioCacheSize: Long, currentOtherCacheSize: Long): Boolean {
        val totalLimit = getCacheLimitBytes()
        if (totalLimit <= 0) return false // 无限制
        
        val totalCurrentSize = currentAudioCacheSize + currentOtherCacheSize
        return totalCurrentSize > totalLimit
    }
    
    // 自动清理配置方法
    /**
     * 是否启用自动清理缓存
     */
    fun isAutoCacheCleanEnabled(): Boolean {
        return getBoolean(AUTO_CACHE_CLEAN_ENABLED_KEY, DEFAULT_AUTO_CLEAN_ENABLED)
    }
    
    /**
     * 设置自动清理缓存开关
     */
    fun setAutoCacheCleanEnabled(enabled: Boolean) {
        putBoolean(AUTO_CACHE_CLEAN_ENABLED_KEY, enabled)
    }
    
    /**
     * 获取自动清理间隔（毫秒）
     */
    fun getAutoCacheCleanInterval(): Long {
        return getLong(AUTO_CACHE_CLEAN_INTERVAL_KEY, DEFAULT_AUTO_CLEAN_INTERVAL)
    }
    
    /**
     * 设置自动清理间隔
     */
    fun setAutoCacheCleanInterval(intervalMs: Long) {
        putLong(AUTO_CACHE_CLEAN_INTERVAL_KEY, intervalMs)
    }

    /**
     * 获取自动清理间隔（小时）
     */
    fun getAutoCacheCleanIntervalHours(): Int {
        return getInt("auto_cache_clean_interval_hours", 168) // 默认1周=168小时
    }

    /**
     * 设置自动清理间隔（小时）
     */
    fun setAutoCacheCleanIntervalHours(hours: Int) {
        putInt("auto_cache_clean_interval_hours", hours)
    }
    
    /**
     * 获取自动清理阈值（百分比）
     */
    fun getAutoCacheCleanThreshold(): Int {
        return getInt(AUTO_CACHE_CLEAN_THRESHOLD_KEY, DEFAULT_AUTO_CLEAN_THRESHOLD)
    }
    
    /**
     * 设置自动清理阈值
     */
    fun setAutoCacheCleanThreshold(threshold: Int) {
        putInt(AUTO_CACHE_CLEAN_THRESHOLD_KEY, threshold)
    }
    
    /**
     * 获取上次自动清理时间
     */
    fun getLastAutoCacheCleanTime(): Long {
        return getLong(LAST_AUTO_CLEAN_TIME_KEY, 0L)
    }
    
    /**
     * 设置上次自动清理时间
     */
    fun setLastAutoCacheCleanTime(time: Long) {
        putLong(LAST_AUTO_CLEAN_TIME_KEY, time)
    }
    
    /**
     * 是否需要执行自动清理
     */
    fun shouldPerformAutoClean(currentUsagePercentage: Double): Boolean {
        if (!isAutoCacheCleanEnabled()) {
            return false
        }
        
        val now = System.currentTimeMillis()
        val lastCleanTime = getLastAutoCacheCleanTime()
        val cleanInterval = getAutoCacheCleanInterval()
        val threshold = getAutoCacheCleanThreshold()
        
        // 检查时间间隔和使用率阈值
        val timeToClean = (now - lastCleanTime) >= cleanInterval
        val thresholdReached = currentUsagePercentage >= threshold
        
        return timeToClean || thresholdReached
    }

    /**
     * 是否启用应用退出时自动清理缓存
     */
    fun isAppExitAutoCacheCleanEnabled(): Boolean {
        return getBoolean(APP_EXIT_AUTO_CACHE_CLEAN_ENABLED_KEY, DEFAULT_APP_EXIT_AUTO_CLEAN_ENABLED)
    }

    /**
     * 设置应用退出时自动清理缓存开关
     */
    fun setAppExitAutoCacheCleanEnabled(enabled: Boolean) {
        putBoolean(APP_EXIT_AUTO_CACHE_CLEAN_ENABLED_KEY, enabled)
    }
}