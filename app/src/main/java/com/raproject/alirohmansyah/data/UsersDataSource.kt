package com.raproject.alirohmansyah.data

import androidx.paging.PagingSource
import com.raproject.alirohmansyah.api.GithubApiService
import com.raproject.alirohmansyah.models.User
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
private const val IN_QUALIFIER = "in:login"


class UsersDataSource(
    private val githubApiService: GithubApiService,
    private val query: String
): PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key?: STARTING_PAGE_INDEX
        val apiQuery = query + IN_QUALIFIER
        return try {
            val response = githubApiService.searchUsers(apiQuery, page, params.loadSize)
            val users = response.items
            LoadResult.Page(
                data = users,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (users.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}