package com.hits.impl.data.mapper

import com.hits.api.model.Topic
import com.hits.impl.data.remote.dto.TopicDto

fun TopicDto.toDomain() =
    Topic(
        id = id,
        title = title
    )

fun Topic.toDton() =
    TopicDto(
        id = id,
        title = title
    )