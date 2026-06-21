package com.hits.impl.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.hits.api.model.CachedResult
import com.hits.api.model.Chat
import com.hits.api.model.ChatMessage
import com.hits.api.repository.ChatRepository
import com.hits.impl.data.local.dao.ChatDao
import com.hits.impl.data.local.dao.MessageDao
import com.hits.impl.data.local.entity.MessageEntity
import com.hits.impl.data.mapper.toDomain
import com.hits.impl.data.mapper.toEntity
import com.hits.impl.data.remote.datasource.ChatRemoteDataSource

class ChatRepositoryImpl(
    private val remoteDataSource: ChatRemoteDataSource,
    private val chatDao: ChatDao,
    private val messageDao: MessageDao
) : ChatRepository {

    override suspend fun getChats(): CachedResult<List<Chat>> {
        return try {

            val remoteChats = remoteDataSource.getChats()

            val entities = remoteChats.map { it.toEntity() }
            chatDao.insertChats(entities)

            CachedResult(
                value = entities.map { it.toDomain() },
                fromCache = false
            )

        } catch (e: Exception) {

            e.printStackTrace()

            val cache = chatDao.getChats()

            CachedResult(
                value = cache.map { it.toDomain() },
                fromCache = true
            )
        }
    }

    override suspend fun createChat(companionId: String): Chat {
        return try {

            val remote = remoteDataSource.createChat(companionId)

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

            val remote = remoteDataSource.getMessages(chatId)

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

            val remote = remoteDataSource.sendMessage(chatId, text)

            val entity = remote.toEntity(chatId)

            messageDao.insertMessages(listOf(entity))

            entity.toDomain()

        } catch (e: Exception) {
            throw e
        }
    }
}
