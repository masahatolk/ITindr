package com.hits.chat_feature.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.hits.chat_feature.data.local.entity.MessageEntity
import com.hits.chat_feature.data.remote.dto.MessageDto
import com.hits.chat_feature.domain.ChatMessage
import com.hits.chat_feature.parsers.parseDate

@RequiresApi(Build.VERSION_CODES.O)
fun MessageDto.toEntity(chatId: String): MessageEntity {
    return MessageEntity(
        id = id,
        chatId = chatId,
        text = text,
        createdAt = parseDate(createdAt),
        isOutgoing = false,
    )
}

fun MessageEntity.toDomain(): ChatMessage {
    return ChatMessage(
        id = id,
        chatId = chatId,
        text = text,
        createdAt = createdAt,
        isOutgoing = isOutgoing,
        senderName = null
    )
}