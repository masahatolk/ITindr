package com.hits.itindr.login.presentation

sealed interface RegisterUiEvent {
    data object OpenInfoScreen : RegisterUiEvent
    data class ShowError(val messageResId: Int) : RegisterUiEvent
}
