package com.hits.impl.ui.swipeableCards.state

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import com.hits.impl.ui.swipeableCards.ui.SwipeableCardDirection
import com.hits.impl.ui.swipeableCards.ui.SwipeableCardsDefaults

class SwipeableCardsState(
    val visibleCardsInStack: Int = SwipeableCardsDefaults.VISIBLE_CARDS_IN_STACK,
    initialCardIndex: Int = 0,
    private val itemCount: () -> Int,
) {

    var size by mutableStateOf(IntSize.Zero)
        private set

    val dragOffsets = mutableStateMapOf<Int, Offset>()

    var currentCardIndex by mutableIntStateOf(initialCardIndex)
        private set

    val swipingVisibleCards = mutableStateListOf<Int>()

    var canSwipeBack = derivedStateOf { currentCardIndex > 0 }
        private set

    val visibleCardIndexes = derivedStateOf {
        val maxVisible = currentCardIndex + visibleCardsInStack - 1
        val lastIndex = minOf(maxVisible, itemCount() - 1)
        (currentCardIndex..lastIndex).toList() + swipingVisibleCards
    }

    internal fun onDragOffsetChange(
        index: Int,
        offset: Offset,
    ) {
        dragOffsets[index] = offset
    }

    internal fun onSizeChange(size: IntSize) {
        this.size = size
    }

    fun goBack() {
        swipingVisibleCards.remove(currentCardIndex)
        if (currentCardIndex > 0) {
            currentCardIndex--
            dragOffsets.remove(currentCardIndex)
            swipingVisibleCards.remove(currentCardIndex)
        }
    }

    fun moveNext() {
        if (currentCardIndex < itemCount()) {
            currentCardIndex++
        }
    }

    fun swipe(direction: SwipeableCardDirection) {
        if (currentCardIndex !in 0..<itemCount()) return

        val targetX = when (direction) {
            SwipeableCardDirection.Left -> -size.width.toFloat() * 1.5f
            SwipeableCardDirection.Right -> size.width.toFloat() * 1.5f
        }

        swipingVisibleCards.add(currentCardIndex)
        dragOffsets[currentCardIndex] = Offset(targetX, 0f)
        moveNext()
    }

    fun completeSwipe(index: Int) {
        swipingVisibleCards.remove(index)
        dragOffsets.remove(index)
    }

    fun setCurrentIndex(index: Int) {
        if (index in 0..<itemCount()) {
            currentCardIndex = index
            dragOffsets.clear()
        }
    }
}