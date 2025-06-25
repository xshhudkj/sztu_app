package me.ckn.music.voice

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.ckn.music.service.PlayerController
import me.ckn.music.service.PlayState
import me.ckn.music.service.PlayMode
import me.ckn.music.storage.preference.ConfigPreferences
import android.util.Log
import javax.inject.Inject
import android.Manifest

/**
 * WhisperPlay Music Player - 语音控制ViewModel（重构版）
 *
 * 文件描述：MVVM架构的语音控制核心逻辑，严格按照MainActivity.java实现
 * File Description: Voice control ViewModel with strict mode separation
 *
 * @author ckn
 * @since 2025-06-20
 * @version 3.0.0
 */
@HiltViewModel
class VoiceControlViewModel @Inject constructor(
    private val application: Application,
    private val playerController: PlayerController
) : ViewModel(), VoiceControlCallback {

    companion object {
        private const val TAG = "VoiceControlViewModel"
    }

    // 语音控制管理器
    private var voiceControlManager: VoiceControlManager? = null

    // 智能语义解析引擎
    private val semanticParser = IntelligentSemanticParser()

    // 状态管理
    private val _state = MutableStateFlow(VoiceControlState())
    val state: StateFlow<VoiceControlState> = _state.asStateFlow()

    // 事件流
    private val _events = MutableSharedFlow<VoiceControlEvent>()
    val events: SharedFlow<VoiceControlEvent> = _events.asSharedFlow()

    // 语音命令映射（保留作为备用，主要使用智能解析）
    private val commandMap = mapOf(
        "播放" to { executePlayCommand() },
        "暂停" to { executePauseCommand() },
        "下一首" to { executeNextCommand() },
        "上一首" to { executePrevCommand() },
        "随机播放" to { executeShuffleCommand() },
        "循环播放" to { executeRepeatCommand() },
        "增大音量" to { executeVolumeUpCommand() },
        "减小音量" to { executeVolumeDownCommand() }
    )
    
    init {
        Log.d(TAG, "VoiceControlViewModel 初始化")
        initializeVoiceControlManager()
        checkPermissions()
    }

    /**
     * 初始化语音控制管理器
     */
    private fun initializeVoiceControlManager() {
        voiceControlManager = VoiceControlManager(application, this)
        val success = voiceControlManager?.initialize() ?: false

        if (success) {
            Log.d(TAG, "语音控制管理器初始化成功")
            // 如果设置为自动开启，则启动语音功能
            if (ConfigPreferences.voiceAutoEnable) {
                enableVoiceControl()
            }
        } else {
            Log.e(TAG, "语音控制管理器初始化失败")
            updateState { copy(errorMessage = "语音功能初始化失败") }
        }
    }
    
    /**
     * 检查权限状态
     */
    private fun checkPermissions() {
        val hasRecordPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
        
        updateState { copy(hasRecordPermission = hasRecordPermission) }
        Log.d(TAG, "录音权限状态: $hasRecordPermission")
    }
    
    /**
     * 启用语音控制
     */
    fun enableVoiceControl() {
        if (!state.value.hasRecordPermission) {
            Log.w(TAG, "缺少录音权限，无法启用语音控制")
            viewModelScope.launch {
                _events.emit(VoiceControlEvent.PermissionResult(false))
            }
            return
        }

        viewModelScope.launch {
            val success = voiceControlManager?.enableVoiceControl() ?: false
            if (success) {
                updateState { copy(isVoiceEnabled = true, errorMessage = null) }
                Log.d(TAG, "语音控制已启用")
            } else {
                updateState { copy(errorMessage = "启动语音控制失败") }
                Log.e(TAG, "启动语音控制失败")
            }
        }
    }
    
    /**
     * 禁用语音控制
     */
    fun disableVoiceControl() {
        viewModelScope.launch {
            voiceControlManager?.disableVoiceControl()
            updateState {
                copy(
                    isVoiceEnabled = false,
                    isListening = false,
                    isRecognizing = false,
                    errorMessage = null
                )
            }
            Log.d(TAG, "语音控制已禁用")
        }
    }

    /**
     * 切换语音控制状态
     */
    fun toggleVoiceControl() {
        if (state.value.isVoiceEnabled) {
            disableVoiceControl()
        } else {
            enableVoiceControl()
        }
    }
    
    /**
     * 权限请求结果处理
     */
    fun onPermissionResult(granted: Boolean) {
        updateState { copy(hasRecordPermission = granted) }
        
        viewModelScope.launch {
            _events.emit(VoiceControlEvent.PermissionResult(granted))
        }
        
        if (granted && ConfigPreferences.voiceAutoEnable) {
            enableVoiceControl()
        }
        
        Log.d(TAG, "权限请求结果: $granted")
    }
    
    /**
     * 手动开始语音识别
     */
    fun startManualRecognition() {
        if (!state.value.isVoiceEnabled) {
            Log.w(TAG, "语音控制未启用")
            return
        }
        
        // 暂停音乐播放
        pauseMusicForVoiceInteraction()
        
        // 手动语音识别功能暂时保留，但主要使用自动流程
        Log.d(TAG, "手动语音识别请求（当前主要使用自动语音交互流程）")
    }
    
    /**
     * 暂停音乐播放用于语音交互（仅对话模式）
     * Pause music for voice interaction (dialog mode only)
     */
    private fun pauseMusicForVoiceInteraction() {
        val wasPlaying = playerController.playState.value == PlayState.Playing
        updateState { copy(wasMusicPlaying = wasPlaying) }

        if (wasPlaying) {
            playerController.playPause()
            Log.d(TAG, "对话模式：为语音交互暂停音乐播放")
        }
    }

    /**
     * 语音交互后智能恢复音乐播放
     * Intelligently resume music after voice interaction
     */
    private fun resumeMusicAfterVoiceInteraction() {
        if (state.value.wasMusicPlaying) {
            playerController.playPause()
            Log.d(TAG, "语音交互完成，恢复音乐播放")
        }
        updateState { copy(wasMusicPlaying = false) }
    }

    /**
     * 智能音频流控制：根据命令类型决定是否恢复播放
     * Smart audio flow control: decide whether to resume based on command type
     */
    private fun smartResumeMusic(lastCommand: String) {
        // 如果最后执行的命令是暂停相关，则不恢复播放
        val pauseCommands = listOf("暂停", "停止", "pause", "stop")
        val shouldNotResume = pauseCommands.any { lastCommand.contains(it, ignoreCase = true) }

        if (shouldNotResume) {
            Log.d(TAG, "执行了暂停命令，不恢复音乐播放")
            updateState { copy(wasMusicPlaying = false) }
        } else {
            resumeMusicAfterVoiceInteraction()
        }
    }

    /**
     * 执行语音命令（使用智能语义解析）
     */
    private fun executeCommand(command: String) {
        Log.d(TAG, "执行语音命令: $command")

        // 构建语音上下文
        val context = VoiceContext(
            currentPage = "playing",
            isPlaying = playerController.playState.value == PlayState.Playing,
            currentSong = playerController.currentSong.value?.mediaMetadata?.title?.toString() ?: "",
            playMode = playerController.playMode.value.toString()
        )

        // 使用智能语义解析引擎解析命令
        val parseResult = semanticParser.parseCommand(command, context)
        Log.d(TAG, "智能语义解析结果: ${parseResult.command}, 置信度: ${parseResult.confidence}")

        if (parseResult.success && parseResult.confidence >= 0.6f) {
            // 执行解析出的命令
            executeIntelligentCommand(parseResult.command!!, parseResult.parameters)
            updateState { copy(lastCommand = command, errorMessage = null) }
        } else {
            // 智能解析失败，尝试传统匹配作为备用
            val action = commandMap.entries.find { (key, _) ->
                command.contains(key)
            }?.value

            if (action != null) {
                action.invoke()
                updateState { copy(lastCommand = command, errorMessage = null) }
                Log.d(TAG, "使用传统匹配执行命令: $command")
            } else {
                Log.w(TAG, "未识别的语音命令: $command")
                viewModelScope.launch {
                    voiceControlManager?.speakMessage("听不清，请再说一遍")
                }
            }
        }
    }

    /**
     * 执行直接语音命令（无需暂停音乐，使用智能语义解析）
     */
    private fun executeDirectCommand(command: String) {
        Log.d(TAG, "执行直接语音命令: $command")

        // 构建语音上下文
        val context = VoiceContext(
            currentPage = "playing",
            isPlaying = playerController.playState.value == PlayState.Playing,
            currentSong = playerController.currentSong.value?.mediaMetadata?.title?.toString() ?: "",
            playMode = playerController.playMode.value.toString()
        )

        // 使用智能语义解析引擎解析命令
        val parseResult = semanticParser.parseCommand(command, context)
        Log.d(TAG, "直接命令智能语义解析结果: ${parseResult.command}, 置信度: ${parseResult.confidence}")

        if (parseResult.success && parseResult.confidence >= 0.6f) {
            // 执行解析出的命令
            executeIntelligentCommand(parseResult.command!!, parseResult.parameters)
            updateState { copy(lastCommand = command, errorMessage = null) }
            Log.d(TAG, "智能解析直接命令执行成功: $command")
        } else {
            // 智能解析失败，尝试传统匹配作为备用
            val action = commandMap.entries.find { (key, _) ->
                command.contains(key)
            }?.value

            if (action != null) {
                action.invoke()
                updateState { copy(lastCommand = command, errorMessage = null) }
                Log.d(TAG, "传统匹配直接命令执行成功: $command")
            } else {
                Log.w(TAG, "未识别的直接语音命令: $command")
                // 直接命令识别失败时不播放语音提示，避免干扰
            }
        }
    }

    /**
     * 执行智能解析的命令
     */
    private fun executeIntelligentCommand(command: VoiceCommand, parameters: Map<String, String>) {
        Log.d(TAG, "执行智能命令: $command, 参数: $parameters")

        viewModelScope.launch {
            when (command) {
                // 基础播放控制
                VoiceCommand.PLAY -> executePlayCommand()
                VoiceCommand.PAUSE -> executePauseCommand()
                VoiceCommand.NEXT -> executeNextCommand()
                VoiceCommand.PREVIOUS -> executePrevCommand()

                // 音量控制
                VoiceCommand.VOLUME_UP -> executeVolumeUpCommand()
                VoiceCommand.VOLUME_DOWN -> executeVolumeDownCommand()

                // 播放模式
                VoiceCommand.SHUFFLE -> executeShuffleCommand()
                VoiceCommand.REPEAT -> executeRepeatCommand()

                // 搜索功能
                VoiceCommand.SEARCH_SONG -> {
                    val query = parameters["query"] ?: ""
                    _events.emit(VoiceControlEvent.SearchAndPlaySong(query))
                }
                VoiceCommand.SEARCH_ARTIST -> {
                    val query = parameters["query"] ?: ""
                    _events.emit(VoiceControlEvent.SearchAndPlayArtist(query))
                }

                // 页面导航
                VoiceCommand.NAVIGATE_SEARCH -> {
                    val query = parameters["query"] ?: ""
                    _events.emit(VoiceControlEvent.NavigateToSearch(query))
                }
                VoiceCommand.NAVIGATE_HOME -> _events.emit(VoiceControlEvent.NavigateToHome)
                VoiceCommand.NAVIGATE_SETTINGS -> _events.emit(VoiceControlEvent.NavigateToSettings)

                // 播放列表管理
                VoiceCommand.SHOW_PLAYLIST -> _events.emit(VoiceControlEvent.ShowPlaylist)
                VoiceCommand.ADD_TO_FAVORITE -> _events.emit(VoiceControlEvent.AddToFavorite)
                VoiceCommand.DOWNLOAD_SONG -> _events.emit(VoiceControlEvent.DownloadSong)
                VoiceCommand.SHOW_LYRICS -> _events.emit(VoiceControlEvent.ShowLyrics)
            }
        }
    }



    // 具体命令执行方法
    private fun executePlayCommand() {
        if (playerController.playState.value != PlayState.Playing) {
            playerController.playPause()
        }
        Log.d(TAG, "执行播放命令")
    }

    private fun executePauseCommand() {
        if (playerController.playState.value == PlayState.Playing) {
            playerController.playPause()
        }
        Log.d(TAG, "执行暂停命令")
    }

    private fun executeNextCommand() {
        playerController.next()
        Log.d(TAG, "执行下一首命令")
    }

    private fun executePrevCommand() {
        playerController.prev()
        Log.d(TAG, "执行上一首命令")
    }

    private fun executeShuffleCommand() {
        try {
            val currentMode = playerController.playMode.value
            Log.d(TAG, "当前播放模式: $currentMode")

            // 设置随机播放模式
            playerController.setPlayMode(PlayMode.Shuffle)
            // 严格按照MainActivity.java实现：立即播放下一首（随机）
            playerController.next()
            Log.d(TAG, "执行随机播放命令: $currentMode -> ${PlayMode.Shuffle}，已随机换歌")

            // 语音反馈（仅在对话模式下）
            if (state.value.isRecognizing || state.value.isSpeaking) {
                viewModelScope.launch {
                    voiceControlManager?.speakMessage("已开启随机播放")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "执行随机播放命令失败", e)
            if (state.value.isRecognizing || state.value.isSpeaking) {
                viewModelScope.launch {
                    voiceControlManager?.speakMessage("随机播放设置失败")
                }
            }
        }
    }

    private fun executeRepeatCommand() {
        try {
            val currentMode = playerController.playMode.value
            Log.d(TAG, "当前播放模式: $currentMode")

            // 设置循环播放模式
            playerController.setPlayMode(PlayMode.Loop)
            Log.d(TAG, "执行循环播放命令: $currentMode -> ${PlayMode.Loop}")

            // 语音反馈（仅在对话模式下）
            if (state.value.isRecognizing || state.value.isSpeaking) {
                viewModelScope.launch {
                    voiceControlManager?.speakMessage("已开启循环播放")
                }
            }

            // 如果当前没有播放，则开始播放
            if (playerController.playState.value != PlayState.Playing) {
                playerController.playPause()
                Log.d(TAG, "循环播放：开始播放音乐")
            }
        } catch (e: Exception) {
            Log.e(TAG, "执行循环播放命令失败", e)
            if (state.value.isRecognizing || state.value.isSpeaking) {
                viewModelScope.launch {
                    voiceControlManager?.speakMessage("循环播放设置失败")
                }
            }
        }
    }

    private fun executeVolumeUpCommand() {
        try {
            val audioManager = application.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

            Log.d(TAG, "当前音量: $currentVolume/$maxVolume")

            if (currentVolume < maxVolume) {
                // 增加音量，步长为2以获得更明显的效果
                val newVolume = minOf(currentVolume + 2, maxVolume)
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    newVolume,
                    AudioManager.FLAG_SHOW_UI or AudioManager.FLAG_PLAY_SOUND
                )

                // 计算音量百分比
                val volumePercent = (newVolume * 100) / maxVolume
                Log.d(TAG, "执行增大音量命令: $currentVolume -> $newVolume (${volumePercent}%)")

                // 语音反馈（仅在对话模式下）
                if (state.value.isRecognizing || state.value.isSpeaking) {
                    viewModelScope.launch {
                        voiceControlManager?.speakMessage("音量已调至${volumePercent}%")
                    }
                }
            } else {
                Log.d(TAG, "音量已达最大值: $currentVolume/$maxVolume")
                if (state.value.isRecognizing || state.value.isSpeaking) {
                    viewModelScope.launch {
                        voiceControlManager?.speakMessage("音量已经最大")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "增大音量失败", e)
            if (state.value.isRecognizing || state.value.isSpeaking) {
                viewModelScope.launch {
                    voiceControlManager?.speakMessage("音量调节失败")
                }
            }
        }
    }

    private fun executeVolumeDownCommand() {
        try {
            val audioManager = application.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

            Log.d(TAG, "当前音量: $currentVolume/$maxVolume")

            if (currentVolume > 0) {
                // 减少音量，步长为2以获得更明显的效果
                val newVolume = maxOf(currentVolume - 2, 0)
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    newVolume,
                    AudioManager.FLAG_SHOW_UI or AudioManager.FLAG_PLAY_SOUND
                )

                // 计算音量百分比
                val volumePercent = (newVolume * 100) / maxVolume
                Log.d(TAG, "执行减小音量命令: $currentVolume -> $newVolume (${volumePercent}%)")

                // 语音反馈（仅在对话模式下）
                if (state.value.isRecognizing || state.value.isSpeaking) {
                    viewModelScope.launch {
                        voiceControlManager?.speakMessage("音量已调至${volumePercent}%")
                    }
                }
            } else {
                Log.d(TAG, "音量已达最小值: $currentVolume/$maxVolume")
                if (state.value.isRecognizing || state.value.isSpeaking) {
                    viewModelScope.launch {
                        voiceControlManager?.speakMessage("音量已经最小")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "减小音量失败", e)
            if (state.value.isRecognizing || state.value.isSpeaking) {
                viewModelScope.launch {
                    voiceControlManager?.speakMessage("音量调节失败")
                }
            }
        }
    }

    // 高级功能命令执行方法
    private fun executeSearchCommand(query: String) {
        Log.d(TAG, "执行搜索命令: $query")
        if (query.isNotEmpty()) {
            viewModelScope.launch {
                voiceControlManager?.speakMessage("正在搜索$query")
            }
            // 实现搜索功能，跳转到搜索页面并执行搜索
            viewModelScope.launch {
                _events.emit(VoiceControlEvent.NavigateToSearch(query))
            }
        } else {
            viewModelScope.launch {
                voiceControlManager?.speakMessage("请说出要搜索的歌曲名")
            }
        }
    }

    private fun executeOpenSearchCommand() {
        Log.d(TAG, "执行打开搜索页面命令")
        viewModelScope.launch {
            voiceControlManager?.speakMessage("打开搜索页面")
        }
        viewModelScope.launch {
            _events.emit(VoiceControlEvent.NavigateToSearch(""))
        }
    }

    private fun executeBackToHomeCommand() {
        Log.d(TAG, "执行返回首页命令")
        viewModelScope.launch {
            voiceControlManager?.speakMessage("返回首页")
        }
        viewModelScope.launch {
            _events.emit(VoiceControlEvent.NavigateToHome)
        }
    }

    private fun executeOpenSettingsCommand() {
        Log.d(TAG, "执行打开设置页面命令")
        viewModelScope.launch {
            voiceControlManager?.speakMessage("打开设置页面")
        }
        viewModelScope.launch {
            _events.emit(VoiceControlEvent.NavigateToSettings)
        }
    }

    private fun executeShowPlaylistCommand() {
        Log.d(TAG, "执行显示播放列表命令")
        viewModelScope.launch {
            voiceControlManager?.speakMessage("显示播放列表")
        }
        viewModelScope.launch {
            _events.emit(VoiceControlEvent.ShowPlaylist)
        }
    }

    private fun executeFavoriteCommand() {
        Log.d(TAG, "执行添加到收藏命令")
        viewModelScope.launch {
            voiceControlManager?.speakMessage("已添加到收藏")
        }
        // TODO: 实现收藏功能
        viewModelScope.launch {
            _events.emit(VoiceControlEvent.AddToFavorite)
        }
    }

    private fun executeDownloadCommand() {
        Log.d(TAG, "执行下载歌曲命令")
        viewModelScope.launch {
            voiceControlManager?.speakMessage("开始下载当前歌曲")
        }
        // TODO: 实现下载功能
        viewModelScope.launch {
            _events.emit(VoiceControlEvent.DownloadSong)
        }
    }

    private fun executeShowLyricsCommand() {
        Log.d(TAG, "执行显示歌词命令")
        viewModelScope.launch {
            voiceControlManager?.speakMessage("显示歌词")
        }
        viewModelScope.launch {
            _events.emit(VoiceControlEvent.ShowLyrics)
        }
    }

    /**
     * 更新状态的辅助方法
     */
    private fun updateState(update: VoiceControlState.() -> VoiceControlState) {
        _state.value = _state.value.update()
    }
    
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "VoiceControlViewModel 清理资源")
        voiceControlManager?.release()
        voiceControlManager = null
    }

    // VoiceControlCallback 接口实现
    override fun onModeChanged(mode: VoiceControlMode) {
        when (mode) {
            VoiceControlMode.DISABLED -> {
                updateState { copy(isVoiceEnabled = false, isListening = false, isRecognizing = false) }
            }
            VoiceControlMode.VOICE_WAKEUP -> {
                updateState { copy(isListening = true, isRecognizing = false) }
            }
            VoiceControlMode.VOICE_RECOGNITION -> {
                updateState { copy(isRecognizing = true) }
            }
            VoiceControlMode.VOICE_SYNTHESIS -> {
                updateState { copy(isSpeaking = true) }
            }
        }
        Log.d(TAG, "语音控制模式变化: $mode")
    }

    override fun onWakeWordDetected(word: String) {
        Log.d(TAG, "检测到对话唤醒词: $word")
        viewModelScope.launch {
            _events.emit(VoiceControlEvent.WakeWordDetected(word))
        }
    }

    override fun onDirectCommandRecognized(command: String) {
        Log.d(TAG, "检测到操作唤醒词，直接执行: $command")
        executeDirectCommand(command)
    }

    override fun onCommandRecognized(command: String) {
        Log.d(TAG, "对话模式识别到命令: $command")
        executeCommand(command)
    }

    override fun onRecognitionFailed(error: String) {
        Log.w(TAG, "语音识别失败: $error")
        viewModelScope.launch {
            voiceControlManager?.speakMessage(error)
        }
    }

    override fun onSpeechSynthesisStateChanged(isSpeaking: Boolean) {
        updateState { copy(isSpeaking = isSpeaking) }
        Log.d(TAG, "语音合成状态变化: $isSpeaking")
    }

    override fun onSpeechSynthesisCompleted() {
        updateState { copy(isSpeaking = false) }
        Log.d(TAG, "语音合成完成")
    }

    override fun onError(error: String) {
        Log.e(TAG, "语音控制错误: $error")
        updateState { copy(errorMessage = error) }
    }

    override fun isMusicPlaying(): Boolean {
        return playerController.playState.value == PlayState.Playing
    }

    override fun pauseMusic() {
        if (playerController.playState.value == PlayState.Playing) {
            playerController.playPause()
        }
    }

    override fun resumeMusic() {
        if (playerController.playState.value != PlayState.Playing) {
            playerController.playPause()
        }
    }


}
