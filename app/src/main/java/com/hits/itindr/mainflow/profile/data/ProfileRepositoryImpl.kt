package com.hits.itindr.mainflow.profile.data

class ProfileRepositoryImpl(
    private val remoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {

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