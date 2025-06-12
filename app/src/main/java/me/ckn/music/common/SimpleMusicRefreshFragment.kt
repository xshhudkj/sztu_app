package me.ckn.music.common

import com.kingja.loadsir.callback.Callback
import top.wangchenyan.common.ui.fragment.SimpleRefreshFragment
import me.ckn.music.widget.loadsir.SoundWaveLoadingCallback

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/15
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：简单的音乐刷新Fragment
 * File Description: Simple music refresh Fragment
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
abstract class SimpleMusicRefreshFragment<T> : SimpleRefreshFragment<T>() {

    override fun getLoadingCallback(): Callback {
        return SoundWaveLoadingCallback()
    }

    override fun showLoadSirLoading() {
        loadService?.showCallback(SoundWaveLoadingCallback::class.java)
    }
}