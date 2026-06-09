package com.hits.itindr.login.domain

interface AuthRepository {
    suspend fun login(email: String, password: String)
}