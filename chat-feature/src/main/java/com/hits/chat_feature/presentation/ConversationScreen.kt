package com.hits.chat_feature.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hits.chat_feature.presentation.components.ChatInput
import com.hits.chat_feature.presentation.components.ChatToolbar
import com.hits.chat_feature.presentation.components.MessageBubble

@Composable
fun ConversationScreen(
    state: ChatUiState,
    onBack: () -> Unit,
    onDraftChanged: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
    ) {
        ChatToolbar(
            title = state.selectedChat?.title.orEmpty(),
            onBack = onBack
        )

        if (state.isMessagesLoading) {
            FullScreenLoader(
                modifier = Modifier.weight(1f),
                text = "Загрузка..."
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                reverseLayout = true,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    state.messages.reversed(),
                    key = { it.id }
                ) { message ->
                    MessageBubble(message)
                }
            }
        }

        ChatInput(
            value = state.messageDraft,
            onValueChange = onDraftChanged,
            onSend = onSend,
            isSending = state.isSendingMessage
        )
    }
}