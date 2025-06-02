package me.wcy.music.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import dagger.hilt.android.AndroidEntryPoint;
import me.wcy.music.R;
import me.wcy.music.common.BaseMusicActivity;
import me.wcy.music.common.DarkModeService;
import me.wcy.music.consts.PreferenceName;
import me.wcy.music.service.PlayerController;
import me.wcy.music.storage.preference.ConfigPreferences;
import me.wcy.music.utils.MusicUtils;
import me.wcy.router.annotation.Route;
import javax.inject.Inject;

@me.wcy.router.annotation.Route(value = "/settings")
@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014\u00a8\u0006\b"}, d2 = {"Lme/wcy/music/main/SettingsActivity;", "Lme/wcy/music/common/BaseMusicActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "SettingsFragment", "app_debug"})
public final class SettingsActivity extends me.wcy.music.common.BaseMusicActivity {
    
    public SettingsActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @dagger.hilt.android.AndroidEntryPoint()
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J \u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%2\u0006\u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020(H\u0002J\b\u0010*\u001a\u00020+H\u0002J\b\u0010,\u001a\u00020+H\u0002J\b\u0010-\u001a\u00020+H\u0002J\b\u0010.\u001a\u00020+H\u0002J\b\u0010/\u001a\u00020+H\u0002J\u001c\u00100\u001a\u00020+2\b\u00101\u001a\u0004\u0018\u0001022\b\u00103\u001a\u0004\u0018\u00010%H\u0016J\b\u00104\u001a\u00020+H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001b\u0010\u000f\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\b\u001a\u0004\b\u0010\u0010\u0006R\u001b\u0010\u0012\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0014\u0010\b\u001a\u0004\b\u0013\u0010\u0006R\u001b\u0010\u0015\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\b\u001a\u0004\b\u0016\u0010\u0006R\u001b\u0010\u0018\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001a\u0010\b\u001a\u0004\b\u0019\u0010\u0006R\u001e\u0010\u001b\u001a\u00020\u001c8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001b\u0010!\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b#\u0010\b\u001a\u0004\b\"\u0010\u0006\u00a8\u00065"}, d2 = {"Lme/wcy/music/main/SettingsActivity$SettingsFragment;", "Landroidx/preference/PreferenceFragmentCompat;", "()V", "darkMode", "Landroidx/preference/Preference;", "getDarkMode", "()Landroidx/preference/Preference;", "darkMode$delegate", "Lkotlin/Lazy;", "darkModeService", "Lme/wcy/music/common/DarkModeService;", "getDarkModeService", "()Lme/wcy/music/common/DarkModeService;", "setDarkModeService", "(Lme/wcy/music/common/DarkModeService;)V", "downloadSoundQuality", "getDownloadSoundQuality", "downloadSoundQuality$delegate", "filterSize", "getFilterSize", "filterSize$delegate", "filterTime", "getFilterTime", "filterTime$delegate", "playSoundQuality", "getPlaySoundQuality", "playSoundQuality$delegate", "playerController", "Lme/wcy/music/service/PlayerController;", "getPlayerController", "()Lme/wcy/music/service/PlayerController;", "setPlayerController", "(Lme/wcy/music/service/PlayerController;)V", "soundEffect", "getSoundEffect", "soundEffect$delegate", "getSummary", "", "value", "entries", "", "values", "initDarkMode", "", "initDownloadSoundQuality", "initFilter", "initPlaySoundQuality", "initSoundEffect", "onCreatePreferences", "savedInstanceState", "Landroid/os/Bundle;", "rootKey", "startEqualizer", "app_debug"})
    public static final class SettingsFragment extends androidx.preference.PreferenceFragmentCompat {
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy darkMode$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy playSoundQuality$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy soundEffect$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy downloadSoundQuality$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy filterSize$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy filterTime$delegate = null;
        @javax.inject.Inject()
        public me.wcy.music.service.PlayerController playerController;
        @javax.inject.Inject()
        public me.wcy.music.common.DarkModeService darkModeService;
        
        public SettingsFragment() {
            super();
        }
        
        private final androidx.preference.Preference getDarkMode() {
            return null;
        }
        
        private final androidx.preference.Preference getPlaySoundQuality() {
            return null;
        }
        
        private final androidx.preference.Preference getSoundEffect() {
            return null;
        }
        
        private final androidx.preference.Preference getDownloadSoundQuality() {
            return null;
        }
        
        private final androidx.preference.Preference getFilterSize() {
            return null;
        }
        
        private final androidx.preference.Preference getFilterTime() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final me.wcy.music.service.PlayerController getPlayerController() {
            return null;
        }
        
        public final void setPlayerController(@org.jetbrains.annotations.NotNull()
        me.wcy.music.service.PlayerController p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final me.wcy.music.common.DarkModeService getDarkModeService() {
            return null;
        }
        
        public final void setDarkModeService(@org.jetbrains.annotations.NotNull()
        me.wcy.music.common.DarkModeService p0) {
        }
        
        @java.lang.Override()
        public void onCreatePreferences(@org.jetbrains.annotations.Nullable()
        android.os.Bundle savedInstanceState, @org.jetbrains.annotations.Nullable()
        java.lang.String rootKey) {
        }
        
        private final void initDarkMode() {
        }
        
        private final void initPlaySoundQuality() {
        }
        
        private final void initSoundEffect() {
        }
        
        private final void initDownloadSoundQuality() {
        }
        
        private final void initFilter() {
        }
        
        private final void startEqualizer() {
        }
        
        private final java.lang.String getSummary(java.lang.String value, int entries, int values) {
            return null;
        }
    }
}