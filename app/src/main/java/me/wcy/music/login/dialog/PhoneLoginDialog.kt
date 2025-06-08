package me.wcy.music.login.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.widget.doAfterTextChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.wcy.music.R
import me.wcy.music.account.login.phone.PhoneLoginViewModel
import me.wcy.music.common.ImmersiveDialogHelper
import me.wcy.music.databinding.DialogPhoneLoginBinding
import top.wangchenyan.common.utils.ToastUtils

/**
 * 手机号登录对话框
 * 
 * 完全基于现有的PhoneLoginFragment和PhoneLoginViewModel实现
 */
class PhoneLoginDialog(
    context: Context,
    private val viewModel: PhoneLoginViewModel,
    private val onLoginResult: (Boolean) -> Unit
) : Dialog(context, R.style.Theme_Material3_DayNight_Dialog_Alert) {

    private lateinit var binding: DialogPhoneLoginBinding
    private val dialogScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = DialogPhoneLoginBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        // 设置对话框尺寸，适合横屏显示
        window?.let { window ->
            val layoutParams = window.attributes
            layoutParams.width = (context.resources.displayMetrics.widthPixels * 0.6).toInt() // 屏幕宽度的60%
            layoutParams.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            window.attributes = layoutParams
        }

        // 应用全屏沉浸式模式
        ImmersiveDialogHelper.enableImmersiveForDialog(this)

        initViews()
        initDataObserver()
    }

    private fun initViews() {
        val updateLoginBtnState = {
            binding.btnLogin.isEnabled =
                binding.etPhone.length() > 0 && binding.etPhoneCode.length() > 0
        }
        binding.etPhone.doAfterTextChanged {
            updateLoginBtnState()
        }
        binding.etPhoneCode.doAfterTextChanged {
            updateLoginBtnState()
        }
        
        binding.btnSendCode.setOnClickListener {
            val phone = binding.etPhone.text?.toString()
            if (phone.isNullOrEmpty()) {
                ToastUtils.show("请输入手机号")
                return@setOnClickListener
            }
            dialogScope.launch {
                binding.btnSendCode.isEnabled = false
                val res = viewModel.sendPhoneCode(phone)
                if (res.isSuccess().not()) {
                    binding.btnSendCode.isEnabled = true
                    ToastUtils.show(res.msg)
                }
            }
        }
        
        binding.btnLogin.setOnClickListener {
            val phone = binding.etPhone.text?.toString()
            if (phone.isNullOrEmpty()) {
                ToastUtils.show("请输入手机号")
                return@setOnClickListener
            }
            val code = binding.etPhoneCode.text?.toString()
            if (code.isNullOrEmpty()) {
                ToastUtils.show("请输入手机验证码")
                return@setOnClickListener
            }
            dialogScope.launch {
                binding.btnLogin.isEnabled = false
                binding.btnLogin.text = "登录中..."
                val res = viewModel.phoneLogin(phone, code)
                if (res.isSuccess()) {
                    ToastUtils.show("登录成功")
                    onLoginResult(true)
                    dismiss()
                } else {
                    binding.btnLogin.isEnabled = true
                    binding.btnLogin.text = "登录"
                    ToastUtils.show(res.msg.orEmpty().ifEmpty {
                        "登录失败，请更新服务端版本或稍后重试"
                    })
                }
            }
        }
        
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun initDataObserver() {
        dialogScope.launch {
            viewModel.sendPhoneCodeCountdown.collectLatest { sendPhoneCodeCountdown ->
                if (sendPhoneCodeCountdown > 0) {
                    binding.btnSendCode.isEnabled = false
                    binding.btnSendCode.text = "${sendPhoneCodeCountdown}秒后重发"
                } else {
                    binding.btnSendCode.isEnabled = true
                    binding.btnSendCode.text = "获取验证码"
                }
            }
        }
    }

    override fun dismiss() {
        dialogScope.cancel()
        super.dismiss()
    }
} 