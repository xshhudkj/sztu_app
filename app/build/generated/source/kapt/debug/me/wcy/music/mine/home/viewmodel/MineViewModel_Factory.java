package me.wcy.music.mine.home.viewmodel;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.account.service.UserService;

@ScopeMetadata
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
public final class MineViewModel_Factory implements Factory<MineViewModel> {
  private final Provider<UserService> userServiceProvider;

  public MineViewModel_Factory(Provider<UserService> userServiceProvider) {
    this.userServiceProvider = userServiceProvider;
  }

  @Override
  public MineViewModel get() {
    MineViewModel instance = newInstance();
    MineViewModel_MembersInjector.injectUserService(instance, userServiceProvider.get());
    return instance;
  }

  public static MineViewModel_Factory create(Provider<UserService> userServiceProvider) {
    return new MineViewModel_Factory(userServiceProvider);
  }

  public static MineViewModel newInstance() {
    return new MineViewModel();
  }
}
