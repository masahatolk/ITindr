package com.hits.api.model

@Parcelize
data class Topic(
    val id: String,
    val title: String
) : Parcelable