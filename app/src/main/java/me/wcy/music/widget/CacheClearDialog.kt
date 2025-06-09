package me.wcy.music.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.wcy.music.R
import me.wcy.music.databinding.DialogCacheClearBinding
import me.wcy.music.utils.SmartCacheManager
import top.wangchenyan.common.ext.toast

/**
 * 缓存清理对话框
 * 简洁版本，清理后不闪退，保持在对话框内
 */
class CacheClearDialog : DialogFragment() {

    private var _binding: DialogCacheClearBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var smartCacheManager: SmartCacheManager

    companion object {
        fun newInstance(): CacheClearDialog {
            return CacheClearDialog()
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
        _binding = DialogCacheClearBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        smartCacheManager = SmartCacheManager(requireContext())

        setupViews()
        loadCacheInfo()
    }

    /**
     * 设置视图和监听器
     */
    private fun setupViews() {
        // 取消按钮
        binding.btnCancel.setOnClickListener { dismiss() }

        // 清理按钮
        binding.btnClear.setOnClickListener { performCacheClear() }

        // 清理选项变化监听
        binding.rgClearOptions.setOnCheckedChangeListener { _, checkedId ->
            updateFreedSpaceEstimate(checkedId)
        }

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
     * 加载缓存信息
     */
    private fun loadCacheInfo() {
        lifecycleScope.launch {
            try {
                val cacheInfo = smartCacheManager.getCacheStats()
                binding.tvCacheSize.text = cacheInfo.totalSizeFormatted
                
                // 更新可释放空间估算
                updateFreedSpaceEstimate(binding.rgClearOptions.checkedRadioButtonId)
            } catch (e: Exception) {
                binding.tvCacheSize.text = "获取失败"
                binding.tvFreedSpace.text = "计算失败"
            }
        }
    }

    /**
     * 更新可释放空间估算
     * 修正：使用智能单位格式化，而非硬编码MB单位
     */
    private fun updateFreedSpaceEstimate(checkedId: Int) {
        lifecycleScope.launch {
            try {
                val cacheInfo = smartCacheManager.getCacheStats()
                val estimatedBytes = when (checkedId) {
                    R.id.rbClearAll -> cacheInfo.totalSize
                    R.id.rbClearAudio -> (cacheInfo.totalSize * 0.7).toLong()
                    R.id.rbClearImages -> (cacheInfo.totalSize * 0.3).toLong()
                    R.id.rbClearOld -> (cacheInfo.totalSize * 0.5).toLong()
                    else -> 0L
                }
                binding.tvFreedSpace.text = formatBytes(estimatedBytes)
            } catch (e: Exception) {
                binding.tvFreedSpace.text = "计算失败"
            }
        }
    }

    /**
     * 智能格式化字节大小
     * 自动选择合适的单位（B、KB、MB、GB）
     */
    private fun formatBytes(bytes: Long): String {
        return when {
            bytes >= 1024 * 1024 * 1024 -> String.format("%.1f GB", bytes / (1024.0 * 1024.0 * 1024.0))
            bytes >= 1024 * 1024 -> String.format("%.1f MB", bytes / (1024.0 * 1024.0))
            bytes >= 1024 -> String.format("%.1f KB", bytes / 1024.0)
            else -> "$bytes B"
        }
    }

    /**
     * 执行缓存清理
     */
    private fun performCacheClear() {
        lifecycleScope.launch {
            try {
                // 禁用清理按钮防止重复点击
                binding.btnClear.isEnabled = false
                binding.btnClear.text = "清理中..."

                val clearResult = when (binding.rgClearOptions.checkedRadioButtonId) {
                    R.id.rbClearAll -> {
                        smartCacheManager.clearAllCache()
                    }
                    R.id.rbClearAudio -> {
                        smartCacheManager.clearAudioCache()
                    }
                    R.id.rbClearImages -> {
                        smartCacheManager.clearImageCache()
                    }
                    R.id.rbClearOld -> {
                        smartCacheManager.clearOldCache(7) // 清理7天前的缓存
                    }
                    else -> {
                        smartCacheManager.clearAllCache()
                    }
                }

                if (clearResult.success) {
                    toast("清理成功！释放空间：${clearResult.freedSpaceFormatted}")
                    
                    // 清理完成后重新加载缓存信息，保持在对话框内
                    loadCacheInfo()
                } else {
                    toast("清理失败：${clearResult.errorMessage}")
                }

                // 恢复清理按钮状态
                binding.btnClear.isEnabled = true
                binding.btnClear.text = "清理"

            } catch (e: Exception) {
                toast("清理失败：${e.message}")
                
                // 恢复清理按钮状态
                binding.btnClear.isEnabled = true
                binding.btnClear.text = "清理"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 