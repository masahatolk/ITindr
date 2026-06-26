package com.hits.impl.data.usecase

class LogoutUseCase (
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {
        authRepository.logout()
    }
}