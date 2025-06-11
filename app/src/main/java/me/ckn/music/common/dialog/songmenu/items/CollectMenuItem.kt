package me.ckn.music.common.dialog.songmenu.items

import android.view.View
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CoroutineScope
import me.ckn.music.common.bean.SongData
import me.ckn.music.common.dialog.songmenu.MenuItem
import me.ckn.music.mine.collect.song.CollectSongFragment
import top.wangchenyan.common.ext.findActivity

/**
 * Created by wangchenyan.top on 2023/10/11.
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