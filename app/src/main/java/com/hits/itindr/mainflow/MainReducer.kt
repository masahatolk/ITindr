package com.hits.itindr.mainflow

private val tabRange = screens.indices

fun reduceMainState(state: MainUiState, intent: MainIntent): MainUiState {
    return when (intent) {
        is MainIntent.SelectTab -> {
            if (intent.index !in tabRange) {
                state
            } else {
                state.copy(selectedIndex = intent.index)
            }
        }
    }
}
