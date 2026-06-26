package com.hits.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LikeResponse(
    val isMutual: Boolean
)