package me.wcy.music.main;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import me.wcy.music.R;
import me.wcy.music.discover.home.DiscoverFragment;
import me.wcy.music.mine.home.MineFragment;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00132\u00020\u0001:\u0003\u0013\u0014\u0015B/\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0002\u0010\nJ\u0006\u0010\u0012\u001a\u00020\u0005R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u0082\u0001\u0002\u0016\u0017\u00a8\u0006\u0018"}, d2 = {"Lme/wcy/music/main/NaviTab;", "", "id", "", "icon", "", "name", "newFragment", "Lkotlin/Function0;", "Landroidx/fragment/app/Fragment;", "(Ljava/lang/String;ILjava/lang/String;Lkotlin/jvm/functions/Function0;)V", "getIcon", "()I", "getId", "()Ljava/lang/String;", "getName", "getNewFragment", "()Lkotlin/jvm/functions/Function0;", "getPosition", "Companion", "Discover", "Mine", "Lme/wcy/music/main/NaviTab$Discover;", "Lme/wcy/music/main/NaviTab$Mine;", "app_debug"})
public abstract class NaviTab {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    private final int icon = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<androidx.fragment.app.Fragment> newFragment = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<me.wcy.music.main.NaviTab> ALL = null;
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.main.NaviTab.Companion Companion = null;
    
    private NaviTab(java.lang.String id, @androidx.annotation.DrawableRes()
    int icon, java.lang.String name, kotlin.jvm.functions.Function0<? extends androidx.fragment.app.Fragment> newFragment) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    public final int getIcon() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.jvm.functions.Function0<androidx.fragment.app.Fragment> getNewFragment() {
        return null;
    }
    
    public final int getPosition() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00020\rR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u000e"}, d2 = {"Lme/wcy/music/main/NaviTab$Companion;", "", "()V", "ALL", "", "Lme/wcy/music/main/NaviTab;", "getALL", "()Ljava/util/List;", "findByName", "name", "", "findByPosition", "position", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<me.wcy.music.main.NaviTab> getALL() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final me.wcy.music.main.NaviTab findByPosition(int position) {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final me.wcy.music.main.NaviTab findByName(@org.jetbrains.annotations.NotNull()
        java.lang.String name) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lme/wcy/music/main/NaviTab$Discover;", "Lme/wcy/music/main/NaviTab;", "()V", "app_debug"})
    public static final class Discover extends me.wcy.music.main.NaviTab {
        @org.jetbrains.annotations.NotNull()
        public static final me.wcy.music.main.NaviTab.Discover INSTANCE = null;
        
        private Discover() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lme/wcy/music/main/NaviTab$Mine;", "Lme/wcy/music/main/NaviTab;", "()V", "app_debug"})
    public static final class Mine extends me.wcy.music.main.NaviTab {
        @org.jetbrains.annotations.NotNull()
        public static final me.wcy.music.main.NaviTab.Mine INSTANCE = null;
        
        private Mine() {
        }
    }
}