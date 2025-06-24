package me.ckn.music.search.user

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.launch
import me.ckn.music.account.service.UserService
import me.ckn.music.databinding.ItemSearchUserBinding
import me.ckn.music.search.SearchApi
import me.ckn.music.search.bean.UserData
import me.ckn.music.utils.ImageUtils.loadCover
import me.wcy.radapter3.RItemBinder
import top.wangchenyan.common.ext.toast
import top.wangchenyan.common.net.apiCall

/**
 * 搜索用户ItemBinder
 *
 * Original: Created by wangchenyan.top on 2024/12/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：搜索用户列表项绑定器
 * File Description: Search user item binder
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
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
