package me.wcy.music.search.bean

import com.google.gson.annotations.SerializedName

/**
 * 搜索用户数据
 * Created by wangchenyan.top on 2024/12/20.
 */
data class UserData(
    @SerializedName("userId")
    val userId: Long = 0,
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("avatarUrl")
    val avatarUrl: String = "",
    @SerializedName("signature")
    val signature: String = "",
    @SerializedName("userType")
    val userType: Int = 0,
    @SerializedName("followed")
    var followed: Boolean = false,
    @SerializedName("followeds")
    val followeds: Int = 0,
    @SerializedName("follows")
    val follows: Int = 0,
)
