package me.ckn.music.common.bean

import com.google.gson.annotations.SerializedName
import me.ckn.music.search.SearchAdapterBase

/**
 * Created by wangchenyan.top on 2023/9/6.
 */
data class ArtistData(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("tns")
    val tns: List<Any> = listOf(),
    @SerializedName("alias")
    val alias: List<Any> = listOf(),
    @SerializedName("picUrl")
    val picUrl: String = "",
    @SerializedName("img1v1Url")
    val img1v1Url: String = "",
    @SerializedName("briefDesc")
    val briefDesc: String = "",
    @SerializedName("albumSize")
    val albumSize: Int = 0,
    @SerializedName("musicSize")
    val musicSize: Int = 0,
    @SerializedName("mvSize")
    val mvSize: Int = 0,
    @SerializedName("followed")
    val followed: Boolean = false
) : SearchAdapterBase.SearchItem {
    override val searchId: Long get() = id
}