package me.ckn.music.common.dialog.songmenu.items

import android.view.View
import me.ckn.music.common.bean.SongData
import me.ckn.music.common.dialog.songmenu.MenuItem
import top.wangchenyan.common.ext.toast
import me.ckn.music.consts.RoutePath
import me.wcy.router.CRouter

/**
 * Created by wangchenyan.top on 2023/10/11.
 */
class AlbumMenuItem(private val songData: SongData) : MenuItem {
    override val name: String
        get() = "专辑: ${songData.al.name}"

    override fun onClick(view: View) {
        val albumId = songData.al.id
        if (albumId != 0L) {
            val context = view.context
            val activity = context as? android.app.Activity
            CRouter.with(activity ?: context)
                .url(RoutePath.ALBUM_DETAIL)
                .extra("id", albumId)
                .start()
        } else {
            toast("未找到专辑信息")
        }
    }
}