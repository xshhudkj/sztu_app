package me.ckn.music.search.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import me.ckn.music.common.bean.ArtistData
import me.ckn.music.consts.RoutePath
import me.ckn.music.databinding.ItemSearchArtistBinding
import me.ckn.music.utils.ImageUtils.loadCover
import me.wcy.radapter3.RItemBinder
import me.wcy.router.CRouter

/**
 * 搜索歌手ItemBinder
 *
 * Original: Created by wangchenyan.top on 2024/12/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：搜索歌手列表项绑定器
 * File Description: Search artist item binder
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
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
            // 跳转到歌手详情页，使用Activity context保证Fragment返回栈
            val context = viewBinding.root.context
            val activity = context as? android.app.Activity
            CRouter.with(activity ?: context)
                .url(RoutePath.ARTIST_DETAIL)
                .extra("id", item.id)
                .start()
        }
    }
}
