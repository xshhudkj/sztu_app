package me.wcy.music.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import me.wcy.music.R
import me.wcy.music.utils.CacheInfo
import me.wcy.music.storage.preference.ConfigPreferences
import me.wcy.music.databinding.DialogCacheLimitSettingsBinding
import top.wangchenyan.common.ext.toast

/**
 * 缓存限制设置对话框
 * 允许用户设置总缓存限制，自动计算音频和其他缓存分配
 */
class CacheLimitSettingsDialog : DialogFragment() {

    private var _binding: DialogCacheLimitSettingsBinding? = null
    private val binding get() = _binding!!
    
    private var onLimitSetListener: ((Long) -> Unit)? = null

    // 限制选项映射（字节）
    private val limitOptionsMap = mapOf(
        R.id.rbUnlimited to -1L,
        R.id.rb100MB to 104857600L,
        R.id.rb200MB to 209715200L,
        R.id.rb500MB to 524288000L,
        R.id.rb1GB to 1073741824L,
        R.id.rb2GB to 2147483648L,
        R.id.rb5GB to 5368709120L
    )

    companion object {
        fun newInstance(cacheInfo: CacheInfo, onLimitSet: (Long) -> Unit): CacheLimitSettingsDialog {
            return CacheLimitSettingsDialog().apply {
                arguments = Bundle().apply {
                    putParcelable("cache_info", cacheInfo)
                }
                onLimitSetListener = onLimitSet
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCacheLimitSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val cacheInfo = arguments?.getParcelable("cache_info") as? CacheInfo
        if (cacheInfo != null) {
            setupViews(cacheInfo)
        } else {
            dismiss()
        }
    }

    private fun setupViews(cacheInfo: CacheInfo) {
        // 显示当前缓存信息
        binding.tvCurrentCacheSize.text = cacheInfo.totalSizeFormatted
        
        // 加载当前设置
        loadCurrentSettings()
        
        // 设置按钮事件
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnConfirm.setOnClickListener { applyLimitSettings() }
        
        // 设置RadioGroup监听器，实时显示分配详情
        binding.rgLimitOptions.setOnCheckedChangeListener { _, checkedId ->
            updateAllocationPreview(checkedId)
        }
        
        // 初始显示分配详情
        updateAllocationPreview(binding.rgLimitOptions.checkedRadioButtonId)
        
        // 设置对话框属性
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.let { window ->
            val layoutParams = window.attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.85).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window.attributes = layoutParams
        }
    }

    private fun loadCurrentSettings() {
        val currentLimit = ConfigPreferences.getCacheLimitBytes()
        val selectedId = limitOptionsMap.entries.firstOrNull { it.value == currentLimit }?.key ?: R.id.rbUnlimited
        binding.rgLimitOptions.check(selectedId)
    }

    private fun updateAllocationPreview(checkedId: Int) {
        val totalLimit = limitOptionsMap[checkedId] ?: -1L
        
        if (totalLimit <= 0) {
            binding.tvAllocationPreview.text = "无限制 - 不限制缓存大小"
        } else {
            val audioLimit = (totalLimit * 0.7).toLong()
            val otherLimit = (totalLimit * 0.3).toLong()
            
            val preview = """
                总限制: ${formatBytes(totalLimit)}
                ├─ 音频缓存: ${formatBytes(audioLimit)} (70%)
                └─ 其他缓存: ${formatBytes(otherLimit)} (30%)
            """.trimIndent()
            
            binding.tvAllocationPreview.text = preview
        }
    }

    private fun applyLimitSettings() {
        try {
            val selectedLimit = limitOptionsMap[binding.rgLimitOptions.checkedRadioButtonId] ?: -1L
            
            // 保存设置
            ConfigPreferences.setCacheLimitBytes(selectedLimit)
            
            // 显示结果提示
            val message = if (selectedLimit <= 0) {
                "缓存限制已设置为：无限制"
            } else {
                "缓存限制已设置为：${formatBytes(selectedLimit)}"
            }
            toast(message)
            
            // 回调通知
            onLimitSetListener?.invoke(selectedLimit)
            
            dismiss()
            
        } catch (e: Exception) {
            toast("设置失败：${e.message}")
        }
    }

    private fun formatBytes(bytes: Long): String {
        return when {
            bytes < 1024 -> "${bytes}B"
            bytes < 1024 * 1024 -> "${"%.1f".format(bytes / 1024.0)}KB"
            bytes < 1024 * 1024 * 1024 -> "${"%.1f".format(bytes / (1024.0 * 1024.0))}MB"
            else -> "${"%.1f".format(bytes / (1024.0 * 1024.0 * 1024.0))}GB"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
