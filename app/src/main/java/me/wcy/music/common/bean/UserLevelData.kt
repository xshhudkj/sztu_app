package me.wcy.music.common.bean

import com.google.gson.annotations.SerializedName

/**
 * 用户等级信息数据模型
 * Created by wangchenyan.top on 2024/12/21.
 */
data class UserLevelData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("data")
    val data: LevelInfo? = null
)

data class LevelInfo(
    @SerializedName("userId")
    val userId: Long = 0,
    @SerializedName("level")
    val level: Int = 0,
    @SerializedName("nowPlayCount")
    val nowPlayCount: Int = 0,
    @SerializedName("nowLoginCount")
    val nowLoginCount: Int = 0,
    @SerializedName("nextPlayCount")
    val nextPlayCount: Int = 0,
    @SerializedName("nextLoginCount")
    val nextLoginCount: Int = 0,
    @SerializedName("progress")
    val progress: Int = 0
)
