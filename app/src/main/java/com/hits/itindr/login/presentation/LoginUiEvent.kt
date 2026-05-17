package com.hits.itindr.login.presentation

sealed interface LoginUiEvent {
    data object OpenMainScreen : LoginUiEvent

    data class ShowError(val messageResId: Int) : LoginUiEvent
}
