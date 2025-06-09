package me.wcy.music.discover.playlist.square;

import android.os.Bundle;
import android.view.View;
import dagger.hilt.android.AndroidEntryPoint;
import me.wcy.music.R;
import me.wcy.music.common.BaseMusicFragment;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.databinding.FragmentPlaylistSquareBinding;
import me.wcy.music.discover.playlist.square.viewmodel.PlaylistSquareViewModel;
import me.wcy.router.annotation.Route;
import top.wangchenyan.common.widget.pager.TabLayoutPager;

/**
 * Created by wangchenyan.top on 2023/9/26.
 */
@me.wcy.router.annotation.Route(value = "/playlist/square")
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0014J\b\u0010\u0012\u001a\u00020\u0011H\u0014J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0014J\b\u0010\u0017\u001a\u00020\u0014H\u0002J\b\u0010\u0018\u001a\u00020\u0014H\u0014J\b\u0010\u0019\u001a\u00020\u0014H\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001a"}, d2 = {"Lme/wcy/music/discover/playlist/square/PlaylistSquareFragment;", "Lme/wcy/music/common/BaseMusicFragment;", "()V", "pager", "Ltop/wangchenyan/common/widget/pager/TabLayoutPager;", "viewBinding", "Lme/wcy/music/databinding/FragmentPlaylistSquareBinding;", "getViewBinding", "()Lme/wcy/music/databinding/FragmentPlaylistSquareBinding;", "viewBinding$delegate", "Lkotlin/Lazy;", "viewModel", "Lme/wcy/music/discover/playlist/square/viewmodel/PlaylistSquareViewModel;", "getViewModel", "()Lme/wcy/music/discover/playlist/square/viewmodel/PlaylistSquareViewModel;", "viewModel$delegate", "getLoadSirTarget", "Landroid/view/View;", "getRootView", "initTab", "", "isUseLoadSir", "", "loadTagList", "onLazyCreate", "onReload", "app_debug"})
public final class PlaylistSquareFragment extends me.wcy.music.common.BaseMusicFragment {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewBinding$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.Nullable()
    private top.wangchenyan.common.widget.pager.TabLayoutPager pager;
    
    public PlaylistSquareFragment() {
        super();
    }
    
    private final me.wcy.music.databinding.FragmentPlaylistSquareBinding getViewBinding() {
        return null;
    }
    
    private final me.wcy.music.discover.playlist.square.viewmodel.PlaylistSquareViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected android.view.View getRootView() {
        return null;
    }
    
    @java.lang.Override()
    protected boolean isUseLoadSir() {
        return false;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected android.view.View getLoadSirTarget() {
        return null;
    }
    
    @java.lang.Override()
    protected void onReload() {
    }
    
    @java.lang.Override()
    protected void onLazyCreate() {
    }
    
    private final void initTab() {
    }
    
    private final void loadTagList() {
    }
}