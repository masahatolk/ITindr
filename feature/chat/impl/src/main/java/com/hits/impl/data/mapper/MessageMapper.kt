package com.hits.impl.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.hits.api.model.ChatMessage
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