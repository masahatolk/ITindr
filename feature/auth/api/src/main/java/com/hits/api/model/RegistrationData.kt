package com.hits.api.model

@Parcelize
data class RegistrationData(
    val email: String,
    val password: String
) : Parcelable