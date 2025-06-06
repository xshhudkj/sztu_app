package me.wcy.music.common.bean

import com.google.gson.annotations.SerializedName

/**
 * VIP信息数据模型
 * Created by wangchenyan.top on 2024/12/21.
 */
data class VipData(
    @SerializedName("isVip")
    val isVip: Boolean = false,
    @SerializedName("vipType")
    val vipType: Int = 0,
    @SerializedName("vipLevel")
    val vipLevel: Int = 0,
    @SerializedName("vipLevelIcon")
    val vipLevelIcon: String = "",
    @SerializedName("redVipLevel")
    val redVipLevel: Int = 0,
    @SerializedName("redVipLevelIcon")
    val redVipLevelIcon: String = "",
    @SerializedName("associator")
    val associator: VipAssociator? = null
)

data class VipAssociator(
    @SerializedName("vipCode")
    val vipCode: Int = 0,
    @SerializedName("rights")
    val rights: Boolean = false
)

data class VipInfoData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("data")
    val data: VipData? = null
)
