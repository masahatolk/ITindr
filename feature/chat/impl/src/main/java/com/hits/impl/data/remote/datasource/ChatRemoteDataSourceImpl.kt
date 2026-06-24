package com.hits.impl.data.remote.datasource

import com.hits.api.model.Attachment
import com.hits.impl.data.remote.api.ChatApi
import com.hits.impl.data.remote.dto.ChatListItemDto
import com.hits.impl.data.remote.dto.CreateChatRequest
import com.hits.impl.data.remote.dto.CreatedChatDto
import com.hits.impl.data.remote.dto.MessageDto
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ChatRemoteDataSourceImpl(
    private val api: ChatApi
) : ChatRemoteDataSource {

    override suspend fun getChats(): List<ChatListItemDto> {

        val response = api.getChats()

        if (response.isSuccessful) {

            return response.body() ?: emptyList()
        }

        throw Exception(response.errorBody()?.string().orEmpty())
    }

    override suspend fun createChat(companionId: String): CreatedChatDto {

        val response = api.createChat(CreateChatRequest(companionId))

        if (response.isSuccessful) {
            return response.body()
                ?: throw Exception("Пустое тело запроса")
        }

        throw Exception(response.errorBody()?.string().orEmpty())
    }

    override suspend fun getMessages(chatId: String): List<MessageDto> {

        val response = api.getMessages(chatId, 100, 0)

        if (response.isSuccessful) {

            return response.body() ?: emptyList()
        }

        throw Exception(response.errorBody()?.string().orEmpty())
    }

    override suspend fun sendMessage(
        chatId: String,
        text: String,
        attachments: List<MultipartBody.Part>
    ): MessageDto {

        val response = api.sendMessage(
            chatId = chatId,
            text = text.toRequestBody(),
            attachments = attachments)

        if (response.isSuccessful) {
            val dto = response.body()
                ?: throw Exception("Пустое тело запроса")

            return MessageDto(
                id = dto.id,
                text = dto.text,
                createdAt = dto.createdAt,
                user = dto.user,
                attachments = dto.attachments
            )
        }

        throw Exception(response.errorBody()?.string().orEmpty())
    }
}