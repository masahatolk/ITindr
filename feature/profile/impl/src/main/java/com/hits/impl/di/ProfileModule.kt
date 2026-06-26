package com.hits.impl.di

import com.hits.api.repository.ProfileRepository
import com.hits.impl.data.remote.api.ProfileApi
import com.hits.impl.data.remote.datasource.ProfileRemoteDataSource
import com.hits.impl.data.remote.datasource.ProfileRemoteDataSourceImpl
import com.hits.impl.data.repository.ProfileRepositoryImpl
import com.hits.impl.ui.EditProfileViewModel
import com.hits.impl.ui.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val profileModule = module {

    single<ProfileApi> {
        get<Retrofit>(named("mainRetrofit"))
            .create(ProfileApi::class.java)
    }

    single<ProfileRemoteDataSource> {
        ProfileRemoteDataSourceImpl(get(), get())
    }

    single<ProfileRepository> {
        ProfileRepositoryImpl(get())
    }

    viewModel {
        ProfileViewModel(get(), get())
    }

    viewModel {
        EditProfileViewModel(get(), get())
    }
}