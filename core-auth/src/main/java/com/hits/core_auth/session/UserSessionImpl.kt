package com.hits.core_auth.session

import android.content.Context
import androidx.core.content.edit

class UserSessionImpl(
    context: Context
) : UserSession {

    private val prefs =
        context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

    override fun getUserId(): String? {

        val userId =
            prefs.getString(KEY_USER_ID, null)
                ?: return null

        return userId
    }

    override fun saveUserId(
        userId: String
    ) {
        prefs.edit {
            putString(
                KEY_USER_ID,
                userId
            )
        }
    }

    override fun clear() {
        prefs.edit {
            remove(KEY_USER_ID)
        }
    }

    private companion object {
        const val PREFERENCES_NAME = "user_session"
        const val KEY_USER_ID = "user_id"
    }
}