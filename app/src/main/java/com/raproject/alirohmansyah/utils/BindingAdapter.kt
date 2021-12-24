package com.raproject.alirohmansyah.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("showAvatar")
fun showAvatar(imgView: ImageView, url: String?){
    Glide.with(imgView.context)
        .load(url)
        .into(imgView)
}