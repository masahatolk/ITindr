package com.hits.impl.data.remote.datasource

import com.hits.api.model.Attachment
import com.hits.impl.data.remote.dto.ChatListItemDto
import com.hits.impl.data.remote.dto.CreatedChatDto
import com.hits.impl.data.remote.dto.MessageDto
import okhttp3.MultipartBody

interface ChatRemoteDataSource {

    suspend fun getChats(): List<ChatListItemDto>

    suspend fun createChat(
        companionId: String
    ): CreatedChatDto

    suspend fun getMessages(
        chatId: String,
        limit: Int,
        offset: Int
    ): List<MessageDto>

    suspend fun sendMessage(
        chatId: String,
        text: String,
        attachments: List<MultipartBody.Part>
    ): MessageDto
}