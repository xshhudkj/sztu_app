package me.ckn.music.discover.ranking.item

import android.view.LayoutInflater
import androidx.core.text.buildSpannedString
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import com.blankj.utilcode.util.SizeUtils
import top.wangchenyan.common.ext.context
import top.wangchenyan.common.widget.CustomSpan.appendStyle
import me.ckn.music.common.bean.PlaylistData
import me.ckn.music.databinding.ItemOfficialRankingBinding
import me.ckn.music.databinding.ItemOfficialRankingSongBinding
import me.ckn.music.utils.ImageUtils.loadCover
import me.ckn.music.utils.getSimpleArtist
import me.wcy.radapter3.RItemBinder
import kotlin.reflect.KClass

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/10/24
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：官方排行榜列表项绑定器
 * File Description: Official ranking item binder
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class OfficialRankingItemBinder(private val listener: OnItemClickListener) :
    RItemBinder<ItemOfficialRankingBinding, PlaylistData>() {
    override fun onBind(
        viewBinding: ItemOfficialRankingBinding,
        item: PlaylistData,
        position: Int
    ) {
        viewBinding.root.setOnClickListener {
            listener.onItemClick(item, position)
        }
        viewBinding.ivPlay.setOnClickListener {
            listener.onPlayClick(item, position)
        }
        viewBinding.tvName.text = item.name
        viewBinding.tvUpdateTime.text = item.updateFrequency
        viewBinding.ivCover.loadCover(item.getSmallCover(), SizeUtils.dp2px(6f))
        if (viewBinding.llSongContainer.isEmpty()) {
            for (i in 0 until 3) {
                ItemOfficialRankingSongBinding.inflate(
                    LayoutInflater.from(viewBinding.context),
                    viewBinding.llSongContainer,
                    true
                )
            }
        }
        for (i in 0 until 3) {
            val songItem = item.songList.getOrNull(i)
            val itemBinding = ItemOfficialRankingSongBinding.bind(viewBinding.llSongContainer[i])
            if (songItem == null) {
                itemBinding.root.isVisible = false
            } else {
                itemBinding.root.isVisible = true
                itemBinding.tvIndex.text = (i + 1).toString()
                itemBinding.tvTitle.text = buildSpannedString {
                    appendStyle(songItem.name, isBold = true)
                    append(" - ")
                    append(songItem.getSimpleArtist())
                }
            }
        }
    }

    override fun getViewBindingClazz(): KClass<*> {
        return ItemOfficialRankingBinding::class
    }

    interface OnItemClickListener {
        fun onItemClick(item: PlaylistData, position: Int)
        fun onPlayClick(item: PlaylistData, position: Int)
    }
}