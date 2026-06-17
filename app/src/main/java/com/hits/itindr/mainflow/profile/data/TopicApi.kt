package com.hits.itindr.mainflow.profile.data

import com.hits.itindr.mainflow.profile.data.dto.TopicDto
import retrofit2.Response
import retrofit2.http.GET

interface TopicApi {

    @GET("topic")
    suspend fun getTopics(): Response<List<TopicDto>>
}