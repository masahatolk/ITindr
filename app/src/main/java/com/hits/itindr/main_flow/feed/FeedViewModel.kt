package com.hits.itindr.main_flow.feed

import androidx.lifecycle.ViewModel
import com.hits.itindr.main_flow.feed.data.MockFeedRepository
import com.hits.itindr.main_flow.feed.domain.FeedRepository
import com.hits.itindr.main_flow.feed.swipeable_cards.Profile

class FeedViewModel(
    private val repository: FeedRepository = MockFeedRepository(),
) : ViewModel() {
    val profiles: List<Profile> = repository.getProfiles()
}