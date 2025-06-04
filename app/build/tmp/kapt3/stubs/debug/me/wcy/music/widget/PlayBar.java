package me.wcy.music.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import me.wcy.music.R;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.databinding.LayoutPlayBarBinding;
import me.wcy.music.main.playlist.CurrentPlaylistFragment;
import me.wcy.music.service.PlayState;
import me.wcy.router.CRouter;
import top.wangchenyan.common.CommonApp;

/**
 * Created by wangchenyan.top on 2023/9/4.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0017H\u0002J\u0010\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u0017H\u0002R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\f\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lme/wcy/music/widget/PlayBar;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "likeSongProcessor", "Lme/wcy/music/service/likesong/LikeSongProcessor;", "getLikeSongProcessor", "()Lme/wcy/music/service/likesong/LikeSongProcessor;", "likeSongProcessor$delegate", "Lkotlin/Lazy;", "playerController", "Lme/wcy/music/service/PlayerController;", "getPlayerController", "()Lme/wcy/music/service/PlayerController;", "playerController$delegate", "rotateAnimator", "Landroid/animation/ObjectAnimator;", "viewBinding", "Lme/wcy/music/databinding/LayoutPlayBarBinding;", "initData", "", "lifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "initView", "updateLikeState", "songId", "", "updatePlayProgress", "app_debug"})
public final class PlayBar extends android.widget.FrameLayout {
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.databinding.LayoutPlayBarBinding viewBinding = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy playerController$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy likeSongProcessor$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final android.animation.ObjectAnimator rotateAnimator = null;
    
    @kotlin.jvm.JvmOverloads()
    public PlayBar(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    private final me.wcy.music.service.PlayerController getPlayerController() {
        return null;
    }
    
    private final me.wcy.music.service.likesong.LikeSongProcessor getLikeSongProcessor() {
        return null;
    }
    
    private final void initView() {
    }
    
    private final void initData(androidx.lifecycle.LifecycleOwner lifecycleOwner) {
    }
    
    /**
     * 更新播放进度
     */
    private final void updatePlayProgress() {
    }
    
    /**
     * 更新收藏状态
     */
    private final void updateLikeState(long songId) {
    }
    
    @kotlin.jvm.JvmOverloads()
    public PlayBar(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
}