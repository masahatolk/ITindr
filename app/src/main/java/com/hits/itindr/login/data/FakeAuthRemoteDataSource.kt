package com.hits.itindr.login.data

import com.hits.core_auth.data.AuthResponse

class FakeAuthRemoteDataSource : AuthRemoteDataSource {
    override suspend fun login(email: String, password: String): AuthResponse =
        AuthResponse("", "", "", "")

    override suspend fun register(email: String, password: String): AuthResponse =
        AuthResponse("", "", "", "")
    override suspend fun logout() {}
}