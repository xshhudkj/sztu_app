package me.wcy.music.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import com.blankj.utilcode.util.KeyboardUtils;
import dagger.hilt.android.AndroidEntryPoint;
import me.wcy.music.R;
import me.wcy.music.common.BaseMusicFragment;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.databinding.FragmentSearchBinding;
import me.wcy.music.databinding.ItemSearchHistoryBinding;
import me.wcy.music.databinding.TitleSearchBinding;
import me.wcy.music.search.playlist.SearchPlaylistFragment;
import me.wcy.music.search.song.SearchSongFragment;
import me.wcy.router.annotation.Route;
import top.wangchenyan.common.widget.pager.TabLayoutPager;

/**
 * Created by wangchenyan.top on 2023/9/20.
 */
@me.wcy.router.annotation.Route(value = "/search")
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0014J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u001bH\u0002J\b\u0010\u001d\u001a\u00020\u001bH\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u001bH\u0014R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\b\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006!"}, d2 = {"Lme/wcy/music/search/SearchFragment;", "Lme/wcy/music/common/BaseMusicFragment;", "()V", "menuSearch", "Landroid/widget/TextView;", "getMenuSearch", "()Landroid/widget/TextView;", "menuSearch$delegate", "Lkotlin/Lazy;", "titleBinding", "Lme/wcy/music/databinding/TitleSearchBinding;", "getTitleBinding", "()Lme/wcy/music/databinding/TitleSearchBinding;", "titleBinding$delegate", "viewBinding", "Lme/wcy/music/databinding/FragmentSearchBinding;", "getViewBinding", "()Lme/wcy/music/databinding/FragmentSearchBinding;", "viewBinding$delegate", "viewModel", "Lme/wcy/music/search/SearchViewModel;", "getViewModel", "()Lme/wcy/music/search/SearchViewModel;", "viewModel$delegate", "getRootView", "Landroid/view/View;", "initHistory", "", "initTab", "initTitle", "onInterceptBackEvent", "", "onLazyCreate", "app_debug"})
public final class SearchFragment extends me.wcy.music.common.BaseMusicFragment {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewBinding$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy titleBinding$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy menuSearch$delegate = null;
    
    public SearchFragment() {
        super();
    }
    
    private final me.wcy.music.databinding.FragmentSearchBinding getViewBinding() {
        return null;
    }
    
    private final me.wcy.music.databinding.TitleSearchBinding getTitleBinding() {
        return null;
    }
    
    private final me.wcy.music.search.SearchViewModel getViewModel() {
        return null;
    }
    
    private final android.widget.TextView getMenuSearch() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected android.view.View getRootView() {
        return null;
    }
    
    @java.lang.Override()
    protected void onLazyCreate() {
    }
    
    private final void initTitle() {
    }
    
    private final void initTab() {
    }
    
    private final void initHistory() {
    }
    
    @java.lang.Override()
    public boolean onInterceptBackEvent() {
        return false;
    }
}