package me.ckn.music.common.dialog.songmenu

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import com.blankj.utilcode.util.SizeUtils
import top.wangchenyan.common.widget.dialog.BottomDialog
import top.wangchenyan.common.widget.dialog.BottomDialogBuilder
import me.ckn.music.common.enableImmersiveMode
import me.ckn.music.common.bean.SongData
import me.ckn.music.databinding.DialogSongMoreMenuBinding
import me.ckn.music.databinding.ItemSongMoreMenuBinding
import me.ckn.music.storage.db.entity.SongEntity
import me.ckn.music.utils.ImageUtils.loadCover
import me.ckn.music.utils.getSimpleArtist

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/10/11
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：歌曲更多菜单对话框
 * File Description: Song more menu dialog
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class SongMoreMenuDialog {
    private val context: Context
    private var songEntity: SongEntity? = null
    private var songData: SongData? = null
    private val items = mutableListOf<MenuItem>()

    constructor(context: Context, songEntity: SongEntity) {
        this.context = context
        this.songEntity = songEntity
    }

    constructor(context: Context, songData: SongData) {
        this.context = context
        this.songData = songData
    }

    fun setItems(items: List<MenuItem>) = apply {
        this.items.apply {
            clear()
            addAll(items)
        }
    }

    fun show() {
        val dialog = BottomDialogBuilder(context)
            .contentViewBinding { dialog: BottomDialog, viewBinding: DialogSongMoreMenuBinding ->
                bindSongInfo(viewBinding)
                bindMenus(dialog, viewBinding)
            }
            .cancelable(true)
            .build()

        // 应用全屏沉浸式模式，确保Dialog符合官方最佳实践
        dialog.enableImmersiveMode()
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun bindSongInfo(viewBinding: DialogSongMoreMenuBinding) {
        val songEntity = songEntity
        val songData = songData
        if (songEntity != null) {
            viewBinding.ivCover.loadCover(songEntity.getSmallCover(), SizeUtils.dp2px(4f))
            viewBinding.tvTitle.text = "歌曲: ${songEntity.title}"
            viewBinding.tvArtist.text = songEntity.artist
        } else if (songData != null) {
            viewBinding.ivCover.loadCover(songData.al.getSmallCover(), SizeUtils.dp2px(4f))
            viewBinding.tvTitle.text = "歌曲: ${songData.name}"
            viewBinding.tvArtist.text = songData.getSimpleArtist()
        }
    }

    private fun bindMenus(dialog: BottomDialog, viewBinding: DialogSongMoreMenuBinding) {
        viewBinding.menuContainer.removeAllViews()
        items.forEach { item ->
            ItemSongMoreMenuBinding.inflate(
                LayoutInflater.from(context),
                viewBinding.menuContainer,
                true
            ).apply {
                root.text = item.name
                root.setOnClickListener {
                    dialog.dismiss()
                    item.onClick(it)
                }
            }
        }
    }
}