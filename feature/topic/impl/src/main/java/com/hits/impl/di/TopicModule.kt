package com.hits.impl.di

import com.hits.api.repository.TopicRepository
import com.hits.impl.data.remote.api.TopicApi
import com.hits.impl.data.repository.TopicRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val topicModule = module {

    single<TopicApi> {
        get<Retrofit>(named("mainRetrofit"))
            .create(TopicApi::class.java)
    }

    single<TopicRepository> {
        TopicRepositoryImpl(get())
    }
}