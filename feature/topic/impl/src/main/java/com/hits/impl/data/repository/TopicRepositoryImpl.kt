package com.hits.impl.data.repository

import com.hits.api.model.Topic
import com.hits.api.repository.TopicRepository
import com.hits.core_network.ApiException
import com.hits.impl.data.mapper.toDomain
import com.hits.impl.data.remote.api.TopicApi

class TopicRepositoryImpl(
    private val topicApi: TopicApi
) : TopicRepository {

    override suspend fun getTopics(): List<Topic> {

        val response = topicApi.getTopics()

        if (!response.isSuccessful) {
            throw ApiException(
                response.code(),
                response.errorBody()?.string().orEmpty()
            )
        }

        return response.body().orEmpty().map { it.toDomain() }
    }
}