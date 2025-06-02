package me.wcy.music.search.song;

import me.wcy.music.common.OnItemClickListener2;
import me.wcy.music.common.bean.SongData;
import me.wcy.music.databinding.ItemSearchSongBinding;
import me.wcy.music.utils.MusicUtils;
import me.wcy.radapter3.RItemBinder;

/**
 * Created by wangchenyan.top on 2023/9/20.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\u0002\u0010\u0006J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lme/wcy/music/search/song/SearchSongItemBinder;", "Lme/wcy/radapter3/RItemBinder;", "Lme/wcy/music/databinding/ItemSearchSongBinding;", "Lme/wcy/music/common/bean/SongData;", "listener", "Lme/wcy/music/common/OnItemClickListener2;", "(Lme/wcy/music/common/OnItemClickListener2;)V", "keywords", "", "getKeywords", "()Ljava/lang/String;", "setKeywords", "(Ljava/lang/String;)V", "onBind", "", "viewBinding", "item", "position", "", "app_debug"})
public final class SearchSongItemBinder extends me.wcy.radapter3.RItemBinder<me.wcy.music.databinding.ItemSearchSongBinding, me.wcy.music.common.bean.SongData> {
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.common.OnItemClickListener2<me.wcy.music.common.bean.SongData> listener = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String keywords = "";
    
    public SearchSongItemBinder(@org.jetbrains.annotations.NotNull()
    me.wcy.music.common.OnItemClickListener2<me.wcy.music.common.bean.SongData> listener) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getKeywords() {
        return null;
    }
    
    public final void setKeywords(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @java.lang.Override()
    public void onBind(@org.jetbrains.annotations.NotNull()
    me.wcy.music.databinding.ItemSearchSongBinding viewBinding, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.SongData item, int position) {
    }
}