package com.hits.impl.ui.match.title

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MatchLetter(
    drawable: Int,
    glowDrawable: Int?,
    visible: Boolean,
    glowAlpha: Float,
) {

    if (!visible) return

    val scale = remember {
        Animatable(0.8f)
    }

    LaunchedEffect(Unit) {

        scale.animateTo(
            1.1f, tween(60)
        )

        scale.animateTo(
            1f, tween(60)
        )
    }

    Box(
        modifier = Modifier.height(32.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        if (glowDrawable != null) {

            Image(
                painter = painterResource(glowDrawable),
                contentDescription = null,
                modifier = Modifier
                    .alpha(glowAlpha)
                    .matchParentSize()
            )
        }

        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                }
            )
    }
}