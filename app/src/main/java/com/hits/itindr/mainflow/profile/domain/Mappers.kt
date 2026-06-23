package com.hits.itindr.mainflow.profile.domain

import com.hits.itindr.mainflow.profile.data.dto.ProfileResponse
import com.hits.itindr.mainflow.profile.data.dto.TopicDto

fun ProfileResponse.toDomain() =
    Profile(
        id = userId,
        name = name,
        about = aboutMyself.orEmpty(),
        avatar = avatar,
        topics = topics.map { it.toDomain() }
    )

fun TopicDto.toDomain() =
    Topic(
        id = id,
        title = title
    )