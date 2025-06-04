package me.wcy.music.discover.home;

import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import dagger.hilt.android.AndroidEntryPoint;
import me.wcy.music.R;
import me.wcy.music.account.service.UserService;
import me.wcy.music.common.ApiDomainDialog;
import me.wcy.music.common.BaseMusicFragment;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.databinding.FragmentDiscoverBinding;
import me.wcy.music.discover.DiscoverApi;
import me.wcy.music.discover.banner.BannerData;
import me.wcy.music.discover.home.viewmodel.DiscoverViewModel;
import me.wcy.music.discover.playlist.square.item.PlaylistItemBinder;
import me.wcy.music.discover.ranking.discover.item.DiscoverRankingItemBinder;
import me.wcy.music.main.MainActivity;
import me.wcy.music.service.PlayerController;
import me.wcy.music.storage.preference.ConfigPreferences;
import me.wcy.music.utils.BatchPlaylistLoader;
import me.wcy.radapter3.RAdapter;
import me.wcy.router.CRouter;
import top.wangchenyan.common.utils.LaunchUtils;
import top.wangchenyan.common.widget.decoration.SpacingDecoration;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/8/21.
 */
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0002J\b\u0010,\u001a\u00020-H\u0014J\b\u0010.\u001a\u00020-H\u0014J\b\u0010/\u001a\u00020)H\u0002J\b\u00100\u001a\u00020)H\u0002J\b\u00101\u001a\u00020)H\u0002J\b\u00102\u001a\u00020)H\u0002J\b\u00103\u001a\u00020)H\u0002J\b\u00104\u001a\u00020+H\u0014J\b\u00105\u001a\u00020)H\u0014J\b\u00106\u001a\u00020)H\u0014J\u0018\u00107\u001a\u00020)2\u0006\u00108\u001a\u00020\u00112\u0006\u00109\u001a\u00020:H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR!\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0014\u0010\b\u001a\u0004\b\u0012\u0010\u0013R!\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\b\u001a\u0004\b\u0016\u0010\u0013R\u001e\u0010\u0018\u001a\u00020\u00198\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001b\u0010\u001e\u001a\u00020\u001f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\"\u0010\b\u001a\u0004\b \u0010!R\u001b\u0010#\u001a\u00020$8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\'\u0010\b\u001a\u0004\b%\u0010&\u00a8\u0006;"}, d2 = {"Lme/wcy/music/discover/home/DiscoverFragment;", "Lme/wcy/music/common/BaseMusicFragment;", "()V", "batchPlaylistLoader", "Lme/wcy/music/utils/BatchPlaylistLoader;", "getBatchPlaylistLoader", "()Lme/wcy/music/utils/BatchPlaylistLoader;", "batchPlaylistLoader$delegate", "Lkotlin/Lazy;", "playerController", "Lme/wcy/music/service/PlayerController;", "getPlayerController", "()Lme/wcy/music/service/PlayerController;", "setPlayerController", "(Lme/wcy/music/service/PlayerController;)V", "rankingListAdapter", "Lme/wcy/radapter3/RAdapter;", "Lme/wcy/music/common/bean/PlaylistData;", "getRankingListAdapter", "()Lme/wcy/radapter3/RAdapter;", "rankingListAdapter$delegate", "recommendPlaylistAdapter", "getRecommendPlaylistAdapter", "recommendPlaylistAdapter$delegate", "userService", "Lme/wcy/music/account/service/UserService;", "getUserService", "()Lme/wcy/music/account/service/UserService;", "setUserService", "(Lme/wcy/music/account/service/UserService;)V", "viewBinding", "Lme/wcy/music/databinding/FragmentDiscoverBinding;", "getViewBinding", "()Lme/wcy/music/databinding/FragmentDiscoverBinding;", "viewBinding$delegate", "viewModel", "Lme/wcy/music/discover/home/viewmodel/DiscoverViewModel;", "getViewModel", "()Lme/wcy/music/discover/home/viewmodel/DiscoverViewModel;", "viewModel$delegate", "checkApiDomain", "", "isReload", "", "getLoadSirTarget", "Landroid/view/View;", "getRootView", "initBanner", "initRankingList", "initRecommendPlaylist", "initTitle", "initTopButton", "isUseLoadSir", "onLazyCreate", "onReload", "playPlaylist", "playlistData", "songPosition", "", "app_debug"})
public final class DiscoverFragment extends me.wcy.music.common.BaseMusicFragment {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewBinding$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy recommendPlaylistAdapter$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy rankingListAdapter$delegate = null;
    @javax.inject.Inject()
    public me.wcy.music.account.service.UserService userService;
    @javax.inject.Inject()
    public me.wcy.music.service.PlayerController playerController;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy batchPlaylistLoader$delegate = null;
    
    public DiscoverFragment() {
        super();
    }
    
    private final me.wcy.music.databinding.FragmentDiscoverBinding getViewBinding() {
        return null;
    }
    
    private final me.wcy.music.discover.home.viewmodel.DiscoverViewModel getViewModel() {
        return null;
    }
    
    private final me.wcy.radapter3.RAdapter<me.wcy.music.common.bean.PlaylistData> getRecommendPlaylistAdapter() {
        return null;
    }
    
    private final me.wcy.radapter3.RAdapter<me.wcy.music.common.bean.PlaylistData> getRankingListAdapter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.account.service.UserService getUserService() {
        return null;
    }
    
    public final void setUserService(@org.jetbrains.annotations.NotNull()
    me.wcy.music.account.service.UserService p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.service.PlayerController getPlayerController() {
        return null;
    }
    
    public final void setPlayerController(@org.jetbrains.annotations.NotNull()
    me.wcy.music.service.PlayerController p0) {
    }
    
    private final me.wcy.music.utils.BatchPlaylistLoader getBatchPlaylistLoader() {
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
    
    private final void initTitle() {
    }
    
    private final void initBanner() {
    }
    
    private final void initTopButton() {
    }
    
    private final void initRecommendPlaylist() {
    }
    
    private final void initRankingList() {
    }
    
    private final void checkApiDomain(boolean isReload) {
    }
    
    private final void playPlaylist(me.wcy.music.common.bean.PlaylistData playlistData, int songPosition) {
    }
}