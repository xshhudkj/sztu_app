package me.wcy.music.login.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import cn.bertsir.zbar.utils.QRUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.wcy.music.R
import me.wcy.music.account.AccountApi
import me.wcy.music.account.bean.LoginResultData
import me.wcy.music.account.service.UserService
import me.wcy.music.common.ImmersiveDialogHelper
import me.wcy.music.databinding.DialogQrLoginBinding
import top.wangchenyan.common.net.apiCall
import top.wangchenyan.common.utils.ToastUtils

/**
 * 二维码登录对话框
 * 
 * 完全基于现有的QrcodeLoginFragment实现
 */
class QrCodeLoginDialog(
    context: Context,
    private val userService: UserService,
    private val onLoginResult: (Boolean) -> Unit
) : Dialog(context, R.style.Theme_Material3_DayNight_Dialog_Alert) {

    private lateinit var binding: DialogQrLoginBinding
    private var qrCodeKey = ""
    private val _loginStatus = MutableStateFlow<LoginResultData?>(null)
    private var job: Job? = null
    private val dialogScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = DialogQrLoginBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        // 设置对话框尺寸，适合横屏显示
        window?.let { window ->
            val layoutParams = window.attributes
            layoutParams.width = (context.resources.displayMetrics.widthPixels * 0.5).toInt() // 屏幕宽度的50%
            layoutParams.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            window.attributes = layoutParams
        }

        // 应用全屏沉浸式模式
        ImmersiveDialogHelper.enableImmersiveForDialog(this)

        initViews()
        initDataObserver()
        loadQrCode()
    }

    private fun initViews() {
        binding.btnCancel.setOnClickListener {
            job?.cancel()
            dismiss()
        }

        binding.btnRefresh.setOnClickListener {
            loadQrCode()
        }
    }

    private fun initDataObserver() {
        dialogScope.launch {
            _loginStatus.collect { status ->
                binding.tvTip.setOnClickListener(null)
                if (status == null) {
                    binding.tvTip.text = "正在加载二维码..."
                } else {
                    when (status.code) {
                        LoginResultData.STATUS_NOT_SCAN -> {
                            binding.tvTip.text = "打开网易云音乐APP，点击右上角+，选择扫一扫扫描上方二维码"
                        }

                        LoginResultData.STATUS_SCANNING -> {
                            binding.tvTip.text = "「${status.nickname}」授权中..."
                        }

                        LoginResultData.STATUS_SUCCESS -> {
                            binding.tvTip.text = "登录成功"
                            getProfile(status.cookie)
                        }

                        LoginResultData.STATUS_INVALID -> {
                            binding.tvTip.text = "二维码已失效，点击刷新"
                            binding.tvTip.setOnClickListener {
                                loadQrCode()
                            }
                        }

                        else -> {
                            binding.tvTip.text = status.message.ifEmpty { "二维码错误，点击刷新" }
                            binding.tvTip.setOnClickListener {
                                loadQrCode()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadQrCode() {
        job?.cancel()
        job = GlobalScope.launch(Dispatchers.Default) {
            qrCodeKey = ""
            binding.ivQrCode.post {
                binding.progressBar.visibility = View.VISIBLE
                binding.ivQrCode.visibility = View.GONE
            }
            _loginStatus.value = null
            
            val getKeyRes = apiCall {
                AccountApi.get().getQrCodeKey()
            }
            if (getKeyRes.isSuccessWithData().not()) {
                _loginStatus.value = LoginResultData(-1, "获取二维码失败")
                binding.progressBar.post { 
                    binding.progressBar.visibility = View.GONE 
                }
                return@launch
            }
            
            val keyData = getKeyRes.getDataOrThrow()
            qrCodeKey = keyData.unikey
            
            val getQrCodeRes = apiCall {
                AccountApi.get().getLoginQrCode(qrCodeKey)
            }
            if (getQrCodeRes.isSuccessWithData().not()) {
                _loginStatus.value = LoginResultData(-1, "生成二维码失败")
                binding.progressBar.post { 
                    binding.progressBar.visibility = View.GONE 
                }
                return@launch
            }
            
            val qrCodeData = getQrCodeRes.getDataOrThrow()
            val qrCodeBitmap = QRUtils.getInstance().createQRCode(qrCodeData.qrurl)
            
            binding.ivQrCode.post {
                binding.ivQrCode.setImageBitmap(qrCodeBitmap)
                binding.progressBar.visibility = View.GONE
                binding.ivQrCode.visibility = View.VISIBLE
            }

            // 开始轮询检查登录状态
            while (true) {
                kotlin.runCatching {
                    AccountApi.get().checkLoginStatus(qrCodeKey)
                }.onSuccess { status ->
                    _loginStatus.value = status
                    if (status.code == LoginResultData.STATUS_NOT_SCAN
                        || status.code == LoginResultData.STATUS_SCANNING
                    ) {
                        delay(3000)
                    } else {
                        return@launch
                    }
                }.onFailure {
                    _loginStatus.value = LoginResultData(-1, it.message ?: "检查登录状态失败")
                    return@launch
                }
            }
        }
    }

    private fun getProfile(cookie: String) {
        dialogScope.launch {
            val res = userService.login(cookie)
            if (res.isSuccessWithData()) {
                ToastUtils.show("二维码登录成功")
                onLoginResult(true)
                dismiss()
            } else {
                binding.tvTip.text = "登录失败，点击重试"
                binding.tvTip.setOnClickListener {
                    loadQrCode()
                }
            }
        }
    }

    override fun dismiss() {
        job?.cancel()
        dialogScope.cancel()
        super.dismiss()
    }
} 