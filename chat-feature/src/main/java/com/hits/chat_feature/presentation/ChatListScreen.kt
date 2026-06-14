package com.hits.chat_feature.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hits.chat_feature.ChatUiState
import com.hits.chat_feature.domain.Chat
import com.hits.chat_feature.domain.MatchUi
import com.hits.chat_feature.presentation.components.MatchesRow


@Composable
fun ChatListScreen(
    state: ChatUiState,
    onOpenChat: (Chat) -> Unit,
    onCreateChat: (String) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedMatch by remember { mutableStateOf<MatchUi?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Чаты", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.matches.isNotEmpty()) {
                MatchesRow(
                    matches = state.matches,
                    onMatchClick = { match ->
                        selectedMatch = match
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            when {
                state.isChatsLoading -> FullScreenLoader(text = "Загружаем чаты")
                state.chats.isEmpty() -> Text(text = "Чатов пока нет")
                else -> LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 96.dp),
                ) {
                    items(state.chats, key = { it.id }) { chat ->
                        ChatListItem(chat = chat, onClick = { onOpenChat(chat) })
                    }
                }
            }
        }
    }

    selectedMatch?.let { match ->
        AlertDialog(
            onDismissRequest = { selectedMatch = null },
            title = { Text("Создание чата") },
            text = { Text("Хотите создать чат с ${match.name}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onCreateChat(match.userId)
                        selectedMatch = null
                    }
                ) {
                    Text("Да")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { selectedMatch = null }
                ) {
                    Text("Нет")
                }
            }
        )
    }
}