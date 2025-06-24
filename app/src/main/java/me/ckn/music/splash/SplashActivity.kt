package me.ckn.music.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.ckn.music.account.service.UserService
import me.ckn.music.common.BaseMusicActivity
import me.ckn.music.databinding.ActivitySplashBinding
import me.ckn.music.login.LoginActivity
import me.ckn.music.main.MainActivity
import top.wangchenyan.common.ext.viewBindings
import javax.inject.Inject

/**
 * 启动欢迎页 - 精美分层动画版本
 *
 * 功能：
 * 1. 显示应用Logo和品牌信息
 * 2. 分层动画效果：Logo → 应用名称 → 欢迎文字
 * 3. 检查用户登录状态
 * 4. 未登录跳转到登录页面，已登录跳转到主界面
 * 5. 支持Android Automotive全屏沉浸式体验
 *
 * 动画设计：
 * - 第一层：Logo图标（立即开始，1.5秒）
 * - 第二层：应用名称（延迟200ms，1.2秒）
 * - 第三层：欢迎文字（延迟400ms，1.0秒）
 *
 * Created for SplashLogin Module
 */
@AndroidEntryPoint
class SplashActivity : BaseMusicActivity() {
    private val viewBinding by viewBindings<ActivitySplashBinding>()
    private val viewModel by viewModels<SplashViewModel>()

    @Inject
    lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        // 检查是否从后台恢复：如果不是任务栈根Activity，说明应用从后台恢复
        if (!isTaskRoot) {
            // 应用从后台恢复，直接finish，让系统恢复到用户之前的页面
            finish()
            return
        }

        // 首次启动或冷启动，显示启动页
        // 在super.onCreate之前确保窗口背景正确
        window.setBackgroundDrawableResource(me.ckn.music.R.drawable.splash_gradient)

        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        // 初始化视图并启动分层动画
        initViewsAndStartAnimation()

        // 检查登录状态并跳转
        checkLoginStatusAndNavigate()
    }

    private fun initViewsAndStartAnimation() {
        // 初始化所有视图为不可见状态
        viewBinding.splashImageView.alpha = 0f
        viewBinding.appNameText.alpha = 0f
        viewBinding.welcomeText.alpha = 0f

        // 启动分层动画
        startLayeredAnimation()
    }

    /**
     * 启动分层动画效果
     * 采用渐进式分层显示的设计理念
     */
    private fun startLayeredAnimation() {
        // 第一层 - Logo图标动画（立即开始，1.5秒）
        val logoAnimator = ObjectAnimator.ofFloat(viewBinding.splashImageView, "alpha", 0f, 1f).apply {
            duration = 1500
            interpolator = DecelerateInterpolator()
            startDelay = 0
        }

        // 第二层 - 应用名称动画（延迟200ms，1.2秒）
        val appNameAnimator = ObjectAnimator.ofFloat(viewBinding.appNameText, "alpha", 0f, 1f).apply {
            duration = 1200
            interpolator = DecelerateInterpolator()
            startDelay = 200
        }

        // 第三层 - 欢迎文字动画（延迟400ms，1.0秒）
        val welcomeAnimator = ObjectAnimator.ofFloat(viewBinding.welcomeText, "alpha", 0f, 1f).apply {
            duration = 1000
            interpolator = DecelerateInterpolator()
            startDelay = 400
        }

        // 添加轻微的缩放效果增强视觉冲击力
        val logoScaleX = ObjectAnimator.ofFloat(viewBinding.splashImageView, "scaleX", 0.8f, 1f).apply {
            duration = 1500
            interpolator = DecelerateInterpolator()
            startDelay = 0
        }

        val logoScaleY = ObjectAnimator.ofFloat(viewBinding.splashImageView, "scaleY", 0.8f, 1f).apply {
            duration = 1500
            interpolator = DecelerateInterpolator()
            startDelay = 0
        }

        // 应用名称从下方滑入效果
        val appNameTranslationY = ObjectAnimator.ofFloat(viewBinding.appNameText, "translationY", 50f, 0f).apply {
            duration = 1200
            interpolator = DecelerateInterpolator()
            startDelay = 200
        }

        // 欢迎文字从下方滑入效果
        val welcomeTranslationY = ObjectAnimator.ofFloat(viewBinding.welcomeText, "translationY", 30f, 0f).apply {
            duration = 1000
            interpolator = DecelerateInterpolator()
            startDelay = 400
        }

        // 组合所有动画
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            logoAnimator,
            logoScaleX,
            logoScaleY,
            appNameAnimator,
            appNameTranslationY,
            welcomeAnimator,
            welcomeTranslationY
        )

        // 启动动画
        animatorSet.start()

        android.util.Log.d("SplashActivity", "分层动画已启动：Logo(0ms,1.5s) → 应用名称(200ms,1.2s) → 欢迎文字(400ms,1.0s)")
    }

    private fun checkLoginStatusAndNavigate() {
        lifecycleScope.launch {
            // 等待动画完成后再跳转（总动画时长约1.4秒，加上额外延时）
            Handler(Looper.getMainLooper()).postDelayed({
                if (userService.isLogin()) {
                    // 已登录，跳转到主界面
                    navigateToMain()
                } else {
                    // 未登录，跳转到登录页面
                    navigateToLogin()
                }
            }, 2000) // 2秒延时，确保动画完整播放
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        // 传递原始intent的extras
        intent.putExtras(getIntent().extras ?: Bundle())
        startActivity(intent)
        finish()

        // 使用淡出动画切换，增强过渡效果
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        // 传递原始intent的extras
        intent.putExtras(getIntent().extras ?: Bundle())
        startActivity(intent)
        finish()

        // 使用淡出动画切换，增强过渡效果
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onBackPressed() {
        // 禁用返回键，防止在启动页返回
        super.onBackPressed()
    }
}