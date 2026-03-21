package com.hits.itindr

import androidx.compose.runtime.Composable

sealed class Screen(val route: String, val content: @Composable () -> Unit) {
    object Feed : Screen("feed", { FeedScreen() })
    object People : Screen("people", { PeopleScreen() })
    object Chat : Screen("chat", { ChatScreen() })
    object Profile : Screen("profile", { ProfileScreen() })
}

val screens = listOf(
    Screen.Feed,
    Screen.People,
    Screen.Chat,
    Screen.Profile
)
