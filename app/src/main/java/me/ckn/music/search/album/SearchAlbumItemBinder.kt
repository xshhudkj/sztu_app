package me.ckn.music.search.album

import android.view.LayoutInflater
import android.view.ViewGroup
import me.ckn.music.common.bean.AlbumData
import me.ckn.music.consts.RoutePath
import me.ckn.music.databinding.ItemSearchAlbumBinding
import me.ckn.music.utils.ImageUtils.loadCover
import me.wcy.radapter3.RItemBinder
import me.wcy.router.CRouter

/**
 * 搜索专辑ItemBinder
 *
 * Original: Created by wangchenyan.top on 2024/12/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：搜索专辑列表项绑定器
 * File Description: Search album item binder
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class SearchAlbumItemBinder : RItemBinder<ItemSearchAlbumBinding, AlbumData>() {

    override fun onBind(viewBinding: ItemSearchAlbumBinding, item: AlbumData, position: Int) {
        viewBinding.tvAlbumName.text = item.name
        viewBinding.tvArtistName.text = "专辑"
        viewBinding.ivAlbumCover.loadCover(item.picUrl, 8)

        viewBinding.root.setOnClickListener {
            // 跳转到专辑详情页面
            CRouter.with(viewBinding.root.context)
                .url(RoutePath.ALBUM_DETAIL)
                .extra("id", item.id)
                .start()
        }
    }
}
