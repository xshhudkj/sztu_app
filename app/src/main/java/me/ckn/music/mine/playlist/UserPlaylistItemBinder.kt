package me.ckn.music.mine.playlist

import androidx.core.view.isVisible
import com.blankj.utilcode.util.SizeUtils
import me.ckn.music.common.bean.PlaylistData
import me.ckn.music.databinding.ItemUserPlaylistBinding
import me.ckn.music.utils.ImageUtils.loadCover
import me.wcy.radapter3.RItemBinder

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/28
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：用户歌单列表项绑定器
 * File Description: User playlist item binder
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class UserPlaylistItemBinder(
    private val isMine: Boolean,
    private val listener: OnItemClickListener
) : RItemBinder<ItemUserPlaylistBinding, PlaylistData>() {

    override fun onBind(viewBinding: ItemUserPlaylistBinding, item: PlaylistData, position: Int) {
        viewBinding.root.setOnClickListener {
            listener.onItemClick(item)
        }
        viewBinding.ivCover.loadCover(item.getSmallCover(), SizeUtils.dp2px(4f))
        viewBinding.tvName.text = item.name
        viewBinding.tvCount.text = if (isMine) {
            "${item.trackCount}首"
        } else {
            "${item.trackCount}首, by ${item.creator.nickname}"
        }
        viewBinding.ivMore.isVisible = isMine.not()
        viewBinding.ivMore.setOnClickListener {
            listener.onMoreClick(item)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: PlaylistData)
        fun onMoreClick(item: PlaylistData)
    }
}