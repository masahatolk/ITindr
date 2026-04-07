package com.hits.itindr.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hits.itindr.R
import com.hits.itindr.login.domain.LoginError
import com.hits.itindr.login.domain.LoginResult
import com.hits.itindr.login.domain.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _event = MutableLiveData<LoginUiEvent>()
    val event: LiveData<LoginUiEvent> = _event

    fun onLoginClicked(email: String, password: String) {
        val result = loginUseCase.execute(email.trim(), password)
        _event.value = when (result) {
            is LoginResult.Error -> LoginUiEvent.ShowError(result.type.toMessageRes())
            LoginResult.Success -> LoginUiEvent.OpenMainScreen
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
}
