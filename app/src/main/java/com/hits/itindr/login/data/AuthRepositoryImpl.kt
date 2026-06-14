package com.hits.itindr.login.data

import com.hits.itindr.auth.TokenStore
import com.hits.itindr.login.domain.AuthRepository

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
    private val tokenStore: TokenStore,
) : AuthRepository {
    override suspend fun login(email: String, password: String) {
        tokenStore.saveToken(remoteDataSource.login(email, password))
    }

    override suspend fun register(email: String, password: String) {
        tokenStore.saveToken(remoteDataSource.register(email, password))
    }

    override suspend fun logout() {
        remoteDataSource.logout()
        tokenStore.clearToken()
    }
}