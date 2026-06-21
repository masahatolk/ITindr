package com.hits.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hits.impl.ui.components.ChatInput
import com.hits.impl.ui.components.ChatToolbar
import com.hits.impl.ui.components.MessageBubble

@Composable
fun ConversationScreen(
    state: ChatUiState,
    title: String,
    onBack: () -> Unit,
    onDraftChanged: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),

        containerColor = Color.Transparent,

        topBar = {
            ChatToolbar(
                title = title,
                onBack = onBack
            )
        },

        bottomBar = {
            ChatInput(
                modifier = Modifier.imePadding(),
                value = state.messageDraft,
                onValueChange = onDraftChanged,
                onSend = onSend,
                isSending = state.isSendingMessage,
                onAttachPhoto = {}
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            reverseLayout = true,
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = innerPadding.calculateTopPadding() + 16.dp,
                bottom = innerPadding.calculateBottomPadding() + 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = state.messages,
                key = { it.id }
            ) { message ->
                MessageBubble(
                    message = message,
                )
            }
        }
    }
}