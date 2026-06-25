package com.hits.itindr.mainflow.match

import com.hits.itindr.mainflow.profile.domain.Profile

data class MatchData(
    val chatId: String,
    val chatTitle: String,
    val currentUserAvatar: String?,
    val matchedUser: Profile,
)