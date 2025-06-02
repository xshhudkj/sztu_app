package me.wcy.music.main;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<UserService> userServiceProvider;

  public MainActivity_MembersInjector(Provider<UserService> userServiceProvider) {
    this.userServiceProvider = userServiceProvider;
  }

  public static MembersInjector<MainActivity> create(Provider<UserService> userServiceProvider) {
    return new MainActivity_MembersInjector(userServiceProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectUserService(instance, userServiceProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.main.MainActivity.userService")
  public static void injectUserService(MainActivity instance, UserService userService) {
    instance.userService = userService;
  }
}
