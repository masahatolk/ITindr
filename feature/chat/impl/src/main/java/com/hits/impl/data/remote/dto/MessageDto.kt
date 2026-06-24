package com.hits.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id: String,
    val text: String? = null,
    val createdAt: String,
    val user: UserDto? = null,
    val attachments: List<String> = emptyList(),
)