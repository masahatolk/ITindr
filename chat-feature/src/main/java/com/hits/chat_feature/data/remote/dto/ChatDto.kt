package com.hits.chat_feature.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
    val id: String,
    val title: String,
    val avatar: String? = null,
    val lastMessage: MessageDto? = null
)