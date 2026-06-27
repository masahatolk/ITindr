package com.hits.impl.data.remote.api

import com.hits.impl.data.remote.dto.ProfileResponse
import com.hits.impl.data.remote.dto.UpdateProfileRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileApi {

    @GET("profile")
    suspend fun getProfile(): Response<ProfileResponse>

    @PATCH("profile")
    suspend fun updateProfile(
        @Body request: UpdateProfileRequest
    ): Response<ProfileResponse>

    @Multipart
    @POST("profile/avatar")
    suspend fun uploadAvatar(
        @Part avatar: MultipartBody.Part
    ): Response<Unit>

    @DELETE("profile/avatar")
    suspend fun deleteAvatar(): Response<Unit>
}