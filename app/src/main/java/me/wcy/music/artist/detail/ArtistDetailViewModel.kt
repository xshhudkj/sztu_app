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
        // 参考歌单详情页的安全处理方式，使用kotlin.runCatching
        val detailRes = kotlin.runCatching {
            SearchApi.get().getArtistDetail(artistId)
        }

        val songListRes = kotlin.runCatching {
            SearchApi.get().getArtistTopSongs(artistId)
        }

        return if (detailRes.isSuccess.not()) {
            CommonResult.fail(msg = detailRes.exceptionOrNull()?.message)
        } else if (songListRes.isSuccess.not()) {
            CommonResult.fail(msg = songListRes.exceptionOrNull()?.message)
        } else {
            // 安全地获取数据，参考歌单详情页的处理方式
            val detailResult = detailRes.getOrThrow()
            val songResult = songListRes.getOrThrow()

            // 检查API返回的code状态
            if (detailResult.code != 200) {
                return CommonResult.fail(detailResult.code, "歌手详情获取失败")
            }

            if (songResult.code != 200) {
                return CommonResult.fail(songResult.code, "歌手歌曲获取失败")
            }

            // 优先使用artists API返回的歌手信息，因为它包含完整的头像等信息
            val artistDataFromSongs = songResult.artist
            val artistDataFromDetail = detailResult.data?.artist

            // 如果歌曲API返回了歌手信息，优先使用它；否则使用详情API的数据
            val artistData = if (artistDataFromSongs != null && artistDataFromSongs.picUrl.isNotEmpty()) {
                artistDataFromSongs
            } else {
                artistDataFromDetail
            }
            val songs = songResult.songs

            _artistData.value = artistData
            _songList.value = songs
            CommonResult.success(Unit)
        }
    }

    suspend fun toggleSubscribe(): CommonResult<Unit> {
        val artistData = _artistData.value ?: return CommonResult.fail(msg = "歌手数据不存在")

        return kotlin.runCatching {
            if (_isSubscribed.value) {
                val res = apiCall {
                    SearchApi.get().subscribeArtist(artistData.id, 0)
                }
                if (res.isSuccess()) {
                    _isSubscribed.value = false
                    CommonResult.success(Unit)
                } else {
                    CommonResult.fail(res.code, res.msg)
                }
            } else {
                val res = apiCall {
                    SearchApi.get().subscribeArtist(artistData.id, 1)
                }
                if (res.isSuccess()) {
                    _isSubscribed.value = true
                    CommonResult.success(Unit)
                } else {
                    CommonResult.fail(res.code, res.msg)
                }
            }
        }.getOrElse {
            CommonResult.fail(msg = it.message)
        }
    }
}
