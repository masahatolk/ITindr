package com.hits.impl.data.remote.datasource

import com.hits.api.model.Topic
import com.hits.api.model.User
import com.hits.api.repository.ReactionResult
import com.hits.core_network.ApiException
import com.hits.impl.data.remote.api.FeedApi

class FeedRemoteDataSourceImpl(
    private val api: FeedApi
) : FeedRemoteDataSource {
    override suspend fun getAllUsers(
        limit: Int, offset: Int
    ): List<User> {
        val response = api.getAllUsers(
            limit,
            offset,
        )

        if (!response.isSuccessful) {
            throw ApiException(
                response.code(), response.errorBody()?.string().orEmpty()
            )
        }

        return response.body()?.map { dto ->
            User(
                id = dto.userId,
                name = dto.name,
                about = dto.aboutMyself.orEmpty(),
                avatar = dto.avatar,
                topics = dto.topics.map {
                    Topic(it.id, it.title)
                }
            )
        } ?: emptyList()
    }

    override suspend fun getProfiles(): List<User> {

        val response = api.getFeed()

        if (!response.isSuccessful) {
            throw ApiException(
                response.code(), response.errorBody()?.string().orEmpty()
            )
        }

        return response.body()?.map { dto ->
            User(
                id = dto.userId,
                name = dto.name,
                about = dto.aboutMyself.orEmpty(),
                avatar = dto.avatar,
                topics = dto.topics.map {
                    Topic(it.id, it.title)
                })
        } ?: emptyList()
    }

    override suspend fun likeProfile(
        profileId: String
    ): ReactionResult {

        val response = api.like(profileId)

        if (!response.isSuccessful) {
            throw ApiException(
                response.code(), response.errorBody()?.string().orEmpty()
            )
        }

        return ReactionResult(
            isMutual = response.body()?.isMutual ?: false
        )
    }

    override suspend fun dislikeProfile(
        profileId: String
    ): ReactionResult {

        val response = api.dislike(profileId)

        if (!response.isSuccessful) {
            throw ApiException(
                response.code(), response.errorBody()?.string().orEmpty()
            )
        }

        return ReactionResult(
            isMutual = false
        )
    }
}