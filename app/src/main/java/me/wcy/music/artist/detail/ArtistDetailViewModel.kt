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

            // 歌手详情页统一使用歌手头像作为歌曲封面
            // 因为歌手歌曲API返回的专辑封面信息往往不完整，容易出现404错误
            val artistCover = artistData?.picUrl ?: ""
            val fixedSongs = if (artistCover.isNotEmpty()) {
                songs.map { song ->
                    // 统一使用歌手头像替换专辑封面
                    song.copy(
                        al = song.al.copy(
                            picUrl = artistCover,
                            // 保持原有的专辑信息，只替换封面
                            pic = 0, // 清空pic字段，避免构建错误的URL
                            picStr = "" // 清空picStr字段，直接使用完整的picUrl
                        )
                    )
                }
            } else {
                // 如果歌手头像也没有，保持原状
                songs
            }

            _artistData.value = artistData
            _songList.value = fixedSongs

            // 加载收藏状态
            loadSubscribeState()

            CommonResult.success(Unit)
        }
    }

    /**
     * 加载收藏状态
     */
    private suspend fun loadSubscribeState() {
        val artistData = _artistData.value ?: return

        kotlin.runCatching {
            // 这里需要调用获取用户收藏歌手列表的API来检查是否已收藏
            // 由于API文档中没有明确的检查单个歌手收藏状态的接口
            // 暂时设置为false，实际项目中需要根据具体API实现
            _isSubscribed.value = false
        }.onFailure {
            _isSubscribed.value = false
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
