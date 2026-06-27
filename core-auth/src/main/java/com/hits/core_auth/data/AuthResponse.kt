package com.hits.core_auth.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String
)