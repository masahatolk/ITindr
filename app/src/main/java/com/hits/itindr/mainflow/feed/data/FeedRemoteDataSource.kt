package com.hits.itindr.mainflow.feed.data

import com.hits.itindr.mainflow.feed.domain.ReactionResult
import com.hits.itindr.mainflow.feed.swipeableCards.Profile

interface FeedRemoteDataSource {
    suspend fun getProfiles(): List<Profile>
    suspend fun likeProfile(profileId: String): ReactionResult
    suspend fun dislikeProfile(profileId: String): ReactionResult
}