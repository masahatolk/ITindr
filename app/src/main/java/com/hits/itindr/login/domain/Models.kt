package com.hits.itindr.login.domain

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class RegistrationData(
    val email: String,
    val password: String
) : Parcelable