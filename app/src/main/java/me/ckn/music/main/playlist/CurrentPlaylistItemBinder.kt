package me.ckn.music.main.playlist

import android.annotation.SuppressLint
import androidx.media3.common.MediaItem
import me.ckn.music.common.OnItemClickListener2
import me.ckn.music.databinding.ItemCurrentPlaylistBinding
import me.ckn.music.service.PlayerController
import me.wcy.radapter3.RItemBinder

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/4
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：当前播放列表项绑定器
 * File Description: Current playlist item binder
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class CurrentPlaylistItemBinder(
    private val playerController: PlayerController,
    private val listener: OnItemClickListener2<MediaItem>
) :
    RItemBinder<ItemCurrentPlaylistBinding, MediaItem>() {
    @SuppressLint("SetTextI18n")
    override fun onBind(viewBinding: ItemCurrentPlaylistBinding, item: MediaItem, position: Int) {
        // 修复高亮逻辑：使用 mediaId 比较而非对象引用比较，确保最近播放模式下高亮正常
        val currentSong = playerController.currentSong.value
        viewBinding.root.isSelected = (currentSong != null && currentSong.mediaId == item.mediaId)

        viewBinding.root.setOnClickListener {
            listener.onItemClick(item, position)
        }
        viewBinding.tvTitle.text = item.mediaMetadata.title
        viewBinding.tvArtist.text = " · ${item.mediaMetadata.artist}"
        viewBinding.ivDelete.setOnClickListener {
            listener.onMoreClick(item, position)
        }
    }
}