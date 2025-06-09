package me.wcy.music.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import com.google.android.material.navigation.NavigationView;
import dagger.hilt.android.AndroidEntryPoint;
import me.wcy.music.R;
import me.wcy.music.account.service.UserService;
import me.wcy.music.common.ApiDomainDialog;
import me.wcy.music.common.BaseMusicActivity;
import me.wcy.music.common.ImmersiveDialogHelper;
import me.wcy.music.consts.RoutePath;
import me.wcy.music.databinding.ActivityMainBinding;
import me.wcy.music.databinding.NavigationHeaderBinding;
import me.wcy.music.databinding.TabItemBinding;
import me.wcy.music.service.MusicService;
import me.wcy.music.service.PlayServiceModule;
import me.wcy.music.splash.SplashActivity;
import me.wcy.music.storage.preference.ConfigPreferences;
import me.wcy.music.utils.QuitTimer;
import me.wcy.music.utils.TimeUtils;
import android.os.Handler;
import android.os.Looper;
import me.wcy.router.CRouter;
import top.wangchenyan.common.widget.pager.CustomTabPager;
import javax.inject.Inject;

/**
 * Created by wangchenyan.top on 2023/8/21.
 */
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\r\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u001bH\u0002J\u001a\u0010\u001d\u001a\u00020\u001e2\b\b\u0001\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\u001bH\u0002J\b\u0010$\u001a\u00020\u001bH\u0002J\u0012\u0010%\u001a\u00020\u001b2\b\u0010&\u001a\u0004\u0018\u00010\'H\u0014J\b\u0010(\u001a\u00020\u001bH\u0014J\u0012\u0010)\u001a\u00020\u001b2\b\u0010*\u001a\u0004\u0018\u00010+H\u0014J\u0006\u0010,\u001a\u00020\u001bJ\b\u0010-\u001a\u00020\u001bH\u0002J\u0010\u0010.\u001a\u00020\u001b2\u0006\u0010/\u001a\u00020 H\u0002J\b\u00100\u001a\u00020\u001bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000f\u001a\u00020\u00108\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0015\u001a\u00020\u00168BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0019\u0010\f\u001a\u0004\b\u0017\u0010\u0018\u00a8\u00061"}, d2 = {"Lme/wcy/music/main/MainActivity;", "Lme/wcy/music/common/BaseMusicActivity;", "()V", "onMenuSelectListener", "Lcom/google/android/material/navigation/NavigationView$OnNavigationItemSelectedListener;", "onTimerListener", "Lme/wcy/music/utils/QuitTimer$OnTimerListener;", "quitTimer", "Lme/wcy/music/utils/QuitTimer;", "getQuitTimer", "()Lme/wcy/music/utils/QuitTimer;", "quitTimer$delegate", "Lkotlin/Lazy;", "timerItem", "Landroid/view/MenuItem;", "userService", "Lme/wcy/music/account/service/UserService;", "getUserService", "()Lme/wcy/music/account/service/UserService;", "setUserService", "(Lme/wcy/music/account/service/UserService;)V", "viewBinding", "Lme/wcy/music/databinding/ActivityMainBinding;", "getViewBinding", "()Lme/wcy/music/databinding/ActivityMainBinding;", "viewBinding$delegate", "checkAndExecuteAutoPlay", "", "exitApp", "getTabItem", "Lme/wcy/music/databinding/TabItemBinding;", "icon", "", "text", "", "initDrawer", "logout", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onNewIntent", "intent", "Landroid/content/Intent;", "openDrawer", "parseIntent", "startTimer", "minute", "timerDialog", "app_debug"})
public final class MainActivity extends me.wcy.music.common.BaseMusicActivity {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewBinding$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy quitTimer$delegate = null;
    @org.jetbrains.annotations.Nullable()
    private android.view.MenuItem timerItem;
    @javax.inject.Inject()
    public me.wcy.music.account.service.UserService userService;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener onMenuSelectListener = null;
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.utils.QuitTimer.OnTimerListener onTimerListener = null;
    
    public MainActivity() {
        super();
    }
    
    private final me.wcy.music.databinding.ActivityMainBinding getViewBinding() {
        return null;
    }
    
    private final me.wcy.music.utils.QuitTimer getQuitTimer() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.account.service.UserService getUserService() {
        return null;
    }
    
    public final void setUserService(@org.jetbrains.annotations.NotNull()
    me.wcy.music.account.service.UserService p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onNewIntent(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
    }
    
    private final void initDrawer() {
    }
    
    public final void openDrawer() {
    }
    
    private final void parseIntent() {
    }
    
    private final void timerDialog() {
    }
    
    private final void startTimer(int minute) {
    }
    
    private final void logout() {
    }
    
    private final void exitApp() {
    }
    
    private final me.wcy.music.databinding.TabItemBinding getTabItem(@androidx.annotation.DrawableRes()
    int icon, java.lang.CharSequence text) {
        return null;
    }
    
    /**
     * 检查并执行自动播放功能
     * 根据用户设置在应用启动时自动开始播放音乐
     */
    private final void checkAndExecuteAutoPlay() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
}