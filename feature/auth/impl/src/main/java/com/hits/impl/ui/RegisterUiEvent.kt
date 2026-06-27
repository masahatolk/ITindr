package com.hits.impl.ui

sealed interface RegisterUiEvent {
    data object OpenInfoScreen : RegisterUiEvent
    data class ShowError(val messageResId: Int) : RegisterUiEvent
}
