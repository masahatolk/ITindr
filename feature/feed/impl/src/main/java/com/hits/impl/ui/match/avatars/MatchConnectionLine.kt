package com.hits.impl.ui.match.avatars

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MatchConnectionLine(
    progress: Float,
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
    ) {

        val avatarRadius = 66.dp.toPx()

        val avatarsDistance = avatarRadius * 2 + 56.dp.toPx() + avatarRadius * 2

        val center = size.width / 2

        val startX = center - avatarsDistance / 2 + 2 * avatarRadius

        val endX = center + avatarsDistance / 2 - 2 * avatarRadius

        drawLine(
            color = Color.White.copy(alpha = 0.35f), start = Offset(
                startX, size.height / 2
            ), end = Offset(
                startX + (endX - startX) * progress, size.height / 2
            ), strokeWidth = 7f
        )
    }
}