package com.hits.itindr.login.data

interface AuthRemoteDataSource {
    suspend fun login(email: String, password: String): String
    suspend fun register(email: String, password: String): String
}