package com.hits.impl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hits.impl.R

@Composable
fun ChatInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    onAttachPhoto: () -> Unit,
    isSending: Boolean
) {

    val isNotEmpty = value.isNotBlank()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconCameraButtonContainer(
            icon = R.drawable.camera,
            onClick = onAttachPhoto
        )

        Spacer(Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(com.hits.core_ui.R.color.bottom_nav_gray))
        ) {

            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 72.dp),

                placeholder = {
                    Text("Сообщение...")
                },

                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                ),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,

                    focusedPlaceholderColor = colorResource(com.hits.core_ui.R.color.light_gray),
                    unfocusedPlaceholderColor = colorResource(com.hits.core_ui.R.color.light_gray),

                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.Gray,
                ),
                minLines = 1,
                maxLines = 5,

            )

            IconSendButtonContainer(
                icon = R.drawable.send,
                onClick = onSend,
                enabled = isNotEmpty,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
            )
        }
    }
}