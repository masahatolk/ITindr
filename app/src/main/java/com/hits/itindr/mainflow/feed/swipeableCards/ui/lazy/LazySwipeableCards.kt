package com.hits.itindr.mainflow.feed.swipeableCards.ui.lazy

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.round
import com.hits.itindr.mainflow.feed.swipeableCards.state.SwipeableCardsState
import com.hits.itindr.mainflow.feed.swipeableCards.ui.SwipeableCardDirection
import com.hits.itindr.mainflow.feed.swipeableCards.ui.SwipeableCardsAnimations
import com.hits.itindr.mainflow.feed.swipeableCards.ui.SwipeableCardsFactors
import com.hits.itindr.mainflow.feed.swipeableCards.ui.SwipeableCardsProperties
import kotlinx.coroutines.launch

@Composable
fun <T> LazySwipeableCards(
    modifier: Modifier = Modifier,
    state: SwipeableCardsState,
    properties: SwipeableCardsProperties = SwipeableCardsProperties(),
    animations: SwipeableCardsAnimations = SwipeableCardsAnimations(),
    factors: SwipeableCardsFactors = SwipeableCardsFactors(),
    onSwipe: (T, SwipeableCardDirection) -> Unit,
    content: LazySwipeableCardsScope<T>.() -> Unit,
) {
    val itemProvider = rememberItemProvider<T>(
        state = state,
        properties = properties,
        animations = animations,
        factors = factors,
        onSwipe = onSwipe,
        customLazyListScope = content,
    )

    val indexes by state.visibleCardIndexes

    val animatables = remember {
        mutableStateMapOf<Int, Animatable<Offset, *>>()
    }

    LaunchedEffect(indexes) {
        indexes.forEach { index ->
            animatables.putIfAbsent(index, Animatable(Offset.Zero, Offset.VectorConverter))
        }

        animatables.forEach { index, animatable ->
            launch {
                animatable.animateTo(
                    targetValue = factors.cardOffsetCalculation(index, state, properties),
                    animationSpec = tween()
                )
            }
        }
    }

    LazyLayout(
        modifier = modifier
            .onGloballyPositioned {
                state.onSizeChange(it.size)
            }
            .padding(
                end = properties.padding,
                top = properties.padding.div(2)
            ),
        itemProvider = { itemProvider },
    ) { constraints ->

        val indexesWithPlaceables = indexes.associateWith { that ->
            compose(that).map { it.measure(constraints) }
        }

        val maxHeight = indexesWithPlaceables.values
            .flatMap { it }
            .maxOfOrNull { it.height } ?: 0

        layout(width = constraints.maxWidth, height = maxHeight) {
            indexesWithPlaceables.forEach { (index, placeables) ->
                val item = itemProvider.getItem(index)
                item?.let {
                    placeables.forEach { placeable ->
                        placeable.placeRelative(
                            position = animatables[index]?.value?.round() ?: IntOffset.Zero,
                            zIndex = -index.toFloat(),
                        )
                    }
                }
            }
        }
    }
}