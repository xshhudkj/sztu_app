package me.ckn.music.mine.recent

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.ckn.music.R
import me.ckn.music.common.BaseMusicFragment
import me.ckn.music.main.playlist.RecentPlayRepository
import me.ckn.music.main.playlist.CurrentPlaylistItemBinder
import me.wcy.radapter3.RAdapter
import androidx.media3.common.MediaItem
import me.ckn.music.service.PlayerController
import me.ckn.music.consts.RoutePath
import me.wcy.router.CRouter
import top.wangchenyan.common.ext.viewBindings
import me.ckn.music.databinding.FragmentRecentPlayBinding
import javax.inject.Inject
import android.util.Log
import me.ckn.music.discover.DiscoverApi
import me.ckn.music.common.bean.SongData
import me.ckn.music.utils.toSongEntity
import me.ckn.music.utils.getSongId
import me.ckn.music.utils.getFee
import me.ckn.music.utils.toMediaItem
import android.widget.Toast

@AndroidEntryPoint
class RecentPlayFragment : BaseMusicFragment() {
    private val viewBinding by viewBindings<FragmentRecentPlayBinding>()
    private lateinit var adapter: RAdapter<MediaItem>

    @Inject lateinit var recentPlayRepository: RecentPlayRepository
    @Inject lateinit var playerController: PlayerController

    override fun getRootView(): View {
        return viewBinding.root
    }

    override fun onLazyCreate() {
        super.onLazyCreate()

        initTitle()
        initRecyclerView()
        loadData()

        getTitleLayout()?.findViewById<View>(R.id.ivBack)?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mineDetailContainer, me.ckn.music.mine.home.MineDetailPlaceholderFragment())
                .commit()
        }
    }

    private fun initTitle() {
        getTitleLayout()?.setTitleText("最近播放")
    }

    private fun initRecyclerView() {
        adapter = RAdapter()
        viewBinding.recyclerRecentPlay.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.recyclerRecentPlay.adapter = adapter
        adapter.register(CurrentPlaylistItemBinder(playerController, object : me.ckn.music.common.OnItemClickListener2<MediaItem> {
            override fun onItemClick(item: MediaItem, position: Int) {
                // 先获取真实播放url再播放
                val songId = item.getSongId()
                val fee = item.getFee()
                Log.d("RecentPlayFragment", "点击播放: songId=$songId, title=${item.mediaMetadata.title}")
                lifecycleScope.launch {
                    try {
                        val res = DiscoverApi.get().getSongUrl(songId, "standard")
                        val url = if (res.isSuccessWithData() && res.getDataOrThrow().isNotEmpty()) {
                            res.getDataOrThrow().first().url
                        } else {
                            ""
                        }
                        if (url.isNotEmpty()) {
                            // 构造带真实url的MediaItem
                            val entity = item.toSongEntity().copy(uri = url)
                            val playable = entity.toMediaItem()
                            playerController.addToNextAndPlay(playable)
                            CRouter.with(requireContext()).url(RoutePath.PLAYING).start()
                            Toast.makeText(requireContext(), "正在播放: ${item.mediaMetadata.title}", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "无法获取播放链接，可能无版权或网络异常", Toast.LENGTH_SHORT).show()
                            Log.w("RecentPlayFragment", "获取播放链接失败: songId=$songId, title=${item.mediaMetadata.title}")
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "播放失败，请检查网络", Toast.LENGTH_SHORT).show()
                        Log.e("RecentPlayFragment", "播放异常", e)
                    }
                }
            }
            override fun onMoreClick(item: MediaItem, position: Int) {
                val songId = item.getSongId()
                if (songId > 0) {
                    lifecycleScope.launch {
                        recentPlayRepository.removeFromRecentPlay(songId)
                    }
                }
            }
        }))
    }

    private fun loadData() {
        lifecycleScope.launch {
            recentPlayRepository.recentPlaySongs.collectLatest { songs ->
                adapter.refresh(songs)
            }
        }
    }
} 