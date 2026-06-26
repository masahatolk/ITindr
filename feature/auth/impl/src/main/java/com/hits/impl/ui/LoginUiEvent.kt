package com.hits.impl.ui

sealed interface LoginUiEvent {
    data object OpenMainScreen : LoginUiEvent

    data class ShowError(val messageResId: Int) : LoginUiEvent
}
