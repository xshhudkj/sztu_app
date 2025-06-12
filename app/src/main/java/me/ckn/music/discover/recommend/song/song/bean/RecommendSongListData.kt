package me.ckn.music.discover.recommend.song.bean

import com.google.gson.annotations.SerializedName
import me.ckn.music.common.bean.SongData

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/6
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：每日推荐歌曲列表数据
 * File Description: Recommend song list data
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
data class RecommendSongListData(
    @SerializedName("dailySongs")
    val dailySongs: List<SongData> = emptyList()
)
