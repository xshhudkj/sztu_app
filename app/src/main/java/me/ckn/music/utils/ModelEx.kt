package me.ckn.music.utils

import android.net.Uri
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import me.ckn.music.common.bean.SongData
import me.ckn.music.storage.db.entity.SongEntity
import me.ckn.music.utils.MusicUtils.asLargeCover
import me.ckn.music.utils.MusicUtils.asSmallCover
import top.wangchenyan.common.CommonApp

/**
 * 模型转换扩展函数
 * 提供统一的数据转换和处理工具
 *
 * Original: Created by wangchenyan.top on 2023/9/18
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：数据模型扩展函数
 * File Description: Extension functions for data models
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */

const val SCHEME_NETEASE = "netease"
const val PARAM_ID = "id"

// MediaMetadata extras 常量统一管理
object MediaExtras {
    const val DURATION = "duration"
    const val FILE_PATH = "file_path"
    const val FILE_NAME = "file_name"
    const val FILE_SIZE = "file_size"
    const val BASE_COVER = "base_cover"
    const val FEE = "fee"
}

/**
 * 封面处理工具类
 * 统一处理本地和在线歌曲的封面URL生成
 */
object CoverUtils {
    /**
     * 获取小封面URL
     * @param isLocal 是否为本地歌曲
     * @param baseCover 基础封面URL
     */
    fun getSmallCover(isLocal: Boolean, baseCover: String?): String {
        return if (isLocal) {
            baseCover ?: ""
        } else {
            baseCover?.asSmallCover() ?: ""
        }
    }

    /**
     * 获取大封面URL
     * @param isLocal 是否为本地歌曲
     * @param baseCover 基础封面URL
     */
    fun getLargeCover(isLocal: Boolean, baseCover: String?): String {
        return if (isLocal) {
            baseCover ?: ""
        } else {
            baseCover?.asLargeCover() ?: ""
        }
    }
}

/**
 * MediaMetadata 构建器扩展函数
 * 统一extras操作，减少重复代码
 */
object MetadataBuilderUtils {
    /**
     * 通用的extras设置方法
     */
    private fun MediaMetadata.Builder.setExtrasValue(key: String, value: Any) = apply {
        val extras = build().extras ?: bundleOf()
        when (value) {
            is Long -> extras.putLong(key, value)
            is String -> extras.putString(key, value)
            is Int -> extras.putInt(key, value)
            else -> throw IllegalArgumentException("Unsupported value type: ${value::class.java}")
        }
        setExtras(extras)
    }

    fun MediaMetadata.Builder.setDuration(duration: Long) = 
        setExtrasValue(MediaExtras.DURATION, duration)

    fun MediaMetadata.Builder.setFilePath(value: String) = 
        setExtrasValue(MediaExtras.FILE_PATH, value)

    fun MediaMetadata.Builder.setFileName(name: String) = 
        setExtrasValue(MediaExtras.FILE_NAME, name)

    fun MediaMetadata.Builder.setFileSize(size: Long) = 
        setExtrasValue(MediaExtras.FILE_SIZE, size)

    fun MediaMetadata.Builder.setBaseCover(value: String) = 
        setExtrasValue(MediaExtras.BASE_COVER, value)

    fun MediaMetadata.Builder.setFee(fee: Int) = 
        setExtrasValue(MediaExtras.FEE, fee)
}

// 导入MetadataBuilderUtils的扩展函数到顶级作用域
fun MediaMetadata.Builder.setDuration(duration: Long) = 
    MetadataBuilderUtils.run { setDuration(duration) }

fun MediaMetadata.Builder.setFilePath(value: String) = 
    MetadataBuilderUtils.run { setFilePath(value) }

fun MediaMetadata.Builder.setFileName(name: String) = 
    MetadataBuilderUtils.run { setFileName(name) }

fun MediaMetadata.Builder.setFileSize(size: Long) = 
    MetadataBuilderUtils.run { setFileSize(size) }

fun MediaMetadata.Builder.setBaseCover(value: String) = 
    MetadataBuilderUtils.run { setBaseCover(value) }

fun MediaMetadata.Builder.setFee(fee: Int) = 
    MetadataBuilderUtils.run { setFee(fee) }

// ======================== 数据转换扩展函数 ========================

fun SongData.getSimpleArtist(): String {
    return ar.joinToString("/") { it.name }
}

fun generateUniqueId(type: Int, songId: Long): String {
    return "$type#$songId"
}

// ======================== SongEntity 转换 ========================

fun SongEntity.toMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setMediaId(uniqueId)
        .setUri(uri)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setTitle(title)
                .setArtist(artist)
                .setAlbumTitle(album)
                .setAlbumArtist(artist)
                .setArtworkUri(Uri.parse(getLargeCover()))
                .setBaseCover(albumCover)
                .setDuration(duration)
                .setFilePath(path)
                .setFileName(fileName)
                .setFileSize(fileSize)
                .build()
        )
        .build()
}

// ======================== MediaItem 转换 ========================

fun MediaItem.toSongEntity(): SongEntity {
    return SongEntity(
        type = getSongType(),
        songId = getSongId(),
        title = mediaMetadata.title?.toString() ?: "",
        artist = mediaMetadata.artist?.toString() ?: "",
        artistId = 0,
        album = mediaMetadata.albumTitle?.toString() ?: "",
        albumId = 0,
        albumCover = mediaMetadata.getBaseCover() ?: "",
        duration = mediaMetadata.getDuration(),
        uri = localConfiguration?.uri?.toString() ?: "",
        path = mediaMetadata.getFilePath(),
        fileName = mediaMetadata.getFileName(),
        fileSize = mediaMetadata.getFileSize()
    )
}

fun MediaItem.isLocal(): Boolean {
    return getSongType() == SongEntity.LOCAL
}

fun MediaItem.getSongType(): Int {
    return mediaId.split("#").firstOrNull()?.toIntOrNull() ?: SongEntity.LOCAL
}

fun MediaItem.getSongId(): Long {
    return mediaId.split("#").getOrNull(1)?.toLongOrNull() ?: 0L
}

fun MediaItem.getSmallCover(): String {
    return CoverUtils.getSmallCover(isLocal(), mediaMetadata.getBaseCover())
}

fun MediaItem.getLargeCover(): String {
    return CoverUtils.getLargeCover(isLocal(), mediaMetadata.getBaseCover())
}

fun MediaItem.getFee(): Int {
    return mediaMetadata.getFee()
}

// ======================== SongData 转换 ========================

fun SongData.toMediaItem(): MediaItem {
    val uri = Uri.Builder()
        .scheme(SCHEME_NETEASE)
        .authority(CommonApp.app.packageName)
        .appendQueryParameter(PARAM_ID, id.toString())
        .build()

    // 优化封面获取逻辑：确保专辑和歌手详情页的歌曲也能正确显示封面
    val albumCover = AlbumCoverProcessor.getOptimalCover(al)

    return MediaItem.Builder()
        .setMediaId(generateUniqueId(SongEntity.ONLINE, id))
        .setUri(uri)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setTitle(name)
                .setArtist(getSimpleArtist())
                .setAlbumTitle(al.name)
                .setAlbumArtist(getSimpleArtist())
                .setArtworkUri(Uri.parse(CoverUtils.getLargeCover(false, albumCover)))
                .setBaseCover(albumCover)
                .setDuration(dt)
                .setFee(fee)
                .build()
        )
        .build()
}

/**
 * 专辑封面处理器
 * 统一处理复杂的封面URL逻辑
 */
object AlbumCoverProcessor {
    fun getOptimalCover(album: me.ckn.music.common.bean.AlbumData): String {
        return when {
            // 优先使用专辑的picUrl（通常最完整且包含正确哈希值）
            album.picUrl.isNotEmpty() -> {
                if (album.picUrl.startsWith("http")) album.picUrl else ""
            }
            // 如果专辑picUrl为空，但有pic_str字段（包含哈希值），优先使用
            album.picStr.isNotEmpty() && album.pic > 0 -> {
                "https://p1.music.126.net/${album.picStr}/${album.pic}.jpg"
            }
            // 如果有专辑pic字段但没有pic_str，尝试使用pic作为哈希值
            album.pic > 0 -> {
                "https://p1.music.126.net/${album.pic}/${album.pic}.jpg"
            }
            // 最后的备选方案：使用默认封面
            else -> ""
        }
    }
}

// ======================== MediaMetadata 读取扩展函数 ========================

fun MediaMetadata.getDuration(): Long {
    return extras?.getLong(MediaExtras.DURATION) ?: 0
}

fun MediaMetadata.getFilePath(): String {
    return extras?.getString(MediaExtras.FILE_PATH) ?: ""
}

fun MediaMetadata.getFileName(): String {
    return extras?.getString(MediaExtras.FILE_NAME) ?: ""
}

fun MediaMetadata.getFileSize(): Long {
    return extras?.getLong(MediaExtras.FILE_SIZE) ?: 0
}

fun MediaMetadata.getBaseCover(): String? {
    return extras?.getString(MediaExtras.BASE_COVER)
}

fun MediaMetadata.getFee(): Int {
    return extras?.getInt(MediaExtras.FEE) ?: 0
}
