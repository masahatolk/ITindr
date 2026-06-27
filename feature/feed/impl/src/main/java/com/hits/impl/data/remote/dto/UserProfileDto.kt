package com.hits.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
    val userId: String,
    val name: String,
    val aboutMyself: String? = null,
    val avatar: String? = null,
    val topics: List<TopicDto> = emptyList()
)