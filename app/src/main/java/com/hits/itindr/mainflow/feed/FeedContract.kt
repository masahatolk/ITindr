package com.hits.itindr.mainflow.feed

import com.hits.itindr.mainflow.feed.swipeableCards.Profile

sealed interface FeedIntent {
    data object LoadFeed : FeedIntent
    data class Like(val profile: Profile) : FeedIntent
    data class Dislike(val profile: Profile) : FeedIntent
    data object ErrorShown : FeedIntent
    data object MutualMatchShown : FeedIntent
}

data class FeedUiState(
    val profiles: List<Profile> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mutualMatchMessage: String? = null,
    val isTokenExpired: Boolean = false,
)