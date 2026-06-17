package com.hits.itindr.mainflow.feed.data.network

import kotlinx.serialization.Serializable

@Serializable
data class TopicDto(
    val id: String,
    val title: String
)