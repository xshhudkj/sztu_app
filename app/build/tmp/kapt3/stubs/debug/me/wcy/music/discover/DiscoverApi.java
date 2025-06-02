package me.wcy.music.discover;

import kotlinx.coroutines.Dispatchers;
import me.wcy.music.common.bean.LrcDataWrap;
import me.wcy.music.common.bean.SongData;
import me.wcy.music.common.bean.SongUrlData;
import me.wcy.music.discover.banner.BannerListData;
import me.wcy.music.discover.playlist.detail.bean.PlaylistDetailData;
import me.wcy.music.discover.playlist.detail.bean.SongListData;
import me.wcy.music.discover.playlist.square.bean.PlaylistListData;
import me.wcy.music.discover.playlist.square.bean.PlaylistTagListData;
import me.wcy.music.discover.recommend.song.bean.RecommendSongListData;
import me.wcy.music.net.HttpClient;
import me.wcy.music.storage.preference.ConfigPreferences;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import top.wangchenyan.common.net.NetResult;
import top.wangchenyan.common.net.gson.GsonConverterFactory;
import top.wangchenyan.common.utils.GsonUtils;

/**
 * Created by wangchenyan.top on 2023/9/6.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000 $2\u00020\u0001:\u0001$J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u00020\u000b2\b\b\u0001\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ,\u0010\f\u001a\u00020\r2\b\b\u0001\u0010\u000e\u001a\u00020\u000f2\b\b\u0001\u0010\u0010\u001a\u00020\u00112\b\b\u0001\u0010\u0012\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0013J<\u0010\u0014\u001a\u00020\u00152\b\b\u0001\u0010\u0007\u001a\u00020\b2\n\b\u0003\u0010\u0010\u001a\u0004\u0018\u00010\u00112\n\b\u0003\u0010\u0012\u001a\u0004\u0018\u00010\u00112\n\b\u0003\u0010\u0016\u001a\u0004\u0018\u00010\bH\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u000e\u0010\u0018\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u001a\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u001b\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u00a7@\u00a2\u0006\u0002\u0010\u0004J.\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0 0\u001d2\b\b\u0001\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\"\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010#\u00a8\u0006%"}, d2 = {"Lme/wcy/music/discover/DiscoverApi;", "", "getBannerList", "Lme/wcy/music/discover/banner/BannerListData;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLrc", "Lme/wcy/music/common/bean/LrcDataWrap;", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlaylistDetail", "Lme/wcy/music/discover/playlist/detail/bean/PlaylistDetailData;", "getPlaylistList", "Lme/wcy/music/discover/playlist/square/bean/PlaylistListData;", "cat", "", "limit", "", "offset", "(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlaylistSongList", "Lme/wcy/music/discover/playlist/detail/bean/SongListData;", "timestamp", "(JLjava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Long;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlaylistTagList", "Lme/wcy/music/discover/playlist/square/bean/PlaylistTagListData;", "getRankingList", "getRecommendPlaylists", "getRecommendSongs", "Ltop/wangchenyan/common/net/NetResult;", "Lme/wcy/music/discover/recommend/song/bean/RecommendSongListData;", "getSongUrl", "", "Lme/wcy/music/common/bean/SongUrlData;", "level", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public abstract interface DiscoverApi {
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.discover.DiscoverApi.Companion Companion = null;
    
    @retrofit2.http.POST(value = "recommend/songs")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRecommendSongs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.net.NetResult<me.wcy.music.discover.recommend.song.bean.RecommendSongListData>> $completion);
    
    @retrofit2.http.POST(value = "recommend/resource")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRecommendPlaylists(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.discover.playlist.square.bean.PlaylistListData> $completion);
    
    @retrofit2.http.POST(value = "song/url/v1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSongUrl(@retrofit2.http.Query(value = "id")
    long id, @retrofit2.http.Query(value = "level")
    @org.jetbrains.annotations.NotNull()
    java.lang.String level, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.net.NetResult<java.util.List<me.wcy.music.common.bean.SongUrlData>>> $completion);
    
    @retrofit2.http.POST(value = "lyric")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLrc(@retrofit2.http.Query(value = "id")
    long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.common.bean.LrcDataWrap> $completion);
    
    @retrofit2.http.POST(value = "playlist/detail")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPlaylistDetail(@retrofit2.http.Query(value = "id")
    long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.discover.playlist.detail.bean.PlaylistDetailData> $completion);
    
    @retrofit2.http.POST(value = "playlist/track/all")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPlaylistSongList(@retrofit2.http.Query(value = "id")
    long id, @retrofit2.http.Query(value = "limit")
    @org.jetbrains.annotations.Nullable()
    java.lang.Integer limit, @retrofit2.http.Query(value = "offset")
    @org.jetbrains.annotations.Nullable()
    java.lang.Integer offset, @retrofit2.http.Query(value = "timestamp")
    @org.jetbrains.annotations.Nullable()
    java.lang.Long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.discover.playlist.detail.bean.SongListData> $completion);
    
    @retrofit2.http.POST(value = "playlist/hot")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPlaylistTagList(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.discover.playlist.square.bean.PlaylistTagListData> $completion);
    
    @retrofit2.http.POST(value = "top/playlist")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPlaylistList(@retrofit2.http.Query(value = "cat")
    @org.jetbrains.annotations.NotNull()
    java.lang.String cat, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "offset")
    int offset, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.discover.playlist.square.bean.PlaylistListData> $completion);
    
    @retrofit2.http.POST(value = "toplist")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRankingList(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.discover.playlist.square.bean.PlaylistListData> $completion);
    
    @retrofit2.http.GET(value = "banner?type=2")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getBannerList(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.discover.banner.BannerListData> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u000b\u001a\u00020\u0006J\"\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0011J6\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0013\u001a\u00020\u00042\b\b\u0002\u0010\u0014\u001a\u00020\u00042\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0015R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u0016"}, d2 = {"Lme/wcy/music/discover/DiscoverApi$Companion;", "", "()V", "SONG_LIST_LIMIT", "", "api", "Lme/wcy/music/discover/DiscoverApi;", "getApi", "()Lme/wcy/music/discover/DiscoverApi;", "api$delegate", "Lkotlin/Lazy;", "get", "getFullPlaylistSongList", "Lme/wcy/music/discover/playlist/detail/bean/SongListData;", "id", "", "timestamp", "(JLjava/lang/Long;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlaylistSongListBatch", "limit", "offset", "(JIILjava/lang/Long;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
    public static final class Companion {
        private static final int SONG_LIST_LIMIT = 800;
        @org.jetbrains.annotations.NotNull()
        private static final kotlin.Lazy<?> api$delegate = null;
        
        private Companion() {
            super();
        }
        
        private final me.wcy.music.discover.DiscoverApi getApi() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final me.wcy.music.discover.DiscoverApi get() {
            return null;
        }
        
        /**
         * 分批获取歌单歌曲列表
         * @param id 歌单ID
         * @param limit 每批获取数量
         * @param offset 偏移量
         * @param timestamp 时间戳
         */
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Object getPlaylistSongListBatch(long id, int limit, int offset, @org.jetbrains.annotations.Nullable()
        java.lang.Long timestamp, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super me.wcy.music.discover.playlist.detail.bean.SongListData> $completion) {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Object getFullPlaylistSongList(long id, @org.jetbrains.annotations.Nullable()
        java.lang.Long timestamp, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super me.wcy.music.discover.playlist.detail.bean.SongListData> $completion) {
            return null;
        }
    }
    
    /**
     * Created by wangchenyan.top on 2023/9/6.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}