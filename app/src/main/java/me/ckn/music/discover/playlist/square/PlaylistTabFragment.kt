package me.ckn.music.discover.playlist.square

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import top.wangchenyan.common.model.CommonResult
import top.wangchenyan.common.widget.decoration.GridSpacingDecoration
import me.ckn.music.common.SimpleMusicRefreshFragment
import me.ckn.music.common.bean.PlaylistData
import me.ckn.music.consts.Consts
import me.ckn.music.consts.RoutePath
import me.ckn.music.discover.DiscoverApi
import me.ckn.music.discover.playlist.square.item.PlaylistItemBinder
import me.wcy.radapter3.RAdapter
import me.wcy.router.CRouter

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/26
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：歌单广场标签页Fragment
 * File Description: Playlist square tab Fragment
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@AndroidEntryPoint
class PlaylistTabFragment : SimpleMusicRefreshFragment<PlaylistData>() {
    private val cat by lazy {
        getRouteArguments().getStringExtra("tag") ?: ""
    }

    override fun isShowTitle(): Boolean {
        return false
    }

    override fun isRefreshEnabled(): Boolean {
        return false
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(requireContext(), 3)
    }

    override fun initAdapter(adapter: RAdapter<PlaylistData>) {
        val itemWidth = (ScreenUtils.getAppScreenWidth() - SizeUtils.dp2px(52f)) / 3
        adapter.register(
            PlaylistItemBinder(
                itemWidth,
                false,
                object : PlaylistItemBinder.OnItemClickListener {
                    override fun onItemClick(item: PlaylistData) {
                        CRouter.with(requireActivity())
                            .url(RoutePath.PLAYLIST_DETAIL)
                            .extra("id", item.id)
                            .start()
                    }

                    override fun onPlayClick(item: PlaylistData) {
                    }
                })
        )
    }

    override fun onLazyCreate() {
        super.onLazyCreate()
        getRecyclerView().apply {
            val paddingHorizontal = SizeUtils.dp2px(16f)
            val paddingVertical = SizeUtils.dp2px(10f)
            setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)
            clipToPadding = false
            val spacing = SizeUtils.dp2px(10f)
            addItemDecoration(GridSpacingDecoration(spacing, spacing))
        }
    }

    override suspend fun getData(page: Int): CommonResult<List<PlaylistData>> {
        val res = kotlin.runCatching {
            DiscoverApi.get()
                .getPlaylistList(
                    cat,
                    Consts.PAGE_COUNT_GRID,
                    (page - 1) * Consts.PAGE_COUNT_GRID
                )
        }
        return if (res.getOrNull()?.code == 200) {
            CommonResult.success(res.getOrThrow().playlists)
        } else {
            CommonResult.fail(msg = res.exceptionOrNull()?.message)
        }
    }
}