package com.hits.itindr.login.data

class FakeAuthRemoteDataSource : AuthRemoteDataSource {
    override suspend fun login(email: String, password: String): String = "fake-token"
}