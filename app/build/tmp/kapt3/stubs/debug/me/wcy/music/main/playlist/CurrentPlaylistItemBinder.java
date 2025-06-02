package me.wcy.music.main.playlist;

import android.annotation.SuppressLint;
import androidx.media3.common.MediaItem;
import me.wcy.music.common.OnItemClickListener2;
import me.wcy.music.databinding.ItemCurrentPlaylistBinding;
import me.wcy.music.service.PlayerController;
import me.wcy.radapter3.RItemBinder;

/**
 * Created by wangchenyan.top on 2023/9/4.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u001b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u00a2\u0006\u0002\u0010\bJ \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0017R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lme/wcy/music/main/playlist/CurrentPlaylistItemBinder;", "Lme/wcy/radapter3/RItemBinder;", "Lme/wcy/music/databinding/ItemCurrentPlaylistBinding;", "Landroidx/media3/common/MediaItem;", "playerController", "Lme/wcy/music/service/PlayerController;", "listener", "Lme/wcy/music/common/OnItemClickListener2;", "(Lme/wcy/music/service/PlayerController;Lme/wcy/music/common/OnItemClickListener2;)V", "onBind", "", "viewBinding", "item", "position", "", "app_debug"})
public final class CurrentPlaylistItemBinder extends me.wcy.radapter3.RItemBinder<me.wcy.music.databinding.ItemCurrentPlaylistBinding, androidx.media3.common.MediaItem> {
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.service.PlayerController playerController = null;
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.common.OnItemClickListener2<androidx.media3.common.MediaItem> listener = null;
    
    public CurrentPlaylistItemBinder(@org.jetbrains.annotations.NotNull()
    me.wcy.music.service.PlayerController playerController, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.OnItemClickListener2<androidx.media3.common.MediaItem> listener) {
        super();
    }
    
    @java.lang.Override()
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    public void onBind(@org.jetbrains.annotations.NotNull()
    me.wcy.music.databinding.ItemCurrentPlaylistBinding viewBinding, @org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem item, int position) {
    }
}