package me.wcy.music.utils

import android.net.Uri
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import me.wcy.music.common.bean.SongData
import me.wcy.music.storage.db.entity.SongEntity
import me.wcy.music.utils.MusicUtils.asLargeCover
import me.wcy.music.utils.MusicUtils.asSmallCover
import top.wangchenyan.common.CommonApp

/**
 * Created by wangchenyan.top on 2023/9/18.
 */

const val SCHEME_NETEASE = "netease"
const val PARAM_ID = "id"
const val EXTRA_DURATION = "duration"
const val EXTRA_FILE_PATH = "file_path"
const val EXTRA_FILE_NAME = "file_name"
const val EXTRA_FILE_SIZE = "file_size"
const val EXTRA_BASE_COVER = "base_cover"
const val EXTRA_FEE = "fee"

fun SongData.getSimpleArtist(): String {
    return ar.joinToString("/") { it.name }
}

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

fun SongData.toMediaItem(): MediaItem {
    val uri = Uri.Builder()
        .scheme(SCHEME_NETEASE)
        .authority(CommonApp.app.packageName)
        .appendQueryParameter(PARAM_ID, id.toString())
        .build()

    // 优化封面获取逻辑：确保专辑和歌手详情页的歌曲也能正确显示封面
    // 基于错误日志分析，修复哈希值问题以避免404错误
    val albumCover = when {
        // 优先使用专辑的picUrl（通常最完整且包含正确哈希值）
        al.picUrl.isNotEmpty() -> {
            // 验证URL格式，避免无效链接
            if (al.picUrl.startsWith("http")) al.picUrl else ""
        }
        // 如果专辑picUrl为空，但有pic_str字段（包含哈希值），优先使用
        al.picStr.isNotEmpty() && al.pic > 0 -> {
            // 使用pic_str作为哈希值构建正确的封面URL
            "https://p1.music.126.net/${al.picStr}/${al.pic}.jpg"
        }
        // 如果有专辑pic字段但没有pic_str，尝试使用pic作为哈希值（可能不准确）
        al.pic > 0 -> {
            // 注意：这种方式可能导致404，因为缺少正确的哈希值
            // 但作为备选方案保留
            "https://p1.music.126.net/${al.pic}/${al.pic}.jpg"
        }
        // 最后的备选方案：使用默认封面（空字符串会显示默认封面）
        else -> ""
    }

    return MediaItem.Builder()
        .setMediaId(generateUniqueId(SongEntity.ONLINE, id))
        .setUri(uri)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setTitle(name)
                .setArtist(getSimpleArtist())
                .setAlbumTitle(al.name)
                .setAlbumArtist(getSimpleArtist())
                .setArtworkUri(Uri.parse(al.getLargeCover()))
                .setBaseCover(albumCover)
                .setDuration(dt)
                .setFee(fee)
                .build()
        )
        .build()
}

fun generateUniqueId(type: Int, songId: Long): String {
    return "$type#$songId"
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

fun MediaMetadata.Builder.setDuration(duration: Long) = apply {
    val extras = build().extras ?: bundleOf()
    extras.putLong(EXTRA_DURATION, duration)
    setExtras(extras)
}

fun MediaMetadata.getDuration(): Long {
    return extras?.getLong(EXTRA_DURATION) ?: 0
}

fun MediaMetadata.Builder.setFilePath(value: String) = apply {
    val extras = build().extras ?: bundleOf()
    extras.putString(EXTRA_FILE_PATH, value)
    setExtras(extras)
}

fun MediaMetadata.getFilePath(): String {
    return extras?.getString(EXTRA_FILE_PATH) ?: ""
}

fun MediaMetadata.Builder.setFileName(name: String) = apply {
    val extras = build().extras ?: bundleOf()
    extras.putString(EXTRA_FILE_NAME, name)
    setExtras(extras)
}

fun MediaMetadata.getFileName(): String {
    return extras?.getString(EXTRA_FILE_NAME) ?: ""
}

fun MediaMetadata.Builder.setFileSize(size: Long) = apply {
    val extras = build().extras ?: bundleOf()
    extras.putLong(EXTRA_FILE_SIZE, size)
    setExtras(extras)
}

fun MediaMetadata.getFileSize(): Long {
    return extras?.getLong(EXTRA_FILE_SIZE) ?: 0
}

fun MediaMetadata.Builder.setBaseCover(value: String) = apply {
    val extras = build().extras ?: bundleOf()
    extras.putString(EXTRA_BASE_COVER, value)
    setExtras(extras)
}

fun MediaMetadata.getBaseCover(): String? {
    return extras?.getString(EXTRA_BASE_COVER)
}

fun MediaMetadata.Builder.setFee(fee: Int) = apply {
    val extras = build().extras ?: bundleOf()
    extras.putInt(EXTRA_FEE, fee)
    setExtras(extras)
}

fun MediaMetadata.getFee(): Int {
    return extras?.getInt(EXTRA_FEE) ?: 0
}

fun MediaItem.getSmallCover(): String {
    val baseCover = mediaMetadata.getBaseCover()
    return if (isLocal()) {
        baseCover ?: ""
    } else {
        baseCover?.asSmallCover() ?: ""
    }
}

fun MediaItem.getLargeCover(): String {
    val baseCover = mediaMetadata.getBaseCover()
    return if (isLocal()) {
        baseCover ?: ""
    } else {
        baseCover?.asLargeCover() ?: ""
    }
}

fun MediaItem.getFee(): Int {
    return mediaMetadata.getFee()
}
