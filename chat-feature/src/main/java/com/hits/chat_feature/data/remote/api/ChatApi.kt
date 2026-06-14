package com.hits.chat_feature.data.remote.api

import androidx.room.Query
import com.hits.chat_feature.data.remote.dto.ChatDto
import com.hits.chat_feature.data.remote.dto.CreateChatRequest
import com.hits.chat_feature.data.remote.dto.MessageDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ChatApi {

    @GET("chat")
    suspend fun getChats(): List<ChatDto>

    @POST("chat")
    suspend fun createChat(
        @Body request: CreateChatRequest
    ): ChatDto

    @GET("chat/{chatId}/message")
    suspend fun getMessages(
        @Path("chatId") chatId: String,
    ): List<MessageDto>

    @Multipart
    @POST("chat/{chatId}/message")
    suspend fun sendMessage(
        @Path("chatId") chatId: String,
        @Part("messageText") text: RequestBody
    ): MessageDto
}