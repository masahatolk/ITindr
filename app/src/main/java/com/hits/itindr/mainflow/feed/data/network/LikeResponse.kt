package com.hits.itindr.mainflow.feed.data.network

import kotlinx.serialization.Serializable

@Serializable
data class LikeResponse(
    val isMutual: Boolean
)