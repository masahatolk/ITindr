package com.hits.itindr.mainflow.profile.data

import android.content.Context
import android.net.Uri
import com.hits.core_media.extensions.toAvatarPart
import com.hits.core_network.ApiException
import com.hits.itindr.mainflow.profile.data.dto.ProfileResponse
import com.hits.itindr.mainflow.profile.data.dto.UpdateProfileRequest

class ProfileRemoteDataSourceImpl(
    private val profileApi: ProfileApi,
    private val context: Context
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

    override suspend fun uploadAvatar(avatar: Uri) {
        val response = profileApi.uploadAvatar(avatar.toAvatarPart(context))

        if (!response.isSuccessful) {

            throw ApiException(
                response.code(),
                response.errorBody()?.string().orEmpty()
            )
        }
    }

    override suspend fun deleteAvatar() {
        val response = profileApi.deleteAvatar()

        if (!response.isSuccessful) {

            throw ApiException(
                response.code(),
                response.errorBody()?.string().orEmpty()
            )
        }
    }
}