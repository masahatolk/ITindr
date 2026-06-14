package com.hits.chat_feature.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateChatRequest(
    val userId: String
)