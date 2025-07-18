package me.ckn.music.search.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.ckn.music.databinding.ItemSearchUserBinding
import me.ckn.music.search.SearchAdapterBase
import me.ckn.music.search.bean.UserData
import me.ckn.music.utils.ImageUtils.loadCover

/**
 * 搜索用户适配器
 *
 * Original: Created by wangchenyan.top on 2024/12/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：搜索用户列表适配器
 * File Description: Search user list adapter
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
class SearchUserAdapter : SearchAdapterBase<UserData, SearchUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemSearchUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserData) {
            binding.tvUserName.text = user.nickname
            binding.tvUserDesc.text = if (user.signature.isNotEmpty()) {
                user.signature
            } else {
                "用户"
            }
            binding.ivUserAvatar.loadCover(user.avatarUrl, 28)
            
            binding.root.setOnClickListener {
                // TODO: 跳转到用户详情页面
            }
        }
    }
}
