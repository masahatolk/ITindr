package com.hits.itindr.login.data.dto

data class UpdateProfileRequest(
    val name: String,
    val aboutMyself: String?,
    val topics: List<String>
)