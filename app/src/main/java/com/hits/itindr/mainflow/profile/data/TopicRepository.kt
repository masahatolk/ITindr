package com.hits.itindr.mainflow.profile.data

import com.hits.itindr.mainflow.profile.data.dto.TopicDto

interface TopicRepository {
    suspend fun getTopics(): List<TopicDto>
}