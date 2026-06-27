package com.hits.impl.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.hits.api.model.ChatMessageUi
import com.hits.core_ui.R
import com.hits.impl.ui.ChatMessageAppearanceDirector

@Composable
fun MessageBubble(
    message: ChatMessageUi,
    director: ChatMessageAppearanceDirector = org.koin.compose.koinInject()
) {

    val outgoingColor = colorResource(R.color.chat_outgoing_background)
    val incomingColor = colorResource(R.color.chat_incoming_background)

    val appearance =
        if (message.isOutgoing) {
            director.createOutgoingAppearance(outgoingColor)
        } else {
            director.createIncomingAppearance(incomingColor)
        }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement =
            if (message.isOutgoing) Arrangement.End
            else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!message.isOutgoing) {
            UserAvatar(message.avatar)
            Spacer(Modifier.width(8.dp))
        }

        MessageContent(message, appearance)

        if (message.isOutgoing) {
            Spacer(Modifier.width(8.dp))
            UserAvatar(message.avatar)
        }
    }
}