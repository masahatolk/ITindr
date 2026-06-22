package com.hits.impl.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hits.impl.data.local.entity.ChatEntity

@Dao
interface ChatDao {

    @Query("""
    SELECT *
    FROM chats
    WHERE ownerUserId = :userId
    ORDER BY updatedAt DESC
""")
    suspend fun getChats(userId: String): List<ChatEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChats(chats: List<ChatEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: ChatEntity)
}