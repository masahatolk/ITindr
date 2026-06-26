package com.hits.impl.data.remote.datasource

import com.hits.api.model.User
import com.hits.api.repository.ReactionResult

interface FeedRemoteDataSource {
    suspend fun getAllUsers(
        limit: Int,
        offset: Int
    ): List<User>
    suspend fun getProfiles(): List<User>
    suspend fun likeProfile(profileId: String): ReactionResult
    suspend fun dislikeProfile(profileId: String): ReactionResult
}