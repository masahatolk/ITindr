package com.hits.itindr.login

import androidx.lifecycle.ViewModelProvider
import com.hits.itindr.login.data.AuthRepositoryImpl
import com.hits.itindr.login.data.FakeAuthRemoteDataSource
import com.hits.itindr.login.domain.LoginUseCase
import com.hits.itindr.login.presentation.LoginViewModelFactory

object LoginModule {
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        val remoteDataSource = FakeAuthRemoteDataSource()
        val repository = AuthRepositoryImpl(remoteDataSource)
        val useCase = LoginUseCase(repository)
        return LoginViewModelFactory(useCase)
    }
}
