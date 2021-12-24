package com.raproject.alirohmansyah.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.raproject.alirohmansyah.R
import com.raproject.alirohmansyah.databinding.ItemListUsersBinding
import com.raproject.alirohmansyah.models.User

class UsersAdapter: PagingDataAdapter<User, UsersAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemListUsersBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.item = user
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: UsersAdapter.ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item!!)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_list_users,parent, false
            )
        )
    }

    companion object DiffCallback: DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(
                oldItem: User,
                newItem: User
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
                oldItem: User,
                newItem: User
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

}