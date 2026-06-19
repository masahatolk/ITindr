package com.hits.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.api.model.Chat
import com.hits.api.model.ChatMessage
import com.hits.api.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    private fun loadChats(){
        viewModelScope.launch {

            _uiState.update { it.copy(isChatsLoading = true, snackbarMessage = null) }
            runCatching { repository.getChats() }
                .onSuccess { result ->

                    _uiState.update {
                        it.copy(
                            chats = result.value,
                            isChatsLoading = false,
                            snackbarMessage = if (result.fromCache) CACHE_CHATS_MESSAGE else null,
                        )
                    }
                }
                .onFailure { throwable -> showError(throwable, loadingChats = false) }
        }
    }

    private fun openChat(chat: Chat) {
        _uiState.update { it.copy(selectedChat = chat, isMessagesLoading = true, snackbarMessage = null) }
        viewModelScope.launch {
            runCatching { repository.getMessages(chat.id) }
                .onSuccess { result ->
                    _uiState.update {
                        it.copy(
                            messages = result.value,
                            isMessagesLoading = false,
                            snackbarMessage = if (result.fromCache) CACHE_MESSAGES_MESSAGE else null,
                        )
                    }
                }
                .onFailure { throwable -> showError(throwable, loadingMessages = false) }
        }
    }

    // TODO
    private fun createChat(companionId: String) {
        val normalizedCompanionId = companionId.trim()
        if (normalizedCompanionId.isBlank()) {
            _uiState.update { it.copy(snackbarMessage = "?") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isCreatingChat = true, snackbarMessage = null) }
            runCatching { repository.createChat(normalizedCompanionId) }
                .onSuccess { chat ->
                    _uiState.update { state ->
                        state.copy(
                            chats = listOf(chat) + state.chats.filterNot { it.id == chat.id },
                            isCreatingChat = false,
                            selectedChat = chat,
                        )
                    }
                    openChat(chat)
                }
                .onFailure { throwable -> showError(throwable, creatingChat = false) }
        }
    }

    private fun sendMessage() {
        val state = _uiState.value
        val chat = state.selectedChat ?: return
        val text = state.messageDraft.trim()
        if (text.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSendingMessage = true, snackbarMessage = null) }
            runCatching { repository.sendMessage(chat.id, text) }
                .onSuccess { message -> appendSentMessage(chat, message) }
                .onFailure { throwable -> showError(throwable, sendingMessage = false) }
        }
    }

    private fun appendSentMessage(chat: Chat, message: ChatMessage) {
        _uiState.update { state ->
            val updatedChat = chat.copy(lastMessage = message.text, updatedAt = message.createdAt)
            state.copy(
                selectedChat = updatedChat,
                chats = listOf(updatedChat) + state.chats.filterNot { it.id == updatedChat.id },
                messages = state.messages + message.copy(isOutgoing = true),
                messageDraft = "",
                isSendingMessage = false,
            )
        }
    }

    private fun showError(
        throwable: Throwable,
        loadingChats: Boolean? = null,
        loadingMessages: Boolean? = null,
        creatingChat: Boolean? = null,
        sendingMessage: Boolean? = null,
    ) {
        _uiState.update { state ->
            state.copy(
                isChatsLoading = loadingChats ?: state.isChatsLoading,
                isMessagesLoading = loadingMessages ?: state.isMessagesLoading,
                isCreatingChat = creatingChat ?: state.isCreatingChat,
                isSendingMessage = sendingMessage ?: state.isSendingMessage,
                snackbarMessage = throwable.message?.takeIf(String::isNotBlank) ?: DEFAULT_ERROR_MESSAGE,
            )
        }
    }

    private companion object {
        const val DEFAULT_ERROR_MESSAGE = "Не удалось выполнить запрос чата"
        const val CACHE_CHATS_MESSAGE = "Нет соединения: список чатов загружен из кэша"
        const val CACHE_MESSAGES_MESSAGE = "Нет соединения: история сообщений загружена из кэша"
    }
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