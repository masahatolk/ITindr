package com.hits.api.model

data class Profile(
    val id: String,
    val name: String,
    val about: String,
    val avatar: String?,
    val topics: List<Topic>
)