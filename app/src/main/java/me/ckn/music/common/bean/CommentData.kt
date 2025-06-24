package me.ckn.music.common.bean

import com.google.gson.annotations.SerializedName

/**
 * 评论数据模型
 * Comment data model
 */
data class CommentData(
    @SerializedName("commentId")
    val commentId: Long = 0,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("time")
    val time: Long = 0,
    @SerializedName("likedCount")
    val likedCount: Int = 0,
    @SerializedName("liked")
    val liked: Boolean = false,
    @SerializedName("user")
    val user: CommentUserData = CommentUserData(),
    @SerializedName("beReplied")
    val beReplied: List<CommentData> = emptyList(),
    @SerializedName("showFloorComment")
    val showFloorComment: Boolean = false,
    @SerializedName("floorComment")
    val floorComment: CommentData? = null,
    @SerializedName("replyCount")
    val replyCount: Int = 0,
    @SerializedName("ipLocation")
    val ipLocation: String = ""
)

/**
 * 评论用户数据
 * Comment user data
 */
data class CommentUserData(
    @SerializedName("userId")
    val userId: Long = 0,
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("avatarUrl")
    val avatarUrl: String = "",
    @SerializedName("vipType")
    val vipType: Int = 0,
    @SerializedName("authStatus")
    val authStatus: Int = 0,
    @SerializedName("followed")
    val followed: Boolean = false
)

/**
 * 评论列表数据
 * Comment list data
 */
data class CommentListData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("comments")
    val comments: List<CommentData> = emptyList(),
    @SerializedName("totalCount")
    val totalCount: Int = 0,
    @SerializedName("hasMore")
    val hasMore: Boolean = false,
    @SerializedName("cursor")
    val cursor: String = ""
)

/**
 * 热门评论数据
 * Hot comment data
 */
data class HotCommentListData(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("hotComments")
    val hotComments: List<CommentData> = emptyList(),
    @SerializedName("totalCount")
    val totalCount: Int = 0
) 