package com.hits.itindr.mainflow.feed.di

import com.hits.itindr.domain.usecase.LikeProfileUseCase
import com.hits.itindr.mainflow.feed.FeedViewModel
import com.hits.itindr.mainflow.feed.PeopleViewModel
import com.hits.itindr.mainflow.feed.data.FeedRemoteDataSource
import com.hits.itindr.mainflow.feed.data.FeedRemoteDataSourceImpl
import com.hits.itindr.mainflow.feed.data.FeedRepositoryImpl
import com.hits.itindr.mainflow.feed.data.network.FeedApi
import com.hits.itindr.mainflow.feed.domain.FeedRepository
import com.hits.itindr.mainflow.match.MatchStore
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