package com.hits.itindr.mainflow.profile.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val name: String,
    val aboutMyself: String?,
    val topics: List<String>
)