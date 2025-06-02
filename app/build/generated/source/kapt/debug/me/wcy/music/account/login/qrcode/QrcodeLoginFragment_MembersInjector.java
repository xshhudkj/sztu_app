package me.wcy.music.account.login.qrcode;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import me.wcy.music.account.service.UserService;

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
public final class QrcodeLoginFragment_MembersInjector implements MembersInjector<QrcodeLoginFragment> {
  private final Provider<UserService> userServiceProvider;

  public QrcodeLoginFragment_MembersInjector(Provider<UserService> userServiceProvider) {
    this.userServiceProvider = userServiceProvider;
  }

  public static MembersInjector<QrcodeLoginFragment> create(
      Provider<UserService> userServiceProvider) {
    return new QrcodeLoginFragment_MembersInjector(userServiceProvider);
  }

  @Override
  public void injectMembers(QrcodeLoginFragment instance) {
    injectUserService(instance, userServiceProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.account.login.qrcode.QrcodeLoginFragment.userService")
  public static void injectUserService(QrcodeLoginFragment instance, UserService userService) {
    instance.userService = userService;
  }
}
