package com.hits.api.model

data class MatchData(
    val chatId: String,
    val chatTitle: String,
    val currentUserAvatar: String?,
    val matchedUser: User,
)