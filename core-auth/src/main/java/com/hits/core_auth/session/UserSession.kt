package com.hits.core_auth.session

interface UserSession {

    fun getUserId(): String?

    fun saveUserId(
        userId: String
    )

    fun clear()
}
