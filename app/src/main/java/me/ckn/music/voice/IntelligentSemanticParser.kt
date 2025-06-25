package me.ckn.music.voice

import android.util.Log
import kotlin.math.min

/**
 * WhisperPlay Music Player - 智能语义解析引擎
 *
 * 文件描述：智能语音命令解析器，支持模糊匹配、多语言混合和上下文感知
 * File Description: Intelligent semantic parser for voice commands with fuzzy matching and context awareness
 *
 * @author ckn
 * @since 2025-06-20
 * @version 2.4.0
 */
class IntelligentSemanticParser {
    
    companion object {
        private const val TAG = "IntelligentSemanticParser"
        
        // 相似度阈值
        private const val SIMILARITY_THRESHOLD = 0.6f
        private const val HIGH_SIMILARITY_THRESHOLD = 0.8f
        
        // 命令权重
        private const val EXACT_MATCH_WEIGHT = 1.0f
        private const val FUZZY_MATCH_WEIGHT = 0.8f
        private const val CONTEXT_BOOST_WEIGHT = 0.2f
    }
    
    // 智能命令映射表
    private val intelligentCommandMap = mapOf(
        // 基础播放控制（支持多种表达方式）
        VoiceCommand.PLAY to listOf(
            "播放", "开始播放", "继续", "play", "开始", "放歌", "开始放歌",
            "继续播放", "恢复播放", "resume", "start", "go"
        ),
        VoiceCommand.PAUSE to listOf(
            "暂停", "停止", "pause", "暂停一下", "停下", "别放了", "stop",
            "停止播放", "暂停播放", "hold", "wait"
        ),
        VoiceCommand.NEXT to listOf(
            "下一首", "换一首", "下一个", "跳过", "切歌", "next", "skip",
            "下一曲", "换歌", "切换", "forward", "下首歌"
        ),
        VoiceCommand.PREVIOUS to listOf(
            "上一首", "上一个", "previous", "back", "上一曲", "返回上一首",
            "回到上一首", "prev", "backward"
        ),
        
        // 音量控制
        VoiceCommand.VOLUME_UP to listOf(
            "增大音量", "声音大一点", "调高音量", "大声", "音量加",
            "volume up", "louder", "turn up", "声音调大"
        ),
        VoiceCommand.VOLUME_DOWN to listOf(
            "减小音量", "小声点", "音量减小", "小声", "音量减",
            "volume down", "quieter", "turn down", "声音调小"
        ),
        
        // 播放模式
        VoiceCommand.SHUFFLE to listOf(
            "随机播放", "乱序播放", "shuffle", "random", "随机",
            "打乱播放", "随机模式", "乱序"
        ),
        VoiceCommand.REPEAT to listOf(
            "循环播放", "重复播放", "repeat", "loop", "循环",
            "单曲循环", "列表循环", "重复"
        ),
        
        // 高级功能指令
        VoiceCommand.SEARCH_SONG to listOf(
            "播放", "我想听", "放一首", "搜索", "找歌", "search",
            "play song", "find song", "查找歌曲"
        ),
        VoiceCommand.SEARCH_ARTIST to listOf(
            "播放.*的歌", "我想听.*", "放.*的音乐", ".*的歌曲",
            "artist", "singer", "歌手"
        ),
        
        // 页面导航
        VoiceCommand.NAVIGATE_SEARCH to listOf(
            "打开搜索", "搜索页面", "去搜索", "search page",
            "open search", "搜索界面"
        ),
        VoiceCommand.NAVIGATE_HOME to listOf(
            "回到首页", "返回主页", "go home", "home page",
            "主页", "首页", "回家"
        ),
        VoiceCommand.NAVIGATE_SETTINGS to listOf(
            "打开设置", "设置页面", "settings", "preferences",
            "配置", "选项", "设置界面"
        ),
        
        // 播放列表管理
        VoiceCommand.SHOW_PLAYLIST to listOf(
            "显示播放列表", "播放列表", "playlist", "show list",
            "当前列表", "歌单", "列表"
        ),
        VoiceCommand.ADD_TO_FAVORITE to listOf(
            "添加到收藏", "收藏", "喜欢", "favorite", "like",
            "加入收藏", "收藏这首歌", "我喜欢"
        ),
        VoiceCommand.DOWNLOAD_SONG to listOf(
            "下载这首歌", "下载", "download", "保存",
            "下载当前歌曲", "缓存"
        ),
        VoiceCommand.SHOW_LYRICS to listOf(
            "查看歌词", "显示歌词", "歌词", "lyrics",
            "看歌词", "歌词页面"
        )
    )
    
    /**
     * 解析语音命令
     * @param input 用户语音输入
     * @param context 当前上下文信息
     * @return 解析结果
     */
    fun parseCommand(input: String, context: VoiceContext = VoiceContext()): ParseResult {
        val cleanInput = input.trim().lowercase()
        Log.d(TAG, "解析语音命令: '$cleanInput', 上下文: $context")
        
        if (cleanInput.isEmpty()) {
            return ParseResult.failure("输入为空")
        }
        
        // 1. 精确匹配
        val exactMatch = findExactMatch(cleanInput)
        if (exactMatch != null) {
            Log.d(TAG, "精确匹配成功: ${exactMatch.command}")
            return ParseResult.success(exactMatch.command, exactMatch.parameters, 1.0f)
        }
        
        // 2. 模糊匹配
        val fuzzyMatch = findFuzzyMatch(cleanInput, context)
        if (fuzzyMatch != null && fuzzyMatch.confidence >= SIMILARITY_THRESHOLD) {
            Log.d(TAG, "模糊匹配成功: ${fuzzyMatch.command}, 置信度: ${fuzzyMatch.confidence}")
            return ParseResult.success(fuzzyMatch.command, fuzzyMatch.parameters, fuzzyMatch.confidence)
        }
        
        // 3. 智能推断（基于上下文）
        val contextMatch = inferFromContext(cleanInput, context)
        if (contextMatch != null) {
            Log.d(TAG, "上下文推断成功: ${contextMatch.command}")
            return ParseResult.success(contextMatch.command, contextMatch.parameters, contextMatch.confidence)
        }
        
        Log.w(TAG, "无法解析命令: '$cleanInput'")
        return ParseResult.failure("无法识别的命令")
    }
    
    /**
     * 精确匹配
     */
    private fun findExactMatch(input: String): MatchResult? {
        for ((command, patterns) in intelligentCommandMap) {
            for (pattern in patterns) {
                if (input == pattern.lowercase() || input.contains(pattern.lowercase())) {
                    // 检查是否包含参数（如歌曲名、歌手名）
                    val parameters = extractParameters(input, pattern, command)
                    return MatchResult(command, parameters, EXACT_MATCH_WEIGHT)
                }
            }
        }
        return null
    }
    
    /**
     * 模糊匹配（使用编辑距离算法）
     */
    private fun findFuzzyMatch(input: String, context: VoiceContext): MatchResult? {
        var bestMatch: MatchResult? = null
        var bestSimilarity = 0f
        
        for ((command, patterns) in intelligentCommandMap) {
            for (pattern in patterns) {
                val similarity = calculateSimilarity(input, pattern.lowercase())
                
                // 应用上下文权重
                val contextBoost = getContextBoost(command, context)
                val adjustedSimilarity = similarity + contextBoost
                
                if (adjustedSimilarity > bestSimilarity && adjustedSimilarity >= SIMILARITY_THRESHOLD) {
                    val parameters = extractParameters(input, pattern, command)
                    bestMatch = MatchResult(command, parameters, adjustedSimilarity)
                    bestSimilarity = adjustedSimilarity
                }
            }
        }
        
        return bestMatch
    }
    
    /**
     * 计算字符串相似度（编辑距离算法）
     */
    private fun calculateSimilarity(s1: String, s2: String): Float {
        val distance = editDistance(s1, s2)
        val maxLength = maxOf(s1.length, s2.length)
        return if (maxLength == 0) 1f else 1f - distance.toFloat() / maxLength
    }
    
    /**
     * 编辑距离算法
     */
    private fun editDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
        
        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j
        
        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                dp[i][j] = if (s1[i - 1] == s2[j - 1]) {
                    dp[i - 1][j - 1]
                } else {
                    1 + minOf(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1])
                }
            }
        }
        
        return dp[s1.length][s2.length]
    }
    
    /**
     * 基于上下文推断命令
     */
    private fun inferFromContext(input: String, context: VoiceContext): MatchResult? {
        // 根据当前页面状态推断用户意图
        when (context.currentPage) {
            "playing" -> {
                // 在播放页面，优先考虑播放控制命令
                if (input.contains("歌词") || input.contains("lyrics")) {
                    return MatchResult(VoiceCommand.SHOW_LYRICS, emptyMap(), 0.7f)
                }
            }
            "search" -> {
                // 在搜索页面，优先考虑搜索相关命令
                return MatchResult(VoiceCommand.SEARCH_SONG, mapOf("query" to input), 0.7f)
            }
        }
        
        // 根据播放状态推断
        if (context.isPlaying == false && (input.contains("播放") || input.contains("play"))) {
            return MatchResult(VoiceCommand.PLAY, emptyMap(), 0.8f)
        } else if (context.isPlaying == true && (input.contains("停") || input.contains("pause"))) {
            return MatchResult(VoiceCommand.PAUSE, emptyMap(), 0.8f)
        }
        
        return null
    }
    
    /**
     * 获取上下文权重加成
     */
    private fun getContextBoost(command: VoiceCommand, context: VoiceContext): Float {
        var boost = 0f
        
        // 根据当前播放状态给予权重加成
        when (command) {
            VoiceCommand.PLAY -> if (context.isPlaying == false) boost += CONTEXT_BOOST_WEIGHT
            VoiceCommand.PAUSE -> if (context.isPlaying == true) boost += CONTEXT_BOOST_WEIGHT
            VoiceCommand.SHOW_LYRICS -> if (context.currentPage == "playing") boost += CONTEXT_BOOST_WEIGHT
            else -> {}
        }
        
        return boost
    }
    
    /**
     * 提取命令参数
     */
    private fun extractParameters(input: String, pattern: String, command: VoiceCommand): Map<String, String> {
        val parameters = mutableMapOf<String, String>()
        
        when (command) {
            VoiceCommand.SEARCH_SONG, VoiceCommand.SEARCH_ARTIST -> {
                // 提取歌曲名或歌手名
                val query = extractSearchQuery(input, pattern)
                if (query.isNotEmpty()) {
                    parameters["query"] = query
                }
            }
            else -> {}
        }
        
        return parameters
    }
    
    /**
     * 提取搜索查询词
     */
    private fun extractSearchQuery(input: String, pattern: String): String {
        // 移除命令词，提取剩余部分作为搜索词
        val cleanPattern = pattern.replace(".*", "").trim()
        return input.replace(cleanPattern, "").trim()
    }
}

/**
 * 语音命令枚举
 */
enum class VoiceCommand {
    // 基础播放控制
    PLAY, PAUSE, NEXT, PREVIOUS,
    
    // 音量控制
    VOLUME_UP, VOLUME_DOWN,
    
    // 播放模式
    SHUFFLE, REPEAT,
    
    // 搜索功能
    SEARCH_SONG, SEARCH_ARTIST,
    
    // 页面导航
    NAVIGATE_SEARCH, NAVIGATE_HOME, NAVIGATE_SETTINGS,
    
    // 播放列表管理
    SHOW_PLAYLIST, ADD_TO_FAVORITE, DOWNLOAD_SONG, SHOW_LYRICS
}

/**
 * 语音上下文信息
 */
data class VoiceContext(
    val currentPage: String = "",
    val isPlaying: Boolean? = null,
    val currentSong: String = "",
    val playMode: String = ""
)

/**
 * 匹配结果
 */
data class MatchResult(
    val command: VoiceCommand,
    val parameters: Map<String, String>,
    val confidence: Float
)

/**
 * 解析结果
 */
data class ParseResult(
    val success: Boolean,
    val command: VoiceCommand?,
    val parameters: Map<String, String>,
    val confidence: Float,
    val errorMessage: String?
) {
    companion object {
        fun success(command: VoiceCommand, parameters: Map<String, String> = emptyMap(), confidence: Float = 1.0f): ParseResult {
            return ParseResult(true, command, parameters, confidence, null)
        }
        
        fun failure(errorMessage: String): ParseResult {
            return ParseResult(false, null, emptyMap(), 0f, errorMessage)
        }
    }
}
