package com.hits.itindr.main_flow.feed.swipeable_cards

data class Profile(
    val name: String,
    val tags: List<String>,
    val description: String,
    val imageResName: String,
)