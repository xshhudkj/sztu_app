package me.wcy.music.discover.home.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.Deferred;
import me.wcy.music.account.service.UserService;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.discover.DiscoverApi;
import me.wcy.music.discover.banner.BannerData;
import me.wcy.music.net.NetCache;
import me.wcy.music.storage.preference.ConfigPreferences;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/9/25.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0018H\u0002J\b\u0010\u001a\u001a\u00020\u0018H\u0002J\b\u0010\u001b\u001a\u00020\u0018H\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00070\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00070\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001d\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lme/wcy/music/discover/home/viewmodel/DiscoverViewModel;", "Landroidx/lifecycle/ViewModel;", "userService", "Lme/wcy/music/account/service/UserService;", "(Lme/wcy/music/account/service/UserService;)V", "_bannerList", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lme/wcy/music/discover/banner/BannerData;", "_rankingList", "Landroidx/lifecycle/MutableLiveData;", "Lme/wcy/music/common/bean/PlaylistData;", "_recommendPlaylist", "bannerList", "Lkotlinx/coroutines/flow/StateFlow;", "getBannerList", "()Lkotlinx/coroutines/flow/StateFlow;", "rankingList", "Landroidx/lifecycle/LiveData;", "getRankingList", "()Landroidx/lifecycle/LiveData;", "recommendPlaylist", "getRecommendPlaylist", "loadBanner", "", "loadCache", "loadRankingList", "loadRecommendPlaylist", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DiscoverViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.account.service.UserService userService = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<me.wcy.music.discover.banner.BannerData>> _bannerList = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<me.wcy.music.discover.banner.BannerData>> bannerList = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<me.wcy.music.common.bean.PlaylistData>> _recommendPlaylist = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<me.wcy.music.common.bean.PlaylistData>> recommendPlaylist = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<me.wcy.music.common.bean.PlaylistData>> _rankingList = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<me.wcy.music.common.bean.PlaylistData>> rankingList = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CACHE_KEY_BANNER = "discover_banner";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CACHE_KEY_REC_PLAYLIST = "discover_recommend_playlist";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CACHE_KEY_RANKING_LIST = "discover_ranking_list";
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.discover.home.viewmodel.DiscoverViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public DiscoverViewModel(@org.jetbrains.annotations.NotNull()
    me.wcy.music.account.service.UserService userService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<me.wcy.music.discover.banner.BannerData>> getBannerList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<me.wcy.music.common.bean.PlaylistData>> getRecommendPlaylist() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<me.wcy.music.common.bean.PlaylistData>> getRankingList() {
        return null;
    }
    
    private final void loadCache() {
    }
    
    private final void loadBanner() {
    }
    
    private final void loadRecommendPlaylist() {
    }
    
    private final void loadRankingList() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lme/wcy/music/discover/home/viewmodel/DiscoverViewModel$Companion;", "", "()V", "CACHE_KEY_BANNER", "", "CACHE_KEY_RANKING_LIST", "CACHE_KEY_REC_PLAYLIST", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}