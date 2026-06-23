package com.hits.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hits.impl.ui.components.ChatInput
import com.hits.impl.ui.components.ChatToolbar
import com.hits.impl.ui.components.MessageBubble
import com.hits.impl.ui.components.attachment.AttachmentItem

@Composable
fun ConversationScreen(
    state: ChatUiState,
    title: String,
    onBack: () -> Unit,
    onOpenAttachmentPicker: () -> Unit,
    onDraftChanged: (String) -> Unit,
    onSend: () -> Unit,
    onRemoveAttachment: (String) -> Unit,
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
            Column {
                if (state.attachments.isNotEmpty()) {

                    LazyRow {

                        items(state.attachments) { attachment ->

                            AttachmentItem(
                                attachment = attachment,
                                onRemove = {
                                    onRemoveAttachment(
                                        attachment.uri
                                    )
                                }
                            )
                        }
                    }
                }

                ChatInput(
                    modifier = Modifier.imePadding(),
                    value = state.messageDraft,
                    onValueChange = onDraftChanged,
                    onSend = onSend,
                    isSending = state.isSendingMessage,
                    onAttachPhoto = onOpenAttachmentPicker
                )
            }

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
                items = state.messages.reversed(),
                key = { it.id }
            ) { message ->
                MessageBubble(
                    message = message,
                )
            }
        }
    }
}