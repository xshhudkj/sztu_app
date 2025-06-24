package me.ckn.music.discover.ranking.discover.item

import android.view.LayoutInflater
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import com.blankj.utilcode.util.SizeUtils
import top.wangchenyan.common.ext.context
import me.ckn.music.common.bean.PlaylistData
import me.ckn.music.databinding.ItemDiscoverRankingBinding
import me.ckn.music.databinding.ItemDiscoverRankingSongBinding
import me.ckn.music.utils.ImageUtils.loadCover
import me.ckn.music.utils.getSimpleArtist
import me.wcy.radapter3.RItemBinder

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/10/19
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：发现页排行榜列表项绑定器
 * File Description: Discover ranking item binder
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class DiscoverRankingItemBinder(private val listener: OnItemClickListener) :
    RItemBinder<ItemDiscoverRankingBinding, PlaylistData>() {
    override fun onBind(
        viewBinding: ItemDiscoverRankingBinding,
        item: PlaylistData,
        position: Int
    ) {
        viewBinding.root.setOnClickListener {
            listener.onItemClick(item, position)
        }
        viewBinding.tvName.text = item.name
        if (viewBinding.llSongContainer.isEmpty()) {
            for (i in 0 until 3) {
                ItemDiscoverRankingSongBinding.inflate(
                    LayoutInflater.from(viewBinding.context),
                    viewBinding.llSongContainer,
                    true
                )
            }
        }

        for (i in 0 until 3) {
            val itemBinding = ItemDiscoverRankingSongBinding.bind(viewBinding.llSongContainer[i])
            item.songList.getOrNull(i)?.also { songItem ->
                itemBinding.root.isVisible = true
                itemBinding.root.setOnClickListener {
                    listener.onSongClick(item, i)
                }
                itemBinding.ivCover.loadCover(songItem.al.getSmallCover(), SizeUtils.dp2px(4f))
                itemBinding.tvRank.text = (i + 1).toString()
                itemBinding.tvTitle.text = songItem.name
                itemBinding.tvSubTitle.text = songItem.getSimpleArtist()
            } ?: {
                itemBinding.root.isVisible = false
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: PlaylistData, position: Int)
        fun onSongClick(item: PlaylistData, songPosition: Int)
    }
}