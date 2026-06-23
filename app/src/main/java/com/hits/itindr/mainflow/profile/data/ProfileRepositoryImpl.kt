package com.hits.itindr.mainflow.profile.data

import android.net.Uri
import com.hits.itindr.mainflow.profile.domain.Profile
import com.hits.itindr.mainflow.profile.domain.toDomain

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