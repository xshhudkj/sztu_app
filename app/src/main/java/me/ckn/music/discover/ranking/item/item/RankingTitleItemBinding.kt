package me.ckn.music.discover.ranking.item

import me.ckn.music.databinding.ItemRankingTitleBinding
import me.ckn.music.discover.ranking.viewmodel.RankingViewModel
import me.wcy.radapter3.RItemBinder

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/10/25
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：排行榜标题项绑定
 * File Description: Ranking title item binding
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class RankingTitleItemBinding : RItemBinder<ItemRankingTitleBinding, RankingViewModel.TitleData>() {
    override fun onBind(
        viewBinding: ItemRankingTitleBinding,
        item: RankingViewModel.TitleData,
        position: Int
    ) {
        viewBinding.root.text = item.title
    }
}