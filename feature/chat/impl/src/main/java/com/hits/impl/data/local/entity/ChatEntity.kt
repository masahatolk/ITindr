package com.hits.impl.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey
    val id: String,
    val ownerUserId: String,
    val title: String,
    val lastMessage: String?,
    val avatar: String?,
    val updatedAt: Long,
)