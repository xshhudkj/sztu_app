package me.ckn.music.mine.local

import me.ckn.music.common.OnItemClickListener2
import me.ckn.music.databinding.ItemLocalSongBinding
import me.ckn.music.storage.db.entity.SongEntity
import me.ckn.music.utils.MusicUtils
import me.wcy.radapter3.RItemBinder

/**
 * Created by wangchenyan.top on 2023/8/30.
 */
class LocalSongItemBinder(
    private val listener: OnItemClickListener2<SongEntity>
) : RItemBinder<ItemLocalSongBinding, SongEntity>() {

    override fun onBind(viewBinding: ItemLocalSongBinding, item: SongEntity, position: Int) {
        viewBinding.root.setOnClickListener {
            listener.onItemClick(item, position)
        }
        viewBinding.ivMore.setOnClickListener {
            listener.onMoreClick(item, position)
        }
        viewBinding.tvTitle.text = item.title
        viewBinding.tvArtist.text = MusicUtils.getArtistAndAlbum(item.artist, item.album)
    }
}