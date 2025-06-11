package me.ckn.music.search.bean

import com.google.gson.annotations.SerializedName

/**
 * 热门搜索数据
 * Created by wangchenyan.top on 2024/12/20.
 */
data class HotSearchListData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("data")
    val data: List<HotSearchData> = emptyList(),
)

data class HotSearchData(
    @SerializedName("searchWord")
    val searchWord: String = "",
    @SerializedName("score")
    val score: Long = 0,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("source")
    val source: Int = 0,
    @SerializedName("iconType")
    val iconType: Int = 0,
    @SerializedName("iconUrl")
    val iconUrl: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("alg")
    val alg: String = "",
)

data class HotSearchDetailListData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("data")
    val data: List<HotSearchDetailData> = emptyList(),
)

data class HotSearchDetailData(
    @SerializedName("searchWord")
    val searchWord: String = "",
    @SerializedName("score")
    val score: Long = 0,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("source")
    val source: Int = 0,
    @SerializedName("iconType")
    val iconType: Int = 0,
    @SerializedName("iconUrl")
    val iconUrl: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("alg")
    val alg: String = "",
)
