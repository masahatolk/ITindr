package com.hits.impl.data.remote.datasource

import com.hits.impl.data.remote.dto.ChatDto
import com.hits.impl.data.remote.dto.MessageDto

interface ChatRemoteDataSource {

    suspend fun getChats(): List<ChatDto>

    suspend fun createChat(
        companionId: String
    ): ChatDto

    suspend fun getMessages(
        chatId: String
    ): List<MessageDto>

    suspend fun sendMessage(
        chatId: String,
        text: String
    ): MessageDto
}