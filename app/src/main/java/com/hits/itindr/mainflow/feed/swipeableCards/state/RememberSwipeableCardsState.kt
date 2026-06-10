package com.hits.itindr.mainflow.feed.swipeableCards.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun rememberSwipeableCardsState(
    initialCardIndex: Int = 0,
    itemCount: () -> Int,
): SwipeableCardsState {
    val currentItemCount = rememberUpdatedState(itemCount)
    val state = remember {
        SwipeableCardsState(
            initialCardIndex = initialCardIndex,
            itemCount = { currentItemCount.value() },
        )
    }
    return state
}