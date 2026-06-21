package com.hits.impl.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConversationRoute(
    chatId: String,
    title: String,
    onBack: () -> Unit,
    viewModel: ChatViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(chatId) {
        viewModel.onIntent(
            ChatIntent.LoadMessages(chatId)
        )
    }

    ConversationScreen(
        state = state,
        title = title,
        onBack = onBack,
        onDraftChanged = {
            viewModel.onIntent(
                ChatIntent.MessageDraftChanged(it)
            )
        },
        onSend = {
            viewModel.onIntent(
                ChatIntent.SendMessage
            )
        }
    )
}