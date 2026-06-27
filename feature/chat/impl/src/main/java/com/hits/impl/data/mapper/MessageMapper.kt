package com.hits.impl.data.mapper

import com.hits.api.model.ChatMessage
import com.hits.api.model.ChatMessageUi
import com.hits.impl.data.local.entity.MessageEntity
import com.hits.impl.data.remote.dto.MessageDto
import com.hits.impl.data.remote.parser.parseDate
import com.hits.impl.data.remote.parser.toRussianDate

fun MessageDto.toEntity(chatId: String): MessageEntity {
    return MessageEntity(
        id = id,
        chatId = chatId,
        text = text.orEmpty(),
        createdAt = parseDate(createdAt),
        userId = user?.userId,
        avatar = user?.avatar,
        attachments = attachments,
    )
}

fun MessageEntity.toDomain(): ChatMessage {
    return ChatMessage(
        id = id,
        chatId = chatId,
        text = text,
        createdAt = createdAt,
        senderName = null,
        userId = userId,
        avatar = avatar,
        attachments = attachments,
    )
}

fun ChatMessage.toUi(
    currentUserId: String
): ChatMessageUi {

    return ChatMessageUi(
        id = id,
        text = text,
        senderName = senderName,
        isOutgoing = userId == currentUserId,
        avatar = avatar,
        createdAt = createdAt.toRussianDate(),
        attachments = attachments,
    )
}