package me.ckn.music.album.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import me.ckn.music.common.bean.AlbumData
import me.ckn.music.common.bean.SongData
import me.ckn.music.search.SearchApi
import top.wangchenyan.common.ext.toUnMutable
import top.wangchenyan.common.model.CommonResult
import top.wangchenyan.common.net.apiCall
import javax.inject.Inject

/**
 * 专辑详情ViewModel
 * Created by wangchenyan.top on 2024/12/20.
 */
@HiltViewModel
class AlbumDetailViewModel @Inject constructor() : ViewModel() {

    private val _albumData = MutableStateFlow<AlbumData?>(null)
    val albumData = _albumData.toUnMutable()

    private val _songList = MutableStateFlow<List<SongData>>(emptyList())
    val songList = _songList.toUnMutable()

    private val _isSubscribed = MutableStateFlow(false)
    val isSubscribed = _isSubscribed.toUnMutable()

    private var albumId = 0L

    fun init(albumId: Long) {
        this.albumId = albumId
    }

    suspend fun loadData(): CommonResult<Unit> {
        // 参考歌单详情页的安全处理方式，使用kotlin.runCatching
        val detailRes = kotlin.runCatching {
            SearchApi.get().getAlbumDetail(albumId)
        }

        return if (detailRes.isSuccess.not()) {
            CommonResult.fail(msg = detailRes.exceptionOrNull()?.message)
        } else {
            // 安全地获取数据，参考歌单详情页的处理方式
            val detailResult = detailRes.getOrThrow()

            // 检查API返回的code状态
            if (detailResult.code != 200) {
                return CommonResult.fail(detailResult.code, "专辑详情获取失败")
            }

            // 直接从API返回结果中获取数据
            val albumData = detailResult.album
            val songs = detailResult.songs

            // 专辑详情页统一使用专辑封面作为所有歌曲的封面
            // 确保视觉一致性，避免个别歌曲封面缺失或错误的问题
            val fixedSongs = if (albumData != null && albumData.picUrl.isNotEmpty()) {
                songs.map { song ->
                    // 统一使用专辑封面替换歌曲的专辑封面
                    song.copy(
                        al = song.al.copy(
                            id = albumData.id,
                            name = albumData.name,
                            picUrl = albumData.picUrl,
                            pic = albumData.pic,
                            picStr = albumData.picStr // 保持哈希值信息
                        )
                    )
                }
            } else {
                songs
            }

            _albumData.value = albumData
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
        val albumData = _albumData.value ?: return

        kotlin.runCatching {
            // 这里需要调用获取用户收藏专辑列表的API来检查是否已收藏
            // 由于API文档中没有明确的检查单个专辑收藏状态的接口
            // 暂时设置为false，实际项目中需要根据具体API实现
            _isSubscribed.value = false
        }.onFailure {
            _isSubscribed.value = false
        }
    }

    suspend fun toggleSubscribe(): CommonResult<Unit> {
        val albumData = _albumData.value ?: return CommonResult.fail(msg = "专辑数据不存在")

        return kotlin.runCatching {
            if (_isSubscribed.value) {
                val res = apiCall {
                    SearchApi.get().subscribeAlbum(albumData.id, 0)
                }
                if (res.isSuccess()) {
                    _isSubscribed.value = false
                    CommonResult.success(Unit)
                } else {
                    CommonResult.fail(res.code, res.msg)
                }
            } else {
                val res = apiCall {
                    SearchApi.get().subscribeAlbum(albumData.id, 1)
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
