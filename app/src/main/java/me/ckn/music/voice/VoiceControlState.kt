package me.ckn.music.voice

/**
 * WhisperPlay Music Player - 语音控制状态数据类
 *
 * 文件描述：语音控制功能的状态管理
 * File Description: Voice control state management
 *
 * @author ckn
 * @since 2025-06-20
 * @version 2.3.0
 */
data class VoiceControlState(
    /**
     * 语音功能是否启用
     * Whether voice control is enabled
     */
    val isVoiceEnabled: Boolean = false,
    
    /**
     * 是否正在监听唤醒词
     * Whether listening for wake words
     */
    val isListening: Boolean = false,
    
    /**
     * 是否正在进行语音识别
     * Whether speech recognition is in progress
     */
    val isRecognizing: Boolean = false,
    
    /**
     * 是否正在播放语音合成
     * Whether text-to-speech is playing
     */
    val isSpeaking: Boolean = false,
    
    /**
     * 最后识别的语音命令
     * Last recognized voice command
     */
    val lastCommand: String = "",
    
    /**
     * 错误信息
     * Error message
     */
    val errorMessage: String? = null,
    
    /**
     * 语音交互前的音乐播放状态
     * Music playing state before voice interaction
     */
    val wasMusicPlaying: Boolean = false,
    
    /**
     * 权限状态
     * Permission status
     */
    val hasRecordPermission: Boolean = false
)

/**
 * 语音控制事件
 * Voice control events
 */
sealed class VoiceControlEvent {
    /**
     * 唤醒词检测成功
     * Wake word detected
     */
    data class WakeWordDetected(val word: String) : VoiceControlEvent()
    
    /**
     * 对话模式语音命令识别成功（需要先唤醒）
     * Dialog mode voice command recognized (requires wake word first)
     */
    data class CommandRecognized(val command: String) : VoiceControlEvent()

    /**
     * 直接命令识别成功（无需唤醒词）
     * Direct command recognized (no wake word required)
     */
    data class DirectCommandRecognized(val command: String) : VoiceControlEvent()
    
    /**
     * 语音识别失败
     * Speech recognition failed
     */
    data class RecognitionFailed(val error: String) : VoiceControlEvent()
    
    /**
     * 权限请求结果
     * Permission request result
     */
    data class PermissionResult(val granted: Boolean) : VoiceControlEvent()
    
    /**
     * 语音合成完成
     * Text-to-speech completed
     */
    object SpeechSynthesisCompleted : VoiceControlEvent()

    // 新增的高级功能事件

    /**
     * 导航到搜索页面
     * Navigate to search page
     */
    data class NavigateToSearch(val query: String) : VoiceControlEvent()

    /**
     * 导航到首页
     * Navigate to home page
     */
    object NavigateToHome : VoiceControlEvent()

    /**
     * 导航到设置页面
     * Navigate to settings page
     */
    object NavigateToSettings : VoiceControlEvent()

    /**
     * 显示播放列表
     * Show playlist
     */
    object ShowPlaylist : VoiceControlEvent()

    /**
     * 添加到收藏
     * Add to favorite
     */
    object AddToFavorite : VoiceControlEvent()

    /**
     * 下载歌曲
     * Download song
     */
    object DownloadSong : VoiceControlEvent()

    /**
     * 显示歌词
     * Show lyrics
     */
    object ShowLyrics : VoiceControlEvent()

    /**
     * 搜索并播放歌曲
     * Search and play song
     */
    data class SearchAndPlaySong(val songName: String) : VoiceControlEvent()

    /**
     * 搜索并播放歌手的歌曲
     * Search and play artist's songs
     */
    data class SearchAndPlayArtist(val artistName: String) : VoiceControlEvent()
}
