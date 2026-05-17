package com.hits.chat_feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.hits.itindr.chat.R
import org.koin.compose.koinInject

@Composable
fun ConversationScreen() {
    val director: ChatMessageAppearanceDirector = koinInject()
    val incomingAppearance = director.createIncomingAppearance(
        backgroundColor = colorResource(id = R.color.chat_incoming_background),
    )
    val outgoingAppearance = director.createOutgoingAppearance(
        backgroundColor = colorResource(id = R.color.chat_outgoing_background),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {

        ConversationMessage(
            text = "Привет! Покажешь свой питон?",
            appearance = incomingAppearance,
            isOutgoing = false,
        )

        ConversationMessage(
            text = "Может лучше ты сначала свой перл?",
            appearance = outgoingAppearance,
            isOutgoing = true,
        )
    }
}

@Composable
private fun ConversationMessage(
    text: String,
    appearance: ChatMessageAppearance,
    isOutgoing: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isOutgoing) Arrangement.End else Arrangement.Start,
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = appearance.backgroundColor,
                    shape = RoundedCornerShape(appearance.cornerRadius.dp),
                )
                .padding(horizontal = 14.dp, vertical = 10.dp),
            contentAlignment = if (isOutgoing) Alignment.CenterEnd else Alignment.CenterStart,
        ) {
            Text(
                text = text,
                color = appearance.textColor,
                fontSize = appearance.textSize,
                fontWeight = appearance.textWeight,
            )
        }
    }
}
