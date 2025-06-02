package me.wcy.music.discover.ranking.item;

import android.view.Gravity;
import android.widget.FrameLayout;
import com.blankj.utilcode.util.SizeUtils;
import me.wcy.music.common.bean.PlaylistData;
import me.wcy.music.databinding.ItemSelectedRankingBinding;
import me.wcy.radapter3.RItemBinder;
import kotlin.reflect.KClass;

/**
 * Created by wangchenyan.top on 2023/10/24.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0010B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\f\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0005H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lme/wcy/music/discover/ranking/item/SelectedRankingItemBinder;", "Lme/wcy/radapter3/RItemBinder;", "Lme/wcy/music/databinding/ItemSelectedRankingBinding;", "Lme/wcy/music/common/bean/PlaylistData;", "itemWidth", "", "listener", "Lme/wcy/music/discover/ranking/item/SelectedRankingItemBinder$OnItemClickListener;", "(ILme/wcy/music/discover/ranking/item/SelectedRankingItemBinder$OnItemClickListener;)V", "getViewBindingClazz", "Lkotlin/reflect/KClass;", "onBind", "", "viewBinding", "item", "position", "OnItemClickListener", "app_debug"})
public final class SelectedRankingItemBinder extends me.wcy.radapter3.RItemBinder<me.wcy.music.databinding.ItemSelectedRankingBinding, me.wcy.music.common.bean.PlaylistData> {
    private final int itemWidth = 0;
    @org.jetbrains.annotations.NotNull()
    private final me.wcy.music.discover.ranking.item.SelectedRankingItemBinder.OnItemClickListener listener = null;
    
    public SelectedRankingItemBinder(int itemWidth, @org.jetbrains.annotations.NotNull()
    me.wcy.music.discover.ranking.item.SelectedRankingItemBinder.OnItemClickListener listener) {
        super();
    }
    
    @java.lang.Override()
    public void onBind(@org.jetbrains.annotations.NotNull()
    me.wcy.music.databinding.ItemSelectedRankingBinding viewBinding, @org.jetbrains.annotations.NotNull()
    me.wcy.music.common.bean.PlaylistData item, int position) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlin.reflect.KClass<?> getViewBindingClazz() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H&J\u0018\u0010\t\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H&\u00a8\u0006\n"}, d2 = {"Lme/wcy/music/discover/ranking/item/SelectedRankingItemBinder$OnItemClickListener;", "", "getFirstSelectedPosition", "", "onItemClick", "", "item", "Lme/wcy/music/common/bean/PlaylistData;", "position", "onPlayClick", "app_debug"})
    public static abstract interface OnItemClickListener {
        
        public abstract void onItemClick(@org.jetbrains.annotations.NotNull()
        me.wcy.music.common.bean.PlaylistData item, int position);
        
        public abstract void onPlayClick(@org.jetbrains.annotations.NotNull()
        me.wcy.music.common.bean.PlaylistData item, int position);
        
        public abstract int getFirstSelectedPosition();
    }
}