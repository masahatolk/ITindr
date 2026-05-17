package com.hits.itindr.main_flow

data class MainUiState(
    val selectedIndex: Int = 0,
)

sealed interface MainIntent {
    data class SelectTab(val index: Int) : MainIntent
}
