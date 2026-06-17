package com.hits.itindr.mainflow.feed.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedApi {

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