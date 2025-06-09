package me.wcy.music.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.wcy.music.R
import me.wcy.music.databinding.DialogAutoCacheCleanSettingsBinding
import me.wcy.music.service.AutoCacheCleanService
import me.wcy.music.storage.preference.ConfigPreferences
import me.wcy.music.utils.CacheInfo
import me.wcy.music.utils.SmartCacheManager
import top.wangchenyan.common.ext.toast

/**
 * 自动缓存清理设置对话框
 *
 * 功能特点：
 * 1. 简化的定期清理设置（每天、每周、每月）
 * 2. 到时清理全部缓存
 * 3. 清理完成后通知用户释放空间
 * 4. Android Automotive横屏优化
 *
 * Created for Cache Management Enhancement
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
         * 创建自动清理设置对话框实例
         * @param currentCacheInfo 当前缓存信息
         * @param onSettingsChanged 设置变更回调 (enabled, intervalHours)
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