package com.hits.impl.data.remote.datasource

import com.hits.impl.data.remote.api.ChatApi
import com.hits.impl.data.remote.dto.ChatDto
import com.hits.impl.data.remote.dto.CreateChatRequest
import com.hits.impl.data.remote.dto.MessageDto
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.collections.map

class ChatRemoteDataSourceImpl(
    private val api: ChatApi
) : ChatRemoteDataSource {
    override suspend fun getChats(): List<ChatDto> {

        val response = api.getChats()

        if (response.isSuccessful) {

            return response.body()?.map { dto ->

                ChatDto(
                    id = dto.chat.id,
                    title = dto.chat.title,
                    lastMessage = dto.lastMessage,
                    avatar = dto.chat.avatar,
                    updatedAt = null
                )
            } ?: emptyList()
        }

        throw Exception(response.errorBody()?.string().orEmpty())
    }

    override suspend fun createChat(companionId: String): ChatDto {

        val response = api.createChat(CreateChatRequest(companionId))

        if (response.isSuccessful) {
            val dto = response.body()
                ?: throw Exception("Пустое тело запроса")

            return ChatDto(
                id = dto.id,
                title = dto.title,
                lastMessage = dto.lastMessage,
                updatedAt = dto.updatedAt
            )
        }

        throw Exception(response.errorBody()?.string().orEmpty())
    }

    override suspend fun getMessages(chatId: String): List<MessageDto> {

        //TODO
        val response = api.getMessages(chatId, 100, 0)

        if (response.isSuccessful) {
            return response.body()?.map { dto ->
                MessageDto(
                    id = dto.id,
                    text = dto.text,
                    createdAt = dto.createdAt,
                    user = dto.user,
                )
            } ?: emptyList()
        }

        throw Exception(response.errorBody()?.string().orEmpty())
    }

    override suspend fun sendMessage(
        chatId: String,
        text: String
    ): MessageDto {

        val response = api.sendMessage(chatId, text.toRequestBody())

        if (response.isSuccessful) {
            val dto = response.body()
                ?: throw Exception("Пустое тело запроса")

            return MessageDto(
                id = dto.id,
                text = dto.text,
                createdAt = dto.createdAt,
                user = dto.user
            )
        }

        throw Exception(response.errorBody()?.string().orEmpty())
    }
}