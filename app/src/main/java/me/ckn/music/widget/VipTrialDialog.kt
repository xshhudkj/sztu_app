/**
 * WhisperPlay Music Player
 *
 * 文件描述：VIP试听提示对话框
 * File Description: Dialog for VIP trial prompt.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.widget

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import me.ckn.music.databinding.DialogVipTrialBinding

/**
 * VIP试听提示对话框
 * VIP Trial Prompt Dialog
 *
 * 主要功能：
 * Main Functions:
 * - 提示用户试听已结束 / Informs the user that the trial has ended.
 * - 提供开通VIP或播放下一首的选项 / Provides options to open VIP or play the next song.
 * - 倒计时后自动播放下一首 / Automatically plays the next song after a countdown.
 *
 * 使用示例：
 * Usage Example:
 * ```kotlin
 * VipTrialDialog.newInstance()
 *     .setOnVipClickListener { /* 处理开通VIP逻辑 */ }
 *     .setOnNextClickListener { /* 处理播放下一首逻辑 */ }
 *     .show(supportFragmentManager, "VipTrialDialog")
 * ```
 *
 * @author ckn
 * @since 2025-06-10
 */
class VipTrialDialog : DialogFragment() {

    companion object {
        private const val COUNTDOWN_DURATION = 3000L
        private const val COUNTDOWN_INTERVAL = 1000L

        /**
         * 创建VIP试听提示对话框的新实例
         * Create a new instance of the VIP trial prompt dialog.
         *
         * @return [VipTrialDialog]
         */
        fun newInstance(): VipTrialDialog {
            return VipTrialDialog()
        }
    }

    private var _binding: DialogVipTrialBinding? = null
    private val binding get() = _binding!!
    
    private var countDownTimer: CountDownTimer? = null
    
    // 回调函数
    private var onVipClickListener: (() -> Unit)? = null
    private var onNextClickListener: (() -> Unit)? = null
    private var onDismissListener: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogVipTrialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        startCountdown()
    }

    private fun setupViews() {
        // 设置标题和消息
        binding.tvTitle.text = "试听结束"
        binding.tvMessage.text = "您正在试听VIP歌曲，完整版请开通VIP会员"
        
        // 开通VIP按钮
        binding.btnVip.setOnClickListener {
            onVipClickListener?.invoke()
            dismiss()
        }
        
        // 播放下一首按钮
        binding.btnNext.setOnClickListener {
            onNextClickListener?.invoke()
            dismiss()
        }
        
        // 点击外部区域关闭
        dialog?.setCanceledOnTouchOutside(true)
    }

    private fun startCountdown() {
        countDownTimer = object : CountDownTimer(COUNTDOWN_DURATION, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000).toInt() + 1
                binding.tvCountdown.text = "${seconds}秒后自动播放下一首"
            }

            override fun onFinish() {
                binding.tvCountdown.text = "正在播放下一首..."
                onNextClickListener?.invoke()
                dismiss()
            }
        }
        countDownTimer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        countDownTimer = null
        _binding = null
    }

    override fun onDismiss(dialog: android.content.DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke()
    }

    /**
     * 设置开通VIP按钮点击监听器
     */
    fun setOnVipClickListener(listener: () -> Unit): VipTrialDialog {
        this.onVipClickListener = listener
        return this
    }

    /**
     * 设置播放下一首按钮点击监听器
     */
    fun setOnNextClickListener(listener: () -> Unit): VipTrialDialog {
        this.onNextClickListener = listener
        return this
    }

    /**
     * 设置对话框关闭监听器
     */
    fun setOnDismissListener(listener: () -> Unit): VipTrialDialog {
        this.onDismissListener = listener
        return this
    }
}
