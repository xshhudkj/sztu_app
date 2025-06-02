package me.wcy.music.common.dialog.songmenu.items;

import android.view.View;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.common.bean.SongData;
import me.wcy.music.common.dialog.songmenu.MenuItem;
import me.wcy.music.mine.MineApi;
import top.wangchenyan.common.ui.activity.BaseActivity;

/**
 * Created by wangchenyan.top on 2023/10/11.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B8\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012!\u0010\u0006\u001a\u001d\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0004\u0012\u0004\u0012\u00020\n0\u0007\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0014\u0010\t\u001a\u00020\f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR)\u0010\u0006\u001a\u001d\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0004\u0012\u0004\u0012\u00020\n0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lme/wcy/music/common/dialog/songmenu/items/DeletePlaylistSongMenuItem;", "Lme/wcy/music/common/dialog/songmenu/MenuItem;", "playlistData", "Lme/wcy/music/common/bean/PlaylistData;", "songData", "Lme/wcy/music/common/bean/SongData;", "onDelete", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "", "(Lme/wcy/music/common/bean/PlaylistData;Lme/wcy/music/common/bean/SongData;Lkotlin/jvm/functions/Function1;)V", "", "getName", "()Ljava/lang/String;", "onClick", "view", "Landroid/view/View;", "app_debug"})
public final class DeletePlaylistSongMenuItem implements me.wcy.music.common.dialog.songmenu.MenuItem {
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.common.bean.PlaylistData playlistData = null;
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.common.bean.SongData songData = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<me.wcy.music.common.bean.SongData, kotlin.Unit> onDelete = null;
    
    public DeletePlaylistSongMenuItem(@org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.PlaylistData playlistData, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.SongData songData, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super me.wcy.music.common.bean.SongData, kotlin.Unit> onDelete) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String getName() {
        return null;
    }
    
    @java.lang.Override()
    public void onClick(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
}