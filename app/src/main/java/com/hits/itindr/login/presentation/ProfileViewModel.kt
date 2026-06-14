package com.hits.itindr.login.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.itindr.login.domain.LogoutUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _event = MutableSharedFlow<ProfileUiEvent>()
    val event = _event.asSharedFlow()

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            Log.d("Profile", "Logout success")
            _event.emit(ProfileUiEvent.OpenLoginScreen)
        }
    }
}

sealed interface ProfileUiEvent {
    data object OpenLoginScreen : ProfileUiEvent
}