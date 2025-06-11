/**
 * WhisperPlay Music Player
 *
 * 文件描述：定义“发现”模块相关的API接口。
 * File Description: Defines API interfaces related to the "Discover" module.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.discover

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.ckn.music.common.bean.LrcDataWrap
import me.ckn.music.common.bean.SongData
import me.ckn.music.common.bean.SongUrlData
import me.ckn.music.discover.banner.BannerListData
import me.ckn.music.discover.playlist.detail.bean.PlaylistDetailData
import me.ckn.music.discover.playlist.detail.bean.SongListData
import me.ckn.music.discover.playlist.square.bean.PlaylistListData
import me.ckn.music.discover.playlist.square.bean.PlaylistTagListData
import me.ckn.music.discover.recommend.song.bean.RecommendSongListData
import me.ckn.music.net.HttpClient
import me.ckn.music.storage.preference.ConfigPreferences
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import top.wangchenyan.common.net.NetResult
import top.wangchenyan.common.net.gson.GsonConverterFactory
import top.wangchenyan.common.utils.GsonUtils

/**
 * “发现”模块网络接口
 * "Discover" Module API Interface
 *
 * 主要功能：
 * Main Functions:
 * - 获取推荐歌曲、歌单 / Fetch recommended songs and playlists.
 * - 获取歌曲URL、歌词 / Fetch song URLs and lyrics.
 * - 获取歌单详情、排行榜、Banner等 / Fetch playlist details, rankings, banners, etc.
 *
 * @author ckn
 */
interface DiscoverApi {

    /**
     * 获取每日推荐歌曲
     * Get daily recommended songs.
     */
    @POST("recommend/songs")
    suspend fun getRecommendSongs(): NetResult<RecommendSongListData>

    /**
     * 获取推荐歌单
     * Get recommended playlists.
     */
    @POST("recommend/resource")
    suspend fun getRecommendPlaylists(): PlaylistListData

    /**
     * 获取歌曲播放链接
     * Get song playback URL.
     * @param id 歌曲ID / Song ID
     * @param level 音质等级 / Quality level
     */
    @POST("song/url/v1")
    suspend fun getSongUrl(
        @Query("id") id: Long,
        @Query("level") level: String,
    ): NetResult<List<SongUrlData>>

    /**
     * 获取歌词
     * Get lyrics.
     * @param id 歌曲ID / Song ID
     */
    @POST("lyric")
    suspend fun getLrc(
        @Query("id") id: Long,
        @Query("tv") tv: Int = 0,  // 翻译歌词版本，0表示获取翻译歌词
        @Query("lv") lv: Int = 0,  // 歌词版本，0表示获取歌词
        @Query("rv") rv: Int = 0,  // 罗马音版本
        @Query("kv") kv: Int = 0   // 卡拉OK版本
    ): LrcDataWrap

    /**
     * 获取歌单详情
     * Get playlist details.
     * @param id 歌单ID / Playlist ID
     */
    @POST("playlist/detail")
    suspend fun getPlaylistDetail(
        @Query("id") id: Long,
    ): PlaylistDetailData

    /**
     * 获取歌单所有歌曲
     * Get all songs in a playlist.
     * @param id 歌单ID / Playlist ID
     * @param limit 数量限制 / Limit
     * @param offset 偏移量 / Offset
     */
    @POST("playlist/track/all")
    suspend fun getPlaylistSongList(
        @Query("id") id: Long,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("timestamp") timestamp: Long? = null
    ): SongListData

    /**
     * 获取热门歌单标签
     * Get hot playlist tags.
     */
    @POST("playlist/hot")
    suspend fun getPlaylistTagList(): PlaylistTagListData

    /**
     * 根据分类获取歌单
     * Get playlists by category.
     * @param cat 分类名 / Category name
     * @param limit 数量限制 / Limit
     * @param offset 偏移量 / Offset
     */
    @POST("top/playlist")
    suspend fun getPlaylistList(
        @Query("cat") cat: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): PlaylistListData

    /**
     * 获取所有排行榜
     * Get all ranking lists.
     */
    @POST("toplist")
    suspend fun getRankingList(): PlaylistListData

    /**
     * 获取Banner
     * Get banners.
     */
    @GET("banner?type=2")
    suspend fun getBannerList(): BannerListData

    companion object {
        private const val SONG_LIST_LIMIT = 800

        private val api: DiscoverApi by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(ConfigPreferences.apiDomain)
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.gson, true))
                .client(HttpClient.okHttpClient)
                .build()
            retrofit.create(DiscoverApi::class.java)
        }

        fun get(): DiscoverApi = api

        /**
         * 分批获取歌单歌曲列表
         * @param id 歌单ID
         * @param limit 每批获取数量
         * @param offset 偏移量
         * @param timestamp 时间戳
         */
        suspend fun getPlaylistSongListBatch(
            id: Long,
            limit: Int = 3,
            offset: Int = 0,
            timestamp: Long? = null
        ): SongListData {
            return withContext(Dispatchers.IO) {
                val songList = get().getPlaylistSongList(
                    id,
                    limit = limit,
                    offset = offset,
                    timestamp = timestamp
                )
                if (songList.code != 200) {
                    throw Exception("code = ${songList.code}")
                }
                return@withContext songList
            }
        }

        suspend fun getFullPlaylistSongList(id: Long, timestamp: Long? = null): SongListData {
            return withContext(Dispatchers.IO) {
                var offset = 0
                val list = mutableListOf<SongData>()
                while (true) {
                    val songList = get().getPlaylistSongList(
                        id,
                        limit = SONG_LIST_LIMIT,
                        offset = offset,
                        timestamp = timestamp
                    )
                    if (songList.code != 200) {
                        throw Exception("code = ${songList.code}")
                    }
                    if (songList.songs.isEmpty()) {
                        break
                    }
                    list.addAll(songList.songs)
                    offset = list.size
                }
                return@withContext SongListData(200, list)
            }
        }
    }
}