package me.wcy.music.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.blankj.utilcode.util.AppUtils;
import me.wcy.music.R;
import me.wcy.music.common.BaseMusicActivity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014\u00a8\u0006\b"}, d2 = {"Lme/wcy/music/main/AboutActivity;", "Lme/wcy/music/common/BaseMusicActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "AboutFragment", "app_debug"})
public final class AboutActivity extends me.wcy.music.common.BaseMusicActivity {
    
    public AboutActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\u0010\u0010!\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020 H\u0002J\b\u0010#\u001a\u00020\u001cH\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\n\u0010\u0006R\u001b\u0010\f\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\b\u001a\u0004\b\r\u0010\u0006R\u001b\u0010\u000f\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\b\u001a\u0004\b\u0010\u0010\u0006R\u001b\u0010\u0012\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0014\u0010\b\u001a\u0004\b\u0013\u0010\u0006R\u001b\u0010\u0015\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\b\u001a\u0004\b\u0016\u0010\u0006R\u001b\u0010\u0018\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001a\u0010\b\u001a\u0004\b\u0019\u0010\u0006\u00a8\u0006$"}, d2 = {"Lme/wcy/music/main/AboutActivity$AboutFragment;", "Landroidx/preference/PreferenceFragmentCompat;", "()V", "api", "Landroidx/preference/Preference;", "getApi", "()Landroidx/preference/Preference;", "api$delegate", "Lkotlin/Lazy;", "mBlog", "getMBlog", "mBlog$delegate", "mGithub", "getMGithub", "mGithub$delegate", "mShare", "getMShare", "mShare$delegate", "mStar", "getMStar", "mStar$delegate", "mVersion", "getMVersion", "mVersion$delegate", "mWeibo", "getMWeibo", "mWeibo$delegate", "onCreatePreferences", "", "savedInstanceState", "Landroid/os/Bundle;", "rootKey", "", "openUrl", "url", "share", "app_debug"})
    public static final class AboutFragment extends androidx.preference.PreferenceFragmentCompat {
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy mVersion$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy mShare$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy mStar$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy mWeibo$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy mBlog$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy mGithub$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.Lazy api$delegate = null;
        
        public AboutFragment() {
            super();
        }
        
        private final androidx.preference.Preference getMVersion() {
            return null;
        }
        
        private final androidx.preference.Preference getMShare() {
            return null;
        }
        
        private final androidx.preference.Preference getMStar() {
            return null;
        }
        
        private final androidx.preference.Preference getMWeibo() {
            return null;
        }
        
        private final androidx.preference.Preference getMBlog() {
            return null;
        }
        
        private final androidx.preference.Preference getMGithub() {
            return null;
        }
        
        private final androidx.preference.Preference getApi() {
            return null;
        }
        
        @java.lang.Override()
        public void onCreatePreferences(@org.jetbrains.annotations.Nullable()
        android.os.Bundle savedInstanceState, @org.jetbrains.annotations.Nullable()
        java.lang.String rootKey) {
        }
        
        private final void share() {
        }
        
        private final void openUrl(java.lang.String url) {
        }
    }
}