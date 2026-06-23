package com.hits.itindr.mainflow.profile.di

import com.hits.itindr.mainflow.profile.data.ProfileApi
import com.hits.itindr.mainflow.profile.data.ProfileRemoteDataSource
import com.hits.itindr.mainflow.profile.data.ProfileRemoteDataSourceImpl
import com.hits.itindr.mainflow.profile.data.ProfileRepository
import com.hits.itindr.mainflow.profile.data.ProfileRepositoryImpl
import com.hits.itindr.mainflow.profile.data.TopicApi
import com.hits.itindr.mainflow.profile.data.TopicRepository
import com.hits.itindr.mainflow.profile.data.TopicRepositoryImpl
import com.hits.itindr.mainflow.profile.ui.EditProfileViewModel
import com.hits.itindr.mainflow.profile.ui.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val profileModule = module {

    single<ProfileApi> {
        get<Retrofit>(named("mainRetrofit"))
            .create(ProfileApi::class.java)
    }

    single<TopicApi> {
        get<Retrofit>(named("mainRetrofit"))
            .create(TopicApi::class.java)
    }

    single<TopicRepository> {
        TopicRepositoryImpl(get())
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