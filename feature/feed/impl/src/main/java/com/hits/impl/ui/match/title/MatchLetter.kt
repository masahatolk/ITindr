package com.hits.impl.ui.match.title

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource

@Composable
fun MatchLetter(
    drawable: Int,
    glowDrawable: Int?,
    visible: Boolean,
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

    Layout(
        content = {
            if (glowDrawable != null) {
                Image(
                    painter = painterResource(glowDrawable),
                    contentDescription = null
                )
            }

            Image(
                painter = painterResource(drawable),
                contentDescription = null
            )
        }
    ) { measurables, constraints ->

        val glow = measurables[0].measure(constraints)
        val icon = measurables[1].measure(constraints)

        layout(icon.width, icon.height) {

            glow.place(
                x = (icon.width - glow.width) / 2,
                y = (icon.height - glow.height) / 2
            )

            icon.place(0, 0)
        }
    }
}