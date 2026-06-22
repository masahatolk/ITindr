package com.hits.core_database

import androidx.room.RoomDatabase
import androidx.room.Database
import com.hits.impl.data.local.dao.ChatDao
import com.hits.impl.data.local.dao.MessageDao
import com.hits.impl.data.local.entity.ChatEntity
import com.hits.impl.data.local.entity.MessageEntity

@Database(
    entities = [
        ChatEntity::class,
        MessageEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatDao

    abstract fun messageDao(): MessageDao
}