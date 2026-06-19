package com.hits.impl.data.mapper

import com.hits.api.model.Chat
import com.hits.impl.data.local.entity.ChatEntity
import com.hits.impl.data.remote.dto.ChatDto


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