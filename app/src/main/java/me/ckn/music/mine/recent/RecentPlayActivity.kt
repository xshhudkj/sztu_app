package me.ckn.music.mine.recent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.ckn.music.main.playlist.RecentPlayRepository
import me.ckn.music.main.playlist.CurrentPlaylistItemBinder
import me.wcy.radapter3.RAdapter
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.media3.common.MediaItem
import me.ckn.music.service.PlayerController
import javax.inject.Inject
import androidx.recyclerview.widget.RecyclerView
import me.ckn.music.R
import me.ckn.music.utils.getSongId
import me.ckn.music.utils.getFee
import me.ckn.music.utils.toSongEntity
import me.ckn.music.utils.toMediaItem
import android.view.View

@AndroidEntryPoint
class RecentPlayActivity : AppCompatActivity() {
    private lateinit var adapter: RAdapter<MediaItem>

    @Inject lateinit var recentPlayRepository: RecentPlayRepository
    @Inject lateinit var playerController: PlayerController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置全屏显示
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )
        setContentView(R.layout.activity_recent_play)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerRecentPlay)
        // 临时去掉titleLayout和ivBack相关代码，避免因找不到返回按钮导致闪退
        // val titleLayout = findViewById<View>(R.id.titleLayout)
        // titleLayout.findViewById<View>(R.id.ivBack)?.setOnClickListener { finish() }

        adapter = RAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.register(CurrentPlaylistItemBinder(playerController, object : me.ckn.music.common.OnItemClickListener2<MediaItem> {
            override fun onItemClick(item: MediaItem, position: Int) {
                // 实现点击播放逻辑
                val songId = item.getSongId()
                val fee = item.getFee()
                lifecycleScope.launch {
                    try {
                        val res = me.ckn.music.discover.DiscoverApi.get().getSongUrl(songId, "standard")
                        val url = if (res.isSuccessWithData() && res.getDataOrThrow().isNotEmpty()) {
                            res.getDataOrThrow().first().url
                        } else {
                            ""
                        }
                        if (url.isNotEmpty()) {
                            val entity = item.toSongEntity().copy(uri = url)
                            val playable = entity.toMediaItem()
                            playerController.addToNextAndPlay(playable)
                            android.widget.Toast.makeText(this@RecentPlayActivity, "正在播放: ${item.mediaMetadata.title}", android.widget.Toast.LENGTH_SHORT).show()
                            // 可选：跳转到播放页
                            // me.wcy.router.CRouter.with(this@RecentPlayActivity).url(me.ckn.music.consts.RoutePath.PLAYING).start()
                        } else {
                            android.widget.Toast.makeText(this@RecentPlayActivity, "无法获取播放链接，可能无版权或网络异常", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        android.widget.Toast.makeText(this@RecentPlayActivity, "播放失败，请检查网络", android.widget.Toast.LENGTH_SHORT).show()
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

        lifecycleScope.launch {
            recentPlayRepository.recentPlaySongs.collectLatest { songs ->
                adapter.refresh(songs)
            }
        }
    }
} 