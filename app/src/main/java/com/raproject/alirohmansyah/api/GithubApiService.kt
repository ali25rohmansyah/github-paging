package com.raproject.alirohmansyah.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val token =  "04c0775b550de556d0831d38db4ab08b96eff878"

interface GithubApiService {

    @GET("search/users")
    @Headers("Authorization: token $token")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemPerPage: Int
    ): UserSearchResponse
}