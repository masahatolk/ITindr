package com.hits.itindr.login.data

interface AuthRemoteDataSource {
    fun login(email: String, password: String): Boolean
}