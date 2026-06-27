package com.hits.impl.ui

import com.hits.api.model.User

sealed interface FeedIntent {
    data object LoadFeed : FeedIntent
    data class Like(val profile: User) : FeedIntent
    data class Dislike(val profile: User) : FeedIntent
    data object ErrorShown : FeedIntent
}

data class FeedUiState(
    val profiles: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isTokenExpired: Boolean = false,
)