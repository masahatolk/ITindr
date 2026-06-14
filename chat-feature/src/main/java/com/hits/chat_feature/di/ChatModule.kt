package com.hits.chat_feature.di

import androidx.room.Room
import com.hits.chat_feature.data.local.db.AppDatabase
import com.hits.chat_feature.data.remote.api.ChatApi
import com.hits.chat_feature.data.repository.ChatRepositoryImpl
import com.hits.chat_feature.domain.ChatRepository
import com.hits.chat_feature.presentation.ChatViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
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

    single {
        Retrofit.Builder()
            .baseUrl("http://158.160.26.231:18080/itindr/api/mobile/v1/")
            .addConverterFactory(
                Json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

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
