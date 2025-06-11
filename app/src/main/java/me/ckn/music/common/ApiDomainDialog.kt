package me.ckn.music.common

import android.content.Context
import androidx.core.text.buildSpannedString
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.RegexUtils
import kotlinx.coroutines.launch
import me.ckn.music.R
import me.ckn.music.account.AccountPreference
import me.ckn.music.databinding.DialogApiDomainBinding
import me.ckn.music.net.NetCache
import me.ckn.music.storage.preference.ConfigPreferences
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.ext.getColorEx
import top.wangchenyan.common.ext.setLink
import top.wangchenyan.common.ext.showConfirmDialog
import top.wangchenyan.common.ext.showSingleDialog
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.utils.LaunchUtils
import top.wangchenyan.common.widget.CustomSpan.appendStyle
import top.wangchenyan.common.widget.dialog.CenterDialog
import top.wangchenyan.common.widget.dialog.CenterDialogBuilder

/**
 * Created by wangchenyan.top on 2023/9/18.
 */
class ApiDomainDialog(private val context: Context) {

    fun show() {
        val dialog = CenterDialogBuilder(context)
            .title("请输入云音乐API域名")
            .contentViewBinding { _: CenterDialog, viewBinding: DialogApiDomainBinding ->
                viewBinding.tvDoc.setLink()
                viewBinding.tvDoc.text = buildSpannedString {
                    append("点击查看")
                    appendStyle(
                        "云音乐API文档",
                        color = context.getColorEx(R.color.common_theme_color)
                    ) {
                        LaunchUtils.launchBrowser(
                            context,
                            "https://binaryify.github.io/NeteaseCloudMusicApi"
                        )
                    }
                }
                if (ConfigPreferences.apiDomain.isNotEmpty()) {
                    viewBinding.etInput.hint = ConfigPreferences.apiDomain
                }
            }
            .buttonText(
                context.getString(R.string.common_confirm),
                context.getString(R.string.common_cancel)
            )
            .onButtonClickListener { dialog, which ->
                if (which == 0) {
                    val domain =
                        dialog.getContentViewBinding<DialogApiDomainBinding>()?.etInput?.text?.toString()
                    if (RegexUtils.isURL(domain).not()) {
                        toast("请输入正确的域名")
                    } else if (domain!!.endsWith("/").not()) {
                        toast("域名需要以'/'结尾")
                    } else {
                        ConfigPreferences.apiDomain = domain
                        AccountPreference.clear()
                        CommonApp.appScope.launch {
                            NetCache.userCache.clear()
                        }
                        dialog.dismiss()
                        context.showImmersiveConfirmDialog(
                            message = "设置成功，重启后生效",
                            confirmButton = "确定",
                            cancelButton = ""
                        ) {
                            AppUtils.relaunchApp(true)
                        }
                    }
                } else {
                    dialog.dismiss()
                }
            }
            .isAutoClose(false)
            .build()

        // 应用全屏沉浸式模式
        ImmersiveDialogHelper.enableImmersiveForDialog(dialog)
        dialog.show()
    }

    companion object {
        fun checkApiDomain(context: Context): Boolean {
            return if (ConfigPreferences.apiDomain.isEmpty()) {
                context.showImmersiveConfirmDialog(
                    message = "请先设置云音乐API域名"
                ) {
                    ApiDomainDialog(context).show()
                }
                false
            } else {
                true
            }
        }
    }
}