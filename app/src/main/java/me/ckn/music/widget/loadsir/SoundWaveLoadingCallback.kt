/**
 * WhisperPlay Music Player
 *
 * 文件描述：LoadSir加载状态回调，显示声波动画。
 * File Description: LoadSir loading status callback, displaying a sound wave animation.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.widget.loadsir

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import me.ckn.music.R

/**
 * 声波加载回调
 * Sound Wave Loading Callback
 *
 * 主要功能：
 * Main Functions:
 * - 为LoadSir提供一个自定义的加载中视图，该视图包含一个声波动画。
 *   Provides a custom loading view for LoadSir, which contains a sound wave animation.
 *
 * @author ckn
 * @since 2025-06-10
 */
class SoundWaveLoadingCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.load_sir_loading_sound_wave
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}