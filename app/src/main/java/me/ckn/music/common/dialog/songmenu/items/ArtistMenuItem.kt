package me.ckn.music.common.dialog.songmenu.items

import android.view.View
import me.ckn.music.common.bean.SongData
import me.ckn.music.common.dialog.songmenu.MenuItem
import me.ckn.music.utils.getSimpleArtist
import top.wangchenyan.common.ext.toast
import me.ckn.music.consts.RoutePath
import me.wcy.router.CRouter

/**
 * Created by wangchenyan.top on 2023/10/11.
 */
class ArtistMenuItem(private val songData: SongData) : MenuItem {
    override val name: String
        get() = "歌手: ${songData.getSimpleArtist()}"

    override fun onClick(view: View) {
        val artistId = songData.ar.firstOrNull()?.id ?: 0L
        if (artistId != 0L) {
            val context = view.context
            val activity = context as? android.app.Activity
            CRouter.with(activity ?: context)
                .url(RoutePath.ARTIST_DETAIL)
                .extra("id", artistId)
                .start()
        } else {
            toast("未找到歌手信息")
        }
    }
}