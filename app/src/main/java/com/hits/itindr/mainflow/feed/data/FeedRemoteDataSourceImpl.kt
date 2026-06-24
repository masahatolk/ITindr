package com.hits.itindr.mainflow.feed.data

import com.hits.core_network.ApiException
import com.hits.itindr.mainflow.feed.data.network.FeedApi
import com.hits.itindr.mainflow.feed.domain.ReactionResult
import com.hits.itindr.mainflow.profile.domain.Profile
import com.hits.itindr.mainflow.profile.domain.Topic

class FeedRemoteDataSourceImpl(
    private val api: FeedApi
) : FeedRemoteDataSource {
    override suspend fun getAllUsers(
        limit: Int, offset: Int
    ): List<Profile> {
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
            Profile(
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

    override suspend fun getProfiles(): List<Profile> {

        val response = api.getFeed()

        if (!response.isSuccessful) {
            throw ApiException(
                response.code(), response.errorBody()?.string().orEmpty()
            )
        }

        return response.body()?.map { dto ->
            Profile(
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