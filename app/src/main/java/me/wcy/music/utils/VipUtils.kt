package me.wcy.music.utils

import androidx.media3.common.MediaItem

/**
 * VIP功能工具类
 * 基于真实API数据的VIP功能实现
 *
 * 功能说明：
 * 1. 检查歌曲是否为VIP歌曲（基于fee字段）
 * 2. 检查用户VIP状态（基于真实的VIP API数据）
 * 3. 计算试听限制相关信息
 * 4. 提供试听终点检查功能
 */
object VipUtils {

    /**
     * VIP状态提供者，用于获取当前用户的VIP状态
     */
    private var vipStatusProvider: (() -> Boolean)? = null
    
    /**
     * 试听时长：30秒
     */
    const val TRIAL_DURATION_MS = 30000L
    
    /**
     * 检查歌曲是否为VIP歌曲
     * 根据歌曲的fee字段判断，只有接口明确返回的VIP歌曲才当作VIP
     * fee: 0-免费, 1-VIP歌曲, 4-购买专辑, 8-非会员可免费播放低音质, 16-数字专辑
     */
    fun isVipSong(fee: Int): Boolean {
        return fee == 1  // 只有fee=1才是真正的VIP歌曲
    }

    /**
     * 检查歌曲是否需要音质降级
     * fee=8的歌曲，非会员可免费播放低音质
     */
    fun needQualityDowngrade(fee: Int): Boolean {
        return fee == 8
    }

    /**
     * 获取降级后的音质等级
     * 对于fee=8的歌曲，最高只能播放standard音质
     */
    fun getDowngradedQuality(currentQuality: String, fee: Int): String {
        if (fee == 8) {
            // fee=8的歌曲最高只能播放standard音质
            return "standard"
        }
        return currentQuality
    }

    /**
     * 检查当前音质是否需要降级
     * 根据音质等级判断是否高于standard
     */
    fun isHighQuality(quality: String): Boolean {
        return when (quality) {
            "standard" -> false
            "higher", "exhigh", "lossless", "hires", "jyeffect", "sky", "jymaster" -> true
            else -> false
        }
    }
    
    /**
     * 获取歌曲的fee字段
     * 从MediaItem的extras中获取
     */
    fun getSongFee(song: MediaItem): Int {
        return song.mediaMetadata.getFee()
    }
    
    /**
     * 设置VIP状态提供者
     * 由UserStateManager调用，提供真实的VIP状态
     */
    fun setVipStatusProvider(provider: () -> Boolean) {
        vipStatusProvider = provider
    }

    /**
     * 检查当前用户是否为VIP
     * 基于真实的VIP API数据判断
     */
    fun isUserVip(): Boolean {
        return vipStatusProvider?.invoke() ?: false
    }
    
    /**
     * 检查歌曲是否需要试听限制
     * 非VIP用户播放VIP歌曲时需要试听限制
     */
    fun needTrialLimit(song: MediaItem): Boolean {
        val fee = getSongFee(song)
        return isVipSong(fee) && !isUserVip()
    }
    
    /**
     * 获取试听终点时间（毫秒）
     */
    fun getTrialEndTime(song: MediaItem): Long {
        return if (needTrialLimit(song)) {
            TRIAL_DURATION_MS
        } else {
            song.mediaMetadata.getDuration()
        }
    }
    
    /**
     * 获取试听进度百分比（0-1）
     */
    fun getTrialProgress(song: MediaItem): Float {
        val totalDuration = song.mediaMetadata.getDuration()
        return if (totalDuration > 0 && needTrialLimit(song)) {
            TRIAL_DURATION_MS.toFloat() / totalDuration.toFloat()
        } else {
            1.0f
        }
    }
    
    /**
     * 检查是否到达试听终点
     */
    fun isTrialEndReached(song: MediaItem, currentPosition: Long): Boolean {
        return needTrialLimit(song) && currentPosition >= TRIAL_DURATION_MS
    }
    
    /**
     * 获取VIP歌曲标识文本
     * 只为真正的VIP歌曲显示标签，fee=8的歌曲可以正常播放不显示VIP标签
     */
    fun getVipLabel(fee: Int): String? {
        return when (fee) {
            1 -> "VIP"  // 只有fee=1才显示VIP标签
            else -> null  // fee=8的歌曲不显示VIP标签，因为可以正常播放
        }
    }

    /**
     * VIP标签缓存，避免重复计算
     * 使用LRU缓存，最多缓存1000个标签状态
     */
    private val vipLabelCache = object : LinkedHashMap<Int, String?>(16, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<Int, String?>?): Boolean {
            return size > 1000
        }
    }

    /**
     * 获取缓存的VIP标签，提升性能
     */
    fun getCachedVipLabel(fee: Int): String? {
        return vipLabelCache.getOrPut(fee) { getVipLabel(fee) }
    }

    /**
     * 高性能VIP标签更新方法
     * 避免重复设置相同的文本和可见性状态
     */
    fun updateVipLabelOptimized(labelView: android.widget.TextView, fee: Int) {
        val vipLabel = getCachedVipLabel(fee)

        if (vipLabel != null) {
            // 只有当前不可见或文本不同时才更新
            if (labelView.visibility != android.view.View.VISIBLE || labelView.text != vipLabel) {
                labelView.text = vipLabel
                labelView.visibility = android.view.View.VISIBLE
            }
        } else {
            // 只有当前可见时才隐藏
            if (labelView.visibility != android.view.View.GONE) {
                labelView.visibility = android.view.View.GONE
            }
        }
    }
    
    /**
     * 检查歌曲是否可以完整播放
     * 免费歌曲或VIP用户可以完整播放
     */
    fun canPlayFully(song: MediaItem): Boolean {
        return !needTrialLimit(song)
    }
}
