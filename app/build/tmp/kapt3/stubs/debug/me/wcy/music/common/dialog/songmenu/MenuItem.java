package me.wcy.music.common.dialog.songmenu;

import android.view.View;

/**
 * Created by wangchenyan.top on 2023/10/11.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\n"}, d2 = {"Lme/wcy/music/common/dialog/songmenu/MenuItem;", "", "name", "", "getName", "()Ljava/lang/String;", "onClick", "", "view", "Landroid/view/View;", "app_debug"})
public abstract interface MenuItem {
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.lang.String getName();
    
    public abstract void onClick(@org.jetbrains.annotations.NotNull()
    android.view.View view);
}