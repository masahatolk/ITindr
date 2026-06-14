package com.hits.chat_feature.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hits.chat_feature.data.local.dao.ChatDao
import com.hits.chat_feature.data.local.dao.MessageDao
import com.hits.chat_feature.data.local.entity.ChatEntity
import com.hits.chat_feature.data.local.entity.MessageEntity

@Database(
    entities = [
        ChatEntity::class,
        MessageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatDao

    abstract fun messageDao(): MessageDao
}