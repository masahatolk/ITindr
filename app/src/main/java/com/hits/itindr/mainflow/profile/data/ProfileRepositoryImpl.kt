package com.hits.itindr.mainflow.profile.data

import com.hits.itindr.mainflow.profile.data.dto.ProfileResponse

class ProfileRepositoryImpl(
    private val remoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {

    override suspend fun getProfile(): ProfileResponse {
        return remoteDataSource.getProfile()
    }

    override suspend fun updateProfile(
        name: String,
        aboutMyself: String?,
        topics: List<String>
    ) {
        remoteDataSource.updateProfile(
            name,
            aboutMyself,
            topics
        )
    }
}