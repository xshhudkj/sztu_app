package me.ckn.music.mine.collect.song.bean

import com.google.gson.annotations.SerializedName

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2024/3/21
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：收藏歌曲结果
 * File Description: Collect song result
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
data class CollectSongResult(
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("body")
    val body: Body = Body(),
) {
    data class Body(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("message")
        val message: String = "",
    )
}
