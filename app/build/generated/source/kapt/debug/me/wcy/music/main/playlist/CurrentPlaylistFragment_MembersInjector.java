package me.wcy.music.main.playlist;

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
public final class CurrentPlaylistFragment_MembersInjector implements MembersInjector<CurrentPlaylistFragment> {
  private final Provider<PlayerController> playerControllerProvider;

  public CurrentPlaylistFragment_MembersInjector(
      Provider<PlayerController> playerControllerProvider) {
    this.playerControllerProvider = playerControllerProvider;
  }

  public static MembersInjector<CurrentPlaylistFragment> create(
      Provider<PlayerController> playerControllerProvider) {
    return new CurrentPlaylistFragment_MembersInjector(playerControllerProvider);
  }

  @Override
  public void injectMembers(CurrentPlaylistFragment instance) {
    injectPlayerController(instance, playerControllerProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.main.playlist.CurrentPlaylistFragment.playerController")
  public static void injectPlayerController(CurrentPlaylistFragment instance,
      PlayerController playerController) {
    instance.playerController = playerController;
  }
}
