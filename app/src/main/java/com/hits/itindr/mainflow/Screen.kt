package com.hits.itindr.mainflow

import androidx.compose.runtime.Composable
import com.hits.chat_feature.ChatScreen
import com.hits.itindr.mainflow.feed.FeedScreen

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
