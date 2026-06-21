package com.hits.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hits.api.model.Chat
import com.hits.api.model.ChatMessage
import com.hits.api.model.ChatMessageUi
import com.hits.api.repository.ChatRepository
import com.hits.core_auth.session.UserSession
import com.hits.impl.data.mapper.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository,
    private val userSession: UserSession,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        ChatUiState(
            isChatsLoading = true
        )
    )
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        onIntent(ChatIntent.LoadChats)
    }

    fun onIntent(intent: ChatIntent) {
        when (intent) {
            ChatIntent.LoadChats -> loadChats()

            is ChatIntent.LoadMessages ->
                loadMessages(intent.chatId)

            is ChatIntent.MessageDraftChanged ->
                _uiState.update { it.copy(messageDraft = intent.text) }

            ChatIntent.SendMessage ->
                sendMessage()

            is ChatIntent.CreateChat ->
                createChat(intent.companionId)

            ChatIntent.ErrorShown ->
                _uiState.update { it.copy(snackbarMessage = null) }
        }
    }

    private fun loadChats() {
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
                .onFailure { throwable ->
                    showError(throwable, loadingChats = false)
                }
        }
    }

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
                            chats = listOf(chat) + state.chats,
                            isCreatingChat = false,
                        )
                    }

                    loadChats()
                }
                .onFailure { throwable -> showError(throwable, creatingChat = false) }
        }
    }

    private fun sendMessage() {

        val state = _uiState.value

        val chatId = state.currentChatId ?: return

        val text = state.messageDraft.trim()

        if (text.isBlank()) return

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isSendingMessage = true,
                    snackbarMessage = null,
                )
            }

            runCatching {
                repository.sendMessage(chatId, text)
            }
                .onSuccess { message ->
                    appendSentMessage(message)
                }
                .onFailure {
                    showError(
                        it,
                        sendingMessage = false
                    )
                }
        }
    }

    private fun appendSentMessage(
        message: ChatMessage
    ) {
        val currentUserId =
            userSession.getUserId().orEmpty()

        val uiMessage =
            message.toUi(currentUserId)

        _uiState.update {
            it.copy(
                messages = it.messages + uiMessage,
                messageDraft = "",
                isSendingMessage = false,
            )
        }
    }

    private fun loadMessages(chatId: String) {

        _uiState.update {
            it.copy(
                currentChatId = chatId,
                isMessagesLoading = true
            )
        }

        viewModelScope.launch {

            runCatching {
                repository.getMessages(chatId)
            }
                .onSuccess { result ->

                    val currentUserId = userSession.getUserId()

                    val uiMessages =
                        result.value.map { message ->

                            println("CHAT USER ID = ${message.userId}")

                            ChatMessageUi(
                                id = message.id,
                                text = message.text,
                                senderName = message.senderName,
                                isOutgoing =
                                    message.userId == currentUserId
                            )
                        }

                    _uiState.update {
                        it.copy(
                            messages = uiMessages,
                            isMessagesLoading = false
                        )
                    }
                }
                .onFailure {
                    showError(
                        it,
                        loadingMessages = false
                    )
                }
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
                snackbarMessage = throwable.message?.takeIf(String::isNotBlank)
                    ?: DEFAULT_ERROR_MESSAGE,
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
    val currentChatId: String? = null,
    val chatTitle: String = "",
    val chats: List<Chat> = emptyList(),
    val messages: List<ChatMessageUi> = emptyList(),
    val messageDraft: String = "",
    val isChatsLoading: Boolean = false,
    val isMessagesLoading: Boolean = false,
    val isCreatingChat: Boolean = false,
    val isSendingMessage: Boolean = false,
    val snackbarMessage: String? = null,
)

sealed interface ChatIntent {
    data class LoadMessages(
        val chatId: String
    ) : ChatIntent

    data object LoadChats : ChatIntent
    data class MessageDraftChanged(val text: String) : ChatIntent
    data object SendMessage : ChatIntent
    data class CreateChat(val companionId: String) : ChatIntent
    data object ErrorShown : ChatIntent
}
