package me.ckn.music.voice

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import com.baidu.speech.EventListener
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.baidu.tts.client.SpeechError
import com.baidu.tts.client.SpeechSynthesizer
import com.baidu.tts.client.SpeechSynthesizerListener
import com.baidu.tts.client.TtsMode
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.json.JSONObject
import java.util.*

/**
 * 语音控制管理器
 * 严格按照MainActivity.java的逻辑实现
 * 
 * @author ckn  
 * @since 2025-06-20
 * @version 2.0.0
 */
class VoiceControlManager(
    private val context: Context,
    private val callback: VoiceControlCallback
) {
    companion object {
        private const val TAG = "VoiceControlManager"
        
        // 百度语音配置
        private const val APP_ID = "118558442"
        private const val API_KEY = "l07tTLiM8XdSVcM6Avmv5FG3"
        private const val SECRET_KEY = "e4DxN5gewACp162txczyVRuJs4UGBhdb"
        private const val WAKEUP_FILE = "WakeUp.bin"
        
        // 超时设置（严格按照MainActivity.java）
        private const val RECOGNITION_TIMEOUT = 8000L // 8秒超时
    }
    
    // 语音引擎
    private var wakeupManager: EventManager? = null
    private var asrManager: EventManager? = null 
    private var speechSynthesizer: SpeechSynthesizer? = null
    
    // 状态管理（严格按照MainActivity.java）
    private var isWakeupActive = false
    private var isRecognizing = false
    private var isSpeaking = false
    private var isHandlingVoiceCommand = false  // 防止重复处理
    private var wasMusicPlaying = false
    
    // 超时处理
    private val recognitionTimeoutHandler = Handler(Looper.getMainLooper())
    private var recognitionTimeoutRunnable: Runnable? = null
    
    // 语音合成回调
    private var currentSpeechCallback: Runnable? = null
    
    // 线程安全
    private val stateLock = Mutex()

    /**
     * 初始化语音控制
     */
    suspend fun initialize(): Boolean = stateLock.withLock {
        try {
            Log.d(TAG, "开始初始化语音控制")
            
            // 初始化语音唤醒
            initializeWakeup()
            
            // 初始化语音识别  
            initializeASR()
            
            // 初始化语音合成
            initializeSpeechSynthesizer()
            
            Log.d(TAG, "语音控制初始化完成")
            true
        } catch (e: Exception) {
            Log.e(TAG, "语音控制初始化失败", e)
            false
        }
    }

    /**
     * 启用语音控制
     */
    suspend fun enableVoiceControl(): Boolean = stateLock.withLock {
        if (isWakeupActive) {
            Log.d(TAG, "语音控制已启用")
            return true
        }
        
        return startWakeup()
    }

    /**
     * 禁用语音控制  
     */
    suspend fun disableVoiceControl() = stateLock.withLock {
        Log.d(TAG, "禁用语音控制")
        
        // 先停止语音播报
        stopSpeechSynthesis()
        
        // 停止语音识别
        stopRecognition()
        
        // 停止语音唤醒
        stopWakeup()
        
        // 重置状态
        isHandlingVoiceCommand = false
        
        // 恢复音乐播放
        resumeMusicIfNeeded()
    }

    /**
     * 释放资源
     */
    fun release() {
        Log.d(TAG, "释放语音控制资源")
        
        // 停止所有功能
        recognitionTimeoutHandler.removeCallbacks(recognitionTimeoutRunnable ?: return)
        
        // 释放语音合成
        speechSynthesizer?.let {
            if (isSpeaking) it.stop()
            it.release()
        }
        
        // 释放语音识别
        asrManager?.send(SpeechConstant.ASR_CANCEL, null, null, 0, 0)
        
        // 释放语音唤醒
        wakeupManager?.send(SpeechConstant.WAKEUP_STOP, null, null, 0, 0)
    }

    /**
     * 初始化语音唤醒（严格按照MainActivity.java）
     */
    private fun initializeWakeup() {
        wakeupManager = EventManagerFactory.create(context, "wp")
        
        val wakeupEventListener = object : EventListener {
            override fun onEvent(name: String?, params: String?, data: ByteArray?, offset: Int, length: Int) {
                Log.d(TAG, "唤醒事件: $name, 参数: $params")
                
                when (name) {
                    "wp.data" -> params?.let { handleWakeupData(it) }
                    "wp.start" -> {
                        isWakeupActive = true
                        Log.d(TAG, "语音唤醒已启动")
                    }
                    "wp.exit" -> {
                        isWakeupActive = false
                        Log.d(TAG, "语音唤醒已关闭")
                    }
                    "wp.error" -> params?.let { handleWakeupError(it) }
                }
            }
        }
        
        wakeupManager?.registerListener(wakeupEventListener)
        Log.d(TAG, "语音唤醒初始化完成")
    }

    /**
     * 初始化语音识别（严格按照MainActivity.java）
     */
    private fun initializeASR() {
        asrManager = EventManagerFactory.create(context, "asr")
        
        val asrEventListener = object : EventListener {
            override fun onEvent(name: String?, params: String?, data: ByteArray?, offset: Int, length: Int) {
                Log.d(TAG, "识别事件: $name, 参数: $params")
                
                when (name) {
                    SpeechConstant.CALLBACK_EVENT_ASR_READY -> {
                        Log.d(TAG, "语音识别引擎就绪")
                    }
                    SpeechConstant.CALLBACK_EVENT_ASR_BEGIN -> {
                        Log.d(TAG, "检测到用户开始说话")
                    }
                    SpeechConstant.CALLBACK_EVENT_ASR_END -> {
                        Log.d(TAG, "检测到用户说话结束")
                    }
                    SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL -> {
                        params?.let { handlePartialResult(it) }
                    }
                    SpeechConstant.CALLBACK_EVENT_ASR_FINISH -> {
                        params?.let { handleFinalResult(it) }
                    }
                    SpeechConstant.CALLBACK_EVENT_ASR_ERROR -> {
                        params?.let { handleASRError(it) }
                    }
                }
            }
        }
        
        asrManager?.registerListener(asrEventListener)
        Log.d(TAG, "语音识别初始化完成")
    }

    /**
     * 初始化语音合成（严格按照MainActivity.java）
     */
    private fun initializeSpeechSynthesizer() {
        speechSynthesizer = SpeechSynthesizer.getInstance().apply {
            setContext(context)
            setAppId(APP_ID)
            setApiKey(API_KEY, SECRET_KEY)
            
            // 设置合成参数（与MainActivity.java一致）
            setParam(SpeechSynthesizer.PARAM_SPEAKER, "0")
            setParam(SpeechSynthesizer.PARAM_SPEED, "5")
            setParam(SpeechSynthesizer.PARAM_VOLUME, "9")
            setParam(SpeechSynthesizer.PARAM_PITCH, "5")
            setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT)
            
            setSpeechSynthesizerListener(MessageListener())
            
            val result = initTts(TtsMode.ONLINE)
            if (result != 0) {
                Log.e(TAG, "语音合成初始化失败，错误码：$result")
                throw RuntimeException("语音合成初始化失败，错误码：$result")
            }
        }
        
        Log.d(TAG, "语音合成初始化完成")
    }

    /**
     * 启动语音唤醒（严格按照MainActivity.java）
     */
    private fun startWakeup(): Boolean {
        if (isWakeupActive) {
            Log.d(TAG, "语音唤醒已启动")
            return true
        }
        
        return try {
            val params = hashMapOf<String, Any>(
                SpeechConstant.WP_WORDS_FILE to WAKEUP_FILE,
                "appid" to APP_ID,
                "apikey" to API_KEY,
                "secretkey" to SECRET_KEY,
                "cuid" to getDeviceId()
            )
            
            val json = JSONObject(params as Map<*, *>)
            wakeupManager?.send(SpeechConstant.WAKEUP_START, json.toString(), null, 0, 0)
            Log.d(TAG, "启动语音唤醒")
            true
        } catch (e: Exception) {
            Log.e(TAG, "启动语音唤醒失败", e)
            false
        }
    }

    /**
     * 停止语音唤醒
     */
    private fun stopWakeup() {
        if (wakeupManager != null && isWakeupActive) {
            wakeupManager?.send(SpeechConstant.WAKEUP_STOP, null, null, 0, 0)
            isWakeupActive = false
            Log.d(TAG, "语音唤醒已停止")
        }
    }

    /**
     * 处理唤醒数据（核心逻辑，严格按照MainActivity.java）
     */
    private fun handleWakeupData(params: String) {
        try {
            val json = JSONObject(params)
            val word = json.optString("word", "").lowercase().trim()
            
            if (word.isEmpty()) {
                Log.w(TAG, "唤醒词为空")
                return
            }
            
            Log.d(TAG, "检测到唤醒词: $word")
            handleWakeupSuccess(word)
        } catch (e: Exception) {
            Log.e(TAG, "处理唤醒数据失败", e)
        }
    }

    /**
     * 处理唤醒成功（严格按照MainActivity.java）
     */
    private fun handleWakeupSuccess(wakeupWord: String) {
        // 检查是否已经在处理语音命令
        if (isHandlingVoiceCommand) {
            Log.d(TAG, "已经在处理语音命令，忽略重复唤醒")
            return
        }
        
        // 设置标志位，防止重复处理
        isHandlingVoiceCommand = true
        
        Log.d(TAG, "执行唤醒后操作，唤醒词：$wakeupWord")
        
        val word = wakeupWord.lowercase().trim()
        
        try {
            // 区分通用唤醒词和操作唤醒词（严格按照MainActivity.java）
            if (isGeneralWakeupWord(word)) {
                // 通用唤醒词：你好小聆、小聆同学
                handleGeneralWakeup()
            } else {
                // 操作唤醒词：播放、暂停、下一首等
                handleOperationWakeup(word)
            }
        } catch (e: Exception) {
            Log.e(TAG, "处理唤醒词时出错", e)
            // 出错时恢复到唤醒模式
            isHandlingVoiceCommand = false
            startWakeup()
        }
    }

    /**
     * 判断是否为通用唤醒词（严格按照MainActivity.java）
     */
    private fun isGeneralWakeupWord(word: String): Boolean {
        return word.contains("你好小聆") || 
               word.contains("小聆同学") ||
               word == "你好" ||
               word == "小聆"
    }

    /**
     * 处理通用唤醒词：播报"我在"然后进入语音识别模式（严格按照MainActivity.java）
     */
    private fun handleGeneralWakeup() {
        Log.d(TAG, "处理通用唤醒词：播报'我在'然后进入语音识别")
        
        // 停止当前语音唤醒
        stopWakeup()
        
        // 不播放滴声，直接语音播报"我在"
        speakMessageWithCallback("我在") {
            // 播报完成后启动语音识别
            Log.d(TAG, "播报完成，启动语音识别")
            isHandlingVoiceCommand = false // 重置标志位
            startSpeechRecognition()
        }
    }

    /**
     * 处理操作唤醒词：滴一声然后直接执行操作（严格按照MainActivity.java）
     */
    private fun handleOperationWakeup(word: String) {
        Log.d(TAG, "处理操作唤醒词：$word")
        
        // 播放滴声提示
        playBeepSound()
        
        // 延迟执行操作，确保滴声播放完成
        Handler(Looper.getMainLooper()).postDelayed({
            executeWakeupOperation(word)
            
            // 操作完成后重置标志位并继续语音唤醒
            isHandlingVoiceCommand = false
            startWakeup()
        }, 150) // 延迟150毫秒，确保滴声播放完成
    }

    /**
     * 执行唤醒词对应的操作（严格按照MainActivity.java）
     */
    private fun executeWakeupOperation(word: String) {
        Log.d(TAG, "执行唤醒操作：$word")
        
        try {
            when {
                word.contains("播放") -> {
                    // 播放音乐
                    if (!callback.isMusicPlaying()) {
                        callback.resumeMusic()
                        Log.d(TAG, "唤醒操作：播放音乐")
                    }
                }
                word.contains("暂停") -> {
                    // 暂停音乐
                    if (callback.isMusicPlaying()) {
                        callback.pauseMusic()
                        Log.d(TAG, "唤醒操作：暂停音乐")
                    }
                }
                word.contains("下一首") -> {
                    // 播放下一首
                    callback.playNext()
                    Log.d(TAG, "唤醒操作：下一首")
                }
                word.contains("上一首") -> {
                    // 播放上一首
                    callback.playPrevious()
                    Log.d(TAG, "唤醒操作：上一首")
                }
                word.contains("增大音量") -> {
                    // 增大音量
                    adjustSystemVolume(true)
                    Log.d(TAG, "唤醒操作：增大音量")
                }
                word.contains("减小音量") -> {
                    // 减小音量
                    adjustSystemVolume(false)
                    Log.d(TAG, "唤醒操作：减小音量")
                }
                word.contains("随机播放") -> {
                    // 切换到随机播放模式（关键：立即播放随机歌曲）
                    callback.switchToRandomMode()
                    Log.d(TAG, "唤醒操作：随机播放模式")
                }
                word.contains("循环播放") -> {
                    // 切换到循环播放模式
                    callback.switchToRepeatMode()
                    Log.d(TAG, "唤醒操作：循环播放模式")
                }
                else -> {
                    // 未识别的唤醒词，默认切换播放状态
                    Log.d(TAG, "未识别的唤醒词，执行默认操作：切换播放状态")
                    if (callback.isMusicPlaying()) {
                        callback.pauseMusic()
                    } else {
                        callback.resumeMusic()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "执行唤醒操作时出错", e)
        }
    }

    /**
     * 调整系统音量（严格按照MainActivity.java）
     */
    private fun adjustSystemVolume(increase: Boolean) {
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            
            val targetVolume = if (increase) {
                minOf(currentVolume + 2, maxVolume)
            } else {
                maxOf(currentVolume - 2, 0)
            }
            
            audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                targetVolume,
                AudioManager.FLAG_SHOW_UI
            )
            Log.d(TAG, "音量调节：$currentVolume -> $targetVolume")
        } catch (e: Exception) {
            Log.e(TAG, "调节音量时出错", e)
        }
    }

    /**
     * 播放"嘀"声提示音（严格按照MainActivity.java）
     */
    private fun playBeepSound() {
        try {
            // 创建ToneGenerator实例，音量设为最大值的80%
            val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 80)
            // 播放简短清脆的"滴"声，使用TONE_DTMF_S音调，持续时间仅100毫秒
            toneGenerator.startTone(ToneGenerator.TONE_DTMF_S, 100)
            // 延迟释放ToneGenerator资源，确保声音播放完毕
            Handler(Looper.getMainLooper()).postDelayed({
                toneGenerator.release()
            }, 120)
        } catch (e: Exception) {
            Log.e(TAG, "播放提示音失败", e)
        }
    }

    /**
     * 启动语音识别（严格按照MainActivity.java）
     */
    private fun startSpeechRecognition() {
        // 如果已经在识别中，不再重复启动
        if (isRecognizing) {
            Log.d(TAG, "已经在进行语音识别")
            return
        }
        
        // 先停止语音唤醒，防止与语音识别命令冲突
        if (isWakeupActive) {
            stopWakeup()
            Log.d(TAG, "进入语音识别模式，暂停语音唤醒")
        }
        
        // 暂停音乐播放并记录状态
        wasMusicPlaying = callback.isMusicPlaying()
        if (wasMusicPlaying) {
            callback.pauseMusic()
        }
        
        // 清空识别结果
        isRecognizing = true
        
        // 设置超时处理
        setupRecognitionTimeout()
        
        // 配置识别参数（严格按照MainActivity.java）
        val params = hashMapOf<String, Any>(
            "appid" to APP_ID,
            "apikey" to API_KEY,
            "secretkey" to SECRET_KEY,
            "cuid" to getDeviceId(),
            "pid" to 1537, // 普通话输入法模型
            "vad" to "dnn", // 高性能VAD
            "vad.endpoint-timeout" to 4000, // 语音终点超时时间
            "accept-audio-volume" to true,
            "disable-punctuation" to true
        )
        
        try {
            val json = JSONObject(params as Map<*, *>)
            Log.d(TAG, "启动语音识别")
            asrManager?.send(SpeechConstant.ASR_START, json.toString(), null, 0, 0)
        } catch (e: Exception) {
            Log.e(TAG, "启动语音识别失败", e)
            isRecognizing = false
            resumeMusicIfNeeded()
            // 识别启动失败，重新启动语音唤醒
            startWakeup()
        }
    }

    /**
     * 设置识别超时处理（严格按照MainActivity.java）
     */
    private fun setupRecognitionTimeout() {
        recognitionTimeoutRunnable = Runnable {
            Log.d(TAG, "语音识别超时")
            stopRecognition()
            
            // 超时播报"对不起，没听到"
            speakMessageWithCallback("对不起，没听到") {
                // 播报完成后回到语音唤醒模式
                isRecognizing = false
                resumeMusicIfNeeded()
                startWakeup()
            }
        }
        
        recognitionTimeoutHandler.postDelayed(recognitionTimeoutRunnable!!, RECOGNITION_TIMEOUT)
    }

    /**
     * 停止语音识别
     */
    private fun stopRecognition() {
        recognitionTimeoutHandler.removeCallbacks(recognitionTimeoutRunnable ?: return)
        
        if (asrManager != null && isRecognizing) {
            asrManager?.send(SpeechConstant.ASR_STOP, null, null, 0, 0)
            isRecognizing = false
        }
    }

    /**
     * 处理部分识别结果
     */
    private fun handlePartialResult(params: String) {
        try {
            val json = JSONObject(params)
            val result = json.optString("best_result", "")
            Log.d(TAG, "部分识别结果: $result")
        } catch (e: Exception) {
            Log.e(TAG, "处理部分识别结果失败", e)
        }
    }

    /**
     * 处理最终识别结果（严格按照MainActivity.java）
     */
    private fun handleFinalResult(params: String) {
        // 取消超时处理
        recognitionTimeoutHandler.removeCallbacks(recognitionTimeoutRunnable ?: return)
        
        try {
            val json = JSONObject(params)
            val result = json.optString("best_result", "").trim()
            
            Log.d(TAG, "最终识别结果: $result")
            
            if (result.isEmpty()) {
                // 识别结果为空，播报"听不清，请再说一遍"
                speakMessageWithCallback("听不清，请再说一遍") {
                    isRecognizing = false
                    resumeMusicIfNeeded()
                    startWakeup()
                }
            } else {
                // 识别成功，播放滴声并执行命令
                playBeepSound()
                
                Handler(Looper.getMainLooper()).postDelayed({
                    processVoiceCommand(result)
                    
                    // 命令执行完成后回到语音唤醒模式
                    isRecognizing = false
                    resumeMusicIfNeeded()
                    startWakeup()
                }, 150) // 延迟150毫秒，确保滴声播放完成
            }
        } catch (e: Exception) {
            Log.e(TAG, "处理最终识别结果失败", e)
            // 处理失败，播报错误信息
            speakMessageWithCallback("听不清，请再说一遍") {
                isRecognizing = false
                resumeMusicIfNeeded()
                startWakeup()
            }
        }
    }

    /**
     * 处理ASR错误
     */
    private fun handleASRError(params: String) {
        // 取消超时处理
        recognitionTimeoutHandler.removeCallbacks(recognitionTimeoutRunnable ?: return)
        
        try {
            val json = JSONObject(params)
            val errorCode = json.optInt("error")
            val errorMessage = json.optString("desc", "未知错误")
            
            Log.e(TAG, "语音识别错误: $errorCode - $errorMessage")
            
            // 播报错误信息
            speakMessageWithCallback("听不清，请再说一遍") {
                isRecognizing = false
                resumeMusicIfNeeded()
                startWakeup()
            }
        } catch (e: Exception) {
            Log.e(TAG, "处理ASR错误失败", e)
            isRecognizing = false
            resumeMusicIfNeeded()
            startWakeup()
        }
    }

    /**
     * 处理语音命令（严格按照MainActivity.java）
     */
    private fun processVoiceCommand(command: String) {
        Log.d(TAG, "处理语音命令: $command")
        
        val cmd = command.lowercase().trim()
        
        try {
            when {
                cmd.contains("播放") && !cmd.contains("模式") && !cmd.contains("随机") && !cmd.contains("循环") -> {
                    if (!callback.isMusicPlaying()) {
                        callback.resumeMusic()
                        Log.d(TAG, "执行播放命令")
                    }
                }
                cmd.contains("暂停") || cmd.contains("停止") -> {
                    if (callback.isMusicPlaying()) {
                        callback.pauseMusic()
                        Log.d(TAG, "执行暂停命令")
                    }
                }
                cmd.contains("下一首") || cmd.contains("换歌") || cmd.contains("下首歌") -> {
                    callback.playNext()
                    Log.d(TAG, "执行下一首命令")
                }
                cmd.contains("上一首") || cmd.contains("上首歌") || cmd.contains("前一首") -> {
                    callback.playPrevious()
                    Log.d(TAG, "执行上一首命令")
                }
                cmd.contains("随机播放") || cmd.contains("乱序播放") -> {
                    callback.switchToRandomMode()
                    Log.d(TAG, "执行随机播放命令")
                }
                cmd.contains("循环播放") || cmd.contains("单曲循环") -> {
                    callback.switchToRepeatMode()
                    Log.d(TAG, "执行循环播放命令")
                }
                cmd.contains("增大音量") || cmd.contains("大声点") || cmd.contains("音量增大") -> {
                    adjustSystemVolume(true)
                    Log.d(TAG, "执行增大音量命令")
                }
                cmd.contains("减小音量") || cmd.contains("小声点") || cmd.contains("音量减小") -> {
                    adjustSystemVolume(false)
                    Log.d(TAG, "执行减小音量命令")
                }
                else -> {
                    Log.d(TAG, "未识别的语音命令: $command")
                    // 可以在这里添加更多的命令处理逻辑
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "处理语音命令时出错", e)
        }
    }

    /**
     * 带回调的语音播报方法（严格按照MainActivity.java）
     */
    private fun speakMessageWithCallback(message: String, callback: Runnable?) {
        // 检查语音合成器是否已初始化
        if (speechSynthesizer == null) {
            Log.e(TAG, "语音合成器未初始化")
            callback?.run()
            return
        }
        
        // 暂停可能正在播放的音乐
        wasMusicPlaying = this.callback.isMusicPlaying()
        if (wasMusicPlaying) {
            this.callback.pauseMusic()
        }
        
        // 标记语音合成状态为进行中
        isSpeaking = true
        
        // 保存回调函数
        currentSpeechCallback = callback
        
        // 生成唯一的utteranceId
        val utteranceId = "utteranceId_${System.currentTimeMillis()}"
        
        // 开始语音合成并播放
        val result = speechSynthesizer?.speak(message, utteranceId) ?: -1
        
        if (result < 0) {
            Log.e(TAG, "语音合成失败，错误码：$result，消息内容：$message")
            isSpeaking = false
            
            // 合成失败时恢复音乐播放并执行回调
            resumeMusicIfNeeded()
            callback?.run()
        } else {
            Log.d(TAG, "语音合成开始，utteranceId: $utteranceId，内容：$message")
        }
    }

    /**
     * 停止语音合成
     */
    private fun stopSpeechSynthesis() {
        if (speechSynthesizer != null && isSpeaking) {
            speechSynthesizer?.stop()
            isSpeaking = false
            Log.d(TAG, "语音合成已停止")
        }
    }

    /**
     * 恢复音乐播放
     */
    private fun resumeMusicIfNeeded() {
        if (wasMusicPlaying) {
            callback.resumeMusic()
            wasMusicPlaying = false
        }
    }

    /**
     * 处理唤醒错误
     */
    private fun handleWakeupError(params: String) {
        try {
            val json = JSONObject(params)
            val errorCode = json.optInt("error")
            val errorMessage = json.optString("desc", "未知错误")
            Log.e(TAG, "语音唤醒错误: $errorCode - $errorMessage")
        } catch (e: Exception) {
            Log.e(TAG, "处理唤醒错误失败", e)
        }
    }

    /**
     * 获取设备ID
     */
    private fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) ?: "unknown"
    }

    /**
     * 语音合成监听器（严格按照MainActivity.java）
     */
    private inner class MessageListener : SpeechSynthesizerListener {
        override fun onSynthesizeStart(utteranceId: String?) {
            Log.d(TAG, "语音合成开始: $utteranceId")
        }

        override fun onSynthesizeDataArrived(utteranceId: String?, data: ByteArray?, offset: Int, length: Int) {
            // 数据到达，不需要处理
        }

        override fun onSynthesizeFinish(utteranceId: String?) {
            Log.d(TAG, "语音合成数据完成: $utteranceId")
        }

        override fun onSpeechStart(utteranceId: String?) {
            Log.d(TAG, "语音播放开始: $utteranceId")
        }

        override fun onSpeechProgressChanged(utteranceId: String?, progress: Int) {
            // 播放进度变化，不需要处理
        }

        override fun onSpeechFinish(utteranceId: String?) {
            Log.d(TAG, "语音播放完成: $utteranceId")
            isSpeaking = false
            
            // 恢复音乐播放
            resumeMusicIfNeeded()
            
            // 执行回调
            currentSpeechCallback?.run()
            currentSpeechCallback = null
        }

        override fun onError(utteranceId: String?, speechError: SpeechError?) {
            Log.e(TAG, "语音合成错误: $utteranceId, ${speechError?.description}")
            isSpeaking = false
            
            // 错误时也要恢复音乐播放
            resumeMusicIfNeeded()
            
            // 执行回调
            currentSpeechCallback?.run()
            currentSpeechCallback = null
        }
    }
}
