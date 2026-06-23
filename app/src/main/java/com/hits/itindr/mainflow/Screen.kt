package com.hits.itindr.mainflow

import android.net.Uri
import com.hits.api.model.Chat

sealed class Screen(val route: String) {

    data object Feed : Screen("feed")

    data object People : Screen("people")

    data object ChatList : Screen("chat_list")

    data object Conversation : Screen("conversation/{chatId}/{chatTitle}") {
        fun createRoute(chat: Chat) =
            "conversation/${chat.id}/${Uri.encode(chat.title)}"
    }

    data object Profile : Screen("profile")
    data object EditProfile :
        Screen("edit_profile")
}

val tabOrder = mapOf(
    Screen.Feed.route to 0,
    Screen.People.route to 1,
    Screen.ChatList.route to 2,
    Screen.Profile.route to 3,
)
