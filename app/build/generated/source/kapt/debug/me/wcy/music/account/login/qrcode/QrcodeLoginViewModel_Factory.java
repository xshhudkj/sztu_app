package me.wcy.music.account.login.qrcode;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.account.service.UserService;

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
public final class QrcodeLoginViewModel_Factory implements Factory<QrcodeLoginViewModel> {
  private final Provider<UserService> userServiceProvider;

  public QrcodeLoginViewModel_Factory(Provider<UserService> userServiceProvider) {
    this.userServiceProvider = userServiceProvider;
  }

  @Override
  public QrcodeLoginViewModel get() {
    return newInstance(userServiceProvider.get());
  }

  public static QrcodeLoginViewModel_Factory create(Provider<UserService> userServiceProvider) {
    return new QrcodeLoginViewModel_Factory(userServiceProvider);
  }

  public static QrcodeLoginViewModel newInstance(UserService userService) {
    return new QrcodeLoginViewModel(userService);
  }
}
