package me.ckn.music.account.bean

import com.google.gson.annotations.SerializedName

/**
 * 用户详情数据
 * 基于真实API返回结构：https://ncm.zhenxin.me/user/detail?uid=32953014
 * Created by wangchenyan.top on 2024/12/20.
 */
data class UserDetailData(
    val code: Int,
    val profile: ProfileData?,
    val level: Int?,
    val listenSongs: Int?,
    val userPoint: UserPointData?,
    val mobileSign: Boolean?,
    val pcSign: Boolean?,
    val createTime: Long?,
    val createDays: Int?,
    val newUser: Boolean?,
    val recallUser: Boolean?,
    val adValid: Boolean?,
    val peopleCanSeeMyPlayRecord: Boolean?
)

/**
 * 用户积分数据
 */
data class UserPointData(
    val userId: Long,
    val balance: Int,
    val updateTime: Long,
    val version: Int,
    val status: Int,
    val blockBalance: Int
)
