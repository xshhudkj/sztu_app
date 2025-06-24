package me.ckn.music.search.bean

import com.google.gson.annotations.SerializedName
import me.ckn.music.common.bean.AlbumData
import me.ckn.music.common.bean.ArtistData
import me.ckn.music.common.bean.PlaylistData
import me.ckn.music.common.bean.SongData

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：搜索结果数据
 * File Description: Search result data
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
data class SearchResultData(
    @SerializedName("songs")
    val songs: List<SongData> = emptyList(),
    @SerializedName("songCount")
    val songCount: Int = 0,
    @SerializedName("playlists")
    val playlists: List<PlaylistData> = emptyList(),
    @SerializedName("playlistCount")
    val playlistCount: Int = 0,
    @SerializedName("artists")
    val artists: List<ArtistData> = emptyList(),
    @SerializedName("artistCount")
    val artistCount: Int = 0,
    @SerializedName("albums")
    val albums: List<AlbumData> = emptyList(),
    @SerializedName("albumCount")
    val albumCount: Int = 0,
    @SerializedName("userprofiles")
    val userprofiles: List<UserData> = emptyList(),
    @SerializedName("userprofileCount")
    val userprofileCount: Int = 0,
)
