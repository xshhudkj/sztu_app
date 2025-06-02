package me.wcy.music.search.playlist;

import android.annotation.SuppressLint;
import com.blankj.utilcode.util.SizeUtils;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.databinding.ItemSearchPlaylistBinding;
import me.wcy.music.utils.ConvertUtils;
import me.wcy.music.utils.MusicUtils;
import me.wcy.radapter3.RItemBinder;

/**
 * Created by wangchenyan.top on 2023/9/21.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\b\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J \u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012H\u0017R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lme/wcy/music/search/playlist/SearchPlaylistItemBinder;", "Lme/wcy/radapter3/RItemBinder;", "Lme/wcy/music/databinding/ItemSearchPlaylistBinding;", "Lme/wcy/music/common/bean/PlaylistData;", "onItemClick", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function1;)V", "keywords", "", "getKeywords", "()Ljava/lang/String;", "setKeywords", "(Ljava/lang/String;)V", "onBind", "viewBinding", "item", "position", "", "app_debug"})
public final class SearchPlaylistItemBinder extends me.wcy.radapter3.RItemBinder<me.wcy.music.databinding.ItemSearchPlaylistBinding, me.wcy.music.common.bean.PlaylistData> {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<me.wcy.music.common.bean.PlaylistData, kotlin.Unit> onItemClick = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String keywords = "";
    
    public SearchPlaylistItemBinder(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super me.wcy.music.common.bean.PlaylistData, kotlin.Unit> onItemClick) {
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
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    public void onBind(@org.jetbrains.annotations.NotNull()
    me.wcy.music.databinding.ItemSearchPlaylistBinding viewBinding, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.PlaylistData item, int position) {
    }
}