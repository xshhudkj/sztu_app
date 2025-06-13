package me.ckn.music.main.playlist

import android.content.Context
import android.util.Log
import androidx.media3.common.MediaItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import me.ckn.music.account.service.UserService
import me.ckn.music.common.bean.SongData
import me.ckn.music.common.bean.ArtistData
import me.ckn.music.common.bean.AlbumData

import me.ckn.music.storage.preference.ConfigPreferences
import me.ckn.music.utils.toMediaItem
import me.ckn.music.utils.getFee
import me.ckn.music.utils.getSongId
import top.wangchenyan.common.net.apiCall
import top.wangchenyan.common.utils.GsonUtils
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 导入结果数据类
 */
data class ImportResult(
    val success: Boolean,
    val importedCount: Int = 0,
    val totalCount: Int = 0
)

/**
 * WhisperPlay Music Player
 *
 * Created by ckn on 2025-06-11
 *
 * 文件描述：最近播放数据仓库
 * File Description: Recent play repository
 *
 * @author ckn
 * @since 2025-06-11
 * @version 2.3.0
 */
@Singleton
class RecentPlayRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userService: UserService
) {
    companion object {
        private const val TAG = "RecentPlayRepository"
        private const val PREF_NAME = "recent_play_history"
        private const val KEY_RECENT_SONGS = "recent_songs"
        private const val MAX_RECENT_SONGS = 100
    }

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // 响应式数据流：播放历史歌曲列表
    private val _recentPlaySongs = MutableStateFlow<List<MediaItem>>(emptyList())
    val recentPlaySongs: StateFlow<List<MediaItem>> = _recentPlaySongs.asStateFlow()

    init {
        // 初始化时加载本地数据
        loadInitialData()
    }

    /**
     * 初始化加载本地数据
     */
    private fun loadInitialData() {
        try {
            val localData = getRecentPlayFromLocalSync()
            _recentPlaySongs.value = localData
            Log.d(TAG, "初始化加载播放历史: ${localData.size}首歌曲")
        } catch (e: Exception) {
            Log.e(TAG, "初始化加载播放历史失败", e)
            _recentPlaySongs.value = emptyList()
        }
    }

    /**
     * 获取最近播放歌曲列表（纯本地操作）
     * @deprecated 使用响应式数据流 recentPlaySongs 替代
     */
    suspend fun getRecentPlaySongs(): List<MediaItem> = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "从本地获取最近播放")
            return@withContext getRecentPlayFromLocal()
        } catch (e: Exception) {
            Log.e(TAG, "获取本地最近播放失败", e)
            return@withContext emptyList()
        }
    }

    /**
     * 从网络获取最近播放
     */
    private suspend fun getRecentPlayFromNetwork(): List<MediaItem> {
        return try {
            val result = RecentPlayApi.get().getRecentPlaySongs(50)
            if (result.code == 200) {
                val songs = result.data.list.mapNotNull { item ->
                    // 优先使用 song 字段，如果为空则使用 data 字段
                    val songData = if (item.song.id != 0L) item.song else item.data
                    if (songData.id != 0L) songData else null
                }
                Log.d(TAG, "从网络获取到 ${songs.size} 首最近播放歌曲")
                songs.map { songData -> songData.toMediaItem() }
            } else {
                Log.w(TAG, "网络请求失败，code: ${result.code}, message: ${result.message}")
                getRecentPlayFromLocal()
            }
        } catch (e: Exception) {
            Log.e(TAG, "网络请求异常", e)
            getRecentPlayFromLocal()
        }
    }

    /**
     * 从本地获取最近播放（同步版本，用于初始化）
     */
    private fun getRecentPlayFromLocalSync(): List<MediaItem> {
        return try {
            val jsonString = sharedPreferences.getString(KEY_RECENT_SONGS, null)
            if (jsonString.isNullOrEmpty()) {
                Log.d(TAG, "本地无最近播放记录")
                return emptyList()
            }

            val songs = GsonUtils.fromJson(jsonString, Array<SongData>::class.java)?.toList() ?: emptyList()
            Log.d(TAG, "从本地获取到 ${songs.size} 首最近播放歌曲")
            songs.map { songData -> songData.toMediaItem() }
        } catch (e: Exception) {
            Log.e(TAG, "解析本地最近播放数据失败", e)
            emptyList()
        }
    }

    /**
     * 从本地获取最近播放
     */
    private fun getRecentPlayFromLocal(): List<MediaItem> {
        return getRecentPlayFromLocalSync()
    }

    /**
     * 添加歌曲到本地最近播放记录
     */
    suspend fun addToRecentPlay(songData: SongData) = withContext(Dispatchers.IO) {
        try {
            val currentList = getRecentPlayFromLocal().mapNotNull { mediaItem ->
                // 将MediaItem转换回SongData，保持完整信息
                // 修复：使用正确的mediaId解析方式（格式为 "type#songId"）
                SongData(
                    id = mediaItem.getSongId(), // 使用正确的getSongId()方法
                    name = mediaItem.mediaMetadata.title?.toString() ?: "",
                    ar = listOf(ArtistData(name = mediaItem.mediaMetadata.artist?.toString() ?: "")),
                    al = AlbumData(
                        name = mediaItem.mediaMetadata.albumTitle?.toString() ?: "",
                        picUrl = mediaItem.mediaMetadata.artworkUri?.toString() ?: ""
                    ),
                    dt = mediaItem.mediaMetadata.durationMs ?: 0L,
                    fee = mediaItem.getFee()
                )
            }.toMutableList()

            // 移除重复的歌曲
            currentList.removeAll { it.id == songData.id }

            // 添加到列表开头
            currentList.add(0, songData)

            // 限制列表大小
            if (currentList.size > MAX_RECENT_SONGS) {
                currentList.subList(MAX_RECENT_SONGS, currentList.size).clear()
            }

            // 保存到本地
            val jsonString = GsonUtils.toJson(currentList)
            sharedPreferences.edit()
                .putString(KEY_RECENT_SONGS, jsonString)
                .apply()

            // 立即更新响应式数据流
            val newMediaItems = currentList.map { it.toMediaItem() }
            _recentPlaySongs.value = newMediaItems

            Log.d(TAG, "已添加歌曲到本地最近播放: ${songData.name}，当前列表${newMediaItems.size}首")
        } catch (e: Exception) {
            Log.e(TAG, "添加歌曲到最近播放失败", e)
        }
    }

    /**
     * 从网易云导入最近播放历史（仅作为补充，不覆盖本地历史）
     * @return 导入结果，包含是否成功和导入的歌曲数量
     */
    suspend fun importFromNetease(): ImportResult = withContext(Dispatchers.IO) {
        try {
            if (!userService.isLogin()) {
                Log.w(TAG, "用户未登录，无法导入网易云历史")
                return@withContext ImportResult(false)
            }

            Log.d(TAG, "开始从网易云导入最近播放历史（作为补充）")
            val networkResult = getRecentPlayFromNetwork()

            if (networkResult.isNotEmpty()) {
                // 获取当前本地数据，保持完整信息
                val localList = getRecentPlayFromLocal().mapNotNull { mediaItem ->
                    SongData(
                        id = mediaItem.getSongId(), // 修复：使用正确的getSongId()方法
                        name = mediaItem.mediaMetadata.title?.toString() ?: "",
                        ar = listOf(ArtistData(name = mediaItem.mediaMetadata.artist?.toString() ?: "")),
                        al = AlbumData(
                            name = mediaItem.mediaMetadata.albumTitle?.toString() ?: "",
                            picUrl = mediaItem.mediaMetadata.artworkUri?.toString() ?: ""
                        ),
                        dt = mediaItem.mediaMetadata.durationMs ?: 0L,
                        fee = mediaItem.getFee()
                    )
                }.toMutableList()

                // 获取网络数据并转换为SongData，保持完整信息
                val networkSongs = networkResult.mapNotNull { mediaItem ->
                    SongData(
                        id = mediaItem.getSongId(), // 修复：使用正确的getSongId()方法
                        name = mediaItem.mediaMetadata.title?.toString() ?: "",
                        ar = listOf(ArtistData(name = mediaItem.mediaMetadata.artist?.toString() ?: "")),
                        al = AlbumData(
                            name = mediaItem.mediaMetadata.albumTitle?.toString() ?: "",
                            picUrl = mediaItem.mediaMetadata.artworkUri?.toString() ?: ""
                        ),
                        dt = mediaItem.mediaMetadata.durationMs ?: 0L,
                        fee = mediaItem.getFee()
                    )
                }

                // 修复：本地历史优先，网络数据仅作为补充
                val mergedList = mutableListOf<SongData>()

                // 先保留所有本地数据（本地历史是用户真实的播放记录，不能被覆盖）
                mergedList.addAll(localList)

                // 再添加网络数据中本地没有的歌曲（仅作为补充）
                var importedCount = 0
                networkSongs.forEach { networkSong ->
                    if (mergedList.none { it.id == networkSong.id }) {
                        mergedList.add(networkSong)
                        importedCount++
                    }
                }

                // 限制总数量
                if (mergedList.size > MAX_RECENT_SONGS) {
                    mergedList.subList(MAX_RECENT_SONGS, mergedList.size).clear()
                }

                // 保存合并后的数据
                val jsonString = GsonUtils.toJson(mergedList)
                sharedPreferences.edit()
                    .putString(KEY_RECENT_SONGS, jsonString)
                    .apply()

                // 立即更新响应式数据流
                val newMediaItems = mergedList.map { it.toMediaItem() }
                _recentPlaySongs.value = newMediaItems

                Log.d(TAG, "成功导入网易云历史作为补充，本地${localList.size}首，网络补充${importedCount}首，合并后共${mergedList.size}首")
                return@withContext ImportResult(true, importedCount, mergedList.size)
            } else {
                Log.w(TAG, "网易云历史为空，导入失败")
                return@withContext ImportResult(false)
            }
        } catch (e: Exception) {
            Log.e(TAG, "导入网易云历史失败", e)
            return@withContext ImportResult(false)
        }
    }

    /**
     * 从本地最近播放记录中删除指定歌曲
     */
    suspend fun removeFromRecentPlay(songId: Long) = withContext(Dispatchers.IO) {
        try {
            val currentList = getRecentPlayFromLocal().mapNotNull { mediaItem ->
                // 将MediaItem转换回SongData
                SongData(
                    id = mediaItem.getSongId(), // 修复：使用正确的getSongId()方法
                    name = mediaItem.mediaMetadata.title?.toString() ?: "",
                    ar = listOf(ArtistData(name = mediaItem.mediaMetadata.artist?.toString() ?: "")),
                    al = AlbumData(
                        name = mediaItem.mediaMetadata.albumTitle?.toString() ?: "",
                        picUrl = mediaItem.mediaMetadata.artworkUri?.toString() ?: ""
                    ),
                    dt = mediaItem.mediaMetadata.durationMs ?: 0L,
                    fee = mediaItem.getFee()
                )
            }.toMutableList()

            // 移除指定歌曲
            val removed = currentList.removeAll { it.id == songId }

            if (removed) {
                // 保存更新后的数据
                val jsonString = GsonUtils.toJson(currentList)
                sharedPreferences.edit()
                    .putString(KEY_RECENT_SONGS, jsonString)
                    .apply()

                // 立即更新响应式数据流
                val newMediaItems = currentList.map { it.toMediaItem() }
                _recentPlaySongs.value = newMediaItems

                Log.d(TAG, "已从最近播放记录中删除歌曲: songId=$songId，当前列表${newMediaItems.size}首")
            } else {
                Log.w(TAG, "未找到要删除的歌曲: songId=$songId")
            }
        } catch (e: Exception) {
            Log.e(TAG, "删除最近播放记录失败", e)
        }
    }

    /**
     * 清空本地最近播放记录
     */
    fun clearLocalRecentPlay() {
        sharedPreferences.edit()
            .remove(KEY_RECENT_SONGS)
            .apply()

        // 立即更新响应式数据流
        _recentPlaySongs.value = emptyList()

        Log.d(TAG, "已清空本地最近播放记录")
    }
}
