package me.wcy.music.common;

import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatDelegate;
import com.blankj.utilcode.util.ActivityUtils;
import me.wcy.music.storage.preference.ConfigPreferences;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001:\u0001\u000bB\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0002\u00a8\u0006\f"}, d2 = {"Lme/wcy/music/common/DarkModeService;", "", "()V", "init", "", "isDarkMode", "", "setDarkMode", "mode", "Lme/wcy/music/common/DarkModeService$DarkMode;", "setDarkModeInternal", "DarkMode", "app_debug"})
public final class DarkModeService {
    
    @javax.inject.Inject()
    public DarkModeService() {
        super();
    }
    
    public final void init() {
    }
    
    public final void setDarkMode(@org.jetbrains.annotations.NotNull()
    me.wcy.music.common.DarkModeService.DarkMode mode) {
    }
    
    private final void setDarkModeInternal(me.wcy.music.common.DarkModeService.DarkMode mode) {
    }
    
    public final boolean isDarkMode() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00102\u00020\u0001:\u0004\u000f\u0010\u0011\u0012B\u0017\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000e\u001a\u00020\u0005H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0082\u0001\u0003\u0013\u0014\u0015\u00a8\u0006\u0016"}, d2 = {"Lme/wcy/music/common/DarkModeService$DarkMode;", "", "value", "", "systemValue", "", "(Ljava/lang/String;I)V", "getSystemValue", "()I", "getValue", "()Ljava/lang/String;", "equals", "", "other", "hashCode", "Auto", "Companion", "Dark", "Light", "Lme/wcy/music/common/DarkModeService$DarkMode$Auto;", "Lme/wcy/music/common/DarkModeService$DarkMode$Dark;", "Lme/wcy/music/common/DarkModeService$DarkMode$Light;", "app_debug"})
    public static abstract class DarkMode {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String value = null;
        private final int systemValue = 0;
        @org.jetbrains.annotations.NotNull()
        public static final me.wcy.music.common.DarkModeService.DarkMode.Companion Companion = null;
        
        private DarkMode(java.lang.String value, int systemValue) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getValue() {
            return null;
        }
        
        public final int getSystemValue() {
            return 0;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lme/wcy/music/common/DarkModeService$DarkMode$Auto;", "Lme/wcy/music/common/DarkModeService$DarkMode;", "()V", "app_debug"})
        public static final class Auto extends me.wcy.music.common.DarkModeService.DarkMode {
            @org.jetbrains.annotations.NotNull()
            public static final me.wcy.music.common.DarkModeService.DarkMode.Auto INSTANCE = null;
            
            private Auto() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lme/wcy/music/common/DarkModeService$DarkMode$Companion;", "", "()V", "fromValue", "Lme/wcy/music/common/DarkModeService$DarkMode;", "value", "", "app_debug"})
        public static final class Companion {
            
            private Companion() {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final me.wcy.music.common.DarkModeService.DarkMode fromValue(@org.jetbrains.annotations.NotNull()
            java.lang.String value) {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lme/wcy/music/common/DarkModeService$DarkMode$Dark;", "Lme/wcy/music/common/DarkModeService$DarkMode;", "()V", "app_debug"})
        public static final class Dark extends me.wcy.music.common.DarkModeService.DarkMode {
            @org.jetbrains.annotations.NotNull()
            public static final me.wcy.music.common.DarkModeService.DarkMode.Dark INSTANCE = null;
            
            private Dark() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lme/wcy/music/common/DarkModeService$DarkMode$Light;", "Lme/wcy/music/common/DarkModeService$DarkMode;", "()V", "app_debug"})
        public static final class Light extends me.wcy.music.common.DarkModeService.DarkMode {
            @org.jetbrains.annotations.NotNull()
            public static final me.wcy.music.common.DarkModeService.DarkMode.Light INSTANCE = null;
            
            private Light() {
            }
        }
    }
}