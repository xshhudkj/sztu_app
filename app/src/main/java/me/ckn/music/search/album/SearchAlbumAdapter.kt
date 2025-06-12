package me.ckn.music.search.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.ckn.music.common.bean.AlbumData
import me.ckn.music.databinding.ItemSearchAlbumBinding
import me.ckn.music.search.SearchAdapterBase
import me.ckn.music.utils.ImageUtils.loadCover

/**
 * 搜索专辑适配器
 *
 * Original: Created by wangchenyan.top on 2024/12/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：搜索专辑列表适配器
 * File Description: Search album list adapter
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class SearchAlbumAdapter : SearchAdapterBase<AlbumData, SearchAlbumAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchAlbumBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemSearchAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: AlbumData) {
            binding.tvAlbumName.text = album.name
            binding.tvArtistName.text = "专辑"
            binding.ivAlbumCover.loadCover(album.picUrl, 8)
            
            binding.root.setOnClickListener {
                // TODO: 跳转到专辑详情页面
            }
        }
    }
}
