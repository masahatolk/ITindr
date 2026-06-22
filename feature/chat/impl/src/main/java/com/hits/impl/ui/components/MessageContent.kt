package com.hits.impl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hits.api.model.ChatMessageUi
import com.hits.impl.ui.ChatMessageAppearance


@Composable
fun MessageContent(message: ChatMessageUi, appearance: ChatMessageAppearance) {
    Box(
        modifier = Modifier
            .widthIn(max = 250.dp)
            .clip(
                if (message.isOutgoing) {
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
            )
            .background(appearance.backgroundColor)
            .padding(14.dp),
        contentAlignment = if(message.isOutgoing) Alignment.TopEnd else Alignment.TopStart
    ) {
        Column {
            Text(
                text = message.text,
                color = appearance.textColor,
                fontSize = appearance.textSize,
                fontWeight = appearance.textWeight
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = message.createdAt,
                fontSize = appearance.textDateSize,
                color = appearance.textColor.copy(alpha = 0.5f)
            )
        }
    }
}