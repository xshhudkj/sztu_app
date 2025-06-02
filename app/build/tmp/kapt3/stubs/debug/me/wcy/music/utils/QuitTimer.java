package me.wcy.music.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.format.DateUtils;

/**
 * Created by hzwangchenyan on 2017/8/8.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0013B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000eJ\u0006\u0010\u0012\u001a\u00020\u0010R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lme/wcy/music/utils/QuitTimer;", "", "listener", "Lme/wcy/music/utils/QuitTimer$OnTimerListener;", "(Lme/wcy/music/utils/QuitTimer$OnTimerListener;)V", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "Lkotlin/Lazy;", "mQuitRunnable", "Ljava/lang/Runnable;", "timerRemain", "", "start", "", "milli", "stop", "OnTimerListener", "app_debug"})
public final class QuitTimer {
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.utils.QuitTimer.OnTimerListener listener = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy handler$delegate = null;
    private long timerRemain = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Runnable mQuitRunnable = null;
    
    public QuitTimer(@org.jetbrains.annotations.NotNull()
    me.wcy.music.utils.QuitTimer.OnTimerListener listener) {
        super();
    }
    
    private final android.os.Handler getHandler() {
        return null;
    }
    
    public final void start(long milli) {
    }
    
    public final void stop() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&\u00a8\u0006\u0007"}, d2 = {"Lme/wcy/music/utils/QuitTimer$OnTimerListener;", "", "onTick", "", "remain", "", "onTimeEnd", "app_debug"})
    public static abstract interface OnTimerListener {
        
        /**
         * 更新定时停止播放时间
         */
        public abstract void onTick(long remain);
        
        public abstract void onTimeEnd();
    }
}