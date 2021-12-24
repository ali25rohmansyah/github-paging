package com.raproject.alirohmansyah.ui.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raproject.alirohmansyah.models.User
import com.raproject.alirohmansyah.repositories.MainRepository


class MainViewModel @ViewModelInject constructor(
    app: Application,
    private val mainRepository: MainRepository
): AndroidViewModel(app) {

    private var currentResult: LiveData<PagingData<User>>? = null

    fun searchUsers(query: String): LiveData<PagingData<User>> {
        val newResult: LiveData<PagingData<User>> = mainRepository.getUsers(query).cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }
}