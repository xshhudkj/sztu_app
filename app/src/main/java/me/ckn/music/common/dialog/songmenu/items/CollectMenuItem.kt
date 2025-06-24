package me.ckn.music.common.dialog.songmenu.items

import android.view.View
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CoroutineScope
import me.ckn.music.common.bean.SongData
import me.ckn.music.common.dialog.songmenu.MenuItem
import me.ckn.music.mine.collect.song.CollectSongFragment
import top.wangchenyan.common.ext.findActivity

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/10/11
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：收藏菜单项
 * File Description: Collect menu item
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class CollectMenuItem(
    private val scope: CoroutineScope,
    private val songData: SongData
) : MenuItem {
    override val name: String
        get() = "收藏到歌单"

    override fun onClick(view: View) {
        val activity = view.context.findActivity()
        if (activity is FragmentActivity) {
            CollectSongFragment.newInstance(songData.id)
                .show(activity.supportFragmentManager, CollectSongFragment.TAG)
        }
    }
}