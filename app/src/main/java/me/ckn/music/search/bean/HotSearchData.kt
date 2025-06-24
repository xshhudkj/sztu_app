package me.ckn.music.search.bean

import com.google.gson.annotations.SerializedName

/**
 * 热门搜索数据
 *
 * Original: Created by wangchenyan.top on 2024/12/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：热门搜索数据
 * File Description: Hot search data
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
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
