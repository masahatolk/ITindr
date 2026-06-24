package com.hits.impl.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.hits.api.model.ChatMessageUi
import com.hits.impl.ui.ChatMessageAppearance


@Composable
fun MessageContent(
    message: ChatMessageUi, appearance: ChatMessageAppearance
) {
    val shape = if (message.isOutgoing) {
        RoundedCornerShape(
            topStart = appearance.cornerRadius.dp,
            topEnd = appearance.cornerRadius.dp,
            bottomStart = appearance.cornerRadius.dp
        )
    } else {
        RoundedCornerShape(
            topStart = appearance.cornerRadius.dp,
            topEnd = appearance.cornerRadius.dp,
            bottomEnd = appearance.cornerRadius.dp
        )
    }

    if (message.attachments.isNotEmpty()) {
        PhotoMessage(
            message = message, appearance = appearance, shape = shape
        )
    } else {
        TextMessage(
            message = message, appearance = appearance, shape = shape
        )
    }
}