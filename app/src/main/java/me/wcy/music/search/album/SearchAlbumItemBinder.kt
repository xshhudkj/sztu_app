package me.wcy.music.search.album

import android.view.LayoutInflater
import android.view.ViewGroup
import me.wcy.music.common.bean.AlbumData
import me.wcy.music.consts.RoutePath
import me.wcy.music.databinding.ItemSearchAlbumBinding
import me.wcy.music.utils.ImageUtils.loadCover
import me.wcy.radapter3.RItemBinder
import me.wcy.router.CRouter

/**
 * 搜索专辑ItemBinder
 * Created by wangchenyan.top on 2024/12/20.
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
