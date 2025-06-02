package me.wcy.music.discover.ranking;

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
public final class RankingFragment_MembersInjector implements MembersInjector<RankingFragment> {
  private final Provider<PlayerController> playerControllerProvider;

  public RankingFragment_MembersInjector(Provider<PlayerController> playerControllerProvider) {
    this.playerControllerProvider = playerControllerProvider;
  }

  public static MembersInjector<RankingFragment> create(
      Provider<PlayerController> playerControllerProvider) {
    return new RankingFragment_MembersInjector(playerControllerProvider);
  }

  @Override
  public void injectMembers(RankingFragment instance) {
    injectPlayerController(instance, playerControllerProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.discover.ranking.RankingFragment.playerController")
  public static void injectPlayerController(RankingFragment instance,
      PlayerController playerController) {
    instance.playerController = playerController;
  }
}
