package com.hits.itindr.main_flow.feed.swipeable_cards.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberSwipeableCardsState(
    initialCardIndex: Int = 0,
    itemCount: () -> Int,
): SwipeableCardsState {
    val state = remember {
        SwipeableCardsState(
            initialCardIndex = initialCardIndex,
            itemCount = itemCount,
        )
    }
    return state
}