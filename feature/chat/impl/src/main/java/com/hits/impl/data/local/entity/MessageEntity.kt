package com.hits.impl.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "messages",
    indices = [Index("chatId")]
)
data class MessageEntity(
    @PrimaryKey
    val id: String,
    val chatId: String,
    val text: String,
    val createdAt: Long,
    val userId: String?,
    val avatar: String?,
    val attachments: List<String>
)