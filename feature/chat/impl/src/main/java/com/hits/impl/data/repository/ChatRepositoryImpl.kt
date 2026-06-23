package com.hits.impl.data.repository

import android.content.Context
import com.hits.api.model.Attachment
import com.hits.api.model.CachedResult
import com.hits.api.model.Chat
import com.hits.api.model.ChatMessage
import com.hits.api.repository.ChatRepository
import com.hits.core_auth.session.UserSession
import com.hits.impl.data.local.dao.ChatDao
import com.hits.impl.data.local.dao.MessageDao
import com.hits.impl.data.mapper.toDomain
import com.hits.impl.data.mapper.toEntity
import com.hits.impl.data.remote.datasource.ChatRemoteDataSource
import java.io.IOException
import com.hits.impl.data.mapper.uriToMultipart

class ChatRepositoryImpl(
    private val remoteDataSource: ChatRemoteDataSource,
    private val chatDao: ChatDao,
    private val messageDao: MessageDao,
    private val userSession: UserSession,
    private val context: Context,
) : ChatRepository {

    override suspend fun getChats(): CachedResult<List<Chat>> {
        return try {

            val remoteChats = remoteDataSource.getChats()

            val currentUserId = userSession.getUserId() ?: error("User not authorized")

            val entities = remoteChats.map { it.toEntity(currentUserId) }
            chatDao.insertChats(entities)

            CachedResult(
                value = entities.map { it.toDomain() },
                fromCache = false
            )

        } catch (e: IOException) {

            e.printStackTrace()

            val currentUserId = userSession.getUserId() ?: error("User not authorized")

            val cache = chatDao.getChats(currentUserId)

            CachedResult(
                value = cache.map { it.toDomain() },
                fromCache = true
            )
        }
    }

    override suspend fun createChat(companionId: String): Chat {
        return try {

            val remote = remoteDataSource.createChat(companionId)

            val currentUserId = userSession.getUserId() ?: error("User not authorized")

            val entity = remote.toEntity(currentUserId)
            chatDao.insertChats(listOf(entity))

            entity.toDomain()

        } catch (e: Exception) {
            throw e
        }
    }

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

        } catch (e: IOException) {

            val cache = messageDao.getMessages(chatId)

            CachedResult(
                value = cache.reversed().map { it.toDomain() },
                fromCache = true
            )
        }
    }

    override suspend fun sendMessage(
        chatId: String,
        text: String,
        attachments: List<Attachment>
    ): ChatMessage {

        return try {

            val parts = attachments.map {

                context.uriToMultipart(it)
            }
            val remote = remoteDataSource.sendMessage(chatId, text, parts)

            val entity = remote.toEntity(chatId)

            messageDao.insertMessages(listOf(entity))

            entity.toDomain()

        } catch (e: Exception) {
            throw e
        }
    }
}
