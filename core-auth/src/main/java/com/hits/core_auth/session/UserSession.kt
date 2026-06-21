package com.hits.core_auth.session

import java.util.concurrent.atomic.AtomicReference

data class UserSessionState(
    val userId: String? = null
)

class UserSession {

    private val state = AtomicReference(UserSessionState())

    fun setUserId(userId: String?) {
        state.updateAndGet {
            it.copy(userId = userId)
        }
    }

    fun getUserId(): String? = state.get().userId

    fun clear() {
        state.set(UserSessionState())
    }
}