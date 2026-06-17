package com.hits.itindr.mainflow.profile.di

import com.hits.itindr.mainflow.profile.data.ProfileApi
import com.hits.itindr.mainflow.profile.data.ProfileRemoteDataSource
import com.hits.itindr.mainflow.profile.data.ProfileRemoteDataSourceImpl
import com.hits.itindr.mainflow.profile.data.ProfileRepository
import com.hits.itindr.mainflow.profile.data.ProfileRepositoryImpl
import com.hits.itindr.mainflow.profile.data.TopicApi
import com.hits.itindr.mainflow.profile.data.TopicRepository
import com.hits.itindr.mainflow.profile.data.TopicRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val profileModule = module {

    single<ProfileApi> {
        get<Retrofit>().create(ProfileApi::class.java)
    }

    single<TopicApi> {
        get<Retrofit>().create(TopicApi::class.java)
    }

    single<TopicRepository> {
        TopicRepositoryImpl(get())
    }

    single<ProfileRemoteDataSource> {
        ProfileRemoteDataSourceImpl(get())
    }

    single<ProfileRepository> {
        ProfileRepositoryImpl(get())
    }
}