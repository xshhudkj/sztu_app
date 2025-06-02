package me.wcy.music.mine.local;

import me.wcy.music.common.OnItemClickListener2;
import me.wcy.music.databinding.ItemLocalSongBinding;
import me.wcy.music.storage.db.entity.SongEntity;
import me.wcy.music.utils.MusicUtils;
import me.wcy.radapter3.RItemBinder;

/**
 * Created by wangchenyan.top on 2023/8/30.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lme/wcy/music/mine/local/LocalSongItemBinder;", "Lme/wcy/radapter3/RItemBinder;", "Lme/wcy/music/databinding/ItemLocalSongBinding;", "Lme/wcy/music/storage/db/entity/SongEntity;", "listener", "Lme/wcy/music/common/OnItemClickListener2;", "(Lme/wcy/music/common/OnItemClickListener2;)V", "onBind", "", "viewBinding", "item", "position", "", "app_debug"})
public final class LocalSongItemBinder extends me.wcy.radapter3.RItemBinder<me.wcy.music.databinding.ItemLocalSongBinding, me.wcy.music.storage.db.entity.SongEntity> {
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.common.OnItemClickListener2<me.wcy.music.storage.db.entity.SongEntity> listener = null;
    
    public LocalSongItemBinder(@org.jetbrains.annotations.NotNull()
    me.wcy.music.common.OnItemClickListener2<me.wcy.music.storage.db.entity.SongEntity> listener) {
        super();
    }
    
    @java.lang.Override()
    public void onBind(@org.jetbrains.annotations.NotNull()
    me.wcy.music.databinding.ItemLocalSongBinding viewBinding, @org.jetbrains.annotations.NotNull()
    me.wcy.music.storage.db.entity.SongEntity item, int position) {
    }
}