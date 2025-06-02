package me.wcy.music.discover.playlist.detail.viewmodel;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.service.likesong.LikeSongProcessor;

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
public final class PlaylistViewModel_MembersInjector implements MembersInjector<PlaylistViewModel> {
  private final Provider<LikeSongProcessor> likeSongProcessorProvider;

  public PlaylistViewModel_MembersInjector(Provider<LikeSongProcessor> likeSongProcessorProvider) {
    this.likeSongProcessorProvider = likeSongProcessorProvider;
  }

  public static MembersInjector<PlaylistViewModel> create(
      Provider<LikeSongProcessor> likeSongProcessorProvider) {
    return new PlaylistViewModel_MembersInjector(likeSongProcessorProvider);
  }

  @Override
  public void injectMembers(PlaylistViewModel instance) {
    injectLikeSongProcessor(instance, likeSongProcessorProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.discover.playlist.detail.viewmodel.PlaylistViewModel.likeSongProcessor")
  public static void injectLikeSongProcessor(PlaylistViewModel instance,
      LikeSongProcessor likeSongProcessor) {
    instance.likeSongProcessor = likeSongProcessor;
  }
}
