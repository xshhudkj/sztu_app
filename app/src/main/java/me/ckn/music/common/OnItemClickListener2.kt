package me.ckn.music.common

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/10/11
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：列表项点击监听器2
 * File Description: Item click listener 2
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
interface OnItemClickListener2<T> {
    fun onItemClick(item: T, position: Int)
    fun onMoreClick(item: T, position: Int)
}