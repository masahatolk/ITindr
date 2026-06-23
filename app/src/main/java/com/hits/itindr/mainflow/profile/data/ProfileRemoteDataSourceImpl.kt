package com.hits.itindr.mainflow.profile.data

import com.hits.core_network.ApiException
import com.hits.itindr.mainflow.profile.data.dto.ProfileResponse
import com.hits.itindr.mainflow.profile.data.dto.UpdateProfileRequest

class ProfileRemoteDataSourceImpl(
    private val profileApi: ProfileApi
) : ProfileRemoteDataSource {

    override suspend fun getProfile(): ProfileResponse {

        val response = profileApi.getProfile()

        if (response.isSuccessful) {
            return response.body()
                ?: throw ApiException(
                    response.code(),
                    "Пустой ответ сервера"
                )
        }

        throw ApiException(
            response.code(),
            response.errorBody()?.string().orEmpty()
        )
    }

    override suspend fun updateProfile(
        name: String,
        aboutMyself: String?,
        topics: List<String>
    ) : ProfileResponse {

        val response = profileApi.updateProfile(
            UpdateProfileRequest(
                name = name,
                aboutMyself = aboutMyself,
                topics = topics
            )
        )

        if (response.isSuccessful) {
            return response.body()
                ?: throw ApiException(
                    response.code(),
                    "Пустой ответ сервера"
                )
        }

        throw ApiException(
            response.code(),
            response.errorBody()?.string().orEmpty()
        )
    }
}