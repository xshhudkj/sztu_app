package me.wcy.music.discover.playlist.detail;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.account.service.UserService;
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
public final class PlaylistDetailFragment_MembersInjector implements MembersInjector<PlaylistDetailFragment> {
  private final Provider<UserService> userServiceProvider;

  private final Provider<PlayerController> playerControllerProvider;

  public PlaylistDetailFragment_MembersInjector(Provider<UserService> userServiceProvider,
      Provider<PlayerController> playerControllerProvider) {
    this.userServiceProvider = userServiceProvider;
    this.playerControllerProvider = playerControllerProvider;
  }

  public static MembersInjector<PlaylistDetailFragment> create(
      Provider<UserService> userServiceProvider,
      Provider<PlayerController> playerControllerProvider) {
    return new PlaylistDetailFragment_MembersInjector(userServiceProvider, playerControllerProvider);
  }

  @Override
  public void injectMembers(PlaylistDetailFragment instance) {
    injectUserService(instance, userServiceProvider.get());
    injectPlayerController(instance, playerControllerProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.discover.playlist.detail.PlaylistDetailFragment.userService")
  public static void injectUserService(PlaylistDetailFragment instance, UserService userService) {
    instance.userService = userService;
  }

  @InjectedFieldSignature("me.wcy.music.discover.playlist.detail.PlaylistDetailFragment.playerController")
  public static void injectPlayerController(PlaylistDetailFragment instance,
      PlayerController playerController) {
    instance.playerController = playerController;
  }
}
