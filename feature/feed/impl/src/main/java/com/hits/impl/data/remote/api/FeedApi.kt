package com.hits.impl.data.remote.api

import com.hits.impl.data.remote.dto.LikeResponse
import com.hits.impl.data.remote.dto.UserProfileDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedApi {

    @GET("user")
    suspend fun getAllUsers(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Response<List<UserProfileDto>>

    @GET("user/feed")
    suspend fun getFeed(): Response<List<UserProfileDto>>

    @POST("user/{userId}/like")
    suspend fun like(
        @Path("userId") userId: String
    ): Response<LikeResponse>

    @POST("user/{userId}/dislike")
    suspend fun dislike(
        @Path("userId") userId: String
    ): Response<Unit>
}