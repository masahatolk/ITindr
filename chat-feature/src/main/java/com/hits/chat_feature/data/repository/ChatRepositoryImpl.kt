package com.hits.chat_feature.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.hits.chat_feature.data.local.dao.ChatDao
import com.hits.chat_feature.data.local.dao.MessageDao
import com.hits.chat_feature.data.mappers.toDomain
import com.hits.chat_feature.data.mappers.toEntity
import com.hits.chat_feature.data.remote.api.ChatApi
import com.hits.chat_feature.data.remote.dto.CreateChatRequest
import com.hits.chat_feature.domain.CachedResult
import com.hits.chat_feature.domain.Chat
import com.hits.chat_feature.domain.ChatMessage
import com.hits.chat_feature.domain.ChatRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.collections.map

class ChatRepositoryImpl(
    private val api: ChatApi,
    private val chatDao: ChatDao,
    private val messageDao: MessageDao
) : ChatRepository {

    override suspend fun getChats(): CachedResult<List<Chat>> {
        return try {

            val remoteChats = api.getChats()

            val entities = remoteChats.map { it.toEntity() }
            chatDao.insertChats(entities)

            CachedResult(
                value = entities.map { it.toDomain() },
                fromCache = false
            )

        } catch (e: Exception) {

            val cache = chatDao.getChats()

            CachedResult(
                value = cache.map { it.toDomain() },
                fromCache = true
            )
        }
    }

    override suspend fun createChat(companionId: String): Chat {
        return try {

            val remote = api.createChat(
                CreateChatRequest(userId = companionId)
            )

            val entity = remote.toEntity()
            chatDao.insertChats(listOf(entity))

            entity.toDomain()

        } catch (e: Exception) {
            throw e
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMessages(chatId: String): CachedResult<List<ChatMessage>> {
        return try {

            val remote = api.getMessages(chatId)

            val entities = remote.map {
                it.toEntity(chatId)
            }

            messageDao.insertMessages(entities)

            CachedResult(
                value = entities.map { it.toDomain() },
                fromCache = false
            )

        } catch (e: Exception) {

            val cache = messageDao.getMessages(chatId)

            CachedResult(
                value = cache.map { it.toDomain() },
                fromCache = true
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun sendMessage(
        chatId: String,
        text: String
    ): ChatMessage {

        return try {

            val body = text.toRequestBody("text/plain".toMediaType())

            val remote = api.sendMessage(chatId, body)

            val entity = remote.toEntity(chatId)

            messageDao.insertMessages(listOf(entity))

            entity.toDomain()

        } catch (e: Exception) {
            throw e
        }
    }
}