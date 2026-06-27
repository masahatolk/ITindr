package com.hits.impl.di

import com.hits.api.repository.FeedRepository
import com.hits.impl.data.LikeProfileUseCase
import com.hits.impl.data.MatchStore
import com.hits.impl.data.remote.api.FeedApi
import com.hits.impl.data.remote.datasource.FeedRemoteDataSource
import com.hits.impl.data.remote.datasource.FeedRemoteDataSourceImpl
import com.hits.impl.data.repository.FeedRepositoryImpl
import com.hits.impl.ui.FeedViewModel
import com.hits.impl.ui.PeopleViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val feedModule = module {

    single<FeedApi> {
        get<Retrofit>(named("mainRetrofit")).create(FeedApi::class.java)
    }

    single<FeedRemoteDataSource> {
        FeedRemoteDataSourceImpl(get())
    }

    single<FeedRepository> {
        FeedRepositoryImpl(get())
    }

    viewModel {
        FeedViewModel(get(), get(), get(), get())
    }

    viewModel {
        PeopleViewModel(get(), get(), get(), get())
    }

    single {
        MatchStore()
    }

    factory {
        LikeProfileUseCase(
            feedRepository = get(), chatRepository = get()
        )
    }
}