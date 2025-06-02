package me.wcy.music.discover.home;

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
public final class DiscoverFragment_MembersInjector implements MembersInjector<DiscoverFragment> {
  private final Provider<UserService> userServiceProvider;

  private final Provider<PlayerController> playerControllerProvider;

  public DiscoverFragment_MembersInjector(Provider<UserService> userServiceProvider,
      Provider<PlayerController> playerControllerProvider) {
    this.userServiceProvider = userServiceProvider;
    this.playerControllerProvider = playerControllerProvider;
  }

  public static MembersInjector<DiscoverFragment> create(Provider<UserService> userServiceProvider,
      Provider<PlayerController> playerControllerProvider) {
    return new DiscoverFragment_MembersInjector(userServiceProvider, playerControllerProvider);
  }

  @Override
  public void injectMembers(DiscoverFragment instance) {
    injectUserService(instance, userServiceProvider.get());
    injectPlayerController(instance, playerControllerProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.discover.home.DiscoverFragment.userService")
  public static void injectUserService(DiscoverFragment instance, UserService userService) {
    instance.userService = userService;
  }

  @InjectedFieldSignature("me.wcy.music.discover.home.DiscoverFragment.playerController")
  public static void injectPlayerController(DiscoverFragment instance,
      PlayerController playerController) {
    instance.playerController = playerController;
  }
}
