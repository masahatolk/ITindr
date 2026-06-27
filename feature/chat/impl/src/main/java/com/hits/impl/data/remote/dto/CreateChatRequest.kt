package com.hits.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateChatRequest(
    val userId: String
)