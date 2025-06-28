package com.madarsoft.task.presentation.userslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.madarsoft.task.R
import com.madarsoft.task.databinding.ItemUserBinding
import com.madarsoft.task.domain.model.User
import com.madarsoft.task.utils.errors.generateReadableLightColor
import com.madarsoft.task.utils.errors.toColorStateList

class UsersRecyclerAdapter : RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>() {
    private val users = mutableListOf<User>()
    fun submitList(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): UserViewHolder {
        val binding = DataBindingUtil.inflate<ItemUserBinding>(
            LayoutInflater.from(parent.context), R.layout.item_user, parent, false
        ).apply {
            executePendingBindings()
        }
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: UserViewHolder, position: Int
    ) {
        holder.binding.item = users[position]
        holder.binding.firstLetter.backgroundTintList = generateReadableLightColor().toColorStateList()
    }

    override fun getItemCount() = users.size

    class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}