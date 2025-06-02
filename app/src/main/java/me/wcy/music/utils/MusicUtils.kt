package me.wcy.music.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.audiofx.AudioEffect
import android.text.TextUtils
import android.util.Log
import androidx.core.text.buildSpannedString
import top.wangchenyan.common.ext.getColorEx
import top.wangchenyan.common.widget.CustomSpan.appendStyle
import me.wcy.music.R

/**
 * 歌曲工具类
 * Created by wcy on 2015/11/27.
 */
object MusicUtils {

    private const val TAG = "MusicUtils"

    fun isAudioControlPanelAvailable(context: Context): Boolean {
        return isIntentAvailable(
            context,
            Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL)
        )
    }

    private fun isIntentAvailable(context: Context, intent: Intent): Boolean {
        return context.packageManager.resolveActivity(
            intent,
            PackageManager.GET_RESOLVED_FILTER
        ) != null
    }

    /**
     * 检测是否为Android Automotive系统
     * 通过检查系统特性和包管理器来判断
     */
    fun isAndroidAutomotive(context: Context): Boolean {
        return try {
            // 方法1: 检查Android Automotive系统特性
            val hasAutomotiveFeature = context.packageManager.hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE)

            // 方法2: 检查UI模式是否为车载模式
            val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as android.app.UiModeManager
            val isCarMode = uiModeManager.currentModeType == android.content.res.Configuration.UI_MODE_TYPE_CAR

            val result = hasAutomotiveFeature || isCarMode
            Log.d(TAG, "isAndroidAutomotive() - 系统检测结果: $result (特性检测: $hasAutomotiveFeature, 车载模式: $isCarMode)")
            result
        } catch (e: Exception) {
            Log.e(TAG, "isAndroidAutomotive() - 系统检测失败，默认为非车载系统", e)
            false
        }
    }

    fun getArtistAndAlbum(artist: String?, album: String?): String? {
        return if (TextUtils.isEmpty(artist) && TextUtils.isEmpty(album)) {
            ""
        } else if (!TextUtils.isEmpty(artist) && TextUtils.isEmpty(album)) {
            artist
        } else if (TextUtils.isEmpty(artist) && !TextUtils.isEmpty(album)) {
            album
        } else {
            "$artist - $album"
        }
    }

    fun keywordsTint(context: Context, text: String, keywords: String): CharSequence {
        if (text.isEmpty() || keywords.isEmpty()) {
            return text
        }
        val splitText = text.split(keywords)
        return buildSpannedString {
            splitText.forEachIndexed { index, s ->
                append(s)
                if (index < splitText.size - 1) {
                    appendStyle(
                        keywords,
                        color = context.getColorEx(R.color.common_theme_color)
                    )
                }
            }
        }
    }

    fun String.asSmallCover(): String {
        return appendImageSize(200)
    }

    fun String.asLargeCover(): String {
        return appendImageSize(800)
    }

    private fun String.appendImageSize(size: Int): String {
        return if (contains("?")) {
            "$this&param=${size}y${size}"
        } else {
            "$this?param=${size}y${size}"
        }
    }
}