package com.hits.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.api.repository.AuthRepository
import com.hits.api.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _event = MutableSharedFlow<ProfileUiEvent>()
    val event = _event.asSharedFlow()

    private val _state =
        MutableStateFlow(ProfileUiState())

    val state = _state.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {

            _state.update {
                it.copy(isLoading = true)
            }

            runCatching {
                profileRepository.getProfile()
            }.onSuccess { profile ->

                _state.update {
                    it.copy(
                        isLoading = false,
                        profile = profile
                    )
                }

            }.onFailure {

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = it.error.toString()
                    )
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()

            _event.emit(ProfileUiEvent.OpenLoginScreen)
        }
    }
}

sealed interface ProfileUiEvent {
    data object OpenLoginScreen : ProfileUiEvent
}
