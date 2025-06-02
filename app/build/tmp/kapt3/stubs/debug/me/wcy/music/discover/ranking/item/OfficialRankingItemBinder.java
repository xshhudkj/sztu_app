package me.wcy.music.discover.ranking.item;

import android.view.LayoutInflater;
import com.blankj.utilcode.util.SizeUtils;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.databinding.ItemOfficialRankingBinding;
import me.wcy.music.databinding.ItemOfficialRankingSongBinding;
import me.wcy.radapter3.RItemBinder;
import kotlin.reflect.KClass;

/**
 * Created by wangchenyan.top on 2023/10/24.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u000fB\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\f\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0016J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lme/wcy/music/discover/ranking/item/OfficialRankingItemBinder;", "Lme/wcy/radapter3/RItemBinder;", "Lme/wcy/music/databinding/ItemOfficialRankingBinding;", "Lme/wcy/music/common/bean/PlaylistData;", "listener", "Lme/wcy/music/discover/ranking/item/OfficialRankingItemBinder$OnItemClickListener;", "(Lme/wcy/music/discover/ranking/item/OfficialRankingItemBinder$OnItemClickListener;)V", "getViewBindingClazz", "Lkotlin/reflect/KClass;", "onBind", "", "viewBinding", "item", "position", "", "OnItemClickListener", "app_debug"})
public final class OfficialRankingItemBinder extends me.wcy.radapter3.RItemBinder<me.wcy.music.databinding.ItemOfficialRankingBinding, me.wcy.music.common.bean.PlaylistData> {
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.discover.ranking.item.OfficialRankingItemBinder.OnItemClickListener listener = null;
    
    public OfficialRankingItemBinder(@org.jetbrains.annotations.NotNull()
    me.wcy.music.discover.ranking.item.OfficialRankingItemBinder.OnItemClickListener listener) {
        super();
    }
    
    @java.lang.Override()
    public void onBind(@org.jetbrains.annotations.NotNull()
    me.wcy.music.databinding.ItemOfficialRankingBinding viewBinding, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.PlaylistData item, int position) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlin.reflect.KClass<?> getViewBindingClazz() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&\u00a8\u0006\t"}, d2 = {"Lme/wcy/music/discover/ranking/item/OfficialRankingItemBinder$OnItemClickListener;", "", "onItemClick", "", "item", "Lme/wcy/music/common/bean/PlaylistData;", "position", "", "onPlayClick", "app_debug"})
    public static abstract interface OnItemClickListener {
        
        public abstract void onItemClick(@org.jetbrains.annotations.NotNull()
        me.wcy.music.common.bean.PlaylistData item, int position);
        
        public abstract void onPlayClick(@org.jetbrains.annotations.NotNull()
        me.wcy.music.common.bean.PlaylistData item, int position);
    }
}