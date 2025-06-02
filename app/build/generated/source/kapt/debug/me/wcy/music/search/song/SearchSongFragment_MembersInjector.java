package me.wcy.music.search.song;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.service.PlayerController;

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
public final class SearchSongFragment_MembersInjector implements MembersInjector<SearchSongFragment> {
  private final Provider<PlayerController> playerControllerProvider;

  public SearchSongFragment_MembersInjector(Provider<PlayerController> playerControllerProvider) {
    this.playerControllerProvider = playerControllerProvider;
  }

  public static MembersInjector<SearchSongFragment> create(
      Provider<PlayerController> playerControllerProvider) {
    return new SearchSongFragment_MembersInjector(playerControllerProvider);
  }

  @Override
  public void injectMembers(SearchSongFragment instance) {
    injectPlayerController(instance, playerControllerProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.search.song.SearchSongFragment.playerController")
  public static void injectPlayerController(SearchSongFragment instance,
      PlayerController playerController) {
    instance.playerController = playerController;
  }
}
