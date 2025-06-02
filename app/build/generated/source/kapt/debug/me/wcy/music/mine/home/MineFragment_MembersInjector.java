package me.wcy.music.mine.home;

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
public final class MineFragment_MembersInjector implements MembersInjector<MineFragment> {
  private final Provider<UserService> userServiceProvider;

  public MineFragment_MembersInjector(Provider<UserService> userServiceProvider) {
    this.userServiceProvider = userServiceProvider;
  }

  public static MembersInjector<MineFragment> create(Provider<UserService> userServiceProvider) {
    return new MineFragment_MembersInjector(userServiceProvider);
  }

  @Override
  public void injectMembers(MineFragment instance) {
    injectUserService(instance, userServiceProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.mine.home.MineFragment.userService")
  public static void injectUserService(MineFragment instance, UserService userService) {
    instance.userService = userService;
  }
}
