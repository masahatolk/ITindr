package com.hits.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hits.core_ui.GradientBackground
import com.hits.core_ui.Toolbar
import com.hits.impl.ui.components.ChatInput
import com.hits.impl.ui.components.MessageBubble
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ConversationScreen(
    state: ChatUiState,
    title: String,
    onBack: () -> Unit,
    onOpenAttachmentPicker: () -> Unit,
    onDraftChanged: (String) -> Unit,
    onSend: () -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val listState = rememberLazyListState()


    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemIndex
        }.distinctUntilChanged().collect { firstVisibleIndex ->
                val total = listState.layoutInfo.totalItemsCount

            android.util.Log.d(
                "SCROLL_DEBUG",
                "index=$firstVisibleIndex total=$total"
            )

                if (total > 0 && firstVisibleIndex >= total - 10) {
                    android.util.Log.d(
                        "SCROLL_DEBUG",
                        "TRIGGER LOAD MORE"
                    )
                    onLoadMore()
                }
            }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),

        containerColor = Color.Transparent,

        topBar = {
            Toolbar(
                title = title, onBack = onBack
            )
        },

        bottomBar = {
            ChatInput(
                modifier = Modifier.imePadding(),
                value = state.messageDraft,
                onValueChange = onDraftChanged,
                onSend = onSend,
                isSending = state.isSendingMessage,
                onAttachPhoto = onOpenAttachmentPicker
            )
        }) { innerPadding ->

        GradientBackground (1f) {
            LazyColumn(
                state = listState,
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
                    items = state.messages, key = { it.id }) { message ->
                    MessageBubble(
                        message = message,
                    )
                }
            }
        }
    }
}