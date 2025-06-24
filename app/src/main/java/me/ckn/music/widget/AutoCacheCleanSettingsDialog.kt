/**
 * WhisperPlay Music Player
 *
 * 文件描述：自动缓存清理设置对话框
 * File Description: Dialog for auto cache clean settings.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import me.ckn.music.R
import me.ckn.music.databinding.DialogAutoCacheCleanSettingsBinding
import me.ckn.music.storage.preference.ConfigPreferences
import me.ckn.music.utils.CacheInfo
import me.ckn.music.utils.SmartCacheManager
import top.wangchenyan.common.ext.toast

/**
 * 自动缓存清理设置对话框
 * Auto Cache Clean Settings Dialog
 *
 * 主要功能：
 * Main Functions:
 * - 提供自动缓存清理的设置选项（从不、应用关闭时、每天、每周、每月） / Provides settings for auto cache cleaning (Never, On App Close, Daily, Weekly, Monthly).
 * - 保存用户的设置到 Preferences / Saves user settings to Preferences.
 *
 * 使用示例：
 * Usage Example:
 * ```kotlin
 * AutoCacheCleanSettingsDialog.newInstance(cacheInfo) { enabled, interval ->
 *     // 处理设置变更 / Handle settings change
 * }.show(supportFragmentManager, "AutoCacheCleanSettingsDialog")
 * ```
 *
 * @author ckn
 * @since 2025-06-10
 */
class AutoCacheCleanSettingsDialog : DialogFragment() {

    private var _binding: DialogAutoCacheCleanSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var smartCacheManager: SmartCacheManager
    private var onSettingsChangedListener: ((Boolean, Int) -> Unit)? = null

    // 简化的间隔映射（小时）
    private val intervalHoursMap = mapOf(
        R.id.rbNone to 0,        // 无
        R.id.rbOnAppClose to -1, // 应用关闭时
        R.id.rbDaily to 24,      // 每天
        R.id.rbWeekly to 168,    // 每周
        R.id.rbMonthly to 720    // 每月（30天）
    )

    companion object {
        /**
         * 创建自动清理设置对话框的新实例
         * Create a new instance of the auto clean settings dialog.
         *
         * @param currentCacheInfo 当前缓存信息 / Current cache information.
         * @param onSettingsChanged 设置变更时的回调 / Callback for when settings are changed. (enabled, intervalHours)
         * @return [AutoCacheCleanSettingsDialog]
         */
        fun newInstance(currentCacheInfo: CacheInfo, onSettingsChanged: (Boolean, Int) -> Unit): AutoCacheCleanSettingsDialog {
            return AutoCacheCleanSettingsDialog().apply {
                arguments = Bundle().apply {
                    putParcelable("cache_info", currentCacheInfo)
                }
                onSettingsChangedListener = onSettingsChanged
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置对话框样式
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAutoCacheCleanSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        smartCacheManager = SmartCacheManager(requireContext())

        setupViews()
        loadCurrentStatus()
    }

    /**
     * 设置视图和监听器
     */
    private fun setupViews() {
        // 取消按钮
        binding.btnCancel.setOnClickListener { dismiss() }

        // 确认按钮
        binding.btnConfirm.setOnClickListener { applyAutoCleanSettings() }

        // 设置对话框属性
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.let { window ->
            val layoutParams = window.attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.8).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window.attributes = layoutParams
        }
    }

    /**
     * 加载当前状态
     */
    private fun loadCurrentStatus() {
        loadCurrentSettings()
    }

    /**
     * 加载当前设置
     */
    private fun loadCurrentSettings() {
        // 检查当前的清理设置
        when {
            ConfigPreferences.isAppExitAutoCacheCleanEnabled() -> {
                // 应用退出时清理
                binding.rgCleanInterval.check(R.id.rbOnAppClose)
            }
            ConfigPreferences.isAutoCacheCleanEnabled() -> {
                // 定期清理
                val intervalHours = ConfigPreferences.getAutoCacheCleanIntervalHours()
                val intervalId = intervalHoursMap.entries.firstOrNull { it.value == intervalHours }?.key ?: R.id.rbWeekly
                binding.rgCleanInterval.check(intervalId)
            }
            else -> {
                // 关闭自动清理
                binding.rgCleanInterval.check(R.id.rbNone)
            }
        }
    }

    /**
     * 应用自动清理设置
     */
    private fun applyAutoCleanSettings() {
        try {
            val intervalHours = intervalHoursMap[binding.rgCleanInterval.checkedRadioButtonId] ?: 0

            when (intervalHours) {
                -1 -> {
                    // 应用关闭时清理
                    ConfigPreferences.setAutoCacheCleanEnabled(false)
                    ConfigPreferences.setAppExitAutoCacheCleanEnabled(true)
                    onSettingsChangedListener?.invoke(false, -1)
                }
                0 -> {
                    // 关闭自动清理
                    ConfigPreferences.setAutoCacheCleanEnabled(false)
                    ConfigPreferences.setAppExitAutoCacheCleanEnabled(false)
                    onSettingsChangedListener?.invoke(false, 0)
                }
                else -> {
                    // 定期清理
                    ConfigPreferences.setAutoCacheCleanEnabled(true)
                    ConfigPreferences.setAppExitAutoCacheCleanEnabled(false)
                    ConfigPreferences.setAutoCacheCleanIntervalHours(intervalHours)
                    onSettingsChangedListener?.invoke(true, intervalHours)
                }
            }

            dismiss()

        } catch (e: Exception) {
            toast("设置失败: ${e.message}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}