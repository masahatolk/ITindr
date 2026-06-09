package com.hits.itindr.login.data

import com.hits.itindr.AppGraph.tokenStore
import com.hits.itindr.auth.TokenStore
import com.hits.itindr.login.domain.AuthRepository

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
    tokenStore: TokenStore,
) : AuthRepository {
    override suspend fun login(email: String, password: String) {
        tokenStore.saveToken(remoteDataSource.login(email, password))
    }
}