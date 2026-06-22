package com.hits.impl.data.remote.datasource

import com.hits.impl.data.remote.dto.ChatListItemDto
import com.hits.impl.data.remote.dto.CreatedChatDto
import com.hits.impl.data.remote.dto.MessageDto

interface ChatRemoteDataSource {

    suspend fun getChats(): List<ChatListItemDto>

    suspend fun createChat(
        companionId: String
    ): CreatedChatDto

    suspend fun getMessages(
        chatId: String
    ): List<MessageDto>

    suspend fun sendMessage(
        chatId: String,
        text: String
    ): MessageDto
}