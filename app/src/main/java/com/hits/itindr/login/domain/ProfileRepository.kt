package com.hits.itindr.login.domain

interface ProfileRepository {

    suspend fun updateProfile(
        name: String,
        aboutMyself: String?,
        topics: List<String>
    )
}