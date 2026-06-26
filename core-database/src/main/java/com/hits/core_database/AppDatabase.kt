package com.hits.core_database

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters
import com.hits.impl.data.local.dao.ChatDao
import com.hits.impl.data.local.dao.MessageDao
import com.hits.impl.data.local.entity.ChatEntity
import com.hits.impl.data.local.entity.MessageEntity
import com.hits.impl.data.mapper.Converters

@Database(
    entities = [
        ChatEntity::class,
        MessageEntity::class
    ],
    version = 7,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatDao

    abstract fun messageDao(): MessageDao
}