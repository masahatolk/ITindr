package com.hits.impl.data.validator

object ChatMessageValidator {

    private val htmlTagRegex = Regex("<[^>]*>")
    private val repeatedCharRegex = Regex("(.)\\1{10,}")
    private val repeatedNewLinesRegex = Regex("(\\n){3,}")

    fun validate(message: String): ValidationResult {
        val trimmed = message.trim()

        if (trimmed.isEmpty()) {
            return ValidationResult.Error("Сообщение не может быть пустым")
        }

        if (trimmed.length > 250) {
            return ValidationResult.Error("Максимальная длина сообщения - 250 символов")
        }

        if (htmlTagRegex.containsMatchIn(trimmed)) {
            return ValidationResult.Error("HTML-теги запрещены")
        }

        if (repeatedCharRegex.containsMatchIn(trimmed)) {
            return ValidationResult.Error("Слишком много одинаковых символов подряд")
        }

        if (repeatedNewLinesRegex.containsMatchIn(trimmed)) {
            return ValidationResult.Error("Слишком много переносов строки подряд")
        }

        return ValidationResult.Success(trimmed)
    }
}

sealed class ValidationResult {
    data class Success(val value: String) : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}