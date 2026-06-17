package com.hits.core_auth.data

interface AuthRemoteDataSource {
    suspend fun login(email: String, password: String): String
    suspend fun register(email: String, password: String): String
    suspend fun logout()
}