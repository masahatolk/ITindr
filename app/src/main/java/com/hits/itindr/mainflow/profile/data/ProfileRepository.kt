package com.hits.itindr.mainflow.profile.data

import com.hits.itindr.mainflow.profile.data.dto.ProfileResponse

interface ProfileRepository {

    suspend fun getProfile(): ProfileResponse

    suspend fun updateProfile(
        name: String,
        aboutMyself: String?,
        topics: List<String>
    )
}