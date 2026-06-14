package com.hits.chat_feature.domain

data class Chat(
    val id: String,
    val title: String,
    val companionId: String?,
    val companionName: String?,
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

data class MatchUi(
    val userId: String,
    val name: String,
    val avatarUrl: String? = null,
    val matchedAt: Long,
    val expiresAt: Long,
)