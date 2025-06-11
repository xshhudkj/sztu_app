/**
 * WhisperPlay Music Player
 *
 * 文件描述：缓存清理确认对话框
 * File Description: Dialog for confirming cache clearing.
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
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.ckn.music.R
import me.ckn.music.databinding.DialogCacheClearConfirmBinding
import me.ckn.music.utils.CacheInfo
import me.ckn.music.utils.SmartCacheManager
import top.wangchenyan.common.ext.toast

/**
 * 缓存清理确认对话框
 * Cache Clear Confirmation Dialog
 *
 * 主要功能：
 * Main Functions:
 * - 显示当前的缓存大小和文件数量 / Displays the current cache size and file count.
 * - 提供确认和取消按钮，防止用户误操作 / Provides confirm and cancel buttons to prevent user misoperation.
 *
 * 使用示例：
 * Usage Example:
 * ```kotlin
 * CacheClearConfirmDialog.newInstance(cacheInfo) {
 *     // 执行缓存清理操作 / Perform cache clearing operation
 * }.show(supportFragmentManager, "CacheClearConfirmDialog")
 * ```
 *
 * @author ckn
 * @since 2025-06-10
 */
class CacheClearConfirmDialog : DialogFragment() {

    private var _binding: DialogCacheClearConfirmBinding? = null
    private val binding get() = _binding!!

    private lateinit var smartCacheManager: SmartCacheManager
    private var onConfirmListener: (() -> Unit)? = null

    companion object {
        /**
         * 创建缓存清理确认对话框的新实例
         * Create a new instance of the cache clear confirmation dialog.
         *
         * @param cacheInfo 当前缓存信息 / Current cache information.
         * @param onConfirm 用户点击确认时的回调 / Callback for when the user clicks confirm.
         * @return [CacheClearConfirmDialog]
         */
        fun newInstance(cacheInfo: CacheInfo, onConfirm: () -> Unit): CacheClearConfirmDialog {
            return CacheClearConfirmDialog().apply {
                arguments = Bundle().apply {
                    putParcelable("cache_info", cacheInfo)
                }
                onConfirmListener = onConfirm
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
        _binding = DialogCacheClearConfirmBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        smartCacheManager = SmartCacheManager(requireContext())
        
        val cacheInfo = arguments?.getParcelable("cache_info") as? CacheInfo
        if (cacheInfo != null) {
            setupViews(cacheInfo)
        } else {
            dismiss()
        }
    }
    
    /**
     * 设置视图和监听器
     */
    private fun setupViews(cacheInfo: CacheInfo) {
        // 显示当前缓存信息
        binding.tvCurrentCacheSize.text = cacheInfo.totalSizeFormatted
        binding.tvFileCount.text = "${cacheInfo.fileCount} 个文件"
        
        // 设置按钮事件
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnConfirm.setOnClickListener {
            onConfirmListener?.invoke()
            dismiss()
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
     * 加载并显示缓存信息
     */
    private fun loadCacheInfo() {
        lifecycleScope.launch {
            try {
                // 从参数中获取缓存信息，如果没有则重新获取
                val cacheInfo = arguments?.getParcelable("cache_info") as? CacheInfo
                    ?: smartCacheManager.getCacheStats()
                
                updateCacheDisplay(cacheInfo)
            } catch (e: Exception) {
                toast("获取缓存信息失败")
                dismiss()
            }
        }
    }
    
    /**
     * 更新缓存信息显示
     */
    private fun updateCacheDisplay(cacheInfo: CacheInfo) {
        binding.apply {
            // 显示当前缓存大小
            tvCurrentCacheSize.text = cacheInfo.totalSizeFormatted
            
            // 显示文件数量
            tvFileCount.text = getString(R.string.cache_file_count_format, cacheInfo.fileCount)
            
            // 根据缓存大小调整确认按钮状态
            if (cacheInfo.totalSize <= 0) {
                btnConfirm.isEnabled = false
                btnConfirm.text = getString(R.string.no_cache_to_clear)
                tvConfirmMessage.text = getString(R.string.no_cache_message)
            } else {
                btnConfirm.isEnabled = true
                btnConfirm.text = getString(R.string.cache_clear_confirm_button)
                tvConfirmMessage.text = getString(R.string.cache_clear_confirm_message)
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
