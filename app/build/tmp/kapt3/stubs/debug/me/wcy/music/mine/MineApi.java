package me.wcy.music.mine;

import me.wcy.music.discover.playlist.square.bean.PlaylistListData;
import me.wcy.music.mine.collect.song.bean.CollectSongResult;
import me.wcy.music.net.HttpClient;
import me.wcy.music.service.likesong.bean.LikeSongListData;
import me.wcy.music.storage.preference.ConfigPreferences;
import retrofit2.Retrofit;
import retrofit2.http.POST;
import retrofit2.http.Query;
import top.wangchenyan.common.net.NetResult;
import top.wangchenyan.common.net.gson.GsonConverterFactory;
import top.wangchenyan.common.utils.GsonUtils;
import top.wangchenyan.common.utils.ServerTime;

/**
 * Created by wangchenyan.top on 2023/9/26.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cJ2\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u00072\b\b\u0003\u0010\b\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\tJ6\u0010\n\u001a\u00020\u000b2\b\b\u0001\u0010\f\u001a\u00020\u00052\b\b\u0001\u0010\r\u001a\u00020\u000e2\b\b\u0003\u0010\u000f\u001a\u00020\u000e2\b\b\u0003\u0010\b\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\"\u0010\u0011\u001a\u00020\u00122\b\b\u0001\u0010\u0013\u001a\u00020\u00052\b\b\u0003\u0010\b\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0014J,\u0010\u0015\u001a\u00020\u00162\b\b\u0001\u0010\u0013\u001a\u00020\u00052\b\b\u0003\u0010\u0017\u001a\u00020\u00072\b\b\u0003\u0010\b\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\tJ2\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0003\u0010\u0019\u001a\u00020\u001a2\b\b\u0003\u0010\b\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001b\u00a8\u0006\u001d"}, d2 = {"Lme/wcy/music/mine/MineApi;", "", "collectPlaylist", "Ltop/wangchenyan/common/net/NetResult;", "id", "", "t", "", "timestamp", "(JIJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectSong", "Lme/wcy/music/mine/collect/song/bean/CollectSongResult;", "pid", "tracks", "", "op", "(JLjava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMyLikeSongList", "Lme/wcy/music/service/likesong/bean/LikeSongListData;", "uid", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserPlaylist", "Lme/wcy/music/discover/playlist/square/bean/PlaylistListData;", "limit", "likeSong", "like", "", "(JZJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public abstract interface MineApi {
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.mine.MineApi.Companion Companion = null;
    
    @retrofit2.http.POST(value = "user/playlist")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUserPlaylist(@retrofit2.http.Query(value = "uid")
    long uid, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "timestamp")
    long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.discover.playlist.square.bean.PlaylistListData> $completion);
    
    /**
     * 收藏/取消收藏歌单
     * @param id 歌单 id
     * @param t 类型,1:收藏,2:取消收藏
     */
    @retrofit2.http.POST(value = "playlist/subscribe")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object collectPlaylist(@retrofit2.http.Query(value = "id")
    long id, @retrofit2.http.Query(value = "t")
    int t, @retrofit2.http.Query(value = "timestamp")
    long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.net.NetResult<java.lang.Object>> $completion);
    
    /**
     * 对歌单添加歌曲
     * @param op 从歌单增加单曲为 add, 删除为 del
     * @param pid 歌单 id
     * @param tracks 歌曲 id,可多个,用逗号隔开
     */
    @retrofit2.http.POST(value = "playlist/tracks")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object collectSong(@retrofit2.http.Query(value = "pid")
    long pid, @retrofit2.http.Query(value = "tracks")
    @org.jetbrains.annotations.NotNull()
    java.lang.String tracks, @retrofit2.http.Query(value = "op")
    @org.jetbrains.annotations.NotNull()
    java.lang.String op, @retrofit2.http.Query(value = "timestamp")
    long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.mine.collect.song.bean.CollectSongResult> $completion);
    
    /**
     * 喜欢音乐
     * @param id 歌曲 id
     * @param like 默认为 true 即喜欢 , 若传 false, 则取消喜欢
     */
    @retrofit2.http.POST(value = "like")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object likeSong(@retrofit2.http.Query(value = "id")
    long id, @retrofit2.http.Query(value = "like")
    boolean like, @retrofit2.http.Query(value = "timestamp")
    long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.net.NetResult<java.lang.Object>> $completion);
    
    /**
     * 喜欢音乐列表
     */
    @retrofit2.http.POST(value = "likelist")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMyLikeSongList(@retrofit2.http.Query(value = "uid")
    long uid, @retrofit2.http.Query(value = "timestamp")
    long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.service.likesong.bean.LikeSongListData> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\u0004R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\n"}, d2 = {"Lme/wcy/music/mine/MineApi$Companion;", "", "()V", "api", "Lme/wcy/music/mine/MineApi;", "getApi", "()Lme/wcy/music/mine/MineApi;", "api$delegate", "Lkotlin/Lazy;", "get", "app_debug"})
    public static final class Companion {
        @org.jetbrains.annotations.NotNull()
        private static final kotlin.Lazy<?> api$delegate = null;
        
        private Companion() {
            super();
        }
        
        private final me.wcy.music.mine.MineApi getApi() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final me.wcy.music.mine.MineApi get() {
            return null;
        }
    }
    
    /**
     * Created by wangchenyan.top on 2023/9/26.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}