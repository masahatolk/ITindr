package com.hits.impl.data.remote.datasource

import android.net.Uri
import com.hits.impl.data.remote.dto.ProfileResponse

interface ProfileRemoteDataSource {

    suspend fun getProfile(): ProfileResponse

    suspend fun updateProfile(
        name: String,
        aboutMyself: String?,
        topics: List<String>
    ) : ProfileResponse

    suspend fun uploadAvatar(
        avatar: String
    )

    suspend fun deleteAvatar()
}