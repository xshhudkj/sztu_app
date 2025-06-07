package me.wcy.music.mine.home;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.account.service.UserService;
import me.wcy.music.account.service.UserStateManager;

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
public final class MineFragment_MembersInjector implements MembersInjector<MineFragment> {
  private final Provider<UserService> userServiceProvider;

  private final Provider<UserStateManager> userStateManagerProvider;

  public MineFragment_MembersInjector(Provider<UserService> userServiceProvider,
      Provider<UserStateManager> userStateManagerProvider) {
    this.userServiceProvider = userServiceProvider;
    this.userStateManagerProvider = userStateManagerProvider;
  }

  public static MembersInjector<MineFragment> create(Provider<UserService> userServiceProvider,
      Provider<UserStateManager> userStateManagerProvider) {
    return new MineFragment_MembersInjector(userServiceProvider, userStateManagerProvider);
  }

  @Override
  public void injectMembers(MineFragment instance) {
    injectUserService(instance, userServiceProvider.get());
    injectUserStateManager(instance, userStateManagerProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.mine.home.MineFragment.userService")
  public static void injectUserService(MineFragment instance, UserService userService) {
    instance.userService = userService;
  }

  @InjectedFieldSignature("me.wcy.music.mine.home.MineFragment.userStateManager")
  public static void injectUserStateManager(MineFragment instance,
      UserStateManager userStateManager) {
    instance.userStateManager = userStateManager;
  }
}
