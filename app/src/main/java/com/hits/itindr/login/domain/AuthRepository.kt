package com.hits.itindr.login.domain

interface AuthRepository {
    fun login(email: String, password: String): Boolean
}