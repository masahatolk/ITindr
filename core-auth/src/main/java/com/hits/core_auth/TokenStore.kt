package com.hits.core_auth

interface TokenStore {

    fun getAccessToken(): String?

    fun getRefreshToken(): String?

    fun saveTokens(
        accessToken: String,
        refreshToken: String
    )

    fun clearToken()
}