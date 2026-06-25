package com.hits.itindr.login.di

import com.hits.itindr.login.presentation.InfoViewModel
import com.hits.itindr.login.data.AuthRemoteDataSource
import com.hits.itindr.login.data.AuthRemoteDataSourceImpl
import com.hits.itindr.login.data.AuthRepositoryImpl
import com.hits.itindr.login.domain.AuthRepository
import com.hits.itindr.login.domain.LoginUseCase
import com.hits.itindr.login.domain.LogoutUseCase
import com.hits.itindr.login.domain.RegisterUseCase
import com.hits.itindr.login.presentation.LoginViewModel
import com.hits.itindr.login.presentation.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {

    single<AuthRemoteDataSource> {
        AuthRemoteDataSourceImpl(get())
    }

    single<AuthRepository> {
        AuthRepositoryImpl(
            get(),
            get(),
            get(),
            get()
        )
    }

    factory {
        LoginUseCase(get())
    }

    factory {
        RegisterUseCase(get())
    }

    factory {
        LogoutUseCase(get())
    }

    viewModel {
        LoginViewModel(get())
    }

    viewModel {
        RegisterViewModel(
            get(),
            get()
        )
    }

    viewModel {
        InfoViewModel(
            get(),
            get(),
        )
    }
}
