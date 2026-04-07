package com.hits.itindr.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hits.itindr.login.data.AuthRepositoryImpl
import com.hits.itindr.login.data.FakeAuthRemoteDataSource
import com.hits.itindr.login.domain.LoginUseCase

class LoginViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val remoteDataSource = FakeAuthRemoteDataSource()
            val repository = AuthRepositoryImpl(remoteDataSource)
            val useCase = LoginUseCase(repository)
            return LoginViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
