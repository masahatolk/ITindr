package com.hits.itindr.main_flow

import androidx.compose.runtime.Composable
import com.hits.itindr.main_flow.chat.ChatScreen
import com.hits.itindr.main_flow.feed.FeedScreen

sealed class Screen(val route: String, val content: @Composable () -> Unit) {
    data object Feed : Screen("feed", { FeedScreen() })
    data object People : Screen("people", { PeopleScreen() })
    data object Chat : Screen("chat", { ChatScreen() })
    data object Profile : Screen("profile", { ProfileScreen() })
}

val screens = listOf(
    Screen.Feed,
    Screen.People,
    Screen.Chat,
    Screen.Profile
)
