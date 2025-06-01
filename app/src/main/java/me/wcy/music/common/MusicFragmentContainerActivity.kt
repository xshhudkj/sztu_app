package me.wcy.music.common

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import top.wangchenyan.common.ui.activity.FragmentContainerActivity

/**
 * Created by wangchenyan.top on 2023/8/7.
 */
@AndroidEntryPoint
class MusicFragmentContainerActivity : FragmentContainerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // 在super.onCreate之前应用全屏沉浸式，确保从一开始就是全屏状态
        ImmersiveUtils.enableImmersiveMode(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        // 确保在onResume时重新应用全屏沉浸式
        ImmersiveUtils.enableImmersiveMode(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // 当窗口获得焦点时重新应用全屏沉浸式
            ImmersiveUtils.enableImmersiveMode(this)
        }
    }
}