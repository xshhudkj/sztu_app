package me.wcy.music.search;

import top.wangchenyan.common.net.NetResult;
import top.wangchenyan.common.net.gson.GsonConverterFactory;
import top.wangchenyan.common.utils.GsonUtils;
import me.wcy.music.net.HttpClient;
import me.wcy.music.search.bean.SearchResultData;
import me.wcy.music.storage.preference.ConfigPreferences;
import retrofit2.Retrofit;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wangchenyan.top on 2023/9/20.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u0000 \f2\u00020\u0001:\u0001\fJ<\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\u00062\b\b\u0001\u0010\n\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\r"}, d2 = {"Lme/wcy/music/search/SearchApi;", "", "search", "Ltop/wangchenyan/common/net/NetResult;", "Lme/wcy/music/search/bean/SearchResultData;", "type", "", "keywords", "", "limit", "offset", "(ILjava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
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
}