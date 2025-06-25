package me.ckn.music.storage

import androidx.media3.common.MediaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.ckn.music.consts.FilePath
import me.ckn.music.utils.getFilePath
import me.ckn.music.utils.getSongId
import me.ckn.music.utils.isLocal
import java.io.File

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/9/18
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：歌词缓存
 * File Description: Lyrics cache
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
object LrcCache {

    /**
     * 获取歌词路径
     */
    fun getLrcFilePath(music: MediaItem): String? {
        if (music.isLocal()) {
            val audioFile = File(music.mediaMetadata.getFilePath())
            val lrcFile = File(audioFile.parent, "${audioFile.nameWithoutExtension}.lrc")
            if (lrcFile.exists()) {
                return lrcFile.path
            }
        } else {
            val lrcFile = File(FilePath.lrcDir, music.getSongId().toString())
            if (lrcFile.exists()) {
                return lrcFile.path
            }
        }
        return null
    }

    suspend fun saveLrcFile(music: MediaItem, content: String): File {
        return withContext(Dispatchers.IO) {
            File(FilePath.lrcDir, music.getSongId().toString()).also {
                it.writeText(content)
            }
        }
    }

    /**
     * 获取翻译歌词文件路径
     * Get translation lyrics file path
     */
    fun getTlyricFilePath(music: MediaItem): String? {
        if (!music.isLocal()) {
            val tlyricFile = File(FilePath.lrcDir, "${music.getSongId()}.tlyric")
            if (tlyricFile.exists()) {
                return tlyricFile.path
            }
        }
        return null
    }

    /**
     * 保存翻译歌词文件
     * Save translation lyrics file
     */
    suspend fun saveTlyricFile(music: MediaItem, content: String): File {
        return withContext(Dispatchers.IO) {
            File(FilePath.lrcDir, "${music.getSongId()}.tlyric").also {
                it.writeText(content)
            }
        }
    }

    /**
     * 检查是否有双语歌词缓存
     * Check if dual language lyrics cache exists
     */
    fun hasDualLanguageLrc(music: MediaItem): Boolean {
        return getLrcFilePath(music) != null && getTlyricFilePath(music) != null
    }
}