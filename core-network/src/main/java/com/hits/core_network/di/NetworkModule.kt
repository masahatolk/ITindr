package com.hits.core_network.di

import com.hits.core_network.NetworkConfig
import com.hits.core_network.authenticator.TokenAuthenticator
import com.hits.core_network.interceptor.AuthInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {

    single {
        AuthInterceptor(get())
    }

    single {
        TokenAuthenticator(
            tokenStore = get(),
            authApi = get(named("refreshApi"))
        )
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single(named("mainClient")) {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .authenticator(get<TokenAuthenticator>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single(named("refreshClient")) {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Json {
            ignoreUnknownKeys = true
        }
    }

    single(named("mainRetrofit")) {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(get(named("mainClient")))
            .addConverterFactory(
                get<Json>().asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
    }

    single(named("refreshRetrofit")) {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(get(named("refreshClient")))
            .addConverterFactory(
                get<Json>().asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
    }
}