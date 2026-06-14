package com.hits.chat_feature

import androidx.lifecycle.ViewModel
import com.hits.chat_feature.domain.Chat
import com.hits.chat_feature.domain.ChatMessage
import com.hits.chat_feature.domain.MatchUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatViewModel(
    private val repository: ChatRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState(isChatsLoading = true))
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        onIntent(ChatIntent.LoadChats)
    }

    fun onIntent(intent: ChatIntent) {
        when (intent) {
            ChatIntent.LoadChats -> loadChats()
            is ChatIntent.OpenChat -> openChat(intent.chat)
            ChatIntent.CloseChat -> _uiState.update { it.copy(selectedChat = null, messages = emptyList(), messageDraft = "") }
            is ChatIntent.MessageDraftChanged -> _uiState.update { it.copy(messageDraft = intent.text) }
            ChatIntent.SendMessage -> sendMessage()
            is ChatIntent.CreateChat -> createChat(intent.companionId)
            ChatIntent.ErrorShown -> _uiState.update { it.copy(snackbarMessage = null) }
        }
    }

    private fun loadChats(){}

    private fun openChat(chat: Chat) {}

    private fun createChat(companionId: String) {}

    private fun sendMessage() {}
}

data class ChatUiState(
    val chats: List<Chat> = emptyList(),
    val selectedChat: Chat? = null,
    val messages: List<ChatMessage> = emptyList(),
    val messageDraft: String = "",
    val isChatsLoading: Boolean = false,
    val isMessagesLoading: Boolean = false,
    val isCreatingChat: Boolean = false,
    val isSendingMessage: Boolean = false,
    val snackbarMessage: String? = null,
    val matches: List<MatchUi> = emptyList()
)

sealed interface ChatIntent {
    data object LoadChats : ChatIntent
    data class OpenChat(val chat: Chat) : ChatIntent
    data object CloseChat : ChatIntent
    data class MessageDraftChanged(val text: String) : ChatIntent
    data object SendMessage : ChatIntent
    data class CreateChat(val companionId: String) : ChatIntent
    data object ErrorShown : ChatIntent
}