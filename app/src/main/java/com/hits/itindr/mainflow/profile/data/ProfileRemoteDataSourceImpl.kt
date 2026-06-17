package com.hits.itindr.mainflow.profile.data

import com.hits.core_network.ApiException
import com.hits.itindr.mainflow.profile.data.dto.UpdateProfileRequest

class ProfileRemoteDataSourceImpl(
    private val profileApi: ProfileApi
) : ProfileRemoteDataSource {

    override suspend fun updateProfile(
        name: String,
        aboutMyself: String?,
        topics: List<String>
    ) {

        val response = profileApi.updateProfile(
            UpdateProfileRequest(
                name = name,
                aboutMyself = aboutMyself,
                topics = topics
            )
        )

        if (!response.isSuccessful) {
            throw ApiException(
                response.code(),
                response.errorBody()?.string().orEmpty()
            )
        }
    }
}