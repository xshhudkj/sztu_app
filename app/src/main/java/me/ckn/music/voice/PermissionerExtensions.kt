package me.ckn.music.voice

import android.Manifest
import android.content.Context
import androidx.annotation.MainThread
import top.wangchenyan.common.permission.Permissioner
import top.wangchenyan.common.permission.PermissionCallback

/**
 * WhisperPlay Music Player - Permissioner扩展
 *
 * 文件描述：为Permissioner添加录音权限支持，保持项目权限管理的一致性
 * File Description: Permissioner extensions for record audio permission
 *
 * @author ckn
 * @since 2025-06-20
 * @version 2.3.0
 */

/**
 * 请求录音权限
 * Request record audio permission
 */
@MainThread
fun Permissioner.requestRecordAudioPermission(
    context: Context,
    callback: PermissionCallback?
) {
    requestPermission(context, Manifest.permission.RECORD_AUDIO, callback)
}

/**
 * 检查是否有录音权限
 * Check if has record audio permission
 */
@MainThread
fun Permissioner.hasRecordAudioPermission(context: Context): Boolean {
    return hasPermissions(context, Manifest.permission.RECORD_AUDIO)
}

/**
 * 请求语音控制所需的所有权限
 * Request all permissions required for voice control
 */
@MainThread
fun Permissioner.requestVoiceControlPermissions(
    context: Context,
    callback: PermissionCallback?
) {
    val permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.MODIFY_AUDIO_SETTINGS
    )
    
    requestPermissions(
        context,
        permissions
    ) { allGranted, grantedList, deniedList, shouldRationale ->
        callback?.invoke(allGranted, shouldRationale)
    }
}

/**
 * 检查是否有语音控制所需的所有权限
 * Check if has all permissions required for voice control
 */
@MainThread
fun Permissioner.hasVoiceControlPermissions(context: Context): Boolean {
    return hasPermissions(
        context,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.MODIFY_AUDIO_SETTINGS
    )
}
