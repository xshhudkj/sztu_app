package me.wcy.music.account.service;

import android.app.Application;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Created by wangchenyan.top on 2023/9/18.
 */
@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\'\u0018\u0000 \u00072\u00020\u0001:\u0002\u0007\bB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'\u00a8\u0006\t"}, d2 = {"Lme/wcy/music/account/service/UserServiceModule;", "", "()V", "bindUserService", "Lme/wcy/music/account/service/UserService;", "userServiceImpl", "Lme/wcy/music/account/service/UserServiceImpl;", "Companion", "UserServiceEntryPoint", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class UserServiceModule {
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.account.service.UserServiceModule.Companion Companion = null;
    
    public UserServiceModule() {
        super();
    }
    
    @dagger.Binds()
    @org.jetbrains.annotations.NotNull()
    public abstract me.wcy.music.account.service.UserService bindUserService(@org.jetbrains.annotations.NotNull()
    me.wcy.music.account.service.UserServiceImpl userServiceImpl);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0005\u00a8\u0006\u0006"}, d2 = {"Lme/wcy/music/account/service/UserServiceModule$Companion;", "", "()V", "userService", "Lme/wcy/music/account/service/UserService;", "Landroid/app/Application;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final me.wcy.music.account.service.UserService userService(@org.jetbrains.annotations.NotNull()
        android.app.Application $this$userService) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&\u00a8\u0006\u0004"}, d2 = {"Lme/wcy/music/account/service/UserServiceModule$UserServiceEntryPoint;", "", "userService", "Lme/wcy/music/account/service/UserService;", "app_debug"})
    @dagger.hilt.EntryPoint()
    @dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
    public static abstract interface UserServiceEntryPoint {
        
        @org.jetbrains.annotations.NotNull()
        public abstract me.wcy.music.account.service.UserService userService();
    }
}