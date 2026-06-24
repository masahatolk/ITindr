package com.hits.itindr.mainflow.feed.domain

import com.hits.itindr.mainflow.profile.domain.Profile


interface FeedRepository {
    suspend fun getAllUsers(
        limit: Int,
        offset: Int
    ): List<Profile>
    suspend fun getProfiles(): List<Profile>
    suspend fun likeProfile(profileId: String): ReactionResult
    suspend fun dislikeProfile(profileId: String): ReactionResult
}

data class ReactionResult(
    val isMutual: Boolean,
)