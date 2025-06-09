package me.wcy.music.search.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.wcy.music.common.bean.ArtistData
import me.wcy.music.databinding.ItemSearchArtistBinding
import me.wcy.music.search.SearchAdapterBase
import me.wcy.music.utils.ImageUtils.loadCover

/**
 * 搜索歌手适配器
 * Created by wangchenyan.top on 2024/12/20.
 */
class SearchArtistAdapter : SearchAdapterBase<ArtistData, SearchArtistAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchArtistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemSearchArtistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: ArtistData) {
            binding.tvArtistName.text = artist.name
            binding.tvArtistAlias.text = if (artist.alias.isNotEmpty()) {
                artist.alias.joinToString(" / ")
            } else {
                "歌手"
            }
            binding.ivArtistCover.loadCover("", 8)
            
            binding.root.setOnClickListener {
                // TODO: 跳转到歌手详情页面
            }
        }
    }
}
