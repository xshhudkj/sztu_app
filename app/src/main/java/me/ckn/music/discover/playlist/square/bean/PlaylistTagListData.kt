package me.ckn.music.discover.playlist.square.bean

import com.google.gson.annotations.SerializedName

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/26
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：歌单标签列表数据
 * File Description: Playlist tag list data
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
data class PlaylistTagListData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("tags")
    val tags: List<PlaylistTagData> = emptyList(),
)
