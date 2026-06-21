package com.hits.impl.di

import com.hits.api.repository.ChatRepository
import com.hits.impl.data.remote.api.ChatApi
import com.hits.impl.data.remote.datasource.ChatRemoteDataSource
import com.hits.impl.data.remote.datasource.ChatRemoteDataSourceImpl
import com.hits.impl.data.repository.ChatRepositoryImpl
import com.hits.impl.ui.ChatMessageAppearanceDirector
import com.hits.impl.ui.ChatViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val chatModule = module {

    single<ChatApi> {
        get<Retrofit>(named("mainRetrofit"))
            .create(ChatApi::class.java)
    }

    single<ChatRepository> {
        ChatRepositoryImpl(get(), get(), get())
    }

    single<ChatRemoteDataSource> {
        ChatRemoteDataSourceImpl(get())
    }

    viewModel {
        ChatViewModel(get(), get())
    }

    single {
        ChatMessageAppearanceDirector()
    }
}
