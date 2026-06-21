package com.hits.impl.data.remote.api

import com.hits.impl.data.remote.dto.ChatDto
import com.hits.impl.data.remote.dto.ChatListItemDto
import com.hits.impl.data.remote.dto.CreateChatRequest
import com.hits.impl.data.remote.dto.MessageDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatApi {

    @GET("chat")
    suspend fun getChats(): Response<List<ChatListItemDto>>

    @POST("chat")
    suspend fun createChat(
        @Body request: CreateChatRequest
    ): Response<ChatDto>

    @GET("chat/{chatId}/message")
    suspend fun getMessages(
        @Path("chatId") chatId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Response<List<MessageDto>>

    @Multipart
    @POST("chat/{chatId}/message")
    suspend fun sendMessage(
        @Path("chatId") chatId: String,
        @Part("messageText") text: RequestBody
    ): Response<MessageDto>
}