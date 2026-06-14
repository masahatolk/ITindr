package com.hits.chat_feature.parsers

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
fun parseDate(date: String): Long {
    return runCatching {
        java.time.OffsetDateTime.parse(date).toInstant().toEpochMilli()
    }.getOrDefault(System.currentTimeMillis())
}