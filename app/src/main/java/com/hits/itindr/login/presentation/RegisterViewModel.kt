package com.hits.itindr.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.itindr.R
import com.hits.itindr.login.domain.RegisterError
import com.hits.itindr.login.domain.RegisterResult
import com.hits.itindr.login.domain.RegisterUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _event = MutableSharedFlow<RegisterUiEvent>(extraBufferCapacity = EVENT_BUFFER_CAPACITY)
    val event: SharedFlow<RegisterUiEvent> = _event.asSharedFlow()

    fun onRegisterClicked(email: String, password: String, passwordConfirm: String) {
        viewModelScope.launch {
            val result = registerUseCase.execute(email.trim(), password, passwordConfirm)
            val event = when (result) {
                is RegisterResult.Error -> RegisterUiEvent.ShowError(result.type.toMessageRes())
                RegisterResult.Success -> RegisterUiEvent.OpenInfoScreen
            }
            _event.emit(event)
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