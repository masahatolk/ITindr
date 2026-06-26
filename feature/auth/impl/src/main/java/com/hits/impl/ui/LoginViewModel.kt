package com.hits.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.core_ui.R
import com.hits.itindr.login.domain.LoginError
import com.hits.itindr.login.domain.LoginResult
import com.hits.itindr.login.domain.LoginUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _event = MutableSharedFlow<LoginUiEvent>(extraBufferCapacity = EVENT_BUFFER_CAPACITY)
    val event: SharedFlow<LoginUiEvent> = _event.asSharedFlow()

    fun onLoginClicked(email: String, password: String) {
        viewModelScope.launch {
            val result = loginUseCase.execute(email.trim(), password)
            val event = when (result) {
                is LoginResult.Error -> LoginUiEvent.ShowError(result.type.toMessageRes())
                LoginResult.Success -> LoginUiEvent.OpenMainScreen
            }
            _event.emit(event)
        }
    }

    private fun LoginError.toMessageRes(): Int {
        return when (this) {
            LoginError.EMPTY_EMAIL -> R.string.login_error_empty_email
            LoginError.INVALID_EMAIL -> R.string.login_error_invalid_email
            LoginError.EMPTY_PASSWORD -> R.string.login_error_empty_password
            LoginError.REQUEST_FAILED -> R.string.login_error_request_failed
        }
    }

    private companion object {
        const val EVENT_BUFFER_CAPACITY = 1
    }
}
