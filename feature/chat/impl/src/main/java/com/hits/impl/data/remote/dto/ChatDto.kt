package com.hits.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
    val id: String,
    val title: String,
    val lastMessage: MessageDto? = null,
    val updatedAt: String?
)