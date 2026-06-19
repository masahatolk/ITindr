package com.hits.core_database.di

import androidx.room.Room
import com.hits.core_database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "chat.db"
        ).build()
    }

    single { get<AppDatabase>().chatDao() }
    single { get<AppDatabase>().messageDao() }
}