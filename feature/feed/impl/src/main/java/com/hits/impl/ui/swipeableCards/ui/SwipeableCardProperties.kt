package com.hits.impl.ui.swipeableCards.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object SwipeableCardsDefaults {
    const val VISIBLE_CARDS_IN_STACK = 3
    const val LOCK_BELOW_CARD_DRAGGING = true
    const val ENABLE_ROTATION = true
    const val ENABLE_HAPTIC_FEEDBACK_ON_THRESHOLD = true
    const val DRAGGING_ACCELERATION = 1f
    val STACKED_CARDS_OFFSET = 30.dp
    val SWIPE_THRESHOLD = 100.dp
    val PADDING = 10.dp
}

data class SwipeableCardsProperties(
    val padding: Dp = SwipeableCardsDefaults.PADDING,
    val swipeThreshold: Dp = SwipeableCardsDefaults.SWIPE_THRESHOLD,
    val lockBelowCardDragging: Boolean = SwipeableCardsDefaults.LOCK_BELOW_CARD_DRAGGING,
    val enableRotation: Boolean = SwipeableCardsDefaults.ENABLE_ROTATION,
    val enableHapticFeedbackOnThreshold: Boolean = SwipeableCardsDefaults.ENABLE_HAPTIC_FEEDBACK_ON_THRESHOLD,
    val stackedCardsOffset: Dp = SwipeableCardsDefaults.STACKED_CARDS_OFFSET,
    val draggingAcceleration: Float = SwipeableCardsDefaults.DRAGGING_ACCELERATION,
)