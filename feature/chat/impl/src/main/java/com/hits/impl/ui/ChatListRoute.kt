package com.hits.impl.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hits.api.model.Chat
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatListRoute(
    onOpenChat: (Chat) -> Unit,
    viewModel: ChatViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleResumeEffect(Unit) {

        viewModel.onIntent(ChatIntent.LoadChats)

        onPauseOrDispose {}
    }

    ChatListScreen(
        state = state,
        onOpenChat = { chat ->
            onOpenChat(chat)
        },
        onCreateChat = {
            viewModel.onIntent(
                ChatIntent.CreateChat(it)
            )
        },
        onRefresh = {
            viewModel.onIntent(
                ChatIntent.LoadChats
            )
        }
    )
}