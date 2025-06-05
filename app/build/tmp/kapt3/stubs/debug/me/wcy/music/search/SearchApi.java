package me.wcy.music.search;

import top.wangchenyan.common.net.NetResult;
import top.wangchenyan.common.net.gson.GsonConverterFactory;
import top.wangchenyan.common.utils.GsonUtils;
import me.wcy.music.net.HttpClient;
import me.wcy.music.search.bean.AlbumDetailData;
import me.wcy.music.search.bean.ArtistDetailData;
import me.wcy.music.search.bean.ArtistSongsData;
import me.wcy.music.search.bean.HotSearchDetailListData;
import me.wcy.music.search.bean.HotSearchListData;
import me.wcy.music.search.bean.SearchResultData;
import me.wcy.music.search.bean.SearchSuggestData;
import me.wcy.music.storage.preference.ConfigPreferences;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wangchenyan.top on 2023/9/20.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u0000 \"2\u00020\u0001:\u0001\"J(\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0018\u0010\u000e\u001a\u00020\u000f2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\u0010\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u000e\u0010\u0013\u001a\u00020\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0012J$\u0010\u0015\u001a\u00020\u00162\b\b\u0001\u0010\u0017\u001a\u00020\u00182\n\b\u0003\u0010\u0019\u001a\u0004\u0018\u00010\u0018H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ<\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00032\b\b\u0001\u0010\u0019\u001a\u00020\u00072\b\b\u0001\u0010\u0017\u001a\u00020\u00182\b\b\u0001\u0010\u001d\u001a\u00020\u00072\b\b\u0001\u0010\u001e\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u001fJ(\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ(\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\b\u00a8\u0006#"}, d2 = {"Lme/wcy/music/search/SearchApi;", "", "followUser", "Ltop/wangchenyan/common/net/NetResult;", "id", "", "t", "", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAlbumDetail", "Lme/wcy/music/search/bean/AlbumDetailData;", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getArtistDetail", "Lme/wcy/music/search/bean/ArtistDetailData;", "getArtistTopSongs", "Lme/wcy/music/search/bean/ArtistSongsData;", "getHotSearchDetailList", "Lme/wcy/music/search/bean/HotSearchDetailListData;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHotSearchList", "Lme/wcy/music/search/bean/HotSearchListData;", "getSearchSuggest", "Lme/wcy/music/search/bean/SearchSuggestData;", "keywords", "", "type", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "search", "Lme/wcy/music/search/bean/SearchResultData;", "limit", "offset", "(ILjava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "subscribeAlbum", "subscribeArtist", "Companion", "app_debug"})
public abstract interface SearchApi {
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.search.SearchApi.Companion Companion = null;
    
    /**
     * 搜索歌曲
     * @param type 搜索类型；默认为 1 即单曲 , 取值意义 :
     * - 1: 单曲,
     * - 10: 专辑,
     * - 100: 歌手,
     * - 1000: 歌单,
     * - 1002: 用户,
     * - 1004: MV,
     * - 1006: 歌词,
     * - 1009: 电台,
     * - 1014: 视频,
     * - 1018:综合,
     * - 2000:声音(搜索声音返回字段格式会不一样)
     */
    @retrofit2.http.POST(value = "cloudsearch")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object search(@retrofit2.http.Query(value = "type")
    int type, @retrofit2.http.Query(value = "keywords")
    @org.jetbrains.annotations.NotNull()
    java.lang.String keywords, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "offset")
    int offset, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.net.NetResult<me.wcy.music.search.bean.SearchResultData>> $completion);
    
    /**
     * 获取热门搜索列表(简略)
     */
    @retrofit2.http.GET(value = "search/hot")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getHotSearchList(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.search.bean.HotSearchListData> $completion);
    
    /**
     * 获取热门搜索列表(详细)
     */
    @retrofit2.http.GET(value = "search/hot/detail")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getHotSearchDetailList(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.search.bean.HotSearchDetailListData> $completion);
    
    /**
     * 获取搜索建议
     * @param keywords 关键词
     * @param type 如果传 'mobile' 则返回移动端数据
     */
    @retrofit2.http.GET(value = "search/suggest")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSearchSuggest(@retrofit2.http.Query(value = "keywords")
    @org.jetbrains.annotations.NotNull()
    java.lang.String keywords, @retrofit2.http.Query(value = "type")
    @org.jetbrains.annotations.Nullable()
    java.lang.String type, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.search.bean.SearchSuggestData> $completion);
    
    /**
     * 关注/取消关注用户
     * @param id 用户id
     * @param t 1为关注,其他为取消关注
     */
    @retrofit2.http.POST(value = "follow")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object followUser(@retrofit2.http.Query(value = "id")
    long id, @retrofit2.http.Query(value = "t")
    int t, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.net.NetResult<java.lang.Object>> $completion);
    
    /**
     * 获取歌手详情
     * @param id 歌手id
     */
    @retrofit2.http.POST(value = "artist/detail")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getArtistDetail(@retrofit2.http.Query(value = "id")
    long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.net.NetResult<me.wcy.music.search.bean.ArtistDetailData>> $completion);
    
    /**
     * 获取歌手热门歌曲
     * @param id 歌手id
     */
    @retrofit2.http.POST(value = "artists")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getArtistTopSongs(@retrofit2.http.Query(value = "id")
    long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.search.bean.ArtistSongsData> $completion);
    
    /**
     * 获取专辑内容
     * @param id 专辑id
     */
    @retrofit2.http.GET(value = "album")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAlbumDetail(@retrofit2.http.Query(value = "id")
    long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super me.wcy.music.search.bean.AlbumDetailData> $completion);
    
    /**
     * 收藏/取消收藏歌手
     * @param id 歌手id
     * @param t 1为收藏,其他为取消收藏
     */
    @retrofit2.http.POST(value = "artist/sub")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object subscribeArtist(@retrofit2.http.Query(value = "id")
    long id, @retrofit2.http.Query(value = "t")
    int t, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.net.NetResult<java.lang.Object>> $completion);
    
    /**
     * 收藏/取消收藏专辑
     * @param id 专辑id
     * @param t 1为收藏,其他为取消收藏
     */
    @retrofit2.http.POST(value = "album/sub")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object subscribeAlbum(@retrofit2.http.Query(value = "id")
    long id, @retrofit2.http.Query(value = "t")
    int t, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super top.wangchenyan.common.net.NetResult<java.lang.Object>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\u0004R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\n"}, d2 = {"Lme/wcy/music/search/SearchApi$Companion;", "", "()V", "api", "Lme/wcy/music/search/SearchApi;", "getApi", "()Lme/wcy/music/search/SearchApi;", "api$delegate", "Lkotlin/Lazy;", "get", "app_debug"})
    public static final class Companion {
        @org.jetbrains.annotations.NotNull()
        private static final kotlin.Lazy<?> api$delegate = null;
        
        private Companion() {
            super();
        }
        
        private final me.wcy.music.search.SearchApi getApi() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final me.wcy.music.search.SearchApi get() {
            return null;
        }
    }
    
    /**
     * Created by wangchenyan.top on 2023/9/20.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}