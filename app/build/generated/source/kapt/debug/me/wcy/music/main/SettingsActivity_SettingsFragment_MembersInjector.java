package me.wcy.music.main;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.common.DarkModeService;
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
public final class SettingsActivity_SettingsFragment_MembersInjector implements MembersInjector<SettingsActivity.SettingsFragment> {
  private final Provider<PlayerController> playerControllerProvider;

  private final Provider<DarkModeService> darkModeServiceProvider;

  public SettingsActivity_SettingsFragment_MembersInjector(
      Provider<PlayerController> playerControllerProvider,
      Provider<DarkModeService> darkModeServiceProvider) {
    this.playerControllerProvider = playerControllerProvider;
    this.darkModeServiceProvider = darkModeServiceProvider;
  }

  public static MembersInjector<SettingsActivity.SettingsFragment> create(
      Provider<PlayerController> playerControllerProvider,
      Provider<DarkModeService> darkModeServiceProvider) {
    return new SettingsActivity_SettingsFragment_MembersInjector(playerControllerProvider, darkModeServiceProvider);
  }

  @Override
  public void injectMembers(SettingsActivity.SettingsFragment instance) {
    injectPlayerController(instance, playerControllerProvider.get());
    injectDarkModeService(instance, darkModeServiceProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.main.SettingsActivity.SettingsFragment.playerController")
  public static void injectPlayerController(SettingsActivity.SettingsFragment instance,
      PlayerController playerController) {
    instance.playerController = playerController;
  }

  @InjectedFieldSignature("me.wcy.music.main.SettingsActivity.SettingsFragment.darkModeService")
  public static void injectDarkModeService(SettingsActivity.SettingsFragment instance,
      DarkModeService darkModeService) {
    instance.darkModeService = darkModeService;
  }
}
