package com.hits.itindr.login.data

import com.hits.itindr.login.domain.AuthRepository

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
) : AuthRepository {
    override fun login(email: String, password: String): Boolean {
        return remoteDataSource.login(email, password)
    }
}