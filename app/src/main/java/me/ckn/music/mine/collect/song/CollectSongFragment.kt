package me.ckn.music.mine.collect.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.ckn.music.R
import me.ckn.music.common.BaseMusicBottomSheetFragment
import me.ckn.music.common.bean.PlaylistData
import me.ckn.music.databinding.FragmentCollectSongBinding
import me.ckn.music.mine.playlist.UserPlaylistItemBinder
import me.wcy.radapter3.RAdapter
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.ext.viewBindings
import top.wangchenyan.common.widget.decoration.SpacingDecoration

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2024/3/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：收藏歌曲Fragment
 * File Description: Collect song Fragment
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@AndroidEntryPoint
class CollectSongFragment : BaseMusicBottomSheetFragment() {
    private val viewBinding by viewBindings<FragmentCollectSongBinding>()
    private val viewModel: CollectSongViewModel by viewModels()
    private val adapter by lazy { RAdapter<PlaylistData>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val songId = arguments?.getLong("song_id") ?: 0
        if (songId <= 0) {
            toast("参数错误")
            dismissAllowingStateLoss()
            return
        }

        viewModel.songId = songId

        initView()
        initData()
        lifecycleScope.launch {
            viewModel.getMyPlayList()
        }
    }

    private fun initView() {
        adapter.register(
            UserPlaylistItemBinder(
                true,
                object : UserPlaylistItemBinder.OnItemClickListener {
                    override fun onItemClick(item: PlaylistData) {
                        collectSong(item.id)
                    }

                    override fun onMoreClick(item: PlaylistData) {
                    }
                })
        )
        val spacingDecoration = SpacingDecoration(SizeUtils.dp2px(10f))
        viewBinding.recyclerView.addItemDecoration(spacingDecoration)
        viewBinding.recyclerView.adapter = adapter
    }

    private fun initData() {
        lifecycleScope.launch {
            viewModel.myPlaylists.collectLatest {
                adapter.refresh(it)
            }
        }
    }

    private fun collectSong(pid: Long) {
        lifecycleScope.launch {
            val res = viewModel.collectSong(pid)
            if (res.isSuccess()) {
                toast("操作成功")
                dismissAllowingStateLoss()
            } else {
                toast(res.msg)
            }
        }
    }

    companion object {
        const val TAG = "CollectSongFragment"

        fun newInstance(songId: Long): CollectSongFragment {
            return CollectSongFragment().apply {
                arguments = bundleOf("song_id" to songId)
            }
        }
    }
}