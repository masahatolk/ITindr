package com.hits.api.repository

import com.hits.api.model.User

interface FeedRepository {
    suspend fun getAllUsers(
        limit: Int,
        offset: Int
    ): List<User>
    suspend fun getProfiles(): List<User>
    suspend fun likeProfile(profileId: String): ReactionResult
    suspend fun dislikeProfile(profileId: String): ReactionResult
}

data class ReactionResult(
    val isMutual: Boolean,
)