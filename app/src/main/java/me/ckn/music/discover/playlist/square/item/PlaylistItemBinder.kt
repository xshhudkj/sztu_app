package me.ckn.music.discover.playlist.square.item

import androidx.core.view.isVisible
import com.blankj.utilcode.util.SizeUtils
import me.ckn.music.common.bean.PlaylistData
import me.ckn.music.databinding.ItemDiscoverPlaylistBinding
import me.ckn.music.utils.ConvertUtils
import me.ckn.music.utils.ImageUtils.loadCover
import me.wcy.radapter3.RItemBinder

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/25
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：歌单列表项绑定器
 * File Description: Playlist item binder
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class PlaylistItemBinder(
    private val itemWidth: Int,
    private val showPlayButton: Boolean,
    private val listener: OnItemClickListener
) : RItemBinder<ItemDiscoverPlaylistBinding, PlaylistData>() {

    override fun onBind(
        viewBinding: ItemDiscoverPlaylistBinding,
        item: PlaylistData,
        position: Int
    ) {
        viewBinding.root.setOnClickListener {
            listener.onItemClick(item)
        }
        viewBinding.ivPlay.isVisible = showPlayButton
        viewBinding.ivPlay.setOnClickListener {
            listener.onPlayClick(item)
        }
        val lp = viewBinding.ivCover.layoutParams
        lp.width = itemWidth
        lp.height = itemWidth
        viewBinding.ivCover.layoutParams = lp
        viewBinding.ivCover.loadCover(item.getSmallCover(), SizeUtils.dp2px(6f))
        viewBinding.tvPlayCount.text = ConvertUtils.formatPlayCount(item.playCount)
        viewBinding.tvName.text = item.name
    }

    interface OnItemClickListener {
        fun onItemClick(item: PlaylistData)
        fun onPlayClick(item: PlaylistData)
    }
}