package com.hits.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hits.api.model.Chat
import com.hits.impl.ui.components.ChatListItem
import com.hits.impl.ui.components.FullScreenLoader


@Composable
fun ChatListScreen(
    state: ChatUiState,
    onOpenChat: (Chat) -> Unit,
    onCreateChat: (String) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {

    // TODO Scaffold

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 16.dp)
            .systemBarsPadding(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Чаты", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isChatsLoading -> FullScreenLoader(text = "Загружаем чаты")
            state.chats.isEmpty() -> Text(text = "Чатов пока нет", color = Color.White)
            else -> LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(state.chats, key = { it.id }) { chat ->
                    ChatListItem(chat = chat, onClick = { onOpenChat(chat) })
                }
            }
        }
    }
}