package me.wcy.music.search;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.google.android.material.tabs.TabLayout;
import com.blankj.utilcode.util.SizeUtils;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import dagger.hilt.android.AndroidEntryPoint;
import me.wcy.music.R;
import me.wcy.music.common.BaseMusicFragment;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.databinding.FragmentSearchBinding;
import me.wcy.music.databinding.ItemSearchHistoryBinding;
import me.wcy.music.databinding.TitleSearchBinding;
import me.wcy.music.discover.banner.BannerData;
import me.wcy.music.discover.DiscoverApi;
import me.wcy.music.search.album.SearchAlbumFragment;
import me.wcy.music.search.artist.SearchArtistFragment;
import me.wcy.music.search.bean.HotSearchDetailData;
import me.wcy.music.search.bean.SearchSuggestItem;
import me.wcy.music.search.playlist.SearchPlaylistFragment;
import me.wcy.music.search.song.SearchSongFragment;
import me.wcy.music.search.user.SearchUserFragment;
import me.wcy.music.service.PlayerController;
import me.wcy.router.CRouter;
import me.wcy.router.annotation.Route;
import top.wangchenyan.common.utils.LaunchUtils;
import top.wangchenyan.common.widget.pager.TabLayoutPager;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/9/20.
 */
@me.wcy.router.annotation.Route(value = "/search")
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010 \u001a\u00020!H\u0014J\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\b\u0010&\u001a\u00020#H\u0002J\b\u0010\'\u001a\u00020#H\u0002J\b\u0010(\u001a\u00020#H\u0002J\b\u0010)\u001a\u00020#H\u0002J\b\u0010*\u001a\u00020#H\u0002J\b\u0010+\u001a\u00020#H\u0002J\u0010\u0010,\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\b\u0010-\u001a\u00020.H\u0016J\b\u0010/\u001a\u00020#H\u0014J\u0016\u00100\u001a\u00020#2\f\u00101\u001a\b\u0012\u0004\u0012\u00020302H\u0002J\u0016\u00104\u001a\u00020#2\f\u00105\u001a\b\u0012\u0004\u0012\u00020602H\u0002J\b\u00107\u001a\u00020#H\u0002J\b\u00108\u001a\u00020#H\u0002J\u0010\u00109\u001a\u00020#2\u0006\u0010:\u001a\u00020.H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0015\u0010\b\u001a\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0016\u001a\u00020\u00178BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001a\u0010\b\u001a\u0004\b\u0018\u0010\u0019R\u001b\u0010\u001b\u001a\u00020\u001c8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001f\u0010\b\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006;"}, d2 = {"Lme/wcy/music/search/SearchFragment;", "Lme/wcy/music/common/BaseMusicFragment;", "()V", "menuSearch", "Landroid/widget/TextView;", "getMenuSearch", "()Landroid/widget/TextView;", "menuSearch$delegate", "Lkotlin/Lazy;", "playerController", "Lme/wcy/music/service/PlayerController;", "getPlayerController", "()Lme/wcy/music/service/PlayerController;", "setPlayerController", "(Lme/wcy/music/service/PlayerController;)V", "searchSuggestJob", "Lkotlinx/coroutines/Job;", "titleBinding", "Lme/wcy/music/databinding/TitleSearchBinding;", "getTitleBinding", "()Lme/wcy/music/databinding/TitleSearchBinding;", "titleBinding$delegate", "viewBinding", "Lme/wcy/music/databinding/FragmentSearchBinding;", "getViewBinding", "()Lme/wcy/music/databinding/FragmentSearchBinding;", "viewBinding$delegate", "viewModel", "Lme/wcy/music/search/SearchViewModel;", "getViewModel", "()Lme/wcy/music/search/SearchViewModel;", "viewModel$delegate", "getRootView", "Landroid/view/View;", "handleSearchInput", "", "keywords", "", "initBackPressedHandler", "initHistory", "initHotSearch", "initSearchBanner", "initTab", "initTitle", "loadSearchSuggest", "onInterceptBackEvent", "", "onLazyCreate", "setupHotSearchList", "hotSearchList", "", "Lme/wcy/music/search/bean/HotSearchDetailData;", "setupSearchSuggest", "suggestList", "Lme/wcy/music/search/bean/SearchSuggestItem;", "showHistoryAndHideSuggest", "showSuggestAndHideHistory", "updateViewVisibility", "showResult", "app_debug"})
public final class SearchFragment extends me.wcy.music.common.BaseMusicFragment {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewBinding$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy titleBinding$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy menuSearch$delegate = null;
    @javax.inject.Inject()
    public me.wcy.music.service.PlayerController playerController;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job searchSuggestJob;
    
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
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.service.PlayerController getPlayerController() {
        return null;
    }
    
    public final void setPlayerController(@org.jetbrains.annotations.NotNull()
    me.wcy.music.service.PlayerController p0) {
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
    
    private final void handleSearchInput(java.lang.String keywords) {
    }
    
    private final void updateViewVisibility(boolean showResult) {
    }
    
    private final void showHistoryAndHideSuggest() {
    }
    
    private final void showSuggestAndHideHistory() {
    }
    
    private final void initTab() {
    }
    
    private final void initHistory() {
    }
    
    private final void initHotSearch() {
    }
    
    private final void setupHotSearchList(java.util.List<me.wcy.music.search.bean.HotSearchDetailData> hotSearchList) {
    }
    
    private final void loadSearchSuggest(java.lang.String keywords) {
    }
    
    private final void setupSearchSuggest(java.util.List<me.wcy.music.search.bean.SearchSuggestItem> suggestList) {
    }
    
    private final void initSearchBanner() {
    }
    
    @java.lang.Override()
    public boolean onInterceptBackEvent() {
        return false;
    }
    
    /**
     * 初始化返回键处理逻辑
     * 在显示搜索建议时，点击返回键直接退出，不返回到搜索历史
     */
    private final void initBackPressedHandler() {
    }
}