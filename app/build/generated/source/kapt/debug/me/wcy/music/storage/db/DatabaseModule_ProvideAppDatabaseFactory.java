package me.wcy.music.storage.db;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class DatabaseModule_ProvideAppDatabaseFactory implements Factory<MusicDatabase> {
  @Override
  public MusicDatabase get() {
    return provideAppDatabase();
  }

  public static DatabaseModule_ProvideAppDatabaseFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static MusicDatabase provideAppDatabase() {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAppDatabase());
  }

  private static final class InstanceHolder {
    private static final DatabaseModule_ProvideAppDatabaseFactory INSTANCE = new DatabaseModule_ProvideAppDatabaseFactory();
  }
}
