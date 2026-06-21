package com.hits.impl.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatListItemDto(
    val chat: ChatInfoDto,
    val lastMessage: MessageDto? = null
)