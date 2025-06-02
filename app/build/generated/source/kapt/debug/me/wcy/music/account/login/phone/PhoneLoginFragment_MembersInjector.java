package me.wcy.music.account.login.phone;

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
public final class PhoneLoginFragment_MembersInjector implements MembersInjector<PhoneLoginFragment> {
  private final Provider<UserService> userServiceProvider;

  public PhoneLoginFragment_MembersInjector(Provider<UserService> userServiceProvider) {
    this.userServiceProvider = userServiceProvider;
  }

  public static MembersInjector<PhoneLoginFragment> create(
      Provider<UserService> userServiceProvider) {
    return new PhoneLoginFragment_MembersInjector(userServiceProvider);
  }

  @Override
  public void injectMembers(PhoneLoginFragment instance) {
    injectUserService(instance, userServiceProvider.get());
  }

  @InjectedFieldSignature("me.wcy.music.account.login.phone.PhoneLoginFragment.userService")
  public static void injectUserService(PhoneLoginFragment instance, UserService userService) {
    instance.userService = userService;
  }
}
