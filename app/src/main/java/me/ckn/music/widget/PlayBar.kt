package me.ckn.music.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet

import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.text.buildSpannedString
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.ckn.music.R
import me.ckn.music.consts.RoutePath
import me.ckn.music.databinding.LayoutPlayBarBinding
import me.ckn.music.ext.addButtonAnimation
import me.ckn.music.ext.addListItemAnimation
import me.ckn.music.ext.addPlayControlAnimation
import me.ckn.music.main.playlist.CurrentPlaylistFragment
import me.ckn.music.service.PlayServiceModule.playerController
import me.ckn.music.service.PlayServiceModule.likeSongProcessor
import me.ckn.music.service.PlayState
import me.ckn.music.utils.VipUtils
import me.ckn.music.utils.getDuration
import me.ckn.music.utils.getSongId
import me.ckn.music.utils.getSmallCover
import me.ckn.music.utils.isLocal
import me.wcy.router.CRouter
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.ext.findActivity
import top.wangchenyan.common.ext.findLifecycleOwner
import top.wangchenyan.common.ext.loadAvatar
import top.wangchenyan.common.ext.toast

/**
 * Created by wangchenyan.top on 2023/9/4.
 */
class PlayBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val viewBinding: LayoutPlayBarBinding
    private val playerController by lazy {
        CommonApp.app.playerController()
    }
    private val likeSongProcessor by lazy {
        CommonApp.app.likeSongProcessor()
    }
    private val rotateAnimator: ObjectAnimator

    init {
        id = R.id.play_bar
        viewBinding = LayoutPlayBarBinding.inflate(LayoutInflater.from(context), this, true)

        rotateAnimator = ObjectAnimator.ofFloat(viewBinding.ivCover, "rotation", 0f, 360f).apply {
            duration = 20000
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ObjectAnimator.RESTART
            interpolator = LinearInterpolator()
        }

        initView()
        context.findLifecycleOwner()?.let {
            initData(it)
        }
    }

    private fun initView() {
        viewBinding.root.setOnClickListener {
            CRouter.with(context).url(RoutePath.PLAYING).start()
        }

        // 为收藏按钮添加按钮动画
        viewBinding.ivLike.addButtonAnimation(
            scaleDown = 0.9f,
            duration = 200L,
            rippleColor = 0x40FFFFFF
        )
        viewBinding.ivLike.setOnClickListener {
            val activity = context.findActivity()
            if (activity is FragmentActivity) {
                activity.lifecycleScope.launch {
                    val song = playerController.currentSong.value ?: return@launch
                    val res = likeSongProcessor.like(activity, song.getSongId())
                    if (res.isSuccess()) {
                        updateLikeState(song.getSongId())
                    } else {
                        Toast.makeText(context, res.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // 为播放按钮添加播放控制动画
        viewBinding.flPlay.addPlayControlAnimation()
        viewBinding.flPlay.setOnClickListener {
            playerController.playPause()
        }

        // 为下一首按钮添加播放控制动画
        viewBinding.ivNext.addPlayControlAnimation()
        viewBinding.ivNext.setOnClickListener {
            playerController.next()
        }

        // 为播放列表按钮添加播放控制动画
        viewBinding.ivPlaylist.addPlayControlAnimation()
        viewBinding.ivPlaylist.setOnClickListener {
            val activity = context.findActivity()
            if (activity is FragmentActivity) {
                CurrentPlaylistFragment.newInstance()
                    .show(activity.supportFragmentManager, CurrentPlaylistFragment.TAG)
            }
        }
    }

    private fun initData(lifecycleOwner: LifecycleOwner) {
        playerController.currentSong.observe(lifecycleOwner) { currentSong ->
            if (currentSong != null) {
                isVisible = true
                viewBinding.ivCover.loadAvatar(currentSong.getSmallCover())

                // 分离显示歌曲标题和歌手名
                viewBinding.tvTitle.text = currentSong.mediaMetadata.title
                val artist = currentSong.mediaMetadata.artist
                if (artist.isNullOrBlank().not()) {
                    viewBinding.tvArtist.text = artist
                    viewBinding.tvArtist.isVisible = true
                } else {
                    viewBinding.tvArtist.isVisible = false
                }

                // 显示VIP标签
                updateVipLabel(currentSong)

                // 更新收藏状态和显示
                updateLikeState(currentSong.getSongId())
            } else {
                isVisible = false
            }
        }

        lifecycleOwner.lifecycleScope.launch {
            playerController.playState.collectLatest { playState ->
                when (playState) {
                    PlayState.Preparing -> {
                        viewBinding.flPlay.isEnabled = false
                        viewBinding.ivPlay.isSelected = false
                        viewBinding.loadingProgress.isVisible = true
                    }

                    PlayState.Playing -> {
                        viewBinding.flPlay.isEnabled = true
                        viewBinding.ivPlay.isSelected = true
                        viewBinding.loadingProgress.isVisible = false
                    }

                    else -> {
                        viewBinding.flPlay.isEnabled = true
                        viewBinding.ivPlay.isSelected = false
                        viewBinding.loadingProgress.isVisible = false
                    }
                }

                if (playState.isPlaying) {
                    if (rotateAnimator.isPaused) {
                        rotateAnimator.resume()
                    } else if (rotateAnimator.isStarted.not()) {
                        rotateAnimator.start()
                    }
                } else {
                    if (rotateAnimator.isRunning) {
                        rotateAnimator.pause()
                    }
                }
            }
        }

        // 播放进度更新
        lifecycleOwner.lifecycleScope.launch {
            playerController.playProgress.collectLatest { progress ->
                updatePlayProgress()
            }
        }
    }

    /**
     * 更新VIP标签显示，使用优化的渲染方法
     */
    private fun updateVipLabel(song: androidx.media3.common.MediaItem) {
        val fee = VipUtils.getSongFee(song)
        VipUtils.updateVipLabelOptimized(viewBinding.tvVipLabel, fee)
    }

    /**
     * 更新播放进度
     */
    private fun updatePlayProgress() {
        val currentSong = playerController.currentSong.value
        val progressWidth = if (currentSong != null) {
            val duration = currentSong.mediaMetadata.getDuration()
            val position = playerController.playProgress.value

            if (duration > 0 && viewBinding.root.width > 0) {
                val progress = (position.toFloat() / duration.toFloat()).coerceIn(0f, 1f)
                (viewBinding.root.width * progress).toInt()
            } else 0
        } else 0

        // 更新进度背景宽度
        viewBinding.progressBackground.layoutParams =
            viewBinding.progressBackground.layoutParams.apply { width = progressWidth }
    }

    /**
     * 更新收藏状态
     */
    private fun updateLikeState(songId: Long) {
        val currentSong = playerController.currentSong.value
        if (currentSong != null && !currentSong.isLocal()) {
            viewBinding.ivLike.isVisible = true
            viewBinding.ivLike.isSelected = likeSongProcessor.isLiked(songId)
        } else {
            viewBinding.ivLike.isVisible = false
        }
    }
}