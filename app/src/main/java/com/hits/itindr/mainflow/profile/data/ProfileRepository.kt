package com.hits.itindr.mainflow.profile.data

import com.hits.itindr.mainflow.profile.domain.Profile

interface ProfileRepository {

    suspend fun getProfile(): Profile

    suspend fun updateProfile(
        name: String,
        aboutMyself: String?,
        topics: List<String>
    ) : Profile
}