package me.wcy.music.album.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import me.wcy.music.common.bean.AlbumData
import me.wcy.music.common.bean.SongData
import me.wcy.music.search.SearchApi
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
        // 照抄歌单详情的加载逻辑，调用真实的专辑API
        val detailRes = kotlin.runCatching {
            SearchApi.get().getAlbumDetail(albumId)
        }
        return if (detailRes.isSuccess.not() || detailRes.getOrThrow().code != 200) {
            CommonResult.fail(msg = detailRes.exceptionOrNull()?.message)
        } else {
            // 解析专辑详情数据
            val albumDetailResponse = detailRes.getOrThrow()
            // 暂时使用模拟数据，等API返回结构确定后再修改
            val albumData = AlbumData(
                id = albumId,
                name = "专辑名称",
                picUrl = ""
            )
            _albumData.value = albumData

            // 解析专辑歌曲数据
            _songList.value = emptyList() // 暂时使用空列表
            CommonResult.success(Unit)
        }
    }

    suspend fun toggleSubscribe(): CommonResult<Unit> {
        val currentSubscribed = _isSubscribed.value
        val t = if (currentSubscribed) 0 else 1 // 1为收藏，0为取消收藏

        val res = apiCall {
            SearchApi.get().subscribeAlbum(albumId, t)
        }

        return if (res.isSuccess()) {
            _isSubscribed.value = !currentSubscribed
            CommonResult.success(Unit)
        } else {
            CommonResult.fail(res.code, res.msg)
        }
    }
}
