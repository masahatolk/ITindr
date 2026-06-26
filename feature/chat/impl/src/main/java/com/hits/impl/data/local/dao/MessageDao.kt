package com.hits.impl.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hits.impl.data.local.entity.MessageEntity

@Dao
interface MessageDao {

    @Query("""
        SELECT * FROM messages
        WHERE chatId = :chatId
        ORDER BY createdAt DESC
    """)
    suspend fun getMessages(chatId: String): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)
}