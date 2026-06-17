package com.hits.itindr.mainflow.profile.data

interface ProfileRepository {

    suspend fun updateProfile(
        name: String,
        aboutMyself: String?,
        topics: List<String>
    )
}