package me.wcy.music.common;

import android.os.Bundle;
import com.kingja.loadsir.callback.Callback;
import me.wcy.music.utils.ScreenAdaptManager;
import me.wcy.music.widget.loadsir.SoundWaveLoadingCallback;
import top.wangchenyan.common.ui.activity.BaseActivity;

/**
 * Created by wangchenyan.top on 2023/9/4.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\b&\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0014J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014J\b\u0010\t\u001a\u00020\u0006H\u0014J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u0006H\u0014\u00a8\u0006\u000f"}, d2 = {"Lme/wcy/music/common/BaseMusicActivity;", "Ltop/wangchenyan/common/ui/activity/BaseActivity;", "()V", "getLoadingCallback", "Lcom/kingja/loadsir/callback/Callback;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "onWindowFocusChanged", "hasFocus", "", "showLoadSirLoading", "Companion", "app_debug"})
public abstract class BaseMusicActivity extends top.wangchenyan.common.ui.activity.BaseActivity {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "BaseMusicActivity";
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.common.BaseMusicActivity.Companion Companion = null;
    
    public BaseMusicActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    public void onWindowFocusChanged(boolean hasFocus) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected com.kingja.loadsir.callback.Callback getLoadingCallback() {
        return null;
    }
    
    @java.lang.Override()
    protected void showLoadSirLoading() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lme/wcy/music/common/BaseMusicActivity$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}