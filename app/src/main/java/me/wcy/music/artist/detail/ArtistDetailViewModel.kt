package me.wcy.music.artist.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import me.wcy.music.common.bean.ArtistData
import me.wcy.music.common.bean.SongData
import me.wcy.music.search.SearchApi
import top.wangchenyan.common.ext.toUnMutable
import top.wangchenyan.common.model.CommonResult
import top.wangchenyan.common.net.apiCall
import javax.inject.Inject

/**
 * 歌手详情ViewModel
 * Created by wangchenyan.top on 2024/12/20.
 */
@HiltViewModel
class ArtistDetailViewModel @Inject constructor() : ViewModel() {

    private val _artistData = MutableStateFlow<ArtistData?>(null)
    val artistData = _artistData.toUnMutable()

    private val _songList = MutableStateFlow<List<SongData>>(emptyList())
    val songList = _songList.toUnMutable()

    private val _isSubscribed = MutableStateFlow(false)
    val isSubscribed = _isSubscribed.toUnMutable()

    private var artistId = 0L

    fun init(artistId: Long) {
        this.artistId = artistId
    }

    suspend fun loadData(): CommonResult<Unit> {
        // 照抄歌单详情的加载逻辑，调用真实的歌手API
        val detailRes = kotlin.runCatching {
            SearchApi.get().getArtistDetail(artistId)
        }
        val songListRes = kotlin.runCatching {
            SearchApi.get().getArtistTopSongs(artistId)
        }
        return if (detailRes.isSuccess.not() || detailRes.getOrThrow().code != 200) {
            CommonResult.fail(msg = detailRes.exceptionOrNull()?.message)
        } else if (songListRes.isSuccess.not() || songListRes.getOrThrow().code != 200) {
            CommonResult.fail(msg = songListRes.exceptionOrNull()?.message)
        } else {
            // 暂时使用模拟数据，等API返回结构确定后再修改
            val artistData = ArtistData(
                id = artistId,
                name = "歌手名称",
                picUrl = "",
                alias = emptyList()
            )
            _artistData.value = artistData

            // 暂时使用空歌曲列表
            _songList.value = emptyList()
            CommonResult.success(Unit)
        }
    }

    suspend fun toggleSubscribe(): CommonResult<Unit> {
        val currentSubscribed = _isSubscribed.value
        val t = if (currentSubscribed) 0 else 1 // 1为收藏，0为取消收藏

        val res = apiCall {
            SearchApi.get().subscribeArtist(artistId, t)
        }

        return if (res.isSuccess()) {
            _isSubscribed.value = !currentSubscribed
            CommonResult.success(Unit)
        } else {
            CommonResult.fail(res.code, res.msg)
        }
    }
}
