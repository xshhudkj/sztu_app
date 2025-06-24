package me.ckn.music.search.bean

import com.google.gson.annotations.SerializedName
import me.ckn.music.common.bean.ArtistData

/**
 * 歌手详情数据
 *
 * Original: Created by wangchenyan.top on 2024/12/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：歌手详情数据
 * File Description: Artist detail data
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
data class ArtistDetailData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("artist")
    val artist: ArtistData? = null,
    @SerializedName("more")
    val more: Boolean = false
)

/**
 * 歌手歌曲数据
 */
data class ArtistSongsData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("artist")
    val artist: ArtistData? = null,
    @SerializedName("hotSongs")
    val songs: List<me.ckn.music.common.bean.SongData> = emptyList(),
    @SerializedName("more")
    val more: Boolean = false
)

/**
 * 专辑详情数据
 */
data class AlbumDetailData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("album")
    val album: me.ckn.music.common.bean.AlbumData? = null,
    @SerializedName("songs")
    val songs: List<me.ckn.music.common.bean.SongData> = emptyList()
)
