package me.ckn.music.voice

import android.util.Log
import me.ckn.music.storage.preference.ConfigPreferences
import kotlin.math.min

/**
 * WhisperPlay Music Player - 智能语义解析引擎
 *
 * 文件描述：智能语音命令解析器，支持模糊匹配、多语言混合和上下文理解
 * File Description: Intelligent voice command parser with fuzzy matching, multilingual support and context awareness
 *
 * @author ckn
 * @since 2025-06-20
 * @version 2.3.0
 */
class SemanticParser {
    
    companion object {
        private const val TAG = "SemanticParser"
        
        // 相似度阈值
        private const val SIMILARITY_THRESHOLD = 0.6f
        private const val EXACT_MATCH_THRESHOLD = 0.9f
    }
    
    /**
     * 语音命令类型
     */
    enum class CommandType {
        PLAY_CONTROL,      // 播放控制
        VOLUME_CONTROL,    // 音量控制
        NAVIGATION,        // 页面导航
        SEARCH,           // 搜索功能
        PLAYLIST_MANAGE,  // 播放列表管理
        PLAYBACK_MODE,    // 播放模式
        UNKNOWN           // 未知命令
    }
    
    /**
     * 解析结果
     */
    data class ParseResult(
        val commandType: CommandType,
        val action: String,
        val parameters: Map<String, String> = emptyMap(),
        val confidence: Float = 0.0f,
        val originalText: String = ""
    )
    
    // 基础播放控制命令词库
    private val playControlCommands = mapOf(
        "播放" to listOf("播放", "开始播放", "继续", "play", "开始", "放歌", "继续播放"),
        "暂停" to listOf("暂停", "停止", "pause", "暂停一下", "停下", "别放了", "停止播放"),
        "下一首" to listOf("下一首", "下一个", "跳过", "切歌", "next", "下一曲", "换一首"),
        "上一首" to listOf("上一首", "上一个", "previous", "上一曲", "回到上一首", "前一首"),
        "随机播放" to listOf("随机播放", "随机", "乱序播放", "shuffle", "打乱播放"),
        "循环播放" to listOf("循环播放", "循环", "重复播放", "repeat", "单曲循环", "列表循环")
    )
    
    // 音量控制命令词库
    private val volumeControlCommands = mapOf(
        "增大音量" to listOf("声音大一点", "调高音量", "大声", "音量增大", "增大音量", "音量加大", "声音调高"),
        "减小音量" to listOf("小声点", "音量减小", "小声", "减小音量", "音量调小", "声音调小", "音量降低")
    )
    
    // 搜索功能命令词库
    private val searchCommands = mapOf(
        "搜索歌曲" to listOf("播放", "我想听", "放一首", "搜索", "找一首", "来一首", "听一下", "放个", "来个"),
        "搜索歌手" to listOf("播放.*的歌", "我想听.*", "放.*的音乐", ".*的歌曲", ".*的音乐", "听.*唱的", ".*唱的歌")
    )
    
    // 页面导航命令词库
    private val navigationCommands = mapOf(
        "打开搜索" to listOf("打开搜索", "搜索页面", "去搜索", "搜索音乐"),
        "回到首页" to listOf("回到首页", "返回首页", "主页", "回到主页"),
        "打开设置" to listOf("打开设置", "设置页面", "去设置", "设置选项"),
        "显示播放列表" to listOf("显示播放列表", "播放列表", "歌单", "当前播放列表")
    )
    
    // 播放列表管理命令词库
    private val playlistCommands = mapOf(
        "添加到收藏" to listOf("添加到收藏", "收藏这首歌", "加入收藏", "收藏"),
        "下载这首歌" to listOf("下载这首歌", "下载当前歌曲", "保存这首歌", "下载"),
        "查看歌词" to listOf("查看歌词", "显示歌词", "歌词", "看歌词")
    )
    
    /**
     * 解析语音命令（使用配置的灵敏度设置）
     */
    fun parseCommand(text: String): ParseResult {
        Log.d(TAG, "开始解析语音命令: $text")

        val cleanText = text.trim().lowercase()
        if (cleanText.isEmpty()) {
            return ParseResult(CommandType.UNKNOWN, "", confidence = 0.0f, originalText = text)
        }

        // 获取用户配置的灵敏度阈值
        val sensitivityThreshold = ConfigPreferences.voiceSensitivity
        val fuzzyMatchingEnabled = ConfigPreferences.voiceFuzzyMatching

        Log.d(TAG, "使用灵敏度阈值: $sensitivityThreshold, 模糊匹配: $fuzzyMatchingEnabled")

        // 1. 尝试精确匹配
        val exactMatch = findExactMatch(cleanText)
        if (exactMatch != null) {
            Log.d(TAG, "精确匹配成功: ${exactMatch.action}")
            return exactMatch.copy(originalText = text)
        }

        // 2. 尝试模糊匹配（如果启用）
        if (fuzzyMatchingEnabled) {
            val fuzzyMatch = findFuzzyMatch(cleanText)
            if (fuzzyMatch != null && fuzzyMatch.confidence >= sensitivityThreshold) {
                Log.d(TAG, "模糊匹配成功: ${fuzzyMatch.action}, 相似度: ${fuzzyMatch.confidence}")
                return fuzzyMatch.copy(originalText = text)
            }
        }

        // 3. 尝试语义匹配
        val semanticMatch = findSemanticMatch(cleanText)
        if (semanticMatch != null) {
            Log.d(TAG, "语义匹配成功: ${semanticMatch.action}")
            return semanticMatch.copy(originalText = text)
        }

        Log.w(TAG, "未能识别的语音命令: $text")
        return ParseResult(CommandType.UNKNOWN, "", confidence = 0.0f, originalText = text)
    }
    
    /**
     * 精确匹配
     */
    private fun findExactMatch(text: String): ParseResult? {
        // 检查所有命令词库
        val allCommands = listOf(
            playControlCommands to CommandType.PLAY_CONTROL,
            volumeControlCommands to CommandType.VOLUME_CONTROL,
            navigationCommands to CommandType.NAVIGATION,
            playlistCommands to CommandType.PLAYLIST_MANAGE
        )
        
        for ((commands, type) in allCommands) {
            for ((action, keywords) in commands) {
                for (keyword in keywords) {
                    if (text == keyword.lowercase() || text.contains(keyword.lowercase())) {
                        return ParseResult(
                            commandType = type,
                            action = action,
                            confidence = 1.0f
                        )
                    }
                }
            }
        }
        
        return null
    }
    
    /**
     * 模糊匹配（基于编辑距离）
     */
    private fun findFuzzyMatch(text: String): ParseResult? {
        var bestMatch: ParseResult? = null
        var bestSimilarity = 0.0f
        
        val allCommands = listOf(
            playControlCommands to CommandType.PLAY_CONTROL,
            volumeControlCommands to CommandType.VOLUME_CONTROL,
            navigationCommands to CommandType.NAVIGATION,
            playlistCommands to CommandType.PLAYLIST_MANAGE
        )
        
        for ((commands, type) in allCommands) {
            for ((action, keywords) in commands) {
                for (keyword in keywords) {
                    val similarity = calculateSimilarity(text, keyword.lowercase())
                    if (similarity > bestSimilarity) {
                        bestSimilarity = similarity
                        bestMatch = ParseResult(
                            commandType = type,
                            action = action,
                            confidence = similarity
                        )
                    }
                }
            }
        }
        
        return bestMatch
    }
    
    /**
     * 语义匹配（基于关键词和上下文）
     */
    private fun findSemanticMatch(text: String): ParseResult? {
        // 搜索歌曲/歌手的语义匹配
        if (text.contains("播放") || text.contains("我想听") || text.contains("放")) {
            // 提取歌曲名或歌手名
            val songName = extractSongName(text)
            if (songName.isNotEmpty()) {
                return ParseResult(
                    commandType = CommandType.SEARCH,
                    action = "搜索歌曲",
                    parameters = mapOf("query" to songName),
                    confidence = 0.8f
                )
            }
        }
        
        // 音量控制的语义匹配
        if (text.contains("音量") || text.contains("声音")) {
            return when {
                text.contains("大") || text.contains("高") || text.contains("增") -> {
                    ParseResult(CommandType.VOLUME_CONTROL, "增大音量", confidence = 0.7f)
                }
                text.contains("小") || text.contains("低") || text.contains("减") -> {
                    ParseResult(CommandType.VOLUME_CONTROL, "减小音量", confidence = 0.7f)
                }
                else -> null
            }
        }
        
        return null
    }
    
    /**
     * 计算字符串相似度（基于编辑距离）
     */
    private fun calculateSimilarity(s1: String, s2: String): Float {
        val distance = levenshteinDistance(s1, s2)
        val maxLength = maxOf(s1.length, s2.length)
        return if (maxLength == 0) 1.0f else 1.0f - distance.toFloat() / maxLength
    }
    
    /**
     * 计算编辑距离
     */
    private fun levenshteinDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
        
        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j
        
        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,      // 删除
                    dp[i][j - 1] + 1,      // 插入
                    dp[i - 1][j - 1] + cost // 替换
                )
            }
        }
        
        return dp[s1.length][s2.length]
    }
    
    /**
     * 从文本中提取歌曲名（增强版）
     */
    private fun extractSongName(text: String): String {
        // 增强的歌曲名提取逻辑，支持更多语音表达方式
        val patterns = listOf(
            "播放(.+)".toRegex(),
            "我想听(.+)".toRegex(),
            "放一首(.+)".toRegex(),
            "放个(.+)".toRegex(),
            "来一首(.+)".toRegex(),
            "来个(.+)".toRegex(),
            "听一下(.+)".toRegex(),
            "搜索(.+)".toRegex(),
            "找一首(.+)".toRegex(),
            "放(.+)".toRegex()
        )

        for (pattern in patterns) {
            val match = pattern.find(text)
            if (match != null && match.groupValues.size > 1) {
                var songName = match.groupValues[1].trim()

                // 清理常见的后缀词
                songName = songName.replace("这首歌", "")
                    .replace("歌曲", "")
                    .replace("音乐", "")
                    .replace("的歌", "")
                    .replace("吧", "")
                    .trim()

                if (songName.isNotEmpty()) {
                    return songName
                }
            }
        }

        return ""
    }

    /**
     * 智能上下文理解（根据应用状态优化解析结果）
     */
    fun parseCommandWithContext(text: String, isPlaying: Boolean = false, currentPage: String = ""): ParseResult {
        // 检查是否启用智能上下文理解
        val smartContextEnabled = ConfigPreferences.voiceSmartContext

        if (!smartContextEnabled) {
            // 如果禁用智能上下文，直接使用基础解析
            return parseCommand(text)
        }

        val baseResult = parseCommand(text)

        // 根据上下文调整解析结果
        if (baseResult.commandType == CommandType.UNKNOWN) {
            // 如果基础解析失败，尝试上下文相关的解析
            val contextResult = parseWithContext(text, isPlaying, currentPage)
            if (contextResult != null) {
                Log.d(TAG, "上下文解析成功: ${contextResult.action}")
                return contextResult
            }
        }

        return baseResult
    }

    /**
     * 基于上下文的解析
     */
    private fun parseWithContext(text: String, isPlaying: Boolean, currentPage: String): ParseResult? {
        val cleanText = text.trim().lowercase()

        // 在播放页面的上下文解析
        if (currentPage == "playing") {
            when {
                cleanText.contains("收藏") || cleanText.contains("喜欢") -> {
                    return ParseResult(CommandType.PLAYLIST_MANAGE, "添加到收藏", confidence = 0.8f)
                }
                cleanText.contains("下载") || cleanText.contains("保存") -> {
                    return ParseResult(CommandType.PLAYLIST_MANAGE, "下载这首歌", confidence = 0.8f)
                }
                cleanText.contains("歌词") -> {
                    return ParseResult(CommandType.PLAYLIST_MANAGE, "查看歌词", confidence = 0.8f)
                }
            }
        }

        // 在搜索页面的上下文解析
        if (currentPage == "search") {
            if (cleanText.contains("搜索") || cleanText.contains("找")) {
                val query = extractSongName(cleanText)
                if (query.isNotEmpty()) {
                    return ParseResult(
                        CommandType.SEARCH,
                        "搜索歌曲",
                        parameters = mapOf("query" to query),
                        confidence = 0.8f
                    )
                }
            }
        }

        // 播放状态相关的上下文解析
        if (isPlaying) {
            when {
                cleanText.contains("停") || cleanText.contains("暂停") -> {
                    return ParseResult(CommandType.PLAY_CONTROL, "暂停", confidence = 0.9f)
                }
                cleanText.contains("继续") || cleanText.contains("播放") -> {
                    return ParseResult(CommandType.PLAY_CONTROL, "播放", confidence = 0.9f)
                }
            }
        } else {
            if (cleanText.contains("播放") || cleanText.contains("开始")) {
                return ParseResult(CommandType.PLAY_CONTROL, "播放", confidence = 0.9f)
            }
        }

        return null
    }
}
