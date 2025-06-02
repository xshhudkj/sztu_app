package me.wcy.music.discover.home.viewmodel;

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
public final class DiscoverViewModel_Factory implements Factory<DiscoverViewModel> {
  private final Provider<UserService> userServiceProvider;

  public DiscoverViewModel_Factory(Provider<UserService> userServiceProvider) {
    this.userServiceProvider = userServiceProvider;
  }

  @Override
  public DiscoverViewModel get() {
    return newInstance(userServiceProvider.get());
  }

  public static DiscoverViewModel_Factory create(Provider<UserService> userServiceProvider) {
    return new DiscoverViewModel_Factory(userServiceProvider);
  }

  public static DiscoverViewModel newInstance(UserService userService) {
    return new DiscoverViewModel(userService);
  }
}
