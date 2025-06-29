package me.ckn.music.discover.playlist.detail.item

import me.ckn.music.common.OnItemClickListener2
import me.ckn.music.common.bean.SongData
import me.ckn.music.databinding.ItemPlaylistSongBinding
import me.ckn.music.utils.VipUtils
import me.ckn.music.utils.getSimpleArtist
import me.wcy.radapter3.RItemBinder

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
    }
}