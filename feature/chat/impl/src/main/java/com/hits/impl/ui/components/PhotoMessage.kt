package com.hits.impl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hits.api.model.ChatMessageUi
import com.hits.core_ui.R
import com.hits.impl.ui.ChatMessageAppearance

@Composable
fun PhotoMessage(
    message: ChatMessageUi,
    appearance: ChatMessageAppearance,
    shape: Shape
) {
    Box(
        modifier = Modifier
            .widthIn(max = 250.dp)
            .clip(shape)
            .background(appearance.backgroundColor),
        contentAlignment = if(message.isOutgoing) Alignment.BottomEnd else Alignment.BottomStart,
    ) {
        AsyncImage(
            model = message.attachments.first(),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .padding(14.dp)
                .background(
                    color =colorResource(R.color.black_transparent50),
                    shape = RoundedCornerShape(12.dp),
                ),
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                text = message.createdAt,
                fontSize = appearance.textDateSize,
                color = appearance.textColor
            )
        }
    }
}