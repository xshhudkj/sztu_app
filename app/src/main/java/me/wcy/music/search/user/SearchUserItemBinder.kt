package me.wcy.music.search.user

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.launch
import me.wcy.music.account.service.UserService
import me.wcy.music.databinding.ItemSearchUserBinding
import me.wcy.music.search.SearchApi
import me.wcy.music.search.bean.UserData
import me.wcy.music.utils.ImageUtils.loadCover
import me.wcy.radapter3.RItemBinder
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.net.apiCall

/**
 * 搜索用户ItemBinder
 * Created by wangchenyan.top on 2024/12/20.
 */
class SearchUserItemBinder(
    private val lifecycleScope: LifecycleCoroutineScope,
    private val userService: UserService,
    private val context: Context
) : RItemBinder<ItemSearchUserBinding, UserData>() {

    override fun onBind(viewBinding: ItemSearchUserBinding, item: UserData, position: Int) {
        viewBinding.tvUserName.text = item.nickname
        viewBinding.tvUserDesc.text = if (item.signature.isNotEmpty()) {
            item.signature
        } else {
            "用户"
        }
        viewBinding.ivUserAvatar.loadCover(item.avatarUrl, 28)

        // 更新关注状态
        updateFollowState(viewBinding, item.followed)

        // 关注按钮点击事件
        viewBinding.btnFollow.setOnClickListener {
            followUser(viewBinding, item)
        }

        viewBinding.root.setOnClickListener {
            // TODO: 跳转到用户详情页面
        }
    }

    private fun updateFollowState(viewBinding: ItemSearchUserBinding, isFollowed: Boolean) {
        if (isFollowed) {
            viewBinding.btnFollow.text = "已关注"
            viewBinding.btnFollow.isSelected = true
        } else {
            viewBinding.btnFollow.text = "关注"
            viewBinding.btnFollow.isSelected = false
        }
    }

    private fun followUser(viewBinding: ItemSearchUserBinding, user: UserData) {
        // 检查登录状态
        userService.checkLogin(context as Activity) {
            lifecycleScope.launch {
                try {
                    val t = if (user.followed) 0 else 1 // 1为关注，0为取消关注
                    val result = apiCall { SearchApi.get().followUser(user.userId, t) }
                    if (result.isSuccess()) {
                        user.followed = !user.followed
                        updateFollowState(viewBinding, user.followed)
                        val message = if (user.followed) "关注成功" else "取消关注成功"
                        toast(message)
                    } else {
                        toast(result.msg ?: "操作失败")
                    }
                } catch (e: Exception) {
                    toast("网络错误")
                }
            }
        }
    }
}
