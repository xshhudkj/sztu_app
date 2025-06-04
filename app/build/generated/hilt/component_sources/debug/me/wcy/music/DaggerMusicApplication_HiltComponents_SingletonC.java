package me.wcy.music;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.account.login.phone.PhoneLoginFragment;
import me.wcy.music.account.login.phone.PhoneLoginFragment_MembersInjector;
import me.wcy.music.account.login.phone.PhoneLoginViewModel;
import me.wcy.music.account.login.phone.PhoneLoginViewModel_HiltModules_KeyModule_ProvideFactory;
import me.wcy.music.account.login.qrcode.QrcodeLoginFragment;
import me.wcy.music.account.login.qrcode.QrcodeLoginFragment_MembersInjector;
import me.wcy.music.account.login.qrcode.QrcodeLoginViewModel;
import me.wcy.music.account.login.qrcode.QrcodeLoginViewModel_HiltModules_KeyModule_ProvideFactory;
import me.wcy.music.account.service.UserService;
import me.wcy.music.account.service.UserServiceImpl;
import me.wcy.music.album.detail.AlbumDetailFragment;
import me.wcy.music.album.detail.AlbumDetailFragment_MembersInjector;
import me.wcy.music.album.detail.AlbumDetailViewModel;
import me.wcy.music.album.detail.AlbumDetailViewModel_HiltModules_KeyModule_ProvideFactory;
import me.wcy.music.artist.detail.ArtistDetailFragment;
import me.wcy.music.artist.detail.ArtistDetailFragment_MembersInjector;
import me.wcy.music.artist.detail.ArtistDetailViewModel;
import me.wcy.music.artist.detail.ArtistDetailViewModel_HiltModules_KeyModule_ProvideFactory;
import me.wcy.music.common.DarkModeService;
import me.wcy.music.common.MusicFragmentContainerActivity;
import me.wcy.music.discover.home.DiscoverFragment;
import me.wcy.music.discover.home.DiscoverFragment_MembersInjector;
import me.wcy.music.discover.home.viewmodel.DiscoverViewModel;
import me.wcy.music.discover.home.viewmodel.DiscoverViewModel_HiltModules_KeyModule_ProvideFactory;
import me.wcy.music.discover.playlist.detail.PlaylistDetailFragment;
import me.wcy.music.discover.playlist.detail.PlaylistDetailFragment_MembersInjector;
import me.wcy.music.discover.playlist.detail.viewmodel.PlaylistViewModel;
import me.wcy.music.discover.playlist.detail.viewmodel.PlaylistViewModel_Factory;
import me.wcy.music.discover.playlist.detail.viewmodel.PlaylistViewModel_HiltModules_KeyModule_ProvideFactory;
import me.wcy.music.discover.playlist.detail.viewmodel.PlaylistViewModel_MembersInjector;
import me.wcy.music.discover.playlist.square.PlaylistSquareFragment;
import me.wcy.music.discover.playlist.square.PlaylistTabFragment;
import me.wcy.music.discover.ranking.RankingFragment;
import me.wcy.music.discover.ranking.RankingFragment_MembersInjector;
import me.wcy.music.discover.recommend.song.RecommendSongFragment;
import me.wcy.music.discover.recommend.song.RecommendSongFragment_MembersInjector;
import me.wcy.music.main.MainActivity;
import me.wcy.music.main.MainActivity_MembersInjector;
import me.wcy.music.main.SettingsActivity;
import me.wcy.music.main.SettingsActivity_SettingsFragment_MembersInjector;
import me.wcy.music.main.playing.PlayingActivity;
import me.wcy.music.main.playing.PlayingActivity_MembersInjector;
import me.wcy.music.main.playlist.CurrentPlaylistFragment;
import me.wcy.music.main.playlist.CurrentPlaylistFragment_MembersInjector;
import me.wcy.music.mine.collect.song.CollectSongFragment;
import me.wcy.music.mine.collect.song.CollectSongViewModel;
import me.wcy.music.mine.collect.song.CollectSongViewModel_Factory;
import me.wcy.music.mine.collect.song.CollectSongViewModel_HiltModules_KeyModule_ProvideFactory;
import me.wcy.music.mine.collect.song.CollectSongViewModel_MembersInjector;
import me.wcy.music.mine.home.MineFragment;
import me.wcy.music.mine.home.MineFragment_MembersInjector;
import me.wcy.music.mine.home.viewmodel.MineViewModel;
import me.wcy.music.mine.home.viewmodel.MineViewModel_Factory;
import me.wcy.music.mine.home.viewmodel.MineViewModel_HiltModules_KeyModule_ProvideFactory;
import me.wcy.music.mine.home.viewmodel.MineViewModel_MembersInjector;
import me.wcy.music.mine.local.LocalMusicFragment;
import me.wcy.music.mine.local.LocalMusicFragment_MembersInjector;
import me.wcy.music.search.SearchFragment;
import me.wcy.music.search.SearchFragment_MembersInjector;
import me.wcy.music.search.album.SearchAlbumFragment;
import me.wcy.music.search.artist.SearchArtistFragment;
import me.wcy.music.search.playlist.SearchPlaylistFragment;
import me.wcy.music.search.song.SearchSongFragment;
import me.wcy.music.search.song.SearchSongFragment_MembersInjector;
import me.wcy.music.search.user.SearchUserFragment;
import me.wcy.music.service.PlayServiceModule;
import me.wcy.music.service.PlayServiceModule_ProviderPlayerControllerFactory;
import me.wcy.music.service.PlayerController;
import me.wcy.music.service.likesong.LikeSongProcessor;
import me.wcy.music.service.likesong.LikeSongProcessorImpl;
import me.wcy.music.storage.db.DatabaseModule;
import me.wcy.music.storage.db.DatabaseModule_ProvideAppDatabaseFactory;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DaggerMusicApplication_HiltComponents_SingletonC {
  private DaggerMusicApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static MusicApplication_HiltComponents.SingletonC create() {
    return new Builder().build();
  }

  public static final class Builder {
    private Builder() {
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder databaseModule(DatabaseModule databaseModule) {
      Preconditions.checkNotNull(databaseModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule(
        HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule) {
      Preconditions.checkNotNull(hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder playServiceModule(PlayServiceModule playServiceModule) {
      Preconditions.checkNotNull(playServiceModule);
      return this;
    }

    public MusicApplication_HiltComponents.SingletonC build() {
      return new SingletonCImpl();
    }
  }

  private static final class ActivityRetainedCBuilder implements MusicApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public MusicApplication_HiltComponents.ActivityRetainedC build() {
      return new ActivityRetainedCImpl(singletonCImpl);
    }
  }

  private static final class ActivityCBuilder implements MusicApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public MusicApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements MusicApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public MusicApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements MusicApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public MusicApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements MusicApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public MusicApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements MusicApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public MusicApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements MusicApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public MusicApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends MusicApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends MusicApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }

    @Override
    public void injectPhoneLoginFragment(PhoneLoginFragment phoneLoginFragment) {
      injectPhoneLoginFragment2(phoneLoginFragment);
    }

    @Override
    public void injectQrcodeLoginFragment(QrcodeLoginFragment qrcodeLoginFragment) {
      injectQrcodeLoginFragment2(qrcodeLoginFragment);
    }

    @Override
    public void injectAlbumDetailFragment(AlbumDetailFragment albumDetailFragment) {
      injectAlbumDetailFragment2(albumDetailFragment);
    }

    @Override
    public void injectArtistDetailFragment(ArtistDetailFragment artistDetailFragment) {
      injectArtistDetailFragment2(artistDetailFragment);
    }

    @Override
    public void injectDiscoverFragment(DiscoverFragment discoverFragment) {
      injectDiscoverFragment2(discoverFragment);
    }

    @Override
    public void injectPlaylistDetailFragment(PlaylistDetailFragment playlistDetailFragment) {
      injectPlaylistDetailFragment2(playlistDetailFragment);
    }

    @Override
    public void injectPlaylistSquareFragment(PlaylistSquareFragment playlistSquareFragment) {
    }

    @Override
    public void injectPlaylistTabFragment(PlaylistTabFragment playlistTabFragment) {
    }

    @Override
    public void injectRankingFragment(RankingFragment rankingFragment) {
      injectRankingFragment2(rankingFragment);
    }

    @Override
    public void injectRecommendSongFragment(RecommendSongFragment recommendSongFragment) {
      injectRecommendSongFragment2(recommendSongFragment);
    }

    @Override
    public void injectSettingsActivity_SettingsFragment(
        SettingsActivity.SettingsFragment settingsFragment) {
      injectSettingsFragment(settingsFragment);
    }

    @Override
    public void injectCurrentPlaylistFragment(CurrentPlaylistFragment currentPlaylistFragment) {
      injectCurrentPlaylistFragment2(currentPlaylistFragment);
    }

    @Override
    public void injectCollectSongFragment(CollectSongFragment collectSongFragment) {
    }

    @Override
    public void injectMineFragment(MineFragment mineFragment) {
      injectMineFragment2(mineFragment);
    }

    @Override
    public void injectLocalMusicFragment(LocalMusicFragment localMusicFragment) {
      injectLocalMusicFragment2(localMusicFragment);
    }

    @Override
    public void injectSearchFragment(SearchFragment searchFragment) {
      injectSearchFragment2(searchFragment);
    }

    @Override
    public void injectSearchAlbumFragment(SearchAlbumFragment searchAlbumFragment) {
    }

    @Override
    public void injectSearchArtistFragment(SearchArtistFragment searchArtistFragment) {
    }

    @Override
    public void injectSearchPlaylistFragment(SearchPlaylistFragment searchPlaylistFragment) {
    }

    @Override
    public void injectSearchSongFragment(SearchSongFragment searchSongFragment) {
      injectSearchSongFragment2(searchSongFragment);
    }

    @Override
    public void injectSearchUserFragment(SearchUserFragment searchUserFragment) {
    }

    @CanIgnoreReturnValue
    private PhoneLoginFragment injectPhoneLoginFragment2(PhoneLoginFragment instance) {
      PhoneLoginFragment_MembersInjector.injectUserService(instance, singletonCImpl.userServiceImplProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private QrcodeLoginFragment injectQrcodeLoginFragment2(QrcodeLoginFragment instance) {
      QrcodeLoginFragment_MembersInjector.injectUserService(instance, singletonCImpl.userServiceImplProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private AlbumDetailFragment injectAlbumDetailFragment2(AlbumDetailFragment instance) {
      AlbumDetailFragment_MembersInjector.injectPlayerController(instance, singletonCImpl.playerController());
      return instance;
    }

    @CanIgnoreReturnValue
    private ArtistDetailFragment injectArtistDetailFragment2(ArtistDetailFragment instance) {
      ArtistDetailFragment_MembersInjector.injectUserService(instance, singletonCImpl.userServiceImplProvider.get());
      ArtistDetailFragment_MembersInjector.injectPlayerController(instance, singletonCImpl.playerController());
      return instance;
    }

    @CanIgnoreReturnValue
    private DiscoverFragment injectDiscoverFragment2(DiscoverFragment instance) {
      DiscoverFragment_MembersInjector.injectUserService(instance, singletonCImpl.userServiceImplProvider.get());
      DiscoverFragment_MembersInjector.injectPlayerController(instance, singletonCImpl.playerController());
      return instance;
    }

    @CanIgnoreReturnValue
    private PlaylistDetailFragment injectPlaylistDetailFragment2(PlaylistDetailFragment instance) {
      PlaylistDetailFragment_MembersInjector.injectUserService(instance, singletonCImpl.userServiceImplProvider.get());
      PlaylistDetailFragment_MembersInjector.injectPlayerController(instance, singletonCImpl.playerController());
      return instance;
    }

    @CanIgnoreReturnValue
    private RankingFragment injectRankingFragment2(RankingFragment instance) {
      RankingFragment_MembersInjector.injectPlayerController(instance, singletonCImpl.playerController());
      return instance;
    }

    @CanIgnoreReturnValue
    private RecommendSongFragment injectRecommendSongFragment2(RecommendSongFragment instance) {
      RecommendSongFragment_MembersInjector.injectPlayerController(instance, singletonCImpl.playerController());
      return instance;
    }

    @CanIgnoreReturnValue
    private SettingsActivity.SettingsFragment injectSettingsFragment(
        SettingsActivity.SettingsFragment instance) {
      SettingsActivity_SettingsFragment_MembersInjector.injectPlayerController(instance, singletonCImpl.playerController());
      SettingsActivity_SettingsFragment_MembersInjector.injectDarkModeService(instance, singletonCImpl.darkModeServiceProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private CurrentPlaylistFragment injectCurrentPlaylistFragment2(
        CurrentPlaylistFragment instance) {
      CurrentPlaylistFragment_MembersInjector.injectPlayerController(instance, singletonCImpl.playerController());
      return instance;
    }

    @CanIgnoreReturnValue
    private MineFragment injectMineFragment2(MineFragment instance) {
      MineFragment_MembersInjector.injectUserService(instance, singletonCImpl.userServiceImplProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private LocalMusicFragment injectLocalMusicFragment2(LocalMusicFragment instance) {
      LocalMusicFragment_MembersInjector.injectPlayerController(instance, singletonCImpl.playerController());
      return instance;
    }

    @CanIgnoreReturnValue
    private SearchFragment injectSearchFragment2(SearchFragment instance) {
      SearchFragment_MembersInjector.injectPlayerController(instance, singletonCImpl.playerController());
      return instance;
    }

    @CanIgnoreReturnValue
    private SearchSongFragment injectSearchSongFragment2(SearchSongFragment instance) {
      SearchSongFragment_MembersInjector.injectPlayerController(instance, singletonCImpl.playerController());
      return instance;
    }
  }

  private static final class ViewCImpl extends MusicApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends MusicApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return ImmutableSet.<String>of(AlbumDetailViewModel_HiltModules_KeyModule_ProvideFactory.provide(), ArtistDetailViewModel_HiltModules_KeyModule_ProvideFactory.provide(), CollectSongViewModel_HiltModules_KeyModule_ProvideFactory.provide(), DiscoverViewModel_HiltModules_KeyModule_ProvideFactory.provide(), MineViewModel_HiltModules_KeyModule_ProvideFactory.provide(), PhoneLoginViewModel_HiltModules_KeyModule_ProvideFactory.provide(), PlaylistViewModel_HiltModules_KeyModule_ProvideFactory.provide(), QrcodeLoginViewModel_HiltModules_KeyModule_ProvideFactory.provide());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public void injectMusicFragmentContainerActivity(
        MusicFragmentContainerActivity musicFragmentContainerActivity) {
    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public void injectSettingsActivity(SettingsActivity settingsActivity) {
    }

    @Override
    public void injectPlayingActivity(PlayingActivity playingActivity) {
      injectPlayingActivity2(playingActivity);
    }

    @CanIgnoreReturnValue
    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectUserService(instance, singletonCImpl.userServiceImplProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private PlayingActivity injectPlayingActivity2(PlayingActivity instance) {
      PlayingActivity_MembersInjector.injectPlayerController(instance, singletonCImpl.playerController());
      PlayingActivity_MembersInjector.injectLikeSongProcessor(instance, singletonCImpl.likeSongProcessorImplProvider.get());
      return instance;
    }
  }

  private static final class ViewModelCImpl extends MusicApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AlbumDetailViewModel> albumDetailViewModelProvider;

    private Provider<ArtistDetailViewModel> artistDetailViewModelProvider;

    private Provider<CollectSongViewModel> collectSongViewModelProvider;

    private Provider<DiscoverViewModel> discoverViewModelProvider;

    private Provider<MineViewModel> mineViewModelProvider;

    private Provider<PhoneLoginViewModel> phoneLoginViewModelProvider;

    private Provider<PlaylistViewModel> playlistViewModelProvider;

    private Provider<QrcodeLoginViewModel> qrcodeLoginViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.albumDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.artistDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.collectSongViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.discoverViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.mineViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.phoneLoginViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.playlistViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.qrcodeLoginViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
    }

    @Override
    public Map<String, Provider<ViewModel>> getHiltViewModelMap() {
      return ImmutableMap.<String, Provider<ViewModel>>builderWithExpectedSize(8).put("me.wcy.music.album.detail.AlbumDetailViewModel", ((Provider) albumDetailViewModelProvider)).put("me.wcy.music.artist.detail.ArtistDetailViewModel", ((Provider) artistDetailViewModelProvider)).put("me.wcy.music.mine.collect.song.CollectSongViewModel", ((Provider) collectSongViewModelProvider)).put("me.wcy.music.discover.home.viewmodel.DiscoverViewModel", ((Provider) discoverViewModelProvider)).put("me.wcy.music.mine.home.viewmodel.MineViewModel", ((Provider) mineViewModelProvider)).put("me.wcy.music.account.login.phone.PhoneLoginViewModel", ((Provider) phoneLoginViewModelProvider)).put("me.wcy.music.discover.playlist.detail.viewmodel.PlaylistViewModel", ((Provider) playlistViewModelProvider)).put("me.wcy.music.account.login.qrcode.QrcodeLoginViewModel", ((Provider) qrcodeLoginViewModelProvider)).build();
    }

    @CanIgnoreReturnValue
    private CollectSongViewModel injectCollectSongViewModel(CollectSongViewModel instance) {
      CollectSongViewModel_MembersInjector.injectUserService(instance, singletonCImpl.userServiceImplProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private MineViewModel injectMineViewModel(MineViewModel instance) {
      MineViewModel_MembersInjector.injectUserService(instance, singletonCImpl.userServiceImplProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private PlaylistViewModel injectPlaylistViewModel(PlaylistViewModel instance) {
      PlaylistViewModel_MembersInjector.injectLikeSongProcessor(instance, singletonCImpl.likeSongProcessorImplProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // me.wcy.music.album.detail.AlbumDetailViewModel 
          return (T) new AlbumDetailViewModel();

          case 1: // me.wcy.music.artist.detail.ArtistDetailViewModel 
          return (T) new ArtistDetailViewModel();

          case 2: // me.wcy.music.mine.collect.song.CollectSongViewModel 
          return (T) viewModelCImpl.injectCollectSongViewModel(CollectSongViewModel_Factory.newInstance());

          case 3: // me.wcy.music.discover.home.viewmodel.DiscoverViewModel 
          return (T) new DiscoverViewModel(singletonCImpl.userServiceImplProvider.get());

          case 4: // me.wcy.music.mine.home.viewmodel.MineViewModel 
          return (T) viewModelCImpl.injectMineViewModel(MineViewModel_Factory.newInstance());

          case 5: // me.wcy.music.account.login.phone.PhoneLoginViewModel 
          return (T) new PhoneLoginViewModel(singletonCImpl.userServiceImplProvider.get());

          case 6: // me.wcy.music.discover.playlist.detail.viewmodel.PlaylistViewModel 
          return (T) viewModelCImpl.injectPlaylistViewModel(PlaylistViewModel_Factory.newInstance());

          case 7: // me.wcy.music.account.login.qrcode.QrcodeLoginViewModel 
          return (T) new QrcodeLoginViewModel(singletonCImpl.userServiceImplProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends MusicApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;

      initialize();

    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends MusicApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends MusicApplication_HiltComponents.SingletonC {
    private final SingletonCImpl singletonCImpl = this;

    private Provider<UserServiceImpl> userServiceImplProvider;

    private Provider<DarkModeService> darkModeServiceProvider;

    private Provider<LikeSongProcessorImpl> likeSongProcessorImplProvider;

    private SingletonCImpl() {

      initialize();

    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.userServiceImplProvider = DoubleCheck.provider(new SwitchingProvider<UserServiceImpl>(singletonCImpl, 0));
      this.darkModeServiceProvider = DoubleCheck.provider(new SwitchingProvider<DarkModeService>(singletonCImpl, 1));
      this.likeSongProcessorImplProvider = DoubleCheck.provider(new SwitchingProvider<LikeSongProcessorImpl>(singletonCImpl, 2));
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    @Override
    public void injectMusicApplication(MusicApplication musicApplication) {
      injectMusicApplication2(musicApplication);
    }

    @Override
    public UserService userService() {
      return userServiceImplProvider.get();
    }

    @Override
    public LikeSongProcessor likeSongProcessor() {
      return likeSongProcessorImplProvider.get();
    }

    @Override
    public PlayerController playerController() {
      return PlayServiceModule_ProviderPlayerControllerFactory.providerPlayerController(DatabaseModule_ProvideAppDatabaseFactory.provideAppDatabase());
    }

    @CanIgnoreReturnValue
    private MusicApplication injectMusicApplication2(MusicApplication instance) {
      MusicApplication_MembersInjector.injectUserService(instance, userServiceImplProvider.get());
      MusicApplication_MembersInjector.injectDarkModeService(instance, darkModeServiceProvider.get());
      MusicApplication_MembersInjector.injectLikeSongProcessor(instance, likeSongProcessorImplProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // me.wcy.music.account.service.UserServiceImpl 
          return (T) new UserServiceImpl();

          case 1: // me.wcy.music.common.DarkModeService 
          return (T) new DarkModeService();

          case 2: // me.wcy.music.service.likesong.LikeSongProcessorImpl 
          return (T) new LikeSongProcessorImpl(singletonCImpl.userServiceImplProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
