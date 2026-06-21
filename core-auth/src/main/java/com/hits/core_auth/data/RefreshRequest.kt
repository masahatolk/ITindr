package com.hits.core_auth.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthRefreshRequest(
    val refreshToken: String
)