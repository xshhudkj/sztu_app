package me.wcy.music.common;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class DarkModeService_Factory implements Factory<DarkModeService> {
  @Override
  public DarkModeService get() {
    return newInstance();
  }

  public static DarkModeService_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DarkModeService newInstance() {
    return new DarkModeService();
  }

  private static final class InstanceHolder {
    private static final DarkModeService_Factory INSTANCE = new DarkModeService_Factory();
  }
}
