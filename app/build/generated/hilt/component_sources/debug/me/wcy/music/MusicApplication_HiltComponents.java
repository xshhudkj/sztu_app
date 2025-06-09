package me.wcy.music;

import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.Subcomponent;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.components.ServiceComponent;
import dagger.hilt.android.components.ViewComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.components.ViewWithFragmentComponent;
import dagger.hilt.android.flags.FragmentGetContextFix;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_DefaultViewModelFactories_ActivityModule;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_HiltViewModelFactory_ViewModelModule;
import dagger.hilt.android.internal.managers.ActivityComponentManager;
import dagger.hilt.android.internal.managers.FragmentComponentManager;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedLifecycleEntryPoint;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_LifecycleModule;
import dagger.hilt.android.internal.managers.ServiceComponentManager;
import dagger.hilt.android.internal.managers.ViewComponentManager;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.HiltWrapper_ActivityModule;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import dagger.hilt.android.scopes.ActivityScoped;
import dagger.hilt.android.scopes.FragmentScoped;
import dagger.hilt.android.scopes.ServiceScoped;
import dagger.hilt.android.scopes.ViewModelScoped;
import dagger.hilt.android.scopes.ViewScoped;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedComponent;
import dagger.hilt.migration.DisableInstallInCheck;
import javax.annotation.processing.Generated;
import javax.inject.Singleton;
import me.wcy.music.account.login.phone.PhoneLoginViewModel_HiltModules;
import me.wcy.music.account.login.qrcode.QrcodeLoginViewModel_HiltModules;
import me.wcy.music.account.service.UserServiceModule;
import me.wcy.music.album.detail.AlbumDetailFragment_GeneratedInjector;
import me.wcy.music.album.detail.AlbumDetailViewModel_HiltModules;
import me.wcy.music.artist.detail.ArtistDetailFragment_GeneratedInjector;
import me.wcy.music.artist.detail.ArtistDetailViewModel_HiltModules;
import me.wcy.music.common.MusicFragmentContainerActivity_GeneratedInjector;
import me.wcy.music.discover.home.DiscoverFragment_GeneratedInjector;
import me.wcy.music.discover.home.viewmodel.DiscoverViewModel_HiltModules;
import me.wcy.music.discover.playlist.detail.PlaylistDetailFragment_GeneratedInjector;
import me.wcy.music.discover.playlist.detail.viewmodel.PlaylistViewModel_HiltModules;
import me.wcy.music.discover.playlist.square.PlaylistSquareFragment_GeneratedInjector;
import me.wcy.music.discover.playlist.square.PlaylistTabFragment_GeneratedInjector;
import me.wcy.music.discover.ranking.RankingFragment_GeneratedInjector;
import me.wcy.music.discover.recommend.song.RecommendSongFragment_GeneratedInjector;
import me.wcy.music.login.LoginActivity_GeneratedInjector;
import me.wcy.music.login.LoginViewModel_HiltModules;
import me.wcy.music.main.MainActivity_GeneratedInjector;
import me.wcy.music.main.SettingsActivity_GeneratedInjector;
import me.wcy.music.main.SettingsActivity_SettingsFragment_GeneratedInjector;
import me.wcy.music.main.playing.PlayingActivity_GeneratedInjector;
import me.wcy.music.main.playlist.CurrentPlaylistFragment_GeneratedInjector;
import me.wcy.music.mine.collect.song.CollectSongFragment_GeneratedInjector;
import me.wcy.music.mine.collect.song.CollectSongViewModel_HiltModules;
import me.wcy.music.mine.home.MineFragment_GeneratedInjector;
import me.wcy.music.mine.home.viewmodel.MineViewModel_HiltModules;
import me.wcy.music.mine.local.LocalMusicFragment_GeneratedInjector;
import me.wcy.music.search.SearchFragment_GeneratedInjector;
import me.wcy.music.search.album.SearchAlbumFragment_GeneratedInjector;
import me.wcy.music.search.artist.SearchArtistFragment_GeneratedInjector;
import me.wcy.music.search.playlist.SearchPlaylistFragment_GeneratedInjector;
import me.wcy.music.search.song.SearchSongFragment_GeneratedInjector;
import me.wcy.music.search.user.SearchUserFragment_GeneratedInjector;
import me.wcy.music.service.PlayServiceModule;
import me.wcy.music.service.likesong.LikeSongProcessorModule;
import me.wcy.music.splash.SplashActivity_GeneratedInjector;
import me.wcy.music.splash.SplashViewModel_HiltModules;
import me.wcy.music.storage.db.DatabaseModule;

@Generated("dagger.hilt.processor.internal.root.RootProcessor")
public final class MusicApplication_HiltComponents {
  private MusicApplication_HiltComponents() {
  }

  @Module(
      subcomponents = ServiceC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ServiceCBuilderModule {
    @Binds
    ServiceComponentBuilder bind(ServiceC.Builder builder);
  }

  @Module(
      subcomponents = ActivityRetainedC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ActivityRetainedCBuilderModule {
    @Binds
    ActivityRetainedComponentBuilder bind(ActivityRetainedC.Builder builder);
  }

  @Module(
      subcomponents = ActivityC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ActivityCBuilderModule {
    @Binds
    ActivityComponentBuilder bind(ActivityC.Builder builder);
  }

  @Module(
      subcomponents = ViewModelC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewModelCBuilderModule {
    @Binds
    ViewModelComponentBuilder bind(ViewModelC.Builder builder);
  }

  @Module(
      subcomponents = ViewC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewCBuilderModule {
    @Binds
    ViewComponentBuilder bind(ViewC.Builder builder);
  }

  @Module(
      subcomponents = FragmentC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface FragmentCBuilderModule {
    @Binds
    FragmentComponentBuilder bind(FragmentC.Builder builder);
  }

  @Module(
      subcomponents = ViewWithFragmentC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewWithFragmentCBuilderModule {
    @Binds
    ViewWithFragmentComponentBuilder bind(ViewWithFragmentC.Builder builder);
  }

  @Component(
      modules = {
          ApplicationContextModule.class,
          DatabaseModule.class,
          HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule.class,
          LikeSongProcessorModule.class,
          ActivityRetainedCBuilderModule.class,
          ServiceCBuilderModule.class,
          PlayServiceModule.class,
          UserServiceModule.class
      }
  )
  @Singleton
  public abstract static class SingletonC implements FragmentGetContextFix.FragmentGetContextFixEntryPoint,
      HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint,
      ServiceComponentManager.ServiceComponentBuilderEntryPoint,
      SingletonComponent,
      GeneratedComponent,
      MusicApplication_GeneratedInjector,
      UserServiceModule.UserServiceEntryPoint,
      PlayServiceModule.LikeSongProcessorEntryPoint,
      PlayServiceModule.PlayerControllerEntryPoint,
      LikeSongProcessorModule.LikeSongProcessorEntryPoint {
  }

  @Subcomponent
  @ServiceScoped
  public abstract static class ServiceC implements ServiceComponent,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ServiceComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          AlbumDetailViewModel_HiltModules.KeyModule.class,
          ArtistDetailViewModel_HiltModules.KeyModule.class,
          CollectSongViewModel_HiltModules.KeyModule.class,
          DiscoverViewModel_HiltModules.KeyModule.class,
          HiltWrapper_ActivityRetainedComponentManager_LifecycleModule.class,
          LoginViewModel_HiltModules.KeyModule.class,
          MineViewModel_HiltModules.KeyModule.class,
          ActivityCBuilderModule.class,
          ViewModelCBuilderModule.class,
          PhoneLoginViewModel_HiltModules.KeyModule.class,
          PlaylistViewModel_HiltModules.KeyModule.class,
          QrcodeLoginViewModel_HiltModules.KeyModule.class,
          SplashViewModel_HiltModules.KeyModule.class
      }
  )
  @ActivityRetainedScoped
  public abstract static class ActivityRetainedC implements ActivityRetainedComponent,
      ActivityComponentManager.ActivityComponentBuilderEntryPoint,
      HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedLifecycleEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ActivityRetainedComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          HiltWrapper_ActivityModule.class,
          HiltWrapper_DefaultViewModelFactories_ActivityModule.class,
          FragmentCBuilderModule.class,
          ViewCBuilderModule.class
      }
  )
  @ActivityScoped
  public abstract static class ActivityC implements ActivityComponent,
      DefaultViewModelFactories.ActivityEntryPoint,
      HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint,
      FragmentComponentManager.FragmentComponentBuilderEntryPoint,
      ViewComponentManager.ViewComponentBuilderEntryPoint,
      GeneratedComponent,
      MusicFragmentContainerActivity_GeneratedInjector,
      LoginActivity_GeneratedInjector,
      MainActivity_GeneratedInjector,
      SettingsActivity_GeneratedInjector,
      PlayingActivity_GeneratedInjector,
      SplashActivity_GeneratedInjector {
    @Subcomponent.Builder
    abstract interface Builder extends ActivityComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          AlbumDetailViewModel_HiltModules.BindsModule.class,
          ArtistDetailViewModel_HiltModules.BindsModule.class,
          CollectSongViewModel_HiltModules.BindsModule.class,
          DiscoverViewModel_HiltModules.BindsModule.class,
          HiltWrapper_HiltViewModelFactory_ViewModelModule.class,
          LoginViewModel_HiltModules.BindsModule.class,
          MineViewModel_HiltModules.BindsModule.class,
          PhoneLoginViewModel_HiltModules.BindsModule.class,
          PlaylistViewModel_HiltModules.BindsModule.class,
          QrcodeLoginViewModel_HiltModules.BindsModule.class,
          SplashViewModel_HiltModules.BindsModule.class
      }
  )
  @ViewModelScoped
  public abstract static class ViewModelC implements ViewModelComponent,
      HiltViewModelFactory.ViewModelFactoriesEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ViewModelComponentBuilder {
    }
  }

  @Subcomponent
  @ViewScoped
  public abstract static class ViewC implements ViewComponent,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ViewComponentBuilder {
    }
  }

  @Subcomponent(
      modules = ViewWithFragmentCBuilderModule.class
  )
  @FragmentScoped
  public abstract static class FragmentC implements FragmentComponent,
      DefaultViewModelFactories.FragmentEntryPoint,
      ViewComponentManager.ViewWithFragmentComponentBuilderEntryPoint,
      GeneratedComponent,
      AlbumDetailFragment_GeneratedInjector,
      ArtistDetailFragment_GeneratedInjector,
      DiscoverFragment_GeneratedInjector,
      PlaylistDetailFragment_GeneratedInjector,
      PlaylistSquareFragment_GeneratedInjector,
      PlaylistTabFragment_GeneratedInjector,
      RankingFragment_GeneratedInjector,
      RecommendSongFragment_GeneratedInjector,
      SettingsActivity_SettingsFragment_GeneratedInjector,
      CurrentPlaylistFragment_GeneratedInjector,
      CollectSongFragment_GeneratedInjector,
      MineFragment_GeneratedInjector,
      LocalMusicFragment_GeneratedInjector,
      SearchFragment_GeneratedInjector,
      SearchAlbumFragment_GeneratedInjector,
      SearchArtistFragment_GeneratedInjector,
      SearchPlaylistFragment_GeneratedInjector,
      SearchSongFragment_GeneratedInjector,
      SearchUserFragment_GeneratedInjector {
    @Subcomponent.Builder
    abstract interface Builder extends FragmentComponentBuilder {
    }
  }

  @Subcomponent
  @ViewScoped
  public abstract static class ViewWithFragmentC implements ViewWithFragmentComponent,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ViewWithFragmentComponentBuilder {
    }
  }
}
