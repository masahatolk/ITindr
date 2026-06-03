package com.hits.chat_feature

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class ConversationViewModel(
    private val director: ChatMessageAppearanceDirector,
) : ViewModel() {

    fun buildState(): ConversationUiState {
        val incomingAppearance = director.createIncomingAppearance(
            backgroundColor = Color(0xFF35373B),
        )
        val outgoingAppearance = director.createOutgoingAppearance(
            backgroundColor = Color(0xFF5A1FCC),
        )

        return ConversationUiState(
            messages = listOf(
                ConversationMessageUi(
                    text = "Привет! Покажешь свой питон?",
                    appearance = incomingAppearance,
                    isOutgoing = false,
                ),
                ConversationMessageUi(
                    text = "Может лучше ты сначала свой перл?",
                    appearance = outgoingAppearance,
                    isOutgoing = true,
                ),
            ),
        )
    }
}

data class ConversationUiState(
    val messages: List<ConversationMessageUi>,
)

data class ConversationMessageUi(
    val text: String,
    val appearance: ChatMessageAppearance,
    val isOutgoing: Boolean,
)
