package com.hits.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatInfoDto(
    val id: String,
    val title: String,
    val avatar: String? = null
)