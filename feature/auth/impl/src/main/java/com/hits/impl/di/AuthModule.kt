package com.hits.impl.di

import com.hits.api.repository.AuthRepository
import com.hits.impl.data.remote.datasource.AuthRemoteDataSource
import com.hits.impl.data.remote.datasource.AuthRemoteDataSourceImpl
import com.hits.impl.data.repository.AuthRepositoryImpl
import com.hits.impl.data.usecase.LoginUseCase
import com.hits.impl.data.usecase.LogoutUseCase
import com.hits.impl.data.usecase.RegisterUseCase
import com.hits.impl.ui.InfoViewModel
import com.hits.impl.ui.LoginViewModel
import com.hits.impl.ui.RegisterViewModel
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
