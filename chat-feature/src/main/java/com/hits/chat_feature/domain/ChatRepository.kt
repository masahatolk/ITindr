package com.hits.chat_feature.domain

interface ChatRepository {
    suspend fun getChats(): CachedResult<List<Chat>>
    suspend fun createChat(companionId: String): Chat
    suspend fun getMessages(chatId: String): CachedResult<List<ChatMessage>>
    suspend fun sendMessage(chatId: String, text: String): ChatMessage
}