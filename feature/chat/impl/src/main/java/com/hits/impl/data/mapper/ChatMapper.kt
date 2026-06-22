package com.hits.impl.data.mapper

import com.hits.api.model.Chat
import com.hits.impl.data.local.entity.ChatEntity
import com.hits.impl.data.remote.dto.ChatListItemDto
import com.hits.impl.data.remote.dto.CreatedChatDto

fun ChatListItemDto.toEntity(
    currentUserId: String
): ChatEntity {

    return ChatEntity(
        id = chat.id,
        title = chat.title,
        avatar = chat.avatar,
        lastMessage = lastMessage?.text,
        updatedAt = System.currentTimeMillis(),
        ownerUserId = currentUserId
    )
}

fun CreatedChatDto.toEntity(
    currentUserId: String
): ChatEntity {
    return ChatEntity(
        id = id,
        title = title,
        avatar = avatar,
        lastMessage = null,
        updatedAt = System.currentTimeMillis(),
        ownerUserId = currentUserId
    )
}

fun ChatEntity.toDomain(): Chat {
    return Chat(
        id = id,
        title = title,
        lastMessage = lastMessage,
        avatar = avatar,
        updatedAt = updatedAt
    )
}