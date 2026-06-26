package com.hits.impl.data.repository

import com.hits.api.model.User
import com.hits.api.repository.FeedRepository
import com.hits.api.repository.ReactionResult
import com.hits.impl.data.remote.datasource.FeedRemoteDataSource

class FeedRepositoryImpl(
    private val remoteDataSource: FeedRemoteDataSource,
) : FeedRepository {
    private var selectedUser: User? = null

    override suspend fun getAllUsers(
        limit: Int,
        offset: Int
    ): List<User> = remoteDataSource.getAllUsers(limit, offset)

    override suspend fun getProfiles(): List<User> = remoteDataSource.getProfiles()

    override suspend fun likeProfile(profileId: String): ReactionResult {
        return remoteDataSource.likeProfile(profileId)
    }

    override suspend fun dislikeProfile(profileId: String): ReactionResult {
        return remoteDataSource.dislikeProfile(profileId)
    }

    override fun setSelectedUser(user: User) {
        selectedUser = user
    }

    override fun getSelectedUser(): User? = selectedUser
}