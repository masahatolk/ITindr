package com.hits.itindr.mainflow.profile.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val id: String,
    val name: String,
    val about: String,
    val avatar: String?,
    val topics: List<Topic>
) : Parcelable

@Parcelize
data class Topic(
    val id: String,
    val title: String
) : Parcelable