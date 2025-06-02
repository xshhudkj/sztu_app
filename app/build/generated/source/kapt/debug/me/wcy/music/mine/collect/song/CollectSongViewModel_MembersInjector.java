package me.wcy.music.mine.collect.song;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.account.service.UserService;

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
public final class CollectSongViewModel_MembersInjector implements MembersInjector<CollectSongViewModel> {
  private final Provider<UserService> userServiceProvider;

  public CollectSongViewModel_MembersInjector(Provider<UserService> userServiceProvider) {
    this.userServiceProvider = userServiceProvider;
  }

  public static MembersInjector<CollectSongViewModel> create(
      Provider<UserService> userServiceProvider) {
    return new CollectSongViewModel_MembersInjector(userServiceProvider);
  }

  @Override
  public void injectMembers(CollectSongViewModel instance) {
    injectUserService(instance, userServiceProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.mine.collect.song.CollectSongViewModel.userService")
  public static void injectUserService(CollectSongViewModel instance, UserService userService) {
    instance.userService = userService;
  }
}
