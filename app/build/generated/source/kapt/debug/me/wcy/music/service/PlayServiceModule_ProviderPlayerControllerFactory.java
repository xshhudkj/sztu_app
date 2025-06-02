package me.wcy.music.service;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.storage.db.MusicDatabase;

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
public final class PlayServiceModule_ProviderPlayerControllerFactory implements Factory<PlayerController> {
  private final Provider<MusicDatabase> dbProvider;

  public PlayServiceModule_ProviderPlayerControllerFactory(Provider<MusicDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public PlayerController get() {
    return providerPlayerController(dbProvider.get());
  }

  public static PlayServiceModule_ProviderPlayerControllerFactory create(
      Provider<MusicDatabase> dbProvider) {
    return new PlayServiceModule_ProviderPlayerControllerFactory(dbProvider);
  }

  public static PlayerController providerPlayerController(MusicDatabase db) {
    return Preconditions.checkNotNullFromProvides(PlayServiceModule.INSTANCE.providerPlayerController(db));
  }
}
