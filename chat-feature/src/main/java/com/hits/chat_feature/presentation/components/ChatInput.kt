package com.hits.chat_feature.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChatInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    isSending: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // TODO photo
//        Button(
//            onClick =
//        ) {}

        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text("Сообщение...")
            }
        )

        Spacer(Modifier.width(8.dp))

        Button(
            onClick = onSend,
            enabled = value.isNotBlank() && !isSending
        ) {}
    }
}