package me.wcy.music.common.bean

import com.google.gson.annotations.SerializedName
import me.wcy.music.utils.MusicUtils.asLargeCover
import me.wcy.music.utils.MusicUtils.asSmallCover

/**
 * Created by wangchenyan.top on 2023/9/6.
 */
data class AlbumData(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name")
    val name: String = "",
    @Deprecated("Please use resized url")
    @SerializedName("picUrl")
    val picUrl: String = "",
    @SerializedName("tns")
    val tns: List<Any> = listOf(),
    @SerializedName("pic_str")
    val picStr: String = "",
    @SerializedName("pic")
    val pic: Long = 0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("publishTime")
    val publishTime: Long = 0,
    @SerializedName("company")
    val company: String = "",
    @SerializedName("artist")
    val artist: ArtistData? = null,
    @SerializedName("artists")
    val artists: List<ArtistData> = emptyList(),
    @SerializedName("size")
    val size: Int = 0,
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("subType")
    val subType: String = "",
    @SerializedName("type")
    val type: String = ""
) {
    fun getSmallCover(): String {
        return picUrl.asSmallCover()
    }

    fun getLargeCover(): String {
        return picUrl.asLargeCover()
    }
}
