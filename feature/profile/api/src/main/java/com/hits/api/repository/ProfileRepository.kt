package com.hits.api.repository

import android.net.Uri
import com.hits.api.model.Profile

interface ProfileRepository {

    suspend fun getProfile(): Profile

    suspend fun updateProfile(
        name: String,
        aboutMyself: String?,
        topics: List<String>
    ) : Profile

    suspend fun uploadAvatar(
        avatar: Uri
    )

    suspend fun deleteAvatar()
}