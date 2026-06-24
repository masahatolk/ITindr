package com.hits.itindr.mainflow.feed.data

import com.hits.itindr.mainflow.feed.domain.FeedRepository
import com.hits.itindr.mainflow.feed.domain.ReactionResult
import com.hits.itindr.mainflow.profile.domain.Profile

class FeedRepositoryImpl(
    private val remoteDataSource: FeedRemoteDataSource,
) : FeedRepository {
    override suspend fun getAllUsers(
        limit: Int,
        offset: Int
    ): List<Profile> = remoteDataSource.getAllUsers(limit, offset)

    override suspend fun getProfiles(): List<Profile> = remoteDataSource.getProfiles()

    override suspend fun likeProfile(profileId: String): ReactionResult {
        return remoteDataSource.likeProfile(profileId)
    }

    override suspend fun dislikeProfile(profileId: String): ReactionResult {
        return remoteDataSource.dislikeProfile(profileId)
    }
}