package me.ckn.music.discover.playlist.detail.bean

import com.google.gson.annotations.SerializedName
import me.ckn.music.common.bean.PlaylistData

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/22
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：歌单详情数据
 * File Description: Playlist detail data
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
data class PlaylistDetailData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("playlist")
    val playlist: PlaylistData = PlaylistData(),
)
