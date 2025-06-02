package me.wcy.music.mine.home.viewmodel;

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
public final class MineViewModel_MembersInjector implements MembersInjector<MineViewModel> {
  private final Provider<UserService> userServiceProvider;

  public MineViewModel_MembersInjector(Provider<UserService> userServiceProvider) {
    this.userServiceProvider = userServiceProvider;
  }

  public static MembersInjector<MineViewModel> create(Provider<UserService> userServiceProvider) {
    return new MineViewModel_MembersInjector(userServiceProvider);
  }

  @Override
  public void injectMembers(MineViewModel instance) {
    injectUserService(instance, userServiceProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.mine.home.viewmodel.MineViewModel.userService")
  public static void injectUserService(MineViewModel instance, UserService userService) {
    instance.userService = userService;
  }
}
