package com.hits.itindr

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatScreen() {
    Box(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Чаты",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        )
    }
}