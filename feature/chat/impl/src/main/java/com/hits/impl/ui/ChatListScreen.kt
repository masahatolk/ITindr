package com.hits.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hits.api.model.Chat
import com.hits.core_ui.AppTextStyles
import com.hits.core_ui.R
import com.hits.impl.ui.components.ChatListItem
import com.hits.core_ui.FullScreenLoader


@Composable
fun ChatListScreen(
    state: ChatUiState,
    onOpenChat: (Chat) -> Unit,
    onCreateChat: (String) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        modifier = modifier
            .fillMaxSize(),

        containerColor = Color.Transparent,

        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp)
                    .systemBarsPadding(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.title_chats),
                    style = AppTextStyles.Header
                )
            }
        },

        ) { innerPadding ->

        when {
            state.isChatsLoading -> FullScreenLoader(text = "Загружаем чаты")
            state.chats.isEmpty() -> Text(text = "Чатов пока нет", color = Color.White)
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    bottom = innerPadding.calculateBottomPadding() + 100.dp
                )
            ) {
                items(state.chats, key = { it.id }) { chat ->
                    ChatListItem(chat = chat, onClick = { onOpenChat(chat) })
                }
            }
        }
    }
}