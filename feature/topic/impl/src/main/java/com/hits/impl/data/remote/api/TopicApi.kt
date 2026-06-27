package com.hits.impl.data.remote.api

import com.hits.impl.data.remote.dto.TopicDto
import retrofit2.Response
import retrofit2.http.GET

interface TopicApi {

    @GET("topic")
    suspend fun getTopics(): Response<List<TopicDto>>
}