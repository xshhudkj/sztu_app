package me.ckn.music.voice

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.util.Log
import com.baidu.speech.EventListener
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.baidu.tts.client.SpeechError
import com.baidu.tts.client.SpeechSynthesizer
import com.baidu.tts.client.SpeechSynthesizerListener
import com.baidu.tts.client.TtsMode
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * 语音会话数据类
 */
data class VoiceSession(
    val sessionId: String = UUID.randomUUID().toString(),
    val startTime: Long = System.currentTimeMillis(),
    val sessionType: VoiceSessionType,
    var isCompleted: Boolean = false
)

/**
 * 语音会话类型
 */
enum class VoiceSessionType {
    DIRECT_OPERATION,  // 直接操作（如直接说"播放"）
    DIALOG_MODE       // 对话模式（先说"你好小聆"，再说命令）
}

/**
 * WhisperPlay Music Player - 百度语音SDK管理器（重构版）
 *
 * 文件描述：严格按照MainActivity.java实现的语音控制功能
 * 核心特性：三种模式严格分离，完整的语音交互流程
 * File Description: Baidu Speech SDK manager with strict mode separation
 *
 * @author ckn
 * @since 2025-06-20
 * @version 2.4.0
 */
class BaiduSpeechManager(
    private val context: Context,
    private val callback: BaiduSpeechCallback
) {
    companion object {
        private const val TAG = "BaiduSpeechManager"

        // 百度语音SDK授权信息 - 严格按照MainActivity.java配置
        private const val APP_ID = "118558442"
        private const val API_KEY = "l07tTLiM8XdSVcM6Avmv5FG3"
        private const val SECRET_KEY = "e4DxN5gewACp162txczyVRuJs4UGBhdb"

        // 唤醒资源文件路径
        private const val WAKEUP_FILE = "assets:///WakeUp.bin"

        // 提示音参数（与MainActivity.java完全一致）
        private const val BEEP_TONE = ToneGenerator.TONE_DTMF_S
        private const val BEEP_DURATION = 100
        private const val BEEP_VOLUME = 80
        private const val BEEP_RELEASE_DELAY = 120L

        // 对话唤醒词（触发"我在"响应和语音识别）
        private val DIALOG_WAKE_WORDS = arrayOf("你好小聆", "小聆同学")

        // 操作唤醒词（直接执行命令）
        private val OPERATION_WAKE_WORDS = arrayOf(
            "播放", "暂停", "下一首", "上一首",
            "随机播放", "循环播放", "增大音量", "减小音量"
        )

        // 所有唤醒词（用于WakeUp.bin配置）
        private val ALL_WAKE_WORDS = DIALOG_WAKE_WORDS + OPERATION_WAKE_WORDS
    }
    
    // 语音唤醒器
    private var wakeupManager: EventManager? = null
    private var wakeupEventListener: EventListener? = null
    private var isWakeupActive = false

    // 语音识别器
    private var asrManager: EventManager? = null
    private var asrEventListener: EventListener? = null
    private var isRecognizing = false
    private var recognitionResult = ""

    // 语音控制状态
    private var isVoiceControlEnabled = false
    private var isInDialogMode = false  // 是否处于对话模式（等待命令输入）
    private var currentVoiceSession: VoiceSession? = null

    // 性能监控
    private var lastWakeupTime = 0L
    private var lastRecognitionTime = 0L
    private var recognitionCount = 0
    private var successfulRecognitionCount = 0
    
    // 语音合成器
    private var speechSynthesizer: SpeechSynthesizer? = null
    private var isSpeaking = false
    
    // 提示音生成器
    private var toneGenerator: ToneGenerator? = null
    
    /**
     * 初始化语音SDK
     * Initialize speech SDK with complete voice control support
     */
    fun initialize(): Boolean {
        return try {
            // 加载鉴权配置
            loadAuthConfig()

            // 初始化各个组件
            initializeWakeup()
            initializeASR()
            initializeTTS()
            initializeToneGenerator()

            Log.d(TAG, "百度语音SDK初始化成功 - 支持智能语音控制")
            true
        } catch (e: Exception) {
            Log.e(TAG, "百度语音SDK初始化失败", e)
            callback.onError("语音SDK初始化失败: ${e.message}")
            false
        }
    }

    /**
     * 加载鉴权配置
     */
    private fun loadAuthConfig() {
        try {
            val inputStream = context.assets.open("auth.properties")
            val properties = Properties()
            properties.load(inputStream)

            // 验证配置完整性
            val appId = properties.getProperty("appId")
            val appKey = properties.getProperty("appKey")
            val secretKey = properties.getProperty("secretKey")
            val applicationId = properties.getProperty("applicationId")

            Log.d(TAG, "鉴权配置验证:")
            Log.d(TAG, "  AppID: ${appId?.take(8)}...")
            Log.d(TAG, "  AppKey: ${appKey?.take(8)}...")
            Log.d(TAG, "  SecretKey: ${secretKey?.take(8)}...")
            Log.d(TAG, "  ApplicationId: $applicationId")
            Log.d(TAG, "  实际包名: ${context.packageName}")

            // 检查包名是否匹配
            if (applicationId != context.packageName) {
                Log.w(TAG, "警告：auth.properties中的applicationId($applicationId)与实际包名(${context.packageName})不匹配")
                Log.w(TAG, "这可能导致认证失败，请检查百度控制台中的应用包名配置")
            }

            Log.d(TAG, "鉴权配置加载成功")
        } catch (e: Exception) {
            Log.e(TAG, "无法加载auth.properties，使用默认配置", e)
            Log.e(TAG, "请确保auth.properties文件存在且格式正确")
        }
    }
    
    /**
     * 初始化语音唤醒功能
     * 支持智能唤醒词检测：通用唤醒词 + 操作唤醒词
     */
    private fun initializeWakeup() {
        wakeupManager = EventManagerFactory.create(context, "wp")

        wakeupEventListener = object : EventListener {
            override fun onEvent(name: String, params: String?, data: ByteArray?, offset: Int, length: Int) {
                Log.d(TAG, "唤醒事件: $name, 参数: ${params ?: "null"}")

                when (name) {
                    "wp.data" -> params?.let { handleWakeupData(it) } ?: Log.w(TAG, "唤醒数据为空")
                    "wp.start" -> {
                        isWakeupActive = true
                        callback.onWakeupStateChanged(true)
                        Log.d(TAG, "语音唤醒已启动 - 监听唤醒词: ${ALL_WAKE_WORDS.joinToString()}")
                    }
                    "wp.exit" -> {
                        isWakeupActive = false
                        callback.onWakeupStateChanged(false)
                        Log.d(TAG, "语音唤醒已停止")
                    }
                    "wp.error" -> params?.let { handleWakeupError(it) } ?: Log.w(TAG, "唤醒错误参数为空")
                }
            }
        }

        wakeupManager?.registerListener(wakeupEventListener)
    }
    
    /**
     * 初始化语音识别功能
     */
    private fun initializeASR() {
        asrManager = EventManagerFactory.create(context, "asr")
        
        asrEventListener = object : EventListener {
            override fun onEvent(name: String, params: String?, data: ByteArray?, offset: Int, length: Int) {
                Log.d(TAG, "识别事件: $name, 参数: ${params ?: "null"}")
                
                when (name) {
                    SpeechConstant.CALLBACK_EVENT_ASR_READY -> {
                        Log.d(TAG, "语音识别引擎就绪")
                    }
                    SpeechConstant.CALLBACK_EVENT_ASR_BEGIN -> {
                        Log.d(TAG, "检测到用户开始说话")
                        callback.onRecognitionStateChanged(true)
                    }
                    SpeechConstant.CALLBACK_EVENT_ASR_END -> {
                        Log.d(TAG, "检测到用户说话结束")
                    }
                    SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL -> {
                        params?.let { handlePartialResult(it) } ?: Log.w(TAG, "ASR部分结果参数为空")
                    }
                    SpeechConstant.CALLBACK_EVENT_ASR_FINISH -> {
                        params?.let { handleFinalResult(it) } ?: Log.w(TAG, "ASR最终结果参数为空")
                    }
                    SpeechConstant.CALLBACK_EVENT_ASR_ERROR -> {
                        params?.let { handleASRError(it) } ?: Log.w(TAG, "ASR错误参数为空")
                    }
                }
            }
        }
        
        asrManager?.registerListener(asrEventListener)
    }
    
    /**
     * 初始化语音合成功能
     */
    private fun initializeTTS() {
        speechSynthesizer = SpeechSynthesizer.getInstance().apply {
            setContext(context)
            setAppId(APP_ID)
            setApiKey(API_KEY, SECRET_KEY)
            
            // 设置合成参数
            setParam(SpeechSynthesizer.PARAM_SPEAKER, "0") // 发音人选择，0为女声，1为男声
            setParam(SpeechSynthesizer.PARAM_VOLUME, "9") // 合成的音量，0-15，默认5
            setParam(SpeechSynthesizer.PARAM_SPEED, "5") // 合成的语速，0-15，默认5
            setParam(SpeechSynthesizer.PARAM_PITCH, "5") // 合成的语调，0-15，默认5
            
            setSpeechSynthesizerListener(object : SpeechSynthesizerListener {
                override fun onSynthesizeStart(utteranceId: String) {
                    isSpeaking = true
                    callback.onSpeechSynthesisStateChanged(true)
                }
                
                override fun onSynthesizeDataArrived(utteranceId: String, data: ByteArray, progress: Int, engineType: Int) {
                    // 合成数据到达，可以在这里处理音频数据
                }
                
                override fun onSynthesizeFinish(utteranceId: String) {
                    isSpeaking = false
                    callback.onSpeechSynthesisStateChanged(false)
                    callback.onSpeechSynthesisCompleted()

                    // 如果是对话模式下的"我在"播报完成，启动语音识别等待命令
                    if (isInDialogMode && currentVoiceSession?.sessionType == VoiceSessionType.DIALOG_MODE) {
                        Log.d(TAG, "对话模式语音播报完成，启动语音识别等待命令")
                        startRecognitionForDialog()
                    }
                }
                
                override fun onSpeechStart(utteranceId: String) {
                    Log.d(TAG, "开始播放合成语音")
                }
                
                override fun onSpeechFinish(utteranceId: String) {
                    Log.d(TAG, "合成语音播放完成")
                }
                
                override fun onSpeechProgressChanged(utteranceId: String, progress: Int) {
                    // 播放进度变化
                }
                
                override fun onError(utteranceId: String, error: SpeechError) {
                    Log.e(TAG, "语音合成错误: ${error.description}")
                    isSpeaking = false
                    callback.onSpeechSynthesisStateChanged(false)
                    callback.onError("语音合成失败: ${error.description}")
                }
            })
            
            // 初始化TTS引擎
            val result = initTts(TtsMode.ONLINE)
            if (result != 0) {
                Log.e(TAG, "语音合成初始化失败，错误码：$result")
                throw RuntimeException("语音合成初始化失败，错误码：$result")
            }
        }
    }
    
    /**
     * 初始化提示音生成器
     */
    private fun initializeToneGenerator() {
        toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
    }
    
    /**
     * 启动语音唤醒
     */
    fun startWakeup(): Boolean {
        if (isWakeupActive) {
            Log.d(TAG, "语音唤醒已经启动")
            return true
        }

        return try {
            val params = HashMap<String, Any>().apply {
                put(SpeechConstant.WP_WORDS_FILE, "assets:///WakeUp.bin")
                put("appid", APP_ID)
                put("apikey", API_KEY)
                put("secretkey", SECRET_KEY)
                put("cuid", getDeviceId())
            }

            val json = JSONObject(params as Map<*, *>)
            wakeupManager?.send("wp.start", json.toString(), null, 0, 0)
            Log.d(TAG, "启动语音唤醒")
            true
        } catch (e: Exception) {
            Log.e(TAG, "启动语音唤醒失败", e)
            callback.onError("启动语音唤醒失败: ${e.message}")
            false
        }
    }

    /**
     * 启动完整语音控制
     * 支持智能唤醒词检测和语音命令识别
     */
    fun startVoiceControl(): Boolean {
        if (isVoiceControlEnabled) {
            Log.d(TAG, "语音控制已启动")
            return true
        }

        val success = startWakeup()
        if (success) {
            isVoiceControlEnabled = true
            Log.d(TAG, "智能语音控制启动成功")
            Log.d(TAG, "支持的唤醒词: ${ALL_WAKE_WORDS.joinToString()}")
        } else {
            Log.e(TAG, "智能语音控制启动失败")
        }

        return success
    }
    
    /**
     * 停止语音唤醒
     */
    fun stopWakeup() {
        if (!isWakeupActive) return

        wakeupManager?.send("wp.stop", null, null, 0, 0)
        Log.d(TAG, "停止语音唤醒")
    }

    /**
     * 停止语音控制
     */
    fun stopVoiceControl() {
        if (!isVoiceControlEnabled) {
            Log.d(TAG, "语音控制未启动")
            return
        }

        stopWakeup()
        exitDialogMode()
        isVoiceControlEnabled = false
        Log.d(TAG, "智能语音控制已停止")
    }
    
    /**
     * 开始语音识别
     */
    fun startRecognition(): Boolean {
        if (isRecognizing) {
            Log.d(TAG, "语音识别已在进行中")
            return true
        }
        
        return try {
            recognitionResult = ""
            isRecognizing = true
            
            val params = HashMap<String, Any>().apply {
                put("appid", APP_ID)
                put("apikey", API_KEY)
                put("secretkey", SECRET_KEY)
                put("cuid", getDeviceId())
                put("pid", 1537) // 普通话搜索模型
                put("vad", "dnn") // 高性能VAD
                put("vad.endpoint-timeout", 4000)
                put("accept-audio-volume", true)
                put("disable-punctuation", true)
            }
            
            val json = JSONObject(params as Map<*, *>)
            asrManager?.send(SpeechConstant.ASR_START, json.toString(), null, 0, 0)
            Log.d(TAG, "启动语音识别")
            true
        } catch (e: Exception) {
            Log.e(TAG, "启动语音识别失败", e)
            isRecognizing = false
            callback.onError("启动语音识别失败: ${e.message}")
            false
        }
    }
    
    /**
     * 停止语音识别
     */
    fun stopRecognition() {
        if (!isRecognizing) return
        
        asrManager?.send(SpeechConstant.ASR_STOP, null, null, 0, 0)
        isRecognizing = false
        callback.onRecognitionStateChanged(false)
        Log.d(TAG, "停止语音识别")
    }
    
    /**
     * 播放语音合成
     */
    fun speak(text: String) {
        speechSynthesizer?.speak(text)
        Log.d(TAG, "播放语音合成: $text")
    }
    
    /**
     * 播放提示音
     */
    fun playBeepSound() {
        toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 120)
        Log.d(TAG, "播放提示音")
    }


    
    /**
     * 释放资源
     */
    fun release() {
        try {
            stopVoiceControl()
            stopRecognition()

            wakeupManager?.unregisterListener(wakeupEventListener)
            asrManager?.unregisterListener(asrEventListener)
            speechSynthesizer?.release()
            toneGenerator?.release()

            // 清理状态
            isVoiceControlEnabled = false
            isInDialogMode = false
            currentVoiceSession = null

            Log.d(TAG, "智能语音控制资源释放完成")
        } catch (e: Exception) {
            Log.e(TAG, "释放语音SDK资源失败", e)
        }
    }
    
    // 私有辅助方法
    private fun getDeviceId(): String {
        return android.provider.Settings.Secure.getString(
            context.contentResolver,
            android.provider.Settings.Secure.ANDROID_ID
        )
    }
    
    private fun handleWakeupData(params: String) {
        try {
            val json = JSONObject(params)
            val errorCode = json.getInt("errorCode")

            if (errorCode == 0) {
                val word = json.getString("word")
                Log.d(TAG, "唤醒成功! 唤醒词: $word")

                // 智能判断唤醒词类型并执行相应操作
                when {
                    DIALOG_WAKE_WORDS.contains(word) -> {
                        // 对话唤醒词：进入对话模式
                        handleGeneralWakeWord(word)
                    }
                    OPERATION_WAKE_WORDS.contains(word) -> {
                        // 操作唤醒词：直接执行命令
                        handleOperationWakeWord(word)
                    }
                    else -> {
                        Log.w(TAG, "未识别的唤醒词: $word")
                        callback.onRecognitionFailed("听不清，请再说一遍")
                    }
                }
            } else {
                val errorDesc = json.getString("errorDesc")
                Log.e(TAG, "唤醒出错: $errorCode, $errorDesc")
                callback.onRecognitionFailed("听不清，请再说一遍")
            }
        } catch (e: JSONException) {
            Log.e(TAG, "唤醒结果解析出错", e)
            callback.onRecognitionFailed("听不清，请再说一遍")
        }
    }

    /**
     * 处理通用唤醒词（你好小聆、小聆同学）
     * 进入对话模式：播放"我在" → 等待用户命令
     */
    private fun handleGeneralWakeWord(word: String) {
        Log.d(TAG, "检测到通用唤醒词: $word，进入对话模式")

        // 性能监控
        lastWakeupTime = System.currentTimeMillis()
        logPerformanceMetrics("通用唤醒词检测")

        // 创建对话模式会话
        currentVoiceSession = VoiceSession(
            sessionType = VoiceSessionType.DIALOG_MODE
        )
        isInDialogMode = true

        // 播放提示音
        playBeepSound()

        // 语音合成播报"我在"
        speak("我在")

        // 通知回调
        callback.onWakeWordDetected(word)
    }

    /**
     * 处理操作唤醒词（播放、暂停等）
     * 直接执行模式：播放提示音 → 直接执行操作
     */
    private fun handleOperationWakeWord(word: String) {
        Log.d(TAG, "检测到操作唤醒词: $word，直接执行操作")

        // 创建直接操作会话
        currentVoiceSession = VoiceSession(
            sessionType = VoiceSessionType.DIRECT_OPERATION
        )

        // 播放提示音
        playBeepSound()

        // 直接执行命令
        callback.onDirectCommandRecognized(word)

        // 标记会话完成
        currentVoiceSession?.isCompleted = true
    }

    /**
     * 启动对话模式的语音识别
     * 在"我在"播报完成后调用，等待用户说出具体命令
     */
    private fun startRecognitionForDialog(): Boolean {
        if (isRecognizing) {
            Log.d(TAG, "语音识别已在进行中")
            return true
        }

        return try {
            recognitionResult = ""
            isRecognizing = true

            val params = HashMap<String, Any>().apply {
                put("appid", APP_ID)
                put("apikey", API_KEY)
                put("secretkey", SECRET_KEY)
                put("cuid", getDeviceId())
                put("pid", 1537) // 普通话搜索模型
                put("vad", "dnn") // 高性能VAD
                put("vad.endpoint-timeout", 3000) // 3秒静音超时
                put("accept-audio-volume", true)
                put("disable-punctuation", true)
            }

            val json = JSONObject(params as Map<*, *>)
            asrManager?.send(SpeechConstant.ASR_START, json.toString(), null, 0, 0)
            Log.d(TAG, "启动对话模式语音识别，等待用户命令")
            true
        } catch (e: Exception) {
            Log.e(TAG, "启动对话模式语音识别失败", e)
            isRecognizing = false
            isInDialogMode = false
            currentVoiceSession?.isCompleted = true
            callback.onError("启动语音识别失败: ${e.message}")
            false
        }
    }
    
    private fun handleWakeupError(params: String) {
        try {
            val json = JSONObject(params)
            val errorCode = json.getInt("errorCode")
            val errorDesc = json.getString("errorDesc")
            Log.e(TAG, "唤醒错误: $errorCode, $errorDesc")
            callback.onRecognitionFailed("听不清，请再说一遍")
        } catch (e: JSONException) {
            Log.e(TAG, "唤醒错误解析失败", e)
            callback.onRecognitionFailed("听不清，请再说一遍")
        }
    }
    
    private fun handlePartialResult(params: String) {
        try {
            val json = JSONObject(params)
            if (json.has("results_recognition")) {
                val array = json.getJSONArray("results_recognition")
                if (array.length() > 0) {
                    recognitionResult = array.getString(0)
                    Log.d(TAG, "当前识别结果: $recognitionResult")
                }
            }
        } catch (e: JSONException) {
            Log.e(TAG, "解析识别结果出错", e)
        }
    }
    
    private fun handleFinalResult(params: String) {
        val wasInDialogMode = isInDialogMode
        isRecognizing = false
        callback.onRecognitionStateChanged(false)

        try {
            val json = JSONObject(params)
            val errorCode = json.optInt("error", 0)

            if (errorCode == 0 && recognitionResult.isNotEmpty()) {
                Log.d(TAG, "识别成功: $recognitionResult")

                if (wasInDialogMode) {
                    // 对话模式：处理用户在"我在"后说的命令
                    handleDialogModeCommand(recognitionResult)
                } else {
                    // 其他模式的处理（保留原有逻辑）
                    callback.onCommandRecognized(recognitionResult)
                }
            } else {
                Log.e(TAG, "识别失败或无结果: $params")
                if (wasInDialogMode) {
                    // 对话模式下识别失败
                    handleDialogModeFailure()
                } else {
                    callback.onRecognitionFailed("听不清，请再说一遍")
                }
            }
        } catch (e: JSONException) {
            Log.e(TAG, "解析识别结果出错", e)
            if (wasInDialogMode) {
                handleDialogModeFailure()
            } else {
                callback.onRecognitionFailed("听不清，请再说一遍")
            }
        }
    }

    /**
     * 处理对话模式下的命令（智能化增强版）
     * Enhanced dialog mode command handling with intelligent recognition
     */
    private fun handleDialogModeCommand(command: String) {
        Log.d(TAG, "对话模式命令识别: $command")

        // 智能命令验证：不仅检查精确匹配，还支持模糊匹配
        val isValidCommand = isValidVoiceCommand(command)

        if (isValidCommand) {
            // 播放提示音
            playBeepSound()

            // 执行命令
            callback.onCommandRecognized(command)

            // 更新成功识别统计
            updateRecognitionStats(true)
            logPerformanceMetrics("对话模式命令识别成功")

            Log.d(TAG, "对话模式命令执行成功: $command")
        } else {
            // 无效命令，播放错误提示
            Log.w(TAG, "对话模式无效命令: $command")
            speak("听不清，请再说一遍")

            // 更新失败识别统计
            updateRecognitionStats(false)
        }

        // 退出对话模式
        exitDialogMode()
    }

    /**
     * 智能验证语音命令有效性
     * Intelligently validate voice command validity
     */
    private fun isValidVoiceCommand(command: String): Boolean {
        // 1. 精确匹配检查
        if (OPERATION_WAKE_WORDS.any { command.contains(it) }) {
            return true
        }

        // 2. 扩展命令词检查（支持更多表达方式）
        val extendedCommands = arrayOf(
            // 播放控制扩展
            "开始播放", "继续", "play", "开始", "放歌", "开始放歌",
            "停止", "pause", "暂停一下", "停下", "别放了", "stop",
            "换一首", "下一个", "跳过", "切歌", "next", "skip",
            "上一个", "previous", "back", "上一曲", "返回上一首",

            // 音量控制扩展
            "声音大一点", "调高音量", "大声", "音量加", "volume up", "louder",
            "小声点", "音量减小", "小声", "音量减", "volume down", "quieter",

            // 播放模式扩展
            "乱序播放", "shuffle", "random", "随机", "打乱播放",
            "重复播放", "repeat", "loop", "循环", "单曲循环", "列表循环",

            // 高级功能
            "搜索", "找歌", "search", "播放.*", "我想听.*",
            "打开搜索", "回到首页", "打开设置", "显示播放列表",
            "添加到收藏", "下载", "查看歌词", "显示歌词"
        )

        return extendedCommands.any { pattern ->
            if (pattern.contains(".*")) {
                // 正则表达式匹配
                command.matches(Regex(pattern))
            } else {
                // 简单包含匹配
                command.contains(pattern, ignoreCase = true)
            }
        }
    }

    /**
     * 处理对话模式失败
     */
    private fun handleDialogModeFailure() {
        Log.w(TAG, "对话模式识别失败")
        speak("听不清，请再说一遍")
        exitDialogMode()
    }

    /**
     * 退出对话模式
     */
    private fun exitDialogMode() {
        isInDialogMode = false
        currentVoiceSession?.isCompleted = true
        currentVoiceSession = null
        Log.d(TAG, "退出对话模式，返回待机状态")
    }
    
    private fun handleASRError(params: String) {
        isRecognizing = false
        callback.onRecognitionStateChanged(false)

        try {
            val json = JSONObject(params)
            val errorCode = json.optInt("error", 0)
            val errorDesc = json.optString("desc", "未知错误")

            Log.e(TAG, "语音识别错误: $errorCode, $errorDesc")

            // 特殊处理认证失败错误
            when (errorCode) {
                -3004 -> {
                    Log.e(TAG, "认证失败！请检查以下配置：")
                    Log.e(TAG, "1. 百度控制台应用的AppID、API Key、Secret Key是否正确")
                    Log.e(TAG, "2. 百度控制台中的应用包名是否为：${context.packageName}")
                    Log.e(TAG, "3. 应用是否已启用语音识别服务")
                    Log.e(TAG, "4. 网络连接是否正常")

                    callback.onError("语音识别认证失败，请检查配置")
                    exitDialogMode()
                }
                -3001 -> {
                    Log.e(TAG, "网络连接失败，请检查网络设置")
                    callback.onError("网络连接失败")
                    exitDialogMode()
                }
                -3002 -> {
                    Log.e(TAG, "服务器内部错误")
                    callback.onError("服务器错误")
                    exitDialogMode()
                }
                else -> {
                    Log.e(TAG, "其他识别错误: $errorCode - $errorDesc")
                    // 只有非认证错误才播放语音提示
                    speak("听不清，请再说一遍")
                    exitDialogMode()
                }
            }

            callback.onRecognitionFailed("识别失败: $errorDesc")
        } catch (e: JSONException) {
            Log.e(TAG, "ASR错误解析失败: $params", e)
            speak("听不清，请再说一遍")
            exitDialogMode()
            callback.onRecognitionFailed("识别失败")
        }

        // 更新识别统计
        updateRecognitionStats(false)
    }

    /**
     * 记录性能指标
     * Log performance metrics
     */
    private fun logPerformanceMetrics(operation: String) {
        val currentTime = System.currentTimeMillis()
        val timeSinceLastOperation = if (lastRecognitionTime > 0) {
            currentTime - lastRecognitionTime
        } else 0L

        Log.d(TAG, "性能监控 - $operation: " +
                "识别次数=$recognitionCount, " +
                "成功次数=$successfulRecognitionCount, " +
                "成功率=${if (recognitionCount > 0) (successfulRecognitionCount * 100 / recognitionCount) else 0}%, " +
                "距离上次操作=${timeSinceLastOperation}ms")
    }

    /**
     * 更新识别统计
     * Update recognition statistics
     */
    private fun updateRecognitionStats(success: Boolean) {
        recognitionCount++
        if (success) {
            successfulRecognitionCount++
        }
        lastRecognitionTime = System.currentTimeMillis()
    }

    /**
     * 获取语音控制性能报告
     * Get voice control performance report
     */
    fun getPerformanceReport(): String {
        val successRate = if (recognitionCount > 0) {
            (successfulRecognitionCount * 100 / recognitionCount)
        } else 0

        return "语音控制性能报告:\n" +
                "总识别次数: $recognitionCount\n" +
                "成功识别次数: $successfulRecognitionCount\n" +
                "识别成功率: $successRate%\n" +
                "当前状态: ${if (isVoiceControlEnabled) "已启用" else "已禁用"}\n" +
                "对话模式: ${if (isInDialogMode) "活跃" else "待机"}"
    }

}

/**
 * 百度语音SDK回调接口
 * 支持智能语音控制的完整回调机制
 */
interface BaiduSpeechCallback {
    /**
     * 语音唤醒状态变化
     */
    fun onWakeupStateChanged(isActive: Boolean)

    /**
     * 检测到通用唤醒词（你好小聆、小聆同学）
     * 触发对话模式
     */
    fun onWakeWordDetected(word: String)

    /**
     * 语音识别状态变化
     */
    fun onRecognitionStateChanged(isRecognizing: Boolean)

    /**
     * 对话模式下识别到命令
     * 在用户说"你好小聆"后，再说的具体命令
     */
    fun onCommandRecognized(command: String)

    /**
     * 检测到操作唤醒词（播放、暂停等）
     * 直接执行相应操作
     */
    fun onDirectCommandRecognized(command: String)

    /**
     * 语音识别失败
     */
    fun onRecognitionFailed(error: String)

    /**
     * 语音合成状态变化
     */
    fun onSpeechSynthesisStateChanged(isSpeaking: Boolean)

    /**
     * 语音合成完成
     */
    fun onSpeechSynthesisCompleted()

    /**
     * 错误回调
     */
    fun onError(error: String)
}
