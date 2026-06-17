package com.hits.itindr.login.data

import com.hits.core_auth.data.AuthApi
import com.hits.core_auth.data.AuthRequest
import com.hits.core_auth.data.AuthResponse
import com.hits.core_network.ApiException

class AuthRemoteDataSourceImpl (
    private val authApi: AuthApi
) : AuthRemoteDataSource {

    override suspend fun login(
        email: String,
        password: String,
    ): AuthResponse {

        val response = authApi.login(
            AuthRequest(
                email = email,
                password = password
            )
        )

        if (response.isSuccessful) {
            return response.body()
                ?: throw ApiException(
                    response.code(),
                    "Пустой ответ сервера"
                )
        }

        throw ApiException(
            response.code(),
            response.errorBody()?.string().orEmpty()
        )
    }

    override suspend fun register(
        email: String,
        password: String
    ): AuthResponse {

        val response = authApi.register(
            AuthRequest(
                email = email,
                password = password
            )
        )

        if (response.isSuccessful) {

            response.body()?.let {
                return it
            }

            return login(email, password)
        }

        throw ApiException(
            response.code(),
            response.errorBody()?.string().orEmpty()
        )
    }

    override suspend fun logout() {

        val response = authApi.logout()

        if (!response.isSuccessful) {
            throw ApiException(
                response.code(),
                response.errorBody()?.string().orEmpty()
            )
        }
    }
}