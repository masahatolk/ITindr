package com.hits.itindr.login.data

class FakeAuthRemoteDataSource : AuthRemoteDataSource {
    override fun login(email: String, password: String): Boolean = true
}