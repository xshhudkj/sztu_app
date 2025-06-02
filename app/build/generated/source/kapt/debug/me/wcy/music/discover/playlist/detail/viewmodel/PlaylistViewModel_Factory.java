package me.wcy.music.discover.playlist.detail.viewmodel;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.service.likesong.LikeSongProcessor;

@ScopeMetadata
@QualifierMetadata
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
public final class PlaylistViewModel_Factory implements Factory<PlaylistViewModel> {
  private final Provider<LikeSongProcessor> likeSongProcessorProvider;

  public PlaylistViewModel_Factory(Provider<LikeSongProcessor> likeSongProcessorProvider) {
    this.likeSongProcessorProvider = likeSongProcessorProvider;
  }

  @Override
  public PlaylistViewModel get() {
    PlaylistViewModel instance = newInstance();
    PlaylistViewModel_MembersInjector.injectLikeSongProcessor(instance, likeSongProcessorProvider.get());
    return instance;
  }

  public static PlaylistViewModel_Factory create(
      Provider<LikeSongProcessor> likeSongProcessorProvider) {
    return new PlaylistViewModel_Factory(likeSongProcessorProvider);
  }

  public static PlaylistViewModel newInstance() {
    return new PlaylistViewModel();
  }
}
