package me.ckn.music.discover.playlist.detail.item

import me.ckn.music.common.OnItemClickListener2
import me.ckn.music.common.bean.SongData
import me.ckn.music.databinding.ItemPlaylistSongBinding
import me.ckn.music.utils.VipUtils
import me.ckn.music.utils.getSimpleArtist
import me.wcy.radapter3.RItemBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.ckn.music.utils.TimeUtils
import top.wangchenyan.common.CommonApp
import android.widget.Toast
import android.app.Application
import me.ckn.music.service.PlayServiceModule.likeSongProcessor

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/22
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：歌单歌曲列表项绑定器
 * File Description: Playlist song item binder
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class PlaylistSongItemBinder(private val listener: OnItemClickListener2<SongData>) :
    RItemBinder<ItemPlaylistSongBinding, SongData>() {

    override fun onBind(viewBinding: ItemPlaylistSongBinding, item: SongData, position: Int) {
        // 恢复 root 的点击事件用于item点击
        viewBinding.root.setOnClickListener {
            listener.onItemClick(item, position)
        }
        viewBinding.ivMore.setOnClickListener {
            listener.onMoreClick(item, position)
        }
        viewBinding.tvIndex.text = (position + 1).toString()
        viewBinding.tvTitle.text = item.name

        // 使用优化的VIP标签更新方法，提升渲染性能
        VipUtils.updateVipLabelOptimized(viewBinding.tvVipLabel, item.fee)

        viewBinding.tvSubTitle.text = buildString {
            append(item.getSimpleArtist())
            append(" - ")
            append(item.al.name)
            item.originSongSimpleData?.let { originSong ->
                append(" | 原唱: ")
                append(originSong.artists.joinToString("/") { it.name })
            }
        }

        // 专辑
        viewBinding.tvAlbum.text = item.al.name

        // 时长（毫秒转mm:ss）
        viewBinding.tvDuration.text = TimeUtils.formatMs(item.dt)

        // 喜欢按钮
        val appContext = viewBinding.root.context.applicationContext as Application
        val likeSongProcessor = appContext.likeSongProcessor()
        viewBinding.ivLike.isSelected = likeSongProcessor.isLiked(item.id)
        viewBinding.ivLike.setOnClickListener {
            val activity = viewBinding.root.context as? android.app.Activity ?: return@setOnClickListener
            CoroutineScope(Dispatchers.Main).launch {
                val res = likeSongProcessor.like(activity, item.id)
                if (res.isSuccess()) {
                    // 刷新红心选中状态
                    viewBinding.ivLike.isSelected = likeSongProcessor.isLiked(item.id)
                } else {
                    Toast.makeText(activity, res.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}