package me.wcy.music.common

import android.os.Bundle
import com.kingja.loadsir.callback.Callback
import me.wcy.music.utils.ScreenAdaptManager
import me.wcy.music.widget.loadsir.SoundWaveLoadingCallback
import top.wangchenyan.common.ui.activity.BaseActivity

/**
 * Created by wangchenyan.top on 2023/9/4.
 */
abstract class BaseMusicActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // 在super.onCreate之前应用全屏沉浸式，确保从一开始就是全屏状态
        ImmersiveUtils.enableImmersiveMode(this)
        // 初始化屏幕适配
        ScreenAdaptManager.init(this)
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

    override fun getLoadingCallback(): Callback {
        return SoundWaveLoadingCallback()
    }

    override fun showLoadSirLoading() {
        loadService?.showCallback(SoundWaveLoadingCallback::class.java)
    }

    companion object {
        private const val TAG = "BaseMusicActivity"
    }
}