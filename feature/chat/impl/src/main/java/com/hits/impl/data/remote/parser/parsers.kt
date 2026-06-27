package com.hits.impl.data.remote.parser

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.Instant
import java.time.ZoneId
import java.util.Locale

fun parseDate(date: String): Long {
    return runCatching {
        val cleaned = date.substringBefore("[")

        OffsetDateTime.parse(cleaned)
            .toInstant()
            .toEpochMilli()

    }.getOrDefault(System.currentTimeMillis())
}

fun Long.toRussianDate(): String {
    val formatter = DateTimeFormatter.ofPattern(
        "HH:mm • d MMMM yyyy",
        Locale.forLanguageTag("ru-RU")
    )

    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}