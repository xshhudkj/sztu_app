package me.wcy.music;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.account.service.UserService;
import me.wcy.music.common.DarkModeService;
import me.wcy.music.service.likesong.LikeSongProcessor;

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
public final class MusicApplication_MembersInjector implements MembersInjector<MusicApplication> {
  private final Provider<UserService> userServiceProvider;

  private final Provider<DarkModeService> darkModeServiceProvider;

  private final Provider<LikeSongProcessor> likeSongProcessorProvider;

  public MusicApplication_MembersInjector(Provider<UserService> userServiceProvider,
      Provider<DarkModeService> darkModeServiceProvider,
      Provider<LikeSongProcessor> likeSongProcessorProvider) {
    this.userServiceProvider = userServiceProvider;
    this.darkModeServiceProvider = darkModeServiceProvider;
    this.likeSongProcessorProvider = likeSongProcessorProvider;
  }

  public static MembersInjector<MusicApplication> create(Provider<UserService> userServiceProvider,
      Provider<DarkModeService> darkModeServiceProvider,
      Provider<LikeSongProcessor> likeSongProcessorProvider) {
    return new MusicApplication_MembersInjector(userServiceProvider, darkModeServiceProvider, likeSongProcessorProvider);
  }

  @Override
  public void injectMembers(MusicApplication instance) {
    injectUserService(instance, userServiceProvider.get());
    injectDarkModeService(instance, darkModeServiceProvider.get());
    injectLikeSongProcessor(instance, likeSongProcessorProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.MusicApplication.userService")
  public static void injectUserService(MusicApplication instance, UserService userService) {
    instance.userService = userService;
  }

  @InjectedFieldSignature("me.wcy.music.MusicApplication.darkModeService")
  public static void injectDarkModeService(MusicApplication instance,
      DarkModeService darkModeService) {
    instance.darkModeService = darkModeService;
  }

  @InjectedFieldSignature("me.wcy.music.MusicApplication.likeSongProcessor")
  public static void injectLikeSongProcessor(MusicApplication instance,
      LikeSongProcessor likeSongProcessor) {
    instance.likeSongProcessor = likeSongProcessor;
  }
}
