package com.hits.itindr

import android.content.Context
import com.hits.itindr.auth.EncryptedTokenStore
import com.hits.itindr.auth.TokenStore
import com.hits.itindr.login.data.AuthRemoteDataSource
import com.hits.itindr.login.data.AuthRemoteDataSourceImpl
import com.hits.itindr.login.data.AuthRepositoryImpl
import com.hits.itindr.login.domain.AuthRepository
import com.hits.itindr.login.domain.LoginUseCase
import com.hits.itindr.login.presentation.LoginViewModelFactory
import com.hits.itindr.network.ApiHttpClient

object AppGraph {
    private lateinit var appContext: Context

    val tokenStore: TokenStore by lazy { EncryptedTokenStore(appContext) }
    private val httpClient: ApiHttpClient by lazy { ApiHttpClient(tokenStore) }
    private val authRemoteDataSource: AuthRemoteDataSource by lazy {
        AuthRemoteDataSourceImpl(
            httpClient
        )
    }
    private val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(
            authRemoteDataSource,
            tokenStore
        )
    }
    //private val feedRemoteDataSource: FeedRemoteDataSource by lazy { FeedRemoteDataSourceImpl(httpClient) }
    //val feedRepository: FeedRepository by lazy { FeedRepositoryImpl(feedRemoteDataSource) }

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun provideLoginViewModelFactory(): LoginViewModelFactory {
        return LoginViewModelFactory(LoginUseCase(authRepository))
    }
}