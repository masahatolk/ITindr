package com.hits.itindr.main_flow.feed.domain

import com.hits.itindr.main_flow.feed.swipeable_cards.Profile

interface FeedRepository {
    fun getProfiles(): List<Profile>
}
