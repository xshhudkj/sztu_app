package me.wcy.music.search.bean

import com.google.gson.annotations.SerializedName

/**
 * 搜索建议数据
 * Created by wangchenyan.top on 2024/12/20.
 */
data class SearchSuggestData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("result")
    val result: SearchSuggestResult? = null,
)

data class SearchSuggestResult(
    @SerializedName("allMatch")
    val allMatch: List<SearchSuggestItem> = emptyList(),
    @SerializedName("songs")
    val songs: List<SearchSuggestSong> = emptyList(),
    @SerializedName("artists")
    val artists: List<SearchSuggestArtist> = emptyList(),
    @SerializedName("albums")
    val albums: List<SearchSuggestAlbum> = emptyList(),
    @SerializedName("playlists")
    val playlists: List<SearchSuggestPlaylist> = emptyList(),
)

data class SearchSuggestItem(
    @SerializedName("keyword")
    val keyword: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("alg")
    val alg: String = "",
)

data class SearchSuggestSong(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("artists")
    val artists: List<SearchSuggestArtist> = emptyList(),
    @SerializedName("album")
    val album: SearchSuggestAlbum? = null,
)

data class SearchSuggestArtist(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("picUrl")
    val picUrl: String = "",
    @SerializedName("alias")
    val alias: List<String> = emptyList(),
)

data class SearchSuggestAlbum(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("artist")
    val artist: SearchSuggestArtist? = null,
    @SerializedName("publishTime")
    val publishTime: Long = 0,
    @SerializedName("size")
    val size: Int = 0,
    @SerializedName("copyrightId")
    val copyrightId: Int = 0,
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("picUrl")
    val picUrl: String = "",
)

data class SearchSuggestPlaylist(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("coverImgUrl")
    val coverImgUrl: String = "",
    @SerializedName("creator")
    val creator: SearchSuggestUser? = null,
    @SerializedName("subscribed")
    val subscribed: Boolean = false,
    @SerializedName("trackCount")
    val trackCount: Int = 0,
    @SerializedName("userId")
    val userId: Long = 0,
    @SerializedName("playCount")
    val playCount: Long = 0,
    @SerializedName("bookCount")
    val bookCount: Long = 0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("highQuality")
    val highQuality: Boolean = false,
)

data class SearchSuggestUser(
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("userId")
    val userId: Long = 0,
    @SerializedName("userType")
    val userType: Int = 0,
)
