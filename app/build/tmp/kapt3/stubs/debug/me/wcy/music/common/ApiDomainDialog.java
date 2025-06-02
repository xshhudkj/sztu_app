package me.wcy.music.common;

import android.content.Context;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.RegexUtils;
import me.wcy.music.R;
import me.wcy.music.account.AccountPreference;
import me.wcy.music.databinding.DialogApiDomainBinding;
import me.wcy.music.net.NetCache;
import me.wcy.music.storage.preference.ConfigPreferences;
import top.wangchenyan.common.CommonApp;
import top.wangchenyan.common.utils.LaunchUtils;
import top.wangchenyan.common.widget.dialog.CenterDialog;
import top.wangchenyan.common.widget.dialog.CenterDialogBuilder;

/**
 * Created by wangchenyan.top on 2023/9/18.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lme/wcy/music/common/ApiDomainDialog;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "show", "", "Companion", "app_debug"})
public final class ApiDomainDialog {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.common.ApiDomainDialog.Companion Companion = null;
    
    public ApiDomainDialog(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void show() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lme/wcy/music/common/ApiDomainDialog$Companion;", "", "()V", "checkApiDomain", "", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final boolean checkApiDomain(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return false;
        }
    }
}