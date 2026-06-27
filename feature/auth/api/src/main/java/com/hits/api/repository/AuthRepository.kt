package com.hits.api.repository

interface AuthRepository {
    suspend fun login(email: String, password: String)
    suspend fun register(email: String, password: String)
    suspend fun logout()
    suspend fun refresh(): Boolean
}