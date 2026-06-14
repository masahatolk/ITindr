package com.hits.chat_feature.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id: String,
    val text: String,
    val createdAt: String,
    val user: UserDto? = null
)

@Serializable
data class UserDto(
    val userId: String,
    val name: String
)