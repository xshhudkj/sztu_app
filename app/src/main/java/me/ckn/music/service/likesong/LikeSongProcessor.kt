package me.ckn.music.service.likesong

import android.app.Activity
import top.wangchenyan.common.model.CommonResult


/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2024/3/21
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：喜欢歌曲处理器接口
 * File Description: Like song processor interface
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
interface LikeSongProcessor {
    
    fun init()

    fun updateLikeSongList()

    fun isLiked(id: Long): Boolean

    suspend fun like(activity: Activity, id: Long): CommonResult<Unit>
}