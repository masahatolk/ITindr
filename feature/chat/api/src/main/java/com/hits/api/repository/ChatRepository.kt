package com.hits.api.repository

import com.hits.api.model.CachedResult
import com.hits.api.model.Chat
import com.hits.api.model.ChatMessage

interface ChatRepository {
    suspend fun getChats(): CachedResult<List<Chat>>
    suspend fun createChat(companionId: String): Chat
    suspend fun getMessages(chatId: String): CachedResult<List<ChatMessage>>
    suspend fun sendMessage(chatId: String, text: String): ChatMessage
}