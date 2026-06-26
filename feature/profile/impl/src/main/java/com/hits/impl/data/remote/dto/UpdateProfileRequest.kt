package com.hits.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val name: String,
    val aboutMyself: String?,
    val topics: List<String>
)