package com.hits.itindr.mainflow.feed.data

import com.hits.core_network.ApiException
import com.hits.itindr.mainflow.feed.data.network.FeedApi
import com.hits.itindr.mainflow.feed.domain.ReactionResult
import com.hits.itindr.mainflow.feed.swipeableCards.Profile

class FeedRemoteDataSourceImpl(
    private val api: FeedApi
) : FeedRemoteDataSource {

    override suspend fun getProfiles(): List<Profile> {

        val response = api.getFeed()

        if (!response.isSuccessful) {
            throw ApiException(
                response.code(),
                response.errorBody()?.string().orEmpty()
            )
        }

        return response.body()
            ?.map { dto ->
                Profile(
                    id = dto.userId,
                    name = dto.name,
                    description = dto.aboutMyself.orEmpty(),
                    imageResName = "photo",
                    imageUrl = dto.avatar,
                    tags = dto.topics.map { it.title }
                )
            }
            ?: emptyList()
    }

    override suspend fun likeProfile(
        profileId: String
    ): ReactionResult {

        val response = api.like(profileId)

        if (!response.isSuccessful) {
            throw ApiException(
                response.code(),
                response.errorBody()?.string().orEmpty()
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
                response.code(),
                response.errorBody()?.string().orEmpty()
            )
        }

        return ReactionResult(
            isMutual = false
        )
    }
}