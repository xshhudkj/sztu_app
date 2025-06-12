package me.ckn.music.search.song

import androidx.core.view.isVisible
import top.wangchenyan.common.ext.context
import me.ckn.music.common.OnItemClickListener2
import me.ckn.music.common.bean.SongData
import me.ckn.music.databinding.ItemSearchSongBinding
import me.ckn.music.utils.MusicUtils
import me.ckn.music.utils.VipUtils
import me.ckn.music.utils.getSimpleArtist
import me.wcy.radapter3.RItemBinder

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：搜索歌曲列表项绑定器
 * File Description: Search song item binder
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class SearchSongItemBinder(private val listener: OnItemClickListener2<SongData>) :
    RItemBinder<ItemSearchSongBinding, SongData>() {
    var keywords = ""

    override fun onBind(viewBinding: ItemSearchSongBinding, item: SongData, position: Int) {
        viewBinding.root.setOnClickListener {
            listener.onItemClick(item, position)
        }
        viewBinding.ivMore.setOnClickListener {
            listener.onMoreClick(item, position)
        }
        viewBinding.tvTitle.text = MusicUtils.keywordsTint(viewBinding.context, item.name, keywords)

        // 使用优化的VIP标签更新方法，提升渲染性能
        VipUtils.updateVipLabelOptimized(viewBinding.tvVipLabel, item.fee)

        viewBinding.tvTag.isVisible = item.recommendReason.isNotEmpty()
        viewBinding.tvTag.text = item.recommendReason
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