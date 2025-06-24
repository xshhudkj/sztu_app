package me.ckn.music.account.login

import android.view.View
import top.wangchenyan.common.ext.viewBindings
import me.ckn.music.common.BaseMusicFragment
import me.ckn.music.consts.RoutePath
import me.ckn.music.databinding.FragmentLoginRouteBinding
import me.wcy.router.CRouter
import me.wcy.router.RouteResultListener
import me.wcy.router.annotation.Route

/**
 * Created by wangchenyan.top on 2024/1/3.
 * 
 * 已废弃：现在使用LoginActivity代替
 */
// @Route(RoutePath.LOGIN) // 已移除，现在使用LoginActivity
class LoginRouteFragment : BaseMusicFragment() {
    private val viewBinding by viewBindings<FragmentLoginRouteBinding>()

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun isLazy(): Boolean {
        return false
    }

    private val routeResultListener: RouteResultListener = {
        if (it.isSuccess()) {
            setResultAndFinish()
        } else if (it.resultCode == RESULT_SWITCH_QRCODE) {
            startQrCode()
        } else if (it.resultCode == RESULT_SWITCH_PHONE) {
            startPhone()
        } else {
            finish()
        }
    }

    override fun onLazyCreate() {
        super.onLazyCreate()
        startPhone()
    }

    private fun startPhone() {
        CRouter.with(requireActivity())
            .url(RoutePath.PHONE_LOGIN)
            .startForResult(routeResultListener)
    }

    private fun startQrCode() {
        CRouter.with(requireActivity())
            .url(RoutePath.QRCODE_LOGIN)
            .startForResult(routeResultListener)
    }

    companion object {
        const val RESULT_SWITCH_QRCODE = 100
        const val RESULT_SWITCH_PHONE = 200
    }
}