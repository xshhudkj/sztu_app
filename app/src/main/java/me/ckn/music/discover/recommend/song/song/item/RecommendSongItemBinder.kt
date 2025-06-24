package me.ckn.music.discover.recommend.song.item

import androidx.core.view.isVisible
import com.blankj.utilcode.util.SizeUtils
import me.ckn.music.common.OnItemClickListener2
import me.ckn.music.common.bean.SongData
import me.ckn.music.databinding.ItemRecommendSongBinding
import me.ckn.music.utils.ImageUtils.loadCover
import me.ckn.music.utils.getSimpleArtist
import me.wcy.radapter3.RItemBinder

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/15
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：每日推荐歌曲列表项绑定器
 * File Description: Recommend song item binder
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class RecommendSongItemBinder(private val listener: OnItemClickListener2<SongData>) :
    RItemBinder<ItemRecommendSongBinding, SongData>() {

    override fun onBind(viewBinding: ItemRecommendSongBinding, item: SongData, position: Int) {
        viewBinding.root.setOnClickListener {
            listener.onItemClick(item, position)
        }
        viewBinding.ivMore.setOnClickListener {
            listener.onMoreClick(item, position)
        }
        viewBinding.ivCover.loadCover(item.al.getSmallCover(), SizeUtils.dp2px(4f))
        viewBinding.tvTitle.text = item.name
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