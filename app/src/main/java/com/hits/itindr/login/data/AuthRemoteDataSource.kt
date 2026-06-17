package com.hits.itindr.login.data

import com.hits.core_auth.data.AuthResponse

interface AuthRemoteDataSource {
    suspend fun login(email: String, password: String): AuthResponse
    suspend fun register(email: String, password: String): AuthResponse
    suspend fun logout()
}