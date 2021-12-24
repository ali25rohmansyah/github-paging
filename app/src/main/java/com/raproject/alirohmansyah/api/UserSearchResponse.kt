package com.raproject.alirohmansyah.api

import com.google.gson.annotations.SerializedName
import com.raproject.alirohmansyah.models.User

data class UserSearchResponse(
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean?,
    @SerializedName("items")
    var items: List<User>,
    @SerializedName("total_count")
    var totalCount: Int
)