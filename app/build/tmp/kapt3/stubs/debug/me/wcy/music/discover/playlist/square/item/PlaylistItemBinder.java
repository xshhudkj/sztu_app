package me.wcy.music.discover.playlist.square.item;

import com.blankj.utilcode.util.SizeUtils;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.databinding.ItemDiscoverPlaylistBinding;
import me.wcy.music.utils.ConvertUtils;
import me.wcy.radapter3.RItemBinder;

/**
 * Created by wangchenyan.top on 2023/9/25.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0010B\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0005H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lme/wcy/music/discover/playlist/square/item/PlaylistItemBinder;", "Lme/wcy/radapter3/RItemBinder;", "Lme/wcy/music/databinding/ItemDiscoverPlaylistBinding;", "Lme/wcy/music/common/bean/PlaylistData;", "itemWidth", "", "showPlayButton", "", "listener", "Lme/wcy/music/discover/playlist/square/item/PlaylistItemBinder$OnItemClickListener;", "(IZLme/wcy/music/discover/playlist/square/item/PlaylistItemBinder$OnItemClickListener;)V", "onBind", "", "viewBinding", "item", "position", "OnItemClickListener", "app_debug"})
public final class PlaylistItemBinder extends me.wcy.radapter3.RItemBinder<me.wcy.music.databinding.ItemDiscoverPlaylistBinding, me.wcy.music.common.bean.PlaylistData> {
    private final int itemWidth = 0;
    private final boolean showPlayButton = false;
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.discover.playlist.square.item.PlaylistItemBinder.OnItemClickListener listener = null;
    
    public PlaylistItemBinder(int itemWidth, boolean showPlayButton, @org.jetbrains.annotations.NotNull()
    me.wcy.music.discover.playlist.square.item.PlaylistItemBinder.OnItemClickListener listener) {
        super();
    }
    
    @java.lang.Override()
    public void onBind(@org.jetbrains.annotations.NotNull()
    me.wcy.music.databinding.ItemDiscoverPlaylistBinding viewBinding, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.PlaylistData item, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0007"}, d2 = {"Lme/wcy/music/discover/playlist/square/item/PlaylistItemBinder$OnItemClickListener;", "", "onItemClick", "", "item", "Lme/wcy/music/common/bean/PlaylistData;", "onPlayClick", "app_debug"})
    public static abstract interface OnItemClickListener {
        
        public abstract void onItemClick(@org.jetbrains.annotations.NotNull()
        me.wcy.music.common.bean.PlaylistData item);
        
        public abstract void onPlayClick(@org.jetbrains.annotations.NotNull()
        me.wcy.music.common.bean.PlaylistData item);
    }
}