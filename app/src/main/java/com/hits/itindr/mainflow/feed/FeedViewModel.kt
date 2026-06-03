package com.hits.itindr.mainflow.feed

import androidx.lifecycle.ViewModel
import com.hits.itindr.mainflow.feed.data.MockFeedRepository
import com.hits.itindr.mainflow.feed.domain.FeedRepository
import com.hits.itindr.mainflow.feed.swipeableCards.Profile

class FeedViewModel(
    private val repository: FeedRepository = MockFeedRepository(),
) : ViewModel() {
    val profiles: List<Profile> = repository.getProfiles()
}