package me.ckn.music.service.likesong.bean

import com.google.gson.annotations.SerializedName

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2024/3/21
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：喜欢歌曲列表数据
 * File Description: Like song list data
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
data class LikeSongListData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("ids")
    val ids: Set<Long> = emptySet()
)
