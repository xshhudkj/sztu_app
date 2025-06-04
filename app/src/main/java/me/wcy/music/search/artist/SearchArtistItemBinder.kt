package me.wcy.music.search.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import me.wcy.music.common.bean.ArtistData
import me.wcy.music.consts.RoutePath
import me.wcy.music.databinding.ItemSearchArtistBinding
import me.wcy.music.utils.ImageUtils.loadCover
import me.wcy.radapter3.RItemBinder
import me.wcy.router.CRouter

/**
 * 搜索歌手ItemBinder
 * Created by wangchenyan.top on 2024/12/20.
 */
class SearchArtistItemBinder : RItemBinder<ItemSearchArtistBinding, ArtistData>() {

    override fun onBind(viewBinding: ItemSearchArtistBinding, item: ArtistData, position: Int) {
        viewBinding.tvArtistName.text = item.name
        viewBinding.tvArtistAlias.text = if (item.alias.isNotEmpty()) {
            item.alias.joinToString(" / ")
        } else {
            "歌手"
        }
        viewBinding.ivArtistCover.loadCover(item.picUrl, 8)

        viewBinding.root.setOnClickListener {
            // 跳转到歌手详情页面
            CRouter.with(viewBinding.root.context)
                .url(RoutePath.ARTIST_DETAIL)
                .extra("id", item.id)
                .start()
        }
    }
}
