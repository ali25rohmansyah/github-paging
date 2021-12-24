package com.raproject.alirohmansyah.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raproject.alirohmansyah.R
import com.raproject.alirohmansyah.databinding.ItemNetworkStateBinding

class UsersLoadingAdapter(private val retry: () -> Unit): LoadStateAdapter<UsersLoadingAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemNetworkStateBinding): RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: UsersLoadingAdapter.ViewHolder, loadState: LoadState) {
        if (loadState is LoadState.Loading) {
            holder.binding.progressBarNetworkState.visibility = View.VISIBLE
            holder.binding.txtMessage.visibility = View.GONE
            holder.binding.btnRetry.visibility = View.GONE
        } else {
            holder.binding.progressBarNetworkState.visibility = View.GONE
        }

        if (loadState is LoadState.Error) {
            holder.binding.txtMessage.visibility = View.VISIBLE
            holder.binding.btnRetry.visibility = View.VISIBLE

            holder.binding.txtMessage.text = loadState.error.localizedMessage
        }

        if (loadState is LoadState.NotLoading && loadState.endOfPaginationReached) {
            holder.binding.txtMessage.visibility = View.VISIBLE

            holder.binding.txtMessage.text = "end of page"
        }

        holder.binding.btnRetry.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): UsersLoadingAdapter.ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_network_state,parent, false
            )
        )
    }
}