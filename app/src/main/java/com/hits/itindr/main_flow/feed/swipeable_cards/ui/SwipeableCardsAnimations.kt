package com.hits.itindr.main_flow.feed.swipeable_cards.ui

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.ui.geometry.Offset

const val CARD_ANIMATION_DAMPING_RATIO = 0.6f
const val CARD_ANIMATION_STIFFNESS = 100f

data class SwipeableCardsAnimations(
    val cardsAnimationSpec: AnimationSpec<Offset> = spring(
        dampingRatio = CARD_ANIMATION_DAMPING_RATIO,
        stiffness = CARD_ANIMATION_STIFFNESS,
    ),
    val rotationAnimationSpec: AnimationSpec<Float> = spring(),
)