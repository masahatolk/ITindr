package com.hits.itindr.mainflow.profile.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val userId: String,
    val name: String,
    val aboutMyself: String?,
    val avatar: String?,
    val topics: List<TopicDto>
)