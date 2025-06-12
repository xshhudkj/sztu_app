package me.ckn.music.discover.playlist.square.bean

import com.google.gson.annotations.SerializedName
import me.ckn.music.common.bean.PlaylistData

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/25
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：歌单列表数据
 * File Description: Playlist list data
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
data class PlaylistListData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("playlists", alternate = ["playlist", "recommend", "list"])
    val playlists: List<PlaylistData> = emptyList(),
)
