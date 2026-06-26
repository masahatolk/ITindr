package com.hits.impl.data.repository

import android.net.Uri
import com.hits.api.model.Profile
import com.hits.api.repository.ProfileRepository
import com.hits.impl.data.mapper.toDomain
import com.hits.impl.data.remote.datasource.ProfileRemoteDataSource

class ProfileRepositoryImpl(
    private val remoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {

    override suspend fun getProfile(): Profile {
        return remoteDataSource.getProfile().toDomain()
    }

    override suspend fun updateProfile(
        name: String,
        aboutMyself: String?,
        topics: List<String>
    ): Profile {
        return remoteDataSource.updateProfile(
            name,
            aboutMyself,
            topics
        ).toDomain()
    }

    override suspend fun uploadAvatar(avatar: Uri) {
        remoteDataSource.uploadAvatar(avatar)
    }

    override suspend fun deleteAvatar() {
        remoteDataSource.deleteAvatar()
    }
}