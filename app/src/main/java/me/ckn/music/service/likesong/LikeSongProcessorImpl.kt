package me.ckn.music.service.likesong

import android.app.Activity
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.ckn.music.account.service.UserService
import me.ckn.music.mine.MineApi
import top.wangchenyan.common.model.CommonResult
import top.wangchenyan.common.net.apiCall
import javax.inject.Inject
import javax.inject.Singleton

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2024/3/21
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：喜欢歌曲处理器实现
 * File Description: Like song processor implementation
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
@Singleton
class LikeSongProcessorImpl @Inject constructor(
    private val userService: UserService
) : LikeSongProcessor, CoroutineScope by MainScope() {
    private val likeSongSet = mutableSetOf<Long>()

    // 状态变化通知流
    private val _likeStateChanged = MutableStateFlow<Long?>(null)
    override val likeStateChanged: StateFlow<Long?> = _likeStateChanged.asStateFlow()

    override fun init() {
        launch {
            userService.profile.collectLatest {
                if (it != null) {
                    updateLikeSongList()
                } else {
                    likeSongSet.clear()
                }
            }
        }
    }

    override fun updateLikeSongList() {
        if (userService.isLogin().not()) return
        launch {
            val res = runCatching {
                MineApi.get().getMyLikeSongList(userService.getUserId())
            }
            val data = res.getOrNull()
            if (data?.code == 200) {
                likeSongSet.clear()
                likeSongSet.addAll(data.ids)
            }
        }
    }

    override fun isLiked(id: Long): Boolean {
        if (userService.isLogin().not()) {
            return false
        }
        return likeSongSet.contains(id)
    }

    override suspend fun like(activity: Activity, id: Long): CommonResult<Unit> {
        return try {
            if (userService.isLogin().not()) {
                userService.checkLogin(activity)
                return CommonResult.fail(msg = "需要登录")
            }
            val isLike = isLiked(id)
            if (isLike) {
                val res = apiCall {
                    MineApi.get().likeSong(id, false)
                }
                if (res.isSuccess()) {
                    likeSongSet.remove(id)
                    updateLikeSongList()
                    // 通知状态变化
                    _likeStateChanged.value = id
                    CommonResult.success(Unit)
                } else {
                    CommonResult.fail(res.code, res.msg)
                }
            } else {
                val res = apiCall {
                    MineApi.get().likeSong(id, true)
                }
                if (res.isSuccess()) {
                    likeSongSet.add(id)
                    updateLikeSongList()
                    // 通知状态变化
                    _likeStateChanged.value = id
                    CommonResult.success(Unit)
                } else {
                    CommonResult.fail(res.code, res.msg)
                }
            }
        } catch (e: Exception) {
            Log.e("LikeSongProcessor", "收藏歌曲操作失败", e)
            CommonResult.fail(msg = "操作失败: ${e.message}")
        }
    }
}