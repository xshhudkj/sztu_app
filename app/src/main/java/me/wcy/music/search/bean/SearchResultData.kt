package me.wcy.music.search.bean

import com.google.gson.annotations.SerializedName
import me.wcy.music.common.bean.AlbumData
import me.wcy.music.common.bean.ArtistData
import me.wcy.music.common.bean.PlaylistData
import me.wcy.music.common.bean.SongData

/**
 * Created by wangchenyan.top on 2023/9/20.
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
