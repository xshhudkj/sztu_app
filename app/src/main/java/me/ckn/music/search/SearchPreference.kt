package me.ckn.music.search

import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.storage.IPreferencesFile
import top.wangchenyan.common.storage.PreferencesFile
import me.ckn.music.consts.PreferenceName

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/21
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：搜索偏好设置
 * File Description: Search preference
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
object SearchPreference :
    IPreferencesFile by PreferencesFile(CommonApp.app, PreferenceName.SEARCH) {
    var historyKeywords by IPreferencesFile.ListProperty("history_keywords", String::class.java)
}