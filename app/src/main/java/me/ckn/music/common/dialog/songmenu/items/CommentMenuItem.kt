package me.ckn.music.common.dialog.songmenu.items

import android.content.Intent
import android.view.View
import me.ckn.music.common.bean.SongData
import me.ckn.music.common.dialog.songmenu.MenuItem
import me.ckn.music.comment.CommentActivity
import top.wangchenyan.common.ext.toast

/**
 * Created by wangchenyan.top on 2023/10/11.
 */
class CommentMenuItem(private val songData: SongData) : MenuItem {
    override val name: String
        get() = "评论"

    override fun onClick(view: View) {
        val context = view.context
        val activity = context as? android.app.Activity
        val intent = Intent(activity ?: context, CommentActivity::class.java)
        (activity ?: context).startActivity(intent)
    }
}