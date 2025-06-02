package me.wcy.music.main.playing;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.service.PlayerController;
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
public final class PlayingActivity_MembersInjector implements MembersInjector<PlayingActivity> {
  private final Provider<PlayerController> playerControllerProvider;

  private final Provider<LikeSongProcessor> likeSongProcessorProvider;

  public PlayingActivity_MembersInjector(Provider<PlayerController> playerControllerProvider,
      Provider<LikeSongProcessor> likeSongProcessorProvider) {
    this.playerControllerProvider = playerControllerProvider;
    this.likeSongProcessorProvider = likeSongProcessorProvider;
  }

  public static MembersInjector<PlayingActivity> create(
      Provider<PlayerController> playerControllerProvider,
      Provider<LikeSongProcessor> likeSongProcessorProvider) {
    return new PlayingActivity_MembersInjector(playerControllerProvider, likeSongProcessorProvider);
  }

  @Override
  public void injectMembers(PlayingActivity instance) {
    injectPlayerController(instance, playerControllerProvider.get());
    injectLikeSongProcessor(instance, likeSongProcessorProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.main.playing.PlayingActivity.playerController")
  public static void injectPlayerController(PlayingActivity instance,
      PlayerController playerController) {
    instance.playerController = playerController;
  }

  @InjectedFieldSignature("me.wcy.music.main.playing.PlayingActivity.likeSongProcessor")
  public static void injectLikeSongProcessor(PlayingActivity instance,
      LikeSongProcessor likeSongProcessor) {
    instance.likeSongProcessor = likeSongProcessor;
  }
}
