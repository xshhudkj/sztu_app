package me.ckn.music.consts

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/4/19
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：偏好设置名称
 * File Description: Preference name
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
object PreferenceName {
    val ACCOUNT = "account".assemble()
    val CONFIG = "config".assemble()
    val SEARCH = "search".assemble()

    private fun String.assemble(): String {
        return "music_$this"
    }
}