package me.wcy.music.common.dialog.songmenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import com.blankj.utilcode.util.SizeUtils;
import top.wangchenyan.common.widget.dialog.BottomDialog;
import top.wangchenyan.common.widget.dialog.BottomDialogBuilder;
import me.wcy.music.common.bean.SongData;
import me.wcy.music.databinding.DialogSongMoreMenuBinding;
import me.wcy.music.databinding.ItemSongMoreMenuBinding;
import me.wcy.music.storage.db.entity.SongEntity;

/**
 * Created by wangchenyan.top on 2023/10/11.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0003J\u0014\u0010\u0014\u001a\u00020\u00002\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u0015J\u0006\u0010\u0016\u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lme/wcy/music/common/dialog/songmenu/SongMoreMenuDialog;", "", "context", "Landroid/content/Context;", "songEntity", "Lme/wcy/music/storage/db/entity/SongEntity;", "(Landroid/content/Context;Lme/wcy/music/storage/db/entity/SongEntity;)V", "songData", "Lme/wcy/music/common/bean/SongData;", "(Landroid/content/Context;Lme/wcy/music/common/bean/SongData;)V", "items", "", "Lme/wcy/music/common/dialog/songmenu/MenuItem;", "bindMenus", "", "dialog", "Ltop/wangchenyan/common/widget/dialog/BottomDialog;", "viewBinding", "Lme/wcy/music/databinding/DialogSongMoreMenuBinding;", "bindSongInfo", "setItems", "", "show", "app_debug"})
public final class SongMoreMenuDialog {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private me.wcy.music.storage.db.entity.SongEntity songEntity;
    @org.jetbrains.annotations.Nullable()
    private me.wcy.music.common.bean.SongData songData;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<me.wcy.music.common.dialog.songmenu.MenuItem> items = null;
    
    public SongMoreMenuDialog(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    me.wcy.music.storage.db.entity.SongEntity songEntity) {
        super();
    }
    
    public SongMoreMenuDialog(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.SongData songData) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final me.wcy.music.common.dialog.songmenu.SongMoreMenuDialog setItems(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends me.wcy.music.common.dialog.songmenu.MenuItem> items) {
        return null;
    }
    
    public final void show() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void bindSongInfo(me.wcy.music.databinding.DialogSongMoreMenuBinding viewBinding) {
    }
    
    private final void bindMenus(top.wangchenyan.common.widget.dialog.BottomDialog dialog, me.wcy.music.databinding.DialogSongMoreMenuBinding viewBinding) {
    }
}