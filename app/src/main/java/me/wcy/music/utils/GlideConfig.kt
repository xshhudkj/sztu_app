package me.wcy.music.utils

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

/**
 * Glide配置类
 * 优化图片加载性能，减少内存占用
 * Created by wangchenyan.top on 2024/12/20.
 */
@GlideModule
class GlideConfig : AppGlideModule() {
    
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // 内存缓存配置
        val calculator = MemorySizeCalculator.Builder(context)
            .setMemoryCacheScreens(3f) // 设置内存缓存大小为3个屏幕的图片
            .setBitmapPoolScreens(2f) // 设置Bitmap池大小为2个屏幕
            .build()
        
        // 设置内存缓存
        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))
        
        // 设置Bitmap池
        builder.setBitmapPool(LruBitmapPool(calculator.bitmapPoolSize.toLong()))
        
        // 磁盘缓存配置：100MB
        builder.setDiskCache(
            InternalCacheDiskCacheFactory(context, "glide_cache", 100 * 1024 * 1024)
        )
        
        // 默认请求配置
        builder.setDefaultRequestOptions(
            RequestOptions()
                .format(DecodeFormat.PREFER_RGB_565) // 使用RGB_565减少内存占用
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // 缓存转换后的资源
                .skipMemoryCache(false) // 启用内存缓存
        )
        
        // 设置日志级别（生产环境应该关闭）
        builder.setLogLevel(android.util.Log.ERROR)
    }
    
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        // 可以在这里注册自定义的组件
    }
    
    override fun isManifestParsingEnabled(): Boolean {
        // 禁用清单解析，提高初始化速度
        return false
    }
} 