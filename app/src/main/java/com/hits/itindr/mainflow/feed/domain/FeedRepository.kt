package com.hits.itindr.mainflow.feed.domain

import com.hits.itindr.mainflow.feed.swipeableCards.Profile

interface FeedRepository {
    fun getProfiles(): List<Profile>
}
