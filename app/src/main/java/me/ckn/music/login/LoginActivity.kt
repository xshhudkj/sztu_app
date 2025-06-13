package me.ckn.music.login

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.ckn.music.R
import me.ckn.music.account.login.phone.PhoneLoginViewModel
import me.ckn.music.account.service.UserService
import me.ckn.music.common.BaseMusicActivity
import me.ckn.music.consts.RoutePath
import me.ckn.music.databinding.ActivityLoginBinding
import me.ckn.music.login.dialog.PhoneLoginDialog
import me.ckn.music.login.dialog.QrCodeLoginDialog
import me.ckn.music.main.MainActivity
import me.wcy.router.annotation.Route
import top.wangchenyan.common.permission.Permissioner
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
        requestNotificationPermission()
    }

    private fun setupViews() {
        // 游客登录 - 移除Toast提示，添加视觉反馈和平滑过渡
        binding.btnGuestLogin.setOnClickListener { view ->
            // 添加按钮点击视觉反馈
            addClickFeedback(view) {
                navigateToMainWithAnimation()
            }
        }

        // 二维码登录 - 添加视觉反馈
        binding.btnQrCodeLogin.setOnClickListener { view ->
            addClickFeedback(view) {
                showQrCodeLogin()
            }
        }

        // 手机号登录 - 添加视觉反馈
        binding.btnPhoneLogin.setOnClickListener { view ->
            addClickFeedback(view) {
                showPhoneLogin()
            }
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
        navigateToMainWithAnimation()
    }

    /**
     * 请求通知权限
     * 在登录页面请求通知权限，确保用户能够接收到音乐播放通知
     * 权限被拒绝时不影响登录流程的正常进行
     */
    private fun requestNotificationPermission() {
        Permissioner.requestNotificationPermission(this) { granted, shouldRationale ->
            if (granted) {
                // 通知权限已授予，应用可以正常显示音乐播放通知
            } else {
                // 通知权限被拒绝，应用仍然可以正常运行
                // 不阻塞登录流程，用户可以继续使用应用
            }
        }
    }

    /**
     * 带动画效果的主界面跳转
     * 提供流畅的视觉过渡体验，消除空白跳转
     */
    private fun navigateToMainWithAnimation() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        // 创建淡入淡出过渡动画
        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.fade_in,   // 新Activity淡入
            R.anim.fade_out   // 当前Activity淡出
        )

        startActivity(intent, options.toBundle())
        finish()
    }

    /**
     * 添加按钮点击视觉反馈
     * 提供更好的用户体验，确保用户知道按钮已被点击
     */
    private fun addClickFeedback(view: View, action: () -> Unit) {
        // 添加缩放动画作为点击反馈
        view.animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(100)
            .withEndAction {
                view.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(100)
                    .withEndAction {
                        // 动画完成后执行实际操作
                        action()
                    }
                    .start()
            }
            .start()
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