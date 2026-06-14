package com.hits.itindr.login.domain

class LogoutUseCase (
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {
        authRepository.logout()
    }
}