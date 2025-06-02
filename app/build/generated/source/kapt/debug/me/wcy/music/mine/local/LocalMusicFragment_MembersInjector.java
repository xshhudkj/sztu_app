package me.wcy.music.mine.local;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.service.PlayerController;

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
public final class LocalMusicFragment_MembersInjector implements MembersInjector<LocalMusicFragment> {
  private final Provider<PlayerController> playerControllerProvider;

  public LocalMusicFragment_MembersInjector(Provider<PlayerController> playerControllerProvider) {
    this.playerControllerProvider = playerControllerProvider;
  }

  public static MembersInjector<LocalMusicFragment> create(
      Provider<PlayerController> playerControllerProvider) {
    return new LocalMusicFragment_MembersInjector(playerControllerProvider);
  }

  @Override
  public void injectMembers(LocalMusicFragment instance) {
    injectPlayerController(instance, playerControllerProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.mine.local.LocalMusicFragment.playerController")
  public static void injectPlayerController(LocalMusicFragment instance,
      PlayerController playerController) {
    instance.playerController = playerController;
  }
}
