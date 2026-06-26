package com.hits.api.model

@Parcelize
data class User(
    val id: String,
    val name: String,
    val about: String,
    val avatar: String?,
    val topics: List<Topic>
) : Parcelable