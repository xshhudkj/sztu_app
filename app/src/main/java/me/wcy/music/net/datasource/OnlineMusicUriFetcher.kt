package me.wcy.music.net.datasource

import android.net.Uri
import kotlinx.coroutines.runBlocking
import me.wcy.music.discover.DiscoverApi
import me.wcy.music.storage.preference.ConfigPreferences
import me.wcy.music.utils.VipUtils
import top.wangchenyan.common.net.apiCall

/**
 * Created by wangchenyan.top on 2024/3/26.
 */
object OnlineMusicUriFetcher {

    fun fetchPlayUrl(uri: Uri): String {
        val songId = uri.getQueryParameter("id")?.toLongOrNull() ?: return uri.toString()
        val fee = uri.getQueryParameter("fee")?.toIntOrNull() ?: 0

        return runBlocking {
            // 根据歌曲fee字段决定音质
            val quality = if (VipUtils.needQualityDowngrade(fee) &&
                             VipUtils.isHighQuality(ConfigPreferences.playSoundQuality)) {
                // fee=8且用户设置高音质时，自动降级到standard
                VipUtils.getDowngradedQuality(ConfigPreferences.playSoundQuality, fee)
            } else {
                // 其他情况使用用户设置的音质
                ConfigPreferences.playSoundQuality
            }

            val res = apiCall {
                DiscoverApi.get().getSongUrl(songId, quality)
            }

            if (res.isSuccessWithData() && res.getDataOrThrow().isNotEmpty()) {
                return@runBlocking res.getDataOrThrow().first().url
            } else {
                return@runBlocking ""
            }
        }
    }
}