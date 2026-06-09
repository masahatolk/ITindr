package com.hits.itindr.login.domain

class LoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend fun execute(email: String, password: String): LoginResult {
        if (email.isBlank()) {
            return LoginResult.Error(LoginError.EMPTY_EMAIL)
        }

        if (!EMAIL_REGEX.matches(email)) {
            return LoginResult.Error(LoginError.INVALID_EMAIL)
        }

        if (password.isBlank()) {
            return LoginResult.Error(LoginError.EMPTY_PASSWORD)
        }

        return runCatching {
            authRepository.login(email, password)
        }.fold(
            onSuccess = { LoginResult.Success },
            onFailure = { LoginResult.Error(LoginError.REQUEST_FAILED) },
        )
    }

    private companion object {
        val EMAIL_REGEX =
            Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    }
}

sealed interface LoginResult {
    data object Success : LoginResult

    data class Error(val type: LoginError) : LoginResult
}

enum class LoginError {
    EMPTY_EMAIL,
    INVALID_EMAIL,
    EMPTY_PASSWORD,
    REQUEST_FAILED,
}
