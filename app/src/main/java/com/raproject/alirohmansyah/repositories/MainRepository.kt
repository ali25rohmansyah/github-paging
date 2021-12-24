package com.raproject.alirohmansyah.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.raproject.alirohmansyah.api.GithubApiService
import com.raproject.alirohmansyah.data.UsersDataSource
import com.raproject.alirohmansyah.models.User
import com.raproject.alirohmansyah.utils.Constants.QUERY_PAGE_SIZE
import javax.inject.Inject

class MainRepository @Inject constructor(
        private val githubApiService: GithubApiService,
) {

    fun getUsers(query: String): LiveData<PagingData<User>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = QUERY_PAGE_SIZE),
            pagingSourceFactory = {
                UsersDataSource(githubApiService, query)
            }
        ).liveData

    }
}