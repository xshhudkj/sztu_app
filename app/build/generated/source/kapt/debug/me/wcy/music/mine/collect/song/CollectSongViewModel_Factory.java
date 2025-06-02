package me.wcy.music.mine.collect.song;

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
public final class CollectSongViewModel_Factory implements Factory<CollectSongViewModel> {
  private final Provider<UserService> userServiceProvider;

  public CollectSongViewModel_Factory(Provider<UserService> userServiceProvider) {
    this.userServiceProvider = userServiceProvider;
  }

  @Override
  public CollectSongViewModel get() {
    CollectSongViewModel instance = newInstance();
    CollectSongViewModel_MembersInjector.injectUserService(instance, userServiceProvider.get());
    return instance;
  }

  public static CollectSongViewModel_Factory create(Provider<UserService> userServiceProvider) {
    return new CollectSongViewModel_Factory(userServiceProvider);
  }

  public static CollectSongViewModel newInstance() {
    return new CollectSongViewModel();
  }
}
