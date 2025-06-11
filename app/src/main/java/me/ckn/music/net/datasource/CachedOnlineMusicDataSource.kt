/*
 * Copyright (C) 2024 ckn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.ckn.music.net.datasource

import android.content.Context
import android.net.Uri
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.BaseDataSource
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.TransferListener
import me.ckn.music.utils.LogUtils
import java.io.IOException

/**
 * 支持缓存的在线音乐数据源
 * 
 * 这个数据源结合了URL获取和音频缓存功能：
 * 1. 首先通过OnlineMusicUriFetcher获取真实的播放URL
 * 2. 然后使用缓存数据源（CacheDataSource）来处理HTTP请求和缓存
 * 
 * 这样既保持了原有的URL获取逻辑，又实现了音频数据的缓存。
 */
@UnstableApi
class CachedOnlineMusicDataSource(
    private val context: Context,
    private val baseDataSource: DataSource // 这个实际上不是缓存数据源，我们需要自己创建
) : BaseDataSource(/* isNetwork= */ true) {

    private var currentDataSource: DataSource? = null
    private var opened = false

    // 注意：addTransferListener在BaseDataSource中是final的，不能重写
    // 我们在构造时就将监听器添加到缓存数据源中

    @Throws(IOException::class)
    override fun open(dataSpec: DataSpec): Long {
        if (opened) {
            throw IllegalStateException("DataSource is already opened")
        }

        try {
            // 1. 获取真实的播放URL
            val realUrl = OnlineMusicUriFetcher.fetchPlayUrl(dataSpec.uri)
            if (realUrl.isNullOrEmpty()) {
                throw IOException("Failed to fetch real play URL for: ${dataSpec.uri}")
            }

            // 2. 创建新的DataSpec，使用真实URL
            val realDataSpec = dataSpec.buildUpon()
                .setUri(Uri.parse(realUrl))
                .build()

            // 3. 创建新的缓存数据源实例来处理这个请求
            val cacheDataSourceFactory = ModernMusicCacheDataSourceFactory.getCacheDataSourceFactory(context)
            currentDataSource = cacheDataSourceFactory.createDataSource()

            val result = currentDataSource!!.open(realDataSpec)

            opened = true
            transferInitializing(dataSpec)
            transferStarted(dataSpec)

            return result

        } catch (e: Exception) {
            close()
            throw IOException("Failed to open cached online music data source", e)
        }
    }

    @Throws(IOException::class)
    override fun read(buffer: ByteArray, offset: Int, length: Int): Int {
        if (!opened) {
            throw IllegalStateException("DataSource is not opened")
        }
        
        val currentSource = currentDataSource ?: throw IOException("No current data source")
        val bytesRead = currentSource.read(buffer, offset, length)
        
        if (bytesRead > 0) {
            bytesTransferred(bytesRead)
        }
        
        return bytesRead
    }

    override fun getUri(): Uri? {
        return currentDataSource?.uri
    }

    @Throws(IOException::class)
    override fun close() {
        if (opened) {
            opened = false
            transferEnded()
            
            currentDataSource?.let { source ->
                try {
                    source.close()
                } catch (e: IOException) {
                    // 忽略关闭时的错误
                } finally {
                    currentDataSource = null
                }
            }
        }
    }
}
