package com.hits.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreatedChatDto(
    val id: String,
    val title: String,
    val avatar: String? = null,
)