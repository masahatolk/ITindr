package com.hits.api.repository

import com.hits.api.model.Topic

interface TopicRepository {
    suspend fun getTopics(): List<Topic>
}