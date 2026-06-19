package com.hits.impl.data.remote.parser

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime

@RequiresApi(Build.VERSION_CODES.O)
fun parseDate(date: String): Long {
    return runCatching {
        OffsetDateTime.parse(date).toInstant().toEpochMilli()
    }.getOrDefault(System.currentTimeMillis())
}