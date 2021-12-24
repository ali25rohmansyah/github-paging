package com.raproject.alirohmansyah.models

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int?,
    var avatar_url: String?,
    @SerializedName("login")
    var username: String?,
)
