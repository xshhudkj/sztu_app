package me.ckn.music.common.dialog.songmenu

import android.view.View

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/10/11
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：歌曲更多菜单项
 * File Description: Song more menu item
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
interface MenuItem {
    val name: String
    fun onClick(view: View)
}

data class SimpleMenuItem(
    override val name: String,
    val onClick: (View) -> Unit = {}
) : MenuItem {
    override fun onClick(view: View) {
        onClick.invoke(view)
    }
}
