package me.ckn.music.main.playlist.bean

import com.google.gson.annotations.SerializedName
import me.ckn.music.common.bean.SongData

/**
 * WhisperPlay Music Player
 *
 * Created by ckn on 2025-06-11
 *
 * 文件描述：最近播放数据模型
 * File Description: Recent play data model
 *
 * @author ckn
 * @since 2025-06-11
 * @version 2.3.0
 */
data class RecentPlayData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("data")
    val data: RecentPlayListData = RecentPlayListData(),
    @SerializedName("message")
    val message: String = ""
)

data class RecentPlayListData(
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("list")
    val list: List<RecentPlayItem> = listOf()
)

data class RecentPlayItem(
    @SerializedName("song")
    val song: SongData = SongData(),
    @SerializedName("playTime")
    val playTime: Long = 0,
    @SerializedName("resourceId")
    val resourceId: String = "",
    @SerializedName("data")
    val data: SongData = SongData()
)
