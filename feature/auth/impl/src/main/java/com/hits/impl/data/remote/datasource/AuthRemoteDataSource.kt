package com.hits.impl.data.remote.datasource

import com.hits.core_auth.data.AuthResponse

interface AuthRemoteDataSource {
    suspend fun login(email: String, password: String): AuthResponse
    suspend fun register(email: String, password: String): AuthResponse
    suspend fun logout()
    suspend fun refresh(refreshToken: String): AuthResponse
}