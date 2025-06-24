package me.ckn.music.common

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/10/24
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：列表项点击监听器
 * File Description: Item click listener
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
interface OnItemClickListener<T> {
    fun onItemClick(item: T, position: Int)
}