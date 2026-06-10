package com.hits.itindr.mainflow.feed.swipeableCards

data class Profile(
    val id: String,
    val name: String,
    val tags: List<String>,
    val description: String,
    val imageResName: String,
    val imageUrl: String? = null,
)