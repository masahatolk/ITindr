package com.hits.chat_feature.di

import androidx.room.Room
import com.hits.chat_feature.data.local.db.AppDatabase
import com.hits.chat_feature.data.remote.api.ChatApi
import com.hits.chat_feature.data.repository.ChatRepositoryImpl
import com.hits.chat_feature.domain.ChatRepository
import com.hits.chat_feature.presentation.ChatViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val chatModule = module {

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "chat.db"
        ).build()
    }

    single { get<AppDatabase>().chatDao() }
    single { get<AppDatabase>().messageDao() }

    single<ChatApi> {
        get<Retrofit>().create(ChatApi::class.java)
    }

    single<ChatRepository> {
        ChatRepositoryImpl(get(), get(), get())
    }

    viewModel {
        ChatViewModel(get())
    }
}
