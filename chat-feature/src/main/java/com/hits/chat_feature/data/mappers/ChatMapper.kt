package com.hits.chat_feature.data.mappers

import com.hits.chat_feature.data.local.entity.ChatEntity
import com.hits.chat_feature.data.remote.dto.ChatDto
import com.hits.chat_feature.domain.Chat

fun ChatDto.toEntity(): ChatEntity {
    return ChatEntity(
        id = id,
        title = title,
        lastMessage = lastMessage?.text,
        updatedAt = System.currentTimeMillis()
    )
}

fun ChatEntity.toDomain(): Chat {
    return Chat(
        id = id,
        title = title,
        lastMessage = lastMessage,
        updatedAt = updatedAt
    )
}