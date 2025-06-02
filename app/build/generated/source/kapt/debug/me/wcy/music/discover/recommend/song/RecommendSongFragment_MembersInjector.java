package me.wcy.music.discover.recommend.song;

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
public final class RecommendSongFragment_MembersInjector implements MembersInjector<RecommendSongFragment> {
  private final Provider<PlayerController> playerControllerProvider;

  public RecommendSongFragment_MembersInjector(
      Provider<PlayerController> playerControllerProvider) {
    this.playerControllerProvider = playerControllerProvider;
  }

  public static MembersInjector<RecommendSongFragment> create(
      Provider<PlayerController> playerControllerProvider) {
    return new RecommendSongFragment_MembersInjector(playerControllerProvider);
  }

  @Override
  public void injectMembers(RecommendSongFragment instance) {
    injectPlayerController(instance, playerControllerProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.discover.recommend.song.RecommendSongFragment.playerController")
  public static void injectPlayerController(RecommendSongFragment instance,
      PlayerController playerController) {
    instance.playerController = playerController;
  }
}
