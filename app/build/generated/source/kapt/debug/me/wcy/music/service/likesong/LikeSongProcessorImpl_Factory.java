package me.wcy.music.service.likesong;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.account.service.UserService;

@ScopeMetadata("javax.inject.Singleton")
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
public final class LikeSongProcessorImpl_Factory implements Factory<LikeSongProcessorImpl> {
  private final Provider<UserService> userServiceProvider;

  public LikeSongProcessorImpl_Factory(Provider<UserService> userServiceProvider) {
    this.userServiceProvider = userServiceProvider;
  }

  @Override
  public LikeSongProcessorImpl get() {
    return newInstance(userServiceProvider.get());
  }

  public static LikeSongProcessorImpl_Factory create(Provider<UserService> userServiceProvider) {
    return new LikeSongProcessorImpl_Factory(userServiceProvider);
  }

  public static LikeSongProcessorImpl newInstance(UserService userService) {
    return new LikeSongProcessorImpl(userService);
  }
}
