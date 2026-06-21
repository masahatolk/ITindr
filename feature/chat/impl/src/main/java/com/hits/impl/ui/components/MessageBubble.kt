package com.hits.impl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            if (message.isOutgoing) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
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
                .padding(14.dp)
        ) {
            Text(
                text = message.text,
                color = appearance.textColor,
                fontSize = appearance.textSize,
                fontWeight = appearance.textWeight
            )
        }
    }
}