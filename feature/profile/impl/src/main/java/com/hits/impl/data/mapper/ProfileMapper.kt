package com.hits.impl.data.mapper

import com.hits.api.model.Profile
import com.hits.impl.data.remote.dto.ProfileResponse

fun ProfileResponse.toDomain() =
    Profile(
        id = userId,
        name = name,
        about = aboutMyself.orEmpty(),
        avatar = avatar,
        topics = topics.map { it.toDomain() }
    )