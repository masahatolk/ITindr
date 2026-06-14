package com.hits.itindr.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hits.itindr.login.domain.LogoutUseCase

class ProfileViewModelFactory(
    private val logoutUseCase: LogoutUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(logoutUseCase) as T
    }
}