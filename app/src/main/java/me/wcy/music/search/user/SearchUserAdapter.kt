package me.wcy.music.search.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.wcy.music.databinding.ItemSearchUserBinding
import me.wcy.music.search.bean.UserData
import me.wcy.music.utils.ImageUtils.loadCover

/**
 * 搜索用户适配器
 * Created by wangchenyan.top on 2024/12/20.
 */
class SearchUserAdapter : ListAdapter<UserData, SearchUserAdapter.ViewHolder>(DiffCallback()) {

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

    private class DiffCallback : DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem == newItem
        }
    }
}
