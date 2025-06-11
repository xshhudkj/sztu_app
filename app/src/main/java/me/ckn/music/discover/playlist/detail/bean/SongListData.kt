package me.ckn.music.discover.playlist.detail.bean

import com.google.gson.annotations.SerializedName
import me.ckn.music.common.bean.SongData

/**
 * Created by wangchenyan.top on 2023/9/22.
 */
data class SongListData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("songs")
    val songs: List<SongData> = emptyList()
)
