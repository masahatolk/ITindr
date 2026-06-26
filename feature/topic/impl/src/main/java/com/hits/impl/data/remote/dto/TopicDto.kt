package com.hits.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TopicDto(
    val id: String,
    val title: String
)