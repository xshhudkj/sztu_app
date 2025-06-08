package me.wcy.music.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.wcy.music.R
import me.wcy.music.account.login.phone.PhoneLoginViewModel
import me.wcy.music.account.service.UserService
import me.wcy.music.common.BaseMusicActivity
import me.wcy.music.consts.RoutePath
import me.wcy.music.databinding.ActivityLoginBinding
import me.wcy.music.login.dialog.PhoneLoginDialog
import me.wcy.music.login.dialog.QrCodeLoginDialog
import me.wcy.music.main.MainActivity
import me.wcy.router.annotation.Route
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.ext.viewBindings
import top.wangchenyan.common.utils.ToastUtils
import javax.inject.Inject

/**
 * 独立登录页面
 * 
 * 功能：
 * 1. 提供游客登录、二维码登录、手机验证码登录三种方式
 * 2. 严格遵循MVVM架构模式
 * 3. 支持Android Automotive全屏沉浸式体验
 * 4. 登录成功后跳转到主界面
 * 
 * 设计原则：
 * - 大字体、大触摸目标，适合车载环境
 * - 现代化UI设计，符合Material Design 3规范
 * - 简洁的交互流程，减少驾驶分心
 * 
 * Created for Android Automotive Login Module
 */
@Route(RoutePath.LOGIN)
@AndroidEntryPoint
class LoginActivity : BaseMusicActivity() {
    private val binding by viewBindings<ActivityLoginBinding>()
    private val phoneLoginViewModel by viewModels<PhoneLoginViewModel>()

    @Inject
    lateinit var userService: UserService

    private var qrCodeDialog: QrCodeLoginDialog? = null
    private var phoneLoginDialog: PhoneLoginDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        // 游客登录
        binding.btnGuestLogin.setOnClickListener {
            ToastUtils.show("游客登录成功")
            navigateToMain()
        }

        // 二维码登录
        binding.btnQrCodeLogin.setOnClickListener {
            showQrCodeLogin()
        }

        // 手机号登录
        binding.btnPhoneLogin.setOnClickListener {
            showPhoneLogin()
        }
    }

    private fun showQrCodeLogin() {
        if (qrCodeDialog?.isShowing == true) {
            return
        }
        
        qrCodeDialog?.dismiss()
        qrCodeDialog = QrCodeLoginDialog(this, userService) { success ->
            if (success) {
                handleLoginSuccess()
            }
        }
        qrCodeDialog?.show()
    }

    private fun showPhoneLogin() {
        if (phoneLoginDialog?.isShowing == true) {
            return
        }
        
        phoneLoginDialog?.dismiss()
        phoneLoginDialog = PhoneLoginDialog(this, phoneLoginViewModel) { success ->
            if (success) {
                handleLoginSuccess()
            }
        }
        phoneLoginDialog?.show()
    }

    private fun handleLoginSuccess() {
        navigateToMain()
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        // 在登录页面按返回键退出应用
        super.onBackPressed()
        finishAffinity()
    }

    override fun onDestroy() {
        super.onDestroy()
        qrCodeDialog?.dismiss()
        phoneLoginDialog?.dismiss()
    }
} 