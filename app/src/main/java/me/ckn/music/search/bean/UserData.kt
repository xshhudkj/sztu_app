package me.ckn.music.search.bean

import com.google.gson.annotations.SerializedName
import me.ckn.music.search.SearchAdapterBase

/**
 * 搜索用户数据
 *
 * Original: Created by wangchenyan.top on 2024/12/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：用户数据
 * File Description: User data
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
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
) : SearchAdapterBase.SearchItem {
    override val searchId: Long get() = userId
}
