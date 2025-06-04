package me.wcy.music.search

import top.wangchenyan.common.net.NetResult
import top.wangchenyan.common.net.gson.GsonConverterFactory
import top.wangchenyan.common.utils.GsonUtils
import me.wcy.music.net.HttpClient
import me.wcy.music.search.bean.AlbumDetailData
import me.wcy.music.search.bean.ArtistDetailData
import me.wcy.music.search.bean.ArtistSongsData
import me.wcy.music.search.bean.HotSearchDetailListData
import me.wcy.music.search.bean.HotSearchListData
import me.wcy.music.search.bean.SearchResultData
import me.wcy.music.search.bean.SearchSuggestData
import me.wcy.music.storage.preference.ConfigPreferences
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by wangchenyan.top on 2023/9/20.
 */
interface SearchApi {

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
    @POST("cloudsearch")
    suspend fun search(
        @Query("type") type: Int,
        @Query("keywords") keywords: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): NetResult<SearchResultData>

    /**
     * 获取热门搜索列表(简略)
     */
    @GET("search/hot")
    suspend fun getHotSearchList(): HotSearchListData

    /**
     * 获取热门搜索列表(详细)
     */
    @GET("search/hot/detail")
    suspend fun getHotSearchDetailList(): HotSearchDetailListData

    /**
     * 获取搜索建议
     * @param keywords 关键词
     * @param type 如果传 'mobile' 则返回移动端数据
     */
    @GET("search/suggest")
    suspend fun getSearchSuggest(
        @Query("keywords") keywords: String,
        @Query("type") type: String? = null,
    ): SearchSuggestData

    /**
     * 关注/取消关注用户
     * @param id 用户id
     * @param t 1为关注,其他为取消关注
     */
    @POST("follow")
    suspend fun followUser(
        @Query("id") id: Long,
        @Query("t") t: Int,
    ): NetResult<Any>

    /**
     * 获取歌手详情
     * @param id 歌手id
     */
    @GET("artist/detail")
    suspend fun getArtistDetail(@Query("id") id: Long): NetResult<ArtistDetailData>

    /**
     * 获取歌手热门50首歌曲
     * @param id 歌手id
     */
    @GET("artist/top/song")
    suspend fun getArtistTopSongs(@Query("id") id: Long): NetResult<ArtistSongsData>

    /**
     * 获取专辑内容
     * @param id 专辑id
     */
    @GET("album")
    suspend fun getAlbumDetail(@Query("id") id: Long): NetResult<AlbumDetailData>

    /**
     * 收藏/取消收藏歌手
     * @param id 歌手id
     * @param t 1为收藏,其他为取消收藏
     */
    @POST("artist/sub")
    suspend fun subscribeArtist(
        @Query("id") id: Long,
        @Query("t") t: Int,
    ): NetResult<Any>

    /**
     * 收藏/取消收藏专辑
     * @param id 专辑id
     * @param t 1为收藏,其他为取消收藏
     */
    @POST("album/sub")
    suspend fun subscribeAlbum(
        @Query("id") id: Long,
        @Query("t") t: Int,
    ): NetResult<Any>

    companion object {
        private val api: SearchApi by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(ConfigPreferences.apiDomain)
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.gson, true))
                .client(HttpClient.okHttpClient)
                .build()
            retrofit.create(SearchApi::class.java)
        }

        fun get(): SearchApi = api
    }
}