package me.ckn.music.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import top.wangchenyan.common.ext.toUnMutable
import me.ckn.music.consts.Consts

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：搜索ViewModel
 * File Description: Search ViewModel
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class SearchViewModel : ViewModel() {
    private val _keywords = MutableStateFlow("")
    val keywords = _keywords.toUnMutable()

    private val _historyKeywords = MutableStateFlow(SearchPreference.historyKeywords ?: emptyList())
    val historyKeywords = _historyKeywords.toUnMutable()

    private val _showResult = MutableStateFlow(false)
    val showResult = _showResult.toUnMutable()

    fun search(keywords: String) {
        if (keywords.isEmpty()) {
            return
        }
        _keywords.value = keywords
        _showResult.value = true

        val list = _historyKeywords.value.toMutableList()
        list.remove(keywords)
        list.add(0, keywords)
        val realList = list.take(Consts.SEARCH_HISTORY_COUNT)
        _historyKeywords.value = realList
        viewModelScope.launch(Dispatchers.IO) {
            SearchPreference.historyKeywords = realList
        }
    }

    fun showHistory() {
        _showResult.value = false
    }

    fun clearAllHistory() {
        _historyKeywords.value = emptyList()
        viewModelScope.launch(Dispatchers.IO) {
            SearchPreference.historyKeywords = emptyList()
        }
    }

    fun removeHistoryKeyword(keyword: String) {
        val list = _historyKeywords.value.toMutableList()
        list.remove(keyword)
        _historyKeywords.value = list
        viewModelScope.launch(Dispatchers.IO) {
            SearchPreference.historyKeywords = list
        }
    }
}