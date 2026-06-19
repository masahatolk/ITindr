package com.hits.api.model

data class Chat(
    val id: String,
    val title: String,
    val lastMessage: String?,
    val updatedAt: Long,
)

data class ChatMessage(
    val id: String,
    val chatId: String,
    val text: String,
    val createdAt: Long,
    val isOutgoing: Boolean,
    val senderName: String?,
)

data class CachedResult<T>(
    val value: T,
    val fromCache: Boolean,
)