package me.ckn.music.service

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/18
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：播放状态
 * File Description: Play state
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
sealed class PlayState {
    object Idle : PlayState()
    object Preparing : PlayState()
    object Playing : PlayState()
    object Pause : PlayState()

    val isIdle: Boolean
        get() = this is Idle
    val isPreparing: Boolean
        get() = this is Preparing
    val isPlaying: Boolean
        get() = this is Playing
    val isPausing: Boolean
        get() = this is Pause
}
