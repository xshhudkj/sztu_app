package me.wcy.music.account.login.phone;

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
public final class PhoneLoginViewModel_Factory implements Factory<PhoneLoginViewModel> {
  private final Provider<UserService> userServiceProvider;

  public PhoneLoginViewModel_Factory(Provider<UserService> userServiceProvider) {
    this.userServiceProvider = userServiceProvider;
  }

  @Override
  public PhoneLoginViewModel get() {
    return newInstance(userServiceProvider.get());
  }

  public static PhoneLoginViewModel_Factory create(Provider<UserService> userServiceProvider) {
    return new PhoneLoginViewModel_Factory(userServiceProvider);
  }

  public static PhoneLoginViewModel newInstance(UserService userService) {
    return new PhoneLoginViewModel(userService);
  }
}
