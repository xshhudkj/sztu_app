package me.wcy.music.common;

import com.kingja.loadsir.callback.Callback;
import top.wangchenyan.common.ui.fragment.BaseFragment;
import me.wcy.music.widget.loadsir.SoundWaveLoadingCallback;

/**
 * Created by wangchenyan.top on 2023/9/6.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0014J\b\u0010\u0005\u001a\u00020\u0006H\u0014\u00a8\u0006\u0007"}, d2 = {"Lme/wcy/music/common/BaseMusicFragment;", "Ltop/wangchenyan/common/ui/fragment/BaseFragment;", "()V", "getLoadingCallback", "Lcom/kingja/loadsir/callback/Callback;", "showLoadSirLoading", "", "app_debug"})
public abstract class BaseMusicFragment extends top.wangchenyan.common.ui.fragment.BaseFragment {
    
    public BaseMusicFragment() {
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