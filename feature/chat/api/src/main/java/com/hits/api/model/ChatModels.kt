package com.hits.api.model

data class Chat(
    val id: String,
    val title: String,
    val lastMessage: String?,
    val avatar: String? = null,
    val updatedAt: Long,
)

data class ChatMessage(
    val id: String,
    val chatId: String,
    val text: String,
    val createdAt: Long,
    val userId: String?,
    val senderName: String?,
    val avatar: String?,
    val attachments: List<String>
)

data class ChatMessageUi(
    val id: String,
    val text: String,
    val isOutgoing: Boolean,
    val senderName: String?,
    val avatar: String?,
    val createdAt: String,
    val attachments: List<String>,
)

data class Attachment(
    val uri: String,
)

data class CachedResult<T>(
    val value: T,
    val fromCache: Boolean,
)