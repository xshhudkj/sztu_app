package me.wcy.music.common;

import com.kingja.loadsir.callback.Callback;
import top.wangchenyan.common.ui.fragment.BaseRefreshFragment;
import me.wcy.music.widget.loadsir.SoundWaveLoadingCallback;

/**
 * Created by wangchenyan.top on 2023/9/15.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0014J\b\u0010\u0006\u001a\u00020\u0007H\u0014\u00a8\u0006\b"}, d2 = {"Lme/wcy/music/common/BaseMusicRefreshFragment;", "T", "Ltop/wangchenyan/common/ui/fragment/BaseRefreshFragment;", "()V", "getLoadingCallback", "Lcom/kingja/loadsir/callback/Callback;", "showLoadSirLoading", "", "app_debug"})
public abstract class BaseMusicRefreshFragment<T extends java.lang.Object> extends top.wangchenyan.common.ui.fragment.BaseRefreshFragment<T> {
    
    public BaseMusicRefreshFragment() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected com.kingja.loadsir.callback.Callback getLoadingCallback() {
        return null;
    }
    
    @java.lang.Override()
    protected void showLoadSirLoading() {
    }
}