package com.hits.impl.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.hits.api.model.ChatMessage
import com.hits.api.model.ChatMessageUi
import com.hits.impl.data.local.entity.MessageEntity
import com.hits.impl.data.remote.dto.MessageDto
import com.hits.impl.data.remote.parser.parseDate

@RequiresApi(Build.VERSION_CODES.O)
fun MessageDto.toEntity(chatId: String): MessageEntity {
    return MessageEntity(
        id = id,
        chatId = chatId,
        text = text,
        createdAt = parseDate(createdAt),
        userId = user?.userId
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
    )
}

fun ChatMessage.toUi(
    currentUserId: String
): ChatMessageUi {

    return ChatMessageUi(
        id = id,
        text = text,
        senderName = senderName,
        isOutgoing = userId == currentUserId
    )
}