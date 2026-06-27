package com.hits.impl.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hits.core_ui.R

@Composable
fun IconSendButtonContainer(
    icon: Int,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {

    val alpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.4f,
        label = "send_alpha"
    )

    val bgColor = if (enabled) {
        Color.White
    } else {
        colorResource(R.color.light_gray)
    }

    Surface(
        modifier = modifier
            .size(42.dp)
            .graphicsLayer {
                this.alpha = alpha
            },
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        enabled = enabled,
        color = bgColor,
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}