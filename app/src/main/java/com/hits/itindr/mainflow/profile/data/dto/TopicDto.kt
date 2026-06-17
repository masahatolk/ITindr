package com.hits.itindr.mainflow.profile.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TopicDto(
    val id: String,
    val title: String
)
