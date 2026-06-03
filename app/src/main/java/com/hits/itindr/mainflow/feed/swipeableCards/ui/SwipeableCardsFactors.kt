package com.hits.itindr.mainflow.feed.swipeableCards.ui

import androidx.compose.ui.geometry.Offset
import com.hits.itindr.mainflow.feed.swipeableCards.state.SwipeableCardsState

data class SwipeableCardsFactors(
    val rotationFactor: (offset: Offset) -> Float = {
        it.x / 50
    },
    val scaleFactor: (
        index: Int,
        state: SwipeableCardsState,
        props: SwipeableCardsProperties,
    ) -> Float = { index, state, props ->
        1f
    },
    val cardOffsetCalculation: (
        index: Int,
        state: SwipeableCardsState,
        props: SwipeableCardsProperties,
    ) -> Offset = { index, state, props ->
        val offset =
            props.stackedCardsOffset.value * (state.visibleCardsInStack - 1 - (index - state.currentCardIndex))
        Offset(offset, -offset)
    },
)