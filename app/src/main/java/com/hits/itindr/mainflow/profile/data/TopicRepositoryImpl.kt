package com.hits.itindr.mainflow.profile.data

import com.hits.core_network.ApiException
import com.hits.itindr.mainflow.profile.data.dto.TopicDto

class TopicRepositoryImpl(
    private val topicApi: TopicApi
) : TopicRepository {

    override suspend fun getTopics(): List<TopicDto> {

        val response = topicApi.getTopics()

        if (!response.isSuccessful) {
            throw ApiException(
                response.code(),
                response.errorBody()?.string().orEmpty()
            )
        }

        return response.body().orEmpty()
    }
}