package me.wcy.music.discover.ranking.discover.item;

import android.view.LayoutInflater;
import com.blankj.utilcode.util.SizeUtils;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.databinding.ItemDiscoverRankingBinding;
import me.wcy.music.databinding.ItemDiscoverRankingSongBinding;
import me.wcy.radapter3.RItemBinder;

/**
 * Created by wangchenyan.top on 2023/10/19.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\rB\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lme/wcy/music/discover/ranking/discover/item/DiscoverRankingItemBinder;", "Lme/wcy/radapter3/RItemBinder;", "Lme/wcy/music/databinding/ItemDiscoverRankingBinding;", "Lme/wcy/music/common/bean/PlaylistData;", "listener", "Lme/wcy/music/discover/ranking/discover/item/DiscoverRankingItemBinder$OnItemClickListener;", "(Lme/wcy/music/discover/ranking/discover/item/DiscoverRankingItemBinder$OnItemClickListener;)V", "onBind", "", "viewBinding", "item", "position", "", "OnItemClickListener", "app_debug"})
public final class DiscoverRankingItemBinder extends me.wcy.radapter3.RItemBinder<me.wcy.music.databinding.ItemDiscoverRankingBinding, me.wcy.music.common.bean.PlaylistData> {
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.discover.ranking.discover.item.DiscoverRankingItemBinder.OnItemClickListener listener = null;
    
    public DiscoverRankingItemBinder(@org.jetbrains.annotations.NotNull()
    me.wcy.music.discover.ranking.discover.item.DiscoverRankingItemBinder.OnItemClickListener listener) {
        super();
    }
    
    @java.lang.Override()
    public void onBind(@org.jetbrains.annotations.NotNull()
    me.wcy.music.databinding.ItemDiscoverRankingBinding viewBinding, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.PlaylistData item, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0007H&\u00a8\u0006\n"}, d2 = {"Lme/wcy/music/discover/ranking/discover/item/DiscoverRankingItemBinder$OnItemClickListener;", "", "onItemClick", "", "item", "Lme/wcy/music/common/bean/PlaylistData;", "position", "", "onSongClick", "songPosition", "app_debug"})
    public static abstract interface OnItemClickListener {
        
        public abstract void onItemClick(@org.jetbrains.annotations.NotNull()
        me.wcy.music.common.bean.PlaylistData item, int position);
        
        public abstract void onSongClick(@org.jetbrains.annotations.NotNull()
        me.wcy.music.common.bean.PlaylistData item, int songPosition);
    }
}