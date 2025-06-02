package me.wcy.music.storage;

import androidx.media3.common.MediaItem;
import kotlinx.coroutines.Dispatchers;
import me.wcy.music.consts.FilePath;
import java.io.File;

/**
 * Created by wangchenyan.top on 2023/9/18.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0004H\u0086@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lme/wcy/music/storage/LrcCache;", "", "()V", "getLrcFilePath", "", "music", "Landroidx/media3/common/MediaItem;", "saveLrcFile", "Ljava/io/File;", "content", "(Landroidx/media3/common/MediaItem;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class LrcCache {
    @org.jetbrains.annotations.NotNull()
    public static final me.wcy.music.storage.LrcCache INSTANCE = null;
    
    private LrcCache() {
        super();
    }
    
    /**
     * 获取歌词路径
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLrcFilePath(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem music) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveLrcFile(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.MediaItem music, @org.jetbrains.annotations.NotNull()
    java.lang.String content, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.io.File> $completion) {
        return null;
    }
}