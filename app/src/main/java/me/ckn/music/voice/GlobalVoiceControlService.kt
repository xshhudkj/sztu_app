package me.ckn.music.voice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*
import me.ckn.music.service.PlayerController
import me.ckn.music.service.PlayState
import me.ckn.music.service.PlayMode
import javax.inject.Inject
import dagger.hilt.android.AndroidEntryPoint

/**
 * 全局语音控制服务
 * 
 * 确保语音控制功能在整个应用的任何位置都能正常运行
 * 不仅仅限于播放页面，而是全局可用
 * 
 * @author ckn
 * @since 2025-06-20
 * @version 1.0.0
 */
@AndroidEntryPoint
class GlobalVoiceControlService : Service(), VoiceControlCallback {
    
    companion object {
        private const val TAG = "GlobalVoiceControlService"
        const val ACTION_ENABLE_VOICE_CONTROL = "me.ckn.music.voice.ENABLE"
        const val ACTION_DISABLE_VOICE_CONTROL = "me.ckn.music.voice.DISABLE"
        const val ACTION_TOGGLE_VOICE_CONTROL = "me.ckn.music.voice.TOGGLE"
    }
    
    @Inject
    lateinit var playerController: PlayerController
    
    // 语音控制管理器
    private var voiceControlManager: VoiceControlManager? = null
    
    // 服务绑定器
    private val binder = GlobalVoiceControlBinder()
    
    // 协程作用域
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    // 语音控制状态
    private var isVoiceControlEnabled = false
    
    // 回调监听器列表
    private val callbacks = mutableListOf<GlobalVoiceControlCallback>()
    
    inner class GlobalVoiceControlBinder : Binder() {
        fun getService(): GlobalVoiceControlService = this@GlobalVoiceControlService
    }
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "全局语音控制服务创建")
        
        // 初始化语音控制管理器
        initializeVoiceControl()
    }
    
    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "服务绑定")
        return binder
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "服务启动命令: ${intent?.action}")
        
        when (intent?.action) {
            ACTION_ENABLE_VOICE_CONTROL -> enableVoiceControl()
            ACTION_DISABLE_VOICE_CONTROL -> disableVoiceControl()
            ACTION_TOGGLE_VOICE_CONTROL -> toggleVoiceControl()
        }
        
        return START_STICKY // 服务被杀死后自动重启
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "全局语音控制服务销毁")
        
        // 释放资源
        voiceControlManager?.release()
        serviceScope.cancel()
    }
    
    /**
     * 初始化语音控制
     */
    private fun initializeVoiceControl() {
        try {
            voiceControlManager = VoiceControlManager(this, this)
            val success = voiceControlManager?.initialize() ?: false
            
            if (success) {
                Log.d(TAG, "全局语音控制初始化成功")
            } else {
                Log.e(TAG, "全局语音控制初始化失败")
            }
        } catch (e: Exception) {
            Log.e(TAG, "初始化语音控制失败", e)
        }
    }
    
    /**
     * 启用语音控制
     */
    fun enableVoiceControl() {
        if (isVoiceControlEnabled) {
            Log.d(TAG, "语音控制已启用")
            return
        }
        
        serviceScope.launch {
            try {
                val success = voiceControlManager?.enableVoiceControl() ?: false
                if (success) {
                    isVoiceControlEnabled = true
                    Log.d(TAG, "全局语音控制已启用")
                    notifyCallbacks { it.onVoiceControlStateChanged(true) }
                } else {
                    Log.e(TAG, "启用全局语音控制失败")
                }
            } catch (e: Exception) {
                Log.e(TAG, "启用语音控制异常", e)
            }
        }
    }
    
    /**
     * 禁用语音控制
     */
    fun disableVoiceControl() {
        if (!isVoiceControlEnabled) {
            Log.d(TAG, "语音控制已禁用")
            return
        }
        
        serviceScope.launch {
            try {
                voiceControlManager?.disableVoiceControl()
                isVoiceControlEnabled = false
                Log.d(TAG, "全局语音控制已禁用")
                notifyCallbacks { it.onVoiceControlStateChanged(false) }
            } catch (e: Exception) {
                Log.e(TAG, "禁用语音控制异常", e)
            }
        }
    }
    
    /**
     * 切换语音控制状态
     */
    fun toggleVoiceControl() {
        if (isVoiceControlEnabled) {
            disableVoiceControl()
        } else {
            enableVoiceControl()
        }
    }
    
    /**
     * 获取语音控制状态
     */
    fun isVoiceControlEnabled(): Boolean = isVoiceControlEnabled
    
    /**
     * 注册回调监听器
     */
    fun registerCallback(callback: GlobalVoiceControlCallback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback)
            Log.d(TAG, "注册语音控制回调监听器")
        }
    }
    
    /**
     * 注销回调监听器
     */
    fun unregisterCallback(callback: GlobalVoiceControlCallback) {
        callbacks.remove(callback)
        Log.d(TAG, "注销语音控制回调监听器")
    }
    
    /**
     * 通知所有回调监听器
     */
    private fun notifyCallbacks(action: (GlobalVoiceControlCallback) -> Unit) {
        callbacks.forEach { callback ->
            try {
                action(callback)
            } catch (e: Exception) {
                Log.e(TAG, "回调通知失败", e)
            }
        }
    }
    
    // VoiceControlCallback 接口实现
    override fun onModeChanged(mode: VoiceControlMode) {
        Log.d(TAG, "全局语音控制模式变化: $mode")
        notifyCallbacks { it.onVoiceControlModeChanged(mode) }
    }
    
    override fun onWakeWordDetected(word: String) {
        Log.d(TAG, "全局检测到对话唤醒词: $word")
        notifyCallbacks { it.onWakeWordDetected(word) }
    }
    
    override fun onDirectCommandRecognized(command: String) {
        Log.d(TAG, "全局检测到操作唤醒词，直接执行: $command")
        executeDirectCommand(command)
        notifyCallbacks { it.onDirectCommandRecognized(command) }
    }
    
    override fun onCommandRecognized(command: String) {
        Log.d(TAG, "全局对话模式识别到命令: $command")
        executeCommand(command)
        notifyCallbacks { it.onCommandRecognized(command) }
    }
    
    override fun onRecognitionFailed(error: String) {
        Log.w(TAG, "全局语音识别失败: $error")
        serviceScope.launch {
            voiceControlManager?.speakMessage(error)
        }
        notifyCallbacks { it.onRecognitionFailed(error) }
    }
    
    override fun onSpeechSynthesisStateChanged(isSpeaking: Boolean) {
        Log.d(TAG, "全局语音合成状态变化: $isSpeaking")
        notifyCallbacks { it.onSpeechSynthesisStateChanged(isSpeaking) }
    }
    
    override fun onSpeechSynthesisCompleted() {
        Log.d(TAG, "全局语音合成完成")
        notifyCallbacks { it.onSpeechSynthesisCompleted() }
    }
    
    override fun onError(error: String) {
        Log.e(TAG, "全局语音控制错误: $error")
        notifyCallbacks { it.onError(error) }
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
    
    /**
     * 执行直接命令（操作唤醒词）
     * 严格按照MainActivity.java实现
     */
    private fun executeDirectCommand(command: String) {
        when (command.lowercase()) {
            "播放" -> {
                if (playerController.playState.value != PlayState.Playing) {
                    playerController.playPause()
                }
            }
            "暂停" -> {
                if (playerController.playState.value == PlayState.Playing) {
                    playerController.playPause()
                }
            }
            "下一首" -> playerController.next()
            "上一首" -> playerController.prev()
            "随机播放" -> {
                // 按照MainActivity.java实现：切换到随机模式并立即随机换歌
                playerController.setPlayMode(PlayMode.Shuffle)
                // 立即播放下一首（随机）
                playerController.next()
                Log.d(TAG, "执行随机播放：已切换到随机模式并换歌")
            }
            "循环播放" -> {
                // 按照MainActivity.java实现：切换到循环模式
                playerController.setPlayMode(PlayMode.Loop)
                // 如果当前没有播放，则开始播放
                if (playerController.playState.value != PlayState.Playing) {
                    playerController.playPause()
                }
                Log.d(TAG, "执行循环播放：已切换到循环模式")
            }
            "增大音量" -> {
                // TODO: 实现音量控制
                Log.d(TAG, "执行增大音量命令")
            }
            "减小音量" -> {
                // TODO: 实现音量控制
                Log.d(TAG, "执行减小音量命令")
            }
        }
    }
    
    /**
     * 执行对话模式命令
     * 严格按照MainActivity.java的processVoiceCommand实现
     */
    private fun executeCommand(command: String) {
        val cmd = command.lowercase()

        when {
            cmd.contains("播放") && !cmd.contains("随机") && !cmd.contains("循环") -> {
                if (playerController.playState.value != PlayState.Playing) {
                    playerController.playPause()
                }
            }
            cmd.contains("暂停") -> {
                if (playerController.playState.value == PlayState.Playing) {
                    playerController.playPause()
                }
            }
            cmd.contains("下一首") || cmd.contains("下一曲") -> {
                playerController.next()
            }
            cmd.contains("上一首") || cmd.contains("上一曲") -> {
                playerController.prev()
            }
            cmd.contains("随机") && cmd.contains("播放") -> {
                // 随机播放：切换模式并立即随机换歌
                playerController.setPlayMode(PlayMode.Shuffle)
                playerController.next()
                Log.d(TAG, "对话模式-随机播放：已切换到随机模式并换歌")
            }
            (cmd.contains("单曲") || cmd.contains("循环")) && cmd.contains("播放") -> {
                // 循环播放：切换模式并确保播放
                playerController.setPlayMode(PlayMode.Loop)
                if (playerController.playState.value != PlayState.Playing) {
                    playerController.playPause()
                }
                Log.d(TAG, "对话模式-循环播放：已切换到循环模式")
            }
            else -> {
                Log.w(TAG, "未识别的对话命令: $command")
            }
        }
    }
}

/**
 * 全局语音控制回调接口
 */
interface GlobalVoiceControlCallback {
    fun onVoiceControlStateChanged(enabled: Boolean)
    fun onVoiceControlModeChanged(mode: VoiceControlMode)
    fun onWakeWordDetected(word: String)
    fun onDirectCommandRecognized(command: String)
    fun onCommandRecognized(command: String)
    fun onRecognitionFailed(error: String)
    fun onSpeechSynthesisStateChanged(isSpeaking: Boolean)
    fun onSpeechSynthesisCompleted()
    fun onError(error: String)
}
