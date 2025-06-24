package me.ckn.music.ext

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import dagger.hilt.android.EntryPointAccessors

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/7/12
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：Context扩展函数
 * File Description: Context extension functions
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */

inline fun <reified T : Any> Application.accessEntryPoint(): T {
    return EntryPointAccessors.fromApplication(this, T::class.java)
}

fun Context.registerReceiverCompat(receiver: BroadcastReceiver, filter: IntentFilter) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED)
    } else {
        registerReceiver(receiver, filter)
    }
}
