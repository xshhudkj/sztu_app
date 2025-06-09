package me.wcy.music.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.wcy.music.R
import me.wcy.music.databinding.DialogCacheClearConfirmBinding
import me.wcy.music.utils.CacheInfo
import me.wcy.music.utils.SmartCacheManager
import top.wangchenyan.common.ext.toast

/**
 * 缓存清理确认对话框
 * 
 * 功能特点：
 * 1. 显示当前缓存信息（大小、文件数量）
 * 2. 清理前确认，避免误操作
 * 3. 现代化Material Design风格
 * 4. Android Automotive横屏优化
 * 
 * Created for Cache Management Enhancement
 */
class CacheClearConfirmDialog : DialogFragment() {
    
    private var _binding: DialogCacheClearConfirmBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var smartCacheManager: SmartCacheManager
    private var onConfirmListener: (() -> Unit)? = null
    
    companion object {
        /**
         * 创建缓存清理确认对话框实例
         * @param cacheInfo 当前缓存信息
         * @param onConfirm 确认清理回调
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
