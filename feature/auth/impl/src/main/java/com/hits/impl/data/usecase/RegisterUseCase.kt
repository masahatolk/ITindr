package com.hits.impl.data.usecase

class RegisterUseCase(private val authRepository: AuthRepository) {

    fun validate(
        email: String,
        password: String,
        passwordConfirm: String
    ): RegisterResult {

        if (email.isBlank()) {
            return RegisterResult.Error(RegisterError.EMPTY_EMAIL)
        }

        if (!EMAIL_REGEX.matches(email)) {
            return RegisterResult.Error(RegisterError.INVALID_EMAIL)
        }

        if (password.isBlank()) {
            return RegisterResult.Error(RegisterError.EMPTY_PASSWORD)
        }

        if (password != passwordConfirm) {
            return RegisterResult.Error(RegisterError.PASSWORDS_DO_NOT_MATCH)
        }

        return RegisterResult.Success
    }

    private companion object {
        val EMAIL_REGEX =
            Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    }
}

sealed interface RegisterResult {
    data object Success : RegisterResult

    data class Error(val type: RegisterError) : RegisterResult
}

enum class RegisterError {
    EMPTY_EMAIL,
    INVALID_EMAIL,
    EMPTY_PASSWORD,
    PASSWORDS_DO_NOT_MATCH,
    REQUEST_FAILED,
}
