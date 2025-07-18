package me.ckn.music.search

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * 搜索适配器基类
 * 统一DiffCallback的重复实现，减少代码冗余
 *
 * Original: Created by wangchenyan.top on 2024/12/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：搜索适配器基类
 * File Description: Search adapter base class
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
abstract class SearchAdapterBase<T : SearchAdapterBase.SearchItem, VH : RecyclerView.ViewHolder>(
) : ListAdapter<T, VH>(SearchDiffCallback<T>()) {

    /**
     * 搜索项基础接口
     * 所有搜索数据类都应实现此接口
     */
    interface SearchItem {
        val searchId: Long
    }

    /**
     * 统一的DiffCallback实现
     * 基于searchId进行比较，避免重复代码
     */
    private class SearchDiffCallback<T : SearchAdapterBase.SearchItem> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.searchId == newItem.searchId
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }
} 