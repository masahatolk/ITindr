package com.hits.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id: String,
    val text: String,
    val createdAt: String,
    val attachments: List<String> = emptyList(),
    val user: UserDto? = null,
)