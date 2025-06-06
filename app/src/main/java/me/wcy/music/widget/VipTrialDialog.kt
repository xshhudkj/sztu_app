package me.wcy.music.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import me.wcy.music.databinding.DialogVipTrialBinding

/**
 * VIP试听提示对话框
 * 当非VIP用户播放VIP歌曲到达试听终点时显示
 * 3秒后自动关闭并切换到下一首歌曲
 * 
 * 功能说明：
 * 1. 显示VIP试听限制提示信息
 * 2. 提供开通VIP和播放下一首的选项
 * 3. 3秒倒计时后自动关闭
 * 4. 支持手动关闭和按钮点击事件
 */
class VipTrialDialog : DialogFragment() {

    companion object {
        private const val COUNTDOWN_DURATION = 3000L
        private const val COUNTDOWN_INTERVAL = 1000L
        
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
