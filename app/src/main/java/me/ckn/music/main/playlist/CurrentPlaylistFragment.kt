package me.ckn.music.main.playlist

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.text.buildSpannedString
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.ckn.music.R
import me.ckn.music.common.BaseMusicBottomSheetFragment
import me.ckn.music.common.OnItemClickListener2
import me.ckn.music.databinding.FragmentCurrentPlaylistBinding
import me.ckn.music.main.playing.PlayingActivity
import me.ckn.music.service.PlayMode
import me.ckn.music.service.PlayerController
import me.ckn.music.utils.getSongId
import me.wcy.radapter3.RAdapter
import top.wangchenyan.common.ext.getColorEx
import me.ckn.music.common.showImmersiveConfirmDialog
import top.wangchenyan.common.ext.viewBindings
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.widget.CustomSpan.appendStyle
import javax.inject.Inject

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/10/13
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：当前播放列表Fragment
 * File Description: Current playlist Fragment
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@AndroidEntryPoint
class CurrentPlaylistFragment : BaseMusicBottomSheetFragment() {
    private val viewBinding by viewBindings<FragmentCurrentPlaylistBinding>()
    private val adapter by lazy { RAdapter<MediaItem>() }
    private val layoutManager by lazy { LinearLayoutManager(requireContext()) }

    @Inject
    lateinit var playerController: PlayerController

    @Inject
    lateinit var recentPlayRepository: RecentPlayRepository

    @Inject
    lateinit var userService: me.ckn.music.account.service.UserService

    // 播放模式：true为最近播放，false为当前播放列表
    private var isRecentPlayMode = false
    private var recentPlayList: List<MediaItem> = emptyList()

    // 高度状态管理：为不同模式分别保存高度状态
    private var playlistModeHeight: Int = 0
    private var recentPlayModeHeight: Int = 0
    private var currentBehavior: BottomSheetBehavior<View>? = null

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
        initBottomSheetBehavior()
        initView()
        initData()
    }

    private fun initView() {
        viewBinding.llPlayMode.setOnClickListener {
            switchPlayMode()
        }

        viewBinding.llRecentPlay.setOnClickListener {
            switchDisplayMode()
        }

        viewBinding.btnImport.setOnClickListener {
            if (isRecentPlayMode) {
                importNeteaseHistory()
            }
        }

        viewBinding.btnClear.setOnClickListener {
            if (isRecentPlayMode) {
                requireContext().showImmersiveConfirmDialog(message = "确认清空最近播放记录？") {
                    // 清空操作，响应式数据流会自动更新UI
                    recentPlayRepository.clearLocalRecentPlay()
                    Log.d(TAG, "已清空最近播放记录，响应式更新UI")
                }
            } else {
                requireContext().showImmersiveConfirmDialog(message = "确认清空播放列表？") {
                    playerController.clearPlaylist()
                    dismissAllowingStateLoss()
                    ActivityUtils.finishActivity(PlayingActivity::class.java)
                }
            }
        }

        adapter.register(
            CurrentPlaylistItemBinder(
                playerController,
                object : OnItemClickListener2<MediaItem> {
                    override fun onItemClick(item: MediaItem, position: Int) {
                        if (isRecentPlayMode) {
                            // 最近播放模式：点击歌曲时添加到播放列表并播放
                            try {
                                playerController.addAndPlay(item)
                                Log.d(TAG, "最近播放模式：开始播放歌曲 ${item.mediaMetadata.title}")
                            } catch (e: Exception) {
                                Log.e(TAG, "最近播放模式：播放歌曲失败", e)
                                // 播放失败时的处理逻辑可以在这里添加，比如显示错误提示
                            }
                        } else {
                            // 播放列表模式：直接播放
                            try {
                                playerController.play(item.mediaId)
                                Log.d(TAG, "播放列表模式：开始播放歌曲 ${item.mediaMetadata.title}")
                            } catch (e: Exception) {
                                Log.e(TAG, "播放列表模式：播放歌曲失败", e)
                            }
                        }
                    }

                    override fun onMoreClick(item: MediaItem, position: Int) {
                        if (isRecentPlayMode) {
                            // 最近播放模式：从最近播放记录中删除
                            removeFromRecentPlay(item, position)
                        } else {
                            // 播放列表模式：从播放列表中删除
                            playerController.delete(item)
                        }
                    }
                })
        )
        viewBinding.recyclerView.layoutManager = layoutManager
        viewBinding.recyclerView.adapter = adapter
    }

    private fun initData() {
        lifecycleScope.launch {
            playerController.playMode.collectLatest { playMode ->
                viewBinding.ivMode.setImageLevel(playMode.value)
                viewBinding.tvPlayMode.setText(playMode.nameRes)
            }
        }

        // 订阅播放列表变化
        playerController.playlist.observe(this) { playlist ->
            playlist ?: return@observe
            if (!isRecentPlayMode) {
                updateDisplayContent()
            }
        }

        // 订阅最近播放历史变化（响应式数据流）
        lifecycleScope.launch {
            recentPlayRepository.recentPlaySongs.collectLatest { songs ->
                recentPlayList = songs
                if (isRecentPlayMode) {
                    updateDisplayContent()
                    Log.d(TAG, "响应式更新最近播放列表: ${songs.size}首歌曲")
                }
            }
        }

        playerController.currentSong.observe(this) { song ->
            // 更新列表高亮状态
            adapter.notifyDataSetChanged()

            if (song != null) {
                if (isRecentPlayMode) {
                    // 最近播放模式：滚动到当前播放歌曲
                    val index = recentPlayList.indexOfFirst { it.mediaId == song.mediaId }
                    if (index >= 0) {
                        if (index == 0) {
                            layoutManager.scrollToPosition(index)
                        } else if (index > 0) {
                            layoutManager.scrollToPosition(index - 1)
                        }
                        Log.d(TAG, "最近播放模式：滚动到当前播放歌曲，位置: $index")
                    }
                } else {
                    // 播放列表模式：原有逻辑
                    val playlist = playerController.playlist.value
                    if (playlist?.isNotEmpty() == true) {
                        val index = playlist.indexOfFirst { it.mediaId == song.mediaId }
                        if (index == 0) {
                            layoutManager.scrollToPosition(index)
                        } else if (index > 0) {
                            layoutManager.scrollToPosition(index - 1)
                        }
                    }
                }
            }
        }

        // 初始更新显示内容
        updateDisplayContent()
    }

    private fun switchPlayMode() {
        val mode = when (playerController.playMode.value) {
            PlayMode.Loop -> PlayMode.Shuffle
            PlayMode.Shuffle -> PlayMode.Single
            PlayMode.Single -> PlayMode.Loop
        }
        playerController.setPlayMode(mode)
    }

    /**
     * 切换显示模式（播放列表 <-> 最近播放）
     */
    private fun switchDisplayMode() {
        // 保存当前模式的高度状态
        saveCurrentModeHeight()

        // 切换模式
        isRecentPlayMode = !isRecentPlayMode

        // 执行淡入淡出动画
        val fadeOut = ObjectAnimator.ofFloat(viewBinding.recyclerView, "alpha", 1f, 0f)
        fadeOut.duration = 150
        fadeOut.interpolator = AccelerateDecelerateInterpolator()

        fadeOut.addUpdateListener {
            if (it.animatedFraction == 1f) {
                // 更新UI状态
                updateModeButtonUI()
                updateDisplayContent()

                // 恢复新模式的高度状态
                restoreCurrentModeHeight()

                // 自动滚动到当前播放歌曲
                scrollToCurrentSong()

                // 淡入动画
                val fadeIn = ObjectAnimator.ofFloat(viewBinding.recyclerView, "alpha", 0f, 1f)
                fadeIn.duration = 150
                fadeIn.interpolator = AccelerateDecelerateInterpolator()
                fadeIn.start()
            }
        }
        fadeOut.start()
    }

    /**
     * 初始化BottomSheetBehavior，设置高度状态监听
     */
    private fun initBottomSheetBehavior() {
        dialog?.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                currentBehavior = BottomSheetBehavior.from(sheet)
                val displayMetrics = resources.displayMetrics
                val screenHeight = displayMetrics.heightPixels

                // 初始化默认高度
                val defaultHeight = screenHeight * 3 / 4
                if (playlistModeHeight == 0) playlistModeHeight = defaultHeight
                if (recentPlayModeHeight == 0) recentPlayModeHeight = defaultHeight

                // 设置初始高度
                currentBehavior?.peekHeight = if (isRecentPlayMode) recentPlayModeHeight else playlistModeHeight
                currentBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED

                // 添加高度变化监听器
                currentBehavior?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        Log.d(TAG, "BottomSheet状态变化: $newState, 当前模式: ${if(isRecentPlayMode) "最近播放" else "播放列表"}")

                        // 当用户拖拽改变高度时，保存当前模式的高度状态
                        when (newState) {
                            BottomSheetBehavior.STATE_COLLAPSED -> {
                                // 收起状态：保存当前peekHeight作为当前模式的高度
                                saveCurrentModeHeight()
                                Log.d(TAG, "保存${if(isRecentPlayMode) "最近播放" else "播放列表"}模式高度: ${currentBehavior?.peekHeight}")
                            }
                            BottomSheetBehavior.STATE_EXPANDED -> {
                                // 展开状态：记录展开状态，但主要关注收起时的高度
                                Log.d(TAG, "对话框已展开")
                            }
                            BottomSheetBehavior.STATE_DRAGGING -> {
                                // 拖拽开始：记录当前模式，确保拖拽过程中模式不变
                                Log.d(TAG, "开始拖拽，当前模式: ${if(isRecentPlayMode) "最近播放" else "播放列表"}")
                            }
                            BottomSheetBehavior.STATE_SETTLING -> {
                                // 拖拽结束，正在settling：保存最终高度
                                saveCurrentModeHeight()
                                Log.d(TAG, "拖拽结束，保存${if(isRecentPlayMode) "最近播放" else "播放列表"}模式高度: ${currentBehavior?.peekHeight}")
                            }
                        }
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        // 在拖拽过程中实时保存当前模式的高度状态
                        // slideOffset: -1 (hidden) to 0 (collapsed) to 1 (expanded)
                        if (slideOffset >= 0) {
                            // 计算当前实际高度并保存
                            val currentHeight = ((1 - slideOffset) * (currentBehavior?.peekHeight ?: 0) +
                                               slideOffset * bottomSheet.height).toInt()

                            // 实时更新当前模式的高度状态（但不要过于频繁地打印日志）
                            if (isRecentPlayMode) {
                                recentPlayModeHeight = currentHeight
                            } else {
                                playlistModeHeight = currentHeight
                            }
                        }
                    }
                })
            }
        }
    }

    /**
     * 保存当前模式的高度状态
     */
    private fun saveCurrentModeHeight() {
        currentBehavior?.let { behavior ->
            val currentHeight = behavior.peekHeight
            if (isRecentPlayMode) {
                recentPlayModeHeight = currentHeight
            } else {
                playlistModeHeight = currentHeight
            }
        }
    }

    /**
     * 恢复当前模式的高度状态
     */
    private fun restoreCurrentModeHeight() {
        currentBehavior?.let { behavior ->
            val targetHeight = if (isRecentPlayMode) recentPlayModeHeight else playlistModeHeight
            val currentHeight = behavior.peekHeight

            Log.d(TAG, "恢复${if(isRecentPlayMode) "最近播放" else "播放列表"}模式高度: $currentHeight -> $targetHeight")

            // 强制设置新的peekHeight
            behavior.peekHeight = targetHeight

            // 强制重新应用状态，确保高度变化立即生效
            when (behavior.state) {
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    // 如果当前是收起状态，强制重新设置以应用新的peekHeight
                    behavior.state = BottomSheetBehavior.STATE_HIDDEN
                    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
                BottomSheetBehavior.STATE_EXPANDED -> {
                    // 如果当前是展开状态，保持展开但更新peekHeight
                    // peekHeight的变化会在下次收起时生效
                }
                else -> {
                    // 其他状态，强制设置为收起状态
                    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }

            Log.d(TAG, "高度恢复完成，当前状态: ${behavior.state}, peekHeight: ${behavior.peekHeight}")
        }
    }

    /**
     * 更新模式按钮UI
     */
    private fun updateModeButtonUI() {
        if (isRecentPlayMode) {
            viewBinding.ivRecentPlay.setImageResource(R.drawable.ic_playlist)
            viewBinding.tvRecentPlay.text = getString(R.string.playlist_title)

            // 最近播放模式：根据登录状态显示导入按钮
            if (userService.isLogin()) {
                viewBinding.btnImport.visibility = View.VISIBLE
            } else {
                viewBinding.btnImport.visibility = View.GONE
            }
        } else {
            viewBinding.ivRecentPlay.setImageResource(R.drawable.ic_recent_play)
            viewBinding.tvRecentPlay.text = getString(R.string.recent_play)

            // 播放列表模式：隐藏导入按钮
            viewBinding.btnImport.visibility = View.GONE
        }
    }

    /**
     * MediaItem DiffUtil回调，用于智能列表更新
     */
    private class MediaItemDiffCallback(
        private val oldList: List<MediaItem>,
        private val newList: List<MediaItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].mediaId == newList[newItemPosition].mediaId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.mediaId == newItem.mediaId &&
                   oldItem.mediaMetadata.title == newItem.mediaMetadata.title &&
                   oldItem.mediaMetadata.artist == newItem.mediaMetadata.artist
        }
    }

    /**
     * 更新显示内容（使用DiffUtil实现智能更新）
     */
    private fun updateDisplayContent() {
        val currentList = if (isRecentPlayMode) recentPlayList else playerController.playlist.value ?: emptyList()
        val size = currentList.size

        // 更新标题
        viewBinding.tvTitle.text = buildSpannedString {
            append(if (isRecentPlayMode) getString(R.string.recent_play_title) else getString(R.string.playlist_title))
            if (size > 0) {
                appendStyle(
                    "($size)",
                    color = context.getColorEx(R.color.common_text_h2_color),
                    isBold = true
                )
            }
        }

        // 使用DiffUtil智能更新列表
        val oldList = adapter.getDataList()
        if (oldList.isNotEmpty() && currentList.isNotEmpty()) {
            val diffCallback = MediaItemDiffCallback(oldList, currentList)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            adapter.refresh(currentList)
            diffResult.dispatchUpdatesTo(adapter)
            Log.d(TAG, "使用DiffUtil智能更新列表: ${oldList.size} -> ${currentList.size}")
        } else {
            // 空列表或初始化时直接刷新
            adapter.refresh(currentList)
            Log.d(TAG, "直接刷新列表: ${currentList.size}首歌曲")
        }

        // 如果是空列表，显示提示
        if (size == 0 && isRecentPlayMode) {
            // 可以在这里添加空状态提示
        }
    }

    /**
     * 加载最近播放数据
     * @deprecated 已使用响应式数据流替代，无需手动加载
     */
    @Deprecated("使用响应式数据流自动更新")
    private fun loadRecentPlayData() {
        // 此方法已废弃，响应式数据流会自动更新数据
        Log.d(TAG, "loadRecentPlayData已废弃，使用响应式数据流自动更新")
    }

    /**
     * 从最近播放中删除歌曲（响应式更新，无需手动刷新UI）
     */
    private fun removeFromRecentPlay(item: MediaItem, position: Int) {
        lifecycleScope.launch {
            try {
                // 直接从持久化存储中删除歌曲（响应式数据流会自动更新UI）
                val songId = item.getSongId()
                if (songId > 0) {
                    recentPlayRepository.removeFromRecentPlay(songId)
                    Log.d(TAG, "已从最近播放删除歌曲，响应式更新UI: ${item.mediaMetadata.title}")
                } else {
                    Log.w(TAG, "无效的歌曲ID，删除失败: ${item.mediaMetadata.title}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "从最近播放删除歌曲失败", e)
            }
        }
    }

    /**
     * 导入网易云历史（响应式更新，无需手动刷新UI）
     */
    private fun importNeteaseHistory() {
        if (!userService.isLogin()) {
            Log.w(TAG, "用户未登录，无法导入网易云历史")
            return
        }

        lifecycleScope.launch {
            try {
                // 显示加载状态
                Log.d(TAG, "开始导入网易云历史...")

                val result = recentPlayRepository.importFromNetease()
                if (result.success) {
                    // 导入成功，响应式数据流会自动更新UI
                    Log.d(TAG, "网易云历史导入成功，响应式更新UI")

                    // 显示成功提示，包含具体导入数量
                    val message = if (result.importedCount > 0) {
                        "成功导入 ${result.importedCount} 首云端播放历史，已按时间顺序合并到本地历史"
                    } else {
                        "云端播放历史已是最新，无需导入新歌曲"
                    }

                    // 使用Toast显示成功反馈，持续3秒
                    toast(message)
                } else {
                    Log.w(TAG, "网易云历史导入失败")
                    toast("导入失败，请检查网络连接后重试")
                }
            } catch (e: Exception) {
                Log.e(TAG, "导入网易云历史异常", e)
                toast("导入过程中发生错误，请稍后重试")
            }
        }
    }

    /**
     * 滚动到当前播放歌曲
     */
    private fun scrollToCurrentSong() {
        val currentSong = playerController.currentSong.value ?: return

        if (isRecentPlayMode) {
            // 最近播放模式
            val index = recentPlayList.indexOfFirst { it.mediaId == currentSong.mediaId }
            if (index >= 0) {
                if (index == 0) {
                    layoutManager.scrollToPosition(index)
                } else if (index > 0) {
                    layoutManager.scrollToPosition(index - 1)
                }
                Log.d(TAG, "切换到最近播放模式：滚动到当前播放歌曲，位置: $index")
            }
        } else {
            // 播放列表模式
            val playlist = playerController.playlist.value
            if (playlist?.isNotEmpty() == true) {
                val index = playlist.indexOfFirst { it.mediaId == currentSong.mediaId }
                if (index == 0) {
                    layoutManager.scrollToPosition(index)
                } else if (index > 0) {
                    layoutManager.scrollToPosition(index - 1)
                }
                Log.d(TAG, "切换到播放列表模式：滚动到当前播放歌曲，位置: $index")
            }
        }
    }

    /**
     * 刷新最近播放数据（供外部调用）
     * @deprecated 已使用响应式数据流替代，无需手动刷新
     */
    @Deprecated("使用响应式数据流自动更新")
    fun refreshRecentPlayData() {
        // 此方法已废弃，响应式数据流会自动更新数据
        Log.d(TAG, "refreshRecentPlayData已废弃，使用响应式数据流自动更新")
    }

    companion object {
        const val TAG = "CurrentPlaylistFragment"
        fun newInstance(): CurrentPlaylistFragment {
            return CurrentPlaylistFragment()
        }
    }
}