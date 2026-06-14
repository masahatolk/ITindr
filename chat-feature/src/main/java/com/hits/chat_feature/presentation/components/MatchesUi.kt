package com.hits.chat_feature.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hits.chat_feature.domain.MatchUi
import com.hits.itindr.chat.R
import kotlinx.coroutines.delay

@Composable
fun MatchesRow(
    matches: List<MatchUi>,
    onMatchClick: (MatchUi) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(matches, key = { it.userId }) { match ->
            MatchItem(
                match = match,
                onClick = { onMatchClick(match) }
            )
        }
    }
}

@Composable
fun MatchItem(
    match: MatchUi,
    onClick: () -> Unit,
) {
    val progress by rememberCountdownProgress(match.expiresAt)
    val indicatorColor = colorResource(R.color.match_indicator)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Canvas(
                modifier = Modifier.size(74.dp)
            ) {
                drawArc(
                    color = Color.DarkGray,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(6f)
                )

                drawArc(
                    color = indicatorColor,
                    startAngle = -90f,
                    sweepAngle = 360 * progress,
                    useCenter = false,
                    style = Stroke(6f)
                )
            }

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = match.name,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}

@Composable
fun rememberCountdownProgress(
    expiresAt: Long
): State<Float> {
    val progress = remember { mutableFloatStateOf(1f) }

    LaunchedEffect(expiresAt) {
        while (true) {
            val now = System.currentTimeMillis()
            val total = 24 * 60 * 60 * 1000L
            val remaining = (expiresAt - now).coerceAtLeast(0)

            progress.floatValue = remaining.toFloat() / total

            delay(1000)
        }
    }

    return progress
}