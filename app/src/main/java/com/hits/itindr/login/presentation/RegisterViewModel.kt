package com.hits.itindr.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.itindr.R
import com.hits.itindr.login.domain.AuthRepository
import com.hits.itindr.login.domain.RegisterError
import com.hits.itindr.login.domain.RegisterResult
import com.hits.itindr.login.domain.RegisterUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _event =
        MutableSharedFlow<RegisterUiEvent>(extraBufferCapacity = EVENT_BUFFER_CAPACITY)
    val event: SharedFlow<RegisterUiEvent> = _event.asSharedFlow()

    fun onRegisterClicked(
        email: String,
        password: String,
        passwordConfirm: String
    ) {
        val result =
            registerUseCase.validate(
                email.trim(),
                password,
                passwordConfirm
            )

        when (result) {
            is RegisterResult.Error -> {
                _event.tryEmit(
                    RegisterUiEvent.ShowError(
                        result.type.toMessageRes()
                    )
                )
            }

            RegisterResult.Success -> {

                viewModelScope.launch {

                    try {
                        authRepository.register(
                            email = email,
                            password = password
                        )

                        _event.emit(RegisterUiEvent.OpenInfoScreen)
                    } catch (e: Exception) {
                        _event.emit(RegisterUiEvent.ShowError(R.string.register_error_request_failed))
                    }
                }
            }
        }
    }

    private fun RegisterError.toMessageRes(): Int {
        return when (this) {
            RegisterError.EMPTY_EMAIL -> R.string.login_error_empty_email
            RegisterError.INVALID_EMAIL -> R.string.login_error_invalid_email
            RegisterError.EMPTY_PASSWORD -> R.string.login_error_empty_password
            RegisterError.PASSWORDS_DO_NOT_MATCH -> R.string.register_error_passwords_do_not_match
            RegisterError.REQUEST_FAILED -> R.string.register_error_request_failed
        }
    }

    private companion object {
        const val EVENT_BUFFER_CAPACITY = 1
    }
}